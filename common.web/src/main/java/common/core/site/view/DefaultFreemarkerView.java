package common.core.site.view;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.support.RequestContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerView;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

import freemarker.template.Template;

public class DefaultFreemarkerView extends FreeMarkerView {

	private static Logger logger = LoggerFactory.getLogger(DefaultFreemarkerView.class);

	private boolean exposeRequestAttributes = false;

	private boolean allowRequestOverride = false;

	private boolean exposeSessionAttributes = false;

	private boolean allowSessionOverride = false;

	private boolean exposeSpringMacroHelpers = true;

	public DefaultFreemarkerView() {
		super();
	}

	@Override
	protected void exposeHelpers(Map<String, Object> model, HttpServletRequest request) throws Exception {
		Map<String, Object> viewModel = ViewContext.currentValues();
		if (null != viewModel) {
			model.putAll(viewModel);
		}
		super.exposeHelpers(model, request);
	}

	@Override
	public Template getTemplate(String fullTemplatePath, Locale locale) throws IOException {
		return super.getTemplate(fullTemplatePath, locale);
	}

	@Override
	protected void exposeModelAsRequestAttributes(Map<String, Object> model, HttpServletRequest request) throws Exception {
		for (Map.Entry<String, Object> entry : model.entrySet()) {
			String modelName = entry.getKey();
			Object modelValue = entry.getValue();
			if (modelValue != null) {
				request.setAttribute(modelName, modelValue);
			} else {
				request.removeAttribute(modelName);
			}
		}
	}

	@Override
	public void setAllowRequestOverride(boolean allowRequestOverride) {
		super.setAllowRequestOverride(allowRequestOverride);
		this.allowRequestOverride = allowRequestOverride;
	}

	@Override
	public void setExposeSessionAttributes(boolean exposeSessionAttributes) {
		super.setExposeSessionAttributes(exposeSessionAttributes);
		this.exposeSessionAttributes = exposeSessionAttributes;
	}

	@Override
	public void setAllowSessionOverride(boolean allowSessionOverride) {
		super.setAllowSessionOverride(allowSessionOverride);
		this.allowSessionOverride = allowSessionOverride;
	}

	@Override
	public void setExposeSpringMacroHelpers(boolean exposeSpringMacroHelpers) {
		super.setExposeSpringMacroHelpers(exposeSpringMacroHelpers);
		this.exposeSpringMacroHelpers = exposeSpringMacroHelpers;
	}

	@Override
	public void setExposeRequestAttributes(boolean exposeRequestAttributes) {
		super.setExposeRequestAttributes(exposeRequestAttributes);
		this.exposeRequestAttributes = exposeRequestAttributes;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		logger.trace("Rendering view with name '{}' with model {} and static attributes {}", this.getBeanName(), model, this.getStaticAttributes());
		Map<String, Object> mergedModel = createMergedOutputModel(model, request, response);
		prepareResponse(request, response);
		renderAndMergedOutputModel(mergedModel, getRequestToExpose(request), response);
	}

	protected void renderAndMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {

		if (this.exposeRequestAttributes) {
			for (Enumeration<String> en = request.getAttributeNames(); en.hasMoreElements();) {
				String attribute = en.nextElement();
				if (model.containsKey(attribute) && !this.allowRequestOverride) {
					throw new ServletException("Cannot expose request attribute '" + attribute + "' because of an existing model object of the same name");
				}
				Object attributeValue = request.getAttribute(attribute);
				model.put(attribute, attributeValue);
			}
		}

		if (this.exposeSessionAttributes) {
			HttpSession session = request.getSession(false);
			if (session != null) {
				for (Enumeration<String> en = session.getAttributeNames(); en.hasMoreElements();) {
					String attribute = en.nextElement();
					if (model.containsKey(attribute) && !this.allowSessionOverride) {
						throw new ServletException("Cannot expose session attribute '" + attribute + "' because of an existing model object of the same name");
					}
					Object attributeValue = session.getAttribute(attribute);
					model.put(attribute, attributeValue);
				}
			}
		}

		if (this.exposeSpringMacroHelpers) {
			if (model.containsKey(SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE)) {
				throw new ServletException("Cannot expose bind macro helper '" + SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE + "' because of an existing model object of the same name");
			}
			// Expose RequestContext instance for Spring macros.
			model.put(SPRING_MACRO_REQUEST_CONTEXT_ATTRIBUTE, new RequestContext(request, response, getServletContext(), model));
		}

		applyContentType(response);

		renderMergedTemplateModel(model, request, response);
	}
}
