package com.specmate.model.support.commands;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

public class GenericCreateCommand implements ICommand {

	private EObject parent;
	private EObject toAdd;
	private String featureName;

	public GenericCreateCommand(EObject parent, EObject toAdd, String featureName) {
		this.toAdd = toAdd;
		this.parent = parent;
		this.featureName = featureName;
	}

	@Override
	public void execute() {
		if (parent instanceof Resource) {
			((Resource) parent).getContents().add(toAdd);
		} else {
			EStructuralFeature containmentFeature = parent.eClass().getEStructuralFeature(featureName);
			if (containmentFeature.isMany()) {
				((EList<Object>) parent.eGet(containmentFeature)).add(toAdd);
			} else {
				parent.eSet(containmentFeature, toAdd);
			}
		}
	}

}
