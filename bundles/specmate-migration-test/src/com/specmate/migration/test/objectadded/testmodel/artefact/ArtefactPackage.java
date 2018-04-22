/**
 */
package com.specmate.migration.test.objectadded.testmodel.artefact;

import com.specmate.migration.test.objectadded.testmodel.base.BasePackage;

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
 * @see com.specmate.migration.test.objectadded.testmodel.artefact.ArtefactFactory
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
	ArtefactPackage eINSTANCE = com.specmate.migration.test.objectadded.testmodel.artefact.impl.ArtefactPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DiagramImpl <em>Diagram</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.DiagramImpl
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.ArtefactPackageImpl#getDiagram()
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
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__ID = BasePackage.IMODIFIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM__CONTENTS = BasePackage.IMODIFIABLE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_FEATURE_COUNT = BasePackage.IMODIFIABLE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Diagram</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIAGRAM_OPERATION_COUNT = BasePackage.IMODIFIABLE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl <em>Document</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.ArtefactPackageImpl#getDocument()
	 * @generated
	 */
	int DOCUMENT = 1;

	/**
	 * The feature id for the '<em><b>Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__TESTED = BasePackage.IMODIFIABLE__TESTED;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__ID = BasePackage.IMODIFIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__CONTENTS = BasePackage.IMODIFIABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Length</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__LENGTH = BasePackage.IMODIFIABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT__OWNER = BasePackage.IMODIFIABLE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_FEATURE_COUNT = BasePackage.IMODIFIABLE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Document</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_OPERATION_COUNT = BasePackage.IMODIFIABLE_OPERATION_COUNT + 0;


	/**
	 * The meta object id for the '{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.FileImpl <em>File</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.FileImpl
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.ArtefactPackageImpl#getFile()
	 * @generated
	 */
	int FILE = 2;

	/**
	 * The feature id for the '<em><b>Tested</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__TESTED = BasePackage.IMODIFIABLE__TESTED;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__ID = BasePackage.IMODIFIABLE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contents</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__CONTENTS = BasePackage.IMODIFIABLE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Byte Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BYTE_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Byte Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BYTE_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Byte Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BYTE_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Byte Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BYTE_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Byte Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BYTE_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Short Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__SHORT_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Short Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__SHORT_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Short Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__SHORT_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Short Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__SHORT_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Short Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__SHORT_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Int Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__INT_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Int Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__INT_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Int Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__INT_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Int Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__INT_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Int Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__INT_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Char Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__CHAR_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Char Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__CHAR_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>Char Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__CHAR_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>Char Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__CHAR_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Char Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__CHAR_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Long Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__LONG_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 22;

	/**
	 * The feature id for the '<em><b>Long Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__LONG_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 23;

	/**
	 * The feature id for the '<em><b>Long Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__LONG_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 24;

	/**
	 * The feature id for the '<em><b>Long Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__LONG_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 25;

	/**
	 * The feature id for the '<em><b>Long Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__LONG_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 26;

	/**
	 * The feature id for the '<em><b>Float Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__FLOAT_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 27;

	/**
	 * The feature id for the '<em><b>Float Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__FLOAT_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 28;

	/**
	 * The feature id for the '<em><b>Float Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__FLOAT_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 29;

	/**
	 * The feature id for the '<em><b>Float Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__FLOAT_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 30;

	/**
	 * The feature id for the '<em><b>Float Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__FLOAT_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 31;

	/**
	 * The feature id for the '<em><b>Double Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__DOUBLE_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 32;

	/**
	 * The feature id for the '<em><b>Double Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__DOUBLE_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 33;

	/**
	 * The feature id for the '<em><b>Double Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__DOUBLE_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 34;

	/**
	 * The feature id for the '<em><b>Double Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__DOUBLE_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 35;

	/**
	 * The feature id for the '<em><b>Double Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__DOUBLE_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 36;

	/**
	 * The feature id for the '<em><b>Boolean Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BOOLEAN_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 37;

	/**
	 * The feature id for the '<em><b>Boolean Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BOOLEAN_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 38;

	/**
	 * The feature id for the '<em><b>Boolean Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BOOLEAN_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 39;

	/**
	 * The feature id for the '<em><b>Boolean Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BOOLEAN_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 40;

	/**
	 * The feature id for the '<em><b>Boolean Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__BOOLEAN_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 41;

	/**
	 * The feature id for the '<em><b>String Var1</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__STRING_VAR1 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 42;

	/**
	 * The feature id for the '<em><b>String Var2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__STRING_VAR2 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 43;

	/**
	 * The feature id for the '<em><b>String Var3</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__STRING_VAR3 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 44;

	/**
	 * The feature id for the '<em><b>String Var4</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__STRING_VAR4 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 45;

	/**
	 * The feature id for the '<em><b>String Var5</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE__STRING_VAR5 = BasePackage.IMODIFIABLE_FEATURE_COUNT + 46;

	/**
	 * The number of structural features of the '<em>File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_FEATURE_COUNT = BasePackage.IMODIFIABLE_FEATURE_COUNT + 47;

	/**
	 * The number of operations of the '<em>File</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FILE_OPERATION_COUNT = BasePackage.IMODIFIABLE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.objectadded.testmodel.artefact.Diagram <em>Diagram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Diagram</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.Diagram
	 * @generated
	 */
	EClass getDiagram();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.objectadded.testmodel.artefact.Document <em>Document</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.Document
	 * @generated
	 */
	EClass getDocument();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.Document#getLength <em>Length</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Length</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.Document#getLength()
	 * @see #getDocument()
	 * @generated
	 */
	EAttribute getDocument_Length();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.Document#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Owner</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.Document#getOwner()
	 * @see #getDocument()
	 * @generated
	 */
	EAttribute getDocument_Owner();

	/**
	 * Returns the meta object for class '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File <em>File</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>File</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File
	 * @generated
	 */
	EClass getFile();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar1 <em>Byte Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Byte Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ByteVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar2 <em>Byte Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Byte Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ByteVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar3 <em>Byte Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Byte Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ByteVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar4 <em>Byte Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Byte Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ByteVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar5 <em>Byte Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Byte Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getByteVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ByteVar5();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar1 <em>Short Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ShortVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar2 <em>Short Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ShortVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar3 <em>Short Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ShortVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar4 <em>Short Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ShortVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar5 <em>Short Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Short Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getShortVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_ShortVar5();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar1 <em>Int Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_IntVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar2 <em>Int Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_IntVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar3 <em>Int Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_IntVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar4 <em>Int Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_IntVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar5 <em>Int Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Int Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getIntVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_IntVar5();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar1 <em>Char Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Char Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_CharVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar2 <em>Char Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Char Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_CharVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar3 <em>Char Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Char Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_CharVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar4 <em>Char Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Char Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_CharVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar5 <em>Char Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Char Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getCharVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_CharVar5();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar1 <em>Long Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Long Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_LongVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar2 <em>Long Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Long Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_LongVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar3 <em>Long Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Long Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_LongVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar4 <em>Long Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Long Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_LongVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar5 <em>Long Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Long Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getLongVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_LongVar5();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar1 <em>Float Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Float Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_FloatVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar2 <em>Float Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Float Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_FloatVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar3 <em>Float Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Float Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_FloatVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar4 <em>Float Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Float Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_FloatVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar5 <em>Float Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Float Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getFloatVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_FloatVar5();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar1 <em>Double Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_DoubleVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar2 <em>Double Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_DoubleVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar3 <em>Double Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_DoubleVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar4 <em>Double Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_DoubleVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar5 <em>Double Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Double Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getDoubleVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_DoubleVar5();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar1 <em>Boolean Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Boolean Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_BooleanVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar2 <em>Boolean Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Boolean Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_BooleanVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar3 <em>Boolean Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Boolean Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_BooleanVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar4 <em>Boolean Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Boolean Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_BooleanVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar5 <em>Boolean Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Boolean Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#isBooleanVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_BooleanVar5();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar1 <em>String Var1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Var1</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar1()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_StringVar1();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar2 <em>String Var2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Var2</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar2()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_StringVar2();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar3 <em>String Var3</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Var3</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar3()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_StringVar3();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar4 <em>String Var4</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Var4</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar4()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_StringVar4();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar5 <em>String Var5</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>String Var5</em>'.
	 * @see com.specmate.migration.test.objectadded.testmodel.artefact.File#getStringVar5()
	 * @see #getFile()
	 * @generated
	 */
	EAttribute getFile_StringVar5();

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
		 * The meta object literal for the '{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DiagramImpl <em>Diagram</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.DiagramImpl
		 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.ArtefactPackageImpl#getDiagram()
		 * @generated
		 */
		EClass DIAGRAM = eINSTANCE.getDiagram();

		/**
		 * The meta object literal for the '{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl <em>Document</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.DocumentImpl
		 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.ArtefactPackageImpl#getDocument()
		 * @generated
		 */
		EClass DOCUMENT = eINSTANCE.getDocument();

		/**
		 * The meta object literal for the '<em><b>Length</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT__LENGTH = eINSTANCE.getDocument_Length();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT__OWNER = eINSTANCE.getDocument_Owner();

		/**
		 * The meta object literal for the '{@link com.specmate.migration.test.objectadded.testmodel.artefact.impl.FileImpl <em>File</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.FileImpl
		 * @see com.specmate.migration.test.objectadded.testmodel.artefact.impl.ArtefactPackageImpl#getFile()
		 * @generated
		 */
		EClass FILE = eINSTANCE.getFile();

		/**
		 * The meta object literal for the '<em><b>Byte Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BYTE_VAR1 = eINSTANCE.getFile_ByteVar1();

		/**
		 * The meta object literal for the '<em><b>Byte Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BYTE_VAR2 = eINSTANCE.getFile_ByteVar2();

		/**
		 * The meta object literal for the '<em><b>Byte Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BYTE_VAR3 = eINSTANCE.getFile_ByteVar3();

		/**
		 * The meta object literal for the '<em><b>Byte Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BYTE_VAR4 = eINSTANCE.getFile_ByteVar4();

		/**
		 * The meta object literal for the '<em><b>Byte Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BYTE_VAR5 = eINSTANCE.getFile_ByteVar5();

		/**
		 * The meta object literal for the '<em><b>Short Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__SHORT_VAR1 = eINSTANCE.getFile_ShortVar1();

		/**
		 * The meta object literal for the '<em><b>Short Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__SHORT_VAR2 = eINSTANCE.getFile_ShortVar2();

		/**
		 * The meta object literal for the '<em><b>Short Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__SHORT_VAR3 = eINSTANCE.getFile_ShortVar3();

		/**
		 * The meta object literal for the '<em><b>Short Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__SHORT_VAR4 = eINSTANCE.getFile_ShortVar4();

		/**
		 * The meta object literal for the '<em><b>Short Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__SHORT_VAR5 = eINSTANCE.getFile_ShortVar5();

		/**
		 * The meta object literal for the '<em><b>Int Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__INT_VAR1 = eINSTANCE.getFile_IntVar1();

		/**
		 * The meta object literal for the '<em><b>Int Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__INT_VAR2 = eINSTANCE.getFile_IntVar2();

		/**
		 * The meta object literal for the '<em><b>Int Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__INT_VAR3 = eINSTANCE.getFile_IntVar3();

		/**
		 * The meta object literal for the '<em><b>Int Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__INT_VAR4 = eINSTANCE.getFile_IntVar4();

		/**
		 * The meta object literal for the '<em><b>Int Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__INT_VAR5 = eINSTANCE.getFile_IntVar5();

		/**
		 * The meta object literal for the '<em><b>Char Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__CHAR_VAR1 = eINSTANCE.getFile_CharVar1();

		/**
		 * The meta object literal for the '<em><b>Char Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__CHAR_VAR2 = eINSTANCE.getFile_CharVar2();

		/**
		 * The meta object literal for the '<em><b>Char Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__CHAR_VAR3 = eINSTANCE.getFile_CharVar3();

		/**
		 * The meta object literal for the '<em><b>Char Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__CHAR_VAR4 = eINSTANCE.getFile_CharVar4();

		/**
		 * The meta object literal for the '<em><b>Char Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__CHAR_VAR5 = eINSTANCE.getFile_CharVar5();

		/**
		 * The meta object literal for the '<em><b>Long Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__LONG_VAR1 = eINSTANCE.getFile_LongVar1();

		/**
		 * The meta object literal for the '<em><b>Long Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__LONG_VAR2 = eINSTANCE.getFile_LongVar2();

		/**
		 * The meta object literal for the '<em><b>Long Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__LONG_VAR3 = eINSTANCE.getFile_LongVar3();

		/**
		 * The meta object literal for the '<em><b>Long Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__LONG_VAR4 = eINSTANCE.getFile_LongVar4();

		/**
		 * The meta object literal for the '<em><b>Long Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__LONG_VAR5 = eINSTANCE.getFile_LongVar5();

		/**
		 * The meta object literal for the '<em><b>Float Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__FLOAT_VAR1 = eINSTANCE.getFile_FloatVar1();

		/**
		 * The meta object literal for the '<em><b>Float Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__FLOAT_VAR2 = eINSTANCE.getFile_FloatVar2();

		/**
		 * The meta object literal for the '<em><b>Float Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__FLOAT_VAR3 = eINSTANCE.getFile_FloatVar3();

		/**
		 * The meta object literal for the '<em><b>Float Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__FLOAT_VAR4 = eINSTANCE.getFile_FloatVar4();

		/**
		 * The meta object literal for the '<em><b>Float Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__FLOAT_VAR5 = eINSTANCE.getFile_FloatVar5();

		/**
		 * The meta object literal for the '<em><b>Double Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__DOUBLE_VAR1 = eINSTANCE.getFile_DoubleVar1();

		/**
		 * The meta object literal for the '<em><b>Double Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__DOUBLE_VAR2 = eINSTANCE.getFile_DoubleVar2();

		/**
		 * The meta object literal for the '<em><b>Double Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__DOUBLE_VAR3 = eINSTANCE.getFile_DoubleVar3();

		/**
		 * The meta object literal for the '<em><b>Double Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__DOUBLE_VAR4 = eINSTANCE.getFile_DoubleVar4();

		/**
		 * The meta object literal for the '<em><b>Double Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__DOUBLE_VAR5 = eINSTANCE.getFile_DoubleVar5();

		/**
		 * The meta object literal for the '<em><b>Boolean Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BOOLEAN_VAR1 = eINSTANCE.getFile_BooleanVar1();

		/**
		 * The meta object literal for the '<em><b>Boolean Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BOOLEAN_VAR2 = eINSTANCE.getFile_BooleanVar2();

		/**
		 * The meta object literal for the '<em><b>Boolean Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BOOLEAN_VAR3 = eINSTANCE.getFile_BooleanVar3();

		/**
		 * The meta object literal for the '<em><b>Boolean Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BOOLEAN_VAR4 = eINSTANCE.getFile_BooleanVar4();

		/**
		 * The meta object literal for the '<em><b>Boolean Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__BOOLEAN_VAR5 = eINSTANCE.getFile_BooleanVar5();

		/**
		 * The meta object literal for the '<em><b>String Var1</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__STRING_VAR1 = eINSTANCE.getFile_StringVar1();

		/**
		 * The meta object literal for the '<em><b>String Var2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__STRING_VAR2 = eINSTANCE.getFile_StringVar2();

		/**
		 * The meta object literal for the '<em><b>String Var3</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__STRING_VAR3 = eINSTANCE.getFile_StringVar3();

		/**
		 * The meta object literal for the '<em><b>String Var4</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__STRING_VAR4 = eINSTANCE.getFile_StringVar4();

		/**
		 * The meta object literal for the '<em><b>String Var5</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FILE__STRING_VAR5 = eINSTANCE.getFile_StringVar5();

	}

} //ArtefactPackage
