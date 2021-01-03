package common.core.web.api;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import common.core.app.dao.DaoException;
import common.core.app.exception.ControllerException;
import common.core.app.exception.FieldValidationException;
import common.core.app.exception.ServiceException;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.exception.ErrorAssertionException;
import common.core.common.assertion.exception.InfoAssertionException;
import common.core.common.assertion.exception.WarnAssertionException;
import common.core.common.assertion.util.AssertWarnUtils;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.ClassUtil;
import common.core.common.util.ObjectUtil;
import common.core.common.util.StringUtil;
import common.core.common.util.UuidUtil;
import common.core.site.controller.BaseController;
import common.core.site.handler.PreHandler;
import common.core.web.api.exception.ApiMatchException;
import common.core.web.api.interceptor.ApiInterceptor;
import common.core.web.api.view.api.method.ApiMethodInvokeObjectContext;
import common.core.web.api.view.views.ApiResponsePlugin;
import common.core.web.exception.InfoMessageException;
import common.core.web.exception.RedirectException;
import common.core.web.exception.WarnMessageException;
import common.core.web.queue.QueueRequiredException;

public class BaseApiController extends BaseController implements PreHandler {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	private ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

	private ApiSignature apiSignature = new ApiSignature();

	private List<ApiInterceptor> apiInterceptors;

	/**
	 * api请求入口
	 * 
	 * @param apiRequest
	 *            ApiRequest
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @return ApiResponse
	 */
	protected ApiResponse execute(ApiRequest apiRequest, HttpServletRequest httpServletRequest) {
		ApiRequestContext.set(apiRequest);
		try {
			logger.debug("api request:{}", apiRequest.toString());
			// 验签
			apiSignature.verify(apiRequest);
			apiRequest.setIp(httpServletRequest.getRemoteAddr());
			ApiMethodInvokeObject methodInvokeObject = getApiMethodInvokeObject(apiRequest.getMethod());
			AssertWarnUtils.assertNotNull(methodInvokeObject, "method [{}] not found!", apiRequest.getMethod());
			AssertWarnUtils.assertTrue(methodInvokeObject.getMethod().getParameterCount() <= 1, "parameter count of method [{}] must Less than or equal to 1 ", methodInvokeObject.getMethod().getName());

			Object parameter = buildParameter(apiRequest, methodInvokeObject.getMethod());
			preHandle(methodInvokeObject, parameter, apiRequest);
			validateParameter(parameter);
			validateApiMethodInvokeObject(methodInvokeObject);
			Object responseData = buildResponseData(methodInvokeObject, parameter);
			afterHandle(methodInvokeObject, parameter, apiRequest, responseData);
			ApiResponse apiResponse = buildApiResponse(responseData);
			logger.debug("api response:{}", apiResponse.toString());
			return apiResponse;
		} finally {
			ApiRequestContext.clean();
		}
	}

	/**
	 * 校验ApiMethodInvokeObject信息
	 * 
	 * @param methodInvokeObject
	 *            ApiMethodInvokeObject
	 */
	protected void validateApiMethodInvokeObject(ApiMethodInvokeObject methodInvokeObject) {
		if (!ApiRequestContext.match(methodInvokeObject.getMethodAnnotation().match())) {
			throw new ApiMatchException("版本不支持");
		}
	}

	protected ApiMethodInvokeObject getApiMethodInvokeObject(String method) {
		Map<String, ApiMethodInvokeObject> methodMap = ApiMethodInvokeObjectContext.getMethodInvokeObjectMap();
		return methodMap.get(method);
	}

	protected void validateParameter(Object parameter) {
		if (null == parameter)
			return;
		Validator validator = this.getValidatorFactory().getValidator();
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(parameter);
		if (constraintViolations.isEmpty())
			return;
		FieldValidationException fieldValidationException = new FieldValidationException();
		for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
			fieldValidationException.addFieldError(new common.core.app.exception.FieldError(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage()));
		}
		throw fieldValidationException;

	}

	private void preHandle(MethodInvokeObject methodInvokeObject, Object paramObject, ApiRequest apiRequest) {
		List<ApiInterceptor> apiInterceptors = getApiInterceptors();
		ApiInterceptor apiInterceptor = null;
		for (int i = 0; i < apiInterceptors.size(); i++) {
			apiInterceptor = apiInterceptors.get(i);
			logger.debug("apiInterceptor preHandle {}", apiInterceptor);
			apiInterceptor.preHandle(methodInvokeObject, paramObject, apiRequest);
		}
	}

	private void afterHandle(MethodInvokeObject methodInvokeObject, Object paramObject, ApiRequest apiRequest, Object responseObject) {
		List<ApiInterceptor> apiInterceptors = getApiInterceptors();
		ApiInterceptor apiInterceptor = null;
		for (int i = 0; i < apiInterceptors.size(); i++) {
			apiInterceptor = apiInterceptors.get(apiInterceptors.size() - i - 1);
			logger.debug("apiInterceptor afterHandle {}", apiInterceptor);
			apiInterceptor.afterHandle(methodInvokeObject, paramObject, apiRequest, responseObject);
		}
	}

	private List<ApiInterceptor> getApiInterceptors() {
		if (null == apiInterceptors) {
			apiInterceptors = ApplicationContextUtil.getBeans(ApiInterceptor.class);
		}
		return apiInterceptors;
	}

	/**
	 * 文件上传
	 * 
	 * @param apiRequest
	 *            ApiRequest
	 * @param file
	 *            ApiRequest
	 * @param httpServletRequest
	 *            HttpServletRequest
	 * @return
	 */
	protected ApiResponse upload(ApiRequest apiRequest, @RequestParam(value = "file", required = false) MultipartFile file, HttpServletRequest httpServletRequest) {
		MultipartFileContext.initContext(file);
		try {
			ApiResponse apiResponse = execute(apiRequest, httpServletRequest);
			return apiResponse;
		} finally {
			MultipartFileContext.cleanContext();
		}

	}

	private void signResponse(ApiResponse apiResponse) {
		apiSignature.sign(apiResponse);
	}

	private String buidResponseString(Object responseData) {
		String responseString = null;
		if (null != responseData) {
			if (ClassUtil.isSimpaleType(responseData.getClass())) {
				responseString = responseData.toString();
			} else {
				responseString = ObjectUtil.toJson(responseData);
			}
		}
		return responseString;
	}

	private ApiResponse buildApiResponse(Object responseData) {
		String code = "success";
		ApiResponse apiResponse = new ApiResponse();
		apiResponse.setTimestamp(System.currentTimeMillis());
		apiResponse.setUuid(UuidUtil.generateFullUuid());
		String responseString = buidResponseString(responseData);
		apiResponse.setData(responseString);
		if (null != responseData && null != responseData.getClass().getAnnotation(ApiResponsePlugin.class)) {
			code = PluginProtocols.RENDER_PLUGIN + responseData.getClass().getSimpleName();
		}
		apiResponse.setCode(code);
		signResponse(apiResponse);
		return apiResponse;
	}

	private Object buildResponseData(MethodInvokeObject methodInvokeObject, Object parameter) {
		try {
			if (parameter == null) {
				return methodInvokeObject.getMethod().invoke(methodInvokeObject.getInvokeObject());
			} else {
				return methodInvokeObject.getMethod().invoke(methodInvokeObject.getInvokeObject(), parameter);
			}
		} catch (InvocationTargetException e) {
			fixException(e.getCause() == null ? e : e.getCause());
		} catch (IllegalAccessException e) {
			fixException(e.getCause() == null ? e : e.getCause());
		} catch (IllegalArgumentException e) {
			fixException(e.getCause() == null ? e : e.getCause());
		} catch (Exception e) {
			fixException(e);
		}
		return null;
	}

	private void fixException(Throwable e) {
		logger.error(e.getMessage(), e);
		throw new ApiException(ApiException.RUNTIME_EXCEPITON, e.getMessage());
	}

	private Object buildParameter(ApiRequest apiRequest, Method method) {
		Object parameter = null;
		if (method.getParameterCount() == 1) {
			String jsonData = apiRequest.getData();
			if (StringUtil.isBlank(jsonData)) {
				// throw new ApiException(ApiException.PARAM_IS_NOT_EMPTY,
				// "data不能为空");
				try {
					parameter = method.getParameters()[0].getType().newInstance();
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} else {
				parameter = ObjectUtil.fromJson(method.getParameterTypes()[0], jsonData);
			}
		} else if (method.getParameterCount() > 1) {
			logger.warn("api method check  method.getParameterCount() > 1", apiRequest.toString());
		}
		return parameter;
	}

	protected List<ApiMethodInvokeObject> getAllMethodInvokeObjectMap() {
		Map<String, ApiMethodInvokeObject> maps = ApiMethodInvokeObjectContext.getMethodInvokeObjectMap();
		if (null == maps)
			return null;
		return new ArrayList<ApiMethodInvokeObject>(maps.values());
	}

	@Override
	public void preHandle() {

	}

	private ApiResponse buildApiExceptionResponse(ApiException apiException) {
		ApiResponse apiResponse = buildApiResponse(null);
		apiResponse.setCode(apiException.getCode());
		apiResponse.setMessage(apiException.getMessage());
		signResponse(apiResponse);
		return apiResponse;
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(Throwable exception) {
		logger.error(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object dataAccessException(ApiException exception) {
		logger.error(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(ServiceException exception) {
		logger.error(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(InfoAssertionException exception) {
		logger.info(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(WarnAssertionException exception) {
		logger.info(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object apiMatchException(ApiMatchException apiMatchException) {
		logger.info(apiMatchException.getMessage(), apiMatchException);
		return buildApiExceptionResponse(new ApiException(apiMatchException.getClass().getSimpleName(), apiMatchException.getMessage()));

	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(ErrorAssertionException exception) {
		logger.error(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(ControllerException exception) {
		logger.error(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(DaoException exception) {
		logger.error(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(QueueRequiredException exception) {
		logger.info(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(InfoMessageException exception) {
		logger.info(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object exception(WarnMessageException exception) {
		logger.info(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}


	@ExceptionHandler
	@ResponseBody
	public Object exception(RedirectException exception) {
		logger.info(exception.getMessage(), exception);
		return buildApiExceptionResponse(new ApiException(exception.getClass().getSimpleName(), exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object bindException(BindException exception) {
		logger.info(exception.getMessage(), exception);
		if (null != exception.getFieldErrors() && exception.getFieldErrors().size() > 0) {
			for (FieldError fieldError : exception.getFieldErrors()) {
				return buildApiExceptionResponse(new ApiException(ApiException.PARAM_VALIDATOR_ERROR, StringUtil.format(fieldError.getDefaultMessage())));
			}
		}
		return buildApiExceptionResponse(new ApiException(ApiException.PARAM_VALIDATOR_ERROR, exception.getMessage()));
	}

	@ExceptionHandler
	@ResponseBody
	public Object fieldValidationException(FieldValidationException exception) {
		logger.info(exception.getMessage(), exception);
		if (null != exception.getFieldErrors() && exception.getFieldErrors().size() > 0) {
			for (common.core.app.exception.FieldError fieldError : exception.getFieldErrors()) {
				return buildApiExceptionResponse(new ApiException(ApiException.PARAM_VALIDATOR_ERROR, StringUtil.format(fieldError.getMessage())));
			}
		}
		return buildApiExceptionResponse(new ApiException(ApiException.PARAM_VALIDATOR_ERROR, exception.getMessage()));
	}

	public ValidatorFactory getValidatorFactory() {
		return validatorFactory;
	}

	public void setValidatorFactory(ValidatorFactory validatorFactory) {
		this.validatorFactory = validatorFactory;
	}

}
