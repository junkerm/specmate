/**
 */
package com.specmate.model.testspecification;

import com.specmate.model.base.INamed;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Test Specification Skeleton</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.TestProcedureSkeleton#getLanguage <em>Language</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.TestProcedureSkeleton#getCode <em>Code</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestSpecificationSkeleton()
 * @model
 * @generated
 */
public interface TestProcedureSkeleton extends INamed {
	/**
	 * Returns the value of the '<em><b>Language</b></em>' attribute.
	 * The default value is <code>""</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Language</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Language</em>' attribute.
	 * @see #setLanguage(String)
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestSpecificationSkeleton_Language()
	 * @model default=""
	 * @generated
	 */
	String getLanguage();

	/**
	 * Sets the value of the '{@link com.specmate.model.testspecification.TestProcedureSkeleton#getLanguage <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Language</em>' attribute.
	 * @see #getLanguage()
	 * @generated
	 */
	void setLanguage(String value);

	/**
	 * Returns the value of the '<em><b>Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Code</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Code</em>' attribute.
	 * @see #setCode(String)
	 * @see com.specmate.model.testspecification.TestspecificationPackage#getTestSpecificationSkeleton_Code()
	 * @model
	 * @generated
	 */
	String getCode();

	/**
	 * Sets the value of the '{@link com.specmate.model.testspecification.TestProcedureSkeleton#getCode <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Code</em>' attribute.
	 * @see #getCode()
	 * @generated
	 */
	void setCode(String value);

} // TestSpecificationSkeleton
