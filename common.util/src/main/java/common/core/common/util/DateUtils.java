package common.core.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Date should be considered as immutable object (e.g. String), all methods in
 * this util return new instance
 *
 */
public final class DateUtils {
	public static String FORMATE_ISO_DATE_TIME = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";

	public static ThreadLocal<DateFormat> FORMATE_STAND_DATE_TIME = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}
	};
//	public static SimpleDateFormat FORMATE_STAND_DATE_TIME = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


	public static ThreadLocal<DateFormat> FORMATE_STAND_DATE = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy-MM-dd");
		}
	};
//	public static SimpleDateFormat FORMATE_STAND_DATE = new SimpleDateFormat("yyyy-MM-dd");

	public static ThreadLocal<DateFormat> FORMATE_CN_DATE = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("yyyy.MM.dd");
		}
	};
//	public static SimpleDateFormat FORMATE_CN_DATE = new SimpleDateFormat("yyyy.MM.dd");

	public static ThreadLocal<DateFormat> FORMATE_STAND_TIME = new ThreadLocal<DateFormat>() {
		@Override
		protected DateFormat initialValue() {
			return new SimpleDateFormat("HH:mm:ss");
		}
	};
//	public static SimpleDateFormat FORMATE_STAND_TIME = new SimpleDateFormat("HH:mm:ss");

	public static String formateStandDateString(Date date) {
		if (null == date)
			return null;
		if (date instanceof java.sql.Date) {
			return DateUtils.formateStandDate(date);
		} else if (date instanceof java.sql.Time) {
			return DateUtils.formateStandTime(date);
		} else {
			return DateUtils.formateStandDateTime(date);
		}
	}

	public static String formateDateString(Date date, String format) {
		if (StringUtil.isEmpty(date))
			return null;
		return new SimpleDateFormat(format).format(date);
	}

	public static Date parseDate(String dateString, String format) {
		if (StringUtil.isEmpty(dateString))
			return null;
		try {
			return new SimpleDateFormat(format).parse(dateString);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
	}

	public static String formateStandDateTime(Date date) {
		if (null == date)
			return null;
		return FORMATE_STAND_DATE_TIME.get().format(date);
	}

	public static Date formateStandDateTime(String source) {
		try {
			return FORMATE_STAND_DATE_TIME.get().parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String formateStandDate(Date date) {
		if (null == date)
			return null;
		return FORMATE_STAND_DATE.get().format(date);
	}

	public static Date formateStandDate(String source) {
		try {
			return FORMATE_STAND_DATE.get().parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String formateStandTime(Date date) {
		if (null == date)
			return null;
		return FORMATE_STAND_DATE.get().format(date);
	}

	public static Date formateStandTime(String source) {
		try {
			return FORMATE_STAND_TIME.get().parse(source);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	public static String currentIsoDateString() {
		return ConvertUtil.toIsoString(new Date());
	}

	public static String currentIsoDateTimeString() {
		return new SimpleDateFormat(DateUtils.FORMATE_ISO_DATE_TIME).format(new Date());
	}

	public static Date date(int year, int month, int day) {
		return date(year, month, day, 0, 0, 0);
	}

	public static Date date(int year, int month, int day, int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(year, month, day, hour, minute, second);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static Calendar calendar(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}

	public static Date add(Date date, int field, int value) {
		Calendar calendar = calendar(date);
		calendar.add(field, value);
		return calendar.getTime();
	}

	public static Date addDate(Date date, int value) {
		return add(date, Calendar.DATE, value);
	}

	public static Date addMonth(Date date, int value) {
		return add(date, Calendar.MONTH, value);
	}

	public static int get(Date date, int field) {
		Calendar calendar = calendar(date);
		return calendar.get(field);
	}

	public static Date withField(Date date, int field, int value) {
		Calendar calendar = calendar(date);
		calendar.set(field, value);
		return calendar.getTime();
	}

	public static int getYear(Date date) {
		return get(date, Calendar.YEAR);
	}

	public static int getMonth(Date date) {
		return get(date, Calendar.MONTH);
	}

	public static int getDay(Date date) {
		return get(date, Calendar.DATE);
	}

	public static int getHour(Date date) {
		return get(date, Calendar.HOUR_OF_DAY);
	}

	public static int getMinute(Date date) {
		return get(date, Calendar.MINUTE);
	}

	public static Date withHour(Date date, int value) {
		return withField(date, Calendar.HOUR_OF_DAY, value);
	}

	public static Date withMinute(Date date, int value) {
		return withField(date, Calendar.MINUTE, value);
	}

	public static Date toCurrentTimeZone(Date targetDate, TimeZone targetTimeZone) {
		Calendar target = calendar(targetDate);

		Calendar result = Calendar.getInstance(targetTimeZone);
		result.set(Calendar.YEAR, target.get(Calendar.YEAR));
		result.set(Calendar.MONTH, target.get(Calendar.MONTH));
		result.set(Calendar.DATE, target.get(Calendar.DATE));
		result.set(Calendar.HOUR_OF_DAY, target.get(Calendar.HOUR_OF_DAY));
		result.set(Calendar.MINUTE, target.get(Calendar.MINUTE));
		result.set(Calendar.SECOND, target.get(Calendar.SECOND));
		result.set(Calendar.MILLISECOND, target.get(Calendar.MILLISECOND));

		return result.getTime();
	}

	public static boolean isWeekDay(Date targetDate) {
		Calendar calendar = calendar(targetDate);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		return dayOfWeek != Calendar.SATURDAY && dayOfWeek != Calendar.SUNDAY;
	}

	public static boolean isDateValid(int year, int month, int day) {
		try {
			date(year, month, day);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public static Date truncateTime(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	/**
	 * 获得时分秒字符串 （根据毫秒时长）
	 * 
	 * @return
	 */
	public static String getHHmmddByMinSec(long minSec) {
		long second = minSec / 1000 + 1;// 推后一秒
		long h = second / 3600, d = (second % 3600) / 60, s = second % 60;
		String HH = h < 10 ? "0" + h : h + "";
		HH = HH.equals("0") ? "00" : HH + "";
		String dd = d < 10 ? "0" + d : d + "";
		dd = dd.equals("0") ? "00" : dd + "";
		String ss = s < 10 ? "0" + s : s + "";
		ss = ss.equals("0") ? "00" : ss + "";
		return HH + ":" + dd + ":" + ss;
	}

	/**
	 * @倒计时判断 倒计时到了返回false 未到返回true
	 * @param countdown
	 *            ,Date
	 * @return boolean
	 * @throws ParseException
	 */
	public static long getDisTime(String countdown) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date dateStart;
		try {
			dateStart = format.parse(countdown);
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}
		long disTime = dateStart.getTime() - System.currentTimeMillis();
		return disTime;
	}

	private DateUtils() {
	}

	public static Date getMondayOfThisWeek() {
		return DateUtils.getMondayOfWeek(new Date());
	}

	public static Date getMondayOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (day_of_week == 0)
			day_of_week = 7;
		cal.add(Calendar.DATE, -day_of_week + 1);
		return cal.getTime();
	}

	public static Date getFirstDayOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	public static Date getFirstDayOfYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		cal.set(Calendar.DAY_OF_YEAR, 1);
		return cal.getTime();
	}

	public static int getWeekOfDate(Date dt) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static void main(String[] args) {
		System.out.println(DateUtils.getMondayOfWeek(DateUtils.formateStandDate("2016-08-21")));
		System.out.println(DateUtils.getMondayOfWeek(DateUtils.formateStandDateTime("2016-08-22 22:22:22")));
		System.out.println(DateUtils.getMondayOfWeek(DateUtils.formateStandDate("2016-08-28")));
		System.out.println(DateUtils.getFirstDayOfMonth(DateUtils.formateStandDateTime("2016-08-22 22:22:22")));
		System.out.println(DateUtils.add(DateUtils.getFirstDayOfMonth(DateUtils.formateStandDateTime("2016-02-22 22:22:22")), Calendar.MONTH, -1));
		System.out.println(DateUtils.add(DateUtils.getFirstDayOfMonth(DateUtils.formateStandDateTime("2016-02-22 22:22:22")), Calendar.MONTH, 1));
		System.out.println(DateUtils.getFirstDayOfYear(DateUtils.formateStandDate("2016-08-28")));
		Date date = DateUtils.formateStandDate("2016-11-11");
		System.out.println(DateUtils.getWeekOfDate(date));
		System.out.println(DateUtils.getWeekOfDate(DateUtils.addDate(date, 2)));
		System.out.println(DateUtils.getWeekOfDate(DateUtils.addDate(date, -4)));
	}
}
