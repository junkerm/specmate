package com.specmate.bdd;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang3.tuple.Pair;
import org.logicng.formulas.Formula;
import org.logicng.formulas.FormulaFactory;
import org.logicng.formulas.Literal;
import org.logicng.transformations.qmc.QuineMcCluskeyAlgorithm;
import static org.logicng.formulas.FType.*;
import com.specmate.model.base.IModelNode;
import com.specmate.model.bdd.*;
import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;
import com.specmate.common.AssertUtil;

public class BDD2CEGTranslator {
	
	//list of all nodes in the model
	private List<IModelNode> nodes;
	
	//bidirectional mapping between indices and (variable, condition) pairs
	public DoubleMap indexmap;
	
	//mapping from actual node to corresponding RecNode
	private Map<BDDNode, RecNode> act2rec;
	
	/**
	 * Creates a RecNode for each element in the nodes list and creates the
	 * mapping between them (in act2rec).
	 */
	public void makeRecNodes(){
		act2rec = new HashMap<>();
		for(IModelNode node : nodes){
			RecNode rec = new RecNode((BDDNode) node);
			act2rec.put((BDDNode) node, rec);  
		}
	}
	
	/**
	 * This code was originally used to check the BDD. For now, the only purpose  
	 * of this method is to establish the mapping between (variable, condition) pairs
	 * and indices (stored in indexmap).
	 */
	public void createIndexMap(){
		//put all nodes without incoming connections into a set
		Set<IModelNode> starters = new HashSet<>();
		nodes.stream().filter(node -> (node.getIncomingConnections().isEmpty())).forEach(node -> {
			starters.add(node);
		});
			
		//set the start node
		RecNode start = null;
		for (IModelNode node : starters){
			//get the RecNode from act2rec
			start = act2rec.get((BDDNode) node);
		}
		
		//initialize a new indexmap each time this method is executed
		indexmap = new DoubleMap();
		
		//start recursion
		start.setIndices(0, indexmap, act2rec);
	}
	
	/*
	 * Translates the BDD into a minimal disjunctive normal form (DNF).
	 * The BDD is traversed starting from the 1-terminals.
	 * The representation and minimization of the formula are handled by the LogicNG library.
	 */
	public Formula translateToDNF(){
		//put all 1-terminals into the ones set 
		Set<IModelNode> ones = new HashSet<>();
		nodes.stream().filter(node -> (node.getOutgoingConnections().isEmpty())).forEach(node -> {
			ones.add(node);
		});
		for(IModelNode node : ones){
			AssertUtil.assertTrue(node instanceof BDDTerminalNode);
			//only terminals have the isValue() method
			if(((BDDTerminalNode) node).isValue()==false){
				//0-terminals are removed
				ones.remove(node);
			}
		}
		
		//formula factory to create formulas
		FormulaFactory f = new FormulaFactory();
		
		//will contain a formula (conjunction) for each path
		Set<Formula> products = new HashSet<>();
				
		//start from each 1-terminal
		for (IModelNode node : ones) {
			//get the RecNode from act2rec
			RecNode current = act2rec.get((BDDNode) node);
			//first parameter: current path, which is initially just true
			current.findPaths(f.verum(), products, indexmap, act2rec);
		}
				
		//create the sum of all products
		Formula dnf = f.or(products);
				
		//return minimized DNF
		return QuineMcCluskeyAlgorithm.compute(dnf);
	}
	
	/*
	 * Creates a CEG from the given formula if possible.
	 * Decides whether the given formula is a LITERAL or an OR.
	 */
	public CEGModel createCeg(Formula formula) {
		switch(formula.type()) {
			case LITERAL: return literalCase(formula);	
			case OR: return orCase(formula);	
			default: throw new IllegalArgumentException("ceg cannot be generated from this formula");
		}			
	}
	
	/*
	 * Handles the LITERAL case of the CEG generation. In this case the final CEG consists of an
	 * input node, an output node and a connection between them.
	 * LITERAL case: formula that is only a literal, e.g. ~A.
	 */
	private CEGModel literalCase(Formula formula){
		CEGModel ceg = createBasicCeg();
		
		//create the input node
		CEGNode in = RequirementsFactory.eINSTANCE.createCEGNode();
		in.setId("node-in-1");
		//get (variable, condition) pair for the index
		Pair<String, String> vc = indexmap.getPairFor(Integer.parseInt(((Literal) formula).name()));
		in.setVariable(vc.getLeft());
		in.setCondition(vc.getRight());
		
		//create connection
		CEGConnection connection = RequirementsFactory.eINSTANCE.createCEGConnection();
		connection.setId("conn-1");
		connection.setSource(in);
		//it is assumed that the out node is the first element of the model
		connection.setTarget((CEGNode) ceg.getContents().get(0));
		if(((Literal) formula).phase()==false) {
			//negated literal (e.g. ~A) -> negated connection
			connection.setNegate(true);
		}
		
		//add components to model
		ceg.getContents().add(in);
		ceg.getContents().add(connection);
		return ceg;
	}
	
	/*
	 * Creates a CEG Model with an output node as the only element of the model.
	 * The returned model can be used for both the LITERAL and the OR case.
	 */
	private CEGModel createBasicCeg(){
		//create CEGModel
		CEGModel ceg = RequirementsFactory.eINSTANCE.createCEGModel();
		ceg.setId("Model-1");
		
		//create output node
		CEGNode out = RequirementsFactory.eINSTANCE.createCEGNode();
		out.setId("node-out");
		out.setVariable("BDD");
		out.setCondition("is true");
		out.setType(NodeType.OR);
		
		//add component to model
		ceg.getContents().add(out);
		return ceg;
	}
	
	/*
	 * Creates a CEG Model for a formula that is a disjunction, e.g. ~A | A & B.
	 */
	private CEGModel orCase(Formula formula){
		CEGModel ceg = createBasicCeg();
		
		//mapping from variable name to node to make sure that there is only one node for each variable
		Map<String, CEGNode> name2node = new HashMap<>();
		
		//current count of the nodes representing a variable; used for setting the node ids
		int number_in_nodes = 0;
		
		//current count of the connection; used for setting the connection ids
		int number_connections = 0;
		
		//current number of minterms; used for setting the node ids
		int number_minterms = 0;
			
		//iterate through all minterms of the formula
		Iterator<Formula> or_it = formula.iterator();	
		while(or_it.hasNext()) {
			Formula minterm = or_it.next();
			AssertUtil.assertTrue(minterm.type()==LITERAL||minterm.type()==AND);
			
			if(minterm.type()==LITERAL) {
				//only incremented if new in node has to be created
				number_in_nodes = number_in_nodes + orCase_lit(ceg, (Literal) minterm, name2node, number_in_nodes, number_connections);
				//is incremented anyway
				number_connections++;					
			}else{ //minterm.type()==AND
				Pair<Integer, Integer> pair = orCase_and(ceg, minterm, name2node, number_minterms, number_connections, number_in_nodes);
				//updating the variables
				number_minterms++;
				number_connections = number_connections + 1 + pair.getRight();	
				number_in_nodes = number_in_nodes + pair.getLeft();
			}
		} //end of while
		
		return ceg;
	}
	
	/*
	 * Is called when a minterm is just a literal. In this case the node for this variable
	 * is directly connected to the output node (no extra minterm node).
	 */
	private int orCase_lit(CEGModel ceg, Literal l, Map<String, CEGNode> name2node, int number_in_nodes, int number_connections){
		//tells the calling method how number_of_nodes should be increased
		int ret = 0;
		
		//in node for this variable has not been created yet 
		if(!name2node.containsKey(l.name())){
			CEGNode node = RequirementsFactory.eINSTANCE.createCEGNode();
			node.setId("node-in-"+(number_in_nodes+1));	
			Pair<String, String> pair = indexmap.getPairFor(Integer.parseInt(l.name()));
			node.setVariable(pair.getLeft());
			node.setCondition(pair.getRight());
			ceg.getContents().add(node);
			//new node -> increase number_of_nodes
			ret = 1;
			name2node.put(l.name(), node);		
		}
		
		//create connection from this in node to output
		CEGConnection connection = RequirementsFactory.eINSTANCE.createCEGConnection();
		connection.setId("conn-"+(number_connections+1));
		//get the node for this variable from the map
		connection.setSource(name2node.get(l.name()));
		//the output node has been added first, so it should be in the first place
		connection.setTarget((CEGNode) ceg.getContents().get(0));
		if(l.phase()==false) {
			//negated literal (e.g. ~A) -> negated connection
			connection.setNegate(true);
		}
		ceg.getContents().add(connection);
		
		return ret;
	}
	
	private Pair<Integer, Integer> orCase_and(CEGModel ceg, Formula minterm, Map<String, CEGNode> name2node, int number_minterms, int number_connections, int number_in_nodes){
		//tells the caller how many in nodes and connections have been created
		int add_in = 0;
		int add_conn = 0;
		
		//create a new node for this minterm
		CEGNode minnode = RequirementsFactory.eINSTANCE.createCEGNode();
		minnode.setId("node-min-"+(number_minterms+1));		
		minnode.setVariable("Min"+(number_minterms+1));
		minnode.setCondition("is true");
		minnode.setType(NodeType.AND);
		ceg.getContents().add(minnode);
		
		//create connection from this minnode to output
		CEGConnection min2out = RequirementsFactory.eINSTANCE.createCEGConnection();
		min2out.setId("conn-"+(number_connections+1));
		min2out.setSource(minnode); 
		//out node should be the first element
		min2out.setTarget((CEGNode) ceg.getContents().get(0));
		ceg.getContents().add(min2out);
		
		//iterate through all literals of the minterm
		Iterator<Formula> and_it = minterm.iterator();
		while(and_it.hasNext()) {
			Formula f = and_it.next();
			AssertUtil.assertTrue(f.type()==LITERAL);
			Literal l = (Literal) f;
			
			//in node for this variable has not been created yet 
			if(!name2node.containsKey(l.name())){
				CEGNode innode = RequirementsFactory.eINSTANCE.createCEGNode();
				add_in++;
				innode.setId("node-in-"+(number_in_nodes+add_in));
				Pair<String, String> pair = indexmap.getPairFor(Integer.parseInt(l.name()));	
				innode.setVariable(pair.getLeft());
				innode.setCondition(pair.getRight());
				name2node.put(l.name(), innode);
				ceg.getContents().add(innode);
			}
			
			//create connection from this in node to minterm
			CEGConnection in2min = RequirementsFactory.eINSTANCE.createCEGConnection();
			add_conn++;
			in2min.setId("conn-"+(number_connections+add_conn));
			in2min.setSource(name2node.get(l.name()));
			in2min.setTarget(minnode);
			if(l.phase()==false) {
				//negated literal (e.g. ~A) -> negated connection
				in2min.setNegate(true);
			}
			ceg.getContents().add(in2min);
		}
		
		return Pair.of(add_in, add_conn);
	}
	
	/*
	 * the method to be called from other classes 
	 */
	public CEGModel translate(BDDModel model) {
		setupNodes(model);
		makeRecNodes();
		createIndexMap();
		Formula f = translateToDNF();
		return createCeg(f);
	}
	
	public void setupNodes(BDDModel model){
		nodes = (List<IModelNode>) SpecmateEcoreUtil.pickInstancesOf(model.getContents(), IModelNode.class);
	}
}
