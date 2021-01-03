package common.core.web.api.view.views;

import java.util.List;

import common.core.web.api.view.button.Button;
import common.core.web.api.view.form.FormField;

@ApiResponsePlugin
public class FormView {

	/**
	 * 后台api接口名
	 */
	private String action;

	/**
	 * 视图标题名称，如贷款要素
	 */
	private String title;

	/**
	 * 展示模板类型
	 */
	private String type;
	
	/**
	 * 加载方式
	 */
	private String loadType=ViewLoadTypes.INSTANT;
	
	/**
	 * 是否刷新
	 */
	private Boolean isFresh=false;

	/**
	 * 表单字段
	 */
	private List<FormField> formFields;

	/**
	 * 按钮
	 */
	private List<Button> buttonFields;
	
	/**
	 * 按钮
	 */
	private List<ButtonView> buttonViews;
	
	/**
	 * 底部按钮
	 */
	private List<ButtonView> underButtonViews;
	
	/*
	 * 是否分享
	 */
	private Boolean isShare=false;
	
	public List<FormField> getFormFields() {
		return formFields;
	}

	public FormView setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
		return this;
	}

	public String getAction() {
		return action;
	}

	public FormView setAction(String action) {
		this.action = action;
		return this;
	}

	public String getType() {
		return type;
	}

	public FormView setType(String type) {
		this.type = type;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public FormView setTitle(String title) {
		this.title = title;
		return this;
	}

	public List<Button> getButtonFields() {
		return buttonFields;
	}

	public void setButtonFields(List<Button> buttonFields) {
		this.buttonFields = buttonFields;
	}

	public List<ButtonView> getUnderButtonViews() {
		return underButtonViews;
	}

	public void setUnderButtonViews(List<ButtonView> underButtonViews) {
		this.underButtonViews = underButtonViews;
	}

	public Boolean getIsFresh() {
		return isFresh;
	}

	public FormView setIsFresh(Boolean isFresh) {
		this.isFresh = isFresh;
		return this;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public void setIsShare(Boolean isShare) {
		this.isShare = isShare;
	}

	public String getLoadType() {
		return loadType;
	}

	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	@Override
	public String toString() {
		return "FormView [action=" + action + ", title=" + title + ", type=" + type + ", loadType=" + loadType
				+ ", isFresh=" + isFresh + ", formFields=" + formFields + ", buttonFields=" + buttonFields
				+ ", buttonViews=" + buttonViews + ", underButtonViews=" + underButtonViews + ", isShare=" + isShare
				+ "]";
	}

}
