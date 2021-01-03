package common.core.web.interceptor;

import java.lang.reflect.Method;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.core.app.log.LogConstants;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.log.TraceLogger;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.site.controller.BaseRestController;
import common.core.site.controller.BaseViewController;
import common.core.site.cookie.CookieSpec;
import common.core.site.resolver.ExceptionHandler;
import common.core.site.view.BaseRestResult;

public class ActionInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(ActionInterceptor.class);
	private final static String REQUEST_INIT_NAME = ActionInterceptor.class.getName();
	public final static CookieSpec COOKIE_UUID = CookieSpec.build("u_id").httpOnly(false).maxAge(10 * 365 * 24 * 60 * 60);
	private static final String START_TIME_NAME = "ActionInterceptor.START_TIME_NAME";

	@Inject
	private ExceptionHandler exceptionHandler;

	public ActionInterceptor() {
		super();
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
		logger.debug("ActionInterceptor.postHandle handler={}", handler);
		super.postHandle(request, response, handler, modelAndView);

	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (null == request.getAttribute(START_TIME_NAME)) {
			request.setAttribute(START_TIME_NAME, System.currentTimeMillis());
		}
		if (request.getAttribute(ActionInterceptor.REQUEST_INIT_NAME) == null) {
			request.setAttribute(ActionInterceptor.REQUEST_INIT_NAME, true);
			if (handler instanceof HandlerMethod) {
				HandlerMethod mandlerMethod = (HandlerMethod) handler;
				TraceLogger.putContext(LogConstants.MDC_ACTION, mandlerMethod.getBeanType().getSimpleName());
				TraceLogger.putContext(LogConstants.MDC_ACTION_METHOD, mandlerMethod.getMethod().getName());
				request.setAttribute(LogConstants.MDC_ACTION_METHOD, mandlerMethod.getMethod());

				// check BaseRestController
				if (mandlerMethod.getBean() instanceof BaseRestController) {
					AssertErrorUtils.assertTrue(BaseRestResult.class.isAssignableFrom(mandlerMethod.getMethod().getReturnType()) || Void.class.equals(mandlerMethod.getMethod().getReturnType()) || mandlerMethod.getMethod().getReturnType() == Void.TYPE,
							"BaseRestController check: {} must return BaseRestResult or void", mandlerMethod.getMethod());
				}

				// check BaseViewController
				if (mandlerMethod.getBean() instanceof BaseViewController) {
					AssertErrorUtils.assertTrue(String.class.equals(mandlerMethod.getMethod().getReturnType()) || Void.class.equals(mandlerMethod.getMethod().getReturnType()) || mandlerMethod.getMethod().getReturnType() == Void.TYPE, "BaseViewController check: {} must return string or void",
							mandlerMethod.getMethod());
				}
			}
		}

		return super.preHandle(request, response, handler);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
		logger.debug("ActionInterceptor.afterCompletion handler={}", handler);
		if (null != ex)
			this.exceptionHandler.handler(ex);
		super.afterCompletion(request, response, handler, ex);

		long times = System.currentTimeMillis() - (Long) request.getAttribute(START_TIME_NAME);
		if (TraceLogger.isTimeOut(times, (Method) request.getAttribute(LogConstants.MDC_ACTION_METHOD))) {
			logger.warn("action time out, times={},please check it", times);
		}
	}

}
