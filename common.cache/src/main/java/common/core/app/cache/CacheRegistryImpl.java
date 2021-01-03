package common.core.app.cache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.config.CacheConfiguration;

import org.springframework.cache.CacheManager;

public class CacheRegistryImpl implements CacheRegistry {
	private final CacheManager cacheManager;

	public CacheRegistryImpl(CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}

	@Override
	public void addCache(CacheSpec cacheSpec) {
		if (cacheManager instanceof CustomCacheManager) {
			((CustomCacheManager) cacheManager).add(cacheSpec);
		} else if (cacheManager instanceof EhcacheCacheManager) {
			// for dev env, use simple hardcoded config
			CacheConfiguration cacheConfiguration = new CacheConfiguration(cacheSpec.getName(), cacheSpec.getMaxEntriesLocalHeap());
			cacheConfiguration.setTimeToLiveSeconds(cacheSpec.getLiveSeconds());
			((EhcacheCacheManager) cacheManager).addCache(new Cache(cacheConfiguration));
		}

	}
}
