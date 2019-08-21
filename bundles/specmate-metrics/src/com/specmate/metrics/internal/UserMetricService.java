package com.specmate.metrics.internal;

import java.util.List;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

import com.specmate.scheduler.*;
import com.specmate.common.exception.SpecmateException;
import com.specmate.metrics.IGauge;
import com.specmate.metrics.IMetricsService;
import com.specmate.metrics.IUserMetricsService;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.IView;
import com.specmate.usermodel.UsermodelFactory;

@Component(immediate=true)
public class UserMetricService implements IUserMetricsService {
	/*	
	 * 1. When Specmate starts again initialize the counter 
	 * 2. increase counter with isNewUserWeekly (check if the user was active the last 7 days) 
	 * 3. schedule at Sunday 0:00 and at each Day 0:00 that the respective counter (day, week, month) is reseted 
	 * 
	 */
	
	
	private IPersistencyService persistencyService;
	private IMetricsService metricsService;
	private IView sessionView;
	
	private IGauge specmate_current_day;
	private IGauge specmate_current_week;
	private IGauge specmate_current_month;
	private IGauge specmate_current_year;
	
	@Activate
	public void start() throws SpecmateException {
		this.specmate_current_day = metricsService.
				createGauge("specmate_login_counter_current_day", "Number of users logged in at the current day");
		this.specmate_current_week = metricsService.
				createGauge("specmate_login_counter_current_week", "Number of users logged in at the current week");
		this.specmate_current_month = metricsService.
				createGauge("specmate_login_counter_current_month", "Number of users logged in at the current month");
		this.specmate_current_year = metricsService.
				createGauge("specmate_login_counter_current_year", "Number of users logged in at the current year");
		//activeScheduler();
		//initializeAfterResart();
	}
	
	@Deactivate
	public void deactivate() throws SpecmateException {

		if (sessionView != null) {
			sessionView.close();
		}
	}
	
	private void initializeAfterResart() {
		// initialize counter after restart with the currently active sessions
		initializeGaugeCurrentDay();
		initializeGaugeCurrentWeek();
		initializeGaugeCurrentMonth();
		initializeGaugeCurrentYear();
	}
	
	private void activeScheduler() {
		// Create different schedulers for the different counters
		//TODO: change schedule Time
		try {
			String scheduleDay = "minute 2";
			SchedulerTask metricRunnable = new MetricTask(CounterType.CURRENTDAY, specmate_current_day, sessionView);
			metricRunnable.run();
			Scheduler scheduler = new Scheduler();
			scheduler.schedule(metricRunnable, SchedulerIteratorFactory.create(scheduleDay));
			// Get the resetted counter back
			specmate_current_day = ((MetricTask) metricRunnable).getGauge();
			
			String scheduleWeek = "minute 4";
			SchedulerTask metricRunnableWeek = new MetricTask(CounterType.CURRENTWEEK, specmate_current_week, sessionView);
			metricRunnable.run();
			Scheduler schedulerWeek = new Scheduler();
			schedulerWeek.schedule(metricRunnableWeek, SchedulerIteratorFactory.create(scheduleWeek));
			// Get the resetted counter back
			specmate_current_week = ((MetricTask) metricRunnableWeek).getGauge();

			String scheduleMonth = "minute 6";
			SchedulerTask metricRunnableMonth = new MetricTask(CounterType.CURRENTMONTH, specmate_current_month, sessionView);
			metricRunnableMonth.run();
			Scheduler schedulerMonth = new Scheduler();
			schedulerMonth.schedule(metricRunnableMonth, SchedulerIteratorFactory.create(scheduleMonth));
			// Get the resetted counter back
			specmate_current_month = ((MetricTask) metricRunnableMonth).getGauge();
			
			String scheduleYear = "minute 8";
			SchedulerTask metricRunnableYear = new MetricTask(CounterType.CURRENTYEAR, specmate_current_year, sessionView);
			metricRunnableYear.run();
			Scheduler schedulerYear = new Scheduler();
			schedulerYear.schedule(metricRunnableYear, SchedulerIteratorFactory.create(scheduleYear));
			// Get the resetted counter back
			specmate_current_year = ((MetricTask) metricRunnableYear).getGauge();
		} catch (SpecmateException e) {
			e.printStackTrace();
		}
	}
	
	public void loginCounter(IView sessionView, String userName) {
		// Increment the different counters
		if (isNewUserCurrentDay(sessionView, userName)) {
			specmate_current_day.inc();
		}
		if (isNewUserCurrentWeek(sessionView, userName)) {
			specmate_current_week.inc();
		}
		if (isNewUserCurrentMonth(sessionView, userName)) {
			specmate_current_month.inc();
		}
		if (isNewUserCurrentYear(sessionView, userName)) {
			specmate_current_year.inc();
		}
	}
	/**
	 * 
	 * @param sessionView
	 * @param userName
	 * @param difference 
	 * @return Returns if the user with the userName has been logged in in the specified time difference 
	 * @throws SpecmateException 
	 */
	private boolean isNewUser(IView sessionView, String userName, long difference) {
		try {
			this.sessionView = persistencyService.openView();
		} catch (SpecmateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//String query = "UserSession.allInstances()->select(u | u.userName='" + userName + "' and u.lastActive> " + difference + " )";
		
		String sqlQuery = "SELECT DISTINCT username FROM UserSession WHERE username="+userName+" AND lastActive>"+difference;

		List<Object> results = sessionView.querySQL(sqlQuery,
				UsermodelFactory.eINSTANCE.getUsermodelPackage().getUserSession());

		if (sessionView != null) {
			sessionView.close();
		}
		if (results.size() > 0) {
			return false;
		}
		return true;
	}
	
	private boolean isNewUserCurrentDay(IView sessionView, String userName) {
		long difference = TimeUtil.getDiffDay();
		
		return isNewUser(sessionView, userName, difference);
	}
	
	private boolean isNewUserCurrentWeek(IView sessionView, String userName) {
		long difference = TimeUtil.getDiffWeek();
		
		return isNewUser(sessionView, userName, difference);
	}
	
	private boolean isNewUserCurrentMonth(IView sessionView, String userName) {
		long difference = TimeUtil.getDiffMonth();
		
		return isNewUser(sessionView, userName, difference);
	}
	
	private boolean isNewUserCurrentYear(IView sessionView, String userName) {
		long difference = TimeUtil.getDiffYear(); 
		
		return isNewUser(sessionView, userName, difference);
	}
	
	private void initializeGauge(long difference, IGauge gauge) {
		try {
			this.sessionView = persistencyService.openView();
		} catch (SpecmateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Use the session view to identify how many times we need to decrement the counter
		//String query = "UserSession.allInstances()->select(u | u.lastActive>" + difference + "->forAll(user1 | user1 <> self implies user1.userName <> self.userName))";
		
		String sqlQuery = "SELECT DISTINCT username FROM UserSession WHERE lastActive>"+difference;

		List<Object> results = sessionView.querySQL(sqlQuery,
				UsermodelFactory.eINSTANCE.getUsermodelPackage().getUserSession());
		int numberOfSessions = results.size();

		while(numberOfSessions>0) {
			gauge.inc();
			numberOfSessions--;
		}
		if (sessionView != null) {
			sessionView.close();
		}
	}
	
	private void initializeGaugeCurrentDay() {
		IGauge gauge = getCurrentGauge(CounterType.CURRENTDAY);
		initializeGauge(TimeUtil.getDiffDay(), gauge);
	}
	
	private void initializeGaugeCurrentWeek() {
		IGauge gauge = getCurrentGauge(CounterType.CURRENTWEEK);
		initializeGauge(TimeUtil.getDiffWeek(), gauge);
	}
	
	private void initializeGaugeCurrentMonth() {
		IGauge gauge = getCurrentGauge(CounterType.CURRENTMONTH);
		initializeGauge(TimeUtil.getDiffMonth(), gauge);
	}
	
	private void initializeGaugeCurrentYear() {
		IGauge gauge = getCurrentGauge(CounterType.CURRENTYEAR);
		initializeGauge(TimeUtil.getDiffYear(), gauge);
	}
	 
	private IGauge getCurrentGauge(CounterType counterType) {
		IGauge gauge = null;
		switch (counterType) {
		case CURRENTDAY:
			gauge = specmate_current_day;
		case CURRENTMONTH:
			gauge = specmate_current_week;
		case CURRENTWEEK:
			gauge = specmate_current_month;
		case CURRENTYEAR:
			gauge = specmate_current_year; 
		}
		return gauge;
	}

	@Reference
	public void setMetricsService(IMetricsService metricsService) {
		this.metricsService = metricsService;
	}

	//@Reference 
	//public void setPersistencyService(IPersistencyService persistencyService) {
		//this.persistencyService = persistencyService;
	//}
}