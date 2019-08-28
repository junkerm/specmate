package com.specmate.metrics.internal;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;

public class TimeUtil {
	
	public static long getDiffDay() {
		LocalDateTime localNow = LocalDateTime.now();

		ZoneId currentZone = ZoneId.systemDefault();
		ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);

		// Now we set the time of the current day to 00:00:00.000
		ZonedDateTime zonedNextTarget = zonedNow.withHour(0).withMinute(0).withSecond(0).withNano(0);

		long time = zonedNextTarget.toInstant().toEpochMilli(); 
		
		return time;
	}
	
	public static long getDiffWeek() {
		LocalDateTime localNow = LocalDateTime.now();

		ZoneId currentZone = ZoneId.systemDefault();
		ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
		
		// Go back to beginning of current week
		zonedNow = zonedNow.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
		zonedNow = zonedNow.withHour(0).withMinute(0).withSecond(0).withNano(0);

		long time = zonedNow.toInstant().toEpochMilli(); 
		
		return time;
	}
	
	public static long getDiffMonth() {
		LocalDateTime localNow = LocalDateTime.now();

		ZoneId currentZone = ZoneId.systemDefault();
		ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
		
		// Go back to beginning of current week
		zonedNow = zonedNow.with(TemporalAdjusters.firstDayOfMonth());
		zonedNow = zonedNow.withHour(0).withMinute(0).withSecond(0).withNano(0);

		long time = zonedNow.toInstant().toEpochMilli(); 

		return time;
	}
	
	public static long getDiffYear() {
		LocalDateTime localNow = LocalDateTime.now();

		ZoneId currentZone = ZoneId.systemDefault();
		ZonedDateTime zonedNow = ZonedDateTime.of(localNow, currentZone);
		
		// Go back to beginning of current week
		zonedNow = zonedNow.with(TemporalAdjusters.firstDayOfYear());
		zonedNow = zonedNow.withHour(0).withMinute(0).withSecond(0).withNano(0);

		long time = zonedNow.toInstant().toEpochMilli(); 
		
		return time;
	}
}