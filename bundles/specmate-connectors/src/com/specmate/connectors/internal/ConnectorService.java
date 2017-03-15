package com.specmate.connectors.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

@Component(immediate = true)
public class ConnectorService {

	List<IRequirementsSource> requirementsSources = new ArrayList<>();
	private LogService logService;
	private IPersistencyService persistencyService;
	private ScheduledExecutorService scheduler;

	@Activate
	public void activate() {
		this.scheduler = Executors.newScheduledThreadPool(1);
		this.scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				syncRequirementsFromSources();
			}
		}, 0 /* Startverzögerung */, 2 /* Dauer */, TimeUnit.SECONDS);
	}

	@Deactivate
	public void deactivate() {
		this.scheduler.shutdown();
	}

	private void syncRequirementsFromSources() {
		logService.log(LogService.LOG_INFO, "Synchronizing requirements");
		ITransaction transaction = this.persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		for (IRequirementsSource source : requirementsSources) {
			logService.log(LogService.LOG_INFO, "Retrieving requirements from " + source.getId());
			try {
				IContainer requirements = source.getRequirements();
				logService.log(LogService.LOG_INFO,
						"Retrieved " + requirements.getContents().size() + " requirements.");
				IContainer localContainer = getOrCreateLocalContainer(resource, source.getId());
				syncContainers(localContainer, requirements);
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, e.getMessage());
			}

		}
	}

	private void syncContainers(IContainer localContainer, IContainer requirements) {
		//Build hashset (extid -> requirement) for local requirements
		TreeIterator<EObject> iterator = localContainer.eAllContents();
		HashMap<String,EObject> localRequirements = new HashMap<String, EObject>();
		iterator.
		
		//Build hashset (extid -> requirement) for remote requirements
		
		//find new requirements
		
		//add new requirements to local container and all folders on the way

	}

	private IContainer getOrCreateLocalContainer(Resource resource, String name) {
		EObject object = SpecmateEcoreUtil.getEObjectWithId(name, resource.getContents());
		if (object != null) {
			if (object instanceof IContainer) {
				return (IContainer) object;
			}
		}

		Folder folder = BaseFactory.eINSTANCE.createFolder();
		folder.setName(name);
		// TODO: sanitize id
		folder.setId(name);
		resource.getContents().add(folder);
		return folder;
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

	public void unsetPersistency() {
		this.persistencyService = null;
	}
}
