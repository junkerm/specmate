package com.specmate.model.support.commands;

import org.eclipse.emf.ecore.EObject;

import com.specmate.model.support.util.SpecmateEcoreUtil;

public class GenericUpdateCommand extends CommandBase {

	private EObject originalObject;
	private EObject updateObject;

	public GenericUpdateCommand(EObject originalObject, EObject updateObject) {
		this.originalObject = originalObject;
		this.updateObject = updateObject;
	}

	@Override
	public EObject execute() {
		SpecmateEcoreUtil.copyAttributeValues(updateObject, originalObject);
		SpecmateEcoreUtil.copyReferences(updateObject, originalObject);
		return originalObject;
	}

}
