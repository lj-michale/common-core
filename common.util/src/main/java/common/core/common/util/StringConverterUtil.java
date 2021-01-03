package common.core.common.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.core.convert.converter.Converter;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.converter.BigDecimalConverter;
import common.core.common.converter.BooleanConverter;
import common.core.common.converter.DoubleConverter;
import common.core.common.converter.FloatConverter;
import common.core.common.converter.IntegerConverter;
import common.core.common.converter.LongConverter;
import common.core.common.converter.SqlDateConverter;
import common.core.common.converter.SqlTimestampConverter;
import common.core.common.converter.StringArrayConverter;
import common.core.common.converter.StringConverter;
import common.core.common.converter.UtilDateConverter;

public class StringConverterUtil {

	@SuppressWarnings("rawtypes")
	private static Map<Class<?>, Converter> CONVERTER_MAP = new HashMap<>();

	static {
		CONVERTER_MAP.put(Integer.class, new IntegerConverter());
		CONVERTER_MAP.put(Integer.TYPE, new IntegerConverter());
		CONVERTER_MAP.put(Long.class, new LongConverter());
		CONVERTER_MAP.put(Long.TYPE, new LongConverter());
		CONVERTER_MAP.put(Double.class, new DoubleConverter());
		CONVERTER_MAP.put(Double.TYPE, new DoubleConverter());
		CONVERTER_MAP.put(Float.class, new FloatConverter());
		CONVERTER_MAP.put(Float.TYPE, new FloatConverter());
		CONVERTER_MAP.put(Boolean.class, new BooleanConverter());
		CONVERTER_MAP.put(Boolean.TYPE, new BooleanConverter());
		CONVERTER_MAP.put(BigDecimal.class, new BigDecimalConverter());
		CONVERTER_MAP.put(String.class, new StringConverter());
		CONVERTER_MAP.put(Date.class, new UtilDateConverter());
		CONVERTER_MAP.put(java.sql.Date.class, new SqlDateConverter());
		CONVERTER_MAP.put(java.sql.Timestamp.class, new SqlTimestampConverter());
		CONVERTER_MAP.put(java.sql.Time.class, new UtilDateConverter());
		CONVERTER_MAP.put(String[].class, new StringArrayConverter());
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert(String source, Class<T> type) {
		Converter<String, ?> converter = CONVERTER_MAP.get(type);
		AssertErrorUtils.assertNotNull(converter, "can't convert {} to {}", source, type);
		return (T) converter.convert(source);
	}

	public static boolean isSupportConvert(Class<?> type) {
		return null != CONVERTER_MAP.get(type);
	}

}
