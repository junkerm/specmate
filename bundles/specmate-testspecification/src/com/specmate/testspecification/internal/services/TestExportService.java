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
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.rest.RestResult;
import com.specmate.testspecification.api.ITestExporter;

/** Service that exports a test specification in various formats */
@Component(immediate = true, service = IRestService.class)
public class TestExportService extends RestServiceBase {
	private final String LANGUAGE_PARAM = "language";
	private Map<String, ITestExporter> testSpecificationExporters = new HashMap<String, ITestExporter>();
	private Map<String, ITestExporter> testProcedureExporters = new HashMap<String, ITestExporter>();
	private IMetricsService metricsService;
	private ICounter exportCounter;
	private LogService logService;

	@Activate
	public void activate() throws SpecmateException {
		exportCounter = metricsService.createCounter("export_counter", "Total number of exported test specifications");
	}

	@Override
	public String getServiceName() {
		return "export";
	}

	@Override
	public boolean canGet(Object object) {
		return (object instanceof TestSpecification) || (object instanceof TestProcedure);
	}

	@Override
	public RestResult<?> get(Object object, MultivaluedMap<String, String> queryParams, String token)
			throws SpecmateException {

		String language = queryParams.getFirst(LANGUAGE_PARAM);
		if (language == null) {
			throw new SpecmateValidationException("Language for export not specified.");
		}
		ITestExporter generator = null;
		if (object instanceof TestSpecification) {
			generator = testSpecificationExporters.get(language);
		} else if (object instanceof TestProcedure) {
			generator = testProcedureExporters.get(language);
		}
		if (generator == null) {
			throw new SpecmateValidationException("Generator for langauge " + language + " does not exist.");
		}
		exportCounter.inc();
		return new RestResult<TestSpecificationSkeleton>(Response.Status.OK, generator.generate(object));
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
	public void addTestSpecificationExporter(ITestExporter exporter) {
		if (exporter.canExportTestProcedure()) {
			addToExporterCollection(exporter, testProcedureExporters);
		}
		if (exporter.canExportTestSpecification()) {
			addToExporterCollection(exporter, testSpecificationExporters);
		}
	}

	public void removeTestSpecificationExporter(ITestExporter exporter) {
		ITestExporter existing = testSpecificationExporters.get(exporter.getLanguage());
		if (existing == exporter) {
			testSpecificationExporters.remove(exporter.getLanguage());
		}
	}

	private void addToExporterCollection(ITestExporter exporter, Map<String, ITestExporter> exporterMap) {
		if (exporterMap.containsKey(exporter.getLanguage())) {
			logService.log(LogService.LOG_WARNING, "Test exporter for langugae " + exporter.getLanguage()
					+ " already exists. Ignoring: " + exporter.getClass().getName());
		}
		exporterMap.put(exporter.getLanguage(), exporter);
	}
}
