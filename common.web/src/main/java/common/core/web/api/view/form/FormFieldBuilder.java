package common.core.web.api.view.form;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import common.core.common.util.ClassUtil;
import common.core.common.util.ObjectUtil;
import common.core.common.util.StringUtil;
import common.core.web.api.view.ObjectViewFieldBuildFactory;
import common.core.web.api.view.field.FieldTypes;

public class FormFieldBuilder {

	public static FormField buildDateFormField(String title, String name) {
		FormField formField = new FormField(FieldTypes.DATE, title, name);
		formField.setOperationType(FormFieldOperationTypes.DATE);
		return formField;
	}

	public static FormField buildTextFormField(String title, String name) {
		FormField formField = new FormField(FieldTypes.STRING, title, name);
		return formField;
	}

	public static FormField buildAutocompleteFormField(String title, String name, String autoApi, String hidenFieldName) {
		FormField formField = new FormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.AUTOCOMPLETE).addAttribute("autoApi", autoApi).addAttribute("hidenFieldName", hidenFieldName);
		return formField;
	}
	
	public static EditFormField buildAutocompleteEditFormField(String title, String name, String autoApi, String hidenFieldName) {
		EditFormField formField = new EditFormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.AUTOCOMPLETE).addAttribute("autoApi", autoApi).addAttribute("hidenFieldName", hidenFieldName);
		return formField;
	}

	public static FormField buildImgFormField(String title, String name, String viewUrl, String uploadApi) {
		FormField formField = new FormField(FieldTypes.IMAGE, title, name);
		formField.setOperationType(FormFieldOperationTypes.IMG).addAttribute("viewUrl", viewUrl).addAttribute("uploadApi", uploadApi);
		return formField;
	}
	
	public static EditFormField buildImgEditFormField(String title, String name, String viewUrl, String uploadApi) {
		EditFormField formField = new EditFormField(FieldTypes.IMAGE, title, name);
		formField.setOperationType(FormFieldOperationTypes.IMG).addAttribute("viewUrl", viewUrl).addAttribute("uploadApi", uploadApi);
		return formField;
	}

	public static FormFieldItem buildFormFieldItem(Object[] datas) {
		return new FormFieldItem(datas[0].toString(), datas[1].toString());
	}
	
	public static FormFieldItem buildFormFieldItemThree(Object[] datas) {
		return new FormFieldItem(datas[0].toString(), datas[1].toString(),datas[2].toString());
	}
    
	public static FormFieldItem buildFormFieldLinkage(Object[] datas) {
		return new FormFieldItem(datas[0].toString(), datas[1].toString(),"",datas[3].toString());
	}
	
	public static FormField buildRequestFormField(String title, String name, List<Object[]> datasList) {
		FormField formField = new FormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.REQUEST);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldItem(datasList.get(i)));
		}
		formField.setFormFieldItems(formFieldItems);
		return formField;
	}
	
	public static FormField buildRadioFormField(String title, String name, List<Object[]> datasList) {
		FormField formField = new FormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.RADIO);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldItem(datasList.get(i)));
		}
		formField.setFormFieldItems(formFieldItems);
		return formField;
	}

	public static FormField buildSelectFormField(String title, String name, List<Object[]> datasList) {
		FormField formField = new FormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.SELECT);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldItem(datasList.get(i)));
		}
		formField.setFormFieldItems(formFieldItems);
		return formField;
	}
	
	public static FormField buildSelectBankFormField(String title, String name, List<Object[]> datasList) {
		FormField formField = new FormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.SECRET_BANK_INPUT);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldItemThree(datasList.get(i)));
		}
		formField.setFormFieldItems(formFieldItems);
		return formField;
	}
	
	public static EditFormField buildSelectEditFormThreeField(String title, String name, List<Object[]> datasList) {
		EditFormField formField = new EditFormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.SECRET_BANK_INPUT);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldItemThree(datasList.get(i)));
		}
		formField.setFormFieldItems(formFieldItems);
		return formField;
	}
	
	public static EditFormField buildSelectEditFormLinkage(String title, String name, List<Object[]> datasList) {
		EditFormField formField = new EditFormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.SECRET_BANK_INPUT);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldLinkage(datasList.get(i)));
		}
		formField.setFormFieldItems(formFieldItems);
		return formField;
	}
	
	public static EditFormField buildSelectEditFormField(String title, String name, List<Object[]> datasList) {
		EditFormField editFormField = new EditFormField(FieldTypes.STRING, title, name);
		editFormField.setOperationType(FormFieldOperationTypes.SELECT);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldItem(datasList.get(i)));
		}
		editFormField.setFormFieldItems(formFieldItems);
		return editFormField;
	}
	
	public static EditFormField buildCheckBoxEditFormField(String title, String name, List<Object[]> datasList) {
		EditFormField editFormField = new EditFormField(FieldTypes.STRING, title, name);
		editFormField.setOperationType(FormFieldOperationTypes.MULTISELECT);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldItem(datasList.get(i)));
		}
		editFormField.setFormFieldItems(formFieldItems);
		return editFormField;
	}

	public static FormField buildCheckBoxFormField(String title, String name, List<Object[]> datasList) {
		FormField formField = new FormField(FieldTypes.STRING, title, name);
		formField.setOperationType(FormFieldOperationTypes.CHECKBOX);
		List<FormFieldItem> formFieldItems = new ArrayList<>();
		for (int i = 0; null != datasList && i < datasList.size(); i++) {
			formFieldItems.add(FormFieldBuilder.buildFormFieldItem(datasList.get(i)));
		}
		formField.setFormFieldItems(formFieldItems);
		return formField;
	}

	public static FormField buildHiddenFormField(String name, String value) {
		FormField formField = new FormField().setOperationType(FormFieldOperationTypes.HIDDEN).setName(name);
		formField.setValue(value);
		return formField;
	}

	/**
	 * 根据对象的属性生成隐藏表单字段
	 * 
	 * @param obj Object
	 * @param ignoreFields String[]
	 * @return  List of FormField 
	 */
	public static List<FormField> buildHiddenFormFields(Object obj, String[] ignoreFields) {
		List<FormField> formFields = null;
		Map<String, Object> valueMap = ObjectUtil.toMap(obj);
		String value = null;
		for (Iterator<Entry<String, Object>> iterator = valueMap.entrySet().iterator(); iterator.hasNext();) {
			Map.Entry<String, Object> item = iterator.next();
			if (null != ignoreFields && Arrays.binarySearch(ignoreFields, item.getKey()) >= 0)
				continue;
			if (StringUtil.isEmpty(item.getValue()) || !ClassUtil.isSimpaleType(item.getValue().getClass()))
				continue;
			if (null == formFields)
				formFields = new ArrayList<>();
			value = ObjectViewFieldBuildFactory.convertObjectToString(item.getValue());
			formFields.add(FormFieldBuilder.buildHiddenFormField(item.getKey(), value));
		}
		return formFields;
	}

	/**
	 * 根据对象的属性生成隐藏表单字段
	 *  
	 * @param obj Object
	 * @return List of FormField
	 */
	public static List<FormField> buildHiddenFormFields(Object obj) {
		return FormFieldBuilder.buildHiddenFormFields(obj, null);
	}
}
