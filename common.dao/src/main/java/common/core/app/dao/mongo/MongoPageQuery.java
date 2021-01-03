package common.core.app.dao.mongo;

import java.util.HashMap;
import java.util.Map;

import common.core.app.dao.PageQuery;

public class MongoPageQuery<T> extends PageQuery<T> {

	private static final long serialVersionUID = 5857391873103986286L;

	private Map<String, Object> params = new HashMap<>();
	private Map<String, Object> sorts = new HashMap<>();
	private String collectionName;

	public MongoPageQuery(Class<T> returnType, int pageSize, int pageIndex) {
		super(returnType, pageSize, pageIndex);
	}

	public MongoPageQuery<T> addParam(String name, Object value) {
		params.put(name, value);
		return this;
	}

	public MongoPageQuery<T> addParams(Map<String, Object> params) {
		this.params.putAll(params);
		return this;
	}

	public Map<String, Object> getParams() {
		return params;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public void addSort(String name, Integer sortType) {
		this.sorts.put(name, sortType);
	}

	public void addSorts(Map<String, Integer> sorts) {
		this.sorts.putAll(sorts);
	}

	public Map<String, Object> getSorts() {
		return sorts;
	}

	@Override
	public String toString() {
		return "MongoPageQuery [params=" + params + ", sorts=" + sorts + ", collectionName=" + collectionName + ", toString()=" + super.toString() + "]";
	}

}
