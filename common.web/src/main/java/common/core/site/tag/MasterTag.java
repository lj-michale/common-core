package common.core.site.tag;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.support.RequestContextUtils;

import common.core.common.util.ApplicationContextUtil;
import common.core.site.view.DefaulteDeviceDelegatingViewResolver;
import common.core.site.view.ViewContext;
import common.core.site.view.ViewException;
import common.core.web.context.RequestContext;
import common.core.web.context.ResponseContext;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

public class MasterTag extends TagSupport {
	// private final MasterTemplateLoader templateLoader;

	@SuppressWarnings("unchecked")
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] arg2, TemplateDirectiveBody body) throws TemplateException, IOException {
		assertHasBody(body);
		String template = getRequiredStringParam(params, "template");
		ViewContext.currentValues().putAll(params);
		ViewContext.put(BodyTag.BODY_TEMPLATE, body);
		render(template, ViewContext.currentValues());
	}

	@SuppressWarnings("unchecked")
	protected void render(String template, @SuppressWarnings("rawtypes") Map model) throws IOException {
		DefaulteDeviceDelegatingViewResolver defaulteDeviceDelegatingViewResolver = ApplicationContextUtil.getBean(DefaulteDeviceDelegatingViewResolver.class);
		Locale locale = RequestContextUtils.getLocale(RequestContext.get());
		try {	
			View view = defaulteDeviceDelegatingViewResolver.resolveViewName(template, locale);
			view.render(model, RequestContext.get(), ResponseContext.get());
		} catch (Exception e) {
			throw new ViewException(e.getMessage(), e);
		}
	}

}
