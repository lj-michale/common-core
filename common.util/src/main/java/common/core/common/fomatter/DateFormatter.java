package common.core.common.fomatter;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

import org.springframework.format.Formatter;

import common.core.common.util.DateUtils;
import common.core.common.util.StringUtil;

public class DateFormatter implements Formatter<Date> {
	private final static String DATE = "yyyy-MM-dd";
	private final static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";

	@Override
	public String print(Date object, Locale locale) {
		if (null != object) {
			return DateUtils.FORMATE_STAND_DATE_TIME.get().format(object);
		}
		return null;
	}

	@Override
	public Date parse(String text, Locale locale) throws ParseException {
		if (StringUtil.isNotBlank(text)) {
			if (DateFormatter.DATE_TIME.length() == text.length()) {
				return DateUtils.FORMATE_STAND_DATE_TIME.get().parse(text);
			}
			if (DateFormatter.DATE.length() == text.length()) {
				return DateUtils.FORMATE_STAND_DATE.get().parse(text);
			}
		}
		return null;
	}

}
