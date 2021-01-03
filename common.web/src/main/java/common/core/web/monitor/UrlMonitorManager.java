package common.core.web.monitor;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;
import common.core.common.util.ApplicationContextUtil;
import common.core.common.util.StringUtil;

/**
 * @author lq
 * @version 创建时间：2015年6月19日 上午10:57:06 类说明
 */
public class UrlMonitorManager {
	private final Logger LOGGER = LoggerFactory.getLogger(UrlMonitorManager.class);

	public void init(String monitorUrls) {
		if (StringUtil.isNull(monitorUrls)) {
			return;
		}
		String[] urls = monitorUrls.split("[,;]");
		int i = 0;
		for (String url : urls) {
			UrlMonitor urlMonitor = ApplicationContextUtil.createBean(UrlMonitor.class);
			urlMonitor.setUrl(url);
			ApplicationContextUtil.registerSingletonBean(UrlMonitor.class.getSimpleName() + i, urlMonitor);
			LOGGER.debug("register url to monitor: {}", url);
			i++;
		}
	}

}
