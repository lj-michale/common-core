package common.core.web.api.view.form;

import java.util.List;

import common.core.web.api.view.field.Field;
import common.core.web.api.view.validater.Validater;

public class EditFormField extends Field{
	/**
	 * 表单字段标题
	 */
	private String title;
	
	/**
	 * 表单副标题
	 */
	private String subTitle;
	
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
	 * 是否缓存
	 */
	private boolean cache=false;
	
	/**
	 * 要删除的链接
	 */
	private String delAction;
	
	public String getDelAction() {
		return delAction;
	}

	public EditFormField setDelAction(String delAction) {
		this.delAction = delAction;
		
		return this;
	}

	/**
	 * 表单列表
	 */
	private List<FormField> formFields;
	
	/**
	 * 表单字段数据源，多选或者单选使用
	 */
	private List<FormFieldItem> formFieldItems;
	
	private List<FormFieldLinkage> formFieldLinkages;

	/**
	 * 表单字段校验规则
	 */
	private List<Validater> validaters;

	
	public EditFormField() {
		super();
	}

	public EditFormField(String subTitle, List<FormField> formFields, boolean cache) {
		super();
		this.subTitle = subTitle;
		this.formFields = formFields;
		this.cache=cache;
	}
	
	public EditFormField(String type, String title, String name) {
		super();
		this.title = title;
		this.name = name;
	}


	public String getTitle() {
		return title;
	}

	public EditFormField setTitle(String title) {
		this.title = title;
		return this;
	}

	public String getSubTitle() {
		return subTitle;
	}

	public EditFormField setSubTitle(String subTitle) {
		this.subTitle = subTitle;
		return this;
	}

	public String getName() {
		return name;
	}

	public EditFormField setName(String name) {
		this.name = name;
		return this;
	}

	public String getDescription() {
		return description;
	}

	public EditFormField setDescription(String description) {
		this.description = description;
		return this;
	}

	public String getPlaceholder() {
		return placeholder;
	}

	public EditFormField setPlaceholder(String placeholder) {
		this.placeholder = placeholder;
		return this;
	}

	public String getOperationType() {
		return operationType;
	}

	public EditFormField setOperationType(String operationType) {
		this.operationType = operationType;
		return this;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public EditFormField setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
		return this;
	}

	public List<FormField> getFormFields() {
		return formFields;
	}

	public EditFormField setFormFields(List<FormField> formFields) {
		this.formFields = formFields;
		return this;
	}

	public List<FormFieldItem> getFormFieldItems() {
		return formFieldItems;
	}

	public EditFormField setFormFieldItems(List<FormFieldItem> formFieldItems) {
		this.formFieldItems = formFieldItems;
		return this;
	}

	public List<Validater> getValidaters() {
		return validaters;
	}

	public EditFormField setValidaters(List<Validater> validaters) {
		this.validaters = validaters;
		return this;
	}

	public boolean isCache() {
		return cache;
	}

	public EditFormField setCache(boolean cache) {
		this.cache = cache;
		return this;
	}
    
	
	public List<FormFieldLinkage> getFormFieldLinkages() {
		return formFieldLinkages;
	}

	public void setFormFieldLinkages(List<FormFieldLinkage> formFieldLinkages) {
		this.formFieldLinkages = formFieldLinkages;
	}

	@Override
	public String toString() {
		return "EditFormField [title=" + title + ", subTitle=" + subTitle + ", name=" + name + ", description="
				+ description + ", placeholder=" + placeholder + ", operationType=" + operationType + ", readOnly="
				+ readOnly + ", cache=" + cache + ", formFields=" + formFields + ", formFieldItems=" + formFieldItems
				+ ", formFieldLinkages=" + formFieldLinkages + ", validaters=" + validaters + "]";
	}

	

}
