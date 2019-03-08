package com.specmate.nlp.api;

public enum ELanguage {
	DE("de"), EN("en");

	private String language;

	private ELanguage(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return language;
	}
}
