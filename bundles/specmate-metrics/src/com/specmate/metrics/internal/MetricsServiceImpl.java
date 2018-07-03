package com.specmate.metrics.internal;

import io.prometheus.client.Gauge;

public class MetricsServiceImpl {

	public Gauge getGauge(String name, String description) {
		return Gauge.build(name, description).register();
	}
}
