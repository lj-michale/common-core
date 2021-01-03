package common.core.site.tag;

import java.io.IOException;
import java.util.Map;

import common.core.common.util.UuidUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class HtmlEditorTag extends TagSupport {

	private static final String CLASS_APPEND = " action-html-edit";

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody body) throws TemplateException, IOException {
		// assertHasBody(body);

		// build attribute value map
		Map<String, String> attrValMap = TagSupport.buildAttributeValueMap(params, HtmlEditorTag.CLASS_APPEND, UuidUtil.generateShortUuid());

		StringBuffer pref = new StringBuffer();
		pref.append("<textarea ");
		pref.append(TagSupport.buildAttributeHtml(attrValMap));
		pref.append(" >");
		env.getOut().write(pref.toString());
		if (null != body)
			body.render(env.getOut());
		env.getOut().write("</textarea>");
	}

}
