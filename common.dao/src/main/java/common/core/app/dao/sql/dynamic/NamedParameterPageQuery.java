package common.core.app.dao.sql.dynamic;

import java.util.HashMap;
import java.util.Map;

import common.core.app.dao.PageQuery;

public class NamedParameterPageQuery<T> extends PageQuery<T> {

	private static final long serialVersionUID = 387593346974719994L;

	/**
	 * params
	 */
	public Map<String, Object> params = new HashMap<>();

	/**
	 * 合计
	 */
	private int[] sumColumnIndexs;

	/**
	 * @param param
	 *            add more param
	 */
	public void addParam(String name, Object param) {
		this.params.put(name, param);
	}

	public void addLikeParam(String name, String param) {
		this.params.put(name, "%" + param + "%");
	}

	/**
	 * @param params
	 */
	public void addParams(Map<String, Object> params) {
		this.params.putAll(params);
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public NamedParameterPageQuery(Class<T> returnType, int pageSize, int pageIndex, String querySql) {
		super(returnType, pageSize, pageIndex, querySql);
	}

	public NamedParameterPageQuery(Class<T> returnType, int pageSize, int pageIndex) {
		super(returnType, pageSize, pageIndex);
	}

	public int[] getSumColumnIndexs() {
		return sumColumnIndexs;
	}

	public void setSumColumnIndexs(int[] sumColumnIndexs) {
		this.sumColumnIndexs = sumColumnIndexs;
	}

}
