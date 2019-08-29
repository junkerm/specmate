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
public class MinuteIterator implements ScheduleIterator {
	private ZonedDateTime zoneDate;

	public MinuteIterator(Date date, int... time) {
		this(getSecond(time), date);
	}

	public MinuteIterator(int second, Date date) {
		
		// Get the specified date
		LocalDate localDate = date.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
		// Convert LocalDate to LocalDateTime with parameter of method  
		LocalDateTime localDT = LocalDateTime.of(localDate, LocalTime.now());
		second = second%60;
		localDT = localDT.withSecond(second).withNano(0);

		ZoneId currentZone = ZoneId.systemDefault();
		zoneDate = ZonedDateTime.of(localDT, currentZone);
	}

	@Override
	public Date next() {
		zoneDate = zoneDate.plusMinutes(1);
		return Date.from(zoneDate.toInstant());
	}

	private static int getSecond(int... time) {
		int temp = SchedulerUtils.getNumberIfExistsOrZero(0, time);
		return SchedulerUtils.normalizeInput(temp, 60);
	}
}
