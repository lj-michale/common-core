package common.core.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.xml.bind.annotation.XmlElement;

public class ParamMappingUtil {
	private final static ConcurrentMap<Class<?>, Map<String, String>> PARAM_MAPPINGS = new ConcurrentHashMap<Class<?>, Map<String, String>>();
	private final static ConcurrentMap<Class<?>, Map<String, Field>> PARAM_FIELD_MAPS = new ConcurrentHashMap<Class<?>, Map<String, Field>>();

	public static Map<String, String> createParamMapping(Class<?> targetClass) {
		Field[] fields = targetClass.getDeclaredFields();
		Map<String, String> mappings = new HashMap<String, String>(fields.length);
		// TODO: iterate parent class fields?
		for (Field field : fields) {
			XmlElement xmlElement = field.getAnnotation(XmlElement.class);
			if (xmlElement != null && !xmlElement.name().isEmpty()) {
				mappings.put(xmlElement.name(), field.getName());
			}
		}
		return mappings;
	}

	public static Map<String, String> getOrCreateParamMapping(Class<?> targetClass) {
		Map<String, String> existingMappings = PARAM_MAPPINGS.get(targetClass);
		if (existingMappings == null) {
			Map<String, String> mappings = ParamMappingUtil.createParamMapping(targetClass);
			Map<String, String> result = PARAM_MAPPINGS.putIfAbsent(targetClass, mappings);
			if (result == null) {
				result = mappings;
			}
			return result;
		}
		return existingMappings;
	}

	public static Map<String, Field> getParamFieldMap(Class<?> targetClass) {
		Map<String, Field> mappings = PARAM_FIELD_MAPS.get(targetClass);
		if (null != mappings)
			return mappings;

		Field[] fields = targetClass.getDeclaredFields();
		mappings = new HashMap<String, Field>(fields.length);
		// TODO: iterate parent class fields?
		for (Field field : fields) {
			field.setAccessible(true);
			mappings.put(field.getName(), field);
			XmlElement xmlElement = field.getAnnotation(XmlElement.class);
			if (xmlElement != null && !xmlElement.name().isEmpty()) {
				mappings.put(xmlElement.name(), field);
			}
		}
		PARAM_FIELD_MAPS.put(targetClass, mappings);
		return mappings;
	}

}
