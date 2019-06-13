package com.specmate.connectors.internal;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.ConfigurationPolicy;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.exception.SpecmateException;
import com.specmate.common.exception.SpecmateValidationException;
import com.specmate.connectors.api.IRequirementsSource;
import com.specmate.connectors.config.ProjectConfigService;
import com.specmate.connectors.internal.config.ArtificialRequirementsConnectorConfig;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;

@Component(immediate=true, configurationPid = ArtificialRequirementsConnectorConfig.PID, configurationPolicy = ConfigurationPolicy.REQUIRE)
public class ArtificialRequirementsConnector implements IRequirementsSource {
	
	private Collection<Requirement> requirements;
	private String id;
	private Folder folder;
	private int numberOfRequirements;
	private int simulateDelay;
	private LogService logService;

	@Activate
	public void activate(Map<String, Object> properties) throws SpecmateValidationException {
		readConfig(properties);
	}

	private void readConfig(Map<String, Object>  properties) throws SpecmateValidationException {
		this.id=(String)properties.get(ProjectConfigService.KEY_CONNECTOR_ID);
		String numberOfRequirementsString = (String)properties.get(ArtificialRequirementsConnectorConfig.KEY_NUMBER_OF_REQUIREMENTS);
		String simulatedDelayString = (String)properties.get(ArtificialRequirementsConnectorConfig.KEY_DELAY);
		if(StringUtils.isEmpty(id)) {
			throw new SpecmateValidationException("No id given for artifical requirements connector");
		}
		try {
			this.numberOfRequirements= Integer.parseInt(numberOfRequirementsString);
		} catch (Exception e) {
			throw new SpecmateValidationException("Invalid number of requirements " + numberOfRequirementsString);
		}
		if(!StringUtils.isEmpty(simulatedDelayString)) {
			try {
				this.simulateDelay= Integer.parseInt(simulatedDelayString);
			} catch (Exception e) {
				throw new SpecmateValidationException("Invalid simulated delay " + simulatedDelayString);
			}
		} else {
			this.simulateDelay=0;
		}
	}

	@Override
	public Collection<Requirement> getRequirements() throws SpecmateException {
		if(this.requirements==null) {
			this.requirements = generateRequirements();
		}
		return this.requirements;
	}

	private Collection<Requirement> generateRequirements() {
		logService.log(LogService.LOG_INFO, "Generating " + numberOfRequirements + " artifical rquirements.");
		ArrayList<Requirement> requirements = new ArrayList<>();
		for(int i=1;i<numberOfRequirements;i++) {
			Requirement req = RequirementsFactory.eINSTANCE.createRequirement();
			req.setId("requirement"+i);
			req.setExtId("requirement"+i);
			req.setName("Requirement " + i);
			req.setDescription("This is a generated requirment");
			requirements.add(req);
		}
		if(this.simulateDelay>0) {
			try {
				Thread.sleep(simulateDelay);
			} catch (InterruptedException e) {
			}
		}
		return requirements;
	}


	/** The id for this connector. */
	@Override
	public String getId() {
		return this.id;
	}
	
	@Override
	public IContainer getContainerForRequirement(Requirement requirement) throws SpecmateException {
		if(this.folder==null) {
			this.folder = BaseFactory.eINSTANCE.createFolder();
			this.folder.setId("default");
			this.folder.setName("default");
		}
		if(this.simulateDelay>0) {
			try {
				Thread.sleep(simulateDelay);
			} catch (InterruptedException e) {
			}
		}
		return this.folder;
	}

	@Override
	public boolean authenticate(String username, String password) throws SpecmateException {
		return true;
	}
	
	/** Service reference */
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}


}
