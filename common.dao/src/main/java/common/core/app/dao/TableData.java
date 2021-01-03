package common.core.app.dao;

import java.util.List;

public class TableData {

	private String[] columnNames;
	private List<Object[]> objectsList;

	public String[] getColumnNames() {
		return columnNames;
	}

	public void setColumnNames(String[] columnNames) {
		this.columnNames = columnNames;
	}

	public List<Object[]> getObjectsList() {
		return objectsList;
	}

	public void setObjectsList(List<Object[]> objectsList) {
		this.objectsList = objectsList;
	}

}
