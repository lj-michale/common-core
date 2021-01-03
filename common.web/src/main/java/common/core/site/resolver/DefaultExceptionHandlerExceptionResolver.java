package common.core.site.resolver;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.JsonViewResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;

import common.core.site.view.DefaultRequestResponseBodyMethodProcessor;

public class DefaultExceptionHandlerExceptionResolver extends ExceptionHandlerExceptionResolver {

	private ExceptionHandler exceptionHandler = new DefaultExceptionHandler();

	public DefaultExceptionHandlerExceptionResolver() {
		super();
		this.setPreventResponseCaching(true);
	}

	@Override
	public List<HandlerMethodReturnValueHandler> getCustomReturnValueHandlers() {
		return super.getCustomReturnValueHandlers();
	}

	@Override
	protected List<HandlerMethodReturnValueHandler> getDefaultReturnValueHandlers() {
		List<HandlerMethodReturnValueHandler> handlers = new ArrayList<HandlerMethodReturnValueHandler>();
		JsonViewResponseBodyAdvice jsonViewResponseBodyAdvice = new JsonViewResponseBodyAdvice();
		List<ResponseBodyAdvice<?>> interceptors = new ArrayList<ResponseBodyAdvice<?>>();
		interceptors.add(jsonViewResponseBodyAdvice);
		this.setResponseBodyAdvice(interceptors);
		List<Object> objs = new ArrayList<Object>();
		objs.add(jsonViewResponseBodyAdvice);
		handlers.add(new DefaultRequestResponseBodyMethodProcessor(getMessageConverters(), this.getContentNegotiationManager(), objs));
		handlers.addAll(super.getDefaultReturnValueHandlers());
		return handlers;
	}

	@Override
	protected ModelAndView doResolveHandlerMethodException(HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod, Exception exception) {
		ModelAndView modelAndView = super.doResolveHandlerMethodException(request, response, handlerMethod, exception);
		return modelAndView;
	}

	@Override
	protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod, Exception exception) {
		return super.getExceptionHandlerMethod(handlerMethod, exception);
	}

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		exceptionHandler.handler(ex);
	}

	@Override
	protected boolean shouldApplyTo(HttpServletRequest request, Object handler) {
		boolean result = super.shouldApplyTo(request, handler);
		return result;
	}

}
