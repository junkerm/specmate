package com.specmate.connectors.internal;

import static com.specmate.connectors.internal.config.ConnectorServiceConfig.KEY_POLL_SCHEDULE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import org.eclipse.emf.cdo.common.id.CDOWithID;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.common.SpecmateValidationException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.internal.config.ConnectorServiceConfig;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

import it.sauronsoftware.cron4j.Scheduler;
import it.sauronsoftware.cron4j.SchedulingPattern;

@Component(immediate = true, configurationPid = ConnectorServiceConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ConnectorService {
	CDOWithID id;
	List<IRequirementsSource> requirementsSources = new ArrayList<>();
	private LogService logService;
	private IPersistencyService persistencyService;
	private ScheduledExecutorService scheduler;
	private ITransaction transaction;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException, SpecmateException {
		validateConfig(properties);
		
		String cronStr = (String) properties.get(KEY_POLL_SCHEDULE);
		if(cronStr == null) {
			return;
		}
		
		this.transaction = this.persistencyService.openTransaction();
		Runnable connectorRunnable = new ConnectorJobRunnable(requirementsSources, transaction, logService);
		
		Scheduler scheduler = new Scheduler();
		scheduler.schedule(cronStr, connectorRunnable);
		scheduler.start();
	}

	private void validateConfig(Map<String, Object> properties) throws SpecmateValidationException {
		String cronStr = (String) properties.get(KEY_POLL_SCHEDULE);
		if(!SchedulingPattern.validate(cronStr)) {
			String message = "Cron " + cronStr + " invalid!";
			logService.log(LogService.LOG_ERROR, message);
			throw new SpecmateValidationException(message);
			
		}
		logService.log(LogService.LOG_DEBUG, "Connector service config validated.");
	}

	@Deactivate
	public void deactivate() {
		this.scheduler.shutdown();
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

	public void unsetPersistency(IPersistencyService persistencyService) {
		this.persistencyService = null;
	}
}
