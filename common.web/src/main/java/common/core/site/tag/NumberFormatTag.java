
package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.text.DecimalFormat;
import java.util.Map;

import common.core.common.util.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class NumberFormatTag extends TagSupport {

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		String body = buildNumberFormatBody(params);
		Writer out = env.getOut();
		out.write(body);
	}

	private String buildNumberFormatBody(@SuppressWarnings("rawtypes") Map params) {
		String value = getStringParam(params, "value");
		if (StringUtil.isEmpty(value)) {
			String defaultValue = getStringParam(params, "defaultValue");
			if (StringUtil.isEmpty(defaultValue)) {
				defaultValue = "";
			}
			return defaultValue;
		}
		String format = getStringParam(params, "format");
		if (StringUtil.isEmpty(format)) {
			format = ",##0.00";
		}
		DecimalFormat decimalFormat = new DecimalFormat(format);
		return decimalFormat.format(Double.valueOf(value));
	}

}
