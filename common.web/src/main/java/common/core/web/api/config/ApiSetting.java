package common.core.web.api.config;

import javax.xml.bind.annotation.XmlElement;

public class ApiSetting {

	@XmlElement(name = "api.server.privateKey", required = false)
	private String apiServerPrivateKey;

	public String getApiServerPrivateKey() {
		return apiServerPrivateKey;
	}

	public void setApiServerPrivateKey(String apiServerPrivateKey) {
		this.apiServerPrivateKey = apiServerPrivateKey;
	}

	private static ApiSetting ME = new ApiSetting();

	public static ApiSetting get() {
		return ApiSetting.ME;
	}

	private ApiSetting() {
		super();
	}

}
