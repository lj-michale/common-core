package common.core.app.dao.page;

import java.util.List;

public class NextPageResult<T> implements java.io.Serializable {
	private static final long serialVersionUID = -7450671021280131333L;
	private int pageSize;
	private int returnDataSize;
	private List<T> data;
	private String indexColumnName;
	private Object indexColumnValue;

	public NextPageResult() {
		super();
	}

	/**
	 * @param pageSize
	 * @param pageIndex
	 * @param dbRowCount
	 */
	public NextPageResult(int pageSize) {
		super();
		this.pageSize = pageSize;
	}

	/**
	 * @return current page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
		if (null != this.data) {
			this.returnDataSize = this.data.size();
		}
		this.pageSize = this.pageSize == 0 ? this.returnDataSize : this.pageSize;
	}

	/**
	 * @return current data row
	 */
	public int getReturnDataSize() {
		return returnDataSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setReturnDataSize(int returnDataSize) {
		this.returnDataSize = returnDataSize;
	}

	public String getIndexColumnName() {
		return indexColumnName;
	}

	public void setIndexColumnName(String indexColumnName) {
		this.indexColumnName = indexColumnName;
	}

	public Object getIndexColumnValue() {
		return indexColumnValue;
	}

	public void setIndexColumnValue(Object indexColumnValue) {
		this.indexColumnValue = indexColumnValue;
	}

	@Override
	public String toString() {
		return "NextPageResult [pageSize=" + pageSize + ", returnDataSize=" + returnDataSize + ", data=" + data + ", indexColumnName=" + indexColumnName + ", indexColumnValue=" + indexColumnValue + "]";
	}

}
