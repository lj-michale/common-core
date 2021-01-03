package common.core.app.dao;

public abstract class PageQuery<T> implements java.io.Serializable {
	private static final long serialVersionUID = 4022901120269859088L;

	/**
	 * query string or query string id
	 */
	private String querySql;

	/**
	 * page size
	 */
	private int pageSize;

	/**
	 * return data type class
	 */
	private Class<T> returnType;

	/**
	 * page index, start with 0
	 */
	private int pageIndex;

	/**
	 * @param returnType
	 * @param pageSize
	 * @param pageIndex
	 * @param querySql
	 */
	public PageQuery(Class<T> returnType, int pageSize, int pageIndex, String querySql) {
		this.pageIndex = pageIndex;
		this.returnType = returnType;
		this.pageSize = pageSize;
		this.querySql = querySql;
	}

	/**
	 * @param returnType
	 * @param pageSize
	 * @param pageIndex
	 */
	public PageQuery(Class<T> returnType, int pageSize, int pageIndex) {
		this.pageIndex = pageIndex;
		this.returnType = returnType;
		this.pageSize = pageSize;
	}

	/**
	 * @return start row number , start with 0 , as pageIndex * pageSize
	 */
	public int getStartRowIndex() {
		return pageIndex * this.getPageSize();
	}

	/**
	 * @return end row number, as (pageIndex + 1) * pageSize
	 */
	public int getEndRowIndex() {
		return (pageIndex + 1) * this.getPageSize();
	}

	public String getQuerySql() {
		return querySql;
	}

	public void setQuerySql(String querySql) {
		this.querySql = querySql;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public Class<T> getReturnType() {
		return returnType;
	}

	public void setReturnType(Class<T> returnType) {
		this.returnType = returnType;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	@Override
	public String toString() {
		return "PageQuery [querySql=" + querySql + ", pageSize=" + pageSize + ", returnType=" + returnType + ", pageIndex=" + pageIndex + "]";
	}

}
