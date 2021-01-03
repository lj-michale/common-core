package common.core.web.api.view.method;

import java.lang.reflect.Method;

public class InvokeObjectSimpleView {

	private String methodName;

	private String description;
	
	private String group;
	
	private Method method;

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public InvokeObjectSimpleView(String methodName, String description,String group, Method method) {
		super();
		this.methodName = methodName;
		this.description = description;
		this.method = method;
		this.group=group;
	}

	@Override
	public String toString() {
		return "ApiMethodInvokeObjectSimpleView [methodName=" + methodName + ", description=" + description + ", group=" + group + ", method=" + method + "]";
	}

}
