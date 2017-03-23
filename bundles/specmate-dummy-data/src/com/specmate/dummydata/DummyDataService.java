package com.specmate.dummydata;

import org.eclipse.emf.cdo.common.id.CDOWithID;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.persistency.IPersistencyService;
import com.specmate.persistency.ITransaction;

@Component(immediate = true)
public class DummyDataService {
	CDOWithID id;
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
			folder1.setId("Folder-1");
			folder1.setName("My First Folder");

			Folder folder2 = BaseFactory.eINSTANCE.createFolder();
			folder2.setId("Folder-2");
			folder2.setName("My Second Folder");

			Requirement requirement1 = RequirementsFactory.eINSTANCE.createRequirement();
			requirement1.setId("Requirement-1");
			requirement1.setName("My First Requirement");
			requirement1.setDescription(
					"When a customer has selected to create a new contract, the form for a new contract must be shown. The customer is guided through the process bz a wizard like dialogue. At every point, the data entered bz the customer has to be persisted and nothing should be lost.");
			requirement1.setImplementingBOTeam("Business Analysts");
			requirement1.setImplementingITTeam("The IT Nerds");
			requirement1.setImplementingUnit("Allianz IT and Infrastructure");
			requirement1.setNumberOfTests(4);
			requirement1.setPlannedRelease("Release 10 - Mount Everest");
			requirement1.setStatus("In Progress");
			requirement1.setTac("All tests must pass and the code is reviewed");
			requirement1.setExtId("EX1");
			requirement1.setExtId2("EX2-1");

			Requirement requirement2 = RequirementsFactory.eINSTANCE.createRequirement();
			requirement2.setId("Requirement-2");
			requirement2.setName("My Second Requirement");
			requirement2.setExtId("EX2");
			requirement2.setExtId2("EX1-7");

			CEGModel model1 = RequirementsFactory.eINSTANCE.createCEGModel();
			model1.setName("Model 1");
			model1.setDescription("This is the first CEG model");
			model1.setId("Model-1");

			CEGNode node1 = RequirementsFactory.eINSTANCE.createCEGCauseNode();
			node1.setId("node-1");
			node1.setName("The first node");
			node1.setDescription("Condition 1 is met");
			node1.setX(1);
			node1.setY(100);
			node1.setVariable("Var1");
			node1.setOperator("=");
			node1.setValue("true");

			CEGNode node2 = RequirementsFactory.eINSTANCE.createCEGCauseNode();
			node2.setId("node-2");
			node2.setName("The second node");
			node2.setDescription("Condition 2 is met");
			node2.setX(200);
			node2.setY(100);
			node2.setVariable("Var2");
			node2.setOperator(">");
			node2.setValue("100");

			CEGNode node3 = RequirementsFactory.eINSTANCE.createCEGCauseNode();
			node3.setId("node-3");
			node3.setName("The third node");
			node3.setDescription("Condition 3 is met");
			node3.setX(400);
			node3.setY(100);
			node3.setVariable("Customer");
			node3.setOperator("is");
			node3.setValue("present");

			CEGNode node4 = RequirementsFactory.eINSTANCE.createCEGEffectNode();
			node4.setId("node-4");
			node4.setName("The fourth node");
			node4.setDescription("Condition 4 is met");
			node4.setX(300);
			node4.setY(250);
			node4.setVariable("Contract");
			node4.setOperator("is");
			node4.setValue("signed");

			CEGConnection connection1 = RequirementsFactory.eINSTANCE.createCEGConnection();
			connection1.setId("conn-1");
			connection1.setName("The first connection");
			connection1.setSource(node1);
			connection1.setTarget(node4);

			CEGConnection connection2 = RequirementsFactory.eINSTANCE.createCEGConnection();
			connection2.setId("conn-2");
			connection2.setName("The second connection");
			connection2.setSource(node2);
			connection2.setTarget(node4);

			CEGConnection connection3 = RequirementsFactory.eINSTANCE.createCEGConnection();
			connection3.setId("conn-3");
			connection3.setName("The third connection");
			connection3.setSource(node3);
			connection3.setTarget(node4);

			model1.getContents().add(node1);
			model1.getContents().add(node2);
			model1.getContents().add(node3);
			model1.getContents().add(node4);
			model1.getContents().add(connection1);
			model1.getContents().add(connection2);
			model1.getContents().add(connection3);

			CEGModel model2 = RequirementsFactory.eINSTANCE.createCEGModel();
			model2.setName("Model 2");
			model2.setDescription("This is the second CEG model");
			model2.setId("Model-2");

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
