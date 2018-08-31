package com.specmate.scheduler.iterators;

import java.util.Calendar;
import java.util.Date;

/**
 * An <code>OffsetIterator</code> modifies the dates returned by a
 * {@link ScheduleIterator} by a constant calendar offset.
 */
public class OffsetIterator implements ScheduleIterator {

	private final ScheduleIterator scheduleIterator;
	private final int field;
	private final int offset;
	private final Calendar calendar = Calendar.getInstance();

	public OffsetIterator(ScheduleIterator scheduleIterator, int field, int offset) {
		this.scheduleIterator = scheduleIterator;
		this.field = field;
		this.offset = offset;
	}

	public Date next() {
		Date date = scheduleIterator.next();
		if (date == null) {
			return null;
		}
		calendar.setTime(date);
		calendar.add(field, offset);
		return calendar.getTime();
	}
}
