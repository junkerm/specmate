package com.specmate.scheduler.iterators;

import java.util.Calendar;
import java.util.Date;

/**
 * A <code>DailyIterator</code> returns a sequence of dates on subsequent days
 * representing the same time each day.
 */
public class HourlyIterator implements ScheduleIterator {
	private final Calendar calendar = Calendar.getInstance();

	public HourlyIterator(Date date, int... time) {
		this(getMinute(time), getSecond(time), date);
	}

	public HourlyIterator(int minute, int second, Date date) {
		calendar.setTime(date);
		calendar.set(Calendar.MINUTE, minute);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		if (!calendar.getTime().before(date)) {
			calendar.add(Calendar.HOUR_OF_DAY, -1);
		}
	}

	@Override
	public Date next() {
		calendar.add(Calendar.HOUR_OF_DAY, 1);
		return calendar.getTime();
	}

	private static int getMinute(int... time) {
		return SchedulerUtils.getNumberIfExistsOrZero(0, time);
	}

	private static int getSecond(int... time) {
		return SchedulerUtils.getNumberIfExistsOrZero(1, time);
	}
}
