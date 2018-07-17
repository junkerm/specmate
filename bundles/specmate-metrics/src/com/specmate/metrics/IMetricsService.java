package com.specmate.metrics;

import com.specmate.common.SpecmateException;

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
	 * @throws SpecmateException
	 *
	 */
	IGauge createGauge(String name, String description) throws SpecmateException;

	IHistogram createHistogram(String name, String description) throws SpecmateException;

	ICounter creatCounter(String name, String description) throws SpecmateException;

}
