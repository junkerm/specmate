/**
 */
package com.specmate.model.processes;

import com.specmate.model.base.BasePackage;

import org.eclipse.emf.ecore.EClass;
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
 * @see com.specmate.model.processes.ProcessesFactory
 * @model kind="package"
 * @generated
 */
public interface ProcessesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "processes";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20170409/model/processes";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.model.processes";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ProcessesPackage eINSTANCE = com.specmate.model.processes.impl.ProcessesPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.model.processes.impl.ProcessImpl <em>Process</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.processes.impl.ProcessImpl
	 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcess()
	 * @generated
	 */
	int PROCESS = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__ID = BasePackage.ICONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__NAME = BasePackage.ICONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__DESCRIPTION = BasePackage.ICONTAINER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS__CONTENTS = BasePackage.ICONTAINER__CONTENTS;

	/**
	 * The number of structural features of the '<em>Process</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_FEATURE_COUNT = BasePackage.ICONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Process</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_OPERATION_COUNT = BasePackage.ICONTAINER_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.specmate.model.processes.impl.ProcessNodeImpl <em>Process Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.processes.impl.ProcessNodeImpl
	 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcessNode()
	 * @generated
	 */
	int PROCESS_NODE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__ID = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__NAME = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__DESCRIPTION = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__CONTENTS = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__X = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__Y = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__OUTGOING_CONNECTIONS = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE__INCOMING_CONNECTIONS = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Process Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE_FEATURE_COUNT = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Process Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_NODE_OPERATION_COUNT = BasePackage.ISPECMATE_POSITIONABLE_MODEL_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.processes.impl.ProcessStepImpl <em>Process Step</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.processes.impl.ProcessStepImpl
	 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcessStep()
	 * @generated
	 */
	int PROCESS_STEP = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP__ID = PROCESS_NODE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP__NAME = PROCESS_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP__DESCRIPTION = PROCESS_NODE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP__CONTENTS = PROCESS_NODE__CONTENTS;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP__X = PROCESS_NODE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP__Y = PROCESS_NODE__Y;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP__OUTGOING_CONNECTIONS = PROCESS_NODE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP__INCOMING_CONNECTIONS = PROCESS_NODE__INCOMING_CONNECTIONS;

	/**
	 * The number of structural features of the '<em>Process Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP_FEATURE_COUNT = PROCESS_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Process Step</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_STEP_OPERATION_COUNT = PROCESS_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.processes.impl.ProcessDecisionImpl <em>Process Decision</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.processes.impl.ProcessDecisionImpl
	 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcessDecision()
	 * @generated
	 */
	int PROCESS_DECISION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION__ID = PROCESS_NODE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION__NAME = PROCESS_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION__DESCRIPTION = PROCESS_NODE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION__CONTENTS = PROCESS_NODE__CONTENTS;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION__X = PROCESS_NODE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION__Y = PROCESS_NODE__Y;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION__OUTGOING_CONNECTIONS = PROCESS_NODE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION__INCOMING_CONNECTIONS = PROCESS_NODE__INCOMING_CONNECTIONS;

	/**
	 * The number of structural features of the '<em>Process Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION_FEATURE_COUNT = PROCESS_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Process Decision</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_DECISION_OPERATION_COUNT = PROCESS_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.processes.impl.ProcessConnectionImpl <em>Process Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.processes.impl.ProcessConnectionImpl
	 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcessConnection()
	 * @generated
	 */
	int PROCESS_CONNECTION = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONNECTION__ID = BasePackage.ISPECMATE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONNECTION__NAME = BasePackage.ISPECMATE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONNECTION__DESCRIPTION = BasePackage.ISPECMATE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONNECTION__CONTENTS = BasePackage.ISPECMATE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONNECTION__SOURCE = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONNECTION__TARGET = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Process Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONNECTION_FEATURE_COUNT = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Process Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_CONNECTION_OPERATION_COUNT = BasePackage.ISPECMATE_MODEL_OBJECT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.specmate.model.processes.Process <em>Process</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process</em>'.
	 * @see com.specmate.model.processes.Process
	 * @generated
	 */
	EClass getProcess();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.processes.ProcessNode <em>Process Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Node</em>'.
	 * @see com.specmate.model.processes.ProcessNode
	 * @generated
	 */
	EClass getProcessNode();

	/**
	 * Returns the meta object for the reference list '{@link com.specmate.model.processes.ProcessNode#getOutgoingConnections <em>Outgoing Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Connections</em>'.
	 * @see com.specmate.model.processes.ProcessNode#getOutgoingConnections()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_OutgoingConnections();

	/**
	 * Returns the meta object for the reference list '{@link com.specmate.model.processes.ProcessNode#getIncomingConnections <em>Incoming Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Connections</em>'.
	 * @see com.specmate.model.processes.ProcessNode#getIncomingConnections()
	 * @see #getProcessNode()
	 * @generated
	 */
	EReference getProcessNode_IncomingConnections();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.processes.ProcessStep <em>Process Step</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Step</em>'.
	 * @see com.specmate.model.processes.ProcessStep
	 * @generated
	 */
	EClass getProcessStep();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.processes.ProcessDecision <em>Process Decision</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Decision</em>'.
	 * @see com.specmate.model.processes.ProcessDecision
	 * @generated
	 */
	EClass getProcessDecision();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.processes.ProcessConnection <em>Process Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Connection</em>'.
	 * @see com.specmate.model.processes.ProcessConnection
	 * @generated
	 */
	EClass getProcessConnection();

	/**
	 * Returns the meta object for the reference '{@link com.specmate.model.processes.ProcessConnection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see com.specmate.model.processes.ProcessConnection#getSource()
	 * @see #getProcessConnection()
	 * @generated
	 */
	EReference getProcessConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link com.specmate.model.processes.ProcessConnection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see com.specmate.model.processes.ProcessConnection#getTarget()
	 * @see #getProcessConnection()
	 * @generated
	 */
	EReference getProcessConnection_Target();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ProcessesFactory getProcessesFactory();

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
		 * The meta object literal for the '{@link com.specmate.model.processes.impl.ProcessImpl <em>Process</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.processes.impl.ProcessImpl
		 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcess()
		 * @generated
		 */
		EClass PROCESS = eINSTANCE.getProcess();
		/**
		 * The meta object literal for the '{@link com.specmate.model.processes.impl.ProcessNodeImpl <em>Process Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.processes.impl.ProcessNodeImpl
		 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcessNode()
		 * @generated
		 */
		EClass PROCESS_NODE = eINSTANCE.getProcessNode();
		/**
		 * The meta object literal for the '<em><b>Outgoing Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__OUTGOING_CONNECTIONS = eINSTANCE.getProcessNode_OutgoingConnections();
		/**
		 * The meta object literal for the '<em><b>Incoming Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_NODE__INCOMING_CONNECTIONS = eINSTANCE.getProcessNode_IncomingConnections();
		/**
		 * The meta object literal for the '{@link com.specmate.model.processes.impl.ProcessStepImpl <em>Process Step</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.processes.impl.ProcessStepImpl
		 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcessStep()
		 * @generated
		 */
		EClass PROCESS_STEP = eINSTANCE.getProcessStep();
		/**
		 * The meta object literal for the '{@link com.specmate.model.processes.impl.ProcessDecisionImpl <em>Process Decision</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.processes.impl.ProcessDecisionImpl
		 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcessDecision()
		 * @generated
		 */
		EClass PROCESS_DECISION = eINSTANCE.getProcessDecision();
		/**
		 * The meta object literal for the '{@link com.specmate.model.processes.impl.ProcessConnectionImpl <em>Process Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.processes.impl.ProcessConnectionImpl
		 * @see com.specmate.model.processes.impl.ProcessesPackageImpl#getProcessConnection()
		 * @generated
		 */
		EClass PROCESS_CONNECTION = eINSTANCE.getProcessConnection();
		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_CONNECTION__SOURCE = eINSTANCE.getProcessConnection_Source();
		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_CONNECTION__TARGET = eINSTANCE.getProcessConnection_Target();

	}

} //ProcessesPackage
