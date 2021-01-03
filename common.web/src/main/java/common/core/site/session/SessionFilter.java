package common.core.site.session;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.StringUtil;
import common.core.common.util.UuidUtil;
import common.core.web.context.WebSetting;

public class SessionFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(SessionFilter.class);

	final static public String COOKIE_SESSION_NAME = "_sid";
	private SessionProvider sessionProvider;
	final static public String REQUEST_FILTER = SessionFilter.class.getName();
	private CommonsMultipartResolver commonsMultipartResolver;
	private boolean initCommonsMultipartResolver = false;

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		if (null != request.getAttribute(REQUEST_FILTER)) {
			filterChain.doFilter(request, response);
			return;
		}
		request.setAttribute(REQUEST_FILTER, true);
		if (null != sessionProvider) {
			if (!initCommonsMultipartResolver) {
				commonsMultipartResolver = ApplicationContextUtil.getBean(CommonsMultipartResolver.class);
				initCommonsMultipartResolver = true;
			}
			if (null != commonsMultipartResolver && commonsMultipartResolver.isMultipart((HttpServletRequest) request)) {
				request = commonsMultipartResolver.resolveMultipart((HttpServletRequest) request);
			}
			String sessionId = getSessionId((HttpServletRequest) request, (HttpServletResponse) response);
			request = new SessionRequestWrapper(sessionId, (HttpServletRequest) request);
		} else {
			// update MaxInactiveInterval
			((HttpServletRequest) request).getSession().setMaxInactiveInterval(WebSetting.get().getSessionTimeOut());
		}
		try {
			SessionContext.initContext(((HttpServletRequest) request).getSession());
			filterChain.doFilter(request, response);
		} finally {
			SessionContext.cleanContext();
		}
	}

	private String getSessionId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String sessionId = null;
		Cookie sCookie = null;
		Cookie cookies[] = httpServletRequest.getCookies();
		// 如果是ApiRequest，则sessionId直接取token，否則取cookie中的sessionId。小程序获取header部的"WX_HEADER_TOKEN"来获取sessionId
		if (StringUtil.isNotBlank(httpServletRequest.getParameter("token")) && StringUtil.isNotBlank(httpServletRequest.getParameter("appId")) && StringUtil.isNotBlank(httpServletRequest.getParameter("method")) && StringUtil.isNotBlank(httpServletRequest.getParameter("uuid"))
				&& StringUtil.isNotBlank(httpServletRequest.getParameter("timestamp")) && StringUtil.isNotBlank(httpServletRequest.getParameter("userAgent"))) {
			sessionId = httpServletRequest.getParameter("token");
		} else if (cookies != null && cookies.length > 0) {
			for (int i = 0; i < cookies.length; i++) {
				sCookie = cookies[i];
				if (sCookie.getName().equals(SessionFilter.COOKIE_SESSION_NAME)) {
					sessionId = sCookie.getValue();
				}
			}
		}else if(StringUtil.isNotBlank(httpServletRequest.getHeader("token"))){
			sessionId = httpServletRequest.getHeader("token");
		}

		if (StringUtils.isEmpty(sessionId)) {
			sessionId = buildSessionId(httpServletRequest, httpServletResponse);
		}
		return sessionId;
	}

	private String buildSessionId(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
		String sessionId;
		sessionId = UuidUtil.generateShortUuid(4);
		String domain = httpServletRequest.getServerName();
		String contextPath = WebSetting.get().getContextPath();
		if (StringUtils.isEmpty(contextPath)) {
			contextPath = httpServletRequest.getContextPath();
		}
		if (StringUtils.isEmpty(contextPath)) {
			contextPath = "/";
		}

		Cookie sessionCookies = new Cookie(SessionFilter.COOKIE_SESSION_NAME, sessionId);
		sessionCookies.setMaxAge(-1);
		sessionCookies.setPath(contextPath);
		sessionCookies.setHttpOnly(true);
		sessionCookies.setDomain(domain);
		httpServletResponse.addCookie(sessionCookies);
		return sessionId;
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		String sessionProvider = WebSetting.get().getSessionProvider();
		if (!StringUtils.isEmpty(sessionProvider)) {
			try {
				ApplicationContextUtil.registerSingletonBean(SessionProvider.class.getName(), Class.forName(sessionProvider));
				this.sessionProvider = ApplicationContextUtil.getBean(SessionProvider.class);
			} catch (ClassNotFoundException e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

}
