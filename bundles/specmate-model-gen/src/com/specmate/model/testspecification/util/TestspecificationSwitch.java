/**
 */
package com.specmate.model.testspecification.util;

import com.specmate.model.base.IContainer;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.IExternal;
import com.specmate.model.base.IID;
import com.specmate.model.base.INamed;
import com.specmate.model.base.IPositionable;

import com.specmate.model.testspecification.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see com.specmate.model.testspecification.TestspecificationPackage
 * @generated
 */
public class TestspecificationSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static TestspecificationPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TestspecificationSwitch() {
		if (modelPackage == null) {
			modelPackage = TestspecificationPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case TestspecificationPackage.TEST_SPECIFICATION: {
				TestSpecification testSpecification = (TestSpecification)theEObject;
				T result = caseTestSpecification(testSpecification);
				if (result == null) result = caseIContainer(testSpecification);
				if (result == null) result = caseIContentElement(testSpecification);
				if (result == null) result = caseIID(testSpecification);
				if (result == null) result = caseINamed(testSpecification);
				if (result == null) result = caseIDescribed(testSpecification);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestspecificationPackage.TEST_PARAMETER: {
				TestParameter testParameter = (TestParameter)theEObject;
				T result = caseTestParameter(testParameter);
				if (result == null) result = caseIContentElement(testParameter);
				if (result == null) result = caseIPositionable(testParameter);
				if (result == null) result = caseIID(testParameter);
				if (result == null) result = caseINamed(testParameter);
				if (result == null) result = caseIDescribed(testParameter);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestspecificationPackage.TEST_CASE: {
				TestCase testCase = (TestCase)theEObject;
				T result = caseTestCase(testCase);
				if (result == null) result = caseIContainer(testCase);
				if (result == null) result = caseIPositionable(testCase);
				if (result == null) result = caseIContentElement(testCase);
				if (result == null) result = caseIID(testCase);
				if (result == null) result = caseINamed(testCase);
				if (result == null) result = caseIDescribed(testCase);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestspecificationPackage.PARAMETER_ASSIGNMENT: {
				ParameterAssignment parameterAssignment = (ParameterAssignment)theEObject;
				T result = caseParameterAssignment(parameterAssignment);
				if (result == null) result = caseIContentElement(parameterAssignment);
				if (result == null) result = caseIID(parameterAssignment);
				if (result == null) result = caseINamed(parameterAssignment);
				if (result == null) result = caseIDescribed(parameterAssignment);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestspecificationPackage.TEST_PROCEDURE: {
				TestProcedure testProcedure = (TestProcedure)theEObject;
				T result = caseTestProcedure(testProcedure);
				if (result == null) result = caseIContainer(testProcedure);
				if (result == null) result = caseIExternal(testProcedure);
				if (result == null) result = caseIContentElement(testProcedure);
				if (result == null) result = caseIID(testProcedure);
				if (result == null) result = caseINamed(testProcedure);
				if (result == null) result = caseIDescribed(testProcedure);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case TestspecificationPackage.TEST_STEP: {
				TestStep testStep = (TestStep)theEObject;
				T result = caseTestStep(testStep);
				if (result == null) result = caseIContentElement(testStep);
				if (result == null) result = caseIPositionable(testStep);
				if (result == null) result = caseIID(testStep);
				if (result == null) result = caseINamed(testStep);
				if (result == null) result = caseIDescribed(testStep);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Specification</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Specification</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestSpecification(TestSpecification object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Parameter</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Parameter</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestParameter(TestParameter object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Case</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Case</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestCase(TestCase object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Parameter Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Parameter Assignment</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseParameterAssignment(ParameterAssignment object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Procedure</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Procedure</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestProcedure(TestProcedure object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Test Step</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Test Step</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTestStep(TestStep object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IID</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IID</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIID(IID object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>INamed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>INamed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseINamed(INamed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IDescribed</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IDescribed</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIDescribed(IDescribed object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IContent Element</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IContent Element</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIContentElement(IContentElement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IContainer</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IContainer</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIContainer(IContainer object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IPositionable</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IPositionable</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIPositionable(IPositionable object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>IExternal</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>IExternal</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseIExternal(IExternal object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //TestspecificationSwitch
