package common.core.app.cache;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Date;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.interceptor.KeyGenerator;

import common.core.app.cache.annotation.NotCacheKey;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.Convert;
import common.core.common.util.DateUtils;
import common.core.common.util.DigestUtils;

public class DefaultCacheKeyGenerator implements KeyGenerator {
	private final Logger logger = LoggerFactory.getLogger(DefaultCacheKeyGenerator.class);
	static final String NO_PARAM_KEY = "default";

	@Override
	public Object generate(Object target, Method method, Object... params) {
		String key;

		if (params.length == 0) {
			key = NO_PARAM_KEY;
		} else {
			key = buildStringCacheKey(params, this.getKeyParams(method));
		}
		String encodedKey = encodeKey(key);
		String cacheName = null;
		Cacheable cacheable = method.getAnnotation(org.springframework.cache.annotation.Cacheable.class);
		if (null != cacheable && cacheable.value().length > 0) {
			cacheName = cacheable.value()[0];
		}
		logger.debug("cache key, method={},name={}, key={}, encodedKey={}", method, cacheName, key, encodedKey);
		return encodedKey;
	}

	public String encodeKey(String key) {
		return DigestUtils.md5(new StringBuffer(key).append(DateUtils.currentIsoDateString()).toString());
	}

	private String buildStringCacheKey(Object[] params, int[] keyParams) {
		if (params.length == 1)
			return getKeyValue(params[0]);

		StringBuilder builder = new StringBuilder();
		int index = 0;
		int keyIndex = 0;// 做为key的序列号，用于判断是否append :
		for (Object param : params) {
			// 为0说明此参数不需加入key
			if (keyParams[index] == 0) {
				index++;
				continue;
			}
			if (keyIndex > 0)
				builder.append(':');
			String value = getKeyValue(param);
			builder.append(value);
			keyIndex++;
			index++;
		}
		return builder.toString();
	}

	@SuppressWarnings("rawtypes")
	private String getKeyValue(Object param) {
		if (null == param)
			return "null";
		if (param instanceof CacheKeyGenerator)
			return ((CacheKeyGenerator) param).buildCacheKey();
		if (param instanceof Enum)
			return ((Enum) param).name();
		if (param instanceof Date)
			return Convert.toString((Date) param, Convert.DATE_FORMAT_DATETIME);
		return String.valueOf(param);
	}

	@SuppressWarnings("unused")
	private boolean containsIllegalKeyChar(String value) {
		return value.contains(" ");
	}

	private int[] getKeyParams(Method method) {
		Annotation[][] types = method.getParameterAnnotations();
		int[] keyParams = new int[method.getParameterCount()];
		int count = 0;
		for (Annotation[] annotatedType : types) {
			keyParams[count] = 1;
			for (Annotation annotation : annotatedType) {
				if (NotCacheKey.class.isInstance(annotation)) {
					keyParams[count] = 0;
				}
				;
			}
			count++;
		}
		return keyParams;
	}
}
