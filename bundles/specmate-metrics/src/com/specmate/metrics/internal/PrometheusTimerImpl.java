package com.specmate.metrics.internal;

import com.specmate.metrics.ITimer;

import io.prometheus.client.Histogram.Timer;

public class PrometheusTimerImpl implements ITimer {

	private Timer timer;

	public PrometheusTimerImpl(Timer timer) {
		this.timer = timer;
	}

	@Override
	public void observeDuration() {
		this.timer.observeDuration();
	}

}
