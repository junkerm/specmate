package com.specmate.metrics.internal;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.http.HttpService;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.metrics.IGauge;
import com.specmate.metrics.IMetricsService;

import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.MetricsServlet;
import io.prometheus.client.hotspot.DefaultExports;

/**
 * Metrics service implementation that exposes metrics in prometheus format. See
 * http://prometheus.io
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

	@Activate
	public void activate() throws SpecmateException {
		// Default JVM metrics (memory, threads, etc.)
		DefaultExports.initialize();

		// Register the metrics servlet
		MetricsServlet metricsServlet = new MetricsServlet();
		try {
			this.httpService.registerServlet("/metrics", metricsServlet, null, null);
		} catch (Exception e) {
			this.logService.log(LogService.LOG_ERROR, "Could not initialize metrics servlet", e);
			throw new SpecmateException(e);
		}
	}

	@Override
	public IGauge createGauge(String name, String description) {
		String theName = "specmate_" + name.toLowerCase();
		return new PrometheusGaugeImpl(Gauge.build(theName, description).register());
	}

	@Reference
	public void setHttpService(HttpService httpService) {
		this.httpService = httpService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
}
