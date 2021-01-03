package common.core.web.interceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

public class AnnotationProcessorInterceptor extends HandlerInterceptorAdapter {
	private static final Logger LOGGER = LoggerFactory.getLogger(AnnotationProcessorInterceptor.class);
	List<AnnotationProcessor<Annotation>> annotationProcessors = new ArrayList<AnnotationProcessor<Annotation>>();

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod) || annotationProcessors.size() == 0) {
			return super.preHandle(request, response, handler);
		}

		for (AnnotationProcessor<Annotation> annotationProcessor : annotationProcessors) {
			Annotation annotation = this.findAnnotation((HandlerMethod) handler, annotationProcessor.getAnnotation());
			if (null == annotation)
				continue;
			LOGGER.debug("exe {}.preHandle", annotationProcessor.getClass().getName());
			annotationProcessor.preHandle(annotation, (HandlerMethod) handler);
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);
		AnnotationProcessor<Annotation> annotationProcessor = null;
		for (int i = annotationProcessors.size(); i > 0; i--) {
			if(!(handler instanceof HandlerMethod))
				continue;
			annotationProcessor = annotationProcessors.get(i - 1);
			Annotation annotation = this.findAnnotation((HandlerMethod) handler, annotationProcessor.getAnnotation());
			if (null == annotation)
				continue;
			LOGGER.debug("exe {}.afterHandle", annotationProcessor.getClass().getName());
			annotationProcessor.afterHandle(annotation, (HandlerMethod) handler);
		}
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
	}

	private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
		T annotation = handler.getBeanType().getAnnotation(annotationType);
		if (annotation != null)
			return annotation;
		return handler.getMethodAnnotation(annotationType);
	}

	public void addAnnotationProcessor(AnnotationProcessor<Annotation> annotationProcessor) {
		this.annotationProcessors.add(annotationProcessor);
	}
}
