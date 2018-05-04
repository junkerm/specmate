/**
 */
package com.specmate.migration.test.attributerenamed.testmodel.base;

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
 * @see com.specmate.migration.test.attributerenamed.testmodel.base.BaseFactory
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
	String eNS_URI = "http://specmate.com/1/testmodel/base";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.testmodel.base";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BasePackage eINSTANCE = com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IID <em>IID</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IID
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getIID()
	 * @generated
	 */
	int IID = 0;

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
	 * The meta object id for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IContentElement <em>IContent Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IContentElement
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getIContentElement()
	 * @generated
	 */
	int ICONTENT_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT__ID = IID__ID;

	/**
	 * The number of structural features of the '<em>IContent Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT_FEATURE_COUNT = IID_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>IContent Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT_OPERATION_COUNT = IID_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IContainer <em>IContainer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IContainer
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getIContainer()
	 * @generated
	 */
	int ICONTAINER = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER__ID = ICONTENT_ELEMENT__ID;

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
	 * The meta object id for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.FolderImpl
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__ID = ICONTAINER__ID;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__CONTENTS = ICONTAINER__CONTENTS;

	/**
	 * The number of structural features of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_FEATURE_COUNT = ICONTAINER_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Folder</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER_OPERATION_COUNT = ICONTAINER_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.ITestable <em>ITestable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.ITestable
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getITestable()
	 * @generated
	 */
	int ITESTABLE = 4;

	/**
	 * The feature id for the '<em><b>Istested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITESTABLE__ISTESTED = 0;

	/**
	 * The number of structural features of the '<em>ITestable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITESTABLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>ITestable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITESTABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IModifiable <em>IModifiable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IModifiable
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getIModifiable()
	 * @generated
	 */
	int IMODIFIABLE = 5;

	/**
	 * The feature id for the '<em><b>Istested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODIFIABLE__ISTESTED = ITESTABLE__ISTESTED;

	/**
	 * The number of structural features of the '<em>IModifiable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODIFIABLE_FEATURE_COUNT = ITESTABLE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>IModifiable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODIFIABLE_OPERATION_COUNT = ITESTABLE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IID <em>IID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IID</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IID
	 * @generated
	 */
	EClass getIID();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IID#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IID#getId()
	 * @see #getIID()
	 * @generated
	 */
	EAttribute getIID_Id();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IContentElement <em>IContent Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IContent Element</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IContentElement
	 * @generated
	 */
	EClass getIContentElement();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IContainer <em>IContainer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IContainer</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IContainer
	 * @generated
	 */
	EClass getIContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IContainer#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IContainer#getContents()
	 * @see #getIContainer()
	 * @generated
	 */
	EReference getIContainer_Contents();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.attributerenamed.testmodel.base.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.attributerenamed.testmodel.base.ITestable <em>ITestable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ITestable</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.ITestable
	 * @generated
	 */
	EClass getITestable();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.attributerenamed.testmodel.base.ITestable#isIstested <em>Istested</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Istested</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.ITestable#isIstested()
	 * @see #getITestable()
	 * @generated
	 */
	EAttribute getITestable_Istested();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IModifiable <em>IModifiable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IModifiable</em>'.
	 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IModifiable
	 * @generated
	 */
	EClass getIModifiable();

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
		 * The meta object literal for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IID <em>IID</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IID
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getIID()
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
		 * The meta object literal for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IContentElement <em>IContent Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IContentElement
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getIContentElement()
		 * @generated
		 */
		EClass ICONTENT_ELEMENT = eINSTANCE.getIContentElement();

		/**
		 * The meta object literal for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IContainer <em>IContainer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IContainer
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getIContainer()
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
		 * The meta object literal for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.FolderImpl
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.ITestable <em>ITestable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.ITestable
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getITestable()
		 * @generated
		 */
		EClass ITESTABLE = eINSTANCE.getITestable();

		/**
		 * The meta object literal for the '<em><b>Istested</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITESTABLE__ISTESTED = eINSTANCE.getITestable_Istested();

		/**
		 * The meta object literal for the '{@link com.specmate.migration.test.attributerenamed.testmodel.base.IModifiable <em>IModifiable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.IModifiable
		 * @see com.specmate.migration.test.attributerenamed.testmodel.base.impl.BasePackageImpl#getIModifiable()
		 * @generated
		 */
		EClass IMODIFIABLE = eINSTANCE.getIModifiable();

	}

} //BasePackage
