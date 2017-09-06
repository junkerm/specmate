/**
 */
package com.specmate.model.requirements;

import com.specmate.model.base.IExternal;
import com.specmate.model.base.ISpecmateModelObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.Requirement#getNumberOfTests <em>Number Of Tests</em>}</li>
 *   <li>{@link com.specmate.model.requirements.Requirement#getTac <em>Tac</em>}</li>
 *   <li>{@link com.specmate.model.requirements.Requirement#getImplementingUnit <em>Implementing Unit</em>}</li>
 *   <li>{@link com.specmate.model.requirements.Requirement#getImplementingBOTeam <em>Implementing BO Team</em>}</li>
 *   <li>{@link com.specmate.model.requirements.Requirement#getImplementingITTeam <em>Implementing IT Team</em>}</li>
 *   <li>{@link com.specmate.model.requirements.Requirement#getPlannedRelease <em>Planned Release</em>}</li>
 *   <li>{@link com.specmate.model.requirements.Requirement#getStatus <em>Status</em>}</li>
 * </ul>
 *
 * @see com.specmate.model.requirements.RequirementsPackage#getRequirement()
 * @model
 * @generated
 */
public interface Requirement extends ISpecmateModelObject, IExternal {
	/**
	 * Returns the value of the '<em><b>Number Of Tests</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Number Of Tests</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Number Of Tests</em>' attribute.
	 * @see #setNumberOfTests(int)
	 * @see com.specmate.model.requirements.RequirementsPackage#getRequirement_NumberOfTests()
	 * @model
	 * @generated
	 */
	int getNumberOfTests();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.Requirement#getNumberOfTests <em>Number Of Tests</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Number Of Tests</em>' attribute.
	 * @see #getNumberOfTests()
	 * @generated
	 */
	void setNumberOfTests(int value);

	/**
	 * Returns the value of the '<em><b>Tac</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Tac</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Tac</em>' attribute.
	 * @see #setTac(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getRequirement_Tac()
	 * @model
	 * @generated
	 */
	String getTac();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.Requirement#getTac <em>Tac</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Tac</em>' attribute.
	 * @see #getTac()
	 * @generated
	 */
	void setTac(String value);

	/**
	 * Returns the value of the '<em><b>Implementing Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementing Unit</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementing Unit</em>' attribute.
	 * @see #setImplementingUnit(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getRequirement_ImplementingUnit()
	 * @model
	 * @generated
	 */
	String getImplementingUnit();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.Requirement#getImplementingUnit <em>Implementing Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementing Unit</em>' attribute.
	 * @see #getImplementingUnit()
	 * @generated
	 */
	void setImplementingUnit(String value);

	/**
	 * Returns the value of the '<em><b>Implementing BO Team</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementing BO Team</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementing BO Team</em>' attribute.
	 * @see #setImplementingBOTeam(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getRequirement_ImplementingBOTeam()
	 * @model
	 * @generated
	 */
	String getImplementingBOTeam();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.Requirement#getImplementingBOTeam <em>Implementing BO Team</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementing BO Team</em>' attribute.
	 * @see #getImplementingBOTeam()
	 * @generated
	 */
	void setImplementingBOTeam(String value);

	/**
	 * Returns the value of the '<em><b>Implementing IT Team</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Implementing IT Team</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Implementing IT Team</em>' attribute.
	 * @see #setImplementingITTeam(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getRequirement_ImplementingITTeam()
	 * @model
	 * @generated
	 */
	String getImplementingITTeam();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.Requirement#getImplementingITTeam <em>Implementing IT Team</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Implementing IT Team</em>' attribute.
	 * @see #getImplementingITTeam()
	 * @generated
	 */
	void setImplementingITTeam(String value);

	/**
	 * Returns the value of the '<em><b>Planned Release</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Planned Release</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Planned Release</em>' attribute.
	 * @see #setPlannedRelease(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getRequirement_PlannedRelease()
	 * @model
	 * @generated
	 */
	String getPlannedRelease();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.Requirement#getPlannedRelease <em>Planned Release</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Planned Release</em>' attribute.
	 * @see #getPlannedRelease()
	 * @generated
	 */
	void setPlannedRelease(String value);

	/**
	 * Returns the value of the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Status</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Status</em>' attribute.
	 * @see #setStatus(String)
	 * @see com.specmate.model.requirements.RequirementsPackage#getRequirement_Status()
	 * @model
	 * @generated
	 */
	String getStatus();

	/**
	 * Sets the value of the '{@link com.specmate.model.requirements.Requirement#getStatus <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Status</em>' attribute.
	 * @see #getStatus()
	 * @generated
	 */
	void setStatus(String value);

} // Requirement
