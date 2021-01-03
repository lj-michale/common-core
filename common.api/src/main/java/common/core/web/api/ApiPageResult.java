package common.core.web.api;

import java.util.Arrays;
import java.util.List;

public class ApiPageResult<T> implements java.io.Serializable {
	private static final long serialVersionUID = -7450671021280131333L;
	private int pageSize;
	private int pageIndex;
	private int dbRowCount;
	private int pageTotal;
	private int returnDataSize;
	private List<T> data;
	private Object[] sumData;

	public ApiPageResult() {
		super();
	}

	/**
	 * @param pageSize
	 * @param pageIndex
	 * @param dbRowCount
	 */
	public ApiPageResult(int pageSize, int pageIndex, int dbRowCount) {
		super();
		this.pageSize = pageSize;
		this.pageIndex = pageIndex;
		this.dbRowCount = dbRowCount;
		if (this.pageSize > 0) {
			this.pageTotal = this.dbRowCount / this.pageSize;
			if (this.dbRowCount % this.pageSize > 0)
				this.pageTotal++;
		}
	}

	/**
	 * @return current page size
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @return all data count
	 */
	public int getDbRowCount() {
		return dbRowCount;
	}

	/**
	 * @return total page
	 */
	public int getPageTotal() {
		return pageTotal;
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
		this.dbRowCount = this.dbRowCount == 0 ? this.returnDataSize : this.dbRowCount;
		this.pageTotal = this.pageTotal == 0 ? 1 : this.pageTotal;
	}

	/**
	 * @return page index
	 */
	public int getPageIndex() {
		return pageIndex;
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

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public void setDbRowCount(int dbRowCount) {
		this.dbRowCount = dbRowCount;
	}

	public void setPageTotal(int pageTotal) {
		this.pageTotal = pageTotal;
	}

	public void setReturnDataSize(int returnDataSize) {
		this.returnDataSize = returnDataSize;
	}

	public Object[] getSumData() {
		return sumData;
	}

	public void setSumData(Object[] sumData) {
		this.sumData = sumData;
	}

	@Override
	public String toString() {
		return "PageResult [pageSize=" + pageSize + ", pageIndex=" + pageIndex + ", dbRowCount=" + dbRowCount + ", pageTotal=" + pageTotal + ", returnDataSize=" + returnDataSize + ", sumData=" + Arrays.toString(sumData) + "]";
	}

}
