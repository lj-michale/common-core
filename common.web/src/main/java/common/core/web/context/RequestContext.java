package common.core.web.context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.util.StringUtils;

import common.core.app.context.ConfigContext;
import common.core.common.util.StringUtil;
import common.core.common.util.UrlUtil;
import common.core.common.util.UuidUtil;
import common.core.site.cookie.CookieDevice;
import common.core.site.session.SessionContext;

public class RequestContext {
	public final static String REQUEST_ID = "_requestId";
	public static final String HEADER_X_FORWARDED_PROTO = "x-forwarded-proto";
	private final static ThreadLocal<HttpServletRequest> request = new ThreadLocal<>();

	public final static String METHOD_GET = "GET";
	public final static String METHOD_POST = "POST";

	public static boolean isPermissiveOrInternalIp() {
		String ip = RequestContext.getRemoteAddr();
		if (!StringUtils.hasText(ip))
			return true;
		// 获取ip白名单
		String whiteList = ConfigContext.getStringValue("web.intranet.captcha.whitelist", null);
		if (whiteList != null && whiteList.contains(ip)) {
			return true;
		}
		return ip.startsWith("10.") || ip.startsWith("127.") || ip.startsWith("172.") || ip.startsWith("192.") || ip.equals("121.15.129.253") || ip.equals("14.153.26.231");
	}

	public static void initContext(HttpServletRequest httpServletRequest) {
		request.set(httpServletRequest);
	}

	public static void cleanContext() {
		request.remove();
	}

	public static HttpServletRequest get() {
		return request.get();
	}

	public static String getRequestId() {
		String requestId = (String) RequestContext.getAttribute(RequestContext.REQUEST_ID);
		if (StringUtils.hasText(requestId))
			return requestId;
		requestId = RequestContext.getParameter(RequestContext.REQUEST_ID);
		if (!StringUtils.hasText(requestId))
			requestId = RequestContext.getHeader(RequestContext.REQUEST_ID);
		if (!StringUtils.hasText(requestId))
			requestId = UuidUtil.generateUuidWithoutLine();
		RequestContext.setAttribute(RequestContext.REQUEST_ID, requestId);
		return requestId;
	}

	public static Object getAttribute(String name) {
		return RequestContext.get().getAttribute(name);
	}

	public static String getAuthType() {
		return RequestContext.get().getAuthType();
	}

	public static void putCookiesToMap(Map<String, String> requestCookieMap) {
		Cookie[] cookies = RequestContext.get().getCookies();
		if (null == cookies)
			return;
		for (Cookie cookie : cookies) {
			requestCookieMap.put(cookie.getName(), cookie.getValue());
		}
	}

	public static String getDomain() {
		String serverName = WebSetting.get().getServerName();
		if (StringUtils.isEmpty(serverName)) {
			serverName = RequestContext.getServerName();
		}
		return serverName;
	}

	public static Enumeration<String> getAttributeNames() {
		return RequestContext.get().getAttributeNames();
	}

	public static long getDateHeader(String name) {
		return RequestContext.get().getDateHeader(name);
	}

	public static String getCharacterEncoding() {
		return RequestContext.get().getCharacterEncoding();
	}

	public static void setCharacterEncoding(String env) throws UnsupportedEncodingException {
		RequestContext.get().setCharacterEncoding(env);
	}

	public static String getHeader(String name) {
		return RequestContext.get().getHeader(name);
	}

	public static int getContentLength() {
		return RequestContext.get().getContentLength();
	}

	public static String getContentType() {
		return RequestContext.get().getContentType();
	}

	public static Enumeration<String> getHeaders(String name) {
		return RequestContext.get().getHeaders(name);
	}

	public static ServletInputStream getInputStream() throws IOException {
		return RequestContext.get().getInputStream();
	}

	public static String getParameter(String name) {
		return RequestContext.get().getParameter(name);
	}

	public static Enumeration<String> getHeaderNames() {
		return RequestContext.get().getHeaderNames();
	}

	public static int getIntHeader(String name) {
		return RequestContext.get().getIntHeader(name);
	}

	public static Enumeration<String> getParameterNames() {
		return RequestContext.get().getParameterNames();
	}

	public static String[] getParameterValues(String name) {
		return RequestContext.get().getParameterValues(name);
	}

	public static String getMethod() {
		return RequestContext.get().getMethod();
	}

	public static String getPathInfo() {
		return RequestContext.get().getPathInfo();
	}

	public static Map<String, String[]> getParameterValuesMap() {
		return RequestContext.get().getParameterMap();
	}

	public static Map<String, String> getParameterMap() {
		Map<String, String> paramMap = new java.util.HashMap<String, String>();
		Enumeration<String> names = RequestContext.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			paramMap.put(name, RequestContext.getParameter(name));
		}
		return paramMap;
	}

	public static String getProtocol() {
		return RequestContext.get().getProtocol();
	}

	public static String getPathTranslated() {
		return RequestContext.get().getPathTranslated();
	}

	public static String getScheme() {
		return RequestContext.get().getScheme();
	}

	public static String getServerName() {
		return RequestContext.get().getServerName();
	}

	public static String getContextPath() {
		return RequestContext.get().getContextPath();
	}

	public static int getServerPort() {
		return RequestContext.get().getServerPort();
	}

	public static BufferedReader getReader() throws IOException {
		return RequestContext.get().getReader();
	}

	public static String getQueryString() {
		return RequestContext.get().getQueryString();
	}

	public static String getRemoteAddr() {
		return null == RequestContext.get() ? null : RequestContext.get().getRemoteAddr();
	}

	public static String getRemoteUser() {
		return RequestContext.get().getRemoteUser();
	}

	public static boolean isUserInRole(String role) {
		return RequestContext.get().isUserInRole(role);
	}

	public static void setAttribute(String name, Object o) {
		RequestContext.get().setAttribute(name, o);
	}

	public static Principal getUserPrincipal() {
		return RequestContext.get().getUserPrincipal();
	}

	public static String getRequestedSessionId() {
		return SessionContext.getSessionId();
	}

	public static void removeAttribute(String name) {
		RequestContext.get().removeAttribute(name);
	}

	public static String getRequestURI() {
		return RequestContext.get().getRequestURI();
	}

	public static Locale getLocale() {
		return RequestContext.get().getLocale();
	}

	public static Enumeration<Locale> getLocales() {
		return RequestContext.get().getLocales();
	}

	public static StringBuffer getRequestURL() {
		return RequestContext.get().getRequestURL();
	}

	public static boolean isSecure() {
		return RequestContext.get().isSecure();
	}

	public static RequestDispatcher getRequestDispatcher(String path) {
		return RequestContext.get().getRequestDispatcher(path);
	}

	public static String getServletPath() {
		return RequestContext.get().getServletPath();
	}

	public static HttpSession getSession(boolean create) {
		return RequestContext.get().getSession(create);
	}

	/*
	 * @deprecated
	 * 
	 * @see javax.servlet.ServletRequest#getRealPath(java.lang.String)
	 */
	@SuppressWarnings("deprecation")
	public static String getRealPath(String path) {
		return RequestContext.get().getRealPath(path);
	}

	public static int getRemotePort() {
		return RequestContext.get().getRemotePort();
	}

	public static String getLocalName() {
		return RequestContext.get().getLocalName();
	}

	public static String getLocalAddr() {
		return RequestContext.get().getLocalAddr();
	}

	public static HttpSession getSession() {
		return SessionContext.get();
	}

	public static int getLocalPort() {
		return RequestContext.get().getLocalPort();
	}

	public static ServletContext getServletContext() {
		return RequestContext.get().getServletContext();
	}

	public static boolean isRequestedSessionIdValid() {
		return RequestContext.get().isRequestedSessionIdValid();
	}

	public static AsyncContext startAsync() throws IllegalStateException {
		return RequestContext.get().startAsync();
	}

	public static boolean isRequestedSessionIdFromCookie() {
		return RequestContext.get().isRequestedSessionIdFromCookie();
	}

	public static boolean isRequestedSessionIdFromURL() {
		return RequestContext.get().isRequestedSessionIdFromURL();
	}

	/*
	 * @deprecated
	 * 
	 * @see javax.servlet.http.HttpServletRequest#isRequestedSessionIdFromUrl()
	 */
	@SuppressWarnings("deprecation")
	public static boolean isRequestedSessionIdFromUrl() {
		return RequestContext.get().isRequestedSessionIdFromUrl();
	}

	public static boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		return RequestContext.get().authenticate(response);
	}

	public static AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse) throws IllegalStateException {
		return RequestContext.get().startAsync(servletRequest, servletResponse);
	}

	public static void login(String username, String password) throws ServletException {
		RequestContext.get().login(username, password);
	}

	public static void logout() throws ServletException {
		RequestContext.get().logout();
	}

	public static Collection<Part> getParts() throws IOException, ServletException {
		return RequestContext.get().getParts();
	}

	public static boolean isAsyncStarted() {
		return RequestContext.get().isAsyncStarted();
	}

	public static Part getPart(String name) throws IOException, ServletException {
		return RequestContext.get().getPart(name);
	}

	public static boolean isAsyncSupported() {
		return RequestContext.get().isAsyncSupported();
	}

	public static AsyncContext getAsyncContext() {
		return RequestContext.get().getAsyncContext();
	}

	public static DispatcherType getDispatcherType() {
		return RequestContext.get().getDispatcherType();
	}

	public static String getReferer() {
		return RequestContext.getHeader("Referer");
	}

	public static Device getRequestDevice() {
		Device device = (Device) RequestContext.getAttribute(DeviceUtils.CURRENT_DEVICE_ATTRIBUTE);
		if (null == device) {
			device = CookieDevice.NORMAL_INSTANCE;
		}
		return device;
	}

	public static String getRequestUrlWithQueryString() {
		StringBuffer url = new StringBuffer(RequestContext.getRequestURL().toString());
		String queryString = RequestContext.getQueryString();
		if (StringUtil.hasText(queryString)) {
			url.append("?").append(queryString);
		}
		return url.toString();
	}

	public static String getRequestFullServer() {
		String url = RequestContext.getRequestURL().toString();
		return UrlUtil.getRequestFullServer(url);
	}

	public static String buildRequestUrl(String url) {
		StringBuffer urlBuffer = new StringBuffer(RequestContext.getRequestFullServer());
		if (null == url) {
			return urlBuffer.toString();
		}
		if (UrlUtil.hasScheme(url)) {
			return url;
		}
		if (!url.startsWith("/")) {
			urlBuffer.append("/");
		}
		urlBuffer.append(url);
		return urlBuffer.toString();
	}

}
