/**
 */
package com.specmate.migration.testmodel.baseline.base;

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
 * @see com.specmate.migration.testmodel.baseline.base.BaseFactory
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
	String eNS_URI = "http://specmate.com/20180302/baseline/testmodel/base";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.baseline.testmodel.base";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	BasePackage eINSTANCE = com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.migration.testmodel.baseline.base.INamed <em>INamed</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.testmodel.baseline.base.INamed
	 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getINamed()
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
	 * The meta object id for the '{@link com.specmate.migration.testmodel.baseline.base.IContentElement <em>IContent Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.testmodel.baseline.base.IContentElement
	 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getIContentElement()
	 * @generated
	 */
	int ICONTENT_ELEMENT = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT__NAME = INAMED__NAME;

	/**
	 * The number of structural features of the '<em>IContent Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT_FEATURE_COUNT = INAMED_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>IContent Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTENT_ELEMENT_OPERATION_COUNT = INAMED_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.migration.testmodel.baseline.base.IContainer <em>IContainer</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.testmodel.baseline.base.IContainer
	 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getIContainer()
	 * @generated
	 */
	int ICONTAINER = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ICONTAINER__NAME = ICONTENT_ELEMENT__NAME;

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
	 * The meta object id for the '{@link com.specmate.migration.testmodel.baseline.base.impl.FolderImpl <em>Folder</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.testmodel.baseline.base.impl.FolderImpl
	 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getFolder()
	 * @generated
	 */
	int FOLDER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FOLDER__NAME = ICONTAINER__NAME;

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
	 * The meta object id for the '{@link com.specmate.migration.testmodel.baseline.base.ITestable <em>ITestable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.testmodel.baseline.base.ITestable
	 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getITestable()
	 * @generated
	 */
	int ITESTABLE = 4;

	/**
	 * The feature id for the '<em><b>Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ITESTABLE__TESTED = 0;

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
	 * The meta object id for the '{@link com.specmate.migration.testmodel.baseline.base.IModifiable <em>IModifiable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.testmodel.baseline.base.IModifiable
	 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getIModifiable()
	 * @generated
	 */
	int IMODIFIABLE = 5;

	/**
	 * The feature id for the '<em><b>Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMODIFIABLE__TESTED = ITESTABLE__TESTED;

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
	 * Returns the meta object for class '{@link com.specmate.migration.testmodel.baseline.base.INamed <em>INamed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>INamed</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.INamed
	 * @generated
	 */
	EClass getINamed();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.testmodel.baseline.base.INamed#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.INamed#getName()
	 * @see #getINamed()
	 * @generated
	 */
	EAttribute getINamed_Name();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.testmodel.baseline.base.IContentElement <em>IContent Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IContent Element</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.IContentElement
	 * @generated
	 */
	EClass getIContentElement();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.testmodel.baseline.base.IContainer <em>IContainer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IContainer</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.IContainer
	 * @generated
	 */
	EClass getIContainer();

	/**
	 * Returns the meta object for the containment reference list '{@link com.specmate.migration.testmodel.baseline.base.IContainer#getContents <em>Contents</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Contents</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.IContainer#getContents()
	 * @see #getIContainer()
	 * @generated
	 */
	EReference getIContainer_Contents();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.testmodel.baseline.base.Folder <em>Folder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Folder</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.Folder
	 * @generated
	 */
	EClass getFolder();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.testmodel.baseline.base.ITestable <em>ITestable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>ITestable</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.ITestable
	 * @generated
	 */
	EClass getITestable();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.testmodel.baseline.base.ITestable#isTested <em>Tested</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Tested</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.ITestable#isTested()
	 * @see #getITestable()
	 * @generated
	 */
	EAttribute getITestable_Tested();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.testmodel.baseline.base.IModifiable <em>IModifiable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>IModifiable</em>'.
	 * @see com.specmate.migration.testmodel.baseline.base.IModifiable
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
		 * The meta object literal for the '{@link com.specmate.migration.testmodel.baseline.base.INamed <em>INamed</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.testmodel.baseline.base.INamed
		 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getINamed()
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
		 * The meta object literal for the '{@link com.specmate.migration.testmodel.baseline.base.IContentElement <em>IContent Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.testmodel.baseline.base.IContentElement
		 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getIContentElement()
		 * @generated
		 */
		EClass ICONTENT_ELEMENT = eINSTANCE.getIContentElement();

		/**
		 * The meta object literal for the '{@link com.specmate.migration.testmodel.baseline.base.IContainer <em>IContainer</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.testmodel.baseline.base.IContainer
		 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getIContainer()
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
		 * The meta object literal for the '{@link com.specmate.migration.testmodel.baseline.base.impl.FolderImpl <em>Folder</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.testmodel.baseline.base.impl.FolderImpl
		 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getFolder()
		 * @generated
		 */
		EClass FOLDER = eINSTANCE.getFolder();

		/**
		 * The meta object literal for the '{@link com.specmate.migration.testmodel.baseline.base.ITestable <em>ITestable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.testmodel.baseline.base.ITestable
		 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getITestable()
		 * @generated
		 */
		EClass ITESTABLE = eINSTANCE.getITestable();

		/**
		 * The meta object literal for the '<em><b>Tested</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ITESTABLE__TESTED = eINSTANCE.getITestable_Tested();

		/**
		 * The meta object literal for the '{@link com.specmate.migration.testmodel.baseline.base.IModifiable <em>IModifiable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.testmodel.baseline.base.IModifiable
		 * @see com.specmate.migration.testmodel.baseline.base.impl.BasePackageImpl#getIModifiable()
		 * @generated
		 */
		EClass IMODIFIABLE = eINSTANCE.getIModifiable();

	}

} //BasePackage
