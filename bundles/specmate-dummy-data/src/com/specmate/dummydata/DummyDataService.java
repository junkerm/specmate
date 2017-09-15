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
import com.specmate.model.processes.Process;
import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessNode;
import com.specmate.model.processes.ProcessesFactory;
import com.specmate.model.requirements.CEGConnection;
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
			folder1.setName("Release 2016");

			Folder folder2 = BaseFactory.eINSTANCE.createFolder();
			folder2.setId("Folder-2");
			folder2.setName("Release 2017");
			
			Folder folder3 = BaseFactory.eINSTANCE.createFolder();
			folder3.setId("Folder-3");
			folder3.setName("Release JR");

			Requirement requirement1 = RequirementsFactory.eINSTANCE.createRequirement();
			requirement1.setId("Requirement-1");
			requirement1.setName("Zuschlag und Summenprüfung");
			requirement1.setDescription(
					"Das System ermöglicht die Suche nach Säumnis bzw. Prämienzuschlag wenn eine Einzelrechnung vorhanden ist, "
							+ "eine Reduktion gebucht wurde, und die Betragsart entweder SZ oder BZ ist. Eine Summenprüfung wird "
							+ "durchgeführt, falls eine Einzelabrechnung vorhanden ist.");
			requirement1.setImplementingBOTeam("Business Analysts");
			requirement1.setImplementingITTeam("The IT Nerds");
			requirement1.setImplementingUnit("Allianz IT and Infrastructure");
			requirement1.setNumberOfTests(4);
			requirement1.setPlannedRelease("Release 10 - Mount Everest");
			requirement1.setStatus("In Progress");
			requirement1.setTac("All tests must pass and the code is reviewed");

			Requirement requirement2 = RequirementsFactory.eINSTANCE.createRequirement();
			requirement2.setId("Requirement-2");
			requirement2.setName("My Second Requirement");

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
			node1.setCondition("is true");

			CEGNode node2 = RequirementsFactory.eINSTANCE.createCEGCauseNode();
			node2.setId("node-2");
			node2.setName("The second node");
			node2.setDescription("Condition 2 is met");
			node2.setX(200);
			node2.setY(100);
			node2.setVariable("Var2");
			node2.setCondition("is greater than 100");

			CEGNode node3 = RequirementsFactory.eINSTANCE.createCEGCauseNode();
			node3.setId("node-3");
			node3.setName("The third node");
			node3.setDescription("Condition 3 is met");
			node3.setX(400);
			node3.setY(100);
			node3.setVariable("Customer");
			node3.setCondition("is present");

			CEGNode node4 = RequirementsFactory.eINSTANCE.createCEGEffectNode();
			node4.setId("node-4");
			node4.setName("The fourth node");
			node4.setDescription("Condition 4 is met");
			node4.setX(300);
			node4.setY(250);
			node4.setVariable("Contract");
			node4.setCondition("is signed");

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

			Requirement requirement3 = RequirementsFactory.eINSTANCE.createRequirement();
			requirement3.setId("Requirement-3");
			requirement3.setName("Test Requirement JR");
			requirement3.setDescription(
					"Das System ermöglicht die Suche nach Säumnis bzw. Prämienzuschlag wenn eine Einzelrechnung vorhanden ist, "
							+ "eine Reduktion gebucht wurde, und die Betragsart entweder SZ oder BZ ist. Eine Summenprüfung wird "
							+ "durchgeführt, falls eine Einzelabrechnung vorhanden ist.");
			requirement3.setImplementingBOTeam("Business Analysts");
			requirement3.setImplementingITTeam("The IT Nerds");
			requirement3.setImplementingUnit("Allianz IT and Infrastructure");
			requirement3.setNumberOfTests(4);
			requirement3.setPlannedRelease("Release 10 - Mount Everest");
			requirement3.setStatus("In Progress");
			requirement3.setTac("All tests must pass and the code is reviewed");
			
			CEGModel lmModel = RequirementsFactory.eINSTANCE.createCEGModel();
			lmModel.setName("Liggesmeyer");
			lmModel.setDescription("Die Operation \"Zähle Zeichen\" liest Zeichen von der Tastatur, solange große Konsonanten oder große Vokale eingegeben werden sowie die Gesamtzahl kleiner ist als der Maximalwert des Datentyps integer.\nIst ein gelesenes Zeichen ein großer Konsonant oder Vokal, so wird die Gesamtzahl um eins erhöht. Falls das eingelesene Zeichen ein großer Vokal ist, so wird auch die Vokalanzahl um eins erhöht.");
			lmModel.setId("LM-1");

			CEGNode lmNode1 = RequirementsFactory.eINSTANCE.createCEGNode();
			lmNode1.setVariable("Großer Konsonant");
			lmNode1.setCondition("eingegeben");
			lmNode1.setId("lmNode1");
			lmNode1.setX(20);
			lmNode1.setY(0);
			lmNode1.setType(NodeType.AND);
			
			CEGNode lmNode2 = RequirementsFactory.eINSTANCE.createCEGNode();
			lmNode2.setVariable("Großer Vokal");
			lmNode2.setCondition("eingegeben");
			lmNode2.setId("lmNode2");
			lmNode2.setX(20);
			lmNode2.setY(120);
			lmNode2.setType(NodeType.AND);

			CEGNode lmNode3 = RequirementsFactory.eINSTANCE.createCEGNode();
			lmNode3.setVariable("Gesamtzahl");
			lmNode3.setCondition("< max.Integerwert");
			lmNode3.setId("lmNode3");
			lmNode3.setX(20);
			lmNode3.setY(240);
			lmNode3.setType(NodeType.AND);

			CEGNode lmNode4 = RequirementsFactory.eINSTANCE.createCEGNode();
			lmNode4.setVariable("Z1");
			lmNode4.setCondition("is present");
			lmNode4.setId("lmNode4");
			lmNode4.setX(260);
			lmNode4.setY(0);
			lmNode4.setType(NodeType.OR);

			CEGNode lmNode5 = RequirementsFactory.eINSTANCE.createCEGNode();
			lmNode5.setVariable("Operation");
			lmNode5.setCondition("wird beendet");
			lmNode5.setId("lmNode5");
			lmNode5.setX(260);
			lmNode5.setY(300);
			lmNode5.setType(NodeType.OR);

			CEGNode lmNode6 = RequirementsFactory.eINSTANCE.createCEGNode();
			lmNode6.setVariable("Gesamtanzahl");
			lmNode6.setCondition("wird erhöht");
			lmNode6.setId("lmNode6");
			lmNode6.setX(500);
			lmNode6.setY(0);
			lmNode6.setType(NodeType.AND);

			CEGNode lmNode7 = RequirementsFactory.eINSTANCE.createCEGNode();
			lmNode7.setVariable("Vokalanzahl");
			lmNode7.setCondition("wird erhöht");
			lmNode7.setId("lmNode7");
			lmNode7.setX(500);
			lmNode7.setY(120);
			lmNode7.setType(NodeType.AND);

			CEGNode lmNode8 = RequirementsFactory.eINSTANCE.createCEGNode();
			lmNode8.setVariable("Zeichen");
			lmNode8.setCondition("wird gelesen");
			lmNode8.setId("lmNode8");
			lmNode8.setX(500);
			lmNode8.setY(240);
			lmNode8.setType(NodeType.AND);

			CEGConnection lmConn1 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn1.setId("lmConn1");
			lmConn1.setName("-");
			lmConn1.setSource(lmNode1);
			lmConn1.setTarget(lmNode4);

			CEGConnection lmConn2 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn2.setId("lmConn2");
			lmConn2.setName("-");
			lmConn2.setSource(lmNode2);
			lmConn2.setTarget(lmNode4);

			CEGConnection lmConn3 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn3.setId("lmConn3");
			lmConn3.setName("-");
			lmConn3.setSource(lmNode2);
			lmConn3.setTarget(lmNode7);

			CEGConnection lmConn4 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn4.setId("lmConn4");
			lmConn4.setName("-");
			lmConn4.setSource(lmNode3);
			lmConn4.setTarget(lmNode6);

			CEGConnection lmConn5 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn5.setId("lmConn5");
			lmConn5.setName("-");
			lmConn5.setSource(lmNode3);
			lmConn5.setTarget(lmNode7);

			CEGConnection lmConn6 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn6.setId("lmConn6");
			lmConn6.setName("-");
			lmConn6.setSource(lmNode3);
			lmConn6.setTarget(lmNode8);

			CEGConnection lmConn7 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn7.setId("lmConn7");
			lmConn7.setName("-");
			lmConn7.setSource(lmNode3);
			lmConn7.setTarget(lmNode5);
			lmConn7.setNegate(true);
			
			CEGConnection lmConn8 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn8.setId("lmConn8");
			lmConn8.setName("-");
			lmConn8.setSource(lmNode4);
			lmConn8.setTarget(lmNode6);
			
			CEGConnection lmConn9 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn9.setId("lmConn9");
			lmConn9.setName("-");
			lmConn9.setSource(lmNode4);
			lmConn9.setTarget(lmNode8);
			
			CEGConnection lmConn10 = RequirementsFactory.eINSTANCE.createCEGConnection();
			lmConn10.setId("lmConn10");
			lmConn10.setName("-");
			lmConn10.setSource(lmNode4);
			lmConn10.setTarget(lmNode5);
			lmConn10.setNegate(true);

			lmModel.getContents().add(lmNode1);
			lmModel.getContents().add(lmNode2);
			lmModel.getContents().add(lmNode3);
			lmModel.getContents().add(lmNode4);
			lmModel.getContents().add(lmNode5);
			lmModel.getContents().add(lmNode6);
			lmModel.getContents().add(lmNode7);
			lmModel.getContents().add(lmNode8);
			lmModel.getContents().add(lmConn1);
			lmModel.getContents().add(lmConn2);
			lmModel.getContents().add(lmConn3);
			lmModel.getContents().add(lmConn4);
			lmModel.getContents().add(lmConn5);
			lmModel.getContents().add(lmConn6);
			lmModel.getContents().add(lmConn7);
			lmModel.getContents().add(lmConn8);
			lmModel.getContents().add(lmConn9);
			lmModel.getContents().add(lmConn10);
			
			requirement3.getContents().add(lmModel);
			
			Process process1 = ProcessesFactory.eINSTANCE.createProcess();
			process1.setName("Create Customer");
			process1.setId("process-1");
			process1.setDescription("This is the process for creating new customers.");

			ProcessNode processNode1 = ProcessesFactory.eINSTANCE.createProcessStep();
			processNode1.setName("Underpants");
			processNode1.setId("process-node-1");
			processNode1.setX(200);
			processNode1.setY(100);
			ProcessNode processNode2 = ProcessesFactory.eINSTANCE.createProcessStep();
			processNode2.setName("...");
			processNode2.setId("process-node-2");
			processNode2.setX(200);
			processNode2.setY(300);
			ProcessNode processNode3 = ProcessesFactory.eINSTANCE.createProcessStep();
			processNode3.setName("Profit");
			processNode3.setId("process-node-3");
			processNode3.setX(200);
			processNode3.setY(500);
			ProcessConnection processConnection1 = ProcessesFactory.eINSTANCE.createProcessConnection();
			processConnection1.setName("Process Connection 1");
			processConnection1.setId("process-connection-1");
			processConnection1.setSource(processNode1);
			processConnection1.setTarget(processNode2);
			ProcessConnection processConnection2 = ProcessesFactory.eINSTANCE.createProcessConnection();
			processConnection2.setName("Process Connection 2");
			processConnection2.setId("process-connection-2");
			processConnection2.setSource(processNode2);
			processConnection2.setTarget(processNode3);

			process1.getContents().add(processNode1);
			process1.getContents().add(processNode2);
			process1.getContents().add(processNode3);
			process1.getContents().add(processConnection1);
			process1.getContents().add(processConnection2);
			
			requirement1.getContents().add(model1);
			requirement1.getContents().add(model2);
			folder1.getContents().add(requirement1);
			folder1.getContents().add(requirement2);
			folder1.getContents().add(process1);
			folder3.getContents().add(requirement3);
			testFolder.getContents().add(folder1);
			testFolder.getContents().add(folder2);
			testFolder.getContents().add(folder3);

			transaction.getResource().getContents().add(testFolder);

			try {
				transaction.commit();
			} catch (Exception e) {
				logService.log(LogService.LOG_ERROR, e.getMessage());
			}
		}
	}
}
