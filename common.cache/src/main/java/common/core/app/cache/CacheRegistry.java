package common.core.app.cache;

public interface CacheRegistry {
	/**
	 * Only used for in memory cache, ehcache
	 *
	 * @param cacheName
	 *            the name of cache
	 * @param expirationTime
	 *            expiration time
	 * @param maxEntriesInHeap
	 *            max entries in heap
	 */
	void addCache(CacheSpec cacheSpec);
}
