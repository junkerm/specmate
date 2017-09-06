package com.specmate.connectors.hpconnector.internal;

import java.util.List;

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

	public static void procedure2Json(TestProcedure procedure) {
		JSONObject jsonProc = new JSONObject();
		jsonProc.put("name", procedure.getName());
		jsonProc.put("description", procedure.getDescription());
		List<TestStep> steps = SpecmateEcoreUtil.pickInstancesOf(procedure.getContents(), TestStep.class);
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
		jsonForProcedure.put("steps", steps);
		return jsonForProcedure;
	}
}
