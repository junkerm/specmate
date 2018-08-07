package com.specmate.metrics.internal;

import com.specmate.metrics.IHistogram;
import com.specmate.metrics.ITimer;

import io.prometheus.client.Histogram;

public class PrometheusHistogramImpl implements IHistogram {

	private Histogram histogram;

	public PrometheusHistogramImpl(Histogram histogram) {
		this.histogram = histogram;
	}

	@Override
	public ITimer startTimer() {
		return new PrometheusTimerImpl(histogram.startTimer());
	}

}
