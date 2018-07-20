/**
 */
package com.specmate.model.base;

import org.eclipse.emf.ecore.EAttribute;
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
 * @see com.specmate.model.base.BaseFactory
 * @model kind="package"
 * @generated
 */
public interface BasePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "base";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20180720/model/base";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.model.basemodel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BasePackage eINSTANCE = com.specmate.model.base.impl.BasePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.model.base.INamed <em>INamed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.INamed
	 * @see com.specmate.model.base.impl.BasePackageImpl#getINamed()
	 * @generated
	 */
	int INAMED = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INAMED__NAME = 0;

	/**
	 * The number of structural features of the '<em>INamed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INAMED_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>INamed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INAMED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.IDescribed <em>IDescribed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.IDescribed
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIDescribed()
	 * @generated
	 */
	int IDESCRIBED = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESCRIBED__DESCRIPTION = 0;

	/**
	 * The number of structural features of the '<em>IDescribed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESCRIBED_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IDescribed</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IDESCRIBED_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.IID <em>IID</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.IID
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIID()
	 * @generated
	 */
	int IID = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IID__ID = 0;

	/**
	 * The number of structural features of the '<em>IID</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IID_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IID</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IID_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.IContentElement <em>IContent Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.IContentElement
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIContentElement()
	 * @generated
	 */
	int ICONTENT_ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT__ID = IID__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT__NAME = IID_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT__DESCRIPTION = IID_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IContent Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT_FEATURE_COUNT = IID_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IContent Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT_OPERATION_COUNT = IID_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.IContainer <em>IContainer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.IContainer
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIContainer()
	 * @generated
	 */
	int ICONTAINER = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER__ID = ICONTENT_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER__NAME = ICONTENT_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER__DESCRIPTION = ICONTENT_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER__CONTENTS = ICONTENT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>IContainer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER_FEATURE_COUNT = ICONTENT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>IContainer</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER_OPERATION_COUNT = ICONTENT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.ISpecmateModelObject <em>ISpecmate Model Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.ISpecmateModelObject
	 * @see com.specmate.model.base.impl.BasePackageImpl#getISpecmateModelObject()
	 * @generated
	 */
	int ISPECMATE_MODEL_OBJECT = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT__ID = ICONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT__NAME = ICONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT__DESCRIPTION = ICONTAINER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT__CONTENTS = ICONTAINER__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT__TRACES_TO = ICONTAINER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT__TRACES_FROM = ICONTAINER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ISpecmate Model Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT_FEATURE_COUNT = ICONTAINER_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>ISpecmate Model Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT_OPERATION_COUNT = ICONTAINER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.impl.FolderImpl
	 * @see com.specmate.model.base.impl.BasePackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__ID = ISPECMATE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__NAME = ISPECMATE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__DESCRIPTION = ISPECMATE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__CONTENTS = ISPECMATE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__TRACES_TO = ISPECMATE_MODEL_OBJECT__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__TRACES_FROM = ISPECMATE_MODEL_OBJECT__TRACES_FROM;

	/**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_FEATURE_COUNT = ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_OPERATION_COUNT = ISPECMATE_MODEL_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.IPositionable <em>IPositionable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.IPositionable
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIPositionable()
	 * @generated
	 */
	int IPOSITIONABLE = 7;

	/**
	 * The feature id for the '<em><b>Position</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPOSITIONABLE__POSITION = 0;

	/**
	 * The number of structural features of the '<em>IPositionable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPOSITIONABLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IPositionable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IPOSITIONABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.impl.IExternalImpl <em>IExternal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.impl.IExternalImpl
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIExternal()
	 * @generated
	 */
	int IEXTERNAL = 8;

	/**
	 * The feature id for the '<em><b>Ext Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXTERNAL__EXT_ID = 0;

	/**
	 * The feature id for the '<em><b>Ext Id2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXTERNAL__EXT_ID2 = 1;

	/**
	 * The feature id for the '<em><b>Source</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXTERNAL__SOURCE = 2;

	/**
	 * The feature id for the '<em><b>Live</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXTERNAL__LIVE = 3;

	/**
	 * The number of structural features of the '<em>IExternal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXTERNAL_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>IExternal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXTERNAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.ISpecmatePositionableModelObject <em>ISpecmate Positionable Model Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.ISpecmatePositionableModelObject
	 * @see com.specmate.model.base.impl.BasePackageImpl#getISpecmatePositionableModelObject()
	 * @generated
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT__ID = ISPECMATE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT__NAME = ISPECMATE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT__DESCRIPTION = ISPECMATE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT__CONTENTS = ISPECMATE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT__TRACES_TO = ISPECMATE_MODEL_OBJECT__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT__TRACES_FROM = ISPECMATE_MODEL_OBJECT__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT__X = ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y = ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>ISpecmate Positionable Model Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT_FEATURE_COUNT = ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>ISpecmate Positionable Model Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_POSITIONABLE_MODEL_OBJECT_OPERATION_COUNT = ISPECMATE_MODEL_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.impl.IModelConnectionImpl <em>IModel Connection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.impl.IModelConnectionImpl
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIModelConnection()
	 * @generated
	 */
	int IMODEL_CONNECTION = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION__ID = ISPECMATE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION__NAME = ISPECMATE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION__DESCRIPTION = ISPECMATE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION__CONTENTS = ISPECMATE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION__TRACES_TO = ISPECMATE_MODEL_OBJECT__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION__TRACES_FROM = ISPECMATE_MODEL_OBJECT__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>Source</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION__SOURCE = ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Target</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION__TARGET = ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IModel Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION_FEATURE_COUNT = ISPECMATE_MODEL_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IModel Connection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_CONNECTION_OPERATION_COUNT = ISPECMATE_MODEL_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.impl.IModelNodeImpl <em>IModel Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.impl.IModelNodeImpl
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIModelNode()
	 * @generated
	 */
	int IMODEL_NODE = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__ID = ISPECMATE_POSITIONABLE_MODEL_OBJECT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__NAME = ISPECMATE_POSITIONABLE_MODEL_OBJECT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__DESCRIPTION = ISPECMATE_POSITIONABLE_MODEL_OBJECT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__CONTENTS = ISPECMATE_POSITIONABLE_MODEL_OBJECT__CONTENTS;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__TRACES_TO = ISPECMATE_POSITIONABLE_MODEL_OBJECT__TRACES_TO;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__TRACES_FROM = ISPECMATE_POSITIONABLE_MODEL_OBJECT__TRACES_FROM;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__X = ISPECMATE_POSITIONABLE_MODEL_OBJECT__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__Y = ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y;

	/**
	 * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__OUTGOING_CONNECTIONS = ISPECMATE_POSITIONABLE_MODEL_OBJECT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE__INCOMING_CONNECTIONS = ISPECMATE_POSITIONABLE_MODEL_OBJECT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IModel Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE_FEATURE_COUNT = ISPECMATE_POSITIONABLE_MODEL_OBJECT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IModel Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODEL_NODE_OPERATION_COUNT = ISPECMATE_POSITIONABLE_MODEL_OBJECT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.base.impl.ITracingElementImpl <em>ITracing Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.impl.ITracingElementImpl
	 * @see com.specmate.model.base.impl.BasePackageImpl#getITracingElement()
	 * @generated
	 */
	int ITRACING_ELEMENT = 12;

	/**
	 * The feature id for the '<em><b>Traces To</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITRACING_ELEMENT__TRACES_TO = 0;

	/**
	 * The feature id for the '<em><b>Traces From</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITRACING_ELEMENT__TRACES_FROM = 1;

	/**
	 * The number of structural features of the '<em>ITracing Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITRACING_ELEMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>ITracing Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITRACING_ELEMENT_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.INamed <em>INamed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INamed</em>'.
	 * @see com.specmate.model.base.INamed
	 * @generated
	 */
	EClass getINamed();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.INamed#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.specmate.model.base.INamed#getName()
	 * @see #getINamed()
	 * @generated
	 */
	EAttribute getINamed_Name();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.IDescribed <em>IDescribed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDescribed</em>'.
	 * @see com.specmate.model.base.IDescribed
	 * @generated
	 */
	EClass getIDescribed();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.IDescribed#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.specmate.model.base.IDescribed#getDescription()
	 * @see #getIDescribed()
	 * @generated
	 */
	EAttribute getIDescribed_Description();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.IID <em>IID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IID</em>'.
	 * @see com.specmate.model.base.IID
	 * @generated
	 */
	EClass getIID();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.IID#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.specmate.model.base.IID#getId()
	 * @see #getIID()
	 * @generated
	 */
	EAttribute getIID_Id();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.IContentElement <em>IContent Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IContent Element</em>'.
	 * @see com.specmate.model.base.IContentElement
	 * @generated
	 */
	EClass getIContentElement();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.IContainer <em>IContainer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IContainer</em>'.
	 * @see com.specmate.model.base.IContainer
	 * @generated
	 */
	EClass getIContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.specmate.model.base.IContainer#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see com.specmate.model.base.IContainer#getContents()
	 * @see #getIContainer()
	 * @generated
	 */
	EReference getIContainer_Contents();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.ISpecmateModelObject <em>ISpecmate Model Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ISpecmate Model Object</em>'.
	 * @see com.specmate.model.base.ISpecmateModelObject
	 * @generated
	 */
	EClass getISpecmateModelObject();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see com.specmate.model.base.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.IPositionable <em>IPositionable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IPositionable</em>'.
	 * @see com.specmate.model.base.IPositionable
	 * @generated
	 */
	EClass getIPositionable();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.IPositionable#getPosition <em>Position</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Position</em>'.
	 * @see com.specmate.model.base.IPositionable#getPosition()
	 * @see #getIPositionable()
	 * @generated
	 */
	EAttribute getIPositionable_Position();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.IExternal <em>IExternal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IExternal</em>'.
	 * @see com.specmate.model.base.IExternal
	 * @generated
	 */
	EClass getIExternal();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.IExternal#getExtId <em>Ext Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ext Id</em>'.
	 * @see com.specmate.model.base.IExternal#getExtId()
	 * @see #getIExternal()
	 * @generated
	 */
	EAttribute getIExternal_ExtId();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.IExternal#getExtId2 <em>Ext Id2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Ext Id2</em>'.
	 * @see com.specmate.model.base.IExternal#getExtId2()
	 * @see #getIExternal()
	 * @generated
	 */
	EAttribute getIExternal_ExtId2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.IExternal#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source</em>'.
	 * @see com.specmate.model.base.IExternal#getSource()
	 * @see #getIExternal()
	 * @generated
	 */
	EAttribute getIExternal_Source();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.IExternal#isLive <em>Live</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Live</em>'.
	 * @see com.specmate.model.base.IExternal#isLive()
	 * @see #getIExternal()
	 * @generated
	 */
	EAttribute getIExternal_Live();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.ISpecmatePositionableModelObject <em>ISpecmate Positionable Model Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ISpecmate Positionable Model Object</em>'.
	 * @see com.specmate.model.base.ISpecmatePositionableModelObject
	 * @generated
	 */
	EClass getISpecmatePositionableModelObject();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.ISpecmatePositionableModelObject#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see com.specmate.model.base.ISpecmatePositionableModelObject#getX()
	 * @see #getISpecmatePositionableModelObject()
	 * @generated
	 */
	EAttribute getISpecmatePositionableModelObject_X();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.base.ISpecmatePositionableModelObject#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see com.specmate.model.base.ISpecmatePositionableModelObject#getY()
	 * @see #getISpecmatePositionableModelObject()
	 * @generated
	 */
	EAttribute getISpecmatePositionableModelObject_Y();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.IModelConnection <em>IModel Connection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IModel Connection</em>'.
	 * @see com.specmate.model.base.IModelConnection
	 * @generated
	 */
	EClass getIModelConnection();

	/**
	 * Returns the meta object for the reference '{@link com.specmate.model.base.IModelConnection#getSource <em>Source</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Source</em>'.
	 * @see com.specmate.model.base.IModelConnection#getSource()
	 * @see #getIModelConnection()
	 * @generated
	 */
	EReference getIModelConnection_Source();

	/**
	 * Returns the meta object for the reference '{@link com.specmate.model.base.IModelConnection#getTarget <em>Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Target</em>'.
	 * @see com.specmate.model.base.IModelConnection#getTarget()
	 * @see #getIModelConnection()
	 * @generated
	 */
	EReference getIModelConnection_Target();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.IModelNode <em>IModel Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IModel Node</em>'.
	 * @see com.specmate.model.base.IModelNode
	 * @generated
	 */
	EClass getIModelNode();

	/**
	 * Returns the meta object for the reference list '{@link com.specmate.model.base.IModelNode#getOutgoingConnections <em>Outgoing Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Outgoing Connections</em>'.
	 * @see com.specmate.model.base.IModelNode#getOutgoingConnections()
	 * @see #getIModelNode()
	 * @generated
	 */
	EReference getIModelNode_OutgoingConnections();

	/**
	 * Returns the meta object for the reference list '{@link com.specmate.model.base.IModelNode#getIncomingConnections <em>Incoming Connections</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Incoming Connections</em>'.
	 * @see com.specmate.model.base.IModelNode#getIncomingConnections()
	 * @see #getIModelNode()
	 * @generated
	 */
	EReference getIModelNode_IncomingConnections();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.base.ITracingElement <em>ITracing Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ITracing Element</em>'.
	 * @see com.specmate.model.base.ITracingElement
	 * @generated
	 */
	EClass getITracingElement();

	/**
	 * Returns the meta object for the reference list '{@link com.specmate.model.base.ITracingElement#getTracesTo <em>Traces To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Traces To</em>'.
	 * @see com.specmate.model.base.ITracingElement#getTracesTo()
	 * @see #getITracingElement()
	 * @generated
	 */
	EReference getITracingElement_TracesTo();

	/**
	 * Returns the meta object for the reference list '{@link com.specmate.model.base.ITracingElement#getTracesFrom <em>Traces From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Traces From</em>'.
	 * @see com.specmate.model.base.ITracingElement#getTracesFrom()
	 * @see #getITracingElement()
	 * @generated
	 */
	EReference getITracingElement_TracesFrom();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BaseFactory getBaseFactory();

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
		 * The meta object literal for the '{@link com.specmate.model.base.INamed <em>INamed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.INamed
		 * @see com.specmate.model.base.impl.BasePackageImpl#getINamed()
		 * @generated
		 */
		EClass INAMED = eINSTANCE.getINamed();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INAMED__NAME = eINSTANCE.getINamed_Name();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.IDescribed <em>IDescribed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.IDescribed
		 * @see com.specmate.model.base.impl.BasePackageImpl#getIDescribed()
		 * @generated
		 */
		EClass IDESCRIBED = eINSTANCE.getIDescribed();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IDESCRIBED__DESCRIPTION = eINSTANCE.getIDescribed_Description();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.IID <em>IID</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.IID
		 * @see com.specmate.model.base.impl.BasePackageImpl#getIID()
		 * @generated
		 */
		EClass IID = eINSTANCE.getIID();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IID__ID = eINSTANCE.getIID_Id();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.IContentElement <em>IContent Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.IContentElement
		 * @see com.specmate.model.base.impl.BasePackageImpl#getIContentElement()
		 * @generated
		 */
		EClass ICONTENT_ELEMENT = eINSTANCE.getIContentElement();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.IContainer <em>IContainer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.IContainer
		 * @see com.specmate.model.base.impl.BasePackageImpl#getIContainer()
		 * @generated
		 */
		EClass ICONTAINER = eINSTANCE.getIContainer();

		/**
		 * The meta object literal for the '<em><b>Contents</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ICONTAINER__CONTENTS = eINSTANCE.getIContainer_Contents();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.ISpecmateModelObject <em>ISpecmate Model Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.ISpecmateModelObject
		 * @see com.specmate.model.base.impl.BasePackageImpl#getISpecmateModelObject()
		 * @generated
		 */
		EClass ISPECMATE_MODEL_OBJECT = eINSTANCE.getISpecmateModelObject();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.impl.FolderImpl
		 * @see com.specmate.model.base.impl.BasePackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.IPositionable <em>IPositionable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.IPositionable
		 * @see com.specmate.model.base.impl.BasePackageImpl#getIPositionable()
		 * @generated
		 */
		EClass IPOSITIONABLE = eINSTANCE.getIPositionable();

		/**
		 * The meta object literal for the '<em><b>Position</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IPOSITIONABLE__POSITION = eINSTANCE.getIPositionable_Position();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.impl.IExternalImpl <em>IExternal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.impl.IExternalImpl
		 * @see com.specmate.model.base.impl.BasePackageImpl#getIExternal()
		 * @generated
		 */
		EClass IEXTERNAL = eINSTANCE.getIExternal();

		/**
		 * The meta object literal for the '<em><b>Ext Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IEXTERNAL__EXT_ID = eINSTANCE.getIExternal_ExtId();

		/**
		 * The meta object literal for the '<em><b>Ext Id2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IEXTERNAL__EXT_ID2 = eINSTANCE.getIExternal_ExtId2();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IEXTERNAL__SOURCE = eINSTANCE.getIExternal_Source();

		/**
		 * The meta object literal for the '<em><b>Live</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IEXTERNAL__LIVE = eINSTANCE.getIExternal_Live();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.ISpecmatePositionableModelObject <em>ISpecmate Positionable Model Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.ISpecmatePositionableModelObject
		 * @see com.specmate.model.base.impl.BasePackageImpl#getISpecmatePositionableModelObject()
		 * @generated
		 */
		EClass ISPECMATE_POSITIONABLE_MODEL_OBJECT = eINSTANCE.getISpecmatePositionableModelObject();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ISPECMATE_POSITIONABLE_MODEL_OBJECT__X = eINSTANCE.getISpecmatePositionableModelObject_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ISPECMATE_POSITIONABLE_MODEL_OBJECT__Y = eINSTANCE.getISpecmatePositionableModelObject_Y();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.impl.IModelConnectionImpl <em>IModel Connection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.impl.IModelConnectionImpl
		 * @see com.specmate.model.base.impl.BasePackageImpl#getIModelConnection()
		 * @generated
		 */
		EClass IMODEL_CONNECTION = eINSTANCE.getIModelConnection();

		/**
		 * The meta object literal for the '<em><b>Source</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMODEL_CONNECTION__SOURCE = eINSTANCE.getIModelConnection_Source();

		/**
		 * The meta object literal for the '<em><b>Target</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMODEL_CONNECTION__TARGET = eINSTANCE.getIModelConnection_Target();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.impl.IModelNodeImpl <em>IModel Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.impl.IModelNodeImpl
		 * @see com.specmate.model.base.impl.BasePackageImpl#getIModelNode()
		 * @generated
		 */
		EClass IMODEL_NODE = eINSTANCE.getIModelNode();

		/**
		 * The meta object literal for the '<em><b>Outgoing Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMODEL_NODE__OUTGOING_CONNECTIONS = eINSTANCE.getIModelNode_OutgoingConnections();

		/**
		 * The meta object literal for the '<em><b>Incoming Connections</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IMODEL_NODE__INCOMING_CONNECTIONS = eINSTANCE.getIModelNode_IncomingConnections();

		/**
		 * The meta object literal for the '{@link com.specmate.model.base.impl.ITracingElementImpl <em>ITracing Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.base.impl.ITracingElementImpl
		 * @see com.specmate.model.base.impl.BasePackageImpl#getITracingElement()
		 * @generated
		 */
		EClass ITRACING_ELEMENT = eINSTANCE.getITracingElement();

		/**
		 * The meta object literal for the '<em><b>Traces To</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITRACING_ELEMENT__TRACES_TO = eINSTANCE.getITracingElement_TracesTo();

		/**
		 * The meta object literal for the '<em><b>Traces From</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ITRACING_ELEMENT__TRACES_FROM = eINSTANCE.getITracingElement_TracesFrom();

	}

} //BasePackage
