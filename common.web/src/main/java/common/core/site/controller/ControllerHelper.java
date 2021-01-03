package common.core.site.controller;

import java.lang.annotation.Annotation;

import org.springframework.web.method.HandlerMethod;

public class ControllerHelper {
	public static <A extends Annotation> A findMethodOrClassLevelAnnotation(Object handler, Class<A> annotationClass) {
		if (!(handler instanceof HandlerMethod))
			return null;
		A annotationOnMethod = ((HandlerMethod) handler).getMethodAnnotation(annotationClass);
		if (annotationOnMethod != null)
			return annotationOnMethod;
		Class<?> targetClass = ((HandlerMethod) handler).getBeanType();
		while (true) {
			A annotation = targetClass.getAnnotation(annotationClass);
			if (annotation != null)
				return annotation;
			targetClass = targetClass.getSuperclass();
			if (Object.class.equals(targetClass))
				return null;
		}
	}
}
