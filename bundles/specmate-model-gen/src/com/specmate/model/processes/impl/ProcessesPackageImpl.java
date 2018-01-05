/**
 */
package com.specmate.model.processes.impl;

import com.specmate.model.base.BasePackage;

import com.specmate.model.base.impl.BasePackageImpl;

import com.specmate.model.history.HistoryPackage;
import com.specmate.model.history.impl.HistoryPackageImpl;
import com.specmate.model.processes.ProcessConnection;
import com.specmate.model.processes.ProcessDecision;
import com.specmate.model.processes.ProcessEnd;
import com.specmate.model.processes.ProcessNode;
import com.specmate.model.processes.ProcessStart;
import com.specmate.model.processes.ProcessStep;
import com.specmate.model.processes.ProcessesFactory;
import com.specmate.model.processes.ProcessesPackage;

import com.specmate.model.requirements.RequirementsPackage;

import com.specmate.model.requirements.impl.RequirementsPackageImpl;

import com.specmate.model.testspecification.TestspecificationPackage;

import com.specmate.model.testspecification.impl.TestspecificationPackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ProcessesPackageImpl extends EPackageImpl implements ProcessesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processStepEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processDecisionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processConnectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processStartEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processEndEClass = null;

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
	 * @see com.specmate.model.processes.ProcessesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ProcessesPackageImpl() {
		super(eNS_URI, ProcessesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ProcessesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ProcessesPackage init() {
		if (isInited) return (ProcessesPackage)EPackage.Registry.INSTANCE.getEPackage(ProcessesPackage.eNS_URI);

		// Obtain or create and register package
		ProcessesPackageImpl theProcessesPackage = (ProcessesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ProcessesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ProcessesPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		BasePackageImpl theBasePackage = (BasePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) instanceof BasePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) : BasePackage.eINSTANCE);
		RequirementsPackageImpl theRequirementsPackage = (RequirementsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RequirementsPackage.eNS_URI) instanceof RequirementsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RequirementsPackage.eNS_URI) : RequirementsPackage.eINSTANCE);
		TestspecificationPackageImpl theTestspecificationPackage = (TestspecificationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TestspecificationPackage.eNS_URI) instanceof TestspecificationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TestspecificationPackage.eNS_URI) : TestspecificationPackage.eINSTANCE);
		HistoryPackageImpl theHistoryPackage = (HistoryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(HistoryPackage.eNS_URI) instanceof HistoryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(HistoryPackage.eNS_URI) : HistoryPackage.eINSTANCE);

		// Create package meta-data objects
		theProcessesPackage.createPackageContents();
		theBasePackage.createPackageContents();
		theRequirementsPackage.createPackageContents();
		theTestspecificationPackage.createPackageContents();
		theHistoryPackage.createPackageContents();

		// Initialize created meta-data
		theProcessesPackage.initializePackageContents();
		theBasePackage.initializePackageContents();
		theRequirementsPackage.initializePackageContents();
		theTestspecificationPackage.initializePackageContents();
		theHistoryPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theProcessesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ProcessesPackage.eNS_URI, theProcessesPackage);
		return theProcessesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcess() {
		return processEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessNode() {
		return processNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessStep() {
		return processStepEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessDecision() {
		return processDecisionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessConnection() {
		return processConnectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessConnection_Condition() {
		return (EAttribute)processConnectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessStart() {
		return processStartEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessEnd() {
		return processEndEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessesFactory getProcessesFactory() {
		return (ProcessesFactory)getEFactoryInstance();
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
		processEClass = createEClass(PROCESS);

		processNodeEClass = createEClass(PROCESS_NODE);

		processStepEClass = createEClass(PROCESS_STEP);

		processDecisionEClass = createEClass(PROCESS_DECISION);

		processConnectionEClass = createEClass(PROCESS_CONNECTION);
		createEAttribute(processConnectionEClass, PROCESS_CONNECTION__CONDITION);

		processStartEClass = createEClass(PROCESS_START);

		processEndEClass = createEClass(PROCESS_END);
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

		// Obtain other dependent packages
		BasePackage theBasePackage = (BasePackage)EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		processEClass.getESuperTypes().add(theBasePackage.getIContainer());
		processNodeEClass.getESuperTypes().add(theBasePackage.getIModelNode());
		processStepEClass.getESuperTypes().add(this.getProcessNode());
		processDecisionEClass.getESuperTypes().add(this.getProcessNode());
		processConnectionEClass.getESuperTypes().add(theBasePackage.getIModelConnection());
		processStartEClass.getESuperTypes().add(this.getProcessNode());
		processEndEClass.getESuperTypes().add(this.getProcessNode());

		// Initialize classes, features, and operations; add parameters
		initEClass(processEClass, com.specmate.model.processes.Process.class, "Process", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(processNodeEClass, ProcessNode.class, "ProcessNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(processStepEClass, ProcessStep.class, "ProcessStep", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(processDecisionEClass, ProcessDecision.class, "ProcessDecision", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(processConnectionEClass, ProcessConnection.class, "ProcessConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProcessConnection_Condition(), ecorePackage.getEString(), "condition", null, 0, 1, ProcessConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(processStartEClass, ProcessStart.class, "ProcessStart", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(processEndEClass, ProcessEnd.class, "ProcessEnd", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http://specmate.com/form_meta
		createForm_metaAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://specmate.com/form_meta</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createForm_metaAnnotations() {
		String source = "http://specmate.com/form_meta";	
		addAnnotation
		  (getProcessConnection_Condition(), 
		   source, 
		   new String[] {
			 "shortDesc", "Condition",
			 "longDesc", "The condition the variable has to fulfil",
			 "required", "false",
			 "type", "text",
			 "position", "2"
		   });
	}

} //ProcessesPackageImpl
