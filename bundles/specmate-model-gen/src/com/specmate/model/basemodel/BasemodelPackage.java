/**
 */
package com.specmate.model.basemodel;

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
 * @see com.specmate.model.basemodel.BasemodelFactory
 * @model kind="package"
 * @generated
 */
public interface BasemodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "basemodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20151203_01/model/basemodel";

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
	BasemodelPackage eINSTANCE = com.specmate.model.basemodel.impl.BasemodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.model.basemodel.INamed <em>INamed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.INamed
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getINamed()
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
	 * The meta object id for the '{@link com.specmate.model.basemodel.IDescribed <em>IDescribed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.IDescribed
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIDescribed()
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
	 * The meta object id for the '{@link com.specmate.model.basemodel.IID <em>IID</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.IID
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIID()
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
	 * The meta object id for the '{@link com.specmate.model.basemodel.IContentElement <em>IContent Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.IContentElement
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIContentElement()
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
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT__ANNOTATIONS = IID_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>IContent Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT_FEATURE_COUNT = IID_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>IContent Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT_OPERATION_COUNT = IID_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.basemodel.impl.IUIInfoImpl <em>IUI Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.impl.IUIInfoImpl
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIUIInfo()
	 * @generated
	 */
	int IUI_INFO = 4;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_INFO__X = 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_INFO__Y = 1;

	/**
	 * The number of structural features of the '<em>IUI Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_INFO_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>IUI Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.basemodel.IUIContentElement <em>IUI Content Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.IUIContentElement
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIUIContentElement()
	 * @generated
	 */
	int IUI_CONTENT_ELEMENT = 5;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_CONTENT_ELEMENT__ID = ICONTENT_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_CONTENT_ELEMENT__NAME = ICONTENT_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_CONTENT_ELEMENT__DESCRIPTION = ICONTENT_ELEMENT__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_CONTENT_ELEMENT__ANNOTATIONS = ICONTENT_ELEMENT__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_CONTENT_ELEMENT__X = ICONTENT_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_CONTENT_ELEMENT__Y = ICONTENT_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>IUI Content Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_CONTENT_ELEMENT_FEATURE_COUNT = ICONTENT_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>IUI Content Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IUI_CONTENT_ELEMENT_OPERATION_COUNT = ICONTENT_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.basemodel.IContainer <em>IContainer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.IContainer
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIContainer()
	 * @generated
	 */
	int ICONTAINER = 6;

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
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER__ANNOTATIONS = ICONTENT_ELEMENT__ANNOTATIONS;

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
	 * The meta object id for the '{@link com.specmate.model.basemodel.IAnnotationContainer <em>IAnnotation Container</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.IAnnotationContainer
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIAnnotationContainer()
	 * @generated
	 */
	int IANNOTATION_CONTAINER = 7;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IANNOTATION_CONTAINER__ANNOTATIONS = 0;

	/**
	 * The number of structural features of the '<em>IAnnotation Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IANNOTATION_CONTAINER_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>IAnnotation Container</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IANNOTATION_CONTAINER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.basemodel.IAnnotation <em>IAnnotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.IAnnotation
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIAnnotation()
	 * @generated
	 */
	int IANNOTATION = 8;

	/**
	 * The number of structural features of the '<em>IAnnotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IANNOTATION_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>IAnnotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IANNOTATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.basemodel.impl.UIAnnotationImpl <em>UI Annotation</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.impl.UIAnnotationImpl
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getUIAnnotation()
	 * @generated
	 */
	int UI_ANNOTATION = 9;

	/**
	 * The feature id for the '<em><b>X</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_ANNOTATION__X = IUI_INFO__X;

	/**
	 * The feature id for the '<em><b>Y</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_ANNOTATION__Y = IUI_INFO__Y;

	/**
	 * The number of structural features of the '<em>UI Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_ANNOTATION_FEATURE_COUNT = IUI_INFO_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>UI Annotation</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UI_ANNOTATION_OPERATION_COUNT = IUI_INFO_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.basemodel.impl.BaseModelNodeImpl <em>Base Model Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.basemodel.impl.BaseModelNodeImpl
	 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getBaseModelNode()
	 * @generated
	 */
	int BASE_MODEL_NODE = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MODEL_NODE__ID = ICONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MODEL_NODE__NAME = ICONTAINER__NAME;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MODEL_NODE__DESCRIPTION = ICONTAINER__DESCRIPTION;

	/**
	 * The feature id for the '<em><b>Annotations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MODEL_NODE__ANNOTATIONS = ICONTAINER__ANNOTATIONS;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MODEL_NODE__CONTENTS = ICONTAINER__CONTENTS;

	/**
	 * The number of structural features of the '<em>Base Model Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MODEL_NODE_FEATURE_COUNT = ICONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Base Model Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BASE_MODEL_NODE_OPERATION_COUNT = ICONTAINER_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.INamed <em>INamed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INamed</em>'.
	 * @see com.specmate.model.basemodel.INamed
	 * @generated
	 */
	EClass getINamed();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.basemodel.INamed#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.specmate.model.basemodel.INamed#getName()
	 * @see #getINamed()
	 * @generated
	 */
	EAttribute getINamed_Name();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.IDescribed <em>IDescribed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IDescribed</em>'.
	 * @see com.specmate.model.basemodel.IDescribed
	 * @generated
	 */
	EClass getIDescribed();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.basemodel.IDescribed#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see com.specmate.model.basemodel.IDescribed#getDescription()
	 * @see #getIDescribed()
	 * @generated
	 */
	EAttribute getIDescribed_Description();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.IID <em>IID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IID</em>'.
	 * @see com.specmate.model.basemodel.IID
	 * @generated
	 */
	EClass getIID();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.basemodel.IID#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.specmate.model.basemodel.IID#getId()
	 * @see #getIID()
	 * @generated
	 */
	EAttribute getIID_Id();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.IContentElement <em>IContent Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IContent Element</em>'.
	 * @see com.specmate.model.basemodel.IContentElement
	 * @generated
	 */
	EClass getIContentElement();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.IUIInfo <em>IUI Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IUI Info</em>'.
	 * @see com.specmate.model.basemodel.IUIInfo
	 * @generated
	 */
	EClass getIUIInfo();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.basemodel.IUIInfo#getX <em>X</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>X</em>'.
	 * @see com.specmate.model.basemodel.IUIInfo#getX()
	 * @see #getIUIInfo()
	 * @generated
	 */
	EAttribute getIUIInfo_X();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.basemodel.IUIInfo#getY <em>Y</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Y</em>'.
	 * @see com.specmate.model.basemodel.IUIInfo#getY()
	 * @see #getIUIInfo()
	 * @generated
	 */
	EAttribute getIUIInfo_Y();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.IUIContentElement <em>IUI Content Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IUI Content Element</em>'.
	 * @see com.specmate.model.basemodel.IUIContentElement
	 * @generated
	 */
	EClass getIUIContentElement();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.IContainer <em>IContainer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IContainer</em>'.
	 * @see com.specmate.model.basemodel.IContainer
	 * @generated
	 */
	EClass getIContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.specmate.model.basemodel.IContainer#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see com.specmate.model.basemodel.IContainer#getContents()
	 * @see #getIContainer()
	 * @generated
	 */
	EReference getIContainer_Contents();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.IAnnotationContainer <em>IAnnotation Container</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IAnnotation Container</em>'.
	 * @see com.specmate.model.basemodel.IAnnotationContainer
	 * @generated
	 */
	EClass getIAnnotationContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.specmate.model.basemodel.IAnnotationContainer#getAnnotations <em>Annotations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Annotations</em>'.
	 * @see com.specmate.model.basemodel.IAnnotationContainer#getAnnotations()
	 * @see #getIAnnotationContainer()
	 * @generated
	 */
	EReference getIAnnotationContainer_Annotations();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.IAnnotation <em>IAnnotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IAnnotation</em>'.
	 * @see com.specmate.model.basemodel.IAnnotation
	 * @generated
	 */
	EClass getIAnnotation();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.UIAnnotation <em>UI Annotation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>UI Annotation</em>'.
	 * @see com.specmate.model.basemodel.UIAnnotation
	 * @generated
	 */
	EClass getUIAnnotation();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.basemodel.BaseModelNode <em>Base Model Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Base Model Node</em>'.
	 * @see com.specmate.model.basemodel.BaseModelNode
	 * @generated
	 */
	EClass getBaseModelNode();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	BasemodelFactory getBasemodelFactory();

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
		 * The meta object literal for the '{@link com.specmate.model.basemodel.INamed <em>INamed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.INamed
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getINamed()
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
		 * The meta object literal for the '{@link com.specmate.model.basemodel.IDescribed <em>IDescribed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.IDescribed
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIDescribed()
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
		 * The meta object literal for the '{@link com.specmate.model.basemodel.IID <em>IID</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.IID
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIID()
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
		 * The meta object literal for the '{@link com.specmate.model.basemodel.IContentElement <em>IContent Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.IContentElement
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIContentElement()
		 * @generated
		 */
		EClass ICONTENT_ELEMENT = eINSTANCE.getIContentElement();

		/**
		 * The meta object literal for the '{@link com.specmate.model.basemodel.impl.IUIInfoImpl <em>IUI Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.impl.IUIInfoImpl
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIUIInfo()
		 * @generated
		 */
		EClass IUI_INFO = eINSTANCE.getIUIInfo();

		/**
		 * The meta object literal for the '<em><b>X</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IUI_INFO__X = eINSTANCE.getIUIInfo_X();

		/**
		 * The meta object literal for the '<em><b>Y</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IUI_INFO__Y = eINSTANCE.getIUIInfo_Y();

		/**
		 * The meta object literal for the '{@link com.specmate.model.basemodel.IUIContentElement <em>IUI Content Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.IUIContentElement
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIUIContentElement()
		 * @generated
		 */
		EClass IUI_CONTENT_ELEMENT = eINSTANCE.getIUIContentElement();

		/**
		 * The meta object literal for the '{@link com.specmate.model.basemodel.IContainer <em>IContainer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.IContainer
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIContainer()
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
		 * The meta object literal for the '{@link com.specmate.model.basemodel.IAnnotationContainer <em>IAnnotation Container</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.IAnnotationContainer
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIAnnotationContainer()
		 * @generated
		 */
		EClass IANNOTATION_CONTAINER = eINSTANCE.getIAnnotationContainer();

		/**
		 * The meta object literal for the '<em><b>Annotations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference IANNOTATION_CONTAINER__ANNOTATIONS = eINSTANCE.getIAnnotationContainer_Annotations();

		/**
		 * The meta object literal for the '{@link com.specmate.model.basemodel.IAnnotation <em>IAnnotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.IAnnotation
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getIAnnotation()
		 * @generated
		 */
		EClass IANNOTATION = eINSTANCE.getIAnnotation();

		/**
		 * The meta object literal for the '{@link com.specmate.model.basemodel.impl.UIAnnotationImpl <em>UI Annotation</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.impl.UIAnnotationImpl
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getUIAnnotation()
		 * @generated
		 */
		EClass UI_ANNOTATION = eINSTANCE.getUIAnnotation();

		/**
		 * The meta object literal for the '{@link com.specmate.model.basemodel.impl.BaseModelNodeImpl <em>Base Model Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.basemodel.impl.BaseModelNodeImpl
		 * @see com.specmate.model.basemodel.impl.BasemodelPackageImpl#getBaseModelNode()
		 * @generated
		 */
		EClass BASE_MODEL_NODE = eINSTANCE.getBaseModelNode();

	}

} //BasemodelPackage
