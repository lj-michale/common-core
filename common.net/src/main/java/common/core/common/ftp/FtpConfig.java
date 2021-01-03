package common.core.common.ftp;

import javax.xml.bind.annotation.XmlElement;

public class FtpConfig {

	@XmlElement(name = "ftp.server", required = true)
	private String server;
	@XmlElement(name = "ftp.port", required = true)
	private int port;
	@XmlElement(name = "ftp.user", required = true)
	private String user;
	@XmlElement(name = "ftp.password", required = true)
	private String password;

	@XmlElement(name = "ftp.path", required = true)
	private String path;

	@XmlElement(name = "ftp.bufferSize", required = true)
	private int bufferSize;

	@XmlElement(name = "ftp.controlEncoding", required = true)
	private String controlEncoding;

	@XmlElement(name = "ftp.connectTimeout", required = true)
	private int connectTimeout;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
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

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public int getBufferSize() {
		return bufferSize;
	}

	public void setBufferSize(int bufferSize) {
		this.bufferSize = bufferSize;
	}

	public String getControlEncoding() {
		return controlEncoding;
	}

	public void setControlEncoding(String controlEncoding) {
		this.controlEncoding = controlEncoding;
	}

	public int getConnectTimeout() {
		return connectTimeout;
	}

	public void setConnectTimeout(int connectTimeout) {
		this.connectTimeout = connectTimeout;
	}

}
