package com.specmate.dummydata;

import org.eclipse.emf.cdo.common.id.CDOWithID;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.log.LogService;

import com.specmate.common.SpecmateException;
import com.specmate.model.base.BaseFactory;
import com.specmate.model.base.Folder;
import com.specmate.model.processes.Process;
import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessDecision;
import com.specmate.model.processes.ProcessEnd;
import com.specmate.model.processes.ProcessNode;
import com.specmate.model.processes.ProcessStart;
import com.specmate.model.processes.ProcessStep;
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
import com.specmate.search.api.IModelSearchService;

@Component(immediate = true)
public class DummyDataService {
	CDOWithID id;
	private IPersistencyService persistencyService;
	private IModelSearchService searchService;

	@Reference
	public void setPersistency(IPersistencyService persistencyService) {
		this.persistencyService = persistencyService;
	}

	@Reference
	public void setSearchService(IModelSearchService searchService) {
		// ensure search service is activated before writing dummy data
		this.searchService = searchService;
	}

	private LogService logService;

	@Reference
	public void setLogService(LogService logService) {
		this.logService = logService;
	}

	@Activate
	public void activate() throws SpecmateException {
		new Thread(() -> {
			try {
				// Wait a bit, to avoid the problem that the search service is not yet attached
				// to the system wide event bus and therefore the search index does not contain
				// the dummy data.
				Thread.sleep(5000);
				fillDummyData();
			} catch (Exception e) {
				logService.log(LogService.LOG_ERROR, "Error while writing dummy data.");
			}
		}).start();
	}

	private void fillDummyData() throws SpecmateException {
		ITransaction transaction = this.persistencyService.openTransaction();
		Resource resource = transaction.getResource();
		EObject testProject1 = SpecmateEcoreUtil.getEObjectWithName(DummyProject.TEST_DATA_PROJECT,
				resource.getContents());

		if (testProject1 == null) {
			Folder testFolder = BaseFactory.eINSTANCE.createFolder();
			testFolder.setId(DummyProject.TEST_DATA_PROJECT);
			testFolder.setName(DummyProject.TEST_DATA_PROJECT);

			loadMiniTrainingTestData(testFolder);
			loadGenericTestData(testFolder);
			loadUserStudyTestData(testFolder);

			transaction.getResource().getContents().add(testFolder);

			try {
				transaction.commit();
			} catch (Exception e) {
				logService.log(LogService.LOG_ERROR, e.getMessage());
			}
		}

		// Create another project for manual testing purposes, e.g. to verify
		// authentication behavior
		EObject testProject2 = SpecmateEcoreUtil.getEObjectWithName("another-project", resource.getContents());

		if (testProject2 == null) {
			Folder testFolder = BaseFactory.eINSTANCE.createFolder();
			testFolder.setId("another-project");
			testFolder.setName("another-project");

			loadMiniTrainingTestData(testFolder);
			loadGenericTestData(testFolder);
			loadUserStudyTestData(testFolder);

			transaction.getResource().getContents().add(testFolder);

			try {
				transaction.commit();
			} catch (Exception e) {
				logService.log(LogService.LOG_ERROR, e.getMessage());
			}
		}
	}

	private void loadGenericTestData(Folder testFolder) {
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
		requirement1.setExtId("123");
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
		requirement1.setIsRegressionRequirement(true);
		requirement1.setPlatform("ABS");

		Requirement requirement2 = RequirementsFactory.eINSTANCE.createRequirement();
		requirement2.setId("Requirement-2");
		requirement2.setName("My Second Requirement");

		CEGModel model1 = RequirementsFactory.eINSTANCE.createCEGModel();
		model1.setName("Model 1");
		model1.setDescription("This is the first CEG model");
		model1.setId("Model-1");

		CEGNode node1 = RequirementsFactory.eINSTANCE.createCEGNode();
		node1.setId("node-1");
		node1.setName("The first node");
		node1.setDescription("Condition 1 is met");
		node1.setX(1);
		node1.setY(100);
		node1.setVariable("Var1");
		node1.setCondition("is true");

		CEGNode node2 = RequirementsFactory.eINSTANCE.createCEGNode();
		node2.setId("node-2");
		node2.setName("The second node");
		node2.setDescription("Condition 2 is met");
		node2.setX(200);
		node2.setY(100);
		node2.setVariable("Var2");
		node2.setCondition("is greater than 100");

		CEGNode node3 = RequirementsFactory.eINSTANCE.createCEGNode();
		node3.setId("node-3");
		node3.setName("The third node");
		node3.setDescription("Condition 3 is met");
		node3.setX(400);
		node3.setY(100);
		node3.setVariable("Customer");
		node3.setCondition("is present");

		CEGNode node4 = RequirementsFactory.eINSTANCE.createCEGNode();
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
		lmModel.setDescription(
				"Die Operation \"Zähle Zeichen\" liest Zeichen von der Tastatur, solange große Konsonanten oder große Vokale eingegeben werden sowie die Gesamtzahl kleiner ist als der Maximalwert des Datentyps integer.\nIst ein gelesenes Zeichen ein großer Konsonant oder Vokal, so wird die Gesamtzahl um eins erhöht. Falls das eingelesene Zeichen ein großer Vokal ist, so wird auch die Vokalanzahl um eins erhöht.");
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

		Requirement requirement4 = RequirementsFactory.eINSTANCE.createRequirement();
		requirement4.setId("Requirement-4");
		requirement4.setName("Data Collection Process");

		Process process1 = ProcessesFactory.eINSTANCE.createProcess();
		process1.setName("Create Customer");
		process1.setId("process-1");
		process1.setDescription("This is the process for creating new customers.");

		ProcessNode process1Start = ProcessesFactory.eINSTANCE.createProcessStart();
		process1Start.setName("Start");
		process1Start.setId("process-1-start");
		process1Start.setX(200);
		process1Start.setY(40);
		ProcessNode processNode1 = ProcessesFactory.eINSTANCE.createProcessStep();
		processNode1.setName("Collect Data");
		processNode1.setId("process-node-1");
		processNode1.getTracesTo().add(requirement2);
		processNode1.setX(200);
		processNode1.setY(100);
		ProcessConnection processConnection0 = ProcessesFactory.eINSTANCE.createProcessConnection();
		processConnection0.setName("Process Connection 0");
		processConnection0.setId("process-connection-0");
		processConnection0.setSource(process1Start);
		processConnection0.setTarget(processNode1);
		ProcessNode processNode2 = ProcessesFactory.eINSTANCE.createProcessStep();
		processNode2.setName("...");
		processNode2.setId("process-node-2");
		processNode2.setX(200);
		processNode2.setY(200);
		ProcessNode processNode3 = ProcessesFactory.eINSTANCE.createProcessStep();
		processNode3.setName("Profit");
		processNode3.setId("process-node-3");
		processNode3.setX(200);
		processNode3.setY(300);
		ProcessNode processDecisionNode1 = ProcessesFactory.eINSTANCE.createProcessDecision();
		processDecisionNode1.setName("Decision 1");
		processDecisionNode1.setId("decision-1");
		processDecisionNode1.setDescription("The first decision");
		processDecisionNode1.setX(400);
		processDecisionNode1.setY(200);
		ProcessConnection processConnection1 = ProcessesFactory.eINSTANCE.createProcessConnection();
		processConnection1.setName("Process Connection 1");
		processConnection1.setId("process-connection-1");
		processConnection1.setSource(processNode1);
		processConnection1.setTarget(processDecisionNode1);
		ProcessConnection processConnection2 = ProcessesFactory.eINSTANCE.createProcessConnection();
		processConnection2.setName("Process Connection 2");
		processConnection2.setId("process-connection-2");
		processConnection2.setCondition("Condition met");
		processConnection2.setSource(processDecisionNode1);
		processConnection2.setTarget(processNode3);
		ProcessNode process1End = ProcessesFactory.eINSTANCE.createProcessEnd();
		process1End.setName("End");
		process1End.setId("process-1-end");
		process1End.setX(200);
		process1End.setY(260);
		ProcessConnection processConnection3 = ProcessesFactory.eINSTANCE.createProcessConnection();
		processConnection3.setName("Process Connection 3");
		processConnection3.setId("process-connection-3");
		processConnection3.setSource(processNode3);
		processConnection3.setTarget(process1End);
		ProcessConnection processConnection4 = ProcessesFactory.eINSTANCE.createProcessConnection();
		processConnection4.setName("Process Connection 4");
		processConnection4.setId("process-connection-4");
		processConnection4.setCondition("Condition not met");
		processConnection4.setSource(processDecisionNode1);
		processConnection4.setTarget(processNode2);
		ProcessConnection processConnection5 = ProcessesFactory.eINSTANCE.createProcessConnection();
		processConnection5.setName("Process Connection 5");
		processConnection5.setId("process-connection-5");
		processConnection5.setSource(processNode2);
		processConnection5.setTarget(process1End);

		process1.getContents().add(process1Start);
		process1.getContents().add(process1End);
		process1.getContents().add(processNode1);
		process1.getContents().add(processNode2);
		process1.getContents().add(processNode3);
		process1.getContents().add(processDecisionNode1);
		process1.getContents().add(processConnection0);
		process1.getContents().add(processConnection1);
		process1.getContents().add(processConnection2);
		process1.getContents().add(processConnection3);
		process1.getContents().add(processConnection4);
		process1.getContents().add(processConnection5);

		requirement4.getContents().add(process1);

		requirement1.getContents().add(model1);
		requirement1.getContents().add(model2);
		folder1.getContents().add(requirement1);
		folder1.getContents().add(requirement2);
		folder1.getContents().add(requirement4);
		folder3.getContents().add(requirement3);
		folder1.getContents().add(requirement4);
		testFolder.getContents().add(folder1);
		testFolder.getContents().add(folder2);
		testFolder.getContents().add(folder3);
	}

	private void loadMiniTrainingTestData(Folder testFolder) {

		Folder evalFolder = BaseFactory.eINSTANCE.createFolder();
		testFolder.getContents().add(evalFolder);
		evalFolder.setId("evalFolder");
		evalFolder.setName("Evaluation");

		String excelRequirement = "Markiert der Nutzer mit der Linken Maustaste eine Zelle, so werden alle bestehenden Markierungen gelöscht und die aktuelle Zelle wird markiert."
				+ "Hält der Nutzer zusätzlich die Shift-Taste gedrückt, werden alle Zellen zwischen der letzten markierten Zelle und der aktuellen Zelle auch markiert."
				+ "Hält der Nutzer zusätzlich die Strg-Taste gedrückt, werden die bestehenden Markierungen nicht gelöscht, sondern die aktuelle Zelle zusätzlich markiert.";

		Requirement excelRequirment = RequirementsFactory.eINSTANCE.createRequirement();
		excelRequirment.setId("EvalRequirement-1");
		excelRequirment.setName("Excel Zellenmarkierung");
		excelRequirment.setDescription(excelRequirement);
		evalFolder.getContents().add(excelRequirment);

		String driverLicenceRequirementText = "Prüfung, ob Fahren eines Fahrzeugs erlaubt ist: Das Fahren eines Fahrzeugs ist erlaubt, wenn der Fahrer älter als 18 Jahre ist und "
				+ "einen Führerschein mit sich führt. Das Fahren des Fahrzeugs ist außerdem erlaubt, wenn der Fahrer zwar noch nicht 18 Jahre alt ist, aber einen Führerschein bereits besitzt "
				+ "und eine vollährige Begleitperson mit im Fahrzeug fährt.";

		Requirement driverLicenceRequirement = RequirementsFactory.eINSTANCE.createRequirement();
		driverLicenceRequirement.setId("driverLicenceReq-1");
		driverLicenceRequirement.setName("Erlaubnis Autofahren");
		driverLicenceRequirement.setDescription(driverLicenceRequirementText);
		evalFolder.getContents().add(driverLicenceRequirement);

		CEGModel evalModel1 = createExcelCEGExample(excelRequirement);

		excelRequirment.getContents().add(evalModel1);

	}

	private void loadUserStudyTestData(Folder testFolder) {
		Folder studyFolder = BaseFactory.eINSTANCE.createFolder();
		testFolder.getContents().add(studyFolder);
		studyFolder.setId("studyFolder");
		studyFolder.setName("User Study");

		studyFolder.getContents().add(createSingleStudyFolder("Study-1", "study-1"));
		studyFolder.getContents().add(createSingleStudyFolder("Study-2", "study-2"));
		studyFolder.getContents().add(createSingleStudyFolder("Study-3", "study-3"));
		studyFolder.getContents().add(createSingleStudyFolder("Study-4", "study-4"));
	}

	private Folder createSingleStudyFolder(String name, String id) {
		Folder studyFolder = BaseFactory.eINSTANCE.createFolder();
		studyFolder.setId(id);
		studyFolder.setName(name);

		String atmRequirementText = "Ist die Bankkarte gültig, und hat der Nutzer die korrekte PIN eingegeben, und ist Geld im Automaten verfügbar, so wird das Geld ausgezahlt.\n"
				+ "Falls nicht genügend Geld im Automaten verfügbar ist, fragt der Automat nach einem neuen Geldbetrag.\n"
				+ "\n"
				+ "Ist die Bankkarte zwar gültig, aber der Nutzer gibt die falsche PIN ein, so fragt der Automat nach einer neuen PIN. Gibt der Nutzer drei "
				+ "Mal eine falsche PIN ein, behält der Automat die Karte ein.\n" + "\n"
				+ "Ist die Bankkarte nicht gültig, wird die Bankkarte zurückgewiesen. ";

		Requirement atmRequirement = RequirementsFactory.eINSTANCE.createRequirement();
		atmRequirement.setId("atmrequireemnt");
		atmRequirement.setName("Bankautomat - Geld abheben");
		atmRequirement.setDescription(atmRequirementText);
		studyFolder.getContents().add(atmRequirement);

		Requirement processRequirement = RequirementsFactory.eINSTANCE.createRequirement();
		processRequirement.setId("processrequirement");
		processRequirement.setName("Bankautomat - Gesamtprozess");
		processRequirement.setDescription("Dieser Prozess beschreibt die Interaktion eines Kunden mit dem Geldautomat");

		studyFolder.getContents().add(processRequirement);

		Process process = ProcessesFactory.eINSTANCE.createProcess();
		process.setId("process1");
		process.setName("Prozessmodell");
		processRequirement.getContents().add(process);

		ProcessStart start = ProcessesFactory.eINSTANCE.createProcessStart();
		start.setId("start");
		start.setX(50);
		start.setY(100);
		process.getContents().add(start);

		ProcessStep step1 = ProcessesFactory.eINSTANCE.createProcessStep();
		step1.setId("step1");
		step1.setX(200);
		step1.setY(100);
		step1.setName("Automat aktivieren");
		process.getContents().add(step1);

		ProcessStep step2 = ProcessesFactory.eINSTANCE.createProcessStep();
		step2.setId("step2");
		step2.setX(400);
		step2.setY(100);
		step2.setName("Karte und PIN prüfen");
		process.getContents().add(step2);

		ProcessStep step3 = ProcessesFactory.eINSTANCE.createProcessStep();
		step3.setId("step3");
		step3.setX(400);
		step3.setY(200);
		step3.setName("Funktionsauswahl");
		process.getContents().add(step3);

		ProcessDecision decision = ProcessesFactory.eINSTANCE.createProcessDecision();
		decision.setId("decision1");
		decision.setX(400);
		decision.setY(300);
		process.getContents().add(decision);

		ProcessStep step4 = ProcessesFactory.eINSTANCE.createProcessStep();
		step4.setId("step4");
		step4.setX(300);
		step4.setY(400);
		step4.setName("Geld abheben wählen");
		process.getContents().add(step4);

		ProcessStep step5 = ProcessesFactory.eINSTANCE.createProcessStep();
		step5.setId("step5");
		step5.setX(300);
		step5.setY(500);
		step5.setName("Geldbetrag auswählen");
		process.getContents().add(step5);

		ProcessStep step6 = ProcessesFactory.eINSTANCE.createProcessStep();
		step6.setId("step6");
		step6.setX(300);
		step6.setY(600);
		step6.setName("Geld auszahlen");
		process.getContents().add(step6);

		ProcessStep step7 = ProcessesFactory.eINSTANCE.createProcessStep();
		step7.setId("step7");
		step7.setX(500);
		step7.setY(400);
		step7.setName("Kontostand wählen");
		process.getContents().add(step7);

		ProcessStep step8 = ProcessesFactory.eINSTANCE.createProcessStep();
		step8.setId("step8");
		step8.setX(500);
		step8.setY(500);
		step8.setName("Kontostand anzeigen");
		process.getContents().add(step8);

		ProcessStep step9 = ProcessesFactory.eINSTANCE.createProcessStep();
		step9.setId("step9");
		step9.setX(800);
		step9.setY(600);
		step9.setName("Karte ausgeben");
		process.getContents().add(step9);

		ProcessEnd end = ProcessesFactory.eINSTANCE.createProcessEnd();
		end.setId("end");
		end.setX(800);
		end.setY(300);
		process.getContents().add(end);

		ProcessConnection conn1 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn1.setName("conn1");
		conn1.setSource(start);
		conn1.setTarget(step1);
		conn1.setId("conn1");
		process.getContents().add(conn1);

		ProcessConnection conn2 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn2.setName("conn2");
		conn2.setSource(step1);
		conn2.setTarget(step2);
		conn2.setId("conn2");
		process.getContents().add(conn2);

		ProcessConnection conn3 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn3.setName("conn3");
		conn3.setSource(step2);
		conn3.setTarget(step3);
		conn3.setId("conn3");
		process.getContents().add(conn3);

		ProcessConnection conn4 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn4.setName("conn4");
		conn4.setSource(step3);
		conn4.setTarget(decision);
		conn4.setId("conn4");
		process.getContents().add(conn4);

		ProcessConnection conn5 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn5.setName("conn5");
		conn5.setSource(decision);
		conn5.setTarget(step4);
		conn5.setId("conn5");
		conn5.setCondition("abheben");
		process.getContents().add(conn5);

		ProcessConnection conn6 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn6.setName("conn6");
		conn6.setSource(decision);
		conn6.setTarget(step7);
		conn6.setId("conn6");
		conn6.setCondition("kontostand");
		process.getContents().add(conn6);

		ProcessConnection conn7 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn7.setName("conn7");
		conn7.setSource(decision);
		conn7.setTarget(end);
		conn7.setId("conn7");
		conn7.setCondition("ende");
		process.getContents().add(conn7);

		ProcessConnection conn8 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn8.setName("conn8");
		conn8.setSource(step4);
		conn8.setTarget(step5);
		conn8.setId("conn8");
		process.getContents().add(conn8);

		ProcessConnection conn9 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn9.setName("conn9");
		conn9.setSource(step5);
		conn9.setTarget(step6);
		conn9.setId("conn9");
		process.getContents().add(conn9);

		ProcessConnection conn10 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn10.setName("conn10");
		conn10.setSource(step6);
		conn10.setTarget(step9);
		conn10.setId("conn10");
		process.getContents().add(conn10);

		ProcessConnection conn11 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn11.setName("conn11");
		conn11.setSource(step9);
		conn11.setTarget(end);
		conn11.setId("conn11");
		process.getContents().add(conn11);

		ProcessConnection conn12 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn12.setName("conn12");
		conn12.setSource(step7);
		conn12.setTarget(step8);
		conn12.setId("conn12");
		process.getContents().add(conn12);

		ProcessConnection conn13 = ProcessesFactory.eINSTANCE.createProcessConnection();
		conn13.setName("conn13");
		conn13.setSource(step8);
		conn13.setTarget(step9);
		conn13.setId("conn13");
		process.getContents().add(conn13);

		return studyFolder;

	}

	private CEGModel createExcelCEGExample(String excelRequirement) {
		CEGModel evalModel1 = RequirementsFactory.eINSTANCE.createCEGModel();
		evalModel1.setName("Excel Zeilenmarkierung");
		evalModel1.setDescription(excelRequirement);
		evalModel1.setId("EvalModel-1");

		CEGNode evalNode1 = RequirementsFactory.eINSTANCE.createCEGNode();
		evalNode1.setId("evalnode-1");
		evalNode1.setName("evalnode-1");
		evalNode1.setDescription("");
		evalNode1.setX(100);
		evalNode1.setY(100);
		evalNode1.setVariable("Linke Maustaste");
		evalNode1.setCondition("gedrückt");
		evalModel1.getContents().add(evalNode1);

		CEGNode evalNode2 = RequirementsFactory.eINSTANCE.createCEGNode();
		evalNode2.setId("evalnode-2");
		evalNode2.setName("evalnode-2");
		evalNode2.setDescription("");
		evalNode2.setX(100);
		evalNode2.setY(200);
		evalNode2.setVariable("Strg Taste");
		evalNode2.setCondition("gedrückt");
		evalModel1.getContents().add(evalNode2);

		CEGNode evalNode3 = RequirementsFactory.eINSTANCE.createCEGNode();
		evalNode3.setId("evalnode-3");
		evalNode3.setName("evalnode-3");
		evalNode3.setDescription("");
		evalNode3.setX(100);
		evalNode3.setY(300);
		evalNode3.setVariable("Shift-Taste");
		evalNode3.setCondition("gedrückt");
		evalModel1.getContents().add(evalNode3);

		CEGNode evalNode4 = RequirementsFactory.eINSTANCE.createCEGNode();
		evalNode4.setId("evalnode-4");
		evalNode4.setName("evalnode-4");
		evalNode4.setDescription("");
		evalNode4.setX(500);
		evalNode4.setY(100);
		evalNode4.setVariable("Aktuelle Zelle");
		evalNode4.setCondition("markiert");
		evalModel1.getContents().add(evalNode4);

		CEGNode evalNode5 = RequirementsFactory.eINSTANCE.createCEGNode();
		evalNode5.setId("evalnode-5");
		evalNode5.setName("evalnode-5");
		evalNode5.setDescription("");
		evalNode5.setX(500);
		evalNode5.setY(200);
		evalNode5.setVariable("Vorhandene Markierung");
		evalNode5.setCondition("gelöscht");
		evalModel1.getContents().add(evalNode5);

		CEGNode evalNode6 = RequirementsFactory.eINSTANCE.createCEGNode();
		evalNode6.setId("evalnode-6");
		evalNode6.setName("evalnode-6");
		evalNode6.setDescription("");
		evalNode6.setX(500);
		evalNode6.setY(300);
		evalNode6.setVariable("Alle Zwischenzellen");
		evalNode6.setCondition("markiert");
		evalModel1.getContents().add(evalNode6);

		CEGConnection evalConn1 = RequirementsFactory.eINSTANCE.createCEGConnection();
		evalConn1.setId("evalConn1");
		evalConn1.setName("-");
		evalConn1.setSource(evalNode1);
		evalConn1.setTarget(evalNode4);
		evalConn1.setNegate(false);
		evalModel1.getContents().add(evalConn1);

		CEGConnection evalConn2 = RequirementsFactory.eINSTANCE.createCEGConnection();
		evalConn2.setId("evalConn2");
		evalConn2.setName("-");
		evalConn2.setSource(evalNode1);
		evalConn2.setTarget(evalNode5);
		evalConn2.setNegate(false);
		evalModel1.getContents().add(evalConn2);

		CEGConnection evalConn3 = RequirementsFactory.eINSTANCE.createCEGConnection();
		evalConn3.setId("evalConn3");
		evalConn3.setName("-");
		evalConn3.setSource(evalNode1);
		evalConn3.setTarget(evalNode6);
		evalConn3.setNegate(false);
		evalModel1.getContents().add(evalConn3);

		CEGConnection evalConn4 = RequirementsFactory.eINSTANCE.createCEGConnection();
		evalConn4.setId("evalConn4");
		evalConn4.setName("-");
		evalConn4.setSource(evalNode2);
		evalConn4.setTarget(evalNode5);
		evalConn4.setNegate(true);
		evalModel1.getContents().add(evalConn4);

		CEGConnection evalConn5 = RequirementsFactory.eINSTANCE.createCEGConnection();
		evalConn5.setId("evalConn5");
		evalConn5.setName("-");
		evalConn5.setSource(evalNode3);
		evalConn5.setTarget(evalNode5);
		evalConn5.setNegate(true);
		evalModel1.getContents().add(evalConn5);

		CEGConnection evalConn6 = RequirementsFactory.eINSTANCE.createCEGConnection();
		evalConn6.setId("evalConn6");
		evalConn6.setName("-");
		evalConn6.setSource(evalNode3);
		evalConn6.setTarget(evalNode6);
		evalConn6.setNegate(false);
		evalModel1.getContents().add(evalConn6);
		return evalModel1;
	}

}
