/**
 */
package com.specmate.model.testspecification;

import com.specmate.model.base.BasePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see com.specmate.model.testspecification.TestspecificationFactory
 * @model kind="package"
 * @generated
 */
public interface TestspecificationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "testspecification";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20180126/model/testspecification";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.model.testspecification";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TestspecificationPackage eINSTANCE = com.specmate.model.testspecification.impl.TestspecificationPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.model.testspecification.impl.TestSpecificationImpl <em>Test Specification</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.testspecification.impl.TestSpecificationImpl
	 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestSpecification()
	 * @generated
	 */
	int TEST_SPECIFICATION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SPECIFICATION__ID = BasePackage.ICONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SPECIFICATION__NAME = BasePackage.ICONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SPECIFICATION__DESCRIPTION = BasePackage.ICONTAINER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SPECIFICATION__CONTENTS = BasePackage.ICONTAINER__CONTENTS;

	/**
	 * The number of structural features of the '<em>Test Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SPECIFICATION_FEATURE_COUNT = BasePackage.ICONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Test Specification</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_SPECIFICATION_OPERATION_COUNT = BasePackage.ICONTAINER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.testspecification.impl.TestParameterImpl <em>Test Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.testspecification.impl.TestParameterImpl
	 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestParameter()
	 * @generated
	 */
	int TEST_PARAMETER = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PARAMETER__ID = BasePackage.ICONTENT_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PARAMETER__NAME = BasePackage.ICONTENT_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PARAMETER__DESCRIPTION = BasePackage.ICONTENT_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PARAMETER__POSITION = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PARAMETER__TYPE = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Assignments</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PARAMETER__ASSIGNMENTS = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Test Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PARAMETER_FEATURE_COUNT = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Test Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PARAMETER_OPERATION_COUNT = BasePackage.ICONTENT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.testspecification.impl.TestCaseImpl <em>Test Case</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.testspecification.impl.TestCaseImpl
	 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestCase()
	 * @generated
	 */
	int TEST_CASE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE__ID = BasePackage.ICONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE__NAME = BasePackage.ICONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE__DESCRIPTION = BasePackage.ICONTAINER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE__CONTENTS = BasePackage.ICONTAINER__CONTENTS;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE__POSITION = BasePackage.ICONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Test Case</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE_FEATURE_COUNT = BasePackage.ICONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Test Case</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_CASE_OPERATION_COUNT = BasePackage.ICONTAINER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.testspecification.impl.ParameterAssignmentImpl <em>Parameter Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.testspecification.impl.ParameterAssignmentImpl
	 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getParameterAssignment()
	 * @generated
	 */
	int PARAMETER_ASSIGNMENT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_ASSIGNMENT__ID = BasePackage.ICONTENT_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_ASSIGNMENT__NAME = BasePackage.ICONTENT_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_ASSIGNMENT__DESCRIPTION = BasePackage.ICONTENT_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Parameter</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_ASSIGNMENT__PARAMETER = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_ASSIGNMENT__VALUE = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_ASSIGNMENT__CONDITION = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Parameter Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_ASSIGNMENT_FEATURE_COUNT = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Parameter Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_ASSIGNMENT_OPERATION_COUNT = BasePackage.ICONTENT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.testspecification.impl.TestProcedureImpl <em>Test Procedure</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.testspecification.impl.TestProcedureImpl
	 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestProcedure()
	 * @generated
	 */
	int TEST_PROCEDURE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__ID = BasePackage.ICONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__NAME = BasePackage.ICONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__DESCRIPTION = BasePackage.ICONTAINER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__CONTENTS = BasePackage.ICONTAINER__CONTENTS;

	/**
	 * The feature id for the '<em><b>Ext Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__EXT_ID = BasePackage.ICONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ext Id2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__EXT_ID2 = BasePackage.ICONTAINER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__SOURCE = BasePackage.ICONTAINER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Live</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__LIVE = BasePackage.ICONTAINER_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Is Regression Test</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE__IS_REGRESSION_TEST = BasePackage.ICONTAINER_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Test Procedure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE_FEATURE_COUNT = BasePackage.ICONTAINER_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Test Procedure</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_PROCEDURE_OPERATION_COUNT = BasePackage.ICONTAINER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.testspecification.impl.TestStepImpl <em>Test Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.testspecification.impl.TestStepImpl
	 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestStep()
	 * @generated
	 */
	int TEST_STEP = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STEP__ID = BasePackage.ICONTENT_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STEP__NAME = BasePackage.ICONTENT_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STEP__DESCRIPTION = BasePackage.ICONTENT_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STEP__POSITION = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Expected Outcome</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STEP__EXPECTED_OUTCOME = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Referenced Test Parameters</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STEP__REFERENCED_TEST_PARAMETERS = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Test Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STEP_FEATURE_COUNT = BasePackage.ICONTENT_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Test Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TEST_STEP_OPERATION_COUNT = BasePackage.ICONTENT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.testspecification.ParameterType <em>Parameter Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.testspecification.ParameterType
	 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getParameterType()
	 * @generated
	 */
	int PARAMETER_TYPE = 6;


	/**
	 * Returns the meta object for class '{@link com.specmate.model.testspecification.TestSpecification <em>Test Specification</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Specification</em>'.
	 * @see com.specmate.model.testspecification.TestSpecification
	 * @generated
	 */
	EClass getTestSpecification();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.testspecification.TestParameter <em>Test Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Parameter</em>'.
	 * @see com.specmate.model.testspecification.TestParameter
	 * @generated
	 */
	EClass getTestParameter();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.testspecification.TestParameter#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.specmate.model.testspecification.TestParameter#getType()
	 * @see #getTestParameter()
	 * @generated
	 */
	EAttribute getTestParameter_Type();

	/**
	 * Returns the meta object for the reference list '{@link com.specmate.model.testspecification.TestParameter#getAssignments <em>Assignments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Assignments</em>'.
	 * @see com.specmate.model.testspecification.TestParameter#getAssignments()
	 * @see #getTestParameter()
	 * @generated
	 */
	EReference getTestParameter_Assignments();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.testspecification.TestCase <em>Test Case</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Case</em>'.
	 * @see com.specmate.model.testspecification.TestCase
	 * @generated
	 */
	EClass getTestCase();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.testspecification.ParameterAssignment <em>Parameter Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Assignment</em>'.
	 * @see com.specmate.model.testspecification.ParameterAssignment
	 * @generated
	 */
	EClass getParameterAssignment();

	/**
	 * Returns the meta object for the reference '{@link com.specmate.model.testspecification.ParameterAssignment#getParameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Parameter</em>'.
	 * @see com.specmate.model.testspecification.ParameterAssignment#getParameter()
	 * @see #getParameterAssignment()
	 * @generated
	 */
	EReference getParameterAssignment_Parameter();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.testspecification.ParameterAssignment#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.specmate.model.testspecification.ParameterAssignment#getValue()
	 * @see #getParameterAssignment()
	 * @generated
	 */
	EAttribute getParameterAssignment_Value();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.testspecification.ParameterAssignment#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition</em>'.
	 * @see com.specmate.model.testspecification.ParameterAssignment#getCondition()
	 * @see #getParameterAssignment()
	 * @generated
	 */
	EAttribute getParameterAssignment_Condition();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.testspecification.TestProcedure <em>Test Procedure</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Procedure</em>'.
	 * @see com.specmate.model.testspecification.TestProcedure
	 * @generated
	 */
	EClass getTestProcedure();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.testspecification.TestProcedure#isIsRegressionTest <em>Is Regression Test</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Regression Test</em>'.
	 * @see com.specmate.model.testspecification.TestProcedure#isIsRegressionTest()
	 * @see #getTestProcedure()
	 * @generated
	 */
	EAttribute getTestProcedure_IsRegressionTest();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.testspecification.TestStep <em>Test Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Test Step</em>'.
	 * @see com.specmate.model.testspecification.TestStep
	 * @generated
	 */
	EClass getTestStep();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.testspecification.TestStep#getExpectedOutcome <em>Expected Outcome</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expected Outcome</em>'.
	 * @see com.specmate.model.testspecification.TestStep#getExpectedOutcome()
	 * @see #getTestStep()
	 * @generated
	 */
	EAttribute getTestStep_ExpectedOutcome();

	/**
	 * Returns the meta object for the reference list '{@link com.specmate.model.testspecification.TestStep#getReferencedTestParameters <em>Referenced Test Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Referenced Test Parameters</em>'.
	 * @see com.specmate.model.testspecification.TestStep#getReferencedTestParameters()
	 * @see #getTestStep()
	 * @generated
	 */
	EReference getTestStep_ReferencedTestParameters();

	/**
	 * Returns the meta object for enum '{@link com.specmate.model.testspecification.ParameterType <em>Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Parameter Type</em>'.
	 * @see com.specmate.model.testspecification.ParameterType
	 * @generated
	 */
	EEnum getParameterType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TestspecificationFactory getTestspecificationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link com.specmate.model.testspecification.impl.TestSpecificationImpl <em>Test Specification</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.testspecification.impl.TestSpecificationImpl
		 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestSpecification()
		 * @generated
		 */
		EClass TEST_SPECIFICATION = eINSTANCE.getTestSpecification();

		/**
		 * The meta object literal for the '{@link com.specmate.model.testspecification.impl.TestParameterImpl <em>Test Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.testspecification.impl.TestParameterImpl
		 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestParameter()
		 * @generated
		 */
		EClass TEST_PARAMETER = eINSTANCE.getTestParameter();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_PARAMETER__TYPE = eINSTANCE.getTestParameter_Type();

		/**
		 * The meta object literal for the '<em><b>Assignments</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_PARAMETER__ASSIGNMENTS = eINSTANCE.getTestParameter_Assignments();

		/**
		 * The meta object literal for the '{@link com.specmate.model.testspecification.impl.TestCaseImpl <em>Test Case</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.testspecification.impl.TestCaseImpl
		 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestCase()
		 * @generated
		 */
		EClass TEST_CASE = eINSTANCE.getTestCase();

		/**
		 * The meta object literal for the '{@link com.specmate.model.testspecification.impl.ParameterAssignmentImpl <em>Parameter Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.testspecification.impl.ParameterAssignmentImpl
		 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getParameterAssignment()
		 * @generated
		 */
		EClass PARAMETER_ASSIGNMENT = eINSTANCE.getParameterAssignment();

		/**
		 * The meta object literal for the '<em><b>Parameter</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER_ASSIGNMENT__PARAMETER = eINSTANCE.getParameterAssignment_Parameter();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_ASSIGNMENT__VALUE = eINSTANCE.getParameterAssignment_Value();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_ASSIGNMENT__CONDITION = eINSTANCE.getParameterAssignment_Condition();

		/**
		 * The meta object literal for the '{@link com.specmate.model.testspecification.impl.TestProcedureImpl <em>Test Procedure</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.testspecification.impl.TestProcedureImpl
		 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestProcedure()
		 * @generated
		 */
		EClass TEST_PROCEDURE = eINSTANCE.getTestProcedure();

		/**
		 * The meta object literal for the '<em><b>Is Regression Test</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_PROCEDURE__IS_REGRESSION_TEST = eINSTANCE.getTestProcedure_IsRegressionTest();

		/**
		 * The meta object literal for the '{@link com.specmate.model.testspecification.impl.TestStepImpl <em>Test Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.testspecification.impl.TestStepImpl
		 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getTestStep()
		 * @generated
		 */
		EClass TEST_STEP = eINSTANCE.getTestStep();

		/**
		 * The meta object literal for the '<em><b>Expected Outcome</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TEST_STEP__EXPECTED_OUTCOME = eINSTANCE.getTestStep_ExpectedOutcome();

		/**
		 * The meta object literal for the '<em><b>Referenced Test Parameters</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TEST_STEP__REFERENCED_TEST_PARAMETERS = eINSTANCE.getTestStep_ReferencedTestParameters();

		/**
		 * The meta object literal for the '{@link com.specmate.model.testspecification.ParameterType <em>Parameter Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.testspecification.ParameterType
		 * @see com.specmate.model.testspecification.impl.TestspecificationPackageImpl#getParameterType()
		 * @generated
		 */
		EEnum PARAMETER_TYPE = eINSTANCE.getParameterType();

	}

} //TestspecificationPackage
