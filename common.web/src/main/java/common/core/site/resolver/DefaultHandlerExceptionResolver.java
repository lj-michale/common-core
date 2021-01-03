package common.core.site.resolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;

import common.core.web.exception.RedirectException;

public class DefaultHandlerExceptionResolver extends org.springframework.web.servlet.mvc.support.DefaultHandlerExceptionResolver {
	private ExceptionHandler exceptionHandler = new DefaultExceptionHandler();

	@Override
	protected void logException(Exception ex, HttpServletRequest request) {
		exceptionHandler.handler(ex);
	}

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		ModelAndView modelAndView = super.doResolveException(request, response, handler, ex);
		if (null == modelAndView) {
			if (ex instanceof RedirectException) {
				return new ModelAndView("redirect:" + ((RedirectException) ex).getUrl());
			} /*
				 * else { ViewContext.put("exception", ex);
				 * ViewContext.put("exceptionMessage", ex.getMessage());
				 * ViewContext.put("exceptionStakTrace",
				 * ExceptionUtil.stackTrace(ex)); return new
				 * ModelAndView(WebSetting.get().getErrorView()); }
				 */
		}
		return modelAndView;
	}

}
