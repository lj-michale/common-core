package common.core.app.dao.page;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import common.core.app.dao.PageQuery;

public class NextPageQuery<T> extends PageQuery<T> {

	private static final long serialVersionUID = -3131125307989882748L;

	/**
	 * params
	 */
	protected List<Object> params = new ArrayList<Object>();

	private String indexColumnName;
	private Object indexColumnValue;

	public void setIndexColumn(String indexColumnName, Object indexColumnValue) {
		this.indexColumnName = indexColumnName;
		this.indexColumnValue = indexColumnValue;
	}

	/**
	 * @param param
	 *            add more param
	 */
	public void addParam(Object param) {
		this.params.add(param);
	}

	public void addLikeParam(String param) {
		this.params.add("%" + param + "%");
	}

	/**
	 * @param params
	 */
	public void addParams(List<Object> params) {
		this.params.addAll(params);
	}

	/**
	 * @param params
	 */
	public void addParams(Object[] params) {
		this.params.addAll(Arrays.asList(params));
	}

	/**
	 * @return get params
	 */
	public Object[] getParams() {
		return params.toArray();
	}

	public String getIndexColumnName() {
		return indexColumnName;
	}

	public Object getIndexColumnValue() {
		return indexColumnValue;
	}

	public NextPageQuery(Class<T> returnType, int pageSize, String querySql) {
		super(returnType, pageSize, 0, querySql);
	}

	public NextPageQuery(Class<T> returnType, int pageSize) {
		super(returnType, pageSize, 0);
	}

}
