package common.core.app.dao;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class EntityColumns {

	private Map<String, Field> notIdColumnMap = new HashMap<>();
	private Map<String, Field> idColumnMap = new HashMap<>();

	public Map<String, Field> getNotIdColumnMap() {
		return notIdColumnMap;
	}

	public void setNotIdColumnMap(Map<String, Field> notIdColumnMap) {
		this.notIdColumnMap = notIdColumnMap;
	}

	public Map<String, Field> getIdColumnMap() {
		return idColumnMap;
	}

	public void setIdColumnMap(Map<String, Field> idColumnMap) {
		this.idColumnMap = idColumnMap;
	}

}
