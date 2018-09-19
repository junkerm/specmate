package com.specmate.model.support.api;

import org.eclipse.emf.ecore.EObject;

import com.specmate.common.SpecmateValidationException;
import com.specmate.model.base.Folder;

public interface IAttributeValidationService {
	/**
	 * Validates whether the id of <code>object</code> is defined and contains only
	 * valid symbols (letters, digits, _, -).
	 */
	public void validateID(EObject object) throws SpecmateValidationException;

	/**
	 * Validates whether the id of <object> is unique within the scope of
	 * <code>parent</code>.
	 */
	public void validateUniqueID(Object parent, EObject object) throws SpecmateValidationException;

	/**
	 * Validates whether the name of <code>folder</code> is defined and not empty.
	 */
	public void validateFolderName(Folder folder) throws SpecmateValidationException;
}
