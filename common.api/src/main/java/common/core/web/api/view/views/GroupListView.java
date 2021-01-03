package common.core.web.api.view.views;

import java.util.List;

@ApiResponsePlugin
public class GroupListView {
	
	private String type;
	
	private String title;
	
	private String subTitle;
	
	private String subColor;
	
	private List<ListView> listViews;
	
	/**
	 * 是否刷新
	 */
	private Boolean isFresh=false;

	public String getType() {
		return type;
	}

	public GroupListView setType(String type) {
		this.type = type;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public GroupListView setTitle(String title) {
		this.title = title;
		return this;
	}

	public List<ListView> getListViews() {
		return listViews;
	}

	public GroupListView setListViews(List<ListView> listViews) {
		this.listViews = listViews;
		return this;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public GroupListView setSubTitle(String subTitle) {
		this.subTitle = subTitle;
		return this;
	}
	

	public String getSubColor() {
		return subColor;
	}

	public GroupListView setSubColor(String subColor) {
		this.subColor = subColor;
		return this;
	}
	
	public Boolean getIsFresh() {
		return isFresh;
	}

	public GroupListView setIsFresh(Boolean isFresh) {
		this.isFresh = isFresh;
		return this;
	}

	@Override
	public String toString() {
		return "GroupListView [type=" + type + ", title=" + title + ", subTitle=" + subTitle + ", subColor=" + subColor + ", listViews=" + listViews + ", isFresh=" + isFresh + "]";
	}

}
