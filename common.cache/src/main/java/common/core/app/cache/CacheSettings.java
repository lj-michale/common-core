package common.core.app.cache;

import javax.xml.bind.annotation.XmlElement;

public class CacheSettings {
	@XmlElement(name = "cache.provider", required = true)
	private String cacheProvider;
	@XmlElement(name = "cache.servers", required = false)
	private String servers;

	private final static CacheSettings ME = new CacheSettings();

	private CacheSettings() {

	}

	public static CacheSettings get() {
		return ME;
	}

	public String getCacheProvider() {
		return cacheProvider;
	}

	public void setCacheProvider(String cacheProvider) {
		this.cacheProvider = cacheProvider;
	}

	public String getServers() {
		return servers;
	}

	public void setServers(String servers) {
		this.servers = servers;
	}

}
