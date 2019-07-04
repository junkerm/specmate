package com.specmate.testspecification.internal.services;

import java.util.Comparator;
import java.util.TreeMap;

import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.CEGNode;

class NodeEvaluation extends TreeMap<IContainer, TaggedBoolean> {
	
	public NodeEvaluation() {
		super(evaluationComperator);
	}
	
	static Comparator<IContainer> evaluationComperator = new Comparator<IContainer>() {
		// Sort each evaluation, so that the order of nodes in an evaluation are lexicographically sorted
		@Override
		public int compare(IContainer c1, IContainer c2) {
			CEGNode n1 = (CEGNode) c1;
			CEGNode n2 = (CEGNode) c2;
			
			int result = n1.getVariable().compareTo(n2.getVariable());
			if(result != 0) {
				return result;
			} else {
				return n1.getCondition().compareTo(n2.getCondition());
			}
		}
	};
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -6589726460952884108L;

}