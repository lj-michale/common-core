package common.core.web.api.view.method;

import java.lang.reflect.Type;
import java.util.List;
import common.core.common.util.PropertiesUtil;
import common.core.common.util.ClassUtil;
import common.core.web.api.ApiMethodInvokeObject;

public class InvokeObjectView extends InvokeObjectSimpleView {

	private String apisMockTag;
	private List<Param> requestParams;
	private List<Param> responseParams;

	public String getApisMockTag() {
		return apisMockTag;
	}

	public void setApisMockTag(String apisMockTag) {
		this.apisMockTag = apisMockTag;
	}

	public List<Param> getRequestParams() {
		return requestParams;
	}

	public void setRequestParams(List<Param> requestParams) {
		this.requestParams = requestParams;
	}

	public List<Param> getResponseParams() {
		return responseParams;
	}

	public void setResponseParams(List<Param> responseParams) {
		this.responseParams = responseParams;
	}

	public InvokeObjectView(String methodName, String description, String group,
			ApiMethodInvokeObject apiMethodInvokeObject) {
		super(methodName, description, group, apiMethodInvokeObject.getMethod());
		buidApisMockTag();
		buildRequestOptions(apiMethodInvokeObject);
		buildResponseOptions(apiMethodInvokeObject);
	}
	
	private void buidApisMockTag() {
		PropertiesUtil properties = new PropertiesUtil("/application.properties");
		this.apisMockTag = properties.getProperty("apis.mock.tag") != null
				&& "true".equals(properties.getProperty("apis.mock.tag")) ? "true" : "";
	}

	private void buildResponseOptions(ApiMethodInvokeObject apiMethodInvokeObject) {
		if (ClassUtil.isSimpaleType(apiMethodInvokeObject.getMethod().getReturnType()))
			return;
		Type responseType = apiMethodInvokeObject.getMethod().getGenericReturnType();
		this.responseParams = ParamBuilder.buildApiParams(responseType);
	}

	private void buildRequestOptions(ApiMethodInvokeObject apiMethodInvokeObject) {
		if (apiMethodInvokeObject.getMethod().getGenericParameterTypes().length == 0)
			return;
		if (ClassUtil.isSimpaleType(apiMethodInvokeObject.getMethod().getParameterTypes()[0]))
			return;

		Type requestType = apiMethodInvokeObject.getMethod().getGenericParameterTypes()[0];
		this.requestParams = ParamBuilder.buildApiParams(requestType);
	}

}
