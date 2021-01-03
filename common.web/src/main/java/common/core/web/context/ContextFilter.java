/**
 * 2014年5月28日
 */
package common.core.web.context;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;

import common.core.app.log.LogConstants;
import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.app.log.TraceLogger;
import common.core.app.runtime.RuntimeSetting;
import common.core.common.util.ConvertUtil;
import common.core.common.util.UuidUtil;
import common.core.site.cookie.CookieContext;
import common.core.site.view.ViewContext;
import common.core.web.interceptor.ActionInterceptor;
import common.core.web.request.RequestWrapper;
import common.core.web.response.ResponseWrapper;

/**
 * @author zmuwang
 *
 */
public class ContextFilter implements Filter {
	private final Logger logger = LoggerFactory.getLogger(ContextFilter.class);
	private final static String SHOULD_INIT_CONTEXT = ContextFilter.class.getName();

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest httpServletRequest, ServletResponse httpServletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) httpServletRequest;
		HttpServletResponse response = (HttpServletResponse) httpServletResponse;
		boolean shouldInitContext = request.getAttribute(ContextFilter.SHOULD_INIT_CONTEXT) == null ? true : false;
		try {
			if (shouldInitContext) {
				request = new RequestWrapper(request);
				response = new ResponseWrapper(response);
				initContext(request, response);
			}
			chain.doFilter(request, response);
		} finally {
			if (shouldInitContext) {
				cleanContext(request);
			}
		}

	}

	private void cleanContext(HttpServletRequest request) {
		debugResponse();
		CookieContext.cleanContext();
		ResponseContext.cleanContext();
		RequestContext.cleanContext();
		ViewContext.cleanContext();
		logger.debug("cleanContext end");
		logger.debug("RequestContext & ResponseContext clean ...");
		logger.debug("uri end:{}", request.getRequestURI());
		TraceLogger.get().cleanup(ConvertUtil.toBoolean(request.getParameter(LogConstants.REQ_OPEN_TRACK), false));
	}

	private void initContext(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute(ContextFilter.SHOULD_INIT_CONTEXT, true);
		String requestId = this.initRequestId(request);
		TraceLogger.get().initialize();
		TraceLogger.putContext(LogConstants.MDC_REQUEST_ID, requestId);

		logger.debug("initContext start");
		ViewContext.initContext();
		RequestContext.initContext(request);
		ResponseContext.initContext(response);
		CookieContext.initContext();
		debugRequest();
		initResponseData(response);
	}

	@Override
	public void destroy() {

	}

	private void initResponseData(HttpServletResponse httpServletResponse) {
		try {
			InetAddress inetAddress = InetAddress.getLocalHost();
			String server = inetAddress.getHostName(); // 获取本地的主机名
			if (StringUtils.hasText(server))
				httpServletResponse.addHeader("VServer", server);
			ViewContext.put("_requestId", RequestContext.getRequestId());
			ViewContext.put("_permissiveOrInternalIp", RequestContext.isPermissiveOrInternalIp());
			ViewContext.put("_server", server);
			ViewContext.put("_domain", RequestContext.getDomain());
			ViewContext.put("_runtimeSetting", RuntimeSetting.get());

			if (StringUtils.isEmpty(ActionInterceptor.COOKIE_UUID.getValue())) {
				ActionInterceptor.COOKIE_UUID.setValue(UuidUtil.generateShortUuid(4));
			}

		} catch (UnknownHostException e) {
			logger.debug(e.getMessage());
		}
	}

	private String initRequestId(HttpServletRequest httpServletRequest) {
		String requestIdKey = RequestContext.REQUEST_ID;
		String requestId = (String) httpServletRequest.getAttribute(requestIdKey);
		if (StringUtils.hasText(requestId))
			return requestId;
		requestId = httpServletRequest.getParameter(requestIdKey);
		if (!StringUtils.hasText(requestId))
			requestId = httpServletRequest.getHeader(requestIdKey);
		if (!StringUtils.hasText(requestId))
			requestId = UuidUtil.generateUuidWithoutLine();
		httpServletRequest.setAttribute(requestIdKey, requestId);
		return requestId;
	}

	private void debugResponse() {
		Collection<String> names = ResponseContext.getHeaderNames();
		if (null == names)
			return;
		for (String name : names) {
			logger.debug("Response header {}:{}", name, ResponseContext.getHeader(name));
		}
	}

	private void debugRequest() {
		logger.debug("uri start:{}", RequestContext.getRequestURI());
		logger.debug("RemoteAddr:{}", RequestContext.getRemoteAddr());
		logger.debug("method:{}", RequestContext.getMethod());
		logger.debug("requestId:{}", RequestContext.getRequestId());
		logger.debug("url:{}", RequestContext.getRequestURL());
		logger.debug("LocalPort:{}", RequestContext.getLocalPort());
		logger.debug("RemotePort:{}", RequestContext.getRemotePort());
		logger.debug("Protocol:{}", RequestContext.getProtocol());
		Enumeration<String> headerNames = RequestContext.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String name = headerNames.nextElement();
			logger.debug("Header:{}={}", name, RequestContext.getHeader(name));
		}
		if ("GET".equalsIgnoreCase(RequestContext.getMethod())) {
			for (Map.Entry<String, String> item : RequestContext.getParameterMap().entrySet()) {
				logger.debug("param:{}={}", item.getKey(), item.getValue());
			}
		}

	}

}
