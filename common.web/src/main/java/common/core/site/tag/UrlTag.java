package common.core.site.tag;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import org.springframework.util.StringUtils;

import common.core.app.context.ConfigContext;
import common.core.common.util.StringUtil;
import common.core.web.url.UrlHelper;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class UrlTag extends TagSupport {

	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody arg3) throws TemplateException, IOException {
		String url = getRequiredStringParam(params, "value");
		String scheme = getStringParam(params, "scheme");
		String domain = getStringParam(params, "domain");
		String assign = getStringParam(params, "assign");
		if (StringUtils.hasText(domain)) {
			domain = ConfigContext.getStringValue(domain, domain);
		}
		if (StringUtil.hasText(assign)) {
			env.setVariable(assign, new SimpleScalar(UrlHelper.build(url, scheme, domain)));
			return;
		}
		Writer output = env.getOut();
		output.write(UrlHelper.build(url, scheme, domain));
	}

}
