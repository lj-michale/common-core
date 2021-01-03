package common.core.app.dao.sql.dynamic;

import java.util.List;
import java.util.Map;

import common.core.common.util.StringUtil;

public class NotEmptySqlFilter {

	private static final String defaultValue = "";

	public String getPrefix() {
		return "/*@NotEmpty(";
	}

	public String getSuffix() {
		return ")*/";
	}

	public String buildBody(String body, Map<String, ?> param) {
		int startIndex = -1;
		char[] chars = body.toCharArray();
		StringBuilder buffer = null;
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			if (c == ':') {
				startIndex = i;
				buffer = new StringBuilder();
				continue;
			}
			if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_') {
				if (startIndex >= 0)
					buffer.append(c);
			} else {
				if (startIndex >= 0) {
					startIndex = -1;
					String name = buffer.toString();
					Object value = param.get(name);
					if (null == value)
						return defaultValue;
					if (value instanceof String && StringUtil.isBlank((String) value))
						return defaultValue;
					if (value instanceof List && ((List<?>) value).isEmpty())
						return defaultValue;
				}
			}
		}
		if (startIndex >= 0 && null != buffer) {
			startIndex = -1;
			String name = buffer.toString();
			Object value = param.get(name);
			if (null == value)
				return defaultValue;
			if (value instanceof String && StringUtil.isBlank((String) value))
				return defaultValue;
			if (value instanceof List && ((List<?>) value).isEmpty())
				return defaultValue;
		}

		return body;
	}
}
