/**
 */
package com.specmate.model.testspecification.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.INamed;

import com.specmate.model.base.IPositionable;
import com.specmate.model.testspecification.ParameterAssignment;
import com.specmate.model.testspecification.ParameterType;
import com.specmate.model.testspecification.TestParameter;
import com.specmate.model.testspecification.TestspecificationPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Parameter</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.impl.TestParameterImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestParameterImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestParameterImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestParameterImpl#getPosition <em>Position</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestParameterImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestParameterImpl#getAssignments <em>Assignments</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TestParameterImpl extends CDOObjectImpl implements TestParameter {
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
	 * The default value of the '{@link #getPosition() <em>Position</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPosition()
	 * @generated
	 * @ordered
	 */
	protected static final int POSITION_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final ParameterType TYPE_EDEFAULT = ParameterType.INPUT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestParameterImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestspecificationPackage.Literals.TEST_PARAMETER;
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
		return (String)eDynamicGet(TestspecificationPackage.TEST_PARAMETER__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(TestspecificationPackage.TEST_PARAMETER__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(TestspecificationPackage.TEST_PARAMETER__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(TestspecificationPackage.TEST_PARAMETER__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(TestspecificationPackage.TEST_PARAMETER__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(TestspecificationPackage.TEST_PARAMETER__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getPosition() {
		return (Integer)eDynamicGet(TestspecificationPackage.TEST_PARAMETER__POSITION, BasePackage.Literals.IPOSITIONABLE__POSITION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPosition(int newPosition) {
		eDynamicSet(TestspecificationPackage.TEST_PARAMETER__POSITION, BasePackage.Literals.IPOSITIONABLE__POSITION, newPosition);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterType getType() {
		return (ParameterType)eDynamicGet(TestspecificationPackage.TEST_PARAMETER__TYPE, TestspecificationPackage.Literals.TEST_PARAMETER__TYPE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ParameterType newType) {
		eDynamicSet(TestspecificationPackage.TEST_PARAMETER__TYPE, TestspecificationPackage.Literals.TEST_PARAMETER__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ParameterAssignment> getAssignments() {
		return (EList<ParameterAssignment>)eDynamicGet(TestspecificationPackage.TEST_PARAMETER__ASSIGNMENTS, TestspecificationPackage.Literals.TEST_PARAMETER__ASSIGNMENTS, true, true);
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
			case TestspecificationPackage.TEST_PARAMETER__ASSIGNMENTS:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getAssignments()).basicAdd(otherEnd, msgs);
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
			case TestspecificationPackage.TEST_PARAMETER__ASSIGNMENTS:
				return ((InternalEList<?>)getAssignments()).basicRemove(otherEnd, msgs);
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
			case TestspecificationPackage.TEST_PARAMETER__ID:
				return getId();
			case TestspecificationPackage.TEST_PARAMETER__NAME:
				return getName();
			case TestspecificationPackage.TEST_PARAMETER__DESCRIPTION:
				return getDescription();
			case TestspecificationPackage.TEST_PARAMETER__POSITION:
				return getPosition();
			case TestspecificationPackage.TEST_PARAMETER__TYPE:
				return getType();
			case TestspecificationPackage.TEST_PARAMETER__ASSIGNMENTS:
				return getAssignments();
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
			case TestspecificationPackage.TEST_PARAMETER__ID:
				setId((String)newValue);
				return;
			case TestspecificationPackage.TEST_PARAMETER__NAME:
				setName((String)newValue);
				return;
			case TestspecificationPackage.TEST_PARAMETER__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TestspecificationPackage.TEST_PARAMETER__POSITION:
				setPosition((Integer)newValue);
				return;
			case TestspecificationPackage.TEST_PARAMETER__TYPE:
				setType((ParameterType)newValue);
				return;
			case TestspecificationPackage.TEST_PARAMETER__ASSIGNMENTS:
				getAssignments().clear();
				getAssignments().addAll((Collection<? extends ParameterAssignment>)newValue);
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
			case TestspecificationPackage.TEST_PARAMETER__ID:
				setId(ID_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_PARAMETER__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_PARAMETER__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_PARAMETER__POSITION:
				setPosition(POSITION_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_PARAMETER__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_PARAMETER__ASSIGNMENTS:
				getAssignments().clear();
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
			case TestspecificationPackage.TEST_PARAMETER__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case TestspecificationPackage.TEST_PARAMETER__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case TestspecificationPackage.TEST_PARAMETER__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case TestspecificationPackage.TEST_PARAMETER__POSITION:
				return getPosition() != POSITION_EDEFAULT;
			case TestspecificationPackage.TEST_PARAMETER__TYPE:
				return getType() != TYPE_EDEFAULT;
			case TestspecificationPackage.TEST_PARAMETER__ASSIGNMENTS:
				return !getAssignments().isEmpty();
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
				case TestspecificationPackage.TEST_PARAMETER__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case TestspecificationPackage.TEST_PARAMETER__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == IPositionable.class) {
			switch (derivedFeatureID) {
				case TestspecificationPackage.TEST_PARAMETER__POSITION: return BasePackage.IPOSITIONABLE__POSITION;
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
				case BasePackage.INAMED__NAME: return TestspecificationPackage.TEST_PARAMETER__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return TestspecificationPackage.TEST_PARAMETER__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == IPositionable.class) {
			switch (baseFeatureID) {
				case BasePackage.IPOSITIONABLE__POSITION: return TestspecificationPackage.TEST_PARAMETER__POSITION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //TestParameterImpl
