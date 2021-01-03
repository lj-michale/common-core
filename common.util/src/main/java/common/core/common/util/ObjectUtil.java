package common.core.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.xml.bind.annotation.XmlElement;

import org.springframework.beans.BeanUtils;

import common.core.common.json.JsonBinder;
import common.core.common.xml.XMLBinder;
import common.core.common.xml.XMLException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationIntrospector;

public class ObjectUtil {
	public static <T> T fromJson(Class<T> targetClass, String value) {
		if (null == value)
			return null;
		return JsonBinder.binder(targetClass).fromJson(value);
	}

	@SuppressWarnings("unchecked")
	public static <T> String toJson(T value) {
		if (null == value)
			return null;
		return JsonBinder.binder((Class<T>) value.getClass()).toJson(value);
	}

	public static <T> T fromXml(Class<T> targetClass, String value) {
		if (null == value)
			return null;
		return XMLBinder.binder(targetClass).fromXML(value);
	}

	public static <T> T fromXml(Class<T> targetClass, String value, Charset charSet) {
		if (null == value)
			return null;
		return XMLBinder.binder(targetClass).fromXML(value, charSet);
	}

	public static <T> String toXml(T value) {
		JacksonXmlModule module = new JacksonXmlModule();
		module.setDefaultUseWrapper(false);
		TypeFactory typeFactory = TypeFactory.defaultInstance();
		JaxbAnnotationIntrospector jaxbAnnotationIntrospector = new JaxbAnnotationIntrospector(typeFactory);
		XmlMapper xmlMapper = new XmlMapper(module);
		xmlMapper.setAnnotationIntrospector(jaxbAnnotationIntrospector);
		try {
			return xmlMapper.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new XMLException(e);
		}
	}

	public static <T> T fromStringMap(Class<T> targetClass, Map<String, String> value) {
		Map<String, Object> valueMap = new HashMap<>();
		for (Map.Entry<String, String> entrySet : value.entrySet()) {
			valueMap.put(entrySet.getKey(), entrySet.getValue());
		}
		return ObjectUtil.fromMap(targetClass, valueMap);
	}

	public static <T> T fromMap(Class<T> targetClass, Map<String, Object> value) {
		T object = null;
		try {
			object = targetClass.newInstance();
		} catch (Exception e) {
			return object;
		}
		Map<String, String> xmlNameWithFieldNameMap = ObjectUtil.buildXmlNameWithFieldNameMap(targetClass);
		for (Map.Entry<String, Object> entry : value.entrySet()) {
			String xmlName = entry.getKey();
			String fieldName = xmlNameWithFieldNameMap.get(xmlName);
			if (null == fieldName)
				continue;
			try {
				ClassUtil.setProperty(object, fieldName, entry.getValue());
			} catch (Exception e) {

			}
		}
		return object;
	}

	@SuppressWarnings("unchecked")
	public static <T> Map<String, Object> toMap(T value) {
		if (null == value)
			return null;
		if (value instanceof Map)
			return (Map<String, Object>) value;
		Map<String, Object> valueMap = new HashMap<String, Object>();
		Map<String, String> xmlNameWithFieldNameMap = ObjectUtil.buildXmlNameWithFieldNameMap(value.getClass());
		for (Map.Entry<String, String> entry : xmlNameWithFieldNameMap.entrySet()) {
			String fieldName = entry.getValue();
			String fieldXmlName = entry.getKey();
			Object fieldValue = null;
			fieldValue = ClassUtil.getPropertySilently(value, fieldName);
			if (null == fieldValue)
				continue;
			valueMap.put(fieldXmlName, fieldValue);
		}
		return valueMap;
	}

	public static <T> Map<String, String> toStringMap(T value) {
		Map<String, Object> valueMap = ObjectUtil.toMap(value);
		Map<String, String> stringMap = new HashMap<>();
		for (Map.Entry<String, Object> item : valueMap.entrySet()) {
			if (null == item.getValue())
				continue;
			stringMap.put(item.getKey(), item.getValue().toString());
		}
		return stringMap;
	}

	public static Map<String, String> buildXmlNameWithFieldNameMap(Class<?> c) {
		Field[] fields = c.getDeclaredFields();
		Map<String, String> xmlNameWithFieldNameMap = new HashMap<String, String>();
		Class<?> superclass = c.getSuperclass();
		if (null != superclass && !Object.class.equals(superclass)) {
			xmlNameWithFieldNameMap.putAll(ObjectUtil.buildXmlNameWithFieldNameMap(superclass));
		}
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || Modifier.isNative(field.getModifiers()))
				continue;
			String fieldName = field.getName();
			String fieldXmlName = fieldName;
			XmlElement xmlElement = field.getAnnotation(XmlElement.class);
			if (null != xmlElement) {
				fieldXmlName = xmlElement.name();
			} else {
				Column column = field.getAnnotation(Column.class);
				if (null != column && !StringUtil.isEmpty(column.name()))
					fieldXmlName = column.name();
			}
			xmlNameWithFieldNameMap.put(fieldXmlName, fieldName);
		}
		return xmlNameWithFieldNameMap;
	}

	public static Map<String, Field> buildXmlNameWithFieldMap(Class<?> c) {
		Field[] fields = c.getDeclaredFields();
		Map<String, Field> xmlNameWithFieldMap = new LinkedHashMap<String, Field>();
		Class<?> superclass = c.getSuperclass();
		if (null != superclass && !Object.class.equals(superclass)) {
			xmlNameWithFieldMap.putAll(ObjectUtil.buildXmlNameWithFieldMap(superclass));
		}
		for (Field field : fields) {
			if (Modifier.isStatic(field.getModifiers()) || Modifier.isFinal(field.getModifiers()) || Modifier.isTransient(field.getModifiers()) || Modifier.isNative(field.getModifiers()))
				continue;
			String fieldName = field.getName();
			String fieldXmlName = fieldName;
			XmlElement xmlElement = field.getAnnotation(XmlElement.class);
			if (null != xmlElement) {
				fieldXmlName = xmlElement.name();
			} else {
				Column column = field.getAnnotation(Column.class);
				if (null != column && !StringUtil.isEmpty(column.name()))
					fieldXmlName = column.name();
			}
			xmlNameWithFieldMap.put(fieldXmlName, field);
		}
		return xmlNameWithFieldMap;
	}

	public static String toString(Object obj) {
		if (null == obj)
			return null;
		if (obj instanceof Date) {
			return DateUtils.formateStandDateString((Date) obj);
		}

		return obj.toString();
	}

	public static void copySimpleProperties(Object source, Object target) {
		BeanUtils.copyProperties(source, target);
	}

}
