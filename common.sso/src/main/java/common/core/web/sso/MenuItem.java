package common.core.web.sso;

import java.util.ArrayList;
import java.util.List;

public class MenuItem implements java.io.Serializable {
	private static final long serialVersionUID = -5206183802462218884L;

	private String id;
	private String code;
	private String name;
	private String url;
	private String parentId;
	private int order;
	private List<MenuItem> subMenuItems = new ArrayList<MenuItem>();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public List<MenuItem> getSubMenuItems() {
		return subMenuItems;
	}

	public void setSubMenuItems(List<MenuItem> subMenuItems) {
		this.subMenuItems = subMenuItems;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public MenuItem(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

	public MenuItem() {
		super();
	}

}
