package common.core.web.api.view.views;

import java.util.Arrays;
import java.util.List;

import common.core.web.api.view.form.FormField;

@ApiResponsePlugin
public class ListView {

	private String type;

	private String title;

	private String[] titles;

	private List<String[]> datasList;

	private String[] fieldTypes;

	private String[] actions;
	
	private String[] dataStatus;
	
	private String operateType=ViewLoadTypes.READONLY_TYPE;//操作类型
	
	private List<FormField> nextPageFormFields;
	
	/**
	 * 是否刷新
	 */
	private Boolean isFresh=false;
	
	private List<ButtonView> buttonViews;
	
	/**
	 * 删除链接
	 */
	private String[] deleteAction;
	
	/**
	 * 编辑链接
	 */
	private String[] editAction;
	
	/**
	 *公告数 
	 */
	private String[] messageNum;
	
	/**
	 * icon
	 */
	private String[] icon;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String[] getTitles() {
		return titles;
	}

	public void setTitles(String[] titles) {
		this.titles = titles;
	}

	public String[] getFieldTypes() {
		return fieldTypes;
	}

	public void setFieldTypes(String[] fieldTypes) {
		this.fieldTypes = fieldTypes;
	}

	public String[] getActions() {
		return actions;
	}

	public void setActions(String[] actions) {
		this.actions = actions;
	}

	public List<String[]> getDatasList() {
		return datasList;
	}

	public void setDatasList(List<String[]> datasList) {
		this.datasList = datasList;
	}

	public String[] getDataStatus() {
		return dataStatus;
	}

	public void setDataStatus(String[] dataStatus) {
		this.dataStatus = dataStatus;
	}

	public List<FormField> getNextPageFormFields() {
		return nextPageFormFields;
	}

	public void setNextPageFormFields(List<FormField> nextPageFormFields) {
		this.nextPageFormFields = nextPageFormFields;
	}

	public Boolean getIsFresh() {
		return isFresh;
	}

	public ListView setIsFresh(Boolean isFresh) {
		this.isFresh = isFresh;
		return this;
	}

	public String getOperateType() {
		return operateType;
	}

	public ListView setOperateType(String operateType) {
		this.operateType = operateType;
		return this;
	}

	public List<ButtonView> getButtonViews() {
		return buttonViews;
	}

	public void setButtonViews(List<ButtonView> buttonViews) {
		this.buttonViews = buttonViews;
	}

	public String[] getDeleteAction() {
		return deleteAction;
	}

	public void setDeleteAction(String[] deleteAction) {
		this.deleteAction = deleteAction;
	}

	public String[] getEditAction() {
		return editAction;
	}

	public void setEditAction(String[] editAction) {
		this.editAction = editAction;
	}

	public String[] getMessageNum() {
		return messageNum;
	}

	public void setMessageNum(String[] messageNum) {
		this.messageNum = messageNum;
	}

	public String[] getIcon() {
		return icon;
	}

	public void setIcon(String[] icon) {
		this.icon = icon;
	}

	@Override
	public String toString() {
		return "ListView [type=" + type + ", title=" + title + ", titles=" + Arrays.toString(titles) + ", datasList=" + datasList + ", fieldTypes=" + Arrays.toString(fieldTypes) + ", actions=" + Arrays.toString(actions) + ", dataStatus=" + Arrays.toString(dataStatus) + ", operateType=" + operateType
				+ ", nextPageFormFields=" + nextPageFormFields + ", isFresh=" + isFresh + ", buttonViews=" + buttonViews + ", deleteAction=" + Arrays.toString(deleteAction) + ", editAction=" + Arrays.toString(editAction) + ", messageNum=" + Arrays.toString(messageNum) + ", icon="
				+ Arrays.toString(icon) + "]";
	}
}
