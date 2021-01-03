package common.core.web.api.interceptor;

import java.lang.annotation.Annotation;

import common.core.web.api.ApiRequest;
import common.core.web.api.MethodInvokeObject;

public interface ApiAnnotationProcessor<T extends Annotation> {

	public void preHandle(MethodInvokeObject methodInvokeObject, Object paramObject, ApiRequest apiRequest);

	public void afterHandle(MethodInvokeObject methodInvokeObject, Object paramObject, ApiRequest apiRequest, Object responseObject);

	public Class<T> getAnnotation();

}