package com.specmate.schedule.test;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.specmate.scheduler.SchedulerIteratorFactory;
import com.specmate.scheduler.iterators.ScheduleIterator;

public class IteratorsTest {

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Test
	public void testYearlyIterator() throws Exception {
		Date date = dateFormat.parse("2018-11-01 09:30:10");

		// scheduled time before current time
		ScheduleIterator hourlyIterator = SchedulerIteratorFactory.create("hour 20 15", date);
		Date next = hourlyIterator.next();
		Assert.assertEquals("2018-11-01 10:20:15", dateFormat.format(next));

		// scheduled time after current time
		hourlyIterator = SchedulerIteratorFactory.create("hour 40 15", date);
		next = hourlyIterator.next();
		Assert.assertEquals("2018-11-01 09:40:15", dateFormat.format(next));

		Date endOfDay = dateFormat.parse("2018-12-31 23:30:10");
		// scheduled time need day jump
		hourlyIterator = SchedulerIteratorFactory.create("hour 20 15", endOfDay);
		next = hourlyIterator.next();
		Assert.assertEquals("2019-01-01 00:20:15", dateFormat.format(next));
	}
	
	@Test
	public void testMonthlyIterator() throws Exception {
		Date date = dateFormat.parse("2018-11-01 09:30:10");

		// scheduled time before current time
		ScheduleIterator hourlyIterator = SchedulerIteratorFactory.create("month 4 40 50", date);
		Date next = hourlyIterator.next();
		Assert.assertEquals("2018-12-01 4:40:50", dateFormat.format(next));

		// scheduled time after current time
		hourlyIterator = SchedulerIteratorFactory.create("month 4 40 50", date);
		next = hourlyIterator.next();
		Assert.assertEquals("2018-11-01 04:40:50", dateFormat.format(next));

		Date endOfDay = dateFormat.parse("2018-12-31 23:30:10");
		// scheduled time need day jump
		hourlyIterator = SchedulerIteratorFactory.create("month 4 40 50", endOfDay);
		next = hourlyIterator.next();
		Assert.assertEquals("2019-01-01 04:40:50", dateFormat.format(next));
	}
	
	@Test
	public void testWeeklyIterator() throws Exception {
		Date date = dateFormat.parse("2018-11-01 09:30:10");

		// scheduled time before current time
		ScheduleIterator hourlyIterator = SchedulerIteratorFactory.create("week 7 45 59", date);
		Date next = hourlyIterator.next();
		Assert.assertEquals("2018-11-08 07:45:59", dateFormat.format(next));

		// scheduled time after current time
		hourlyIterator = SchedulerIteratorFactory.create("week 11 45 59", date);
		next = hourlyIterator.next();
		Assert.assertEquals("2018-11-08 11:45:59", dateFormat.format(next));

		Date endOfDay = dateFormat.parse("2018-12-31 23:30:10");
		// scheduled time need day jump
		hourlyIterator = SchedulerIteratorFactory.create("week 2 45 59", endOfDay);
		next = hourlyIterator.next();
		Assert.assertEquals("2019-01-07 02:45:59", dateFormat.format(next));
	}
	
	@Test
	public void testHourlyIterator() throws Exception {
		Date date = dateFormat.parse("2018-11-01 09:30:10");

		// scheduled time before current time
		ScheduleIterator hourlyIterator = SchedulerIteratorFactory.create("hour 20 15", date);
		Date next = hourlyIterator.next();
		Assert.assertEquals("2018-11-01 10:20:15", dateFormat.format(next));

		// scheduled time after current time
		hourlyIterator = SchedulerIteratorFactory.create("hour 40 15", date);
		next = hourlyIterator.next();
		Assert.assertEquals("2018-11-01 09:40:15", dateFormat.format(next));

		Date endOfDay = dateFormat.parse("2018-12-31 23:30:10");
		// scheduled time need day jump
		hourlyIterator = SchedulerIteratorFactory.create("hour 20 15", endOfDay);
		next = hourlyIterator.next();
		Assert.assertEquals("2019-01-01 00:20:15", dateFormat.format(next));
	}

	@Test
	public void testMinuteIterator() throws Exception {
		Date date = dateFormat.parse("2018-11-01 09:30:10");

		// scheduled time before current time
		ScheduleIterator minuteIterator = SchedulerIteratorFactory.create("minute 15", date);
		Date next = minuteIterator.next();
		Assert.assertEquals("2018-11-01 09:30:15", dateFormat.format(next));

		// scheduled time after current time
		minuteIterator = SchedulerIteratorFactory.create("minute 5", date);
		next = minuteIterator.next();
		Assert.assertEquals("2018-11-01 09:31:05", dateFormat.format(next));

		Date endOfDay = dateFormat.parse("2018-12-31 23:59:10");
		// scheduled time need day jump
		minuteIterator = SchedulerIteratorFactory.create("minute 5", endOfDay);
		next = minuteIterator.next();
		Assert.assertEquals("2019-01-01 00:00:05", dateFormat.format(next));
	}

	@Test
	public void testDaily() throws Exception {
		Date date = dateFormat.parse("2018-11-01 09:30:10");

		// scheduled time before current time
		ScheduleIterator dayIterator = SchedulerIteratorFactory.create("day 8 20 5", date);
		Date next = dayIterator.next();
		Assert.assertEquals("2018-11-02 08:20:05", dateFormat.format(next));

		// scheduled time after current time
		dayIterator = SchedulerIteratorFactory.create("day 10 20 5", date);
		next = dayIterator.next();
		Assert.assertEquals("2018-11-01 10:20:05", dateFormat.format(next));

		Date endOfDay = dateFormat.parse("2018-12-31 23:59:10");
		// scheduled minutes need year jump
		dayIterator = SchedulerIteratorFactory.create("day 10 20 5", endOfDay);
		next = dayIterator.next();
		Assert.assertEquals("2019-01-01 10:20:05", dateFormat.format(next));
	}
}
