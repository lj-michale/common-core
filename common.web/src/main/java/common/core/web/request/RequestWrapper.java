package common.core.web.request;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.springframework.util.StringUtils;

import common.core.web.context.RequestContext;

public class RequestWrapper extends HttpServletRequestWrapper {
	public static final String HEADER_X_FORWARDED_PROTO = "x-forwarded-proto";

	@Override
	public String getCharacterEncoding() {
		return super.getCharacterEncoding();
	}

	@Override
	public int getContentLength() {
		return super.getContentLength();
	}

	@Override
	public String getContentType() {
		return super.getContentType();
	}

	@Override
	public String getProtocol() {
		return super.getProtocol();
	}

	@Override
	public String getScheme() {
		String forwardedProtocol = RequestContext.getHeader("x-forwarded-proto");
		if (StringUtils.hasText(forwardedProtocol)) {
			return forwardedProtocol.toUpperCase();
		}
		forwardedProtocol = RequestContext.getHeader("FRONT-END-TYPE");
		if (StringUtils.hasText(forwardedProtocol)) {
			return forwardedProtocol.toUpperCase();
		}
		return super.getScheme();
	}

	@Override
	public String getServerName() {
		return super.getServerName();
	}

	@Override
	public boolean isSecure() {
		return "https".equalsIgnoreCase(getScheme()) || this.getLocalPort() == 443 || this.getLocalPort() == 8443;
	}

	@Override
	public int getServerPort() {
		return super.getServerPort();
	}

	@Override
	public String getRemoteAddr() {
		String ip = this.getHeader("x-forwarded-for");
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = this.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = this.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = super.getRemoteAddr();
		}
		if (StringUtils.hasText(ip)) {
			ip = ip.split(",")[0];
		}
		return ip;
	}

	@Override
	public String getRemoteHost() {
		return super.getRemoteHost();
	}

	@Override
	public Locale getLocale() {
		return super.getLocale();
	}

	@Override
	public Enumeration<Locale> getLocales() {
		return super.getLocales();
	}

	@Override
	public int getRemotePort() {
		return super.getRemotePort();
	}

	@Override
	public int getLocalPort() {
		return super.getLocalPort();
	}

	@Override
	public ServletContext getServletContext() {
		return super.getServletContext();
	}

	public RequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getAuthType() {
		return super.getAuthType();
	}

	@Override
	public Cookie[] getCookies() {
		return super.getCookies();
	}

	@Override
	public long getDateHeader(String name) {
		return super.getDateHeader(name);
	}

	@Override
	public String getHeader(String name) {
		return super.getHeader(name);
	}

	@Override
	public Enumeration<String> getHeaders(String name) {
		return super.getHeaders(name);
	}

	@Override
	public Enumeration<String> getHeaderNames() {
		return super.getHeaderNames();
	}

	@Override
	public int getIntHeader(String name) {
		return super.getIntHeader(name);
	}

	@Override
	public String getMethod() {
		String overrideMethod = getParameter("_method");
		if (overrideMethod != null)
			return overrideMethod;
		return super.getMethod();
	}

	@Override
	public String getPathInfo() {
		return super.getPathInfo();
	}

	@Override
	public String getPathTranslated() {
		return super.getPathTranslated();
	}

	@Override
	public String getContextPath() {
		return super.getContextPath();
	}

	@Override
	public String getQueryString() {
		return super.getQueryString();
	}

	@Override
	public String getRemoteUser() {
		return super.getRemoteUser();
	}

	@Override
	public boolean isUserInRole(String role) {
		return super.isUserInRole(role);
	}

	@Override
	public Principal getUserPrincipal() {
		return super.getUserPrincipal();
	}

	@Override
	public String getRequestedSessionId() {
		return super.getRequestedSessionId();
	}

	@Override
	public String getRequestURI() {
		return super.getRequestURI();
	}

	@Override
	public StringBuffer getRequestURL() {
		return super.getRequestURL();
	}

	@Override
	public String getServletPath() {
		return super.getServletPath();
	}

	@Override
	public HttpSession getSession(boolean create) {
		return super.getSession(create);
	}

	@Override
	public HttpSession getSession() {
		return super.getSession();
	}

	@Override
	public boolean isRequestedSessionIdValid() {
		return super.isRequestedSessionIdValid();
	}

	@Override
	public boolean isRequestedSessionIdFromCookie() {
		return super.isRequestedSessionIdFromCookie();
	}

	@Override
	public boolean isRequestedSessionIdFromURL() {
		return super.isRequestedSessionIdFromURL();
	}

	@Override
	public boolean isRequestedSessionIdFromUrl() {
		return super.isRequestedSessionIdFromUrl();
	}

	@Override
	public boolean authenticate(HttpServletResponse response) throws IOException, ServletException {
		return super.authenticate(response);
	}

	@Override
	public void login(String username, String password) throws ServletException {
		super.login(username, password);
	}

	@Override
	public void logout() throws ServletException {
		super.logout();
	}

	@Override
	public Collection<Part> getParts() throws IOException, ServletException {
		return super.getParts();
	}

	@Override
	public Part getPart(String name) throws IOException, ServletException {
		return super.getPart(name);
	}

}
