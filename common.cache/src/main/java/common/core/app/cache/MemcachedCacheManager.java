package common.core.app.cache;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.annotation.PreDestroy;

import org.springframework.cache.Cache;
import org.springframework.cache.support.AbstractCacheManager;

import common.core.app.lang.TimeLength;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.monitor.status.ServiceStatusMonitor;
import common.core.app.monitor.status.ServiceStatus;
import common.core.app.runtime.RuntimeSetting;
import common.core.common.assertion.util.AssertErrorUtils;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;

/**
 * @author zmuwang
 */
public class MemcachedCacheManager extends AbstractCacheManager implements CustomCacheManager, ServiceStatusMonitor {
	private final Logger logger = LoggerFactory.getLogger(MemcachedCacheManager.class);
	private static final TimeLength MEMCACHED_TIME_OUT = TimeLength.seconds(3);

	protected final List<MemcachedCache> caches = new ArrayList<>();
	protected MemcachedClient memcachedClient;
	private String version;

	public MemcachedCacheManager() {
		super();
		this.setServers(CacheSettings.get().getServers());
		this.setVersion(RuntimeSetting.get().getAppVersion());
	}

	@PreDestroy
	public void shutdown() {
		logger.info("shutdown memcached cache client");
		try {
			memcachedClient.shutdown();
		} catch (IOException e) {
			throw new CacheException(e);
		}
	}

	@Override
	protected Collection<? extends Cache> loadCaches() {
		return caches;
	}

	public void setServers(String remoteServers) {
		MemcachedClientBuilder builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(remoteServers));
		builder.setCommandFactory(new BinaryCommandFactory());
		builder.setOpTimeout(MEMCACHED_TIME_OUT.toMilliseconds());
		try {
			memcachedClient = builder.build();
		} catch (IOException e) {
			throw new CacheException(e);
		}
	}

	@Override
	public ServiceStatus getServiceStatus() throws Exception {
		Collection<InetSocketAddress> availableServers = memcachedClient.getAvailableServers();
		return availableServers.isEmpty() ? ServiceStatus.DOWN : ServiceStatus.UP;
	}

	@Override
	public String getServiceName() {
		return "Memcached";
	}

	@Override
	public void add(CacheSpec cacheSpec) {
		String cacheName = cacheSpec.getName();
		TimeLength expirationTime = TimeLength.seconds(cacheSpec.getLiveSeconds());
		boolean updateWithVersion = cacheSpec.isUpdateWithVersion();
		AssertErrorUtils.assertNotNull(memcachedClient, "memcachedClient is required");
		MemcachedCache cache = new MemcachedCache(cacheName, expirationTime, updateWithVersion);
		cache.setMemcachedClient(memcachedClient);
		cache.setVersion(this.version);
		caches.add(cache);
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

}
