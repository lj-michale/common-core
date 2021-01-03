/*package common.core.web.monitor.controller;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import common.core.common.util.ClassUtil;
import common.core.common.util.StringUtil;
import common.core.web.api.view.api.method.ApiMethodInvokeObjectViewContext;
import common.core.web.api.view.method.InvokeObjectView;
import common.core.web.api.view.method.Option;
import common.core.web.api.view.method.Param;

//@RestController
public class MockController {

//	@ResponseBody
//	@RequestMapping(value = "/system-admin/mock/result", method = { RequestMethod.POST, RequestMethod.GET })
	public Object mockToResult(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 原请求路径
		Object originalPath = request.getAttribute("originalPath");
		if (StringUtil.isEmpty(originalPath)) {
			return null;
		}
		Map<String, Object> resultMap = new LinkedHashMap<>();
		String mapping = String.valueOf(originalPath);
		InvokeObjectView result = ApiMethodInvokeObjectViewContext.getApiMethodInvokeObjectView(mapping);
		if (result == null || result.getResponseParams() == null || result.getResponseParams().size() == 0) {
			return resultMap;
		}

		// 组装参数
		List<Param> params = result.getResponseParams();
		Param iterableElement = params.get(0);
		buildApisParamsResponse(params, resultMap, iterableElement);
		return resultMap;
	}

	private void buildApisParamsResponse(List<Param> params, Map<String, Object> resultMap, Param iterableElement) {
		if (iterableElement == null) {
			return;
		}
		for (int i = 0; i < iterableElement.getOptions().size(); i++) {
			
			Option option = iterableElement.getOptions().get(i);
			if (ClassUtil.isSimpaleType(option.getType())) {
				resultMap.put(option.getName(), option.getDemoValue());
				continue;
			}
			// 组装common.core.web.api.DataApiResponse参数
			if (iterableElement.getType().getTypeName().indexOf("common.core.web.api.DataApiResponse") != -1) {
				if ("data".equals(option.getName()) && "T".equals(option.getType().getTypeName())) {
					Map<String, Object> dataResponse = new LinkedHashMap<>();
					buildApisParamsResponse(params, dataResponse, getNextParam(params, iterableElement.getType()));
					resultMap.put(option.getName(), dataResponse);
				}
				continue;
			}
			// 组装common.core.web.api.ApiPageResult参数
			if (iterableElement.getType().getTypeName().indexOf("common.core.web.api.ApiPageResult") != -1 || 
					iterableElement.getType().getTypeName().indexOf("common.core.app.dao.PageResult") != -1) {
				if (option.getType() instanceof ParameterizedType) {
					List<Map<String, Object>> pageList = new ArrayList<>();
					Map<String, Object> pageResponse = new LinkedHashMap<>();
					buildApisParamsResponse(params, pageResponse, getNextParam(params, iterableElement.getType()));
					pageList.add(pageResponse);
					resultMap.put(option.getName(), pageList);
				}
				continue;
			}
			
			Param param = getCurrentParam(params, option.getType());
			if (param != null) {
				Map<String, Object> paramResponse = new LinkedHashMap<>();
				buildApisParamsResponse(params, paramResponse, param);
				// 判断是否是泛型
				if (option.getType() instanceof ParameterizedType) {
					List<Map<String, Object>> paramList = new ArrayList<>();
					paramList.add(paramResponse);
					resultMap.put(option.getName(), paramList);
				} else {
					resultMap.put(option.getName(), paramResponse);
				}
			}
		}
	}

	private Param getCurrentParam(List<Param> params, Type type) {
		Param param = null;
		for (int j = 0; j < params.size(); j++) {
			Param paramItem = params.get(j);
			if (type.getTypeName().indexOf(paramItem.getType().getTypeName()) != -1) {
				param = paramItem;
				break;
			}
		}
		return param;
	}
	
	private Param getNextParam(List<Param> params, Type type) {
		Param param = null;
		for (int j = 0; j < params.size(); j++) {
			Param paramItem = params.get(j);
			if (type.getTypeName().indexOf(paramItem.getType().getTypeName()) != -1) {
				if((j+1) <= params.size()){
					param = params.get(j+1);
				}
				break;
			}
		}
		return param;
	}

}
*/