package com.specmate.bdd;

import com.specmate.model.bdd.BDDNode;
import com.specmate.model.base.IModelConnection;
import com.specmate.model.base.IModelNode;
import com.specmate.model.bdd.BDDConnection;
import com.specmate.model.bdd.BDDNoTerminalNode;
import org.apache.commons.lang3.tuple.Pair;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.common.util.EList;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import com.specmate.common.AssertUtil;

/**
 * @author david
 * A class which implements the recursive algorithms on the BDD nodes.
 * Each RecNode has a reference to its corresponding actual node.
 */
public class RecNode {
	
	//a reference to the actual BDDNode that this RecNode represents
	private BDDNode actual;
	
	public RecNode(BDDNode actual) {
		this.actual = actual;
	}
	
	/*
	 * This recursive method is first called on the start node by createIndexMap().
	 * The parameters are: the index used for the last pair, an indexmap to store the
	 * mapping, and a mapping from actual nodes to RecNodes for recursive calls.
	 */
	public void setIndices(int current_index, DoubleMap indexmap, Map<BDDNode, RecNode> act2rec){
		if(actual instanceof BDDNoTerminalNode){
			//prepare a pair for this node
			 Pair<String, String> current = Pair.of(((BDDNoTerminalNode) actual).getVariable(), ((BDDNoTerminalNode) actual).getCondition());
			
			//the index for the recursive calls
			int nextIndex;
			
			//this (variable, condition) pair has been seen before
			if(indexmap.hasPair(current)){	
				//current_index is not incremented
				nextIndex = current_index;
			}else{
				//current_index is incremented
				nextIndex = current_index + 1;
				//add new pair to indexmap
				indexmap.add(current, nextIndex);		
			}
			
			//recursive calls on the children of the actual node
			for(IModelConnection conn : actual.getOutgoingConnections()){
				//get the RecNode for this child
				RecNode post = act2rec.get(((BDDNode) conn.getTarget()));
				post.setIndices(nextIndex, indexmap, act2rec);		
			}
		}else{//actual instanceof BDDTerminalNode
			//TODO should anything be done with terminals?
		}	
	}		
		
	/*
	 * For this method it is unnecessary to differentiate between terminal and nonterminal nodes.
	 * Every predecessor is a nonterminal anyway.
	 * It updates the current path, calls the predecessors and stores paths when they are complete.
	 */
	public void findPaths(Formula current, Set<Formula> paths, DoubleMap indexmap, Map<BDDNode, RecNode> act2rec){
		//list of incoming connections for this node
		EList<IModelConnection> incoming = actual.getIncomingConnections();
		
		//this node is the start node
		if(incoming.size() == 0){
			//the current path is complete and can be added to paths
			paths.add(current);
			return;
		}
		
		//formula factory to create formulas
		FormulaFactory f = new FormulaFactory();
		
		//process each incoming connection and the corresponding predecessor
		for (IModelConnection conn : incoming) {
			IModelNode pre = conn.getSource();
			AssertUtil.assertTrue(pre instanceof BDDNoTerminalNode);
			//add pre to current path: if conn is negated, the low edge has to be taken from pre
			Formula currentChanged;
			//the (variable, condition) pair for pre
			Pair<String, String> pair = Pair.of(((BDDNoTerminalNode) pre).getVariable(), ((BDDNoTerminalNode) pre).getCondition());
			if(((BDDConnection) conn).isNegate()){
				currentChanged = f.and(current, f.literal(Integer.toString(indexmap.getIndexFor(pair)), false));
			}else{
				currentChanged = f.and(current, f.variable(Integer.toString(indexmap.getIndexFor(pair))));
			}
			//recursive call on the RecNode of pre
			act2rec.get((BDDNode) pre).findPaths(currentChanged, paths, indexmap, act2rec);
			
			//the next time the original path (current) will be used again
		} //end of for
	}
}
