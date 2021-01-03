package common.core.app.cache;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.monitor.status.ServiceStatusMonitor;
import common.core.app.monitor.status.ServiceStatus;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Status;
import net.sf.ehcache.config.Configuration;

public class EhcacheCacheManager extends EhCacheCacheManager implements ServiceStatusMonitor {
	private static final Logger logger = LoggerFactory.getLogger(EhcacheCacheManager.class);
	private net.sf.ehcache.CacheManager cacheManager;

	public EhcacheCacheManager() {
		super();
		logger.debug("init cacheManager");
		Configuration initialConfiguration = new Configuration();
		initialConfiguration.setName("cacheManager-" + System.currentTimeMillis());
		cacheManager = new net.sf.ehcache.CacheManager(initialConfiguration);
		setCacheManager(cacheManager);
	}

	public void addCache(Ehcache cache) {
		getCacheManager().addCacheIfAbsent(cache);
		// getCacheManager().addCache(cache);
	}

	@Override
	public ServiceStatus getServiceStatus() throws Exception {
		CacheManager cacheManager = getCacheManager();
		boolean alive = Status.STATUS_ALIVE.equals(cacheManager.getStatus());
		if (alive)
			return ServiceStatus.UP;
		return ServiceStatus.DOWN;
	}

	@Override
	public String getServiceName() {
		return "Ehcache";
	}

	@Override
	protected Collection<org.springframework.cache.Cache> loadCaches() {
		CacheManager cacheManager = getCacheManager();

		String[] names = cacheManager.getCacheNames();
		List<Cache> caches = new ArrayList<>(names.length);
		for (String name : names) {
			caches.add(new EhcacheCache(cacheManager.getEhcache(name)));
		}
		return caches;
	}
}
