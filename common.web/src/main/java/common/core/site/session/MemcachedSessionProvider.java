package common.core.site.session;

import common.core.app.lang.TimeLength;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.monitor.status.ServiceStatus;
import common.core.app.monitor.status.ServiceStatusMonitor;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.web.context.WebSetting;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;

public class MemcachedSessionProvider extends  AbstractSessionProvider implements ServiceStatusMonitor {
	private final Logger logger = LoggerFactory.getLogger(MemcachedSessionProvider.class);

	private static final TimeLength MEMCACHED_TIME_OUT = TimeLength.seconds(3);
	private MemcachedClient memcachedClient;

	public MemcachedSessionProvider() {
		super();
		logger.debug("init:{}", this.getClass().getName());
	}

	@PostConstruct
	private void initMemcachedClient() {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(WebSetting.get().getSessionServers()));
		builder.setCommandFactory(new BinaryCommandFactory());
		builder.setOpTimeout(MEMCACHED_TIME_OUT.toMilliseconds());
		try {
			memcachedClient = builder.build();
			AssertErrorUtils.assertNotNull(memcachedClient, "memcachedClient init fail!");
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
	}

	@Override
	public ServiceStatus getServiceStatus() throws Exception {
		Collection<InetSocketAddress> availableServers = memcachedClient.getAvailableServers();
		return availableServers.isEmpty() ? ServiceStatus.DOWN : ServiceStatus.UP;
	}

	@Override
	public String getServiceName() {
		return "Memcached Sessions";
	}

	@Override
	protected SessionData generateSessionData(String sessionKey, int expirationTime) throws Exception{
		SessionData sessionData = memcachedClient.getAndTouch(sessionKey, expirationTime());
		if (null != sessionData && sessionData.getMaxInactiveInterval() != expirationTime()) {
			logger.debug("update MaxInactiveInterval from {} to {}", expirationTime(), sessionData.getMaxInactiveInterval());
			sessionData = memcachedClient.getAndTouch(sessionKey, sessionData.getMaxInactiveInterval());
		}
		return  sessionData;
	}

	@Override
	protected void modifySessionData(String sessionKey, SessionData sessionData) throws Exception {
		memcachedClient.set(sessionKey, sessionData.getMaxInactiveInterval(), sessionData);
	}
}
