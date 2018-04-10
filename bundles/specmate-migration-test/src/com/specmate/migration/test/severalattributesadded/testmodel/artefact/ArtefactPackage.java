/**
 */
package com.specmate.migration.test.severalattributesadded.testmodel.artefact;

import com.specmate.migration.test.severalattributesadded.testmodel.base.BasePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.ArtefactFactory
 * @model kind="package"
 * @generated
 */
public interface ArtefactPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "artefact";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/1/testmodel/artefact";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.testmodel.artefact";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ArtefactPackage eINSTANCE = com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.ArtefactPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl <em>Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.ArtefactPackageImpl#getDiagram()
	 * @generated
	 */
	int DIAGRAM = 0;

	/**
	 * The feature id for the '<em><b>Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__TESTED = BasePackage.IMODIFIABLE__TESTED;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__NAME = BasePackage.IMODIFIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__ID = BasePackage.IMODIFIABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__CONTENTS = BasePackage.IMODIFIABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Amount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__AMOUNT = BasePackage.IMODIFIABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__LENGTH = BasePackage.IMODIFIABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Linked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__LINKED = BasePackage.IMODIFIABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Intamount</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__INTAMOUNT = BasePackage.IMODIFIABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Doublelength</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__DOUBLELENGTH = BasePackage.IMODIFIABLE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Booleanlinked</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__BOOLEANLINKED = BasePackage.IMODIFIABLE_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_FEATURE_COUNT = BasePackage.IMODIFIABLE_FEATURE_COUNT + 9;

	/**
	 * The number of operations of the '<em>Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_OPERATION_COUNT = BasePackage.IMODIFIABLE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram <em>Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram</em>'.
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram
	 * @generated
	 */
	EClass getDiagram();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getAmount <em>Amount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Amount</em>'.
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getAmount()
	 * @see #getDiagram()
	 * @generated
	 */
	EAttribute getDiagram_Amount();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLength()
	 * @see #getDiagram()
	 * @generated
	 */
	EAttribute getDiagram_Length();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLinked <em>Linked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Linked</em>'.
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getLinked()
	 * @see #getDiagram()
	 * @generated
	 */
	EAttribute getDiagram_Linked();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getIntamount <em>Intamount</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Intamount</em>'.
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getIntamount()
	 * @see #getDiagram()
	 * @generated
	 */
	EAttribute getDiagram_Intamount();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getDoublelength <em>Doublelength</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Doublelength</em>'.
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#getDoublelength()
	 * @see #getDiagram()
	 * @generated
	 */
	EAttribute getDiagram_Doublelength();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#isBooleanlinked <em>Booleanlinked</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Booleanlinked</em>'.
	 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.Diagram#isBooleanlinked()
	 * @see #getDiagram()
	 * @generated
	 */
	EAttribute getDiagram_Booleanlinked();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ArtefactFactory getArtefactFactory();

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
		 * The meta object literal for the '{@link com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl <em>Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.DiagramImpl
		 * @see com.specmate.migration.test.severalattributesadded.testmodel.artefact.impl.ArtefactPackageImpl#getDiagram()
		 * @generated
		 */
		EClass DIAGRAM = eINSTANCE.getDiagram();

		/**
		 * The meta object literal for the '<em><b>Amount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM__AMOUNT = eINSTANCE.getDiagram_Amount();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM__LENGTH = eINSTANCE.getDiagram_Length();

		/**
		 * The meta object literal for the '<em><b>Linked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM__LINKED = eINSTANCE.getDiagram_Linked();

		/**
		 * The meta object literal for the '<em><b>Intamount</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM__INTAMOUNT = eINSTANCE.getDiagram_Intamount();

		/**
		 * The meta object literal for the '<em><b>Doublelength</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM__DOUBLELENGTH = eINSTANCE.getDiagram_Doublelength();

		/**
		 * The meta object literal for the '<em><b>Booleanlinked</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIAGRAM__BOOLEANLINKED = eINSTANCE.getDiagram_Booleanlinked();

	}

} //ArtefactPackage
