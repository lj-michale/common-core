package common.core.common.mail;

import javax.xml.bind.annotation.XmlElement;

public class MailConfig {

	@XmlElement(name = "mail.store.protocol", required = true)
	private String protocol;
	@XmlElement(name = "mail.pop3.port", required = true)
	private int port;
	@XmlElement(name = "mail.pop3.host", required = true)
	private String host;
	@XmlElement(name = "mail.folder", required = true)
	private String folder;
	@XmlElement(name = "mail.user", required = true)
	private String user;
	@XmlElement(name = "mail.password", required = true)
	private String password;

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getFolder() {
		return folder;
	}

	public void setFolder(String folder) {
		this.folder = folder;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
