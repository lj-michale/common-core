package common.core.app.cache;

import java.util.Date;

import org.springframework.cache.ehcache.EhCacheCache;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.StopWatch;

import net.sf.ehcache.Ehcache;

public class EhcacheCache extends EhCacheCache implements CacheGroup {
	private final Logger logger = LoggerFactory.getLogger(EhcacheCache.class);
	private int revision;
	private Date lastRefreshed = new Date();

	public EhcacheCache(Ehcache ehcache) {
		super(ehcache);
	}

	@Override
	public ValueWrapper get(Object key) {
		StopWatch watch = new StopWatch();
		boolean hit = false;
		try {
			ValueWrapper value = super.get(key);
			if (value == null)
				return null;
			hit = true;
			return value;
		} finally {
			logger.debug("get, key={}, hit={}, elapsedTime={}", key, hit, watch.elapsedTime());
		}
	}

	@Override
	public void put(Object key, Object value) {
		StopWatch watch = new StopWatch();
		try {
			super.put(key, value);
		} finally {
			logger.debug("put, key={}, elapsedTime={}", key, watch.elapsedTime());
		}
	}

	@Override
	public void evict(Object key) {
		StopWatch watch = new StopWatch();
		try {
			super.evict(key);
		} finally {
			logger.debug("evict, key={}, elapsedTime={}", key, watch.elapsedTime());
		}
	}

	@Override
	public void clear() {
		super.clear();
		revision++;
		lastRefreshed = new Date();
	}

	@Override
	public Date getLastRefreshed() {
		return lastRefreshed;
	}

	@Override
	public int getRevision() {
		return revision;
	}
}
