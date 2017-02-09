/**
 */
package com.specmate.model.basemodel.impl;

import com.specmate.model.basemodel.BaseModelNode;
import com.specmate.model.basemodel.BasemodelFactory;
import com.specmate.model.basemodel.BasemodelPackage;
import com.specmate.model.basemodel.IAnnotation;
import com.specmate.model.basemodel.IAnnotationContainer;
import com.specmate.model.basemodel.IContainer;
import com.specmate.model.basemodel.IContentElement;
import com.specmate.model.basemodel.IDescribed;
import com.specmate.model.basemodel.INamed;
import com.specmate.model.basemodel.IUIContentElement;
import com.specmate.model.basemodel.IUIInfo;
import com.specmate.model.basemodel.UIAnnotation;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class BasemodelPackageImpl extends EPackageImpl implements BasemodelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iNamedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iDescribedEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iidEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iContentElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iuiInfoEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iuiContentElementEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iAnnotationContainerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass iAnnotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uiAnnotationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass baseModelNodeEClass = null;

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
	 * @see com.specmate.model.basemodel.BasemodelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private BasemodelPackageImpl() {
		super(eNS_URI, BasemodelFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link BasemodelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static BasemodelPackage init() {
		if (isInited) return (BasemodelPackage)EPackage.Registry.INSTANCE.getEPackage(BasemodelPackage.eNS_URI);

		// Obtain or create and register package
		BasemodelPackageImpl theBasemodelPackage = (BasemodelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof BasemodelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new BasemodelPackageImpl());

		isInited = true;

		// Create package meta-data objects
		theBasemodelPackage.createPackageContents();

		// Initialize created meta-data
		theBasemodelPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theBasemodelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(BasemodelPackage.eNS_URI, theBasemodelPackage);
		return theBasemodelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getINamed() {
		return iNamedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getINamed_Name() {
		return (EAttribute)iNamedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIDescribed() {
		return iDescribedEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIDescribed_Description() {
		return (EAttribute)iDescribedEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIID() {
		return iidEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIID_Id() {
		return (EAttribute)iidEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIContentElement() {
		return iContentElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIUIInfo() {
		return iuiInfoEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIUIInfo_X() {
		return (EAttribute)iuiInfoEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIUIInfo_Y() {
		return (EAttribute)iuiInfoEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIUIContentElement() {
		return iuiContentElementEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIContainer() {
		return iContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIContainer_Contents() {
		return (EReference)iContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIAnnotationContainer() {
		return iAnnotationContainerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getIAnnotationContainer_Annotations() {
		return (EReference)iAnnotationContainerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIAnnotation() {
		return iAnnotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUIAnnotation() {
		return uiAnnotationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBaseModelNode() {
		return baseModelNodeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasemodelFactory getBasemodelFactory() {
		return (BasemodelFactory)getEFactoryInstance();
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
		iNamedEClass = createEClass(INAMED);
		createEAttribute(iNamedEClass, INAMED__NAME);

		iDescribedEClass = createEClass(IDESCRIBED);
		createEAttribute(iDescribedEClass, IDESCRIBED__DESCRIPTION);

		iidEClass = createEClass(IID);
		createEAttribute(iidEClass, IID__ID);

		iContentElementEClass = createEClass(ICONTENT_ELEMENT);

		iuiInfoEClass = createEClass(IUI_INFO);
		createEAttribute(iuiInfoEClass, IUI_INFO__X);
		createEAttribute(iuiInfoEClass, IUI_INFO__Y);

		iuiContentElementEClass = createEClass(IUI_CONTENT_ELEMENT);

		iContainerEClass = createEClass(ICONTAINER);
		createEReference(iContainerEClass, ICONTAINER__CONTENTS);

		iAnnotationContainerEClass = createEClass(IANNOTATION_CONTAINER);
		createEReference(iAnnotationContainerEClass, IANNOTATION_CONTAINER__ANNOTATIONS);

		iAnnotationEClass = createEClass(IANNOTATION);

		uiAnnotationEClass = createEClass(UI_ANNOTATION);

		baseModelNodeEClass = createEClass(BASE_MODEL_NODE);
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
		iContentElementEClass.getESuperTypes().add(this.getIID());
		iContentElementEClass.getESuperTypes().add(this.getINamed());
		iContentElementEClass.getESuperTypes().add(this.getIDescribed());
		iContentElementEClass.getESuperTypes().add(this.getIAnnotationContainer());
		iuiContentElementEClass.getESuperTypes().add(this.getIContentElement());
		iuiContentElementEClass.getESuperTypes().add(this.getIUIInfo());
		iContainerEClass.getESuperTypes().add(this.getIContentElement());
		uiAnnotationEClass.getESuperTypes().add(this.getIUIInfo());
		uiAnnotationEClass.getESuperTypes().add(this.getIAnnotation());
		baseModelNodeEClass.getESuperTypes().add(this.getIContainer());
		baseModelNodeEClass.getESuperTypes().add(this.getIAnnotationContainer());
		baseModelNodeEClass.getESuperTypes().add(this.getIContentElement());

		// Initialize classes, features, and operations; add parameters
		initEClass(iNamedEClass, INamed.class, "INamed", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getINamed_Name(), ecorePackage.getEString(), "name", null, 0, 1, INamed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iDescribedEClass, IDescribed.class, "IDescribed", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIDescribed_Description(), ecorePackage.getEString(), "description", null, 0, 1, IDescribed.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iidEClass, com.specmate.model.basemodel.IID.class, "IID", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIID_Id(), ecorePackage.getEString(), "id", null, 0, 1, com.specmate.model.basemodel.IID.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iContentElementEClass, IContentElement.class, "IContentElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iuiInfoEClass, IUIInfo.class, "IUIInfo", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIUIInfo_X(), ecorePackage.getEInt(), "x", null, 0, 1, IUIInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getIUIInfo_Y(), ecorePackage.getEInt(), "y", null, 0, 1, IUIInfo.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iuiContentElementEClass, IUIContentElement.class, "IUIContentElement", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(iContainerEClass, IContainer.class, "IContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIContainer_Contents(), this.getIContentElement(), null, "contents", null, 0, -1, IContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iAnnotationContainerEClass, IAnnotationContainer.class, "IAnnotationContainer", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getIAnnotationContainer_Annotations(), this.getIAnnotation(), null, "annotations", null, 0, -1, IAnnotationContainer.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(iAnnotationEClass, IAnnotation.class, "IAnnotation", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(uiAnnotationEClass, UIAnnotation.class, "UIAnnotation", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(baseModelNodeEClass, BaseModelNode.class, "BaseModelNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);
	}

} //BasemodelPackageImpl
