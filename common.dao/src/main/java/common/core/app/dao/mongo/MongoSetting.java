package common.core.app.dao.mongo;

import javax.xml.bind.annotation.XmlElement;

public class MongoSetting {

	@XmlElement(name = "mongo.host", required = true)
	private String host;
	@XmlElement(name = "mongo.port", required = true)
	private int port;
	@XmlElement(name = "mongo.database", required = true)
	private String database;
	@XmlElement(name = "mongo.user", required = false)
	private String user;
	@XmlElement(name = "mongo.password", required = false)
	private String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getDatabase() {
		return database;
	}

	public void setDatabase(String database) {
		this.database = database;
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

	@Override
	public String toString() {
		return "MongoSetting [host=" + host + ", port=" + port + ", database=" + database + ", user=" + user + "]";
	}

}
