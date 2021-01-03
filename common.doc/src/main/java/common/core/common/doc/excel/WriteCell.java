package common.core.common.doc.excel;

public class WriteCell {
	private int colIndex;
	private int rowIndex;
	private Object value;

	public WriteCell(int rowIndex, int colIndex, Object value) {
		super();
		this.colIndex = colIndex;
		this.rowIndex = rowIndex;
		this.value = value;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

}
