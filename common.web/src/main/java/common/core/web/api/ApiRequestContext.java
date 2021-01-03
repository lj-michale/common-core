package common.core.web.api;

import common.core.common.util.ObjectUtil;
import common.core.common.util.StringUtil;
import common.core.web.api.view.api.method.ApiVersionUtil;

public class ApiRequestContext {

	private static final ThreadLocal<ApiRequest> APIREQUEST_HOOK = new ThreadLocal<>();
	private static final ThreadLocal<UserAgent> USERAGENT_HOOK = new ThreadLocal<>();

	public static ApiRequest get() {
		return APIREQUEST_HOOK.get();
	}

	public static void set(ApiRequest apiRequest) {
		APIREQUEST_HOOK.set(apiRequest);
		if (null == apiRequest || StringUtil.isBlank(apiRequest.getUserAgent()))
			return;
		USERAGENT_HOOK.set(ObjectUtil.fromJson(UserAgent.class, apiRequest.getUserAgent()));
	}

	public static UserAgent getUserAgent() {
		return USERAGENT_HOOK.get();
	}

	public static void clean() {
		APIREQUEST_HOOK.remove();
		USERAGENT_HOOK.remove();
	}

	/**
	 * 接口匹配，"ios&#60;=1.0.0|android&#62;=1.0.1"
	 * @param match
	 * @return 是否匹配
	 */
	public static boolean match(String match) {
		UserAgent userAgent = ApiRequestContext.getUserAgent();
		if (null == userAgent || StringUtil.isBlank(match) || ApiMethod.DEFAULT.equals(match))
			return true;
		String platform = userAgent.getPlatform();
		String requestVesion = userAgent.getSoftwareVersion();
		if (StringUtil.isBlank(platform) || StringUtil.isBlank(requestVesion))
			return true;
		String[] matchs = StringUtil.split(match, "|");
		for (String item : matchs) {
			if (item.startsWith(platform)) {
				String exp = item.substring(platform.length());
				String compareType = null;
				if (exp.startsWith("<=") || exp.startsWith(">=") || exp.startsWith("==") || exp.startsWith("!=") || exp.startsWith("<>")) {
					compareType = exp.substring(0, 2);
				} else if (exp.startsWith("=*")) {
					continue;
				} else if (exp.startsWith("<") || exp.startsWith(">") || exp.startsWith("=")) {
					compareType = exp.substring(0, 1);
				} else {
					return false;
				}
				String apiVesion = exp.substring(compareType.length());
				if (!ApiVersionUtil.compareVersionValue(requestVesion, apiVesion, compareType))
					return false;
			}
		}
		return true;
	}
	
	public static boolean compareVersion(UserAgent userAgent ,String match) {
		if (null == userAgent || StringUtil.isBlank(match) || ApiMethod.DEFAULT.equals(match))
			return true;
		String platform = userAgent.getPlatform();
		String requestVesion = userAgent.getSoftwareVersion();
		if (StringUtil.isBlank(platform) || StringUtil.isBlank(requestVesion))
			return true;
		String[] matchs = StringUtil.split(match, "|");
		for (String item : matchs) {
			if (item.startsWith(platform)) {
				String exp = item.substring(platform.length());
				String compareType = null;
				if (exp.startsWith("<=") || exp.startsWith(">=") || exp.startsWith("==") || exp.startsWith("!=") || exp.startsWith("<>")) {
					compareType = exp.substring(0, 2);
				} else if (exp.startsWith("=*")) {
					continue;
				} else if (exp.startsWith("<") || exp.startsWith(">") || exp.startsWith("=")) {
					compareType = exp.substring(0, 1);
				} else {
					return false;
				}
				String apiVesion = exp.substring(compareType.length());
				if (!ApiVersionUtil.compareVersionValue(requestVesion, apiVesion, compareType))
					return false;
			}
		}
		return true;
	}

}
