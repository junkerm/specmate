/**
 */
package com.specmate.migration.test.objectadded.testmodel.artefact.impl;

import com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactFactory;
import com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage;
import com.specmate.migration.test.objectadded.testmodel.artefact.Diagram;
import com.specmate.migration.test.objectadded.testmodel.artefact.Document;
import com.specmate.migration.test.objectadded.testmodel.artefact.Sketch;

import com.specmate.migration.test.objectadded.testmodel.base.BasePackage;

import com.specmate.migration.test.objectadded.testmodel.base.impl.BasePackageImpl;

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
public class ArtefactPackageImpl extends EPackageImpl implements ArtefactPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass diagramEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass sketchEClass = null;

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
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ArtefactPackageImpl() {
		super(eNS_URI, ArtefactFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link ArtefactPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ArtefactPackage init() {
		if (isInited) return (ArtefactPackage)EPackage.Registry.INSTANCE.getEPackage(ArtefactPackage.eNS_URI);

		// Obtain or create and register package
		ArtefactPackageImpl theArtefactPackage = (ArtefactPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ArtefactPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ArtefactPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		BasePackageImpl theBasePackage = (BasePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) instanceof BasePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(BasePackage.eNS_URI) : BasePackage.eINSTANCE);

		// Create package meta-data objects
		theArtefactPackage.createPackageContents();
		theBasePackage.createPackageContents();

		// Initialize created meta-data
		theArtefactPackage.initializePackageContents();
		theBasePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theArtefactPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ArtefactPackage.eNS_URI, theArtefactPackage);
		return theArtefactPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDiagram() {
		return diagramEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocument() {
		return documentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocument_Length() {
		return (EAttribute)documentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocument_Owner() {
		return (EAttribute)documentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getSketch() {
		return sketchEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ByteVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ByteVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ByteVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ByteVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ByteVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ShortVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ShortVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ShortVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ShortVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_ShortVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_IntVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_IntVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_IntVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_IntVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_IntVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_CharVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_CharVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_CharVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_CharVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_CharVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_LongVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_LongVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_LongVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_LongVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_LongVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_FloatVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_FloatVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_FloatVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_FloatVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_FloatVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_DoubleVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_DoubleVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_DoubleVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_DoubleVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(33);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_DoubleVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(34);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_BooleanVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(35);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_BooleanVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(36);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_BooleanVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(37);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_BooleanVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(38);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_BooleanVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(39);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_StringVar1() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(40);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_StringVar2() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(41);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_StringVar3() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(42);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_StringVar4() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(43);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getSketch_StringVar5() {
		return (EAttribute)sketchEClass.getEStructuralFeatures().get(44);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ArtefactFactory getArtefactFactory() {
		return (ArtefactFactory)getEFactoryInstance();
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
		diagramEClass = createEClass(DIAGRAM);

		documentEClass = createEClass(DOCUMENT);
		createEAttribute(documentEClass, DOCUMENT__LENGTH);
		createEAttribute(documentEClass, DOCUMENT__OWNER);

		sketchEClass = createEClass(SKETCH);
		createEAttribute(sketchEClass, SKETCH__BYTE_VAR1);
		createEAttribute(sketchEClass, SKETCH__BYTE_VAR2);
		createEAttribute(sketchEClass, SKETCH__BYTE_VAR3);
		createEAttribute(sketchEClass, SKETCH__BYTE_VAR4);
		createEAttribute(sketchEClass, SKETCH__BYTE_VAR5);
		createEAttribute(sketchEClass, SKETCH__SHORT_VAR1);
		createEAttribute(sketchEClass, SKETCH__SHORT_VAR2);
		createEAttribute(sketchEClass, SKETCH__SHORT_VAR3);
		createEAttribute(sketchEClass, SKETCH__SHORT_VAR4);
		createEAttribute(sketchEClass, SKETCH__SHORT_VAR5);
		createEAttribute(sketchEClass, SKETCH__INT_VAR1);
		createEAttribute(sketchEClass, SKETCH__INT_VAR2);
		createEAttribute(sketchEClass, SKETCH__INT_VAR3);
		createEAttribute(sketchEClass, SKETCH__INT_VAR4);
		createEAttribute(sketchEClass, SKETCH__INT_VAR5);
		createEAttribute(sketchEClass, SKETCH__CHAR_VAR1);
		createEAttribute(sketchEClass, SKETCH__CHAR_VAR2);
		createEAttribute(sketchEClass, SKETCH__CHAR_VAR3);
		createEAttribute(sketchEClass, SKETCH__CHAR_VAR4);
		createEAttribute(sketchEClass, SKETCH__CHAR_VAR5);
		createEAttribute(sketchEClass, SKETCH__LONG_VAR1);
		createEAttribute(sketchEClass, SKETCH__LONG_VAR2);
		createEAttribute(sketchEClass, SKETCH__LONG_VAR3);
		createEAttribute(sketchEClass, SKETCH__LONG_VAR4);
		createEAttribute(sketchEClass, SKETCH__LONG_VAR5);
		createEAttribute(sketchEClass, SKETCH__FLOAT_VAR1);
		createEAttribute(sketchEClass, SKETCH__FLOAT_VAR2);
		createEAttribute(sketchEClass, SKETCH__FLOAT_VAR3);
		createEAttribute(sketchEClass, SKETCH__FLOAT_VAR4);
		createEAttribute(sketchEClass, SKETCH__FLOAT_VAR5);
		createEAttribute(sketchEClass, SKETCH__DOUBLE_VAR1);
		createEAttribute(sketchEClass, SKETCH__DOUBLE_VAR2);
		createEAttribute(sketchEClass, SKETCH__DOUBLE_VAR3);
		createEAttribute(sketchEClass, SKETCH__DOUBLE_VAR4);
		createEAttribute(sketchEClass, SKETCH__DOUBLE_VAR5);
		createEAttribute(sketchEClass, SKETCH__BOOLEAN_VAR1);
		createEAttribute(sketchEClass, SKETCH__BOOLEAN_VAR2);
		createEAttribute(sketchEClass, SKETCH__BOOLEAN_VAR3);
		createEAttribute(sketchEClass, SKETCH__BOOLEAN_VAR4);
		createEAttribute(sketchEClass, SKETCH__BOOLEAN_VAR5);
		createEAttribute(sketchEClass, SKETCH__STRING_VAR1);
		createEAttribute(sketchEClass, SKETCH__STRING_VAR2);
		createEAttribute(sketchEClass, SKETCH__STRING_VAR3);
		createEAttribute(sketchEClass, SKETCH__STRING_VAR4);
		createEAttribute(sketchEClass, SKETCH__STRING_VAR5);
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
		diagramEClass.getESuperTypes().add(theBasePackage.getIModifiable());
		diagramEClass.getESuperTypes().add(theBasePackage.getIContainer());
		documentEClass.getESuperTypes().add(theBasePackage.getIModifiable());
		documentEClass.getESuperTypes().add(theBasePackage.getIContainer());
		sketchEClass.getESuperTypes().add(theBasePackage.getIModifiable());
		sketchEClass.getESuperTypes().add(theBasePackage.getIContainer());

		// Initialize classes, features, and operations; add parameters
		initEClass(diagramEClass, Diagram.class, "Diagram", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(documentEClass, Document.class, "Document", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDocument_Length(), ecorePackage.getELong(), "length", null, 0, 1, Document.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocument_Owner(), ecorePackage.getEString(), "owner", null, 0, 1, Document.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(sketchEClass, Sketch.class, "Sketch", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getSketch_ByteVar1(), ecorePackage.getEByte(), "byteVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ByteVar2(), ecorePackage.getEByte(), "byteVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ByteVar3(), ecorePackage.getEByte(), "byteVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ByteVar4(), ecorePackage.getEByte(), "byteVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ByteVar5(), ecorePackage.getEByte(), "byteVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ShortVar1(), ecorePackage.getEShort(), "shortVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ShortVar2(), ecorePackage.getEShort(), "shortVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ShortVar3(), ecorePackage.getEShort(), "shortVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ShortVar4(), ecorePackage.getEShort(), "shortVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_ShortVar5(), ecorePackage.getEShort(), "shortVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_IntVar1(), ecorePackage.getEInt(), "intVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_IntVar2(), ecorePackage.getEInt(), "intVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_IntVar3(), ecorePackage.getEInt(), "intVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_IntVar4(), ecorePackage.getEInt(), "intVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_IntVar5(), ecorePackage.getEInt(), "intVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_CharVar1(), ecorePackage.getEChar(), "charVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_CharVar2(), ecorePackage.getEChar(), "charVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_CharVar3(), ecorePackage.getEChar(), "charVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_CharVar4(), ecorePackage.getEChar(), "charVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_CharVar5(), ecorePackage.getEChar(), "charVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_LongVar1(), ecorePackage.getELong(), "longVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_LongVar2(), ecorePackage.getELong(), "longVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_LongVar3(), ecorePackage.getELong(), "longVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_LongVar4(), ecorePackage.getELong(), "longVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_LongVar5(), ecorePackage.getELong(), "longVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_FloatVar1(), ecorePackage.getEFloat(), "floatVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_FloatVar2(), ecorePackage.getEFloat(), "floatVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_FloatVar3(), ecorePackage.getEFloat(), "floatVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_FloatVar4(), ecorePackage.getEFloat(), "floatVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_FloatVar5(), ecorePackage.getEFloat(), "floatVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_DoubleVar1(), ecorePackage.getEDouble(), "doubleVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_DoubleVar2(), ecorePackage.getEDouble(), "doubleVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_DoubleVar3(), ecorePackage.getEDouble(), "doubleVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_DoubleVar4(), ecorePackage.getEDouble(), "doubleVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_DoubleVar5(), ecorePackage.getEDouble(), "doubleVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_BooleanVar1(), ecorePackage.getEBoolean(), "booleanVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_BooleanVar2(), ecorePackage.getEBoolean(), "booleanVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_BooleanVar3(), ecorePackage.getEBoolean(), "booleanVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_BooleanVar4(), ecorePackage.getEBoolean(), "booleanVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_BooleanVar5(), ecorePackage.getEBoolean(), "booleanVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_StringVar1(), ecorePackage.getEString(), "stringVar1", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_StringVar2(), ecorePackage.getEString(), "stringVar2", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_StringVar3(), ecorePackage.getEString(), "stringVar3", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_StringVar4(), ecorePackage.getEString(), "stringVar4", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getSketch_StringVar5(), ecorePackage.getEString(), "stringVar5", null, 0, 1, Sketch.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ArtefactPackageImpl
