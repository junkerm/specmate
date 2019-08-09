package com.specmate.testspecification.internal.services;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import com.specmate.model.requirements.CEGNode;

public class CEGNodeEvaluationComparator implements Comparator<CEGNodeEvaluation> {

	@Override
	public int compare(CEGNodeEvaluation nodeEval1, CEGNodeEvaluation nodeEval2) {
		Set<Entry<CEGNode, TaggedBoolean>> sortedNodeSet1 = nodeEval1.entrySet();
		Set<Entry<CEGNode, TaggedBoolean>> sortedNodeSet2 = nodeEval2.entrySet();

		Iterator<Entry<CEGNode, TaggedBoolean>> it1 = sortedNodeSet1.iterator();
		Iterator<Entry<CEGNode, TaggedBoolean>> it2 = sortedNodeSet2.iterator();

		while (it1.hasNext() && it2.hasNext()) {
			Entry<CEGNode, TaggedBoolean> sortedNodeSetEntry1 = it1.next();
			Entry<CEGNode, TaggedBoolean> sortedNodeSetEntry2 = it2.next();
			CEGNode node1 = sortedNodeSetEntry1.getKey();
			CEGNode node2 = sortedNodeSetEntry2.getKey();

			/*
			 * Compare properties of both evaluations until they differ Check order: 1.
			 * Compare names of nodes 2. Compare conditions of nodes 3. Compare
			 * taggedBoolean value property of nodes 4. Compare taggedBoolean tag property
			 * of nodes
			 */
			int result = node1.getVariable().compareTo(node2.getVariable());
			if (result != 0) {
				return result;
			}
			// Check if the condition of the nodes is also the same
			int conditionResult = node1.getCondition().compareTo(node2.getCondition());
			if (result != 0) {
				return conditionResult;
			}

			/*
			 * Check if the taggedBoolean values are also equal Order: false < true
			 */
			TaggedBoolean taggedBoolean1 = sortedNodeSetEntry1.getValue();
			TaggedBoolean taggedBoolean2 = sortedNodeSetEntry2.getValue();
			int boolResult = Boolean.compare(taggedBoolean1.value, taggedBoolean2.value);
			if (boolResult != 0) {
				return boolResult;
			}

			/*
			 * Values are also equal, check if the tags are equal Order: all < any < auto
			 */
			int tagResult = taggedBoolean1.tag.compareTo(taggedBoolean2.tag);
			if (tagResult != 0) {
				return tagResult;
			}
		}
		/*
		 * Both or one of the sets has come to an end Either one set of evaluation is a
		 * subset of the other, or they are the same
		 */
		if (!it1.hasNext() && !it2.hasNext()) {
			/*
			 * Both sets are the same Even if the current test generation heuristic does not
			 * create two sets which are the same, we still need this in order to delete
			 * evaluations from the set
			 */
			return 0;
		}
		if (!it1.hasNext()) {
			// set1 is smaller, set nodeEval1 < nodeEval2
			return -1;
		}
		// set2 is smaller, set nodeEval2 < nodeEval1
		return 1;
	}
}
