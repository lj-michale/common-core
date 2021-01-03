package common.core.app.dao.redis;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.ObjectUtil;
import common.core.common.util.StopWatch;
import redis.clients.jedis.Jedis;

/**
 * 不完整，待完善
 *
 */
public class RedisSession {
	private final Logger logger = LoggerFactory.getLogger(RedisSession.class);

	Jedis jedis = new Jedis("localhost");

	/**
	 * 设置值
	 * 
	 * @param key
	 * @param valueObj
	 * @return
	 */
	public <T> String setValue(String key, T valueObj) {
		StopWatch stopWatch = new StopWatch();
		String value = null;
		try {
			if (null == valueObj) {
				value = null;
			} else if (valueObj instanceof String) {
				value = (String) valueObj;
			} else {
				value = ObjectUtil.toJson(valueObj);
			}
			return jedis.set(key, value);
		} finally {
			logger.debug("redis setValue key={},value={},valueObj={},times=", key, value, valueObj, stopWatch.elapsedTime());
		}
	}

	/**
	 * 取值
	 * 
	 * @param requiredType
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public <T> T getValue(Class<T> requiredType, String key) {
		StopWatch stopWatch = new StopWatch();
		String redisData = null;
		T objResut = null;
		try {
			redisData = jedis.get(key);
			if (null == redisData) {
				redisData = null;
			} else if (requiredType.equals(String.class)) {
				objResut = (T) redisData;
			} else {
				objResut = ObjectUtil.fromJson(requiredType, redisData);
			}
			return objResut;
		} finally {
			logger.debug("redis getValue key={},redisData={},objResut={},times=", key, redisData, objResut, stopWatch.elapsedTime());
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getValues(Class<T> requiredType, String... keys) {
		StopWatch stopWatch = new StopWatch();
		List<String> redisDatas = null;
		List<T> objResut = null;
		try {
			if (keys.length == 0) {
				objResut = new ArrayList<>(0);
				return objResut;
			}
			redisDatas = jedis.mget(keys);
			if (null == redisDatas) {
				redisDatas = new ArrayList<>(0);
			} else if (requiredType.equals(String.class)) {
				objResut = (List<T>) redisDatas;
			} else {
				objResut = new ArrayList<>();
				for (String redisData : redisDatas) {
					objResut.add(ObjectUtil.fromJson(requiredType, redisData));
				}
			}
			return objResut;
		} finally {
			logger.debug("redis getValues keys={},redisDatas={},objResut={},times=", keys, redisDatas, objResut, stopWatch.elapsedTime());
		}
	}

	@SuppressWarnings("unchecked")
	public <T> List<T> getValues(Class<T> requiredType, String pattern) {
		Set<String> keySet = this.getKeys(pattern);
		if (null == keySet)
			return new ArrayList<>();
		List<String> keyList = new ArrayList<>();
		for (Iterator<String> iterator = keySet.iterator(); iterator.hasNext();) {
			String key = (String) iterator.next();
			keyList.add(key);
		}
		return this.getValues(requiredType, keyList.toArray(new String[] {}));
	}

	/**
	 * 取值
	 * 
	 * @param key
	 * @return
	 */
	public String getValue(String key) {
		return this.getValue(String.class, key);
	}

	/**
	 * 模糊匹配取key
	 * 
	 * @param pattern
	 * @return
	 */
	public Set<String> getKeys(String pattern) {
		StopWatch stopWatch = new StopWatch();
		Set<String> result = null;
		try {
			result = jedis.keys(pattern);
			return result;
		} finally {
			logger.debug("redis pattern={},result={},times=", pattern, result, stopWatch.elapsedTime());
		}
	}
}
