/**
 */
package com.specmate.model.requirements.impl;

import com.specmate.model.base.impl.ISpecmateModelObjectImpl;

import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getExtId <em>Ext Id</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getExtId2 <em>Ext Id2</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getNumberOfTests <em>Number Of Tests</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getTac <em>Tac</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getImplementingUnit <em>Implementing Unit</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getImplementingBOTeam <em>Implementing BO Team</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getImplementingITTeam <em>Implementing IT Team</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getPlannedRelease <em>Planned Release</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getStatus <em>Status</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequirementImpl extends ISpecmateModelObjectImpl implements Requirement {
	/**
	 * The default value of the '{@link #getExtId() <em>Ext Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtId()
	 * @generated
	 * @ordered
	 */
	protected static final String EXT_ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getExtId2() <em>Ext Id2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getExtId2()
	 * @generated
	 * @ordered
	 */
	protected static final String EXT_ID2_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getNumberOfTests() <em>Number Of Tests</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getNumberOfTests()
	 * @generated
	 * @ordered
	 */
	protected static final int NUMBER_OF_TESTS_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getTac() <em>Tac</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTac()
	 * @generated
	 * @ordered
	 */
	protected static final String TAC_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getImplementingUnit() <em>Implementing Unit</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementingUnit()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTING_UNIT_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getImplementingBOTeam() <em>Implementing BO Team</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementingBOTeam()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTING_BO_TEAM_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getImplementingITTeam() <em>Implementing IT Team</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getImplementingITTeam()
	 * @generated
	 * @ordered
	 */
	protected static final String IMPLEMENTING_IT_TEAM_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getPlannedRelease() <em>Planned Release</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPlannedRelease()
	 * @generated
	 * @ordered
	 */
	protected static final String PLANNED_RELEASE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getStatus() <em>Status</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getStatus()
	 * @generated
	 * @ordered
	 */
	protected static final String STATUS_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequirementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RequirementsPackage.Literals.REQUIREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtId() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__EXT_ID, RequirementsPackage.Literals.REQUIREMENT__EXT_ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtId(String newExtId) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__EXT_ID, RequirementsPackage.Literals.REQUIREMENT__EXT_ID, newExtId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtId2() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__EXT_ID2, RequirementsPackage.Literals.REQUIREMENT__EXT_ID2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtId2(String newExtId2) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__EXT_ID2, RequirementsPackage.Literals.REQUIREMENT__EXT_ID2, newExtId2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNumberOfTests() {
		return (Integer)eDynamicGet(RequirementsPackage.REQUIREMENT__NUMBER_OF_TESTS, RequirementsPackage.Literals.REQUIREMENT__NUMBER_OF_TESTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumberOfTests(int newNumberOfTests) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__NUMBER_OF_TESTS, RequirementsPackage.Literals.REQUIREMENT__NUMBER_OF_TESTS, newNumberOfTests);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTac() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__TAC, RequirementsPackage.Literals.REQUIREMENT__TAC, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTac(String newTac) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__TAC, RequirementsPackage.Literals.REQUIREMENT__TAC, newTac);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementingUnit() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__IMPLEMENTING_UNIT, RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_UNIT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementingUnit(String newImplementingUnit) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__IMPLEMENTING_UNIT, RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_UNIT, newImplementingUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementingBOTeam() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__IMPLEMENTING_BO_TEAM, RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_BO_TEAM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementingBOTeam(String newImplementingBOTeam) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__IMPLEMENTING_BO_TEAM, RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_BO_TEAM, newImplementingBOTeam);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getImplementingITTeam() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__IMPLEMENTING_IT_TEAM, RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_IT_TEAM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImplementingITTeam(String newImplementingITTeam) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__IMPLEMENTING_IT_TEAM, RequirementsPackage.Literals.REQUIREMENT__IMPLEMENTING_IT_TEAM, newImplementingITTeam);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPlannedRelease() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__PLANNED_RELEASE, RequirementsPackage.Literals.REQUIREMENT__PLANNED_RELEASE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPlannedRelease(String newPlannedRelease) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__PLANNED_RELEASE, RequirementsPackage.Literals.REQUIREMENT__PLANNED_RELEASE, newPlannedRelease);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getStatus() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__STATUS, RequirementsPackage.Literals.REQUIREMENT__STATUS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStatus(String newStatus) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__STATUS, RequirementsPackage.Literals.REQUIREMENT__STATUS, newStatus);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RequirementsPackage.REQUIREMENT__EXT_ID:
				return getExtId();
			case RequirementsPackage.REQUIREMENT__EXT_ID2:
				return getExtId2();
			case RequirementsPackage.REQUIREMENT__NUMBER_OF_TESTS:
				return getNumberOfTests();
			case RequirementsPackage.REQUIREMENT__TAC:
				return getTac();
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_UNIT:
				return getImplementingUnit();
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_BO_TEAM:
				return getImplementingBOTeam();
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_IT_TEAM:
				return getImplementingITTeam();
			case RequirementsPackage.REQUIREMENT__PLANNED_RELEASE:
				return getPlannedRelease();
			case RequirementsPackage.REQUIREMENT__STATUS:
				return getStatus();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RequirementsPackage.REQUIREMENT__EXT_ID:
				setExtId((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__EXT_ID2:
				setExtId2((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__NUMBER_OF_TESTS:
				setNumberOfTests((Integer)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__TAC:
				setTac((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_UNIT:
				setImplementingUnit((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_BO_TEAM:
				setImplementingBOTeam((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_IT_TEAM:
				setImplementingITTeam((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__PLANNED_RELEASE:
				setPlannedRelease((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__STATUS:
				setStatus((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case RequirementsPackage.REQUIREMENT__EXT_ID:
				setExtId(EXT_ID_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__EXT_ID2:
				setExtId2(EXT_ID2_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__NUMBER_OF_TESTS:
				setNumberOfTests(NUMBER_OF_TESTS_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__TAC:
				setTac(TAC_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_UNIT:
				setImplementingUnit(IMPLEMENTING_UNIT_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_BO_TEAM:
				setImplementingBOTeam(IMPLEMENTING_BO_TEAM_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_IT_TEAM:
				setImplementingITTeam(IMPLEMENTING_IT_TEAM_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__PLANNED_RELEASE:
				setPlannedRelease(PLANNED_RELEASE_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__STATUS:
				setStatus(STATUS_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case RequirementsPackage.REQUIREMENT__EXT_ID:
				return EXT_ID_EDEFAULT == null ? getExtId() != null : !EXT_ID_EDEFAULT.equals(getExtId());
			case RequirementsPackage.REQUIREMENT__EXT_ID2:
				return EXT_ID2_EDEFAULT == null ? getExtId2() != null : !EXT_ID2_EDEFAULT.equals(getExtId2());
			case RequirementsPackage.REQUIREMENT__NUMBER_OF_TESTS:
				return getNumberOfTests() != NUMBER_OF_TESTS_EDEFAULT;
			case RequirementsPackage.REQUIREMENT__TAC:
				return TAC_EDEFAULT == null ? getTac() != null : !TAC_EDEFAULT.equals(getTac());
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_UNIT:
				return IMPLEMENTING_UNIT_EDEFAULT == null ? getImplementingUnit() != null : !IMPLEMENTING_UNIT_EDEFAULT.equals(getImplementingUnit());
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_BO_TEAM:
				return IMPLEMENTING_BO_TEAM_EDEFAULT == null ? getImplementingBOTeam() != null : !IMPLEMENTING_BO_TEAM_EDEFAULT.equals(getImplementingBOTeam());
			case RequirementsPackage.REQUIREMENT__IMPLEMENTING_IT_TEAM:
				return IMPLEMENTING_IT_TEAM_EDEFAULT == null ? getImplementingITTeam() != null : !IMPLEMENTING_IT_TEAM_EDEFAULT.equals(getImplementingITTeam());
			case RequirementsPackage.REQUIREMENT__PLANNED_RELEASE:
				return PLANNED_RELEASE_EDEFAULT == null ? getPlannedRelease() != null : !PLANNED_RELEASE_EDEFAULT.equals(getPlannedRelease());
			case RequirementsPackage.REQUIREMENT__STATUS:
				return STATUS_EDEFAULT == null ? getStatus() != null : !STATUS_EDEFAULT.equals(getStatus());
		}
		return super.eIsSet(featureID);
	}

} //RequirementImpl
