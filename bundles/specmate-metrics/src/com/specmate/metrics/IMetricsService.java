package com.specmate.metrics;

/**
 * A service that collects and exposes metrics.
 * 
 * @author junkerm
 *
 */
public interface IMetricsService {

	/**
	 * Creates a metric of type Gauge
	 * 
	 */
	IGauge createGauge(String name, String description);

}
