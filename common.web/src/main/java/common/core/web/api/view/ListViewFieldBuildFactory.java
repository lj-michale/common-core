package common.core.web.api.view;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import common.core.common.util.DateUtils;

public class ListViewFieldBuildFactory {

	private List<Object[]> objList;
	private int[] columnIndexs;
	private Format[] formats;
	private Map<Integer, Map<Object, Object>> fieldValueConvertMaps;

	public List<Object[]> getObjList() {
		return objList;
	}

	public ListViewFieldBuildFactory setObjList(List<Object[]> objList) {
		this.objList = objList;
		return this;
	}

	public int[] getColumnIndexs() {
		return columnIndexs;
	}

	public ListViewFieldBuildFactory setColumnIndexs(int[] columnIndexs) {
		this.columnIndexs = columnIndexs;
		return this;
	}

	public Format[] getFormats() {
		return formats;
	}

	public ListViewFieldBuildFactory setFormats(Format[] formats) {
		this.formats = formats;
		return this;
	}

	public Map<Integer, Map<Object, Object>> getFieldValueConvertMaps() {
		return fieldValueConvertMaps;
	}

	public ListViewFieldBuildFactory setFieldValueConvertMaps(Map<Integer, Map<Object, Object>> fieldValueConvertMaps) {
		this.fieldValueConvertMaps = fieldValueConvertMaps;
		return this;
	}

	public ListViewFieldBuildFactory addFieldValueConvertMap(int columnIndex, Map<Object, Object> fieldValueConvertMap) {
		if (null == fieldValueConvertMaps)
			fieldValueConvertMaps = new HashMap<>();
		fieldValueConvertMaps.put(columnIndex, fieldValueConvertMap);
		return this;
	}

	public ListViewFieldBuildFactory() {
		super();
	}

	public ListViewFieldBuildFactory(List<Object[]> objList, int[] columnIndexs) {
		super();
		this.objList = objList;
		this.columnIndexs = columnIndexs;
	}

	public List<String[]> buildListData() {
		List<String[]> listData = new ArrayList<>();
		if (null == objList)
			return listData;

		Object obj = null;
		for (int i = 0; i < objList.size(); i++) {
			String[] datas = new String[columnIndexs.length];
			Object[] objs = objList.get(i);
			for (int j = 0; j < columnIndexs.length; j++) {
				obj = objs[columnIndexs[j]];
				datas[j] = convetToString(obj, j);
			}
			listData.add(datas);
		}
		return listData;
	}

	private String convetToString(Object valueObject, int fieldIndex) {
		if (null == valueObject)
			return null;

		// Convert
		if (null != fieldValueConvertMaps && fieldValueConvertMaps.containsKey(fieldIndex)) {
			Map<Object, Object> fieldValueConvert = fieldValueConvertMaps.get(fieldIndex);
			valueObject = fieldValueConvert.containsKey(valueObject.toString()) ? fieldValueConvert.get(valueObject.toString()) : valueObject;
		}

		// formate
		if (null != formats && formats.length > fieldIndex && null != formats[fieldIndex]) {
			return formats[fieldIndex].format(valueObject);
		} else if (valueObject instanceof java.sql.Timestamp) {
			return DateUtils.formateDateString((java.util.Date) valueObject, "yyyy-MM-dd HH:mm:ss");
		} else if (valueObject instanceof java.sql.Date) {
			return DateUtils.formateDateString((java.util.Date) valueObject, "yyyy-MM-dd");
		} else if (valueObject instanceof java.sql.Time) {
			return DateUtils.formateDateString((java.util.Date) valueObject, "HH:mm:ss");
		} else if (valueObject instanceof java.util.Date) {
			return DateUtils.formateDateString((java.util.Date) valueObject, "yyyy-MM-dd HH:mm:ss");
		}
		return null == valueObject ? null : valueObject.toString();
	}
}
