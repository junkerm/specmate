package com.specmate.dummydata;

import java.util.IllformedLocaleException;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

@Component(immediate = true)
public class DummyDataService {
	
	private IPersistencyService persistencyService;
	
	@Reference
	public void setPersistency(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}
	
	private LogService logService;
	
	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Activate
	public void activate() {
		ITransaction transaction = this.persistencyService.openTransaction();
		
		Folder folder = BaseFactory.eINSTANCE.createFolder();
		folder.setId("Folder1");
		folder.setName("My First Folder");
		
		Requirement requirement = RequirementsFactory.eINSTANCE.createRequirement();
		requirement.setId("Requirement1");
		requirement.setName("My Fresh Requirement");
		
		folder.getContents().add(requirement);
		
		transaction.getResource().getContents().add(folder);
		
		try {
			transaction.commit();
		} catch (Exception e) {
			logService.log(LogService.LOG_ERROR, e.getMessage());
		}
	}
}
