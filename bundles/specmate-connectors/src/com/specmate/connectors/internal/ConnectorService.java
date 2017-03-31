package com.specmate.connectors.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.Stack;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.common.id.CDOWithID;
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
import com.specmate.model.requirements.Requirement;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

@Component(immediate = true)
public class ConnectorService {
	CDOWithID id;
	List<IRequirementsSource> requirementsSources = new ArrayList<>();
	private LogService logService;
	private IPersistencyService persistencyService;
	private ScheduledExecutorService scheduler;
	private ITransaction transaction;

	@Activate
	public void activate() {
		this.transaction = this.persistencyService.openTransaction();
		this.scheduler = Executors.newScheduledThreadPool(1);
		this.scheduler.scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				syncRequirementsFromSources();
			}
		}, 0 /* start delay */, 30 /* wait time */, TimeUnit.SECONDS);
	}

	@Deactivate
	public void deactivate() {
		this.scheduler.shutdown();
		transaction.close();
	}

	private void syncRequirementsFromSources() {
		logService.log(LogService.LOG_INFO, "Synchronizing requirements");
		Resource resource = transaction.getResource();
		for (IRequirementsSource source : requirementsSources) {
			logService.log(LogService.LOG_INFO, "Retrieving requirements from " + source.getId());
			try {
				IContainer requirements = source.getRequirements();
				if (requirements == null) {
					continue;
				}
				IContainer localContainer = getOrCreateLocalContainer(resource, source.getId());
				syncContainers(localContainer, requirements);
				transaction.commit();
			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, e.getMessage());
				transaction.rollback();
			}

		}
	}

	private void syncContainers(IContainer localContainer, IContainer requirements) {
		// Build hashset (extid -> requirement) for local requirements
		TreeIterator<EObject> localIterator = localContainer.eAllContents();
		HashMap<String, EObject> localRequirementsMap = new HashMap<>();
		buildExtIdMap(localIterator, localRequirementsMap);

		// Build hashset (extid -> requirement) for remote requirements
		TreeIterator<EObject> remoteIterator = requirements.eAllContents();
		HashMap<String, EObject> remoteRequirementsMap = new HashMap<>();
		buildExtIdMap(remoteIterator, remoteRequirementsMap);
		logService.log(LogService.LOG_INFO, "Retrieved " + remoteRequirementsMap.entrySet().size() + " requirements.");

		// find new requirements
		remoteRequirementsMap.keySet().removeAll(localRequirementsMap.keySet());

		logService.log(LogService.LOG_INFO, "Adding " + remoteRequirementsMap.size() + " new requirements.");

		// add new requirements to local container and all folders on the way
		for (Entry<String, EObject> entry : remoteRequirementsMap.entrySet()) {
			Requirement requirementToAdd = (Requirement) entry.getValue();
			IContainer currentContainer = (IContainer) requirementToAdd.eContainer();
			Stack<IContainer> ancestorContainers = new Stack<>();
			while (currentContainer != requirements) {
				ancestorContainers.push(currentContainer);
				currentContainer = (IContainer) currentContainer.eContainer();
			}
			currentContainer = localContainer;
			IContainer foundContainer = localContainer;
			while (!ancestorContainers.isEmpty()) {
				IContainer nextContainer = ancestorContainers.pop();
				foundContainer = (IContainer) SpecmateEcoreUtil.getEObjectWithId(nextContainer.getId(),
						currentContainer.eContents());
				if (foundContainer != null) {
					currentContainer = foundContainer;
				} else {
					logService.log(LogService.LOG_DEBUG, "Creating new folder " + nextContainer.getName());
					foundContainer = BaseFactory.eINSTANCE.createFolder();
					SpecmateEcoreUtil.copyAttributeValues(nextContainer, foundContainer);
					currentContainer.getContents().add(foundContainer);
					currentContainer = foundContainer;
				}
			}
			logService.log(LogService.LOG_DEBUG, "Adding requirement " + requirementToAdd.getId());
			currentContainer.getContents().add(requirementToAdd);
		}
	}

	private void buildExtIdMap(TreeIterator<EObject> iterator, HashMap<String, EObject> requirementsMap) {
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if (content.eClass().getName().equals("Requirement")) {
				Requirement requirement = (Requirement) content;
				if (!StringUtils.isEmpty(requirement.getExtId())) {
					requirementsMap.put(requirement.getExtId(), requirement);
				}
			}
		}
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

	public void unsetPersistency(IPersistencyService persistencyService) {
		this.persistencyService = null;
	}
}
