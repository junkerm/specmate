package com.specmate.testspecification.internal.generators;

import java.util.Comparator;
import java.util.TreeMap;

import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.CEGNode;

@SuppressWarnings("serial")
class CEGNodeEvaluation extends TreeMap<IContainer, TaggedBoolean> {
	
	private static Comparator<IContainer> evaluationComperator = new Comparator<IContainer>() {
		// Sort each evaluation, so that the order of nodes in an evaluation are lexicographically sorted
		@Override
		public int compare(IContainer c1, IContainer c2) {
			CEGNode node1 = (CEGNode) c1;
			CEGNode node2 = (CEGNode) c2;
			
			int result = node1.getVariable().compareTo(node2.getVariable());
			if(result != 0) {
				return result;
			} else {
				return node1.getCondition().compareTo(node2.getCondition());
			}
		}
	};
	
	public CEGNodeEvaluation() {
		super(evaluationComperator);
	}
}