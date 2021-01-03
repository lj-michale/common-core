package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import common.core.common.util.StringEscapeUtil;
import common.core.common.util.StringUtil;
import common.core.common.util.UuidUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class AutocompleteTag extends TagSupport {

	private static final String AUTOCOMPLETE_CLASS = " action-autocomplete-input ";

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		String body = buildBody(params);
		Writer out = env.getOut();
		out.write(body);
	}

	private String buildBody(@SuppressWarnings("rawtypes") Map params) {
		String renderonly = getStringParam(params, "renderonly");
		String value = getStringParam(params, "value");
		if ("true".equals(renderonly)) {
			return StringEscapeUtil.escapeHtml4(value);
		}

		@SuppressWarnings("rawtypes")
		Iterator items = params.entrySet().iterator();
		String hideIdName = getStringParam(params, "hideIdName");
		String hideIdValue = getStringParam(params, "hideIdValue");
		String hideIdId = null;
		StringBuffer body = new StringBuffer();
		body.append("<input ");
		String itemClass = AUTOCOMPLETE_CLASS;
		while (items.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry item = (Entry) items.next();
			if ("class".equals(item.getKey())) {
				itemClass = AUTOCOMPLETE_CLASS + (null == item.getValue() ? "" : item.getValue());
				continue;
			}
			if ("width".equals(item.getKey())) {
				body.append(" action-autocomplete-width").append("=\"").append(item.getValue()).append("\" ");
				continue;
			}
			if ("url".equals(item.getKey())) {
				body.append(" action-autocomplete-url").append("=\"").append(item.getValue()).append("\" ");
				continue;
			}
			body.append(item.getKey()).append("=\"").append(item.getValue()).append("\" ");
		}
		body.append(" class=\"").append(itemClass).append("\"");
		if (StringUtil.isNotBlank(hideIdName)) {
			hideIdId = UuidUtil.generateShortUuid();
			body.append(" action-autocomplete-id=\"").append(hideIdId).append("\" ");
		}
		body.append("/>");

		if (StringUtil.isNotBlank(hideIdName)) {
			hideIdValue = null != hideIdValue ? hideIdValue : "";
			body.append("<input type=\"hidden\" id=\"").append(hideIdId).append("\" name=\"").append(hideIdName).append("\" value=\"").append(hideIdValue).append("\"/>");
		}

		return body.toString();
	}

	public static void main(String[] args) {
		StringBuffer s = new StringBuffer();
		Object o = null;
		s.append("a").append(o).append("c");
		System.out.println(s.toString());
	}

}
