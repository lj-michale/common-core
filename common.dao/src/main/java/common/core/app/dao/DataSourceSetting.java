package common.core.app.dao;

import javax.xml.bind.annotation.XmlElement;

public class DataSourceSetting {

	@XmlElement(name = "jdbc.driver", required = true)
	private String driver;
	@XmlElement(name = "jdbc.url", required = true)
	private String url;
	@XmlElement(name = "jdbc.password", required = false)
	private String password;
	@XmlElement(name = "jdbc.username", required = true)
	private String username;
	@XmlElement(name = "jdbc.sqlDialect", required = true)
	private String sqlDialect;
	@XmlElement(name = "jdbc.databaseName", required = true)
	private String databaseName;
	@XmlElement(name = "jdbc.validationQuery", required = true)
	private String validationQuery;

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSqlDialect() {
		return sqlDialect;
	}

	public void setSqlDialect(String sqlDialect) {
		this.sqlDialect = sqlDialect;
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public String getValidationQuery() {
		return validationQuery;
	}

	public void setValidationQuery(String validationQuery) {
		this.validationQuery = validationQuery;
	}

}
