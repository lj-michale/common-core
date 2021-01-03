package common.core.web.sso;

import javax.xml.bind.annotation.XmlElement;

import common.core.app.context.ConfigContext;

public class EaSsoSetting {
	private static EaSsoSetting ME = new EaSsoSetting();

	static {
		ConfigContext.bindConfigObject(ME);
	}

	@XmlElement(name = "sso.ea.ignoreStartWithUrls", required = false)
	private String[] ignoreStartWithUrls = {};
	@XmlElement(name = "sso.ea.ignoreUrls", required = false)
	private String[] ignoreUrls = {};
	@XmlElement(name = "sso.ea.casServerLoginUrl", required = true)
	private String casServerLoginUrl;
	@XmlElement(name = "sso.ea.casServerUrlPrefix", required = true)
	private String casServerUrlPrefix;
	@XmlElement(name = "sso.ea.serverName", required = true)
	private String serverName;

	@XmlElement(name = "sso.ea.renew", required = false)
	private boolean renew = false;
	@XmlElement(name = "sso.ea.gateway", required = false)
	private boolean gateway = false;
	@XmlElement(name = "sso.ea.gatewayStorageClass", required = false)
	private String gatewayStorageClass;
	@XmlElement(name = "sso.ea.validUrl", required = false)
	private String validUrl;

	public boolean isRenew() {
		return renew;
	}

	public void setRenew(boolean renew) {
		this.renew = renew;
	}

	public boolean getGateway() {
		return gateway;
	}

	public void setGateway(boolean gateway) {
		this.gateway = gateway;
	}

	public String getGatewayStorageClass() {
		return gatewayStorageClass;
	}

	public void setGatewayStorageClass(String gatewayStorageClass) {
		this.gatewayStorageClass = gatewayStorageClass;
	}

	public String[] getIgnoreUrls() {
		return ignoreUrls;
	}

	public void setIgnoreUrls(String[] ignoreUrls) {
		this.ignoreUrls = ignoreUrls;
	}

	public static EaSsoSetting get() {
		return EaSsoSetting.ME;
	}

	public String[] getIgnoreStartWithUrls() {
		return ignoreStartWithUrls;
	}

	public void setIgnoreStartWithUrls(String[] ignoreStartWithUrls) {
		this.ignoreStartWithUrls = ignoreStartWithUrls;
	}

	public String getCasServerLoginUrl() {
		return casServerLoginUrl;
	}

	public void setCasServerLoginUrl(String casServerLoginUrl) {
		this.casServerLoginUrl = casServerLoginUrl;
	}

	public String getCasServerUrlPrefix() {
		return casServerUrlPrefix;
	}

	public void setCasServerUrlPrefix(String casServerUrlPrefix) {
		this.casServerUrlPrefix = casServerUrlPrefix;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getValidUrl() {
		return validUrl;
	}

	public void setValidUrl(String validUrl) {
		this.validUrl = validUrl;
	}

}
