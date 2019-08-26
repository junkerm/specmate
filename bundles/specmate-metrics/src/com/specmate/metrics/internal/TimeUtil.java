package com.specmate.metrics.internal;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	public static long getDiffDay() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		long now = new Date().getTime();
		// get start of this week in milliseconds
		long startOfDay = cal.getTimeInMillis();
		System.err.println("Start of Day:" + startOfDay);
		System.err.println("Current time:" + now);
		return startOfDay;
	}
	
	public static long getDiffWeek() {
		Calendar cal = Calendar.getInstance();
		// Reset hour of day separately
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);
		
		long now = new Date().getTime();

		// get start of this week in milliseconds
		cal.set(Calendar.DAY_OF_WEEK, cal.getFirstDayOfWeek());
		long startOfWeek = cal.getTimeInMillis();
		System.err.println("Start of week:" + startOfWeek);
		System.err.println("Current time:" + now);
		return startOfWeek;
	}
	
	public static long getDiffMonth() {
		Calendar cal = Calendar.getInstance();
		// Reset hour of day separately
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// get start of this month in milliseconds
		cal.set(Calendar.DAY_OF_MONTH, 1);
		long startOfMonth = cal.getTimeInMillis();
		System.err.println("Start of month:" + startOfMonth);
		return startOfMonth;
	}
	
	public static long getDiffYear() {
		Calendar cal = Calendar.getInstance();
		// Reset hour of day separately
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.clear(Calendar.MINUTE);
		cal.clear(Calendar.SECOND);
		cal.clear(Calendar.MILLISECOND);

		// get start of this week in milliseconds
		cal.set(Calendar.DAY_OF_YEAR, 1);
		long startOfYear = cal.getTimeInMillis();
		System.err.println("Start of year:" + startOfYear);
		return startOfYear;
	}
}