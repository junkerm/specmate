/**
 */
package com.specmate.model.testspecification.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.INamed;

import com.specmate.model.testspecification.TestSpecification;
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
 * An implementation of the model object '<em><b>Test Specification</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.impl.TestSpecificationImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestSpecificationImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestSpecificationImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestSpecificationImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestSpecificationImpl#isGenerated <em>Generated</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TestSpecificationImpl extends CDOObjectImpl implements TestSpecification {
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
	 * The default value of the '{@link #isGenerated() <em>Generated</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isGenerated()
	 * @generated
	 * @ordered
	 */
	protected static final boolean GENERATED_EDEFAULT; // TODO The default value literal "" is not valid.

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestSpecificationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestspecificationPackage.Literals.TEST_SPECIFICATION;
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
		return (String)eDynamicGet(TestspecificationPackage.TEST_SPECIFICATION__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(TestspecificationPackage.TEST_SPECIFICATION__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(TestspecificationPackage.TEST_SPECIFICATION__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(TestspecificationPackage.TEST_SPECIFICATION__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(TestspecificationPackage.TEST_SPECIFICATION__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(TestspecificationPackage.TEST_SPECIFICATION__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(TestspecificationPackage.TEST_SPECIFICATION__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isGenerated() {
		return (Boolean)eDynamicGet(TestspecificationPackage.TEST_SPECIFICATION__GENERATED, TestspecificationPackage.Literals.TEST_SPECIFICATION__GENERATED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGenerated(boolean newGenerated) {
		eDynamicSet(TestspecificationPackage.TEST_SPECIFICATION__GENERATED, TestspecificationPackage.Literals.TEST_SPECIFICATION__GENERATED, newGenerated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case TestspecificationPackage.TEST_SPECIFICATION__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
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
			case TestspecificationPackage.TEST_SPECIFICATION__ID:
				return getId();
			case TestspecificationPackage.TEST_SPECIFICATION__NAME:
				return getName();
			case TestspecificationPackage.TEST_SPECIFICATION__DESCRIPTION:
				return getDescription();
			case TestspecificationPackage.TEST_SPECIFICATION__CONTENTS:
				return getContents();
			case TestspecificationPackage.TEST_SPECIFICATION__GENERATED:
				return isGenerated();
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
			case TestspecificationPackage.TEST_SPECIFICATION__ID:
				setId((String)newValue);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION__NAME:
				setName((String)newValue);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION__GENERATED:
				setGenerated((Boolean)newValue);
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
			case TestspecificationPackage.TEST_SPECIFICATION__ID:
				setId(ID_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION__CONTENTS:
				getContents().clear();
				return;
			case TestspecificationPackage.TEST_SPECIFICATION__GENERATED:
				setGenerated(GENERATED_EDEFAULT);
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
			case TestspecificationPackage.TEST_SPECIFICATION__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case TestspecificationPackage.TEST_SPECIFICATION__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case TestspecificationPackage.TEST_SPECIFICATION__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case TestspecificationPackage.TEST_SPECIFICATION__CONTENTS:
				return !getContents().isEmpty();
			case TestspecificationPackage.TEST_SPECIFICATION__GENERATED:
				return isGenerated() != GENERATED_EDEFAULT;
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
				case TestspecificationPackage.TEST_SPECIFICATION__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case TestspecificationPackage.TEST_SPECIFICATION__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
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
				case BasePackage.INAMED__NAME: return TestspecificationPackage.TEST_SPECIFICATION__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return TestspecificationPackage.TEST_SPECIFICATION__DESCRIPTION;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //TestSpecificationImpl
