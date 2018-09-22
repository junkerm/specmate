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

	/**
	 * Validates that the new object is not created at the top-level, i.e.
	 * immediately below the project root
	 */
	public void validateNotTopLevel(Object parent, EObject object) throws SpecmateValidationException;

	/**
	 * Validates that the object is not a top-level library folder
	 */
	public void validateNotTopLevelLibraryFolder(Object parent, EObject object) throws SpecmateValidationException;
}
