/**
 */
package com.specmate.model.administration;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Problem Detail</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.administration.ProblemDetail#getEcode <em>Ecode</em>}</li>
 *   <li>{@link com.specmate.model.administration.ProblemDetail#getStatus <em>Status</em>}</li>
 *   <li>{@link com.specmate.model.administration.ProblemDetail#getDetail <em>Detail</em>}</li>
 *   <li>{@link com.specmate.model.administration.ProblemDetail#getInstance <em>Instance</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.administration.AdministrationPackage#getProblemDetail()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProblemDetail extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Ecode</b></em>' attribute.
	 * The literals are from the enumeration {@link com.specmate.model.administration.ErrorCode}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ecode</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ecode</em>' attribute.
	 * @see com.specmate.model.administration.ErrorCode
	 * @see #setEcode(ErrorCode)
	 * @see com.specmate.model.administration.AdministrationPackage#getProblemDetail_Ecode()
	 * @model
	 * @generated
	 */
	ErrorCode getEcode();

	/**
	 * Sets the value of the '{@link com.specmate.model.administration.ProblemDetail#getEcode <em>Ecode</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ecode</em>' attribute.
	 * @see com.specmate.model.administration.ErrorCode
	 * @see #getEcode()
	 * @generated
	 */
	void setEcode(ErrorCode value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(int)
	 * @see com.specmate.model.administration.AdministrationPackage#getProblemDetail_Status()
	 * @model
	 * @generated
	 */
	int getStatus();

	/**
	 * Sets the value of the '{@link com.specmate.model.administration.ProblemDetail#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(int value);

	/**
	 * Returns the value of the '<em><b>Detail</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Detail</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Detail</em>' attribute.
	 * @see #setDetail(String)
	 * @see com.specmate.model.administration.AdministrationPackage#getProblemDetail_Detail()
	 * @model
	 * @generated
	 */
	String getDetail();

	/**
	 * Sets the value of the '{@link com.specmate.model.administration.ProblemDetail#getDetail <em>Detail</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Detail</em>' attribute.
	 * @see #getDetail()
	 * @generated
	 */
	void setDetail(String value);

	/**
	 * Returns the value of the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Instance</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Instance</em>' attribute.
	 * @see #setInstance(String)
	 * @see com.specmate.model.administration.AdministrationPackage#getProblemDetail_Instance()
	 * @model
	 * @generated
	 */
	String getInstance();

	/**
	 * Sets the value of the '{@link com.specmate.model.administration.ProblemDetail#getInstance <em>Instance</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Instance</em>' attribute.
	 * @see #getInstance()
	 * @generated
	 */
	void setInstance(String value);

} // ProblemDetail
