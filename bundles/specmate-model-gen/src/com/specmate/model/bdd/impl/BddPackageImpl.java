/**
 */
package com.specmate.model.bdd.impl;

import com.specmate.model.administration.AdministrationPackage;

import com.specmate.model.administration.impl.AdministrationPackageImpl;

import com.specmate.model.base.BasePackage;

import com.specmate.model.base.impl.BasePackageImpl;

import com.specmate.model.batch.BatchPackage;

import com.specmate.model.batch.impl.BatchPackageImpl;

import com.specmate.model.bdd.BDDConnection;
import com.specmate.model.bdd.BDDModel;
import com.specmate.model.bdd.BDDNoTerminalNode;
import com.specmate.model.bdd.BDDNode;
import com.specmate.model.bdd.BDDTerminalNode;
import com.specmate.model.bdd.BddFactory;
import com.specmate.model.bdd.BddPackage;

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
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BddPackageImpl extends EPackageImpl implements BddPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bddNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bddTerminalNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bddNoTerminalNodeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bddConnectionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass bddModelEClass = null;

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
	 * @see com.specmate.model.bdd.BddPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BddPackageImpl() {
		super(eNS_URI, BddFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BddPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BddPackage init() {
		if (isInited) return (BddPackage)EPackage.Registry.INSTANCE.getEPackage(BddPackage.eNS_URI);

		// Obtain or create and register package
		BddPackageImpl theBddPackage = (BddPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BddPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BddPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		BasePackageImpl theBasePackage = (BasePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) instanceof BasePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) : BasePackage.eINSTANCE);
		RequirementsPackageImpl theRequirementsPackage = (RequirementsPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(RequirementsPackage.eNS_URI) instanceof RequirementsPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(RequirementsPackage.eNS_URI) : RequirementsPackage.eINSTANCE);
		TestspecificationPackageImpl theTestspecificationPackage = (TestspecificationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TestspecificationPackage.eNS_URI) instanceof TestspecificationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TestspecificationPackage.eNS_URI) : TestspecificationPackage.eINSTANCE);
		ProcessesPackageImpl theProcessesPackage = (ProcessesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProcessesPackage.eNS_URI) instanceof ProcessesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProcessesPackage.eNS_URI) : ProcessesPackage.eINSTANCE);
		HistoryPackageImpl theHistoryPackage = (HistoryPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(HistoryPackage.eNS_URI) instanceof HistoryPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(HistoryPackage.eNS_URI) : HistoryPackage.eINSTANCE);
		AdministrationPackageImpl theAdministrationPackage = (AdministrationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(AdministrationPackage.eNS_URI) instanceof AdministrationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(AdministrationPackage.eNS_URI) : AdministrationPackage.eINSTANCE);
		BatchPackageImpl theBatchPackage = (BatchPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BatchPackage.eNS_URI) instanceof BatchPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BatchPackage.eNS_URI) : BatchPackage.eINSTANCE);

		// Create package meta-data objects
		theBddPackage.createPackageContents();
		theBasePackage.createPackageContents();
		theRequirementsPackage.createPackageContents();
		theTestspecificationPackage.createPackageContents();
		theProcessesPackage.createPackageContents();
		theHistoryPackage.createPackageContents();
		theAdministrationPackage.createPackageContents();
		theBatchPackage.createPackageContents();

		// Initialize created meta-data
		theBddPackage.initializePackageContents();
		theBasePackage.initializePackageContents();
		theRequirementsPackage.initializePackageContents();
		theTestspecificationPackage.initializePackageContents();
		theProcessesPackage.initializePackageContents();
		theHistoryPackage.initializePackageContents();
		theAdministrationPackage.initializePackageContents();
		theBatchPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBddPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BddPackage.eNS_URI, theBddPackage);
		return theBddPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBDDNode() {
		return bddNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBDDTerminalNode() {
		return bddTerminalNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBDDTerminalNode_Value() {
		return (EAttribute)bddTerminalNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBDDNoTerminalNode() {
		return bddNoTerminalNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBDDNoTerminalNode_Variable() {
		return (EAttribute)bddNoTerminalNodeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBDDNoTerminalNode_Condition() {
		return (EAttribute)bddNoTerminalNodeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBDDConnection() {
		return bddConnectionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBDDConnection_Negate() {
		return (EAttribute)bddConnectionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBDDModel() {
		return bddModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BddFactory getBddFactory() {
		return (BddFactory)getEFactoryInstance();
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
		bddNodeEClass = createEClass(BDD_NODE);

		bddTerminalNodeEClass = createEClass(BDD_TERMINAL_NODE);
		createEAttribute(bddTerminalNodeEClass, BDD_TERMINAL_NODE__VALUE);

		bddNoTerminalNodeEClass = createEClass(BDD_NO_TERMINAL_NODE);
		createEAttribute(bddNoTerminalNodeEClass, BDD_NO_TERMINAL_NODE__VARIABLE);
		createEAttribute(bddNoTerminalNodeEClass, BDD_NO_TERMINAL_NODE__CONDITION);

		bddConnectionEClass = createEClass(BDD_CONNECTION);
		createEAttribute(bddConnectionEClass, BDD_CONNECTION__NEGATE);

		bddModelEClass = createEClass(BDD_MODEL);
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
		bddNodeEClass.getESuperTypes().add(theBasePackage.getIModelNode());
		bddTerminalNodeEClass.getESuperTypes().add(this.getBDDNode());
		bddNoTerminalNodeEClass.getESuperTypes().add(this.getBDDNode());
		bddConnectionEClass.getESuperTypes().add(theBasePackage.getIModelConnection());
		bddModelEClass.getESuperTypes().add(theBasePackage.getISpecmateModelObject());

		// Initialize classes, features, and operations; add parameters
		initEClass(bddNodeEClass, BDDNode.class, "BDDNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(bddTerminalNodeEClass, BDDTerminalNode.class, "BDDTerminalNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBDDTerminalNode_Value(), ecorePackage.getEBoolean(), "value", null, 0, 1, BDDTerminalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bddNoTerminalNodeEClass, BDDNoTerminalNode.class, "BDDNoTerminalNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBDDNoTerminalNode_Variable(), ecorePackage.getEString(), "variable", null, 0, 1, BDDNoTerminalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBDDNoTerminalNode_Condition(), ecorePackage.getEString(), "condition", null, 0, 1, BDDNoTerminalNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bddConnectionEClass, BDDConnection.class, "BDDConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBDDConnection_Negate(), ecorePackage.getEBoolean(), "negate", null, 0, 1, BDDConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(bddModelEClass, BDDModel.class, "BDDModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //BddPackageImpl
