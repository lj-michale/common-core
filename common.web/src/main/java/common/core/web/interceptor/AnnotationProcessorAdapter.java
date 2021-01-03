package common.core.web.interceptor;

import java.lang.annotation.Annotation;

import org.springframework.web.method.HandlerMethod;

public abstract class AnnotationProcessorAdapter<T extends Annotation> implements AnnotationProcessor<T> {

	@Override
	public void preHandle(T annotation, HandlerMethod handlerMethod) {
	}

	@Override
	public void afterHandle(T annotation, HandlerMethod handlerMethod) {
	}

	@Override
	public Class<T> getAnnotation() {
		return null;
	}

}
