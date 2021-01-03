package common.core.web.api.view.views;

import java.util.List;

public class Select {
	
	private String no;
	
	private String name;
	
	private List<String> contents;
	
	private String action;

	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getContents() {
		return contents;
	}

	public void setContents(List<String> contents) {
		this.contents = contents;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	@Override
	public String toString() {
		return "Select [no=" + no + ", name=" + name + ", contents=" + contents + ", action=" + action + "]";
	}
}
