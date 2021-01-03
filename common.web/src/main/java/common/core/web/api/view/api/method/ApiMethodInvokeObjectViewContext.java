package common.core.web.api.view.api.method;

import java.io.FileNotFoundException;

import common.core.web.api.ApiMethodInvokeObject;
import common.core.web.api.view.method.InvokeObjectView;

public class ApiMethodInvokeObjectViewContext {
	public static InvokeObjectView getApiMethodInvokeObjectView(String methodName) {
		ApiMethodInvokeObject apiMethodInvokeObject = ApiMethodInvokeObjectContext.getMethodInvokeObjectMap().get(methodName);
		if (null == apiMethodInvokeObject) {
			throw new RuntimeException(new FileNotFoundException(methodName));
		}
		InvokeObjectView result = new InvokeObjectView(methodName, apiMethodInvokeObject.getMethodAnnotation().description(),apiMethodInvokeObject.getMethodAnnotation().group(), apiMethodInvokeObject);
		return result;
	}
}
