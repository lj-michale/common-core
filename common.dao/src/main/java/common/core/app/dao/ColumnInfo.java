package common.core.app.dao;

import java.lang.reflect.Field;

public class ColumnInfo {

	private String columnName;
	private Field columnField;
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public Field getColumnField() {
		return columnField;
	}
	public void setColumnField(Field columnField) {
		this.columnField = columnField;
	}
	public ColumnInfo(String columnName, Field columnField) {
		super();
		this.columnName = columnName;
		this.columnField = columnField;
	}

	

}
