package com.specmate.scheduler.iterators;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

/**
 * A <code>DailyIterator</code> returns a sequence of dates on subsequent days
 * representing the same time each day.
 */
public class HourlyIterator implements ScheduleIterator {
	private ZonedDateTime zoneDate;

	public HourlyIterator(Date date, int... time) {
		this(getMinute(time), getSecond(time), date);
	}

	public HourlyIterator(int minute, int second, Date date) {

		// Convert LocalDate to LocalDateTime with parameter of method  
		LocalDateTime localDT = LocalDateTime.of(LocalDate.now(), LocalTime.now());
		localDT = localDT.withMinute(minute).withSecond(second).withNano(0);

		ZoneId currentZone = ZoneId.systemDefault();
		zoneDate = ZonedDateTime.of(localDT, currentZone);
	}

	@Override
	public Date next() {
		zoneDate = zoneDate.plusHours(1);
		return Date.from(zoneDate.toInstant());
	}

	private static int getMinute(int... time) {
		int temp = SchedulerUtils.getNumberIfExistsOrZero(0, time);
		return SchedulerUtils.normalizeInput(temp, 60);
	}

	private static int getSecond(int... time) {
		int temp = SchedulerUtils.getNumberIfExistsOrZero(1, time);
		return SchedulerUtils.normalizeInput(temp, 60);
	}
}
