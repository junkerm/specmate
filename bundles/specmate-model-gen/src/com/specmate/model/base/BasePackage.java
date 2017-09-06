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
	String eNS_URI = "http://specmate.com/20170209/model/base";

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
	 * The number of structural features of the '<em>ISpecmate Model Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ISPECMATE_MODEL_OBJECT_FEATURE_COUNT = ICONTAINER_FEATURE_COUNT + 0;

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
	 * The meta object id for the '{@link com.specmate.model.base.impl.IExternalImpl <em>IExternal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.base.impl.IExternalImpl
	 * @see com.specmate.model.base.impl.BasePackageImpl#getIExternal()
	 * @generated
	 */
	int IEXTERNAL = 7;

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
	 * The number of structural features of the '<em>IExternal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXTERNAL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>IExternal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IEXTERNAL_OPERATION_COUNT = 0;


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

	}

} //BasePackage
