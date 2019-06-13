package com.specmate.connectors.internal;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IChange;
import com.specmate.persistency.ITransaction;
import com.specmate.scheduler.SchedulerTask;

public class ConnectorTask extends SchedulerTask {

	private static final int MAX_FIELD_LENGTH = 4000;
	private LogService logService;
	private ITransaction transaction;
	private List<IRequirementsSource> requirementsSources;

	public ConnectorTask(List<IRequirementsSource> requirementsSources, ITransaction transaction,
			LogService logService) {
		super();
		this.requirementsSources = requirementsSources;
		this.transaction = transaction;
		this.logService = logService;
	}

	@Override
	public void run() {
		syncRequirementsFromSources();
	}

	private void syncRequirementsFromSources() {
		logService.log(LogService.LOG_INFO, "Synchronizing requirements");
		Resource resource = transaction.getResource();
		for (IRequirementsSource source : requirementsSources) {
			logService.log(LogService.LOG_INFO, "Retrieving requirements from " + source.getId());
			try {
				Collection<Requirement> requirements = source.getRequirements();
				if (requirements == null) {
					continue;
				}
				IContainer localContainer = getOrCreateLocalContainer(resource, source.getId());
				Requirement[] reqArray = requirements.toArray(new Requirement[0]);
				int greatestUnhandledIndex = 0;
				int maxIndex = requirements.size() - 1;
				while (greatestUnhandledIndex <= maxIndex) {
					int upperIndexExclusive = Math.min(greatestUnhandledIndex + 100, maxIndex + 1);
					Requirement[] current = Arrays.copyOfRange(reqArray, greatestUnhandledIndex, upperIndexExclusive);
					greatestUnhandledIndex = upperIndexExclusive;
					List<Requirement> tosync = Arrays.asList(current);

					try {
						transaction.doAndCommit(new IChange<Object>() {
							@Override
							public Object doChange() throws SpecmateException {
								syncContainers(localContainer, tosync, source);
								return null;
							}
						});
					} catch (Exception e) {
						logService.log(LogService.LOG_ERROR, e.getMessage());
						transaction.rollback();
					}

				}
			} catch (Exception e) {
				logService.log(LogService.LOG_ERROR, e.getMessage());
				try {
					transaction.rollback();
				} catch (SpecmateException rbe) {}
			}

		}
	}

	private void syncContainers(IContainer localContainer, Collection<Requirement> requirements,
			IRequirementsSource source) {
		// Build hashset (extid -> requirement) for local requirements
		TreeIterator<EObject> localIterator = localContainer.eAllContents();
		HashMap<String, EObject> localRequirementsMap = new HashMap<>();
		buildExtIdMap(localIterator, localRequirementsMap);

		// Build hashset (extid -> requirement) for remote requirements
		HashMap<String, EObject> remoteRequirementsMap = new HashMap<>();
		buildExtIdMap(requirements.iterator(), remoteRequirementsMap);
		logService.log(LogService.LOG_INFO, "Retrieved " + remoteRequirementsMap.entrySet().size() + " requirements.");

		// find new requirements
		remoteRequirementsMap.keySet().removeAll(localRequirementsMap.keySet());

		logService.log(LogService.LOG_INFO, "Adding " + remoteRequirementsMap.size() + " new requirements.");

		// add new requirements to local container and all folders on the way
		for (Entry<String, EObject> entry : remoteRequirementsMap.entrySet()) {
			Requirement requirementToAdd = (Requirement) entry.getValue();
			boolean valid = ensureValid(requirementToAdd);
			if (!valid) {
				logService.log(LogService.LOG_WARNING, "Found invalid requirement with id " + requirementToAdd.getId());
				continue;
			}
			IContainer reqContainer;
			try {
				reqContainer = source.getContainerForRequirement((Requirement) entry.getValue());

			} catch (SpecmateException e) {
				logService.log(LogService.LOG_ERROR, e.getMessage());
				continue;
			}
			IContainer foundContainer = (IContainer) SpecmateEcoreUtil.getEObjectWithId(reqContainer.getId(),
					localContainer.eContents());
			if (foundContainer == null) {
				logService.log(LogService.LOG_DEBUG, "Creating new folder " + reqContainer.getName());
				foundContainer = BaseFactory.eINSTANCE.createFolder();
				SpecmateEcoreUtil.copyAttributeValues(reqContainer, foundContainer);
				valid = ensureValid(foundContainer);
				if (!valid) {
					logService.log(LogService.LOG_WARNING, "Found invalid folder with id " + foundContainer.getId());
					continue;
				}
				localContainer.getContents().add(foundContainer);
			}

			logService.log(LogService.LOG_DEBUG, "Adding requirement " + requirementToAdd.getId());
			foundContainer.getContents().add(requirementToAdd);
		}
	}

	private boolean ensureValid(IContentElement element) {
		if (StringUtils.isEmpty(element.getId())) {
			return false;
		}
		if (StringUtils.isEmpty(element.getName())) {
			element.setName(element.getId());
		}
		if (element.getName().length() > MAX_FIELD_LENGTH) {
			element.setName(element.getName().substring(0, MAX_FIELD_LENGTH - 1));
		}
		element.setName(element.getName().replaceAll("[,\\|;]", " "));
		if (element.getDescription() != null && element.getDescription().length() > MAX_FIELD_LENGTH) {
			element.setDescription(element.getDescription().substring(0, MAX_FIELD_LENGTH - 1));
		}
		return true;
	}

	private IContainer getOrCreateLocalContainer(Resource resource, String name) {
		EObject object = SpecmateEcoreUtil.getEObjectWithId(name, resource.getContents());
		if (object != null) {
			if (object instanceof IContainer) {
				return (IContainer) object;
			}
		}

		Folder folder = BaseFactory.eINSTANCE.createFolder();
		String validName = name.replaceAll("[,\\|;]", " ");
		folder.setName(validName);
		folder.setId(validName);
		resource.getContents().add(folder);
		return folder;
	}

	private void buildExtIdMap(Iterator<? extends EObject> iterator, HashMap<String, EObject> requirementsMap) {
		while (iterator.hasNext()) {
			EObject content = iterator.next();
			if (content == null) {
				continue;
			}
			if (content.eClass().getName().equals("Requirement")) {
				Requirement requirement = (Requirement) content;
				if (!StringUtils.isEmpty(requirement.getExtId())) {
					requirementsMap.put(requirement.getExtId(), requirement);
				}
			}
		}
	}
}
