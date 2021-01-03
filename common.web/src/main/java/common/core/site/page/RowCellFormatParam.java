package common.core.site.page;

public class RowCellFormatParam {
	private PaginationInfo paginationInfo;
	private int rowDataIndex;
	private int rowIndex;
	private int colIndex;

	public PaginationInfo getPaginationInfo() {
		return paginationInfo;
	}

	public void setPaginationInfo(PaginationInfo paginationInfo) {
		this.paginationInfo = paginationInfo;
	}

	public int getRowDataIndex() {
		return rowDataIndex;
	}

	public void setRowDataIndex(int rowDataIndex) {
		this.rowDataIndex = rowDataIndex;
	}

	public int getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(int rowIndex) {
		this.rowIndex = rowIndex;
	}

	public int getColIndex() {
		return colIndex;
	}

	public void setColIndex(int colIndex) {
		this.colIndex = colIndex;
	}

	public RowCellFormatParam(PaginationInfo paginationInfo, int rowDataIndex, int rowIndex, int colIndex) {
		super();
		this.paginationInfo = paginationInfo;
		this.rowDataIndex = rowDataIndex;
		this.rowIndex = rowIndex;
		this.colIndex = colIndex;
	}

}
