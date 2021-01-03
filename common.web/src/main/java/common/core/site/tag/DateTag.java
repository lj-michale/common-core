
package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import common.core.common.util.StringEscapeUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class DateTag extends TagSupport {

	private static final String DATE_CLASS = " Wdate action-date-input ";

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		String body = buildDate(params);
		Writer out = env.getOut();
		out.write(body);
	}

	private String buildDate(@SuppressWarnings("rawtypes") Map params) {
		String renderonly = getStringParam(params, "renderonly");
		String value = getStringParam(params, "value");
		if ("true".equals(renderonly)) {
			return StringEscapeUtil.escapeHtml4(value);
		}

		@SuppressWarnings("rawtypes")
		Iterator items = params.entrySet().iterator();
		StringBuffer body = new StringBuffer();
		body.append("<input ");
		String itemClass = DATE_CLASS;
		while (items.hasNext()) {
			@SuppressWarnings("rawtypes")
			Map.Entry item = (Entry) items.next();
			if ("class".equals(item.getKey())) {
				itemClass = DATE_CLASS + (null == item.getValue() ? "" : item.getValue());
				continue;
			}
			body.append(item.getKey()).append("=\"").append(item.getValue()).append("\" ");
		}
		body.append(" class=\"").append(itemClass).append("\"");

		if (!params.containsKey(renderonly)) {
			body.append(" readonly=\"readonly\" ");
		}

		body.append("/>");

		return body.toString();
	}

}
