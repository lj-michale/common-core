package common.core.common.dfs.fastdfs;

import javax.xml.bind.annotation.XmlElement;

public class FastDfsSetting {

	@XmlElement(name = "fastdfs.connect_timeout_in_seconds", required = true)
	private Long connectTimeoutInSeconds;
	@XmlElement(name = "fastdfs.network_timeout_in_seconds", required = true)
	private Long networkTimeoutInSeconds;
	@XmlElement(name = "fastdfs.charset", required = true)
	private String charset;
	@XmlElement(name = "fastdfs.http_anti_steal_token", required = true)
	private String httpAntiStealToken;
	@XmlElement(name = "fastdfs.http_secret_key", required = false)
	private String httpSecretKey;
	@XmlElement(name = "fastdfs.http_tracker_http_port", required = true)
	private Integer httpTrackerHttpPort;
	@XmlElement(name = "fastdfs.tracker_servers", required = true)
	private String trackerServers;

	public Long getConnectTimeoutInSeconds() {
		return connectTimeoutInSeconds;
	}

	public void setConnectTimeoutInSeconds(Long connectTimeoutInSeconds) {
		this.connectTimeoutInSeconds = connectTimeoutInSeconds;
	}

	public Long getNetworkTimeoutInSeconds() {
		return networkTimeoutInSeconds;
	}

	public void setNetworkTimeoutInSeconds(Long networkTimeoutInSeconds) {
		this.networkTimeoutInSeconds = networkTimeoutInSeconds;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getHttpAntiStealToken() {
		return httpAntiStealToken;
	}

	public void setHttpAntiStealToken(String httpAntiStealToken) {
		this.httpAntiStealToken = httpAntiStealToken;
	}

	public String getHttpSecretKey() {
		return httpSecretKey;
	}

	public void setHttpSecretKey(String httpSecretKey) {
		this.httpSecretKey = httpSecretKey;
	}

	public Integer getHttpTrackerHttpPort() {
		return httpTrackerHttpPort;
	}

	public void setHttpTrackerHttpPort(Integer httpTrackerHttpPort) {
		this.httpTrackerHttpPort = httpTrackerHttpPort;
	}

	public String getTrackerServers() {
		return trackerServers;
	}

	public void setTrackerServers(String trackerServers) {
		this.trackerServers = trackerServers;
	}

	@Override
	public String toString() {
		return "FastDfsSetting [connectTimeoutInSeconds=" + connectTimeoutInSeconds + ", networkTimeoutInSeconds=" + networkTimeoutInSeconds + ", charset=" + charset + ", httpAntiStealToken=" + httpAntiStealToken + ", httpTrackerHttpPort=" + httpTrackerHttpPort + ", trackerServers=" + trackerServers
				+ "]";
	}

}
