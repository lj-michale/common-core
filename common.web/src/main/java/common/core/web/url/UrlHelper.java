package common.core.web.url;

import org.springframework.util.StringUtils;

import common.core.common.util.UrlUtil;
import common.core.web.context.RequestContext;
import common.core.web.context.Schemes;
import common.core.web.context.WebSetting;

public class UrlHelper {
	public static String build(String url) {
		return UrlHelper.build(url, null, null);
	}

	public static String build(String url, String scheme) {
		return UrlHelper.build(url, scheme, null);
	}

	public static String build(String url, String scheme, String domain) {
		StringBuffer stringBuffer = new StringBuffer();
		if (UrlUtil.hasScheme(url)) {
			return url;
		}
		if (StringUtils.isEmpty(domain)) {
			domain = WebSetting.get().getServerName();
		}
		if (UrlUtil.hasScheme(domain)) {
			// domain with scheme
			return stringBuffer.append(domain).append(url).toString();
		}

		if (StringUtils.isEmpty(scheme)) {
			scheme = RequestContext.getScheme();
		}
		scheme = scheme.toLowerCase();// 协议转小写
		if (StringUtils.hasText(scheme) && !UrlUtil.hasScheme(url) && StringUtils.hasText(domain)) {
			stringBuffer = new StringBuffer(scheme);
			stringBuffer.append("://").append(domain);
			if (domain.equalsIgnoreCase(WebSetting.get().getServerName())) {
				if (Schemes.HTTPS.equals(scheme) && 443 != WebSetting.get().getHttpsPort()) {
					stringBuffer.append(":").append(WebSetting.get().getHttpsPort());
				} else if (80 != WebSetting.get().getHttpPort()) {
					stringBuffer.append(":").append(WebSetting.get().getHttpPort());
				}
				if (StringUtils.hasText(WebSetting.get().getContextPath()) && !"/".equals(WebSetting.get().getContextPath())) {
					stringBuffer.append(WebSetting.get().getContextPath());
				}
			}
			stringBuffer.append(url);
			url = stringBuffer.toString();
		}
		return url;
	}
}
