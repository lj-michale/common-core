package common.core.web.api;

import common.core.app.context.ConfigContext;
import common.core.common.assertion.util.AssertWarnUtils;
import common.core.web.api.config.ApiSetting;

public class ApiContext {

	public static String getPrivateKey() {
		return ApiSetting.get().getApiServerPrivateKey();
	}

	public static String getPublicKey(String appId) {
		String configName = "api.app." + appId + ".publicKey";
		AssertWarnUtils.assertTrue(ConfigContext.containsKey(configName), "configName[{}] not found!");
		return ConfigContext.getStringValue(configName, null);
	}

}
