package com.specmate.testspecification.internal.services;

import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.emfrest.api.IRestService;
import com.specmate.emfrest.api.RestServiceBase;
import com.specmate.metrics.ICounter;
import com.specmate.metrics.IMetricsService;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.rest.RestResult;
import com.specmate.testspecification.api.ITestSpecificationExporter;

/** Service that exports a test specification in various formats */
@Component(immediate = true, service = IRestService.class)
public class TestSpecificationExportService extends RestServiceBase {
	private final String LPARAM = "language";
	private Map<String, ITestSpecificationExporter> exporters = new HashMap<String, ITestSpecificationExporter>();
	private IMetricsService metricsService;
	private ICounter exportCounter;
	private LogService logService;

	@Activate
	public void activate() throws SpecmateException {
		exportCounter = metricsService.createCounter("export_counter", "Total number of exported test specifications");
	}

	@Override
	public String getServiceName() {
		return "generateTestSkeleton";
	}

	@Override
	public boolean canGet(Object object) {
		return object instanceof TestSpecification;
	}

	@Override
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {

		String language = queryParams.getFirst(LPARAM);
		if (language == null) {
			throw new SpecmateValidationException("Language for test skeleton not specified.");
		}

		ITestSpecificationExporter generator = exporters.get(language);
		if (generator == null) {
			throw new SpecmateValidationException("Generator for langauge " + language + " does not exist.");
		}

		if (!(object instanceof TestSpecification)) {
			throw new SpecmateValidationException(
					"Cannot generate test skeleton for object of type " + object.getClass().getName());
		}

		TestSpecification ts = (TestSpecification) object;

		exportCounter.inc();

		return new RestResult<TestSpecificationSkeleton>(Response.Status.OK, generator.generate(ts));
	}

	@Reference
	public void setMetricsService(IMetricsService metricsService) {
		this.metricsService = metricsService;
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addTestSpecificationExporter(ITestSpecificationExporter exporter) {
		if (exporters.containsKey(exporter.getLanguage())) {
			logService.log(LogService.LOG_WARNING, "Test specification exporter for langugae " + exporter.getLanguage()
					+ " already exists. Ignoring: " + exporter.getClass().getName());
		}
		exporters.put(exporter.getLanguage(), exporter);
	}

	public void removeTestSpecificationExporter(ITestSpecificationExporter exporter) {
		ITestSpecificationExporter existing = exporters.get(exporter.getLanguage());
		if (existing == exporter) {
			exporters.remove(exporter.getLanguage());
		}
	}
}
