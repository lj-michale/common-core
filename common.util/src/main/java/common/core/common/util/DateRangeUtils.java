package common.core.common.util;

import java.util.Calendar;
import java.util.Date;

public final class DateRangeUtils {
	public static double secondsBetween(Date date1, Date date2) {
		long gapInMilliseconds = date2.getTime() - date1.getTime();
		return Math.abs(gapInMilliseconds / (double) 1000);
	}

	public static double minutesBetween(Date date1, Date date2) {
		long gapInMilliseconds = date2.getTime() - date1.getTime();
		return Math.abs(gapInMilliseconds / (double) (1000 * 60));
	}

	public static double hoursBetween(Date date1, Date date2) {
		return minutesBetween(date1, date2) / 60;
	}

	public static double daysBetween(Date date1, Date date2) {
		return hoursBetween(date1, date2) / 24;
	}

	public static double monthsBetween(Date date1, Date date2) {
		Calendar calendar1 = DateUtils.calendar(date1);
		Calendar calendar2 = DateUtils.calendar(date2);

		int yearDiff = calendar2.get(Calendar.YEAR) - calendar1.get(Calendar.YEAR);
		int monthDiff = calendar2.get(Calendar.MONTH) - calendar1.get(Calendar.MONTH);
		int dayDiff = calendar2.get(Calendar.DATE) - calendar1.get(Calendar.DATE);

		return Math.abs(yearDiff * 12 + monthDiff + dayDiff / (double) 30);
	}

	public static double yearsBetween(Date date1, Date date2) {
		return monthsBetween(date1, date2) / 12;
	}

	private DateRangeUtils() {
	}
}
