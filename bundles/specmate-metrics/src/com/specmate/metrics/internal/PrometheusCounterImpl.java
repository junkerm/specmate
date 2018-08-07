package com.specmate.metrics.internal;

import com.specmate.metrics.ICounter;

import io.prometheus.client.Counter;

public class PrometheusCounterImpl implements ICounter {

	private Counter counter;

	public PrometheusCounterImpl(Counter counter) {
		this.counter = counter;
	}

	@Override
	public void inc() {
		this.counter.inc();
	}

}
