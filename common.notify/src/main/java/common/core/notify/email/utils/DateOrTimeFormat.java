package common.core.notify.email.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateOrTimeFormat {

	/**
	 * 将日期转换成 yyyy-MM-dd H:m:s
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringTime(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
		return format.format(date);
	}

	/**
	 * 将日期转换成 yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(date);
	}

	/**
	 * 将日期转换成 yyyyMMdd
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToString(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(date);
	}

	/**
	 * 将日期转换成 yyyyMMddHHmmss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringT(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		return format.format(date);
	}

	/**
	 * 将日期转换成 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param date
	 * @return
	 */
	public static String dateToStringTimes(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(date);
	}
}
