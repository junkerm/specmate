package com.specmate.metrics.internal;

import org.osgi.service.component.annotations.Component;

@Component(immediate=true)
public class UserMetricService {
	// When Specmate starts again check if we need to initialise the the counter to some value 
	// 1. initialize counter after restart with the currently active sessions
	// 2. increase counter with isNewUserWeekly (check if the user was active the last 7 days) 
	// 3. schedule at Sunday 0:00 and at each Day 0:00 that the respective counter (day, week, month) is reseted 
	
	//@Activator
	// use for starting the scheduler service 
	
	// methods
	// isNewUserDaily, isNewUserWeekly, isNewUserMonthly, isNewUserYearly

}
