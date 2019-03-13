package com.specmate.emfrest.internal.metrics;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.MultivaluedMap;

import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.metrics.ICounter;
import com.specmate.metrics.IHistogram;
import com.specmate.metrics.IMetricsService;
import com.specmate.metrics.ITimer;

public class MetricsFilter implements ContainerRequestFilter, ContainerResponseFilter {

	private static final String TRACKER_TIMER = "prometheus.timer";

	protected final ResourceInfo resourceInfo;
	private IHistogram tracker;

	private AMetric annotation;

	private IMetricsService metricsService;

	private LogService logService;

	private ICounter response_5xx;

	private ICounter response_4xx;

	private ICounter response_3xx;

	private ICounter response_2xx;

	private ICounter requests;

	/**
	 * Registers a filter specifically for the defined method.
	 *
	 * @param resourceInfo
	 *            - the resource (uri ==> class + method) we are registering this
	 *            filter for
	 * @param prefix
	 *            - the prefix we should apply to all metrics (if any)
	 * @param annotation
	 * @throws SpecmateException
	 */
	public MetricsFilter(IMetricsService metricsService, LogService logService, ResourceInfo resourceInfo,
			AMetric annotation) throws SpecmateException {
		this.resourceInfo = resourceInfo;
		this.annotation = annotation;
		this.metricsService = metricsService;
		this.logService = logService;

		getGeneralMetics();

		buildTimerFromAnnotation(annotation);
	}

	private void getGeneralMetics() throws SpecmateException {

		this.requests = metricsService.createCounter("requests", "Total number of requests");
		this.response_5xx = metricsService.createCounter("response_5xx", "Responses with code 5xx");
		this.response_4xx = metricsService.createCounter("response_4xx", "Responses with code 4xx");
		this.response_3xx = metricsService.createCounter("response_3xx", "Responses with code 3xx");
		this.response_2xx = metricsService.createCounter("response_2xx", "Responses with code 2xx");
	}

	/**
	 * if the annotation is fully specified, use it.
	 *
	 * @param annotation
	 *            - provides us a name and help
	 */
	private void buildTimerFromAnnotation(AMetric annotation) {
		if (annotation != null && annotation.help().length() > 0 && annotation.name().length() > 0) {
			try {
				tracker = metricsService.createHistogram(annotation.name(), annotation.help());
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, "Could not register jersey metric " + annotation.name() + ".", e);
			}
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		requests.inc();
		if (tracker == null) { // we only need to do this once
			try {
				buildTracker(requestContext);
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, "Could not create metrics tracker.", e);
			}
		}

		ITimer timer = tracker.startTimer();
		requestContext.setProperty(TRACKER_TIMER, timer);
	}

	private void buildTracker(ContainerRequestContext requestContext) throws SpecmateException {
		String path = annotation == null ? "" : annotation.help();

		if (path.length() == 0) {
			// this won't change from request to request
			MultivaluedMap<String, String> pathParameters = requestContext.getUriInfo().getPathParameters();

			path = path(requestContext.getUriInfo().getRequestUri());

			for (Map.Entry<String, List<String>> entry : pathParameters.entrySet()) {
				final String originalPathFragment = String.format("{%s}", entry.getKey());

				for (String currentPathFragment : entry.getValue()) {
					path = path.replace(currentPathFragment, originalPathFragment);
				}
			}
		}

		String name = annotation == null ? "" : annotation.name();

		if (name.length() == 0) {
			// we cannot use the class name as it is always a proxy
			name = resourceInfo.getResourceMethod().getName();
		}

		tracker = metricsService.createHistogram(name, path);
	}

	/**
	 * Returns path of given URI. If the first character of path is '/' then it is
	 * removed.
	 *
	 * @author Pavol Loffay
	 * @param uri
	 * @return path or null
	 */
	public static String path(URI uri) {
		String path = uri.getPath();
		if (path != null && path.startsWith("/")) {
			path = path.substring(1);
		}

		return path;
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		ITimer timer = ITimer.class.cast(requestContext.getProperty(TRACKER_TIMER));

		if (timer != null) {
			timer.observeDuration();
		}

		if (responseContext.getStatus() >= 500) {
			response_5xx.inc();
		} else if (responseContext.getStatus() >= 400) {
			response_4xx.inc();
		} else if (responseContext.getStatus() >= 300) {
			response_3xx.inc();
		} else if (responseContext.getStatus() >= 200) {
			response_2xx.inc();
		}
	}
}
