package com.specmate.model.support.commands;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateException;

public class GenericRetrieveCommand extends CommandBase {

	private EObject object;

	public GenericRetrieveCommand(EObject object) {
		this.object = object;
	}

	@Override
	public EObject execute() throws SpecmateException {
		return this.object;
	}

}
