package com.specmate.scheduler;

import java.util.Arrays;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateInternalException;
import com.specmate.model.administration.ErrorCode;
import com.specmate.scheduler.iterators.DailyIterator;
import com.specmate.scheduler.iterators.HourlyIterator;
import com.specmate.scheduler.iterators.MinuteIterator;
import com.specmate.scheduler.iterators.ScheduleIterator;

public class SchedulerIteratorFactory {

	private static final String DAY = "day".toLowerCase();
	private static final String MINUTE = "minute".toLowerCase();
	private static final String HOUR = "hour".toLowerCase();

	private static final String DELIM = " ";

	public static ScheduleIterator create(String schedule) throws SpecmateException {
		validate(schedule);
		schedule = normalizeScheduleString(schedule);
		return constructScheduleIterator(schedule);
	}

	private static ScheduleIterator constructScheduleIterator(String schedule) throws SpecmateException {
		String type = getType(schedule);
		int[] args = getArgs(schedule);

		if (type.equalsIgnoreCase(DAY)) {
			return new DailyIterator(args);
		}
		if (type.equalsIgnoreCase(HOUR)) {
			return new HourlyIterator(args);
		}
		if (type.equalsIgnoreCase(MINUTE)) {
			return new MinuteIterator(args);
		}
		throw new SpecmateInternalException(ErrorCode.SCHEDULER, "Invalid scheduler type.");
	}

	public static void validate(String schedule) throws SpecmateException {
		if (schedule == null) {
			throw new SpecmateInternalException(ErrorCode.SCHEDULER, "Schedule must not be null.");
		}

		schedule = normalizeScheduleString(schedule);

		if (schedule.length() < 0) {
			throw new SpecmateInternalException(ErrorCode.SCHEDULER, "Schedule length must be greater than 0.");
		}

		String type = getType(schedule);

		String[] validTypesStr = { DAY, HOUR, MINUTE };
		boolean isValidType = Arrays.stream(validTypesStr)
				.anyMatch(validType -> validType.compareToIgnoreCase(type) == 0);
		if (!isValidType) {
			throw new SpecmateInternalException(ErrorCode.SCHEDULER,
					"Invalid type (" + type + "). Valid types are " + String.join(", ", validTypesStr));
		}

		String[] argumentsStr = getArgsStrs(schedule);
		for (String argStr : argumentsStr) {
			try {
				Integer.parseInt(argStr);
			} catch (NumberFormatException nfe) {
				throw new SpecmateInternalException(ErrorCode.SCHEDULER,
						"Invalid argument for schedule: " + argStr + " (Must be integer)");
			}
		}

	}

	private static String normalizeScheduleString(String schedule) {
		return schedule.replaceAll(" {2,}", " ").trim();
	}

	private static String getType(String schedule) {
		String[] parts = schedule.split(DELIM);
		return parts[0];
	}

	private static int[] getArgs(String schedule) {
		String[] argumentsStr = getArgsStrs(schedule);
		return Arrays.stream(argumentsStr).mapToInt(Integer::parseInt).toArray();
	}

	private static String[] getArgsStrs(String schedule) {
		String[] parts = schedule.split(DELIM);
		if (parts.length <= 1) {
			String[] empty = new String[0];
			return empty;
		}
		String[] argumentsStr = Arrays.copyOfRange(parts, 1, parts.length - 1);
		return argumentsStr;
	}
}
