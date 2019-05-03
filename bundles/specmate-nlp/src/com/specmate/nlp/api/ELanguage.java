package com.specmate.nlp.api;

public enum ELanguage {
	DE("de", "SUBJ"), EN("en", "subj");

	private String language;
	private String subjectDependencyType;

	private ELanguage(String language, String subjectDependencyType) {
		this.language = language;
		this.subjectDependencyType = subjectDependencyType;
	}

	public String getLanguage() {
		return language;
	}

	public String getSubjectDependencyType() {
		return subjectDependencyType;
	}
}
