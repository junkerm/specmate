package com.specmate.metrics.internal;

import com.specmate.metrics.IGauge;

import io.prometheus.client.Gauge;

/** Implementation of a gauge metric based on the prometheus client. */
public class PrometheusGaugeImpl implements IGauge {

	private Gauge prometheusGauge;

	public PrometheusGaugeImpl(Gauge prometheusGauge) {
		this.prometheusGauge = prometheusGauge;
	}

	@Override
	public void inc() {
		prometheusGauge.inc();
	}

	@Override
	public void dec() {
		prometheusGauge.dec();
	}

	@Override
	public void dec(double amt) {
		prometheusGauge.dec(amt);
	}

	@Override
	public void inc(double amt) {
		prometheusGauge.inc(amt);
	}
	
	@Override
	public void set(double val) {
		prometheusGauge.set(val);
	}
	
	@Override
	public double get() {
		return prometheusGauge.get();
	}
}
