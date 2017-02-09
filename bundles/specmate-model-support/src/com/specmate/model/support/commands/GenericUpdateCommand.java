package com.specmate.model.support.commands;

import org.eclipse.emf.ecore.EObject;

import com.specmate.model.support.util.SpecmateEcoreUtil;

public class GenericUpdateCommand implements ICommand {

	private EObject originalObject;
	private EObject updateObject;

	public GenericUpdateCommand(EObject originalObject, EObject updateObject) {
		this.originalObject = originalObject;
		this.updateObject = updateObject;
	}

	@Override
	public void execute() {
		SpecmateEcoreUtil.copyAttributeValues(updateObject, originalObject);
		SpecmateEcoreUtil.copyReferences(updateObject, originalObject);
	}

}
