package com.specmate.metrics.internal;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import com.specmate.scheduler.*;
import com.specmate.common.exception.SpecmateException;
import com.specmate.persistency.IView;
import com.specmate.usermodel.UsermodelFactory;

@Component(immediate=true)
public class UserMetricService {
	// When Specmate starts again check if we need to initialize the the counter to some value 
	// 1. initialize counter after restart with the currently active sessions
	// 		1.0 sort after the most recent ones (we need sorting in order to avoid counting one user with multiple session multiple times
	// 		1.1 get all sessions in certain lastActive time frame to initialize counter again 
	// 			SpecmateUsersLoggedIn_currentDay UserSession.getAllInstances() where u.lastActive < 24 h 
	// 		1.2 If we have an inactive session we need to 
	// 			
	// 2. increase counter with isNewUserWeekly (check if the user was active the last 7 days) 
	// 3. schedule at Sunday 0:00 and at each Day 0:00 that the respective counter (day, week, month) is reseted 
	
	// @Activator
	// use for starting the scheduler service 
	// initialize counter after restart
	
	@Activate
	public void start() {
		activeScheduler();
		initializeAfterResart();
	}
	
	private void initializeAfterResart() {
		// initialize counter after restart with the currently active sessions
	}
	
	public void activeScheduler() {
		// TODO: set time intervall of scheduled job
		try {
			String schedule = "MetricSchedule";
			SchedulerTask metricRunnable = new MetricTask();
			metricRunnable.run();
			Scheduler scheduler = new Scheduler();
			scheduler.schedule(metricRunnable, SchedulerIteratorFactory.create(schedule));
		} catch (SpecmateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void loginCounter(IView sessionView, String userName) {
		// Increment the different counters
		if (isNewUserCurrentDay(sessionView, userName)) {
			
		}
		if (isNewUserCurrentWeek(sessionView, userName)) {
			
		}
		if (isNewUserCurrentMonth(sessionView, userName)) {
			
		}
		if (isNewUserCurrentYear(sessionView, userName)) {
			
		}
	}
	/**
	 * 
	 * @param sessionView
	 * @param userName
	 * @param difference 
	 * @return Returns if the user with the userName has been logged in in the specified time difference 
	 */
	private boolean isNewUser(IView sessionView, String userName, long difference) {
		
		String query = "UserSession.allInstances()->select(u | u.userName='" + userName + "' and u.lastActive>'" + difference + "')";

		List<Object> results = sessionView.query(query,
				UsermodelFactory.eINSTANCE.getUsermodelPackage().getUserSession());

		if (results.size() > 0) {
			return false;
		}
		return true;
	}
	
	private boolean isNewUserCurrentDay(IView sessionView, String userName) {
		long oneDay = 1000*60*60*24;
		long now = new Date().getTime();
		long difference = now - oneDay;
		
		return isNewUser(sessionView, userName, difference);
	}
	
	private boolean isNewUserCurrentWeek(IView sessionView, String userName) {
		long oneWeek = 1000*60*60*24*7;
		long now = new Date().getTime();
		long difference = now - oneWeek;
		
		return isNewUser(sessionView, userName, difference);
	}
	
	private boolean isNewUserCurrentMonth(IView sessionView, String userName) {
		// TODO: depending on the month we need to multiply with either 30 or 31 or 28 
		int daysInCurrentMonth = getDaysInMonth();
		long oneMonth = 1000*60*60*24*7*daysInCurrentMonth;
		long now = new Date().getTime();
		long difference = now - oneMonth;
		
		return isNewUser(sessionView, userName, difference);
	}
	
	private boolean isNewUserCurrentYear(IView sessionView, String userName) {
		int daysInCurrentMonth = getDaysInMonth();
		int daysInCurrentYear = getDaysInYear();
		long oneYear = 1000*60*60*24*7*daysInCurrentMonth*daysInCurrentYear;
		long now = new Date().getTime();
		long difference = now - oneYear;
		
		return isNewUser(sessionView, userName, difference);
	}
	
	private int getDaysInMonth() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.getActualMaximum(Calendar.DAY_OF_MONTH);
	}
	
	private int getDaysInYear() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		return c.getActualMaximum(Calendar.DAY_OF_YEAR);
	}

}
