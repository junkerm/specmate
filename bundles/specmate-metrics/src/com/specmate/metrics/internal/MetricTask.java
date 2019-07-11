package com.specmate.metrics.internal;

import com.specmate.scheduler.SchedulerTask;

public class MetricTask extends SchedulerTask  {
	
	public MetricTask () {
		// Übergebe die COunter hier 
	}

	@Override
	public void run() {
		// Reset counter to 0 
		resetCounter();
	}
	
	private void resetCounter() {
		
	}

}
