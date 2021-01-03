package common.core.web.url;

import common.core.common.util.UrlUtil;

public class UrlBuilder {

	private String url;

	public UrlBuilder(String url) {
		super();
		this.url = url;
	}

	public String buildUrl() {
		return url;
	}

	public UrlBuilder appendParam(String name, String value) {
		this.url = UrlUtil.appendUrl(this.url, name, value);
		return this;
	}

	/**
	 * @param params
	 *            object or map
	 * @return UrlBuilder
	 */
	public UrlBuilder appendParam(Object params) {
		this.url = UrlUtil.appendUrl(this.url, params);
		return this;
	}

	public UrlBuilder removeParam(String paramName) {
		this.url = UrlUtil.removeParam(this.url, paramName);
		return this;
	}

}
