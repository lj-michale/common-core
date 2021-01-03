package common.core.app.context;

import common.core.app.exception.InitException;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.ClassUtil;
import common.core.common.util.StringUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.xml.bind.annotation.XmlElement;
import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class ConfigContext {
	private static Properties PROPERTIES = new Properties();

	static {
		try {
			Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath*:*.properties");
			for (Resource resource : resources) {
				PROPERTIES.load(resource.getInputStream());
			}

			MultiplePropertyPlaceholderHelper multiplePropertyPlaceholderHelper = new MultiplePropertyPlaceholderHelper();
			Iterator<String> names = PROPERTIES.stringPropertyNames().iterator();
			String name;
			Object value;
			while (names.hasNext()) {
				name = names.next();
				value = ConfigContext.getConfigs().get(name);
				if (null != value && value instanceof String)
					value = multiplePropertyPlaceholderHelper.decodeValue((String) value);
				PROPERTIES.put(name, value);
			}
		} catch (Exception e) {
			throw new InitException(e);
		}

	}

	public static Properties getConfigs() {
		return PROPERTIES;
	}

	public static Boolean getBooleanValue(String configName) {
		return ConfigContext.getValue(configName, Boolean.class);
	}

	public static String getStringValue(String configName) {
		return ConfigContext.getValue(configName, String.class);
	}

	public static String[] getStringValues(String configName) {
		String value = ConfigContext.getValue(configName, String.class);
		return StringUtil.split(value);
	}

	public static String[] getStringValues(String configName, String[] defaultValue) {
		String value = ConfigContext.getValue(configName, String.class, false);
		return StringUtil.hasText(value) ? value.split(";") : defaultValue;
	}

	public static Integer getIntegerValue(String configName) {
		return ConfigContext.getValue(configName, Integer.class);
	}

	public static Double getDoubleValue(String configName) {
		return ConfigContext.getValue(configName, Double.class);
	}

	public static Long getLongValue(String configName) {
		return ConfigContext.getValue(configName, Long.class);
	}

	public static String getStringValue(String configName, String defaultValue) {
		String value = ConfigContext.getValue(configName, String.class, false);
		return StringUtil.isNotBlank(value) ? value : defaultValue;
	}

	public static Boolean getBooleanValue(String configName, Boolean defaultValue) {
		Boolean value = ConfigContext.getValue(configName, Boolean.class, false);
		return value != null ? value : defaultValue;
	}

	public static Integer getIntegerValue(String configName, Integer defaultValue) {
		Integer value = ConfigContext.getValue(configName, Integer.class, false);
		return value != null ? value : defaultValue;
	}

	public static Double getDoubleValue(String configName, Double defaultValue) {
		Double value = ConfigContext.getValue(configName, Double.class, false);
		return value != null ? value : defaultValue;
	}

	public static Long getLongValue(String configName, Long defaultValue) {
		Long value = ConfigContext.getValue(configName, Long.class, false);
		return value != null ? value : defaultValue;
	}

	public static <T> T getValueWithDefalut(String configName, Class<T> targetClass) {
		return getValue(configName, targetClass, true);
	}

	public static <T> T getValue(String configName, Class<T> targetClass) {
		return getValue(configName, targetClass, true);
	}

	public static <T> T getValue(String configName, Class<T> targetClass, boolean required) {
        String value = PROPERTIES.getProperty(configName);

        if (ApplicationContextUtil.getConfigurableEnvironment() != null
				&& ApplicationContextUtil.getConfigurableEnvironment().containsProperty(configName)) {
        	value = ApplicationContextUtil.getConfigurableEnvironment().getProperty(configName);
			if (null != value && value instanceof String) {
				MultiplePropertyPlaceholderHelper multiplePropertyPlaceholderHelper = new MultiplePropertyPlaceholderHelper();
				value = multiplePropertyPlaceholderHelper.decodeValue((String) value);
			}
		}

		if (required == true)
			AssertErrorUtils.assertTrue(StringUtil.isNotBlank(value), "Can't found config with [{}]", configName);
		return StringUtil.convert(value, targetClass);
	}

	public static boolean containsKey(String configName) {

		if ((ApplicationContextUtil.getConfigurableEnvironment() != null
				&& ApplicationContextUtil.getConfigurableEnvironment().containsProperty(configName))
			|| PROPERTIES.containsKey(configName)) {
			return true;
		} else {
			return  false;
		}

	}

	private static String buidKey(String key, String prefix) {
		if (StringUtil.hasText(prefix)) {
			return prefix + key;
		}
		return key;
	}

	public static Object bindConfigObject(Object obj) {
		return ConfigContext.bindConfigObject(obj, null);
	}

	public static Object bindConfigObject(Object obj, String prefix) {
		List<Field> fields = ClassUtil.findAnnotationFields(obj.getClass(), XmlElement.class);

		for (Field field : fields) {
			field.setAccessible(true);
			String key = null;
			boolean required = true;
			XmlElement xmlElement = field.getAnnotation(XmlElement.class);
			if (null != xmlElement) {
				key = xmlElement.name();
				required = xmlElement.required();
			}
			key = ConfigContext.buidKey(key, prefix);
			Object value = null;
			if (String[].class.equals(field.getType())) {
				value = ConfigContext.getValue(key, String.class, required);
				if (!StringUtil.isEmpty(value)) {
					value = ((String) value).split(";");
				}
			} else {
				value = ConfigContext.getValue(key, field.getType(), required);
			}

			try {
				if (!StringUtil.isEmpty(value))
					field.set(obj, value);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new IllegalArgumentException(e);
			}
		}
		return obj;
	}
}
