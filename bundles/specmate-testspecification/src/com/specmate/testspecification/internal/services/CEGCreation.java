package com.specmate.testspecification.internal.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import com.specmate.model.requirements.CEGConnection;
import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;
import com.specmate.model.requirements.NodeType;
import com.specmate.model.requirements.RequirementsFactory;
import com.specmate.model.support.util.SpecmateEcoreUtil;

/**
 * Class creates Node and Edges for a CEG-Graphs
 * 
 * @author Andreas Wehrle
 * 
 */
public class CEGCreation {

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/**
	 * Create a new node and add it to the CEGModel
	 * 
	 * @param model
	 * @param variable
	 * @param condition
	 * @param x
	 * @param y
	 * @param type
	 * @return the created node
	 */
	public CEGNode createNode(CEGModel model, String variable, String condition, int x, int y, NodeType type) {
		CEGNode node = RequirementsFactory.eINSTANCE.createCEGNode();
		node.setId(SpecmateEcoreUtil.getIdForChild(model, node.eClass()));
		node.setVariable(variable);
		node.setCondition(condition);
		node.setY(y);
		node.setX(x);
		node.setType(type);
		model.getContents().add(node);
		return node;
	}

	/**
	 * Create a new connection to the CEGModel
	 * 
	 * @param model
	 * @param nodeFrom
	 * @param nodeTo
	 * @param negate
	 * @return
	 */
	public CEGConnection createConnection(CEGModel model, CEGNode nodeFrom, CEGNode nodeTo, boolean negate) {
		CEGConnection con = RequirementsFactory.eINSTANCE.createCEGConnection();
		con.setId(SpecmateEcoreUtil.getIdForChild(model, con.eClass()));
		con.setSource(nodeFrom);
		con.setTarget(nodeTo);
		con.setNegate(negate);
		con.setName("New Connection " + dateFormat.format(new Date()));
		model.getContents().add(con);
		return con;
	}

	/**
	 * Create a new node it it does not exist in the list. Otherwise return the
	 * existing node. Nodes are only compared by variable, condition and type
	 * 
	 * @param list
	 * @param model
	 * @param variable
	 * @param condition
	 * @param x
	 * @param y
	 * @param type
	 * @return new or existing node
	 */
	public CEGNode createNodeIfNotExist(LinkedList<CEGNode> list, CEGModel model, String variable, String condition,
			int x, int y, NodeType type) {
		for (CEGNode cegNode : list) {
			if (cegNode.getVariable().equals(variable) && cegNode.getCondition().equals(condition)
					&& cegNode.getType().equals(type)) {
				return cegNode;
			}
		}
		CEGNode node = createNode(model, variable, condition, x, y, type);
		list.add(node);
		return node;
	}
}
