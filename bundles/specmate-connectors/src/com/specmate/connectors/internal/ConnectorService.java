package com.specmate.connectors.internal;

import static com.specmate.connectors.internal.config.ConnectorServiceConfig.KEY_POLL_SCHEDULE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.cdo.common.id.CDOWithID;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.internal.config.ConnectorServiceConfig;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;
import com.specmate.persistency.validation.TopLevelValidator;
import com.specmate.scheduler.Scheduler;
import com.specmate.scheduler.SchedulerIteratorFactory;
import com.specmate.scheduler.SchedulerTask;
import com.specmate.search.api.IModelSearchService;

@Component(immediate = true, configurationPid = ConnectorServiceConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ConnectorService {
	CDOWithID id;
	List<IRequirementsSource> requirementsSources = new ArrayList<>();
	private LogService logService;
	private IPersistencyService persistencyService;
	private IModelSearchService modelSearchService;
	private ITransaction transaction;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateException {
		validateConfig(properties);

		String schedule = (String) properties.get(KEY_POLL_SCHEDULE);
		if (schedule == null) {
			return;
		}

		this.transaction = this.persistencyService.openTransaction();
		this.transaction.removeValidator(TopLevelValidator.class.getName());

		new Thread(new Runnable() {
			@Override
			public void run() {

				// Ensure that requirements source are loaded.
				while (requirementsSources.size() == 0) {
					try {
						logService.log(LogService.LOG_INFO, "No requirement sources here yet. Waiting.");
						// Requirements Sources could be added after the
						// component is activated
						Thread.sleep(20 * 1000);
					} catch (InterruptedException e) {
						logService.log(LogService.LOG_ERROR, e.getMessage());
					}
				}

				try {
					SchedulerTask connectorRunnable = new ConnectorTask(requirementsSources, transaction, logService);
					connectorRunnable.run();
					modelSearchService.startReIndex();
					Scheduler scheduler = new Scheduler();
					scheduler.schedule(connectorRunnable, SchedulerIteratorFactory.create(schedule));
				} catch (SpecmateException e) {
					e.printStackTrace();
					logService.log(LogService.LOG_ERROR, "Could not create schedule iterator.", e);
				}
			}
		}, "connector-service-initializer").start();

	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateException {
		SchedulerIteratorFactory.validate((String) properties.get(KEY_POLL_SCHEDULE));
		logService.log(LogService.LOG_DEBUG, "Connector service config validated.");
	}

	@Deactivate
	public void deactivate() {
		transaction.close();
	}

	@Reference(cardinality = ReferenceCardinality.MULTIPLE, policy = ReferencePolicy.DYNAMIC)
	public void addRequirementsConnector(IRequirementsSource source) {
		this.requirementsSources.add(source);
	}

	public void removeRequirementsConnector(IRequirementsSource source) {
		this.requirementsSources.remove(source);
	}

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Reference
	public void setPersistency(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Reference
	public void setModelSearchService(IModelSearchService modelSearchService) {
		this.modelSearchService = modelSearchService;
	}

	public void unsetPersistency(IPersistencyService persistencyService) {
		this.persistencyService = null;
	}
}
