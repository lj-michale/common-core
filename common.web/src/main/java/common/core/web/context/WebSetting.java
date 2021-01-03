package common.core.web.context;

import javax.xml.bind.annotation.XmlElement;

import common.core.app.context.ConfigContext;

public class WebSetting {

	private static WebSetting ME = new WebSetting();

	static {
		ConfigContext.bindConfigObject(ME);
	}

	@XmlElement(name = "web.httpPort", required = true)
	private int httpPort = 80;
	@XmlElement(name = "web.httpsPort", required = true)
	private int httpsPort = 443;
	@XmlElement(name = "web.contextPath", required = true)
	private String contextPath = "";
	@XmlElement(name = "web.serverName", required = true)
	private String serverName = "127.0.0.1";
	@XmlElement(name = "web.forceDevice", required = true)
	private String forceDevice;
	@XmlElement(name = "web.errorView", required = true)
	private String errorView = "/errorView";
	@XmlElement(name = "web.notFoundPageView", required = true)
	private String notFoundPageView;
	@XmlElement(name = "web.notAuthView", required = true)
	private String notAuthView;
	@XmlElement(name = "web.cdn.http", required = true)
	private String[] httpCdns;
	@XmlElement(name = "web.cdn.https", required = true)
	private String[] httpsCdns;
	@XmlElement(name = "web.cookie.domain", required = true)
	private String cookieDomian;
	@XmlElement(name = "web.sessionProvider")
	private String sessionProvider = null;
	@XmlElement(name = "web.sessionServers")
	private String sessionServers;
	@XmlElement(name = "web.sessionTimeOut")
	private int sessionTimeOut = 3600;
	@XmlElement(name = "web.homeUrl")
	private String homeUrl;

	public static WebSetting get() {
		return WebSetting.ME;
	}

	private WebSetting() {
		super();
	}

	public String getCookieDomian() {
		return cookieDomian;
	}

	public void setCookieDomian(String cookieDomian) {
		this.cookieDomian = cookieDomian;
	}

	public int getHttpPort() {
		return httpPort;
	}

	public void setHttpPort(int httpPort) {
		this.httpPort = httpPort;
	}

	public int getHttpsPort() {
		return httpsPort;
	}

	public void setHttpsPort(int httpsPort) {
		this.httpsPort = httpsPort;
	}

	public String getContextPath() {
		return contextPath;
	}

	public void setContextPath(String contextPath) {
		this.contextPath = contextPath;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getForceDevice() {
		return forceDevice;
	}

	public void setForceDevice(String forceDevice) {
		this.forceDevice = forceDevice;
	}

	public String getErrorView() {
		return errorView;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

	public String[] getHttpCdns() {
		return httpCdns;
	}

	public void setHttpCdns(String[] httpCdns) {
		this.httpCdns = httpCdns;
	}

	public String[] getHttpsCdns() {
		return httpsCdns;
	}

	public void setHttpsCdns(String[] httpsCdns) {
		this.httpsCdns = httpsCdns;
	}

	public String getSessionProvider() {
		return sessionProvider;
	}

	public void setSessionProvider(String sessionProvider) {
		this.sessionProvider = sessionProvider;
	}

	public String getSessionServers() {
		return sessionServers;
	}

	public void setSessionServers(String sessionServers) {
		this.sessionServers = sessionServers;
	}

	public int getSessionTimeOut() {
		return sessionTimeOut;
	}

	public void setSessionTimeOut(int sessionTimeOut) {
		this.sessionTimeOut = sessionTimeOut;
	}

	public String getNotFoundPageView() {
		return notFoundPageView;
	}

	public void setNotFoundPageView(String notFoundPageView) {
		this.notFoundPageView = notFoundPageView;
	}

	public String getNotAuthView() {
		return notAuthView;
	}

	public void setNotAuthView(String notAuthView) {
		this.notAuthView = notAuthView;
	}

	public String getHomeUrl() {
		return homeUrl;
	}

	public void setHomeUrl(String homeUrl) {
		this.homeUrl = homeUrl;
	}
}
