package common.core.app.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SqlPageQuery<T> extends PageQuery<T> {

	private static final long serialVersionUID = 387593346974719993L;

	/**
	 * params
	 */
	public List<Object> params = new ArrayList<Object>();

	/**
	 * 合计
	 */
	private int[] sumColumnIndexs;

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

	public SqlPageQuery(Class<T> returnType, int pageSize, int pageIndex, String querySql) {
		super(returnType, pageSize, pageIndex, querySql);
	}

	public SqlPageQuery(Class<T> returnType, int pageSize, int pageIndex) {
		super(returnType, pageSize, pageIndex);
	}

	public int[] getSumColumnIndexs() {
		return sumColumnIndexs;
	}

	public void setSumColumnIndexs(int[] sumColumnIndexs) {
		this.sumColumnIndexs = sumColumnIndexs;
	}

}
