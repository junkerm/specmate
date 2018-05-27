/**
 */
package com.specmate.usermodel.impl;

import com.specmate.usermodel.AccessRights;
import com.specmate.usermodel.User;
import com.specmate.usermodel.UserSession;
import com.specmate.usermodel.UsermodelFactory;
import com.specmate.usermodel.UsermodelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class UsermodelPackageImpl extends EPackageImpl implements UsermodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userSessionEClass = null;
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum accessRightsEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see com.specmate.usermodel.UsermodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private UsermodelPackageImpl() {
		super(eNS_URI, UsermodelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link UsermodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static UsermodelPackage init() {
		if (isInited) return (UsermodelPackage)EPackage.Registry.INSTANCE.getEPackage(UsermodelPackage.eNS_URI);

		// Obtain or create and register package
		UsermodelPackageImpl theUsermodelPackage = (UsermodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof UsermodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new UsermodelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theUsermodelPackage.createPackageContents();

		// Initialize created meta-data
		theUsermodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theUsermodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(UsermodelPackage.eNS_URI, theUsermodelPackage);
		return theUsermodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUser() {
		return userEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_AllowedUrls() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_UserName() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_PassWord() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_ProjectName() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserSession() {
		return userSessionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserSession_Id() {
		return (EAttribute)userSessionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserSession_AllowedPathPattern() {
		return (EAttribute)userSessionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserSession_LastActive() {
		return (EAttribute)userSessionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserSession_PPMRights() {
		return (EAttribute)userSessionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserSession_ALMRights() {
		return (EAttribute)userSessionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAccessRights() {
		return accessRightsEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UsermodelFactory getUsermodelFactory() {
		return (UsermodelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		userEClass = createEClass(USER);
		createEAttribute(userEClass, USER__ALLOWED_URLS);
		createEAttribute(userEClass, USER__USER_NAME);
		createEAttribute(userEClass, USER__PASS_WORD);
		createEAttribute(userEClass, USER__PROJECT_NAME);

		userSessionEClass = createEClass(USER_SESSION);
		createEAttribute(userSessionEClass, USER_SESSION__ID);
		createEAttribute(userSessionEClass, USER_SESSION__ALLOWED_PATH_PATTERN);
		createEAttribute(userSessionEClass, USER_SESSION__LAST_ACTIVE);
		createEAttribute(userSessionEClass, USER_SESSION__PPM_RIGHTS);
		createEAttribute(userSessionEClass, USER_SESSION__ALM_RIGHTS);

		// Create enums
		accessRightsEEnum = createEEnum(ACCESS_RIGHTS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes

		// Initialize classes, features, and operations; add parameters
		initEClass(userEClass, User.class, "User", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUser_AllowedUrls(), ecorePackage.getEString(), "allowedUrls", null, 0, -1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_UserName(), ecorePackage.getEString(), "userName", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_PassWord(), ecorePackage.getEString(), "passWord", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_ProjectName(), ecorePackage.getEString(), "projectName", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userSessionEClass, UserSession.class, "UserSession", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUserSession_Id(), ecorePackage.getEString(), "id", null, 0, 1, UserSession.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUserSession_AllowedPathPattern(), ecorePackage.getEString(), "allowedPathPattern", null, 0, 1, UserSession.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUserSession_LastActive(), ecorePackage.getELong(), "lastActive", null, 0, 1, UserSession.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUserSession_PPMRights(), this.getAccessRights(), "PPMRights", null, 0, 1, UserSession.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUserSession_ALMRights(), this.getAccessRights(), "ALMRights", null, 0, 1, UserSession.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(accessRightsEEnum, AccessRights.class, "AccessRights");
		addEEnumLiteral(accessRightsEEnum, AccessRights.NONE);
		addEEnumLiteral(accessRightsEEnum, AccessRights.READ);
		addEEnumLiteral(accessRightsEEnum, AccessRights.WRITE);
		addEEnumLiteral(accessRightsEEnum, AccessRights.ALL);

		// Create resource
		createResource(eNS_URI);
	}

} //UsermodelPackageImpl
