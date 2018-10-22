/**
 */
package com.specmate.model.bdd;

import com.specmate.model.base.BasePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

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
 * @see com.specmate.model.bdd.BddFactory
 * @model kind="package"
 * @generated
 */
public interface BddPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "bdd";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20180925/model/bdd";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.model.bdd";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BddPackage eINSTANCE = com.specmate.model.bdd.impl.BddPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.model.bdd.impl.BDDNodeImpl <em>BDD Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.bdd.impl.BDDNodeImpl
	 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDNode()
	 * @generated
	 */
	int BDD_NODE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__ID = BasePackage.IMODEL_NODE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__NAME = BasePackage.IMODEL_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__DESCRIPTION = BasePackage.IMODEL_NODE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__CONTENTS = BasePackage.IMODEL_NODE__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__TRACES_TO = BasePackage.IMODEL_NODE__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__TRACES_FROM = BasePackage.IMODEL_NODE__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__X = BasePackage.IMODEL_NODE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__Y = BasePackage.IMODEL_NODE__Y;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__OUTGOING_CONNECTIONS = BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE__INCOMING_CONNECTIONS = BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS;

	/**
	 * The number of structural features of the '<em>BDD Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE_FEATURE_COUNT = BasePackage.IMODEL_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>BDD Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NODE_OPERATION_COUNT = BasePackage.IMODEL_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.bdd.impl.BDDTerminalNodeImpl <em>BDD Terminal Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.bdd.impl.BDDTerminalNodeImpl
	 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDTerminalNode()
	 * @generated
	 */
	int BDD_TERMINAL_NODE = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__ID = BDD_NODE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__NAME = BDD_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__DESCRIPTION = BDD_NODE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__CONTENTS = BDD_NODE__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__TRACES_TO = BDD_NODE__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__TRACES_FROM = BDD_NODE__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__X = BDD_NODE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__Y = BDD_NODE__Y;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__OUTGOING_CONNECTIONS = BDD_NODE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__INCOMING_CONNECTIONS = BDD_NODE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE__VALUE = BDD_NODE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>BDD Terminal Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE_FEATURE_COUNT = BDD_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>BDD Terminal Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_TERMINAL_NODE_OPERATION_COUNT = BDD_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.bdd.impl.BDDNoTerminalNodeImpl <em>BDD No Terminal Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.bdd.impl.BDDNoTerminalNodeImpl
	 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDNoTerminalNode()
	 * @generated
	 */
	int BDD_NO_TERMINAL_NODE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__ID = BDD_NODE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__NAME = BDD_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__DESCRIPTION = BDD_NODE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__CONTENTS = BDD_NODE__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__TRACES_TO = BDD_NODE__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__TRACES_FROM = BDD_NODE__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__X = BDD_NODE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__Y = BDD_NODE__Y;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__OUTGOING_CONNECTIONS = BDD_NODE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__INCOMING_CONNECTIONS = BDD_NODE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__VARIABLE = BDD_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE__CONDITION = BDD_NODE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>BDD No Terminal Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE_FEATURE_COUNT = BDD_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>BDD No Terminal Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_NO_TERMINAL_NODE_OPERATION_COUNT = BDD_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.bdd.impl.BDDConnectionImpl <em>BDD Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.bdd.impl.BDDConnectionImpl
	 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDConnection()
	 * @generated
	 */
	int BDD_CONNECTION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__ID = BasePackage.IMODEL_CONNECTION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__NAME = BasePackage.IMODEL_CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__DESCRIPTION = BasePackage.IMODEL_CONNECTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__CONTENTS = BasePackage.IMODEL_CONNECTION__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__TRACES_TO = BasePackage.IMODEL_CONNECTION__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__TRACES_FROM = BasePackage.IMODEL_CONNECTION__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__SOURCE = BasePackage.IMODEL_CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__TARGET = BasePackage.IMODEL_CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Negate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION__NEGATE = BasePackage.IMODEL_CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>BDD Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION_FEATURE_COUNT = BasePackage.IMODEL_CONNECTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>BDD Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_CONNECTION_OPERATION_COUNT = BasePackage.IMODEL_CONNECTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.bdd.impl.BDDModelImpl <em>BDD Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.bdd.impl.BDDModelImpl
	 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDModel()
	 * @generated
	 */
	int BDD_MODEL = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_MODEL__ID = BasePackage.ISPECMATE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_MODEL__NAME = BasePackage.ISPECMATE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_MODEL__DESCRIPTION = BasePackage.ISPECMATE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_MODEL__CONTENTS = BasePackage.ISPECMATE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_MODEL__TRACES_TO = BasePackage.ISPECMATE_MODEL_OBJECT__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_MODEL__TRACES_FROM = BasePackage.ISPECMATE_MODEL_OBJECT__TRACES_FROM;

	/**
	 * The number of structural features of the '<em>BDD Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_MODEL_FEATURE_COUNT = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>BDD Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BDD_MODEL_OPERATION_COUNT = BasePackage.ISPECMATE_MODEL_OBJECT_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.specmate.model.bdd.BDDNode <em>BDD Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BDD Node</em>'.
	 * @see com.specmate.model.bdd.BDDNode
	 * @generated
	 */
	EClass getBDDNode();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.bdd.BDDTerminalNode <em>BDD Terminal Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BDD Terminal Node</em>'.
	 * @see com.specmate.model.bdd.BDDTerminalNode
	 * @generated
	 */
	EClass getBDDTerminalNode();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.bdd.BDDTerminalNode#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see com.specmate.model.bdd.BDDTerminalNode#isValue()
	 * @see #getBDDTerminalNode()
	 * @generated
	 */
	EAttribute getBDDTerminalNode_Value();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.bdd.BDDNoTerminalNode <em>BDD No Terminal Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BDD No Terminal Node</em>'.
	 * @see com.specmate.model.bdd.BDDNoTerminalNode
	 * @generated
	 */
	EClass getBDDNoTerminalNode();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.bdd.BDDNoTerminalNode#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variable</em>'.
	 * @see com.specmate.model.bdd.BDDNoTerminalNode#getVariable()
	 * @see #getBDDNoTerminalNode()
	 * @generated
	 */
	EAttribute getBDDNoTerminalNode_Variable();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.bdd.BDDNoTerminalNode#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition</em>'.
	 * @see com.specmate.model.bdd.BDDNoTerminalNode#getCondition()
	 * @see #getBDDNoTerminalNode()
	 * @generated
	 */
	EAttribute getBDDNoTerminalNode_Condition();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.bdd.BDDConnection <em>BDD Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BDD Connection</em>'.
	 * @see com.specmate.model.bdd.BDDConnection
	 * @generated
	 */
	EClass getBDDConnection();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.bdd.BDDConnection#isNegate <em>Negate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Negate</em>'.
	 * @see com.specmate.model.bdd.BDDConnection#isNegate()
	 * @see #getBDDConnection()
	 * @generated
	 */
	EAttribute getBDDConnection_Negate();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.bdd.BDDModel <em>BDD Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>BDD Model</em>'.
	 * @see com.specmate.model.bdd.BDDModel
	 * @generated
	 */
	EClass getBDDModel();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BddFactory getBddFactory();

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
		 * The meta object literal for the '{@link com.specmate.model.bdd.impl.BDDNodeImpl <em>BDD Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.bdd.impl.BDDNodeImpl
		 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDNode()
		 * @generated
		 */
		EClass BDD_NODE = eINSTANCE.getBDDNode();

		/**
		 * The meta object literal for the '{@link com.specmate.model.bdd.impl.BDDTerminalNodeImpl <em>BDD Terminal Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.bdd.impl.BDDTerminalNodeImpl
		 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDTerminalNode()
		 * @generated
		 */
		EClass BDD_TERMINAL_NODE = eINSTANCE.getBDDTerminalNode();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BDD_TERMINAL_NODE__VALUE = eINSTANCE.getBDDTerminalNode_Value();

		/**
		 * The meta object literal for the '{@link com.specmate.model.bdd.impl.BDDNoTerminalNodeImpl <em>BDD No Terminal Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.bdd.impl.BDDNoTerminalNodeImpl
		 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDNoTerminalNode()
		 * @generated
		 */
		EClass BDD_NO_TERMINAL_NODE = eINSTANCE.getBDDNoTerminalNode();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BDD_NO_TERMINAL_NODE__VARIABLE = eINSTANCE.getBDDNoTerminalNode_Variable();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BDD_NO_TERMINAL_NODE__CONDITION = eINSTANCE.getBDDNoTerminalNode_Condition();

		/**
		 * The meta object literal for the '{@link com.specmate.model.bdd.impl.BDDConnectionImpl <em>BDD Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.bdd.impl.BDDConnectionImpl
		 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDConnection()
		 * @generated
		 */
		EClass BDD_CONNECTION = eINSTANCE.getBDDConnection();

		/**
		 * The meta object literal for the '<em><b>Negate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BDD_CONNECTION__NEGATE = eINSTANCE.getBDDConnection_Negate();

		/**
		 * The meta object literal for the '{@link com.specmate.model.bdd.impl.BDDModelImpl <em>BDD Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.bdd.impl.BDDModelImpl
		 * @see com.specmate.model.bdd.impl.BddPackageImpl#getBDDModel()
		 * @generated
		 */
		EClass BDD_MODEL = eINSTANCE.getBDDModel();

	}

} //BddPackage
