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
 *   <li>{@link com.specmate.model.base.Folder#isIsLibrary <em>Is Library</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getFolder()
 * @model
 * @generated
 */
public interface Folder extends ISpecmateModelObject {

	/**
	 * Returns the value of the '<em><b>Is Library</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Is Library</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Is Library</em>' attribute.
	 * @see #setIsLibrary(boolean)
	 * @see com.specmate.model.base.BasePackage#getFolder_IsLibrary()
	 * @model
	 * @generated
	 */
	boolean isIsLibrary();

	/**
	 * Sets the value of the '{@link com.specmate.model.base.Folder#isIsLibrary <em>Is Library</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Is Library</em>' attribute.
	 * @see #isIsLibrary()
	 * @generated
	 */
	void setIsLibrary(boolean value);
} // Folder
