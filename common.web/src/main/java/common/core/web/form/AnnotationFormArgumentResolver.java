package common.core.web.form;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;

import org.springframework.beans.MutablePropertyValues;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;
import org.springframework.web.servlet.mvc.method.annotation.ServletModelAttributeMethodProcessor;
import org.springframework.web.util.WebUtils;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ClassUtil;
import common.core.common.util.ParamMappingUtil;
import common.core.common.util.StringUtil;

public class AnnotationFormArgumentResolver extends ServletModelAttributeMethodProcessor {

	public AnnotationFormArgumentResolver() {
		super(true);
	}

	@Override
	protected void validateIfApplicable(WebDataBinder binder, MethodParameter parameter) {
		// super.validateIfApplicable(binder, parameter);
		binder.validate();
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (null != parameter.getParameterAnnotations() && parameter.getParameterAnnotations().length > 0) {
			return false;
		}

		Class<?> type = parameter.getParameterType();
		if (ClassUtil.isSimpaleType(type)) {
			return false;
		}
		return true;
		// return
		// parameter.getParameterType().isAnnotationPresent(XmlRootElement.class);
	}

	@Override
	protected void bindRequestParameters(WebDataBinder binder, NativeWebRequest request) {
		ServletRequest servletRequest = request.getNativeRequest(ServletRequest.class);
		ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
		bind(servletRequest, servletBinder);
	}

	private void bind(ServletRequest request, ServletRequestDataBinder binder) {
		Map<String, ?> values = parsePropertyValues(request, binder.getTarget().getClass());
		MutablePropertyValues propertyValues = new MutablePropertyValues(values);
		MultipartRequest multipartRequest = WebUtils.getNativeRequest(request, MultipartRequest.class);
		if (multipartRequest != null) {
			bindMultipart(multipartRequest.getMultiFileMap(), propertyValues);
		}

		// TODO: support path variables?
		// from ExtendedServletRequestDataBinder to support path variable
		// String attr = HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE;
		// propertyValues.addPropertyValues((Map<String, String>)
		// request.getAttribute(attr));
		binder.bind(propertyValues);
	}

	private Map<String, ?> parsePropertyValues(ServletRequest request, Class<?> targetClass) {
		Map<String, Object> params = new HashMap<String, Object>();
		AssertErrorUtils.assertNotNull(request, "request must not be null");
		Enumeration<?> paramNames = request.getParameterNames();
		Map<String, String> parameterMappings = ParamMappingUtil.getOrCreateParamMapping(targetClass);
		if (paramNames != null)
			while (paramNames.hasMoreElements()) {
				String paramName = (String) paramNames.nextElement();
				String fieldName = parameterMappings.get(paramName);
				if (fieldName == null) {
					fieldName = paramName;
				}
				Object value = getParamValue(request, paramName);
				if ("".equals(value))
					continue;
				params.put(fieldName, value);
			}
		return params;
	}

	private Object getParamValue(ServletRequest request, String paramName) {
		String[] values = request.getParameterValues(paramName);
		if (values == null || values.length == 0)
			return null;
		for (int i = 0; i < values.length; i++) {
			values[i] = StringUtil.trimBlank(values[i]);
		}
		if (values.length > 1)
			return values;
		return values[0];
	}



	// from WebDataBinder
	private void bindMultipart(Map<String, List<MultipartFile>> multipartFiles, MutablePropertyValues propertyValues) {
		for (Map.Entry<String, List<MultipartFile>> entry : multipartFiles.entrySet()) {
			String key = entry.getKey();
			List<MultipartFile> values = entry.getValue();
			if (values.size() == 1) {
				MultipartFile value = values.get(0);
				if (!value.isEmpty()) {
					propertyValues.add(key, value);
				}
			} else {
				propertyValues.add(key, values);
			}
		}
	}
}
