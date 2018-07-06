/**
 */
package com.specmate.model.batch;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.batch.BatchOperation#getOperations <em>Operations</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.batch.BatchPackage#getBatchOperation()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface BatchOperation extends CDOObject {

	/**
	 * Returns the value of the '<em><b>Operations</b></em>' containment reference list.
	 * The list contents are of type {@link com.specmate.model.batch.Operation}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operations</em>' containment reference list.
	 * @see com.specmate.model.batch.BatchPackage#getBatchOperation_Operations()
	 * @model containment="true"
	 * @generated
	 */
	EList<Operation> getOperations();
} // BatchOperation
