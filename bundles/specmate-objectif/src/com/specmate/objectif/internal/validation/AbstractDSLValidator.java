/*
 * generated by Xtext 2.17.1
 */
package com.specmate.objectif.internal.validation;

import java.util.ArrayList;
import java.util.List;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.xtext.validation.AbstractDeclarativeValidator;

public abstract class AbstractDSLValidator extends AbstractDeclarativeValidator {
	
	@Override
	protected List<EPackage> getEPackages() {
		List<EPackage> result = new ArrayList<EPackage>();
		result.add(com.specmate.objectif.internal.dSL.DSLPackage.eINSTANCE);
		return result;
	}
}
