package com.specmate.metrics.internal;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	
	public static long getDiffDay() {
		long oneDay = 1000L*60*60*24;
		long now = new Date().getTime();
		long difference = now - oneDay;
		System.err.println("Difference one day:" + difference);
		return difference;
	}
	
	public static long getDiffWeek() {
		long oneWeek = 1000L*60*60*24*7;
		long now = new Date().getTime();
		long difference = now - oneWeek;
		System.err.println("Difference one week:" + difference);
		return difference;
	}
	
	public static long getDiffMonth() {
		int daysInCurrentMonth = getDaysInMonth();
		long oneMonth = 1000L*60*60*24*7*daysInCurrentMonth;
		long now = new Date().getTime();
		long difference = now - oneMonth;
		System.err.println("Difference one month:" + difference);
		return difference;
	}
	
	public static long getDiffYear() {
		int daysInCurrentYear = getDaysInYear();
		long oneYear = 1000L*60*60*24*7*daysInCurrentYear;
		long now = new Date().getTime();
		long difference = now - oneYear;
		System.err.println("Difference one year:" + difference);
		return difference;
	}
	
	private static int getDaysInMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	private static int getDaysInYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.getActualMaximum(Calendar.DAY_OF_YEAR);
	}
}
