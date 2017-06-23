package com.specmate.connectors.hpconnector.internal;

import org.json.JSONObject;

import com.specmate.model.requirements.Requirement;

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
	}

	private static int getNumberOfTests(JSONObject jsonRequirement) {
		int boTests = jsonRequirement.optInt("numberOfTestsBO");
		int agTests = jsonRequirement.optInt("numberOfTestsAG");
		int tcTests = jsonRequirement.optInt("numberOfTestsTC");
		return boTests + agTests + tcTests;
	}
}
