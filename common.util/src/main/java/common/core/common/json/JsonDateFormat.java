package common.core.common.json;

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;
import java.util.regex.Pattern;

import common.core.common.util.Convert;
import common.core.common.util.DateUtils;
import common.core.common.util.StringUtil;

public class JsonDateFormat extends DateFormat {
	private static final long serialVersionUID = -2526086790840094624L;
	private static final Pattern SIMPLEDATE_PATTERN = Pattern.compile("^[0-9]{1,13}$");

	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo, FieldPosition fieldPosition) {
		if (null == date)
			return toAppendTo;
		String format = Convert.DATE_FORMAT_DATETIME;
		if (date instanceof java.sql.Date) {
			format = Convert.DATE_FORMAT_DATE;
		} else if (date instanceof java.sql.Time) {
			format = Convert.DATE_FORMAT_TIME;
		} else if (date instanceof java.sql.Timestamp) {
			format = Convert.DATE_FORMAT_DATETIME;
		}
		toAppendTo.append(DateUtils.formateDateString(date, format));
		return toAppendTo;
	}

	@Override
	public Date parse(String source, ParsePosition pos) {
		if (StringUtil.isBlank(source))
			return null;
		pos.setIndex(source.length());
		String format = Convert.DATE_FORMAT_ISO_WITH_TIMEZONE;
		if (SIMPLEDATE_PATTERN.matcher(source).matches()) {
			return new Date(Long.valueOf(source));
		} else if (Convert.DATE_FORMAT_DATETIME.length() == source.length()) {
			format = Convert.DATE_FORMAT_DATETIME;
		} else if (Convert.DATE_FORMAT_DATE.length() == source.length() && source.indexOf("-") > 0) {
			format = Convert.DATE_FORMAT_DATE;
		} else if (Convert.DATE_FORMAT_TIME.length() == source.length() && source.indexOf(":") > 0) {
			format = Convert.DATE_FORMAT_TIME;
		} else {
			format = getISOPattern(source);
		}
		return DateUtils.parseDate(source, format);
	}

	public static void main(String[] args) {
		System.out.println(System.currentTimeMillis());
	}

	public String getISOPattern(String source) {
		if (source.length() == "2013-10-24T11:39:00.000+08:00".length())
			return "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
		else if (source.length() == "2013-10-24T11:39:00.000+0800".length()) {
			return "yyyy-MM-dd'T'HH:mm:ss.SSSZ";
		}
		StringBuilder b = new StringBuilder("yyyy-MM-dd'T'HH:mm:ss");
		int precision = 0;
		int state = 0;

		for (int i = "yyyy-MM-ddTHH:mm:ss".length(); i < source.length(); i++) {
			char c = source.charAt(i);

			if (c == '.' && state == 0) {
				state = 1;
			} else if (c == '-' || c == '+' || c == 'Z') {
				if (state > 0) {
					b.append('.');
					// support million seconds
					for (int j = 0; j < precision; j++) {
						b.append('S');
					}
				}
				b.append("XXX");
				break;
			} else if (state == 1) {
				precision++;
			}
		}
		return b.toString();
	}

	@Override
	public Object clone() {
		return this;
	}
}
