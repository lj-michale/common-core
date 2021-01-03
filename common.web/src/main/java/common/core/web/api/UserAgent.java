package common.core.web.api;

import java.io.Serializable;

public class UserAgent implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String platform;
	private String softwareVersion;
	private String deviceUDID;
	private String deviceType;
	private String systemVersion;
	private String screenSize;
	
	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getSoftwareVersion() {
		return softwareVersion;
	}

	public void setSoftwareVersion(String softwareVersion) {
		this.softwareVersion = softwareVersion;
	}

	public String getDeviceUDID() {
		return deviceUDID;
	}

	public void setDeviceUDID(String deviceUDID) {
		this.deviceUDID = deviceUDID;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getSystemVersion() {
		return systemVersion;
	}

	public void setSystemVersion(String systemVersion) {
		this.systemVersion = systemVersion;
	}

	public String getScreenSize() {
		return screenSize;
	}

	public void setScreenSize(String screenSize) {
		this.screenSize = screenSize;
	}

	@Override
	public String toString() {
		return "UserAgent [platform=" + platform + ", softwareVersion=" + softwareVersion + ", deviceUDID=" + deviceUDID
				+ ", deviceType=" + deviceType + ", systemVersion=" + systemVersion + ", screenSize=" + screenSize
				+ "]";
	}
}
