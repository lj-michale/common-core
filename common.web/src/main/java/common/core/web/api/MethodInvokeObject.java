package common.core.web.api;

import java.lang.reflect.Method;

public class MethodInvokeObject {

	private Method method;
	private Object invokeObject;

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Object getInvokeObject() {
		return invokeObject;
	}

	public void setInvokeObject(Object invokeObject) {
		this.invokeObject = invokeObject;
	}

	public MethodInvokeObject(Method method, Object invokeObject) {
		super();
		this.method = method;
		this.invokeObject = invokeObject;
	}

	@Override
	public String toString() {
		return "MethodInvokeObject [method=" + method + ", invokeObject=" + invokeObject + "]";
	}

}
