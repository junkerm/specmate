/**
 */
package com.specmate.model.base;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>ITracing Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.ITracingElement#getTracesTo <em>Traces To</em>}</li>
 *   <li>{@link com.specmate.model.base.ITracingElement#getTracesFrom <em>Traces From</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.base.BasePackage#getITracingElement()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ITracingElement extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Traces To</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.base.ITracingElement}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.base.ITracingElement#getTracesFrom <em>Traces From</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traces To</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traces To</em>' reference list.
	 * @see com.specmate.model.base.BasePackage#getITracingElement_TracesTo()
	 * @see com.specmate.model.base.ITracingElement#getTracesFrom
	 * @model opposite="tracesFrom"
	 * @generated
	 */
	EList<ITracingElement> getTracesTo();

	/**
	 * Returns the value of the '<em><b>Traces From</b></em>' reference list.
	 * The list contents are of type {@link com.specmate.model.base.ITracingElement}.
	 * It is bidirectional and its opposite is '{@link com.specmate.model.base.ITracingElement#getTracesTo <em>Traces To</em>}'.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Traces From</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Traces From</em>' reference list.
	 * @see com.specmate.model.base.BasePackage#getITracingElement_TracesFrom()
	 * @see com.specmate.model.base.ITracingElement#getTracesTo
	 * @model opposite="tracesTo"
	 * @generated
	 */
	EList<ITracingElement> getTracesFrom();

} // ITracingElement
