package com.specmate.metrics.internal;

import java.util.HashMap;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.metrics.ICounter;
import com.specmate.metrics.IGauge;
import com.specmate.metrics.IHistogram;
import com.specmate.metrics.IMetricsService;

import io.prometheus.client.Counter;
import io.prometheus.client.Gauge;
import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;

/**
 * Metrics service implementation that exposes metrics in prometheus format. See
 * http://prometheus.io Metrics will be published under
 * http://localhost:[port]/metrics
 *
 * @author junkerm
 *
 */
@Component(immediate = true)
public class MetricsServiceImpl implements IMetricsService {

	/** The http service */
	private HttpService httpService;

	/** The log service */
	private LogService logService;

	private static Map<String, Object> collectors = new HashMap<>();

	@Activate
	public void activate() throws SpecmateException {
		// Default JVM metrics (memory, threads, etc.)
		DefaultExports.initialize();
	}

	private void configureMetricsServlet() throws SpecmateException {
		// Register the metrics servlet
		MetricsServlet metricsServlet = new MetricsServlet();
		try {
			this.httpService.registerServlet("/metrics", metricsServlet, null, null);
		} catch (Exception e) {
			this.logService.log(LogService.LOG_ERROR, "Could not initialize metrics servlet", e);
			throw new SpecmateException(e);
		}
	}

	public <T> T checkIfCreated(Class<T> clazz, String name, String description) throws SpecmateException {
		if (collectors.containsKey(name)) {
			Object collector = collectors.get(name);
			if (clazz.isAssignableFrom(collector.getClass())) {
				return clazz.cast(collector);
			} else {
				throw new SpecmateException(
						"A metric with name " + name + " is already registered, but has a different type.");
			}
		}
		return null;
	}

	private String getMetricName(String name) {
		String theName = "specmate_" + name.toLowerCase();
		return theName;
	}

	@Override
	public synchronized IGauge createGauge(String name, String description) throws SpecmateException {
		String theName = getMetricName(name);
		IGauge gauge = checkIfCreated(IGauge.class, theName, description);
		if (gauge == null) {
			gauge = new PrometheusGaugeImpl(Gauge.build(theName, description).register());
			collectors.put(theName, gauge);
		}
		return gauge;
	}

	@Override
	public synchronized IHistogram createHistogram(String name, String description) throws SpecmateException {
		String theName = getMetricName(name);
		IHistogram histogram = checkIfCreated(IHistogram.class, theName, description);
		if (histogram == null) {
			histogram = new PrometheusHistogramImpl(Histogram.build(theName, description).register());
			collectors.put(theName, histogram);
		}
		return histogram;
	}

	@Override
	public synchronized ICounter createCounter(String name, String description) throws SpecmateException {
		String theName = getMetricName(name);
		ICounter counter = checkIfCreated(ICounter.class, theName, description);
		if (counter == null) {
			counter = new PrometheusCounterImpl(Counter.build(theName, description).register());
			collectors.put(theName, counter);
		}
		return counter;
	}

	@Reference(cardinality = ReferenceCardinality.OPTIONAL)
	public void setHttpService(HttpService httpService) throws SpecmateException {
		this.httpService = httpService;
		configureMetricsServlet();
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
