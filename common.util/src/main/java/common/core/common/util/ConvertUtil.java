package common.core.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public final class ConvertUtil {
	public static final String DATE_FORMAT_DATE = "MM/dd/yyyy";
	public static final String DATE_FORMAT_DATETIME = "MM/dd/yyyy'T'HH:mm:ss";
	public static final String DATE_FORMAT_ISO_WITH_TIMEZONE = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
	public static final String DATE_FORMAT_ISO = "yyyy-MM-dd";

	public static Integer toInt(String text, Integer defaultValue) {
		if (!StringUtil.hasText(text))
			return defaultValue;
		try {
			return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static boolean toBoolean(String text, boolean defaultValue) {
		if (!StringUtil.hasText(text))
			return defaultValue;
		try {
			return Boolean.valueOf(text);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Long toLong(String text, Long defaultValue) {
		if (!StringUtil.hasText(text))
			return defaultValue;
		try {
			return Long.parseLong(text);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	public static Double toDouble(String text, Double defaultValue) {
		if (!StringUtil.hasText(text))
			return defaultValue;
		try {
			return Double.parseDouble(text);
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}

	// since DateFormat is not thread safe, we create for each parsing
	public static Date toDate(String date, String formatPattern, Date defaultValue) {
		if (!StringUtil.hasText(date)) {
			return defaultValue;
		}
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatPattern);
			return format.parse(date);
		} catch (ParseException e) {
			return defaultValue;
		}
	}

	public static Date toDate(String date, String formatPattern) {
		try {
			SimpleDateFormat format = new SimpleDateFormat(formatPattern);
			return format.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static Date toDate(String date, Date defaultValue) {
		return toDate(date, DATE_FORMAT_DATE, defaultValue);
	}

	public static Date toDateTime(String date, Date defaultValue) {
		return toDate(date, DATE_FORMAT_DATETIME, defaultValue);
	}

	public static Date toISODateTime(String date, Date defaultValue) {
		return toDate(date, DATE_FORMAT_ISO_WITH_TIMEZONE, defaultValue);
	}

	public static String toIsoString(Date date) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT_ISO);
		return dateFormat.format(date);
	}

	public static String toString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(date);
	}

	public static String toString(Date date, String format, TimeZone timeZone) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		dateFormat.setTimeZone(timeZone);
		return dateFormat.format(date);
	}

	public static <T extends Enum<T>> T toEnum(String value, Class<T> enumClass, T defaultValue) {
		if (!StringUtil.hasText(value))
			return defaultValue;
		try {
			return Enum.valueOf(enumClass, value);
		} catch (IllegalArgumentException e) {
			return defaultValue;
		}
	}

	private ConvertUtil() {
	}
}
