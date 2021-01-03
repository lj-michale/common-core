package common.core.app.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeoutException;

import org.springframework.cache.Cache;
import org.springframework.cache.support.SimpleValueWrapper;

import common.core.app.lang.TimeLength;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.runtime.RuntimeSetting;
import common.core.common.util.StopWatch;

import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.transcoders.SerializingTranscoder;

public class MemcachedCache implements Cache {
	private final Logger logger = LoggerFactory.getLogger(MemcachedCache.class);
	private final String cacheName;
	private final TimeLength expirationTime;
	private MemcachedClient memcachedClient;
	private String version;
	private boolean updateWithVersion = true;

	public MemcachedCache(String cacheName, TimeLength expirationTime) {
		this.cacheName = cacheName;
		this.expirationTime = expirationTime;
	}

	public MemcachedCache(String cacheName, TimeLength expirationTime, boolean updateWithVersion) {
		this.cacheName = cacheName;
		this.expirationTime = expirationTime;
		this.setUpdateWithVersion(updateWithVersion);
	}

	@Override
	public String getName() {
		return cacheName;
	}

	@Override
	public Object getNativeCache() {
		return memcachedClient;
	}

	@Override
	public ValueWrapper get(Object key) {
		StopWatch watch = new StopWatch();
		String memcachedKey = null;
		boolean hit = false;
		try {
			memcachedKey = constructKey(key);
			Object value = null;
			try {
				value = memcachedClient.get(memcachedKey);
			} catch (TimeoutException | InterruptedException | MemcachedException e) {
				throw new CacheException(e);
			}
			if (value == null)
				return null;
			hit = true;
			if (NullObject.INSTANCE.equals(value)) {
				return new SimpleValueWrapper(null);
			}
			return new SimpleValueWrapper(value);
		} finally {
			logger.debug("get, memcachedKey={}, hit={}, elapsedTime={}", memcachedKey, hit, watch.elapsedTime());
		}
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	private String constructKey(Object key) {
		StringBuffer keys = new StringBuffer().append(RuntimeSetting.get().getEnv()).append(":").append(RuntimeSetting.get().getArtifactId()).append(":").append(cacheName).append(":").append(key.toString());
		if (this.updateWithVersion) {
			keys.append(":").append(this.version);
		}
		return keys.toString();
	}

	@Override
	public void put(Object key, Object value) {
		StopWatch watch = new StopWatch();
		String memcachedKey = null;
		try {
			memcachedKey = constructKey(key);
			if (value == null) {
				value = NullObject.INSTANCE;
			}
			try {
				memcachedClient.set(memcachedKey, (int) expirationTime.toSeconds(), value);
			} catch (TimeoutException | InterruptedException | MemcachedException e) {
				throw new CacheException(e);
			}
		} finally {
			logger.debug("put, memcachedKey={},expirationTime={}, elapsedTime={}", memcachedKey, expirationTime.toSeconds(), watch.elapsedTime());
		}
	}

	@Override
	public void evict(Object key) {
		StopWatch watch = new StopWatch();
		String memcachedKey = null;
		memcachedKey = constructKey(key);
		try {
			memcachedClient.delete(memcachedKey);
		} catch (TimeoutException | InterruptedException | MemcachedException e) {
			throw new CacheException(e);
		} finally {
			logger.debug("evict, memcachedKey={}, elapsedTime={}", memcachedKey, watch.elapsedTime());
		}
	}

	@Override
	public void clear() {
	}

	public void setMemcachedClient(MemcachedClient memcachedClient) {
		this.memcachedClient = memcachedClient;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T get(Object key, Class<T> type) {
		StopWatch watch = new StopWatch();
		String memcachedKey = null;
		boolean hit = false;
		try {
			memcachedKey = constructKey(key);
			SerializingTranscoder serializingTranscoder = new SerializingTranscoder();
			Object value = null;
			try {
				value = memcachedClient.get(memcachedKey, serializingTranscoder);
			} catch (TimeoutException | InterruptedException | MemcachedException e) {
				throw new CacheException(e);
			}
			if (value == null)
				return null;
			hit = true;
			if (NullObject.INSTANCE.equals(value)) {
				return null;
			}
			return (T) value;
		} finally {
			logger.debug("get by type={}, memcachedKey={}, hit={}, elapsedTime={}", type.getName(), memcachedKey, hit, watch.elapsedTime());
		}
	}

	@Override
	public ValueWrapper putIfAbsent(Object key, Object value) {
		return null;
	}

	public boolean isUpdateWithVersion() {
		return updateWithVersion;
	}

	public void setUpdateWithVersion(boolean updateWithVersion) {
		this.updateWithVersion = updateWithVersion;
	}    

	@Override
	public <T> T get(Object key, Callable<T> valueLoader) {
		// TODO Auto-generated method stub
		return null;
	}

}
