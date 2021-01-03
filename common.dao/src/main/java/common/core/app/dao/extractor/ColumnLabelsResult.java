package common.core.app.dao.extractor;

import java.util.List;

public class ColumnLabelsResult<T> {

	private List<String> columnLabels;
	private List<T> dataList;

	public List<String> getColumnLabels() {
		return columnLabels;
	}

	public void setColumnLabels(List<String> columnLabels) {
		this.columnLabels = columnLabels;
	}

	public List<T> getDataList() {
		return dataList;
	}

	public void setDataList(List<T> dataList) {
		this.dataList = dataList;
	}

}
