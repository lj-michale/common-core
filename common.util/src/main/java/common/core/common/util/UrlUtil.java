package common.core.common.util;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UrlUtil {
	public final static String SERVER_NAME_PATTERN_REG = "^http[s]?://[^/]+";
	public final static Pattern SERVER_NAME_PATTERN = Pattern.compile(SERVER_NAME_PATTERN_REG);

	public static String appendUrl(String url, String name, String value) {
		StringBuffer retUrl = new StringBuffer(url);
		if (url.indexOf('?') > 0) {
			retUrl.append('&');
		} else {
			retUrl.append('?');
		}
		retUrl.append(name).append('=');
		if (StringUtil.hasText(value)) {
			retUrl.append(EncodingUtil.urlEncode(value));
		}
		return retUrl.toString();
	}

	public static boolean hasScheme(String url) {
		return url.startsWith("http://") || url.startsWith("https://") || url.startsWith("//");
	}

	public static String appendUrl(String url, Object params) {
		if (null == params || null == url)
			return url;
		Map<String, Object> paramMap = ObjectUtil.toMap(params);
		StringBuffer retUrl = new StringBuffer(url);
		int i = 0;
		for (Map.Entry<String, Object> item : paramMap.entrySet()) {
			if (null == item.getValue())
				continue;
			if (i++ == 0 && url.indexOf('?') < 0) {
				retUrl.append('?');
			} else {
				retUrl.append('&');
			}
			retUrl.append(item.getKey()).append('=').append(EncodingUtil.urlEncode(item.getValue().toString()));
		}
		return retUrl.toString();
	}

	public static String getRequestFullServer(String url) {
		Matcher matcher = SERVER_NAME_PATTERN.matcher(url);
		if (!matcher.find())
			return null;
		return matcher.group();
	}

	public static String getRequestUri(String url) {
		String uri = url.replaceAll(UrlUtil.SERVER_NAME_PATTERN_REG, "");
		if (StringUtil.isEmpty(uri)) {
			uri = "/";
		}
		return uri;
	}

	public static String removeParam(String url, String... paramNames) {
		for (String paramName : paramNames) {
			url = url.replaceAll("\\&" + paramName + "\\=[^\\?\\&]*", "").replaceAll("\\?" + paramName + "\\=[^\\?\\&]*", "?").replaceAll("\\?$", "");
		}
		return url;
	}

	public static String resetParam(String url, String paramName, String value) {
		url = UrlUtil.removeParam(url, paramName);
		url = UrlUtil.appendUrl(url, paramName, value);
		return url;
	}

}
