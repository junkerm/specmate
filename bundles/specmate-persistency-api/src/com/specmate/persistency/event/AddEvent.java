package com.specmate.persistency.event;

public class AddEvent {

	private Object value;
	private int index;
	private String name;

	public AddEvent( String name, int index, Object value) {
		this.name=name;
		this.index=index;
		this.value=value;
	}

	public Object getValue() {
		return value;
	}

	public int getIndex() {
		return index;
	}

	public String getName() {
		return name;
	}


}
