package common.core.app.runtime;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;

import common.core.app.context.ConfigContext;

public class RuntimeSetting {

	@XmlElement(name = "app.env", required = true)
	private String env;
	@XmlElement(name = "app.version", required = true)
	private String appVersion;
	@XmlElement(name = "app.artifactId", required = true)
	private String artifactId;
	@XmlElement(name = "app.groupId", required = true)
	private String groupId;

	private Date startDate = new Date();

	private static RuntimeSetting ME = new RuntimeSetting();

	static {
		ConfigContext.bindConfigObject(ME);
	}

	public static RuntimeSetting get() {
		return ME;
	}

	public String getEnv() {
		if (env.indexOf("{") < 0)
			return env;
		else
			return "dev";
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public void setArtifactId(String artifactId) {
		this.artifactId = artifactId;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

}
