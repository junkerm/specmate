package com.specmate.testspecification.services;

import java.util.HashMap;

import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.CEGNode;

class NodeEvaluation extends HashMap<CEGNode, TaggedBoolean> {
	private CEGModel model;

	public NodeEvaluation(CEGModel model) {
		super();
		this.model = model;
	}

	public CEGModel getModel() {
		return model;
	}

}