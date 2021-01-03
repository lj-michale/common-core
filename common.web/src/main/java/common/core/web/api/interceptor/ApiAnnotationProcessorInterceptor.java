package common.core.web.api.interceptor;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.web.api.ApiRequest;
import common.core.web.api.MethodInvokeObject;

public class ApiAnnotationProcessorInterceptor implements ApiInterceptor {
	private final Logger logger = LoggerFactory.getLogger(ApiAnnotationProcessorInterceptor.class);

	List<ApiAnnotationProcessor<? extends Annotation>> apiAnnotationProcessors = new ArrayList<ApiAnnotationProcessor<? extends Annotation>>();

	@Override
	public void preHandle(MethodInvokeObject methodInvokeObject, Object paramObject, ApiRequest apiRequest) {
		ApiAnnotationProcessor<? extends Annotation> apiAnnotationProcessor = null;
		for (int i = 0; i < apiAnnotationProcessors.size(); i++) {
			apiAnnotationProcessor = apiAnnotationProcessors.get(i);
			Annotation annotation = methodInvokeObject.getMethod().getAnnotation(apiAnnotationProcessor.getAnnotation());
			if (null == annotation)
				annotation = methodInvokeObject.getInvokeObject().getClass().getAnnotation(apiAnnotationProcessor.getAnnotation());
			if (null == annotation)
				continue;
			logger.debug("exe {}.preHandle", apiAnnotationProcessor.getClass().getName());
			apiAnnotationProcessor.preHandle(methodInvokeObject, paramObject, apiRequest);
		}
	}

	@Override
	public void afterHandle(MethodInvokeObject methodInvokeObject, Object paramObject, ApiRequest apiRequest, Object responseObject) {
		ApiAnnotationProcessor<? extends Annotation> apiAnnotationProcessor = null;
		for (int i = 0; i < apiAnnotationProcessors.size(); i++) {
			apiAnnotationProcessor = apiAnnotationProcessors.get(apiAnnotationProcessors.size() - i - 1);
			Annotation annotation = methodInvokeObject.getMethod().getAnnotation(apiAnnotationProcessor.getAnnotation());
			if (null == annotation)
				annotation = methodInvokeObject.getClass().getAnnotation(apiAnnotationProcessor.getAnnotation());
			if (null == annotation)
				continue;
			logger.debug("exe {}.afterHandle", apiAnnotationProcessor.getClass().getName());
			apiAnnotationProcessor.afterHandle(methodInvokeObject, paramObject, apiRequest, responseObject);
		}
	}

	public void addApiAnnotationProcessors(ApiAnnotationProcessor<? extends Annotation> apiAnnotationProcessor) {
		this.apiAnnotationProcessors.add(apiAnnotationProcessor);
	}

}
