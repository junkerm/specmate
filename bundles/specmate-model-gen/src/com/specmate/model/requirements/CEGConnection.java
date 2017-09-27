/**
 */
package com.specmate.model.requirements;

import com.specmate.model.base.IModelConnection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>CEG Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.CEGConnection#isNegate <em>Negate</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.requirements.RequirementsPackage#getCEGConnection()
 * @model
 * @generated
 */
public interface CEGConnection extends IModelConnection {
	/**
	 * Returns the value of the '<em><b>Negate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Negate</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Negate</em>' attribute.
	 * @see #setNegate(boolean)
	 * @see com.specmate.model.requirements.RequirementsPackage#getCEGConnection_Negate()
	 * @model annotation="http://specmate.com/form_meta shortDesc='Negate' longDesc='Negation of this connection' type='checkbox' position='1'"
	 * @generated
	 */
	boolean isNegate();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.CEGConnection#isNegate <em>Negate</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Negate</em>' attribute.
	 * @see #isNegate()
	 * @generated
	 */
	void setNegate(boolean value);

} // CEGConnection
