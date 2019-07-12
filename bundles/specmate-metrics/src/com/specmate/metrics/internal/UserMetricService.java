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
import com.specmate.persistency.IPersistencyService;
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
	
	private IPersistencyService persistencyService;
	private IMetricsService metricsService;
	private IView sessionView;
	
	private IGauge specmate_current_day;
	private IGauge specmate_current_week;
	private IGauge specmate_current_month;
	private IGauge specmate_current_year;
	
	@Activate
	public void start() throws SpecmateException {
		this.sessionView = persistencyService.openView();
		this.specmate_current_day = metricsService.
				createGauge("specmate_login_counter_current_day", "Number of users logged in at the current day");
		this.specmate_current_week = metricsService.
				createGauge("specmate_login_counter_current_week", "Number of users logged in at the current week");
		this.specmate_current_month = metricsService.
				createGauge("specmate_login_counter_current_month", "Number of users logged in at the current month");
		this.specmate_current_year = metricsService.
				createGauge("specmate_login_counter_current_year", "Number of users logged in at the current year");
		activeScheduler();
		initializeAfterResart();
	}
	
	@Deactivate
	public void deactivate() throws SpecmateException {

		if (sessionView != null) {
			sessionView.close();
		}
	}
	
	private void initializeAfterResart() {
		// initialize counter after restart with the currently active sessions
		
		/*
		 * I want to get all Sessions which have a lager last avtive tag but remove the ones where the user name occurs multiple times
		 * */
	}
	
	public void activeScheduler() {
		// Create different schedulers for the different counters
		try {
			// Each minute at 00:00:05
			String scheduleDay = "minute 2";
			SchedulerTask metricRunnable = new MetricTask(CounterType.CURRENTDAY, specmate_current_day, sessionView);
			metricRunnable.run();
			Scheduler scheduler = new Scheduler();
			scheduler.schedule(metricRunnable, SchedulerIteratorFactory.create(scheduleDay));
			// Get the resetted counter back
			specmate_current_day = ((MetricTask) metricRunnable).getGauge();
			
			// Each minute at 00:00:05
			String scheduleWeek = "minute 4";
			SchedulerTask metricRunnableWeek = new MetricTask(CounterType.CURRENTWEEK, specmate_current_week, sessionView);
			metricRunnable.run();
			Scheduler schedulerWeek = new Scheduler();
			schedulerWeek.schedule(metricRunnableWeek, SchedulerIteratorFactory.create(scheduleWeek));
			// Get the resetted counter back
			specmate_current_week = ((MetricTask) metricRunnableWeek).getGauge();

			// Each minute at 00:00:05
			String scheduleMonth = "minute 6";
			SchedulerTask metricRunnableMonth = new MetricTask(CounterType.CURRENTMONTH, specmate_current_month, sessionView);
			metricRunnableMonth.run();
			Scheduler schedulerMonth = new Scheduler();
			schedulerMonth.schedule(metricRunnableMonth, SchedulerIteratorFactory.create(scheduleMonth));
			// Get the resetted counter back
			specmate_current_month = ((MetricTask) metricRunnableMonth).getGauge();
			
			// Each minute at 00:00:05
			String scheduleYear = "minute 8";
			SchedulerTask metricRunnableYear = new MetricTask(CounterType.CURRENTYEAR, specmate_current_year, sessionView);
			metricRunnableYear.run();
			Scheduler schedulerYear = new Scheduler();
			schedulerYear.schedule(metricRunnableYear, SchedulerIteratorFactory.create(scheduleYear));
			// Get the resetted counter back
			specmate_current_year = ((MetricTask) metricRunnableYear).getGauge();
		} catch (SpecmateException e) {
			// TODO Auto-generated catch block
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
	
	@Reference
	public void setMetricsService(IMetricsService metricsService) {
		this.metricsService = metricsService;
	}

	@Reference 
	public void setPersistencyService(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}
}