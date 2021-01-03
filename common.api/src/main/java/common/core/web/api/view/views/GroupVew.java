package common.core.web.api.view.views;

import java.util.List;

@ApiResponsePlugin
public class GroupVew {

	private String type;
	private String title;
	private boolean flush=true;//是否刷新
	private String subTitle;//副标题
	private List<SimpleView> simpleViews;
	private List<FormView> formViews;
	private List<ButtonView> buttonViews;
	private List<ButtonView> underButtonViews;
	private List<GroupListView> groupListViews;
	private List<ListView> listViews;
	/**
	 * 是否刷新
	 */
	private Boolean isFresh=false;

	public List<SimpleView> getSimpleViews() {
		return simpleViews;
	}

	public void setSimpleViews(List<SimpleView> simpleViews) {
		this.simpleViews = simpleViews;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<FormView> getFormViews() {
		return formViews;
	}

	public void setFormViews(List<FormView> formViews) {
		this.formViews = formViews;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<GroupListView> getGroupListViews() {
		return groupListViews;
	}

	public void setGroupListViews(List<GroupListView> groupListViews) {
		this.groupListViews = groupListViews;
	}

	public List<ListView> getListViews() {
		return listViews;
	}

	public void setListViews(List<ListView> listViews) {
		this.listViews = listViews;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}

	public Boolean getIsFresh() {
		return isFresh;
	}

	public void setIsFresh(Boolean isFresh) {
		this.isFresh = isFresh;
	}

	public List<ButtonView> getUnderButtonViews() {
		return underButtonViews;
	}

	public void setUnderButtonViews(List<ButtonView> underButtonViews) {
		this.underButtonViews = underButtonViews;
	}

	public boolean isFlush() {
		return flush;
	}

	public void setFlush(boolean flush) {
		this.flush = flush;
	}

	@Override
	public String toString() {
		return "GroupVew [type=" + type + ", title=" + title + ", flush=" + flush + ", subTitle=" + subTitle
				+ ", simpleViews=" + simpleViews + ", formViews=" + formViews + ", buttonViews=" + buttonViews
				+ ", underButtonViews=" + underButtonViews + ", groupListViews=" + groupListViews + ", listViews="
				+ listViews + ", isFresh=" + isFresh + "]";
	}
}
