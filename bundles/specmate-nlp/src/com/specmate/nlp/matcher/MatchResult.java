package com.specmate.nlp.matcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.uima.fit.util.JCasUtil;
import org.apache.uima.jcas.tcas.Annotation;

public class MatchResult {

	private boolean isMatch;
	private Map<String, List<Annotation>> matchGroups = new HashMap<>();

	public MatchResult(boolean isMatch) {
		this.isMatch = isMatch;
	}

	public boolean isMatch() {
		return isMatch;
	}

	public void setMatch(boolean isMatch) {
		this.isMatch = isMatch;
	}

	public Map<String, List<Annotation>> getMatchGroups() {
		return matchGroups;
	}

	public List<Annotation> addMatchGroup(String arg0, List<Annotation> arg1) {
		return matchGroups.put(arg0, arg1);
	}
	
	public String getMatchGroupAsText(String group){
		List<Annotation> matchGroup = matchGroups.get(group);
		if(matchGroup==null){
			return null;
		}
		return StringUtils.join(JCasUtil.toText(matchGroup)," ");
	}

	public void addMatchGroupsFrom(MatchResult matchResult) {
		Map<String, List<Annotation>> additionalMatchGroups = matchResult.getMatchGroups();
		if (additionalMatchGroups != null) {
			matchGroups.putAll(additionalMatchGroups);
		}
	}

}
