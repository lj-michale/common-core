package common.core.site.view;

import java.util.List;

import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.bind.annotation.ResponseBody;

public class DefaultRequestResponseBodyMethodProcessor extends org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor {

	public DefaultRequestResponseBodyMethodProcessor(List<HttpMessageConverter<?>> messageConverters) {
		super(messageConverters);
	}

	public DefaultRequestResponseBodyMethodProcessor(List<HttpMessageConverter<?>> messageConverters, ContentNegotiationManager contentNegotiationManager, List<Object> responseBodyAdvice) {
		super(messageConverters, contentNegotiationManager, responseBodyAdvice);
	}

	public DefaultRequestResponseBodyMethodProcessor(List<HttpMessageConverter<?>> messageConverters, ContentNegotiationManager contentNegotiationManager) {
		super(messageConverters, contentNegotiationManager);
	}

	@Override
	public boolean supportsReturnType(MethodParameter returnType) {
		if (AnnotationUtils.findAnnotation(returnType.getParameterType(), ResponseBody.class) != null)
			return true;
		return super.supportsReturnType(returnType);
	}

}
