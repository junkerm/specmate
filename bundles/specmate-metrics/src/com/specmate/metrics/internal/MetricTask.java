package com.specmate.metrics.internal;

import java.util.List;

import com.specmate.metrics.IGauge;
import com.specmate.persistency.IView;
import com.specmate.scheduler.SchedulerTask;
import com.specmate.usermodel.UsermodelFactory;

public class MetricTask extends SchedulerTask  {
	
	private CounterType counterType; 
	private IGauge gauge;
	private IView sessionView;
	
	public MetricTask (CounterType counterType, IGauge gauge, IView sessionView) {
		this.counterType = counterType;
		this.gauge = gauge;
		this.sessionView = sessionView;
	}

	@Override
	public void run() {
		resetCounter();
	}
	
	private void resetCounter() {
		switch (counterType) {
		case CURRENTDAY: 
			resetGauge(TimeUtil.getDiffDay());
			break;
		case CURRENTWEEK:
			resetGauge(TimeUtil.getDiffWeek());
			break;
		case CURRENTMONTH: 
			resetGauge(TimeUtil.getDiffMonth());
			break;
		case CURRENTYEAR: 
			resetGauge(TimeUtil.getDiffYear());
			break;
		}		
	}
	
	private void resetGauge(long difference) {
		// Use the session view to identify how many times we need to decrement the counter 
		String query = "UserSession.allInstances()->select(u | u.lastActive>" + difference + ")";

		List<Object> results = sessionView.query(query,
				UsermodelFactory.eINSTANCE.getUsermodelPackage().getUserSession());
		int numberOfSessions = results.size();

		while(numberOfSessions>0) {
			gauge.dec();
			numberOfSessions--;
		}
	}
	
	public IGauge getGauge() {
		return this.gauge;
	}
	

}
