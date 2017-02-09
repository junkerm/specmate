package com.specmate.persistency.event;

public enum EChangeKind {
	SET("set"),ADD("add"),REMOVE("remove"),DELETE("delete"), NEW("new"), CLEAR("clear");
	
	private String type;
	
	EChangeKind(String type){
		this.type=type;
	}
	
	public String toString(){
		return type;
	}
}
