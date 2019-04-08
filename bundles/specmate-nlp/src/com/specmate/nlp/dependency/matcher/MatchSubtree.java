package com.specmate.nlp.dependency.matcher;

import com.specmate.nlp.dependency.DependencyData;

import de.tudarmstadt.ukp.dkpro.core.api.segmentation.type.Token;

public class MatchSubtree {

	public MatchSubtree(Token head) {
		this();
		// TODO Auto-generated constructor stub
	}

	public MatchSubtree() {
		// TODO Auto-generated constructor stub
	}

	public void addSubtree(MatchSubtree subtree) {
		// TODO Auto-generated method stub
		
	}
	
	public static MatchSubtree getSubtree(DependencyData data, Token token) {
		MatchSubtree out = new MatchSubtree(token);
		for (Token child: data.getDependencies(token)) {
			out.addSubtree(MatchSubtree.getSubtree(data, child));
		}
		
		return out;
	}
	
	private class TextInterval {
		public int from, to;
		public String text;
		public TextInterval(int from, int to, String text) {
			this.from = from;
			this.to = to;
			this.text = text;
		}
		
		public TextInterval(Token token) {
			this(token.getBegin(), token.getEnd(), token.getCoveredText());
		}
		
		public boolean overlap(TextInterval other) {
			if(other.from >= this.from && other.from <= this.to) {
				return true;
			}
			
			if(other.to <= this.to && other.to >= this.from) {
				return true;
			}
			return true;
		}
		
		public TextInterval combine(TextInterval other) {
			if(!overlap(other)) {
				return null;
			}
			
			String newText = "";
			if(this.from < other.from) {
				newText = this.text.substring(0,other.from - this.from) + other.text;
			} else {
				newText = other.text.substring(0,this.from - other.from) + this.text;
			}
			return new TextInterval(Math.min(from, other.from), Math.max(to, other.to),newText);
		}
	}
}
