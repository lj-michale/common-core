package common.core.site.tag;

import java.io.IOException;
import java.util.Map;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.site.view.ViewContext;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class BodyTag extends TagSupport {
	public static final String BODY_TEMPLATE = BodyTag.class + "_master_body_template";

	@SuppressWarnings("rawtypes")
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		TemplateDirectiveBody bodyDirective = (TemplateDirectiveBody) ViewContext.get(BODY_TEMPLATE);
		AssertErrorUtils.assertNotNull(bodyDirective, "body template is null, make sure MasterTag puts it");
		bodyDirective.render(env.getOut());
	}

}
