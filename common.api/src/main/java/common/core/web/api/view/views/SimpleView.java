package common.core.web.api.view.views;

import java.util.List;

@ApiResponsePlugin
public class SimpleView {

	private String title;

	private String type;
	
	private String subTitle;
	
	private String subColor;

	private String loadType = ViewLoadTypes.INSTANT;

	private String action;

	private List<ViewField> viewFields;
	
	/**
	 * 按钮
	 */
	private List<ButtonView> buttonViews;
	
	/**
	 * 删除链接
	 */
	private String deleteAction;
	
	/**
	 * 编辑链接
	 */
	private String editAction;

	public String getAction() {
		return action;
	}

	public SimpleView setAction(String action) {
		this.action = action;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public SimpleView setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public SimpleView setSubTitle(String subTitle) {
		this.subTitle = subTitle;
		return this;
	}

	public String getSubColor() {
		return subColor;
	}

	public SimpleView setSubColor(String subColor) {
		this.subColor = subColor;
		return this;
	}

	public String getType() {
		return type;
	}

	public SimpleView setType(String type) {
		this.type = type;
		return this;
	}

	public String getLoadType() {
		return loadType;
	}

	public SimpleView setLoadType(String loadType) {
		this.loadType = loadType;
		return this;
	}

	public List<ViewField> getViewFields() {
		return viewFields;
	}

	public SimpleView setViewFields(List<ViewField> viewFields) {
		this.viewFields = viewFields;
		return this;
	}

	public SimpleView() {
		super();
	}

	public SimpleView(String type, List<ViewField> viewFields) {
		this.type = type;
		this.viewFields = viewFields;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	public String getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(String deleteAction) {
		this.deleteAction = deleteAction;
	}

	public String getEditAction() {
		return editAction;
	}

	public void setEditAction(String editAction) {
		this.editAction = editAction;
	}

	@Override
	public String toString() {
		return "SimpleView [title=" + title + ", type=" + type + ", subTitle=" + subTitle + ", subColor=" + subColor + ", loadType=" + loadType + ", action=" + action + ", viewFields=" + viewFields + ", buttonViews=" + buttonViews + ", deleteAction=" + deleteAction + ", editAction=" + editAction
				+ "]";
	}

}
