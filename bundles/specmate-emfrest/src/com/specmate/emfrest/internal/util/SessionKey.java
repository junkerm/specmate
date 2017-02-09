package com.specmate.emfrest.internal.util;

public enum SessionKey {
	PERSISTENCY("Persistency");
	
	private String key;

	private SessionKey(String key){
		this.key=key;
	}
	
	public String getKey(){
		return key;
	}
}

