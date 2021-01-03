package common.core.web.api.view.form;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import common.core.common.util.ParamMappingUtil;
import common.core.web.api.view.ObjectViewFieldBuildFactory;
import common.core.web.api.view.views.FormView;

public class FormViewFieldHelper {

	public static void initFormViewFieldValue(List<FormView> formViews, Object obj) {
		if (null == formViews)
			return;
		for (FormView formView : formViews) {
			FormViewFieldHelper.initFormViewFieldValue(formView, obj);
		}
	}

	public static void initFormViewFieldValue(FormView formView, Object obj) {
		List<FormField> formFields = formView.getFormFields();
		if (null == formFields)
			return;
		Map<String, Field> map = ParamMappingUtil.getParamFieldMap(obj.getClass());
		Field field = null;
		Object valueObject = null;
		for (FormField formField : formFields) {
			field = map.get(formField.getName());
			if (null == field)
				continue;
			try {
				valueObject = field.get(obj);
			} catch (IllegalArgumentException | IllegalAccessException e) {
				throw new RuntimeException(e);
			}
			formField.setValue(ObjectViewFieldBuildFactory.convertObjectToString(valueObject));
		}
	}
}
