package common.core.web.api;

import java.lang.reflect.Method;

public class ApiMethodInvokeObject extends MethodInvokeObject {

	private ApiMethod methodAnnotation;

	public ApiMethod getMethodAnnotation() {
		return methodAnnotation;
	}

	public void setMethodAnnotation(ApiMethod methodAnnotation) {
		this.methodAnnotation = methodAnnotation;
	}

	public ApiMethodInvokeObject(Method method, Object invokeObject, ApiMethod methodAnnotation) {
		super(method, invokeObject);
		this.methodAnnotation = methodAnnotation;
	}

}
