/**
 */
package com.specmate.model.base;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Folder</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.Folder#isLibrary <em>Library</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getFolder()
 * @model
 * @generated
 */
public interface Folder extends ISpecmateModelObject {

	/**
	 * Returns the value of the '<em><b>Library</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Library</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Library</em>' attribute.
	 * @see #setLibrary(boolean)
	 * @see com.specmate.model.base.BasePackage#getFolder_Library()
	 * @model
	 * @generated
	 */
	boolean isLibrary();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.Folder#isLibrary <em>Library</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Library</em>' attribute.
	 * @see #isLibrary()
	 * @generated
	 */
	void setLibrary(boolean value);
} // Folder
