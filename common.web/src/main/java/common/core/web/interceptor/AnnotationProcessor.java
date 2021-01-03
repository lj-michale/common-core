package common.core.web.interceptor;

import java.lang.annotation.Annotation;

import org.springframework.web.method.HandlerMethod;

public interface AnnotationProcessor<T extends Annotation> {

	public void preHandle(T annotation, HandlerMethod handlerMethod);

	public void afterHandle(T annotation, HandlerMethod handlerMethod);

	public Class<T> getAnnotation();

}
