package com.specmate.testspecification.test;

import static com.specmate.model.testspecification.ParameterType.INPUT;
import static com.specmate.model.testspecification.ParameterType.OUTPUT;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Assert;
import org.junit.Test;

import com.specmate.common.exception.SpecmateException;
import com.specmate.model.processes.Process;
import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessDecision;
import com.specmate.model.processes.ProcessEnd;
import com.specmate.model.processes.ProcessNode;
import com.specmate.model.processes.ProcessStart;
import com.specmate.model.processes.ProcessStep;
import com.specmate.model.processes.ProcessesFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestCase;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestSpecification;
import com.specmate.model.testspecification.TestspecificationFactory;
import com.specmate.testspecification.internal.services.ProcessTestCaseGenerator;

public class ProcessModelTestGenerationTest {

	@Test
	public void testModelGeneration() throws SpecmateException {
		TestSpecification ts = getTestSpecification();
		ProcessTestCaseGenerator generator = new ProcessTestCaseGenerator(ts);
		generator.generate();

		List<TestParameter> parameters = SpecmateEcoreUtil.pickInstancesOf(ts.getContents(), TestParameter.class);
		Assert.assertEquals(5, parameters.size());
		assertParameter(parameters, "step1 outcome", OUTPUT);
		assertParameter(parameters, "start-step-condition is present", INPUT);
		assertParameter(parameters, "step2 outcome", OUTPUT);
		assertParameter(parameters, "decision", INPUT);
		assertParameter(parameters, "decision 2", INPUT);

		List<TestCase> testCases = SpecmateEcoreUtil.pickInstancesOf(ts.getContents(), TestCase.class);
		Assert.assertEquals(3, testCases.size());
		assertTestCase(testCases, Arrays.asList(Pair.of("start-step-condition is present", "is present"),
				Pair.of("step1 outcome", "present"), Pair.of("decision", "opt-condition")));

		assertTestCase(testCases,
				Arrays.asList(Pair.of("start-step-condition is present", "is present"),
						Pair.of("step1 outcome", "present"), Pair.of("decision", "loop-condition"),
						Pair.of("decision 2", "end-condition")));

		assertTestCase(testCases,
				Arrays.asList(Pair.of("step2 outcome", "is present"), Pair.of("decision", "end-condition")));

	}

	private void assertParameter(List<TestParameter> parameters, String name, ParameterType type) {
		Assert.assertTrue(
				parameters.stream().anyMatch(p -> p.getName().contentEquals(name) && p.getType().equals(type)));
	}

	private void assertTestCase(List<TestCase> testCases, List<Pair<String, String>> values) {
		Assert.assertTrue(testCases.stream().anyMatch(t -> {
			List<ParameterAssignment> assignments = SpecmateEcoreUtil.pickInstancesOf(t.getContents(),
					ParameterAssignment.class);
			return values.stream().allMatch(
					v -> assignments.stream().anyMatch(a -> a.getParameter().getName().contentEquals(v.getLeft())
							&& a.getCondition().contentEquals(v.getRight())));
		}));
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
		ProcessConnection conn_13 = connect(step_1, step_3, "conn-13", null);
		ProcessConnection conn_23 = connect(step_2, step_3, "conn-23", null);

		// decision 3 connections
		ProcessDecision decision = createDecision("dec-4", "decision");
		ProcessConnection conn_3d = connect(step_3, decision, "conn-3d", null);
		ProcessEnd end = createEnd("end");
		ProcessConnection conn_de = connect(decision, end, "conn_de", "end-condition");
		ProcessStep step_4 = createStep("step-4", null);
		ProcessConnection conn_d4 = connect(decision, step_4, "conn_d4", "opt-condition");
		ProcessConnection conn_4e = connect(step_4, end, "conn_4e", null);

		// loop
		ProcessConnection conn_d3 = connect(decision, step_3, "conn_d3", "loop-condition");

		process.getContents().addAll(Arrays.asList(start, step_1, conn_s1, step_2, conn_s2, step_3, conn_13, conn_23,
				decision, end, conn_de, step_4, conn_d4, conn_4e, conn_d3, conn_3d, ts));

		return ts;
	}

	private ProcessConnection connect(ProcessNode n1, ProcessNode n2, String id, String condition) {
		ProcessesFactory f = ProcessesFactory.eINSTANCE;
		ProcessConnection connection = f.createProcessConnection();
		connection.setSource(n1);
		connection.setTarget(n2);
		connection.setId(id);
		connection.setName(id);
		connection.setCondition(condition);
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
