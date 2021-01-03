package common.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.site.controller.AfterCompletion;
import common.core.site.handler.PreHandler;

public class HandlerInterceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(HandlerInterceptor.class);

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		super.postHandle(request, response, handler, modelAndView);
		if (handler instanceof HandlerMethod) {
			Object action = ((HandlerMethod) handler).getBean();
			if (action instanceof AfterCompletion) {
				logger.debug("afterCompletion action={}", action);
				((AfterCompletion) action).afterCompletion();
			}
		}
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (handler instanceof HandlerMethod) {
			Object action = ((HandlerMethod) handler).getBean();
			if (action instanceof PreHandler) {
				logger.debug("PreHandle action={}", action);
				((PreHandler) action).preHandle();
			}
		}
		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		super.afterCompletion(request, response, handler, ex);

	}

}
