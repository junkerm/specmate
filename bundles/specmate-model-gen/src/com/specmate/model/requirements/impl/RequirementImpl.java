/**
 */
package com.specmate.model.requirements.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.IExternal;
import com.specmate.model.base.INamed;

import com.specmate.model.base.ITracingElement;
import com.specmate.model.requirements.Requirement;
import com.specmate.model.requirements.RequirementsPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getTracesTo <em>Traces To</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getTracesFrom <em>Traces From</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getExtId <em>Ext Id</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getExtId2 <em>Ext Id2</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getSource <em>Source</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#isLive <em>Live</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getNumberOfTests <em>Number Of Tests</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getTac <em>Tac</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getImplementingUnit <em>Implementing Unit</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getImplementingBOTeam <em>Implementing BO Team</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getImplementingITTeam <em>Implementing IT Team</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getPlannedRelease <em>Planned Release</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#getStatus <em>Status</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.RequirementImpl#isIsRegressionRequirement <em>Is Regression Requirement</em>}</li>
 * </ul>
 *
 * @generated
 */
public class RequirementImpl extends CDOObjectImpl implements Requirement {
	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

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
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isLive() <em>Live</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isLive()
	 * @generated
	 * @ordered
	 */
	protected static final boolean LIVE_EDEFAULT = false;

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
	 * The default value of the '{@link #isIsRegressionRequirement() <em>Is Regression Requirement</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isIsRegressionRequirement()
	 * @generated
	 * @ordered
	 */
	protected static final boolean IS_REGRESSION_REQUIREMENT_EDEFAULT = false;

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
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(RequirementsPackage.REQUIREMENT__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ITracingElement> getTracesTo() {
		return (EList<ITracingElement>)eDynamicGet(RequirementsPackage.REQUIREMENT__TRACES_TO, BasePackage.Literals.ITRACING_ELEMENT__TRACES_TO, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ITracingElement> getTracesFrom() {
		return (EList<ITracingElement>)eDynamicGet(RequirementsPackage.REQUIREMENT__TRACES_FROM, BasePackage.Literals.ITRACING_ELEMENT__TRACES_FROM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtId() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__EXT_ID, BasePackage.Literals.IEXTERNAL__EXT_ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtId(String newExtId) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__EXT_ID, BasePackage.Literals.IEXTERNAL__EXT_ID, newExtId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getExtId2() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__EXT_ID2, BasePackage.Literals.IEXTERNAL__EXT_ID2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExtId2(String newExtId2) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__EXT_ID2, BasePackage.Literals.IEXTERNAL__EXT_ID2, newExtId2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSource() {
		return (String)eDynamicGet(RequirementsPackage.REQUIREMENT__SOURCE, BasePackage.Literals.IEXTERNAL__SOURCE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSource(String newSource) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__SOURCE, BasePackage.Literals.IEXTERNAL__SOURCE, newSource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isLive() {
		return (Boolean)eDynamicGet(RequirementsPackage.REQUIREMENT__LIVE, BasePackage.Literals.IEXTERNAL__LIVE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLive(boolean newLive) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__LIVE, BasePackage.Literals.IEXTERNAL__LIVE, newLive);
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
	public boolean isIsRegressionRequirement() {
		return (Boolean)eDynamicGet(RequirementsPackage.REQUIREMENT__IS_REGRESSION_REQUIREMENT, RequirementsPackage.Literals.REQUIREMENT__IS_REGRESSION_REQUIREMENT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsRegressionRequirement(boolean newIsRegressionRequirement) {
		eDynamicSet(RequirementsPackage.REQUIREMENT__IS_REGRESSION_REQUIREMENT, RequirementsPackage.Literals.REQUIREMENT__IS_REGRESSION_REQUIREMENT, newIsRegressionRequirement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RequirementsPackage.REQUIREMENT__TRACES_TO:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTracesTo()).basicAdd(otherEnd, msgs);
			case RequirementsPackage.REQUIREMENT__TRACES_FROM:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTracesFrom()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case RequirementsPackage.REQUIREMENT__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case RequirementsPackage.REQUIREMENT__TRACES_TO:
				return ((InternalEList<?>)getTracesTo()).basicRemove(otherEnd, msgs);
			case RequirementsPackage.REQUIREMENT__TRACES_FROM:
				return ((InternalEList<?>)getTracesFrom()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case RequirementsPackage.REQUIREMENT__ID:
				return getId();
			case RequirementsPackage.REQUIREMENT__NAME:
				return getName();
			case RequirementsPackage.REQUIREMENT__DESCRIPTION:
				return getDescription();
			case RequirementsPackage.REQUIREMENT__CONTENTS:
				return getContents();
			case RequirementsPackage.REQUIREMENT__TRACES_TO:
				return getTracesTo();
			case RequirementsPackage.REQUIREMENT__TRACES_FROM:
				return getTracesFrom();
			case RequirementsPackage.REQUIREMENT__EXT_ID:
				return getExtId();
			case RequirementsPackage.REQUIREMENT__EXT_ID2:
				return getExtId2();
			case RequirementsPackage.REQUIREMENT__SOURCE:
				return getSource();
			case RequirementsPackage.REQUIREMENT__LIVE:
				return isLive();
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
			case RequirementsPackage.REQUIREMENT__IS_REGRESSION_REQUIREMENT:
				return isIsRegressionRequirement();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case RequirementsPackage.REQUIREMENT__ID:
				setId((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__NAME:
				setName((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__TRACES_TO:
				getTracesTo().clear();
				getTracesTo().addAll((Collection<? extends ITracingElement>)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__TRACES_FROM:
				getTracesFrom().clear();
				getTracesFrom().addAll((Collection<? extends ITracingElement>)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__EXT_ID:
				setExtId((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__EXT_ID2:
				setExtId2((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__SOURCE:
				setSource((String)newValue);
				return;
			case RequirementsPackage.REQUIREMENT__LIVE:
				setLive((Boolean)newValue);
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
			case RequirementsPackage.REQUIREMENT__IS_REGRESSION_REQUIREMENT:
				setIsRegressionRequirement((Boolean)newValue);
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
			case RequirementsPackage.REQUIREMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__CONTENTS:
				getContents().clear();
				return;
			case RequirementsPackage.REQUIREMENT__TRACES_TO:
				getTracesTo().clear();
				return;
			case RequirementsPackage.REQUIREMENT__TRACES_FROM:
				getTracesFrom().clear();
				return;
			case RequirementsPackage.REQUIREMENT__EXT_ID:
				setExtId(EXT_ID_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__EXT_ID2:
				setExtId2(EXT_ID2_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__SOURCE:
				setSource(SOURCE_EDEFAULT);
				return;
			case RequirementsPackage.REQUIREMENT__LIVE:
				setLive(LIVE_EDEFAULT);
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
			case RequirementsPackage.REQUIREMENT__IS_REGRESSION_REQUIREMENT:
				setIsRegressionRequirement(IS_REGRESSION_REQUIREMENT_EDEFAULT);
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
			case RequirementsPackage.REQUIREMENT__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case RequirementsPackage.REQUIREMENT__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case RequirementsPackage.REQUIREMENT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case RequirementsPackage.REQUIREMENT__CONTENTS:
				return !getContents().isEmpty();
			case RequirementsPackage.REQUIREMENT__TRACES_TO:
				return !getTracesTo().isEmpty();
			case RequirementsPackage.REQUIREMENT__TRACES_FROM:
				return !getTracesFrom().isEmpty();
			case RequirementsPackage.REQUIREMENT__EXT_ID:
				return EXT_ID_EDEFAULT == null ? getExtId() != null : !EXT_ID_EDEFAULT.equals(getExtId());
			case RequirementsPackage.REQUIREMENT__EXT_ID2:
				return EXT_ID2_EDEFAULT == null ? getExtId2() != null : !EXT_ID2_EDEFAULT.equals(getExtId2());
			case RequirementsPackage.REQUIREMENT__SOURCE:
				return SOURCE_EDEFAULT == null ? getSource() != null : !SOURCE_EDEFAULT.equals(getSource());
			case RequirementsPackage.REQUIREMENT__LIVE:
				return isLive() != LIVE_EDEFAULT;
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
			case RequirementsPackage.REQUIREMENT__IS_REGRESSION_REQUIREMENT:
				return isIsRegressionRequirement() != IS_REGRESSION_REQUIREMENT_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == INamed.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.REQUIREMENT__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.REQUIREMENT__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == ITracingElement.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.REQUIREMENT__TRACES_TO: return BasePackage.ITRACING_ELEMENT__TRACES_TO;
				case RequirementsPackage.REQUIREMENT__TRACES_FROM: return BasePackage.ITRACING_ELEMENT__TRACES_FROM;
				default: return -1;
			}
		}
		if (baseClass == IExternal.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.REQUIREMENT__EXT_ID: return BasePackage.IEXTERNAL__EXT_ID;
				case RequirementsPackage.REQUIREMENT__EXT_ID2: return BasePackage.IEXTERNAL__EXT_ID2;
				case RequirementsPackage.REQUIREMENT__SOURCE: return BasePackage.IEXTERNAL__SOURCE;
				case RequirementsPackage.REQUIREMENT__LIVE: return BasePackage.IEXTERNAL__LIVE;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == INamed.class) {
			switch (baseFeatureID) {
				case BasePackage.INAMED__NAME: return RequirementsPackage.REQUIREMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return RequirementsPackage.REQUIREMENT__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == ITracingElement.class) {
			switch (baseFeatureID) {
				case BasePackage.ITRACING_ELEMENT__TRACES_TO: return RequirementsPackage.REQUIREMENT__TRACES_TO;
				case BasePackage.ITRACING_ELEMENT__TRACES_FROM: return RequirementsPackage.REQUIREMENT__TRACES_FROM;
				default: return -1;
			}
		}
		if (baseClass == IExternal.class) {
			switch (baseFeatureID) {
				case BasePackage.IEXTERNAL__EXT_ID: return RequirementsPackage.REQUIREMENT__EXT_ID;
				case BasePackage.IEXTERNAL__EXT_ID2: return RequirementsPackage.REQUIREMENT__EXT_ID2;
				case BasePackage.IEXTERNAL__SOURCE: return RequirementsPackage.REQUIREMENT__SOURCE;
				case BasePackage.IEXTERNAL__LIVE: return RequirementsPackage.REQUIREMENT__LIVE;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //RequirementImpl
