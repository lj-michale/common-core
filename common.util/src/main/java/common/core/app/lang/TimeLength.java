package common.core.app.lang;

import java.util.concurrent.TimeUnit;

public final class TimeLength {
	public static final TimeLength ZERO = TimeLength.seconds(0);

	public static TimeLength days(long days) {
		return new TimeLength(days, TimeUnit.DAYS);
	}

	public static TimeLength hours(long hours) {
		return new TimeLength(hours, TimeUnit.HOURS);
	}

	public static TimeLength minutes(long minutes) {
		return new TimeLength(minutes, TimeUnit.MINUTES);
	}

	public static TimeLength seconds(long seconds) {
		return new TimeLength(seconds, TimeUnit.SECONDS);
	}

	public static TimeLength milliseconds(long milliseconds) {
		return new TimeLength(milliseconds, TimeUnit.MILLISECONDS);
	}

	private final long length;
	private final TimeUnit unit;

	private TimeLength(long length, TimeUnit unit) {
		this.length = length;
		this.unit = unit;
	}

	public long length() {
		return length;
	}

	public TimeUnit unit() {
		return unit;
	}

	public long toMilliseconds() {
		return unit.toMillis(length);
	}

	public long toSeconds() {
		return unit.toSeconds(length);
	}

	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (!(other instanceof TimeLength))
			return false;

		TimeLength that = (TimeLength) other;

		return toMilliseconds() == that.toMilliseconds();
	}

	@Override
	public int hashCode() {
		long mills = toMilliseconds();
		return (int) (mills ^ (mills >>> 32));
	}
}
