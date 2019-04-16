/**
 */
package com.specmate.model.testspecification.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.INamed;

import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestspecificationPackage;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Parameter Assignment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.impl.ParameterAssignmentImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.ParameterAssignmentImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.ParameterAssignmentImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.ParameterAssignmentImpl#getParameter <em>Parameter</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.ParameterAssignmentImpl#getValue <em>Value</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.ParameterAssignmentImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ParameterAssignmentImpl extends CDOObjectImpl implements ParameterAssignment {
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
	 * The default value of the '{@link #getValue() <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getValue()
	 * @generated
	 * @ordered
	 */
	protected static final String VALUE_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getCondition() <em>Condition</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCondition()
	 * @generated
	 * @ordered
	 */
	protected static final String CONDITION_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ParameterAssignmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestspecificationPackage.Literals.PARAMETER_ASSIGNMENT;
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
		return (String)eDynamicGet(TestspecificationPackage.PARAMETER_ASSIGNMENT__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(TestspecificationPackage.PARAMETER_ASSIGNMENT__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(TestspecificationPackage.PARAMETER_ASSIGNMENT__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(TestspecificationPackage.PARAMETER_ASSIGNMENT__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(TestspecificationPackage.PARAMETER_ASSIGNMENT__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(TestspecificationPackage.PARAMETER_ASSIGNMENT__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestParameter getParameter() {
		return (TestParameter)eDynamicGet(TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER, TestspecificationPackage.Literals.PARAMETER_ASSIGNMENT__PARAMETER, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestParameter basicGetParameter() {
		return (TestParameter)eDynamicGet(TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER, TestspecificationPackage.Literals.PARAMETER_ASSIGNMENT__PARAMETER, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParameter(TestParameter newParameter, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newParameter, TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameter(TestParameter newParameter) {
		eDynamicSet(TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER, TestspecificationPackage.Literals.PARAMETER_ASSIGNMENT__PARAMETER, newParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCondition() {
		return (String)eDynamicGet(TestspecificationPackage.PARAMETER_ASSIGNMENT__CONDITION, TestspecificationPackage.Literals.PARAMETER_ASSIGNMENT__CONDITION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(String newCondition) {
		eDynamicSet(TestspecificationPackage.PARAMETER_ASSIGNMENT__CONDITION, TestspecificationPackage.Literals.PARAMETER_ASSIGNMENT__CONDITION, newCondition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER:
				TestParameter parameter = basicGetParameter();
				if (parameter != null)
					msgs = ((InternalEObject)parameter).eInverseRemove(this, TestspecificationPackage.TEST_PARAMETER__ASSIGNMENTS, TestParameter.class, msgs);
				return basicSetParameter((TestParameter)otherEnd, msgs);
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
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER:
				return basicSetParameter(null, msgs);
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
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__ID:
				return getId();
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__NAME:
				return getName();
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__DESCRIPTION:
				return getDescription();
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER:
				if (resolve) return getParameter();
				return basicGetParameter();
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__CONDITION:
				return getCondition();
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
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__ID:
				setId((String)newValue);
				return;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__NAME:
				setName((String)newValue);
				return;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER:
				setParameter((TestParameter)newValue);
				return;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__CONDITION:
				setCondition((String)newValue);
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
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__ID:
				setId(ID_EDEFAULT);
				return;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER:
				setParameter((TestParameter)null);
				return;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__CONDITION:
				setCondition(CONDITION_EDEFAULT);
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
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__PARAMETER:
				return basicGetParameter() != null;
			case TestspecificationPackage.PARAMETER_ASSIGNMENT__CONDITION:
				return CONDITION_EDEFAULT == null ? getCondition() != null : !CONDITION_EDEFAULT.equals(getCondition());
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
				case TestspecificationPackage.PARAMETER_ASSIGNMENT__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case TestspecificationPackage.PARAMETER_ASSIGNMENT__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
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
				case BasePackage.INAMED__NAME: return TestspecificationPackage.PARAMETER_ASSIGNMENT__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return TestspecificationPackage.PARAMETER_ASSIGNMENT__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //ParameterAssignmentImpl
