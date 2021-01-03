package common.core.web.api.view;

import common.core.common.util.StringUtil;
import common.core.web.api.view.field.FieldTypes;
import common.core.web.api.view.views.ViewField;

public class ViewFieldBuilder {

	public static ViewField buildImgViewField(String title, String viewUrl) {
		ViewField viewField = new ViewField().setType(FieldTypes.IMAGE).setTitle(title);
		if(StringUtil.isNotBlank(viewUrl))
			viewField.addAttribute("viewUrl", viewUrl);
		return viewField;
	}

}
