/**
 */
package com.specmate.model.requirements;

import com.specmate.model.base.BasePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see com.specmate.model.requirements.RequirementsFactory
 * @model kind="package"
 * @generated
 */
public interface RequirementsPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "requirements";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20180622/model/requirements";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.model.requirements";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	RequirementsPackage eINSTANCE = com.specmate.model.requirements.impl.RequirementsPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.model.requirements.impl.RequirementImpl <em>Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.requirements.impl.RequirementImpl
	 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getRequirement()
	 * @generated
	 */
	int REQUIREMENT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__ID = BasePackage.ISPECMATE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__NAME = BasePackage.ISPECMATE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__DESCRIPTION = BasePackage.ISPECMATE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__CONTENTS = BasePackage.ISPECMATE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__TRACES_TO = BasePackage.ISPECMATE_MODEL_OBJECT__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__TRACES_FROM = BasePackage.ISPECMATE_MODEL_OBJECT__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>Ext Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__EXT_ID = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Ext Id2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__EXT_ID2 = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__SOURCE = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Live</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__LIVE = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Number Of Tests</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__NUMBER_OF_TESTS = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Tac</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__TAC = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Implementing Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__IMPLEMENTING_UNIT = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Implementing BO Team</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__IMPLEMENTING_BO_TEAM = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Implementing IT Team</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__IMPLEMENTING_IT_TEAM = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Planned Release</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__PLANNED_RELEASE = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Status</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__STATUS = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Is Regression Requirement</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__IS_REGRESSION_REQUIREMENT = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Platform</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__PLATFORM = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_FEATURE_COUNT = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 13;

	/**
	 * The number of operations of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_OPERATION_COUNT = BasePackage.ISPECMATE_MODEL_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.requirements.impl.CEGModelImpl <em>CEG Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.requirements.impl.CEGModelImpl
	 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getCEGModel()
	 * @generated
	 */
	int CEG_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_MODEL__ID = BasePackage.ISPECMATE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_MODEL__NAME = BasePackage.ISPECMATE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_MODEL__DESCRIPTION = BasePackage.ISPECMATE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_MODEL__CONTENTS = BasePackage.ISPECMATE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_MODEL__TRACES_TO = BasePackage.ISPECMATE_MODEL_OBJECT__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_MODEL__TRACES_FROM = BasePackage.ISPECMATE_MODEL_OBJECT__TRACES_FROM;

	/**
	 * The number of structural features of the '<em>CEG Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_MODEL_FEATURE_COUNT = BasePackage.ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>CEG Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_MODEL_OPERATION_COUNT = BasePackage.ISPECMATE_MODEL_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.requirements.impl.CEGNodeImpl <em>CEG Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.requirements.impl.CEGNodeImpl
	 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getCEGNode()
	 * @generated
	 */
	int CEG_NODE = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__ID = BasePackage.IMODEL_NODE__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__NAME = BasePackage.IMODEL_NODE__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__DESCRIPTION = BasePackage.IMODEL_NODE__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__CONTENTS = BasePackage.IMODEL_NODE__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__TRACES_TO = BasePackage.IMODEL_NODE__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__TRACES_FROM = BasePackage.IMODEL_NODE__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__X = BasePackage.IMODEL_NODE__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__Y = BasePackage.IMODEL_NODE__Y;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__OUTGOING_CONNECTIONS = BasePackage.IMODEL_NODE__OUTGOING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__INCOMING_CONNECTIONS = BasePackage.IMODEL_NODE__INCOMING_CONNECTIONS;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__TYPE = BasePackage.IMODEL_NODE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__VARIABLE = BasePackage.IMODEL_NODE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE__CONDITION = BasePackage.IMODEL_NODE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>CEG Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE_FEATURE_COUNT = BasePackage.IMODEL_NODE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>CEG Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_NODE_OPERATION_COUNT = BasePackage.IMODEL_NODE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.requirements.impl.CEGConnectionImpl <em>CEG Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.requirements.impl.CEGConnectionImpl
	 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getCEGConnection()
	 * @generated
	 */
	int CEG_CONNECTION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__ID = BasePackage.IMODEL_CONNECTION__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__NAME = BasePackage.IMODEL_CONNECTION__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__DESCRIPTION = BasePackage.IMODEL_CONNECTION__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__CONTENTS = BasePackage.IMODEL_CONNECTION__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__TRACES_TO = BasePackage.IMODEL_CONNECTION__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__TRACES_FROM = BasePackage.IMODEL_CONNECTION__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__SOURCE = BasePackage.IMODEL_CONNECTION__SOURCE;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__TARGET = BasePackage.IMODEL_CONNECTION__TARGET;

	/**
	 * The feature id for the '<em><b>Negate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION__NEGATE = BasePackage.IMODEL_CONNECTION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>CEG Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION_FEATURE_COUNT = BasePackage.IMODEL_CONNECTION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>CEG Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CEG_CONNECTION_OPERATION_COUNT = BasePackage.IMODEL_CONNECTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.requirements.NodeType <em>Node Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.requirements.NodeType
	 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getNodeType()
	 * @generated
	 */
	int NODE_TYPE = 4;


	/**
	 * Returns the meta object for class '{@link com.specmate.model.requirements.Requirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requirement</em>'.
	 * @see com.specmate.model.requirements.Requirement
	 * @generated
	 */
	EClass getRequirement();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#getNumberOfTests <em>Number Of Tests</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Number Of Tests</em>'.
	 * @see com.specmate.model.requirements.Requirement#getNumberOfTests()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_NumberOfTests();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#getTac <em>Tac</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tac</em>'.
	 * @see com.specmate.model.requirements.Requirement#getTac()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_Tac();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#getImplementingUnit <em>Implementing Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementing Unit</em>'.
	 * @see com.specmate.model.requirements.Requirement#getImplementingUnit()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_ImplementingUnit();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#getImplementingBOTeam <em>Implementing BO Team</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementing BO Team</em>'.
	 * @see com.specmate.model.requirements.Requirement#getImplementingBOTeam()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_ImplementingBOTeam();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#getImplementingITTeam <em>Implementing IT Team</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Implementing IT Team</em>'.
	 * @see com.specmate.model.requirements.Requirement#getImplementingITTeam()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_ImplementingITTeam();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#getPlannedRelease <em>Planned Release</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Planned Release</em>'.
	 * @see com.specmate.model.requirements.Requirement#getPlannedRelease()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_PlannedRelease();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#getStatus <em>Status</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Status</em>'.
	 * @see com.specmate.model.requirements.Requirement#getStatus()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_Status();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#isIsRegressionRequirement <em>Is Regression Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Regression Requirement</em>'.
	 * @see com.specmate.model.requirements.Requirement#isIsRegressionRequirement()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_IsRegressionRequirement();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.Requirement#getPlatform <em>Platform</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Platform</em>'.
	 * @see com.specmate.model.requirements.Requirement#getPlatform()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_Platform();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.requirements.CEGModel <em>CEG Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CEG Model</em>'.
	 * @see com.specmate.model.requirements.CEGModel
	 * @generated
	 */
	EClass getCEGModel();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.requirements.CEGNode <em>CEG Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CEG Node</em>'.
	 * @see com.specmate.model.requirements.CEGNode
	 * @generated
	 */
	EClass getCEGNode();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.CEGNode#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see com.specmate.model.requirements.CEGNode#getType()
	 * @see #getCEGNode()
	 * @generated
	 */
	EAttribute getCEGNode_Type();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.CEGNode#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variable</em>'.
	 * @see com.specmate.model.requirements.CEGNode#getVariable()
	 * @see #getCEGNode()
	 * @generated
	 */
	EAttribute getCEGNode_Variable();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.CEGNode#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Condition</em>'.
	 * @see com.specmate.model.requirements.CEGNode#getCondition()
	 * @see #getCEGNode()
	 * @generated
	 */
	EAttribute getCEGNode_Condition();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.requirements.CEGConnection <em>CEG Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CEG Connection</em>'.
	 * @see com.specmate.model.requirements.CEGConnection
	 * @generated
	 */
	EClass getCEGConnection();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.requirements.CEGConnection#isNegate <em>Negate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Negate</em>'.
	 * @see com.specmate.model.requirements.CEGConnection#isNegate()
	 * @see #getCEGConnection()
	 * @generated
	 */
	EAttribute getCEGConnection_Negate();

	/**
	 * Returns the meta object for enum '{@link com.specmate.model.requirements.NodeType <em>Node Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Node Type</em>'.
	 * @see com.specmate.model.requirements.NodeType
	 * @generated
	 */
	EEnum getNodeType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	RequirementsFactory getRequirementsFactory();

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
		 * The meta object literal for the '{@link com.specmate.model.requirements.impl.RequirementImpl <em>Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.requirements.impl.RequirementImpl
		 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getRequirement()
		 * @generated
		 */
		EClass REQUIREMENT = eINSTANCE.getRequirement();

		/**
		 * The meta object literal for the '<em><b>Number Of Tests</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__NUMBER_OF_TESTS = eINSTANCE.getRequirement_NumberOfTests();

		/**
		 * The meta object literal for the '<em><b>Tac</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__TAC = eINSTANCE.getRequirement_Tac();

		/**
		 * The meta object literal for the '<em><b>Implementing Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__IMPLEMENTING_UNIT = eINSTANCE.getRequirement_ImplementingUnit();

		/**
		 * The meta object literal for the '<em><b>Implementing BO Team</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__IMPLEMENTING_BO_TEAM = eINSTANCE.getRequirement_ImplementingBOTeam();

		/**
		 * The meta object literal for the '<em><b>Implementing IT Team</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__IMPLEMENTING_IT_TEAM = eINSTANCE.getRequirement_ImplementingITTeam();

		/**
		 * The meta object literal for the '<em><b>Planned Release</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__PLANNED_RELEASE = eINSTANCE.getRequirement_PlannedRelease();

		/**
		 * The meta object literal for the '<em><b>Status</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__STATUS = eINSTANCE.getRequirement_Status();

		/**
		 * The meta object literal for the '<em><b>Is Regression Requirement</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__IS_REGRESSION_REQUIREMENT = eINSTANCE.getRequirement_IsRegressionRequirement();

		/**
		 * The meta object literal for the '<em><b>Platform</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__PLATFORM = eINSTANCE.getRequirement_Platform();

		/**
		 * The meta object literal for the '{@link com.specmate.model.requirements.impl.CEGModelImpl <em>CEG Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.requirements.impl.CEGModelImpl
		 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getCEGModel()
		 * @generated
		 */
		EClass CEG_MODEL = eINSTANCE.getCEGModel();

		/**
		 * The meta object literal for the '{@link com.specmate.model.requirements.impl.CEGNodeImpl <em>CEG Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.requirements.impl.CEGNodeImpl
		 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getCEGNode()
		 * @generated
		 */
		EClass CEG_NODE = eINSTANCE.getCEGNode();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CEG_NODE__TYPE = eINSTANCE.getCEGNode_Type();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CEG_NODE__VARIABLE = eINSTANCE.getCEGNode_Variable();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CEG_NODE__CONDITION = eINSTANCE.getCEGNode_Condition();

		/**
		 * The meta object literal for the '{@link com.specmate.model.requirements.impl.CEGConnectionImpl <em>CEG Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.requirements.impl.CEGConnectionImpl
		 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getCEGConnection()
		 * @generated
		 */
		EClass CEG_CONNECTION = eINSTANCE.getCEGConnection();

		/**
		 * The meta object literal for the '<em><b>Negate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CEG_CONNECTION__NEGATE = eINSTANCE.getCEGConnection_Negate();

		/**
		 * The meta object literal for the '{@link com.specmate.model.requirements.NodeType <em>Node Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.requirements.NodeType
		 * @see com.specmate.model.requirements.impl.RequirementsPackageImpl#getNodeType()
		 * @generated
		 */
		EEnum NODE_TYPE = eINSTANCE.getNodeType();

	}

} //RequirementsPackage
