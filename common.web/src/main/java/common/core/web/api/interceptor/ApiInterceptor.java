package common.core.web.api.interceptor;

import common.core.web.api.ApiRequest;
import common.core.web.api.MethodInvokeObject;

public interface ApiInterceptor {
	public void preHandle(MethodInvokeObject methodInvokeObject, Object paramObject, ApiRequest apiRequest);

	public void afterHandle(MethodInvokeObject methodInvokeObject, Object paramObject, ApiRequest apiRequest, Object responseObject);
}
