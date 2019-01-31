package com.specmate.nlp.matcher;

import java.util.HashMap;
import java.util.Map;

public class MatchResult {

	private boolean isMatch;
	private Map<String, String> matchGroups = new HashMap<>();

	public MatchResult(boolean isMatch) {
		this.isMatch = isMatch;
	}

	public boolean isMatch() {
		return isMatch;
	}

	public void setMatch(boolean isMatch) {
		this.isMatch = isMatch;
	}

	public Map<String, String> getMatchGroups() {
		return matchGroups;
	}

	public void addMatchGroup(String name, String match) {
		matchGroups.put(name, match);
	}

	public String getMatchGroupAsText(String group) {
		return matchGroups.get(group);
	}

	public void addMatchGroupsFrom(MatchResult matchResult) {
		Map<String, String> additionalMatchGroups = matchResult.getMatchGroups();
		if (additionalMatchGroups != null) {
			matchGroups.putAll(additionalMatchGroups);
		}
	}

}
