/**
 */
package com.specmate.model.testspecification;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see com.specmate.model.testspecification.TestspecificationPackage
 * @generated
 */
public interface TestspecificationFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestspecificationFactory eINSTANCE = com.specmate.model.testspecification.impl.TestspecificationFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Test Specification</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Specification</em>'.
	 * @generated
	 */
	TestSpecification createTestSpecification();

	/**
	 * Returns a new object of class '<em>Test Specification Skeleton</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Specification Skeleton</em>'.
	 * @generated
	 */
	TestSpecificationSkeleton createTestSpecificationSkeleton();
	
	/**
	 * Returns a new object of class '<em>Test Procedure Skeleton</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Procedure Skeleton</em>'.
	 * @generated
	 */
	TestProcedureSkeleton createTestProcedureSkeleton();

	/**
	 * Returns a new object of class '<em>Test Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Parameter</em>'.
	 * @generated
	 */
	TestParameter createTestParameter();

	/**
	 * Returns a new object of class '<em>Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Case</em>'.
	 * @generated
	 */
	TestCase createTestCase();

	/**
	 * Returns a new object of class '<em>Parameter Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Parameter Assignment</em>'.
	 * @generated
	 */
	ParameterAssignment createParameterAssignment();

	/**
	 * Returns a new object of class '<em>Test Procedure</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Procedure</em>'.
	 * @generated
	 */
	TestProcedure createTestProcedure();

	/**
	 * Returns a new object of class '<em>Test Step</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Test Step</em>'.
	 * @generated
	 */
	TestStep createTestStep();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	TestspecificationPackage getTestspecificationPackage();

} //TestspecificationFactory
