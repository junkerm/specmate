package com.specmate.testspecification.internal.generators;

import java.util.Comparator;
import java.util.TreeMap;

import com.specmate.model.base.IContainer;
import com.specmate.model.requirements.CEGNode;

@SuppressWarnings("serial")
class CEGNodeEvaluation extends TreeMap<CEGNode, TaggedBoolean> {

	private static Comparator<CEGNode> evaluationComperator = new Comparator<CEGNode>() {
		// Sort each evaluation, so that the order of nodes in an evaluation are
		// lexicographically sorted
		@Override
		public int compare(CEGNode c1, CEGNode c2) {

			int result = c1.getVariable().compareTo(c2.getVariable());
			if (result != 0) {
				return result;
			} else {
				return c1.getCondition().compareTo(c2.getCondition());
			}
		}
	};

	public CEGNodeEvaluation() {
		super(evaluationComperator);
	}
}