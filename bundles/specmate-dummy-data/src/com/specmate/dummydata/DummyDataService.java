package com.specmate.dummydata;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.requirements.CEGConection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;
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
		Resource resource = transaction.getResource();
		EObject testData = SpecmateEcoreUtil.getEObjectWithName("test-data", resource.getContents());

		if (testData == null) {
			Folder testFolder = BaseFactory.eINSTANCE.createFolder();
			testFolder.setId("test-data");
			testFolder.setName("test-data");

			Folder folder1 = BaseFactory.eINSTANCE.createFolder();
			folder1.setId("Folder1");
			folder1.setName("My First Folder");

			Folder folder2 = BaseFactory.eINSTANCE.createFolder();
			folder2.setId("Folder2");
			folder2.setName("My Second Folder");

			Requirement requirement1 = RequirementsFactory.eINSTANCE.createRequirement();
			requirement1.setId("Requirement1");
			requirement1.setName("My First Requirement");
			requirement1.setDescription("When a customer has selected to create a new contract, the form for a new contract must be shown. The customer is guided through the process bz a wizard like dialogue. At every point, the data entered bz the customer has to be persisted and nothing should be lost.");
			requirement1.setImplementingBOTeam("Business Analysts");
			requirement1.setImplementingITTeam("The IT Nerds");
			requirement1.setImplementingUnit("Allianz IT and Infrastructure");
			requirement1.setNumberOfTests(4);
			requirement1.setPlannedRelease("Release 10 - Mount Everest");
			requirement1.setStatus("In Progress");
			requirement1.setTac("All tests must pass and the code is reviewed");
			
			Requirement requirement2 = RequirementsFactory.eINSTANCE.createRequirement();
			requirement2.setId("Requirement2");
			requirement2.setName("My Second Requirement");

			CEGModel model1 = RequirementsFactory.eINSTANCE.createCEGModel();
			model1.setName("Model 1");
			model1.setDescription("This is the first CEG model");
			model1.setId("Model1");
			
			CEGNode node1 = RequirementsFactory.eINSTANCE.createCEGNode();
			node1.setId("Node1");
			node1.setName("The first node");
			node1.setDescription("Condition 1 is met");
			
			CEGNode node2 = RequirementsFactory.eINSTANCE.createCEGNode();
			node2.setId("Node2");
			node2.setName("The second node");
			node2.setDescription("Condition 2 is met");
			
			CEGConection connection = RequirementsFactory.eINSTANCE.createCEGConection();
			connection.setId("Connection1");
			connection.setName("The first connection");
			connection.setSource(node1);
			connection.setTarget(node2);
			
			CEGModel model2 = RequirementsFactory.eINSTANCE.createCEGModel();
			model2.setName("Model 2");
			model2.setDescription("This is the second CEG model");
			model2.setId("Model2");

			requirement1.getContents().add(model1);
			requirement1.getContents().add(model2);
			folder1.getContents().add(requirement1);
			folder1.getContents().add(requirement2);
			testFolder.getContents().add(folder1);
			testFolder.getContents().add(folder2);

			transaction.getResource().getContents().add(testFolder);

			try {
				transaction.commit();
			} catch (Exception e) {
				logService.log(LogService.LOG_ERROR, e.getMessage());
			}
		}
	}
}
