/**
 */
package com.specmate.model.testspecification.impl;

import com.specmate.model.testspecification.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TestspecificationFactoryImpl extends EFactoryImpl implements TestspecificationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TestspecificationFactory init() {
		try {
			TestspecificationFactory theTestspecificationFactory = (TestspecificationFactory)EPackage.Registry.INSTANCE.getEFactory(TestspecificationPackage.eNS_URI);
			if (theTestspecificationFactory != null) {
				return theTestspecificationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new TestspecificationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestspecificationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case TestspecificationPackage.TEST_SPECIFICATION: return (EObject)createTestSpecification();
			case TestspecificationPackage.TEST_SPECIFICATION_SKELETON: return (EObject)createTestSpecificationSkeleton();
			case TestspecificationPackage.TEST_PARAMETER: return (EObject)createTestParameter();
			case TestspecificationPackage.TEST_CASE: return (EObject)createTestCase();
			case TestspecificationPackage.PARAMETER_ASSIGNMENT: return (EObject)createParameterAssignment();
			case TestspecificationPackage.TEST_PROCEDURE: return (EObject)createTestProcedure();
			case TestspecificationPackage.TEST_STEP: return (EObject)createTestStep();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case TestspecificationPackage.PARAMETER_TYPE:
				return createParameterTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case TestspecificationPackage.PARAMETER_TYPE:
				return convertParameterTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestSpecification createTestSpecification() {
		TestSpecificationImpl testSpecification = new TestSpecificationImpl();
		return testSpecification;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestSpecificationSkeleton createTestSpecificationSkeleton() {
		TestSpecificationSkeletonImpl testSpecificationSkeleton = new TestSpecificationSkeletonImpl();
		return testSpecificationSkeleton;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestParameter createTestParameter() {
		TestParameterImpl testParameter = new TestParameterImpl();
		return testParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestCase createTestCase() {
		TestCaseImpl testCase = new TestCaseImpl();
		return testCase;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterAssignment createParameterAssignment() {
		ParameterAssignmentImpl parameterAssignment = new ParameterAssignmentImpl();
		return parameterAssignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestProcedure createTestProcedure() {
		TestProcedureImpl testProcedure = new TestProcedureImpl();
		return testProcedure;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestStep createTestStep() {
		TestStepImpl testStep = new TestStepImpl();
		return testStep;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterType createParameterTypeFromString(EDataType eDataType, String initialValue) {
		ParameterType result = ParameterType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertParameterTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestspecificationPackage getTestspecificationPackage() {
		return (TestspecificationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static TestspecificationPackage getPackage() {
		return TestspecificationPackage.eINSTANCE;
	}

} //TestspecificationFactoryImpl
