package com.specmate.bdd;

import com.specmate.model.bdd.BDDConnection;
import com.specmate.model.bdd.BDDModel;
import com.specmate.model.bdd.BDDNoTerminalNode;
import com.specmate.model.bdd.BDDTerminalNode;
import com.specmate.model.bdd.BddFactory;

public class Main {

	public static void main(String[] args) {
		BDDModel bm  = hardCodedBDD();
		/*
		
		BDD2CEGTranslator bdd2ceg = new BDD2CEGTranslator();
		bdd2ceg.setupNodes(bm);
		bdd2ceg.makeRecNodes();
		bdd2ceg.createIndexMap();
		System.out.println(bdd2ceg.indexmap);
		Formula f = bdd2ceg.translateToDNF();
		System.out.println(f);

		*/
	}
	
	/*
	 * Returns a Specmate BDD model that can be used for tests.
	 */
	public static BDDModel hardCodedBDD(){
		//model properties
		BDDModel model = BddFactory.eINSTANCE.createBDDModel();
		model.setId("Model-1");
		
		//node 1
		BDDNoTerminalNode node1 = BddFactory.eINSTANCE.createBDDNoTerminalNode();
		node1.setId("node-1");
		node1.setVariable("A");
		node1.setCondition("is true");
		
		//node 2
		BDDNoTerminalNode node2 = BddFactory.eINSTANCE.createBDDNoTerminalNode();
		node2.setId("node-2");
		node2.setVariable("B");
		node2.setCondition("is true");
		
		//node 3
		BDDNoTerminalNode node3 = BddFactory.eINSTANCE.createBDDNoTerminalNode();
		node3.setId("node-3");
		node3.setVariable("C");
		node3.setCondition("is true");
		
		//terminal 1
		BDDTerminalNode terminal1 = BddFactory.eINSTANCE.createBDDTerminalNode();
		terminal1.setId("term-1");
		terminal1.setValue(true);
		
		//terminal 2
		BDDTerminalNode terminal2 = BddFactory.eINSTANCE.createBDDTerminalNode();
		terminal2.setId("term-2");
		terminal2.setValue(false);
		
		//terminal 3
		BDDTerminalNode terminal3 = BddFactory.eINSTANCE.createBDDTerminalNode();
		terminal3.setId("term-3");
		terminal3.setValue(false);
		
		//terminal 4
		BDDTerminalNode terminal4 = BddFactory.eINSTANCE.createBDDTerminalNode();
		terminal4.setId("term-4");
		terminal4.setValue(true);
		
		//connection 1
		BDDConnection connection1 = BddFactory.eINSTANCE.createBDDConnection();
		connection1.setId("conn-1");
		connection1.setNegate(true);
		connection1.setSource(node1);
		connection1.setTarget(node2);
		
		//connection 2
		BDDConnection connection2 = BddFactory.eINSTANCE.createBDDConnection();
		connection2.setId("conn-2");
		connection2.setSource(node1);
		connection2.setTarget(terminal4);
		
		//connection 3
		BDDConnection connection3 = BddFactory.eINSTANCE.createBDDConnection();
		connection3.setId("conn-3");
		connection3.setNegate(true);
		connection3.setSource(node2);
		connection3.setTarget(node3);
		
		//connection 4
		BDDConnection connection4 = BddFactory.eINSTANCE.createBDDConnection();
		connection4.setId("conn-4");
		connection4.setSource(node2);
		connection4.setTarget(terminal3);
		
		//connection 5
		BDDConnection connection5 = BddFactory.eINSTANCE.createBDDConnection();
		connection5.setId("conn-5");
		connection5.setNegate(true);
		connection5.setSource(node3);
		connection5.setTarget(terminal2);
		
		//connection 6
		BDDConnection connection6 = BddFactory.eINSTANCE.createBDDConnection();
		connection6.setId("conn-6");
		connection6.setSource(node3);
		connection6.setTarget(terminal1);
		
		model.getContents().add(node1);
		model.getContents().add(node2);
		model.getContents().add(node3);
		model.getContents().add(terminal1);	
		model.getContents().add(terminal2);	
		model.getContents().add(terminal3);	
		model.getContents().add(terminal4);	
		model.getContents().add(connection1);	
		model.getContents().add(connection2);
		model.getContents().add(connection3);
		model.getContents().add(connection4);
		model.getContents().add(connection5);
		model.getContents().add(connection6);
		
		return model;
	}
	

	
	

}
