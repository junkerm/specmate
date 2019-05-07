package com.specmate.cause_effect_patterns.resolve.util;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class HeadFindStructure<T> {
	
	private Map<T, T> heads;
	public HeadFindStructure() {
		this.heads = new HashMap<T,T>();
	}
	
	public void add(T element) {
		this.heads.put(element, element);
	}
	
	public void add(T element, T head) {
		this.add(element);
		this.setHead(element, head);
	}
	
	public void setHead(T element, T head) {
		T newHead = this.getHead(head);
		this.heads.replace(element, newHead);
	}
	
	public T getHead(T element) {
		if(!this.heads.containsKey(element)) {
			return null;
		}
		
		if (!this.heads.get(element).equals(element)) {
			T newHead = this.getHead(this.heads.get(element));
			this.heads.replace(element, newHead);
			return newHead;
		}
		return element;
	}

	public Set<T> getHeadSet() {
		HashSet<T> out = new HashSet<>();
		for(T element: this.heads.keySet()) {
			out.add(this.getHead(element));
		}
		return out;
	}
	
	public boolean contains(T element) {
		return this.heads.containsKey(element);
	}
}
