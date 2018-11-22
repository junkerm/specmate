/**
 */
package com.specmate.model.testspecification.impl;

import com.specmate.model.base.BasePackage;

import com.specmate.model.testspecification.TestSpecificationSkeleton;
import com.specmate.model.testspecification.TestspecificationPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Test Specification Skeleton</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.testspecification.impl.TestSpecificationSkeletonImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestSpecificationSkeletonImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link com.specmate.model.testspecification.impl.TestSpecificationSkeletonImpl#getCode <em>Code</em>}</li>
 * </ul>
 *
 * @generated
 */
public class TestSpecificationSkeletonImpl extends CDOObjectImpl implements TestSpecificationSkeleton {
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
	 * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLanguage()
	 * @generated
	 * @ordered
	 */
	protected static final String LANGUAGE_EDEFAULT = "";

	/**
	 * The default value of the '{@link #getCode() <em>Code</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCode()
	 * @generated
	 * @ordered
	 */
	protected static final String CODE_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected TestSpecificationSkeletonImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TestspecificationPackage.Literals.TEST_SPECIFICATION_SKELETON;
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
	public String getName() {
		return (String)eDynamicGet(TestspecificationPackage.TEST_SPECIFICATION_SKELETON__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(TestspecificationPackage.TEST_SPECIFICATION_SKELETON__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLanguage() {
		return (String)eDynamicGet(TestspecificationPackage.TEST_SPECIFICATION_SKELETON__LANGUAGE, TestspecificationPackage.Literals.TEST_SPECIFICATION_SKELETON__LANGUAGE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLanguage(String newLanguage) {
		eDynamicSet(TestspecificationPackage.TEST_SPECIFICATION_SKELETON__LANGUAGE, TestspecificationPackage.Literals.TEST_SPECIFICATION_SKELETON__LANGUAGE, newLanguage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCode() {
		return (String)eDynamicGet(TestspecificationPackage.TEST_SPECIFICATION_SKELETON__CODE, TestspecificationPackage.Literals.TEST_SPECIFICATION_SKELETON__CODE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCode(String newCode) {
		eDynamicSet(TestspecificationPackage.TEST_SPECIFICATION_SKELETON__CODE, TestspecificationPackage.Literals.TEST_SPECIFICATION_SKELETON__CODE, newCode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__NAME:
				return getName();
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__LANGUAGE:
				return getLanguage();
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__CODE:
				return getCode();
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
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__NAME:
				setName((String)newValue);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__LANGUAGE:
				setLanguage((String)newValue);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__CODE:
				setCode((String)newValue);
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
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__LANGUAGE:
				setLanguage(LANGUAGE_EDEFAULT);
				return;
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__CODE:
				setCode(CODE_EDEFAULT);
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
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__LANGUAGE:
				return LANGUAGE_EDEFAULT == null ? getLanguage() != null : !LANGUAGE_EDEFAULT.equals(getLanguage());
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON__CODE:
				return CODE_EDEFAULT == null ? getCode() != null : !CODE_EDEFAULT.equals(getCode());
		}
		return super.eIsSet(featureID);
	}

} //TestSpecificationSkeletonImpl
