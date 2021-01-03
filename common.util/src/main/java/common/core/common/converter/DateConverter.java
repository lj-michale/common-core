package common.core.common.converter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

import common.core.common.util.StringUtil;

public abstract class DateConverter<T extends Date> implements Converter<String, T> {
	private final static String SIMPLE_DATE = "yyyyMMdd";
	private final static String STAND_DATE = "yyyy-MM-dd";
	private final static String DATE = "yyyy.MM.dd";
	private final static String TIME = "HH:mm:ss";
	private final static String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
	private final static String FORMATE_ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	@Override
	public T convert(String text) {
		if (StringUtil.isBlank(text)) {
			return null;
		}
		return this.convertToDate(this.buildUtilDate(text));

	}

	public Date buildUtilDate(String text) {
		String format = null;
		try {
			if (text.indexOf(':') > 0 || text.indexOf('-') > 0 || text.indexOf(' ') > 0) {
				format = STAND_DATE;
				if (text.length() == format.length()) {
					return new SimpleDateFormat(format).parse(text);
				}
				format = DATE_TIME;
				if (text.length() == format.length()) {
					return new SimpleDateFormat(format).parse(text);
				}
				format = TIME;
				if (text.length() == format.length()) {
					return new SimpleDateFormat(format).parse(text);
				}
				format = FORMATE_ISO_DATE_TIME;
				return new SimpleDateFormat(format).parse(text);
			}

			if (text.indexOf('.') > 0) {
				format = DATE;
				if (text.length() == format.length()) {
					return new SimpleDateFormat(format).parse(text);
				}
			}

			format = SIMPLE_DATE;
			if (text.length() == format.length()) {
				return new SimpleDateFormat(format).parse(text);
			}
			return new Date(Long.valueOf(text));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public abstract T convertToDate(Date utilDate);

}
