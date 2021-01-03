package common.core.web.api.view;

import java.lang.reflect.Field;
import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.DateUtils;
import common.core.web.api.view.views.ViewField;

public class ObjectViewFieldBuildFactory {

	private Object obj;
	private String[] titles;
	private String[] fields;
	private Format[] formats;
	private Map<String, Map<Object, Object>> fieldValueConvertMaps;

	public Object getObj() {
		return obj;
	}

	public ObjectViewFieldBuildFactory setObj(Object obj) {
		this.obj = obj;
		return this;
	}

	public String[] getTitles() {
		return titles;
	}

	public ObjectViewFieldBuildFactory setTitles(String[] titles) {
		this.titles = titles;
		return this;
	}

	public String[] getFields() {
		return fields;
	}

	public ObjectViewFieldBuildFactory setFields(String[] fields) {
		this.fields = fields;
		return this;
	}

	public Format[] getFormats() {
		return formats;
	}

	public ObjectViewFieldBuildFactory setFormats(Format[] formats) {
		this.formats = formats;
		return this;
	}

	public Map<String, Map<Object, Object>> getFieldValueConvertMaps() {
		return fieldValueConvertMaps;
	}

	public ObjectViewFieldBuildFactory addFieldValueConvertMap(String fieldName, Map<Object, Object> fieldValueConvertMap) {
		if (null == fieldValueConvertMaps)
			fieldValueConvertMaps = new HashMap<>();
		fieldValueConvertMaps.put(fieldName, fieldValueConvertMap);
		return this;
	}

	public ObjectViewFieldBuildFactory setFieldValueConvertMaps(Map<String, Map<Object, Object>> fieldValueConvertMaps) {
		this.fieldValueConvertMaps = fieldValueConvertMaps;
		return this;
	}

	public ObjectViewFieldBuildFactory() {
		super();
	}

	public ObjectViewFieldBuildFactory(Object obj, String[] titles, String[] fields) {
		super();
		this.obj = obj;
		this.titles = titles;
		this.fields = fields;
	}

	public List<ViewField> buildViewFields() {
		List<ViewField> viewFields = new ArrayList<>();
		AssertErrorUtils.assertTrue(titles.length == fields.length, "titles.length!=fields.length");
		for (int i = 0; i < fields.length; i++) {
			String fieldName = fields[i];
			Field field = null;
			try {
				field = obj.getClass().getDeclaredField(fieldName);
				field.setAccessible(true);
				Object valueObject = field.get(obj);
				String value = convetToString(valueObject, i);
				viewFields.add(new ViewField(titles[i], value));
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
		}
		return viewFields;
	}

	private String convetToString(Object valueObject, int fieldIndex) {
		if (null == valueObject)
			return null;

		// Convert
		if (null != fieldValueConvertMaps && fieldValueConvertMaps.containsKey(fields[fieldIndex])) {
			Map<Object, Object> fieldValueConvert = fieldValueConvertMaps.get(fields[fieldIndex]);
			valueObject = fieldValueConvert.containsKey(valueObject.toString()) ? fieldValueConvert.get(valueObject.toString()) : valueObject;
		}

		// formate
		if (null != formats && formats.length > fieldIndex && null != formats[fieldIndex]) {
			return formats[fieldIndex].format(valueObject);
		} else
			return ObjectViewFieldBuildFactory.convertObjectToString(valueObject);
	}

	public static String convertObjectToString(Object valueObject) {
		if (null == valueObject)
			return null;
		if (valueObject instanceof java.sql.Timestamp) {
			return DateUtils.formateDateString((java.util.Date) valueObject, "yyyy-MM-dd HH:mm:ss");
		} else if (valueObject instanceof java.sql.Date) {
			return DateUtils.formateDateString((java.util.Date) valueObject, "yyyy-MM-dd");
		} else if (valueObject instanceof java.sql.Time) {
			return DateUtils.formateDateString((java.util.Date) valueObject, "HH:mm:ss");
		} else if (valueObject instanceof java.util.Date) {
			return DateUtils.formateDateString((java.util.Date) valueObject, "yyyy-MM-dd HH:mm:ss");
		}
		return valueObject.toString();
	}
}
