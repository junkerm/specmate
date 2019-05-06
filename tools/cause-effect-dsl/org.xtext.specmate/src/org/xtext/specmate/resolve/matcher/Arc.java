package org.xtext.specmate.resolve.matcher;

public class Arc {
	public Matcher child;
	public String tag;
	
	public Arc(Matcher child, String tag) {
		this.child = child;
		this.tag = tag;
	}
	
	@Override
	public String toString() {
		return " "+("--\t"+tag+"\t--> "+child.getRepresentation()+"\n"+child).trim();
	}

}
