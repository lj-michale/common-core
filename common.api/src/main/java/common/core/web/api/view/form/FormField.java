package common.core.web.api.view.form;

import java.util.List;

import common.core.web.api.view.button.Button;
import common.core.web.api.view.field.Field;
import common.core.web.api.view.validater.Validater;

public class FormField extends Field {

	/**
	 * 表单字段标题
	 */
	private String title;

	/**
	 * 表单提交字段名称
	 */
	private String name;

	/**
	 * 表单字段描述
	 */
	private String description;
	
	/**
	 * 表单字段占位符
	 */
	private String placeholder;

	/**
	 * 表单操作类型
	 */
	private String operationType = FormFieldOperationTypes.INPUT;

	/**
	 * 是否只读
	 */
	private boolean readOnly = false;

	/**
	 * 表单字段数据源，多选或者单选使用
	 */
	private List<FormFieldItem> formFieldItems;
	
	
	/**
	 * 表单按钮
	 */
	private List<Button> buttons;
	
	/**
	 * 表单字段校验规则
	 */
	private List<Validater> validaters;
	/**
	 * 删除链接
	 */
	private String delAction;
	
	/*
	 * 是否分享
	 */
	private Boolean isShare;
	
	/*
	 * 是否去截取链接的后缀
	 */
	private Boolean isSuffix;
	
	/*
	 * 计算公式
	 */
	private String calculateFormulary;
	
	public String getCalculateFormulary() {
		return calculateFormulary;
	}

	public FormField setCalculateFormulary(String calculateFormulary) {
		this.calculateFormulary = calculateFormulary;
		return this;
	}

	public Boolean getIsSuffix() {
		return isSuffix;
	}

	public FormField setIsSuffix(Boolean isSuffix) {
		this.isSuffix = isSuffix;
		return this;
	}

	public Boolean getIsShare() {
		return isShare;
	}

	public FormField setIsShare(Boolean isShare) {
		this.isShare = isShare;
		return this;
	}

	public String getOperationType() {
		return operationType;
	}

	public FormField setOperationType(String operationType) {
		this.operationType = operationType;
		return this;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public FormField setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	public List<Validater> getValidaters() {
		return validaters;
	}

	public FormField setValidaters(List<Validater> validaters) {
		this.validaters = validaters;
		return this;
	}

	public String getTitle() {
		return title;
	}

	public FormField setTitle(String title) {
		this.title = title;
		return this;
	}

	public List<FormFieldItem> getFormFieldItems() {
		return formFieldItems;
	}

	public FormField setFormFieldItems(List<FormFieldItem> formFieldItems) {
		this.formFieldItems = formFieldItems;
		return this;
	}

	public String getName() {
		return name;
	}

	public FormField setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}
	@Override
	public FormField setValue(String value) {
		return (FormField) super.setValue(value);
	}
    
	@Override
	public FormField setLinkageCode(String[] linkageCode) {
		return (FormField) super.setLinkageCode(linkageCode);
	}
	
	@Override
	public FormField setNineRate(String nineRate) {
		return (FormField) super.setNineRate(nineRate);
	}
	
	@Override
	public FormField setType(String type) {
		return (FormField) super.setType(type);
	}

	@Override
	public FormField setAttribute(String attribute) {
		return (FormField) super.setAttribute(attribute);
	}

	@Override
	public FormField addAttribute(String name, String value) {
		return (FormField) super.addAttribute(name, value);
	}

	public FormField setDescription(String description) {
		this.description = description;
		return this;
	}

	public FormField(String type, String title, String name) {
		super(type);
		this.title = title;
		this.name = name;
	}

	public FormField(String type, String title, String name, String value) {
		super(type, value);
		this.title = title;
		this.name = name;
	}

	public FormField(String title, String name, String description, String operationType, List<Validater> validaters) {
		super();
		this.title = title;
		this.name = name;
		this.description = description;
		this.operationType = operationType;
		this.validaters = validaters;
	}

	public FormField() {
		super();
	}

	public FormField(String type) {
		super(type);
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public FormField setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		return this;
	}


	public List<Button> getButtons() {
		return buttons;
	}

	public void setButtons(List<Button> buttons) {
		this.buttons = buttons;
	}
    
	
	public String getDelAction() {
		return delAction;
	}

	public FormField setDelAction(String delAction) {
		this.delAction = delAction;
		return this;
	}

	@Override
	public String toString() {
		return "FormField [title=" + title + ", name=" + name + ", description=" + description + ", placeholder="
				+ placeholder + ", operationType=" + operationType + ", readOnly=" + readOnly + ", formFieldItems="
				+ formFieldItems + ", buttons=" + buttons + ", validaters=" + validaters + ", delAction=" + delAction
				+ ", isShare=" + isShare + ", isSuffix=" + isSuffix + ", calculateFormulary=" + calculateFormulary
				+ "]";
	}
}
