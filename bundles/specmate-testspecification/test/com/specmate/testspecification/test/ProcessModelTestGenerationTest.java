package com.specmate.testspecification.test;

import java.util.Arrays;

import org.junit.Test;

import com.specmate.model.processes.Process;
import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessDecision;
import com.specmate.model.processes.ProcessEnd;
import com.specmate.model.processes.ProcessNode;
import com.specmate.model.processes.ProcessStart;
import com.specmate.model.processes.ProcessStep;
import com.specmate.model.processes.ProcessesFactory;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestspecificationFactory;

public class ProcessModelTestGenerationTest {

	@Test
	public void testModelGeneration() {
		TestSpecification ts = getTestSpecification();

	}

	private TestSpecification getTestSpecification() {
		ProcessesFactory f = ProcessesFactory.eINSTANCE;
		TestSpecification ts = TestspecificationFactory.eINSTANCE.createTestSpecification();
		ts.setId("testspec");
		ts.setName("testspec");
		Process process = f.createProcess();
		ts.getContents().add(process);
		// connection start node (with/without condition)
		// step (with / without outcome)
		ProcessStart start = createStart("start");
		ProcessStep step_1 = createStep("step-1", "step1 outcome = present");
		ProcessConnection conn_s1 = connect(start, step_1, "conn-1", "start-step-condition is present");
		ProcessStep step_2 = createStep("step-2", "step2 outcome");
		ProcessConnection conn_s2 = connect(start, step_2, "conn-2", null);
		// merge between two steps
		ProcessStep step_3 = createStep("step-3", null);
		ProcessConnection conn_13 = connect(step_1, step_3, "conn-3", null);
		ProcessConnection conn_23 = connect(step_1, step_3, "conn-3", null);

		// decision 3 connections
		ProcessDecision decision = createDecision("dec-4", "decision");
		ProcessEnd end = createEnd("end");
		ProcessConnection conn_de = connect(decision, end, "conn_de", "end-condition");
		ProcessStep step_4 = createStep("step-4", null);
		ProcessConnection conn_d4 = connect(decision, step_4, "conn_d4", "opt-condition");
		ProcessConnection conn_4e = connect(step_4, end, "conn_4e", null);

		// loop
		ProcessConnection conn_d3 = connect(decision, step_3, "conn_d3", "loop-condition");

		process.getContents().addAll(Arrays.asList(start, step_1, conn_s1, step_2, conn_s2, step_3, conn_13, conn_23,
				decision, end, conn_de, step_4, conn_d4, conn_4e, conn_d3, ts));

		return ts;
	}

	private ProcessConnection connect(ProcessNode n1, ProcessNode n2, String id, String condition) {
		ProcessesFactory f = ProcessesFactory.eINSTANCE;
		ProcessConnection connection = f.createProcessConnection();
		connection.setSource(n1);
		connection.setTarget(n2);
		connection.setId(id);
		connection.setName(id);
		return connection;
	}

	private ProcessStep createStep(String id, String expectedOutcome) {
		ProcessesFactory f = ProcessesFactory.eINSTANCE;
		ProcessStep step = f.createProcessStep();
		step.setId(id);
		step.setName(id);
		step.setExpectedOutcome(expectedOutcome);
		return step;
	}

	private ProcessDecision createDecision(String id, String name) {
		ProcessesFactory f = ProcessesFactory.eINSTANCE;
		ProcessDecision decision = f.createProcessDecision();
		decision.setId(id);
		decision.setName(name);
		return decision;
	}

	private ProcessStart createStart(String id) {
		ProcessesFactory f = ProcessesFactory.eINSTANCE;
		ProcessStart start = f.createProcessStart();
		start.setId(id);
		start.setName(id);
		return start;
	}

	private ProcessEnd createEnd(String id) {
		ProcessesFactory f = ProcessesFactory.eINSTANCE;
		ProcessEnd end = f.createProcessEnd();
		end.setId(id);
		end.setName(id);
		return end;
	}

}
