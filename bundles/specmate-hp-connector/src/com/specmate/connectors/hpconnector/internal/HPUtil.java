package com.specmate.connectors.hpconnector.internal;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.specmate.model.requirements.Requirement;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.model.testspecification.TestProcedure;
import com.specmate.model.testspecification.TestStep;

public class HPUtil {
	public static void updateRequirement(JSONObject jsonRequirement, Requirement requirement) {
		requirement.setName(jsonRequirement.optString("title"));
		requirement.setId(jsonRequirement.optString("extId"));
		requirement.setExtId(jsonRequirement.optString("extId"));
		requirement.setDescription(jsonRequirement.optString("description"));
		requirement.setPlannedRelease(jsonRequirement.optString("plannedRelease"));
		requirement.setExtId2(jsonRequirement.optString("extId2"));
		requirement.setImplementingBOTeam(jsonRequirement.optString("implementingBOTeam"));
		requirement.setImplementingITTeam(jsonRequirement.optString("implementingITTeam"));
		requirement.setImplementingUnit(jsonRequirement.optString("implementingUnit"));
		requirement.setNumberOfTests(getNumberOfTests(jsonRequirement));
		requirement.setStatus(jsonRequirement.optString("status"));
		requirement.setTac(jsonRequirement.optString("tac"));
		requirement.setSource("HP");
	}

	private static int getNumberOfTests(JSONObject jsonRequirement) {
		int boTests = jsonRequirement.optInt("numberOfTestsBO");
		int agTests = jsonRequirement.optInt("numberOfTestsAG");
		int tcTests = jsonRequirement.optInt("numberOfTestsTC");
		return boTests + agTests + tcTests;
	}

	public static JSONObject getJSONForTestProcedure(TestProcedure procedure) {
		JSONObject jsonForProcedure = new JSONObject();
		jsonForProcedure.put("name", procedure.getName());
		jsonForProcedure.put("description", procedure.getDescription());
		List<TestStep> steps = SpecmateEcoreUtil.pickInstancesOf(procedure.getContents(), TestStep.class);
		steps.sort((s1, s2) -> Integer.compare(s1.getPosition(), s2.getPosition()));
		jsonForProcedure.put("steps", steps.stream().map(s -> getJSONForTestStep(s)).collect(Collectors.toList()));
		return jsonForProcedure;
	}

	private static JSONObject getJSONForTestStep(TestStep step) {
		JSONObject jsonForStep = new JSONObject();
		jsonForStep.put("name", "Step " + (step.getPosition() + 1));
		jsonForStep.put("description", step.getDescription());
		jsonForStep.put("expected", step.getExpectedOutcome());
		return jsonForStep;
	}
}
