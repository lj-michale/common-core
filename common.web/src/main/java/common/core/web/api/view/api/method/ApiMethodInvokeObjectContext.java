package common.core.web.api.view.api.method;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.util.ApplicationContextUtil;
import common.core.web.api.ApiMethod;
import common.core.web.api.ApiMethodInvokeObject;

public class ApiMethodInvokeObjectContext {

	private static Map<String, ApiMethodInvokeObject> methodInvokeObjectMap = null;

	public static Map<String, ApiMethodInvokeObject> getMethodInvokeObjectMap() {
		if (null == methodInvokeObjectMap) {
			RequestMappingHandlerMapping requestMappingHandlerMapping = ApplicationContextUtil.getBean(RequestMappingHandlerMapping.class);
			if (null == requestMappingHandlerMapping)
				return null;
			Map<String, ApiMethodInvokeObject> maps = new ConcurrentHashMap<>();
			Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
			Set<Entry<RequestMappingInfo, HandlerMethod>> items = handlerMethods.entrySet();
			for (Entry<RequestMappingInfo, HandlerMethod> entry : items) {
				Method method = entry.getValue().getMethod();
				if (method.getDeclaringClass().isInterface())
					continue;
				ApiMethod apiMethod =entry.getValue().getMethodAnnotation(ApiMethod.class);
				if (null == apiMethod)
					continue;
				String key = apiMethod.name();
				if (!ApiMethod.DEFAULT.equals(key)) {
					AssertErrorUtils.assertNull(maps.put(key, new ApiMethodInvokeObject(method, ApplicationContextUtil.getBean(method.getDeclaringClass()), apiMethod)), "repetitive method [{}]", key);
				}
				RequestMapping requestMapping = entry.getValue().getMethodAnnotation(RequestMapping.class);
				if (null == requestMapping)
					continue;
				for (String value : requestMapping.value()) {
					maps.put(value, new ApiMethodInvokeObject(method, ApplicationContextUtil.getBean(method.getDeclaringClass()), apiMethod));
				}
				for (String value : requestMapping.path()) {
					maps.put(value, new ApiMethodInvokeObject(method, ApplicationContextUtil.getBean(method.getDeclaringClass()), apiMethod));
				}
			}
			methodInvokeObjectMap = maps;
		}
		return methodInvokeObjectMap;
	}
}
