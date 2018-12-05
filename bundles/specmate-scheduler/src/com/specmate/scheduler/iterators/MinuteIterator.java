package com.specmate.scheduler.iterators;

import java.util.Calendar;
import java.util.Date;

/**
 * A <code>DailyIterator</code> returns a sequence of dates on subsequent days
 * representing the same time each day.
 */
public class MinuteIterator implements ScheduleIterator {
	private final Calendar calendar = Calendar.getInstance();

	public MinuteIterator(Date date, int... time) {
		this(getSecond(time), date);
	}

	public MinuteIterator(int second, Date date) {
		calendar.setTime(date);
		calendar.set(Calendar.SECOND, second);
		calendar.set(Calendar.MILLISECOND, 0);
		if (!calendar.getTime().before(date)) {
			calendar.add(Calendar.MINUTE, -1);
		}
	}

	@Override
	public Date next() {
		calendar.add(Calendar.MINUTE, 1);
		return calendar.getTime();
	}

	private static int getSecond(int... time) {
		return SchedulerUtils.getNumberIfExistsOrZero(0, time);
	}
}
