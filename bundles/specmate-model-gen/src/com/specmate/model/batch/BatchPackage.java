/**
 */
package com.specmate.model.batch;

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
 * @see com.specmate.model.batch.BatchFactory
 * @model kind="package"
 * @generated
 */
public interface BatchPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "batch";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20180925/model/batch";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.model.batch";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BatchPackage eINSTANCE = com.specmate.model.batch.impl.BatchPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.model.batch.impl.BatchOperationImpl <em>Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.batch.impl.BatchOperationImpl
	 * @see com.specmate.model.batch.impl.BatchPackageImpl#getBatchOperation()
	 * @generated
	 */
	int BATCH_OPERATION = 0;

	/**
	 * The feature id for the '<em><b>Operations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_OPERATION__OPERATIONS = 0;

	/**
	 * The number of structural features of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_OPERATION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BATCH_OPERATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.batch.impl.OperationImpl <em>Operation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.batch.impl.OperationImpl
	 * @see com.specmate.model.batch.impl.BatchPackageImpl#getOperation()
	 * @generated
	 */
	int OPERATION = 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__TYPE = 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__TARGET = 1;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION__VALUE = 2;

	/**
	 * The number of structural features of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Operation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int OPERATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.batch.OperationType <em>Operation Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.batch.OperationType
	 * @see com.specmate.model.batch.impl.BatchPackageImpl#getOperationType()
	 * @generated
	 */
	int OPERATION_TYPE = 2;


	/**
	 * Returns the meta object for class '{@link com.specmate.model.batch.BatchOperation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see com.specmate.model.batch.BatchOperation
	 * @generated
	 */
	EClass getBatchOperation();

	/**
	 * Returns the meta object for the containment reference list '{@link com.specmate.model.batch.BatchOperation#getOperations <em>Operations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Operations</em>'.
	 * @see com.specmate.model.batch.BatchOperation#getOperations()
	 * @see #getBatchOperation()
	 * @generated
	 */
	EReference getBatchOperation_Operations();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.batch.Operation <em>Operation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Operation</em>'.
	 * @see com.specmate.model.batch.Operation
	 * @generated
	 */
	EClass getOperation();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.batch.Operation#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.specmate.model.batch.Operation#getType()
	 * @see #getOperation()
	 * @generated
	 */
	EAttribute getOperation_Type();

	/**
	 * Returns the meta object for the reference '{@link com.specmate.model.batch.Operation#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see com.specmate.model.batch.Operation#getTarget()
	 * @see #getOperation()
	 * @generated
	 */
	EReference getOperation_Target();

	/**
	 * Returns the meta object for the containment reference '{@link com.specmate.model.batch.Operation#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see com.specmate.model.batch.Operation#getValue()
	 * @see #getOperation()
	 * @generated
	 */
	EReference getOperation_Value();

	/**
	 * Returns the meta object for enum '{@link com.specmate.model.batch.OperationType <em>Operation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operation Type</em>'.
	 * @see com.specmate.model.batch.OperationType
	 * @generated
	 */
	EEnum getOperationType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BatchFactory getBatchFactory();

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
		 * The meta object literal for the '{@link com.specmate.model.batch.impl.BatchOperationImpl <em>Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.batch.impl.BatchOperationImpl
		 * @see com.specmate.model.batch.impl.BatchPackageImpl#getBatchOperation()
		 * @generated
		 */
		EClass BATCH_OPERATION = eINSTANCE.getBatchOperation();

		/**
		 * The meta object literal for the '<em><b>Operations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BATCH_OPERATION__OPERATIONS = eINSTANCE.getBatchOperation_Operations();

		/**
		 * The meta object literal for the '{@link com.specmate.model.batch.impl.OperationImpl <em>Operation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.batch.impl.OperationImpl
		 * @see com.specmate.model.batch.impl.BatchPackageImpl#getOperation()
		 * @generated
		 */
		EClass OPERATION = eINSTANCE.getOperation();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute OPERATION__TYPE = eINSTANCE.getOperation_Type();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION__TARGET = eINSTANCE.getOperation_Target();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference OPERATION__VALUE = eINSTANCE.getOperation_Value();

		/**
		 * The meta object literal for the '{@link com.specmate.model.batch.OperationType <em>Operation Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.batch.OperationType
		 * @see com.specmate.model.batch.impl.BatchPackageImpl#getOperationType()
		 * @generated
		 */
		EEnum OPERATION_TYPE = eINSTANCE.getOperationType();

	}

} //BatchPackage
