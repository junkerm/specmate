/**
 */
package com.specmate.model.requirements;

import com.specmate.model.base.ISpecmateModelObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CEG Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.CEGModel#getModelRequirements <em>Model Requirements</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.requirements.RequirementsPackage#getCEGModel()
 * @model
 * @generated
 */
public interface CEGModel extends ISpecmateModelObject {

	/**
	 * Returns the value of the '<em><b>Model Requirements</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Model Requirements</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Model Requirements</em>' attribute.
	 * @see #setModelRequirements(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGModel_ModelRequirements()
	 * @model annotation="http://specmate.com/form_meta shortDesc='Model Requirements' longDesc='' required='false' type='longText' rows='5' position='110'"
	 * @generated
	 */
	String getModelRequirements();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.CEGModel#getModelRequirements <em>Model Requirements</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Model Requirements</em>' attribute.
	 * @see #getModelRequirements()
	 * @generated
	 */
	void setModelRequirements(String value);
} // CEGModel
