package com.specmate.scheduler.iterators;

public class SchedulerUtils {
	public static int getNumberIfExistsOrZero(int index, int... args) {
		return args.length > index ? args[index] : 0;
	}
	
	public static int normalizeInput(int time, int divisor) {
		return time % divisor;
	}
}
