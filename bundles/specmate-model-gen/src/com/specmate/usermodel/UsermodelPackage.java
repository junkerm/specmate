/**
 */
package com.specmate.usermodel;

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
 * @see com.specmate.usermodel.UsermodelFactory
 * @model kind="package"
 * @generated
 */
public interface UsermodelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "usermodel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20180925/model/user";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.model";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	UsermodelPackage eINSTANCE = com.specmate.usermodel.impl.UsermodelPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.usermodel.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.usermodel.impl.UserImpl
	 * @see com.specmate.usermodel.impl.UsermodelPackageImpl#getUser()
	 * @generated
	 */
	int USER = 0;

	/**
	 * The feature id for the '<em><b>Allowed Urls</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__ALLOWED_URLS = 0;

	/**
	 * The feature id for the '<em><b>User Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__USER_NAME = 1;

	/**
	 * The feature id for the '<em><b>Pass Word</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__PASS_WORD = 2;

	/**
	 * The feature id for the '<em><b>Project Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__PROJECT_NAME = 3;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.usermodel.impl.UserSessionImpl <em>User Session</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.usermodel.impl.UserSessionImpl
	 * @see com.specmate.usermodel.impl.UsermodelPackageImpl#getUserSession()
	 * @generated
	 */
	int USER_SESSION = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION__ID = 0;

	/**
	 * The feature id for the '<em><b>Allowed Path Pattern</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION__ALLOWED_PATH_PATTERN = 1;

	/**
	 * The feature id for the '<em><b>User Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION__USER_NAME = 2;

	/**
	 * The feature id for the '<em><b>Last Active</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION__LAST_ACTIVE = 3;

	/**
	 * The feature id for the '<em><b>Source System</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION__SOURCE_SYSTEM = 4;

	/**
	 * The feature id for the '<em><b>Target System</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION__TARGET_SYSTEM = 5;

	/**
	 * The feature id for the '<em><b>Library Folders</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION__LIBRARY_FOLDERS = 6;

	/**
	 * The number of structural features of the '<em>User Session</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>User Session</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_SESSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.usermodel.AccessRights <em>Access Rights</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.usermodel.AccessRights
	 * @see com.specmate.usermodel.impl.UsermodelPackageImpl#getAccessRights()
	 * @generated
	 */
	int ACCESS_RIGHTS = 2;


	/**
	 * Returns the meta object for class '{@link com.specmate.usermodel.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see com.specmate.usermodel.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute list '{@link com.specmate.usermodel.User#getAllowedUrls <em>Allowed Urls</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Allowed Urls</em>'.
	 * @see com.specmate.usermodel.User#getAllowedUrls()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_AllowedUrls();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.User#getUserName <em>User Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Name</em>'.
	 * @see com.specmate.usermodel.User#getUserName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_UserName();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.User#getPassWord <em>Pass Word</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Pass Word</em>'.
	 * @see com.specmate.usermodel.User#getPassWord()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_PassWord();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.User#getProjectName <em>Project Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Project Name</em>'.
	 * @see com.specmate.usermodel.User#getProjectName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_ProjectName();

	/**
	 * Returns the meta object for class '{@link com.specmate.usermodel.UserSession <em>User Session</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Session</em>'.
	 * @see com.specmate.usermodel.UserSession
	 * @generated
	 */
	EClass getUserSession();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.UserSession#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see com.specmate.usermodel.UserSession#getId()
	 * @see #getUserSession()
	 * @generated
	 */
	EAttribute getUserSession_Id();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.UserSession#getAllowedPathPattern <em>Allowed Path Pattern</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Allowed Path Pattern</em>'.
	 * @see com.specmate.usermodel.UserSession#getAllowedPathPattern()
	 * @see #getUserSession()
	 * @generated
	 */
	EAttribute getUserSession_AllowedPathPattern();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.UserSession#getUserName <em>User Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User Name</em>'.
	 * @see com.specmate.usermodel.UserSession#getUserName()
	 * @see #getUserSession()
	 * @generated
	 */
	EAttribute getUserSession_UserName();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.UserSession#getLastActive <em>Last Active</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Active</em>'.
	 * @see com.specmate.usermodel.UserSession#getLastActive()
	 * @see #getUserSession()
	 * @generated
	 */
	EAttribute getUserSession_LastActive();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.UserSession#getSourceSystem <em>Source System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Source System</em>'.
	 * @see com.specmate.usermodel.UserSession#getSourceSystem()
	 * @see #getUserSession()
	 * @generated
	 */
	EAttribute getUserSession_SourceSystem();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.usermodel.UserSession#getTargetSystem <em>Target System</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Target System</em>'.
	 * @see com.specmate.usermodel.UserSession#getTargetSystem()
	 * @see #getUserSession()
	 * @generated
	 */
	EAttribute getUserSession_TargetSystem();

	/**
	 * Returns the meta object for the attribute list '{@link com.specmate.usermodel.UserSession#getLibraryFolders <em>Library Folders</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Library Folders</em>'.
	 * @see com.specmate.usermodel.UserSession#getLibraryFolders()
	 * @see #getUserSession()
	 * @generated
	 */
	EAttribute getUserSession_LibraryFolders();

	/**
	 * Returns the meta object for enum '{@link com.specmate.usermodel.AccessRights <em>Access Rights</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Access Rights</em>'.
	 * @see com.specmate.usermodel.AccessRights
	 * @generated
	 */
	EEnum getAccessRights();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	UsermodelFactory getUsermodelFactory();

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
		 * The meta object literal for the '{@link com.specmate.usermodel.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.usermodel.impl.UserImpl
		 * @see com.specmate.usermodel.impl.UsermodelPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>Allowed Urls</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__ALLOWED_URLS = eINSTANCE.getUser_AllowedUrls();

		/**
		 * The meta object literal for the '<em><b>User Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__USER_NAME = eINSTANCE.getUser_UserName();

		/**
		 * The meta object literal for the '<em><b>Pass Word</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__PASS_WORD = eINSTANCE.getUser_PassWord();

		/**
		 * The meta object literal for the '<em><b>Project Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__PROJECT_NAME = eINSTANCE.getUser_ProjectName();

		/**
		 * The meta object literal for the '{@link com.specmate.usermodel.impl.UserSessionImpl <em>User Session</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.usermodel.impl.UserSessionImpl
		 * @see com.specmate.usermodel.impl.UsermodelPackageImpl#getUserSession()
		 * @generated
		 */
		EClass USER_SESSION = eINSTANCE.getUserSession();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_SESSION__ID = eINSTANCE.getUserSession_Id();

		/**
		 * The meta object literal for the '<em><b>Allowed Path Pattern</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_SESSION__ALLOWED_PATH_PATTERN = eINSTANCE.getUserSession_AllowedPathPattern();

		/**
		 * The meta object literal for the '<em><b>User Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_SESSION__USER_NAME = eINSTANCE.getUserSession_UserName();

		/**
		 * The meta object literal for the '<em><b>Last Active</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_SESSION__LAST_ACTIVE = eINSTANCE.getUserSession_LastActive();

		/**
		 * The meta object literal for the '<em><b>Source System</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_SESSION__SOURCE_SYSTEM = eINSTANCE.getUserSession_SourceSystem();

		/**
		 * The meta object literal for the '<em><b>Target System</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_SESSION__TARGET_SYSTEM = eINSTANCE.getUserSession_TargetSystem();

		/**
		 * The meta object literal for the '<em><b>Library Folders</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_SESSION__LIBRARY_FOLDERS = eINSTANCE.getUserSession_LibraryFolders();

		/**
		 * The meta object literal for the '{@link com.specmate.usermodel.AccessRights <em>Access Rights</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.usermodel.AccessRights
		 * @see com.specmate.usermodel.impl.UsermodelPackageImpl#getAccessRights()
		 * @generated
		 */
		EEnum ACCESS_RIGHTS = eINSTANCE.getAccessRights();

	}

} //UsermodelPackage
