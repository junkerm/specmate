/**
 */
package com.specmate.model.administration.impl;

import com.specmate.model.administration.AdministrationFactory;
import com.specmate.model.administration.AdministrationPackage;
import com.specmate.model.administration.ErrorCode;
import com.specmate.model.administration.ProblemDetail;
import com.specmate.model.administration.Status;

import com.specmate.model.base.BasePackage;

import com.specmate.model.base.impl.BasePackageImpl;

import com.specmate.model.batch.BatchPackage;
import com.specmate.model.batch.impl.BatchPackageImpl;
import com.specmate.model.history.HistoryPackage;

import com.specmate.model.history.impl.HistoryPackageImpl;

import com.specmate.model.processes.ProcessesPackage;

import com.specmate.model.processes.impl.ProcessesPackageImpl;

import com.specmate.model.requirements.RequirementsPackage;

import com.specmate.model.requirements.impl.RequirementsPackageImpl;

import com.specmate.model.testspecification.TestspecificationPackage;

import com.specmate.model.testspecification.impl.TestspecificationPackageImpl;

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
public class AdministrationPackageImpl extends EPackageImpl implements AdministrationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass statusEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass problemDetailEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum errorCodeEEnum = null;

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
	 * @see com.specmate.model.administration.AdministrationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private AdministrationPackageImpl() {
		super(eNS_URI, AdministrationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link AdministrationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static AdministrationPackage init() {
		if (isInited) return (AdministrationPackage)EPackage.Registry.INSTANCE.getEPackage(AdministrationPackage.eNS_URI);

		// Obtain or create and register package
		AdministrationPackageImpl theAdministrationPackage = (AdministrationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof AdministrationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new AdministrationPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		BasePackageImpl theBasePackage = (BasePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) instanceof BasePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) : BasePackage.eINSTANCE);
		RequirementsPackageImpl theRequirementsPackage = (RequirementsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RequirementsPackage.eNS_URI) instanceof RequirementsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RequirementsPackage.eNS_URI) : RequirementsPackage.eINSTANCE);
		TestspecificationPackageImpl theTestspecificationPackage = (TestspecificationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TestspecificationPackage.eNS_URI) instanceof TestspecificationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TestspecificationPackage.eNS_URI) : TestspecificationPackage.eINSTANCE);
		ProcessesPackageImpl theProcessesPackage = (ProcessesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProcessesPackage.eNS_URI) instanceof ProcessesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProcessesPackage.eNS_URI) : ProcessesPackage.eINSTANCE);
		HistoryPackageImpl theHistoryPackage = (HistoryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(HistoryPackage.eNS_URI) instanceof HistoryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(HistoryPackage.eNS_URI) : HistoryPackage.eINSTANCE);
		BatchPackageImpl theBatchPackage = (BatchPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BatchPackage.eNS_URI) instanceof BatchPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BatchPackage.eNS_URI) : BatchPackage.eINSTANCE);

		// Create package meta-data objects
		theAdministrationPackage.createPackageContents();
		theBasePackage.createPackageContents();
		theRequirementsPackage.createPackageContents();
		theTestspecificationPackage.createPackageContents();
		theProcessesPackage.createPackageContents();
		theHistoryPackage.createPackageContents();
		theBatchPackage.createPackageContents();

		// Initialize created meta-data
		theAdministrationPackage.initializePackageContents();
		theBasePackage.initializePackageContents();
		theRequirementsPackage.initializePackageContents();
		theTestspecificationPackage.initializePackageContents();
		theProcessesPackage.initializePackageContents();
		theHistoryPackage.initializePackageContents();
		theBatchPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theAdministrationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(AdministrationPackage.eNS_URI, theAdministrationPackage);
		return theAdministrationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStatus() {
		return statusEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStatus_Value() {
		return (EAttribute)statusEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProblemDetail() {
		return problemDetailEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProblemDetail_Ecode() {
		return (EAttribute)problemDetailEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProblemDetail_Status() {
		return (EAttribute)problemDetailEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProblemDetail_Detail() {
		return (EAttribute)problemDetailEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProblemDetail_Instance() {
		return (EAttribute)problemDetailEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getErrorCode() {
		return errorCodeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AdministrationFactory getAdministrationFactory() {
		return (AdministrationFactory)getEFactoryInstance();
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
		statusEClass = createEClass(STATUS);
		createEAttribute(statusEClass, STATUS__VALUE);

		problemDetailEClass = createEClass(PROBLEM_DETAIL);
		createEAttribute(problemDetailEClass, PROBLEM_DETAIL__ECODE);
		createEAttribute(problemDetailEClass, PROBLEM_DETAIL__STATUS);
		createEAttribute(problemDetailEClass, PROBLEM_DETAIL__DETAIL);
		createEAttribute(problemDetailEClass, PROBLEM_DETAIL__INSTANCE);

		// Create enums
		errorCodeEEnum = createEEnum(ERROR_CODE);
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
		initEClass(statusEClass, Status.class, "Status", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStatus_Value(), ecorePackage.getEString(), "value", null, 0, 1, Status.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(problemDetailEClass, ProblemDetail.class, "ProblemDetail", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProblemDetail_Ecode(), this.getErrorCode(), "ecode", null, 0, 1, ProblemDetail.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProblemDetail_Status(), ecorePackage.getEString(), "status", null, 0, 1, ProblemDetail.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProblemDetail_Detail(), ecorePackage.getEString(), "detail", null, 0, 1, ProblemDetail.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProblemDetail_Instance(), ecorePackage.getEString(), "instance", null, 0, 1, ProblemDetail.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(errorCodeEEnum, ErrorCode.class, "ErrorCode");
		addEEnumLiteral(errorCodeEEnum, ErrorCode.NO_SUCH_SERVICE);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.METHOD_NOT_ALLOWED);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.IN_MAINTENANCE_MODE);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.INVALID_DATA);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.NO_AUTHORIZATION);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.INTERNAL_PROBLEM);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.USER_SESSION);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.CONFIGURATION);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.PERSISTENCY);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.MIGRATION);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.SERALIZATION);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.REST_SERVICE);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.SCHEDULER);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.HP_PROXY);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.JIRA);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.METRICS);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.SEARCH);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.TESTGENERATION);
		addEEnumLiteral(errorCodeEEnum, ErrorCode.TRELLO);

		// Create resource
		createResource(eNS_URI);
	}

} //AdministrationPackageImpl
