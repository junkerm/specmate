package org.xtext.specmate.resolve.matcher;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;

public abstract class Matcher {
	
	private Optional<Matcher> parent;
	private List<Arc> children;
	public Matcher() { 
		this.parent = Optional.empty();
		this.children = new Vector<Arc>();
	}
	
	public void addArc(Matcher rNode, String tag) throws MatcherException {
		if (rNode.hasParent() && !rNode.getParent().equals(this)) {
			throw MatcherException.illegalTwoParentNode(rNode, this, rNode.getParent());
		}
		
		Set<String> sTree = this.getSubtree();
		if(sTree.size() > 0 && rNode.getSubtree().containsAll(sTree)) {
			throw MatcherException.circularRule(this, rNode);
		}
		
		
		rNode.setParent(this);
		this.children.add(new Arc(rNode, tag));
	}

	public boolean hasParent() {
		return this.parent.isPresent();
	}
	
	public Matcher getParent() {
		return this.parent.get();
	}
	
	public void setParent(Matcher parent) {
		this.parent = Optional.of(parent);
	}
	
	public Set<String> getSubtree() {
		return new HashSet<String>();
	}

	public boolean hasChildren() {
		return this.children.size() > 0;
	}
	
	public List<Matcher> getChildMatchers() {
		return this.children.stream().map(arc -> arc.child).collect(Collectors.toList());
	}
	
	public List<Arc> getChildren() {
		return this.children;
	}
	
	public String getConstructorString() {
		return "Matcher()";
	}
	
	public abstract String getRepresentation();
	
	@Override
	public String toString() {
		String out = "";
		for(Arc a: children) {
			out += this.getRepresentation() + a+"\n";
		}
		return out.trim();
	}
}
