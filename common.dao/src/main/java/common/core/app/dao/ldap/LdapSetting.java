package common.core.app.dao.ldap;

import javax.xml.bind.annotation.XmlElement;

public class LdapSetting {

	/**
	 * 连接url，如"ldap://localhost:389/dc=scut,dc=edu,dc=cn"
	 */
	@XmlElement(name = "provider.url")
	private String providerUrl;

	/**
	 * 连接账号，如“cn=Manager”
	 */
	@XmlElement(name = "security.principal")
	private String securityPrincipal;

	/**
	 * 密码，如"123456"
	 */
	@XmlElement(name = "security.credentials")
	private String securityCredentials;

	public String getProviderUrl() {
		return providerUrl;
	}

	public void setProviderUrl(String providerUrl) {
		this.providerUrl = providerUrl;
	}

	public String getSecurityPrincipal() {
		return securityPrincipal;
	}

	public void setSecurityPrincipal(String securityPrincipal) {
		this.securityPrincipal = securityPrincipal;
	}

	public String getSecurityCredentials() {
		return securityCredentials;
	}

	public void setSecurityCredentials(String securityCredentials) {
		this.securityCredentials = securityCredentials;
	}

}
