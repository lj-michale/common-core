package common.core.site.scheme;

import java.lang.annotation.Annotation;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import common.core.app.exception.NotSupportedException;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.assertion.util.AssertErrorUtils;
import common.core.common.http.HTTPConstants;
import common.core.common.util.UrlUtil;
import common.core.web.context.RequestContext;
import common.core.web.context.WebSetting;

public class SchemeEnforceInterceptor extends HandlerInterceptorAdapter {
	private final Logger logger = LoggerFactory.getLogger(SchemeEnforceInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		if (!(handler instanceof HandlerMethod)) {
			return true;
		}

		HttpScheme httpScheme = this.findAnnotation((HandlerMethod) handler, HttpScheme.class);
		if (null == httpScheme) {
			return true;
		}
		AssertErrorUtils.assertNotNull(httpScheme.value(), "Please set HttpScheme value for {}", handler.getClass().getName());
		Scheme currentScheme = Scheme.HTTP;
		if (RequestContext.isSecure()) {
			currentScheme = Scheme.HTTPS;
		}
		if (!httpScheme.value().equals(currentScheme)) {
			enforceScheme(request, response, httpScheme);
			return false;
		}
		
		// 检查是否配置http和https端口
		if (!RequestContext.isSecure() && WebSetting.get().getHttpPort() == 0) {
			throw new NotSupportedException("Not support http, Please config [web.httpPort] on web.properties");
		}
		if (RequestContext.isSecure() && WebSetting.get().getHttpsPort() == 0) {
			throw new NotSupportedException("Not support https, Please config [web.httpsPort] on web.properties");
		}

		return true;
	}

	private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
		T annotation = handler.getBeanType().getAnnotation(annotationType);
		if (annotation != null)
			return annotation;
		return handler.getMethodAnnotation(annotationType);
	}

	private void enforceScheme(HttpServletRequest request, HttpServletResponse response, HttpScheme scheme) {
		if ("POST".equalsIgnoreCase(RequestContext.getMethod())) {
			response.setStatus(HTTPConstants.SC_NOT_ACCEPTABLE);
			logger.warn("scheme error: {}", RequestContext.getRequestURL().toString());
			return;
		}
		int port = Scheme.HTTPS.equals(scheme.value()) ? WebSetting.get().getHttpsPort() : WebSetting.get().getHttpPort();
		StringBuffer buffString = new StringBuffer(Scheme.HTTPS.equals(scheme.value()) ? "https://" : "http://");
		buffString.append(StringUtils.hasText(WebSetting.get().getServerName()) ? WebSetting.get().getServerName() : RequestContext.getServerName());
		if (port != 80 && port != 443) {
			buffString.append(":").append(port);
		}
		buffString.append(RequestContext.getRequestURI());
		String redirectURL = buffString.toString();
		redirectURL = UrlUtil.appendUrl(redirectURL, RequestContext.getParameterMap());
		logger.debug("redirect to different scheme, redirectURL={}", redirectURL);

		response.setStatus(HTTPConstants.SC_MOVED_PERMANENTLY);
		response.setHeader(HTTPConstants.HEADER_REDIRECT_LOCATION, redirectURL);
	}
}
