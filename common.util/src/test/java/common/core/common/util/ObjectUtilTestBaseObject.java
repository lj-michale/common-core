package common.core.common.util;

public class ObjectUtilTestBaseObject {

	@ClassUtilTestObjectAnnotation
	private int id;

	public int getId() {
		return id;
	}

	@ClassUtilTestObjectAnnotation
	public void setId(int id) {
		this.id = id;
	}

}
