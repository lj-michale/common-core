package common.core.site.tag;

import common.core.app.context.ConfigContext;
import common.core.app.runtime.RuntimeSetting;
import common.core.common.util.UrlUtil;

public class VersionCdnTag extends CdnTag {

	public String buildVersionPath(String fullFileName) {
		String url = ConfigContext.getStringValue(fullFileName, fullFileName);
		return UrlUtil.appendUrl(url, "_v", RuntimeSetting.get().getAppVersion());
	}

	public String buildCdnUrl(String url) {
		url = this.buildVersionPath(url);
		return super.buildCdnUrl(url);
	}
}
