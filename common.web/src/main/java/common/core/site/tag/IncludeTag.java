package common.core.site.tag;

import java.io.IOException;
import java.util.Map;

import common.core.app.lang.CharSet;
import common.core.common.util.ApplicationContextUtil;
import common.core.site.view.DefaulteDeviceDelegatingViewResolver;

import freemarker.cache.TemplateCache;
import freemarker.core.Environment;
import freemarker.core.ParseException;
import freemarker.core._DelayedGetMessage;
import freemarker.core._DelayedJQuote;
import freemarker.core._MiscTemplateException;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class IncludeTag extends TagSupport {

	@SuppressWarnings({ "rawtypes", "deprecation" })
	@Override
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
		DefaulteDeviceDelegatingViewResolver defaulteDeviceDelegatingViewResolver = ApplicationContextUtil.getBean(DefaulteDeviceDelegatingViewResolver.class);
		if (null == defaulteDeviceDelegatingViewResolver)
			return;
		String includeFile = IncludeTag.getRequiredStringParam(params, "file");
		String templateNameString = defaulteDeviceDelegatingViewResolver.getDeviceViewName(includeFile);
		Template includedTemplate;
		try {
			templateNameString = TemplateCache.getFullTemplatePath(env, "", templateNameString);
			includedTemplate = env.getTemplateForInclusion(templateNameString, CharSet.DEFAULT_CHAR_SET_STRING, true);
		} catch (ParseException pe) {
			throw new _MiscTemplateException(pe, env, new Object[] { "Error parsing included template ", new _DelayedJQuote(templateNameString), ":\n", new _DelayedGetMessage(pe) });
		} catch (IOException ioe) {
			throw new _MiscTemplateException(ioe, env, new Object[] { "Error reading included file ", new _DelayedJQuote(templateNameString), ":\n", new _DelayedGetMessage(ioe) });
		}
		env.include(includedTemplate);
	}

}
