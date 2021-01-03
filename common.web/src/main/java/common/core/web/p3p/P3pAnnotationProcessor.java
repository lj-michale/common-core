package common.core.web.p3p;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;

import common.core.web.context.ResponseContext;
import common.core.web.interceptor.AnnotationProcessorAdapter;

public class P3pAnnotationProcessor extends AnnotationProcessorAdapter<P3pHeader> {

	@Override
	public void preHandle(P3pHeader annotation, HandlerMethod handlerMethod) {
		if (StringUtils.hasText(ResponseContext.getHeader(P3pHeader.HEADER_NAME)))
			return;
		ResponseContext.setHeader(P3pHeader.HEADER_NAME, P3pHeader.HEADER_VALUE);
	}

	@Override
	public void afterHandle(P3pHeader annotation, HandlerMethod handlerMethod) {
		super.afterHandle(annotation, handlerMethod);
	}

	@Override
	public Class<P3pHeader> getAnnotation() {
		return P3pHeader.class;
	}

}
