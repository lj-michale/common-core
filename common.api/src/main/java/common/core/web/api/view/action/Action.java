package common.core.web.api.view.action;

public class Action {

	private String type;
	private String value;
	private String parentId;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Action(String type, String value) {
		super();
		this.type = type;
		this.value = value;
	}

	public Action(String type) {
		super();
		this.type = type;
	}

	public String getParentId() {
		return parentId;
	}

	public Action setParentId(String parentId) {
		this.parentId = parentId;
		return this;
	}

	public Action(String type, String value, String parentId) {
		super();
		this.type = type;
		this.value = value;
		this.parentId = parentId;
	}
}
