package common.core.app.dao.mongo;

public class MongoOnePageQuery<T> extends MongoPageQuery<T> {

	private static final long serialVersionUID = 7308522205767606286L;

	public MongoOnePageQuery(Class<T> returnType) {
		super(returnType, Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

}
