package common.core.web.api.view.views;

import java.util.List;

import common.core.web.api.view.form.EditFormField;

@ApiResponsePlugin
public class EditFormView {
	/**
	 * 后台api接口名
	 */
	private String action;
	/**
	 * 视图标题
	 */
	private String title;
	
	/**
	 * 展示模板类型
	 */
	private String type;
	
	/**
	 * 是否刷新
	 */
	private Boolean isFresh=false;

	/**
	 * 表单字段
	 */
	private List<EditFormField> editFormFields;

	/**
	 * 按钮
	 */
	private List<ButtonView> buttonViews;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getIsFresh() {
		return isFresh;
	}

	public void setIsFresh(Boolean isFresh) {
		this.isFresh = isFresh;
	}

	public List<EditFormField> getEditFormFields() {
		return editFormFields;
	}

	public void setEditFormFields(List<EditFormField> editFormFields) {
		this.editFormFields = editFormFields;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	@Override
	public String toString() {
		return "EditFormView [action=" + action + ", title=" + title + ", type=" + type + ", isFresh=" + isFresh
				+ ", editFormFields=" + editFormFields + ", buttonViews=" + buttonViews + "]";
	}
}
