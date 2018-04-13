/**
 */
package com.specmate.migration.test.changedtypes.testmodel.artefact.impl;

import com.specmate.migration.test.changedtypes.testmodel.artefact.ArtefactPackage;
import com.specmate.migration.test.changedtypes.testmodel.artefact.File;

import com.specmate.migration.test.changedtypes.testmodel.base.BasePackage;
import com.specmate.migration.test.changedtypes.testmodel.base.IContainer;
import com.specmate.migration.test.changedtypes.testmodel.base.IContentElement;
import com.specmate.migration.test.changedtypes.testmodel.base.IID;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import org.eclipse.emf.ecore.util.InternalEList;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>File</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isTested <em>Tested</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getByteVar1 <em>Byte Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getByteVar2 <em>Byte Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getByteVar3 <em>Byte Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getByteVar4 <em>Byte Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getByteVar5 <em>Byte Var5</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getShortVar1 <em>Short Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getShortVar2 <em>Short Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getShortVar3 <em>Short Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getShortVar4 <em>Short Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getShortVar5 <em>Short Var5</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getIntVar1 <em>Int Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getIntVar2 <em>Int Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getIntVar3 <em>Int Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getIntVar4 <em>Int Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getIntVar5 <em>Int Var5</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getCharVar1 <em>Char Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getCharVar2 <em>Char Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getCharVar3 <em>Char Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getCharVar4 <em>Char Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getCharVar5 <em>Char Var5</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getLongVar1 <em>Long Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getLongVar2 <em>Long Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getLongVar3 <em>Long Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getLongVar4 <em>Long Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getLongVar5 <em>Long Var5</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getFloatVar1 <em>Float Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getFloatVar2 <em>Float Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getFloatVar3 <em>Float Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getFloatVar4 <em>Float Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getFloatVar5 <em>Float Var5</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getDoubleVar1 <em>Double Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getDoubleVar2 <em>Double Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getDoubleVar3 <em>Double Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getDoubleVar4 <em>Double Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getDoubleVar5 <em>Double Var5</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#getBooleanVar1 <em>Boolean Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isBooleanVar2 <em>Boolean Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isBooleanVar3 <em>Boolean Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isBooleanVar4 <em>Boolean Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isBooleanVar5 <em>Boolean Var5</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isStringVar1 <em>String Var1</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isStringVar2 <em>String Var2</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isStringVar3 <em>String Var3</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isStringVar4 <em>String Var4</em>}</li>
 *   <li>{@link com.specmate.migration.test.changedtypes.testmodel.artefact.impl.FileImpl#isStringVar5 <em>String Var5</em>}</li>
 * </ul>
 *
 * @generated
 */
public class FileImpl extends MinimalEObjectImpl.Container implements File {
	/**
	 * The default value of the '{@link #isTested() <em>Tested</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isTested()
	 * @generated
	 * @ordered
	 */
	protected static final boolean TESTED_EDEFAULT = false;

	/**
	 * The default value of the '{@link #getId() <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getId()
	 * @generated
	 * @ordered
	 */
	protected static final String ID_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getByteVar1() <em>Byte Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getByteVar1()
	 * @generated
	 * @ordered
	 */
	protected static final short BYTE_VAR1_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getByteVar2() <em>Byte Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getByteVar2()
	 * @generated
	 * @ordered
	 */
	protected static final int BYTE_VAR2_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getByteVar3() <em>Byte Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getByteVar3()
	 * @generated
	 * @ordered
	 */
	protected static final long BYTE_VAR3_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getByteVar4() <em>Byte Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getByteVar4()
	 * @generated
	 * @ordered
	 */
	protected static final float BYTE_VAR4_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getByteVar5() <em>Byte Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getByteVar5()
	 * @generated
	 * @ordered
	 */
	protected static final double BYTE_VAR5_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getShortVar1() <em>Short Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortVar1()
	 * @generated
	 * @ordered
	 */
	protected static final int SHORT_VAR1_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getShortVar2() <em>Short Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortVar2()
	 * @generated
	 * @ordered
	 */
	protected static final long SHORT_VAR2_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getShortVar3() <em>Short Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortVar3()
	 * @generated
	 * @ordered
	 */
	protected static final float SHORT_VAR3_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getShortVar4() <em>Short Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortVar4()
	 * @generated
	 * @ordered
	 */
	protected static final double SHORT_VAR4_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getShortVar5() <em>Short Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getShortVar5()
	 * @generated
	 * @ordered
	 */
	protected static final short SHORT_VAR5_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getIntVar1() <em>Int Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntVar1()
	 * @generated
	 * @ordered
	 */
	protected static final long INT_VAR1_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getIntVar2() <em>Int Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntVar2()
	 * @generated
	 * @ordered
	 */
	protected static final float INT_VAR2_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getIntVar3() <em>Int Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntVar3()
	 * @generated
	 * @ordered
	 */
	protected static final double INT_VAR3_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getIntVar4() <em>Int Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntVar4()
	 * @generated
	 * @ordered
	 */
	protected static final int INT_VAR4_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getIntVar5() <em>Int Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getIntVar5()
	 * @generated
	 * @ordered
	 */
	protected static final int INT_VAR5_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getCharVar1() <em>Char Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharVar1()
	 * @generated
	 * @ordered
	 */
	protected static final int CHAR_VAR1_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getCharVar2() <em>Char Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharVar2()
	 * @generated
	 * @ordered
	 */
	protected static final long CHAR_VAR2_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getCharVar3() <em>Char Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharVar3()
	 * @generated
	 * @ordered
	 */
	protected static final float CHAR_VAR3_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getCharVar4() <em>Char Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharVar4()
	 * @generated
	 * @ordered
	 */
	protected static final double CHAR_VAR4_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getCharVar5() <em>Char Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getCharVar5()
	 * @generated
	 * @ordered
	 */
	protected static final int CHAR_VAR5_EDEFAULT = 0;

	/**
	 * The default value of the '{@link #getLongVar1() <em>Long Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLongVar1()
	 * @generated
	 * @ordered
	 */
	protected static final float LONG_VAR1_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getLongVar2() <em>Long Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLongVar2()
	 * @generated
	 * @ordered
	 */
	protected static final double LONG_VAR2_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getLongVar3() <em>Long Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLongVar3()
	 * @generated
	 * @ordered
	 */
	protected static final long LONG_VAR3_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getLongVar4() <em>Long Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLongVar4()
	 * @generated
	 * @ordered
	 */
	protected static final long LONG_VAR4_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getLongVar5() <em>Long Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getLongVar5()
	 * @generated
	 * @ordered
	 */
	protected static final long LONG_VAR5_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getFloatVar1() <em>Float Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatVar1()
	 * @generated
	 * @ordered
	 */
	protected static final double FLOAT_VAR1_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getFloatVar2() <em>Float Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatVar2()
	 * @generated
	 * @ordered
	 */
	protected static final float FLOAT_VAR2_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getFloatVar3() <em>Float Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatVar3()
	 * @generated
	 * @ordered
	 */
	protected static final float FLOAT_VAR3_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getFloatVar4() <em>Float Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatVar4()
	 * @generated
	 * @ordered
	 */
	protected static final float FLOAT_VAR4_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getFloatVar5() <em>Float Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getFloatVar5()
	 * @generated
	 * @ordered
	 */
	protected static final float FLOAT_VAR5_EDEFAULT = 0.0F;

	/**
	 * The default value of the '{@link #getDoubleVar1() <em>Double Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleVar1()
	 * @generated
	 * @ordered
	 */
	protected static final double DOUBLE_VAR1_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getDoubleVar2() <em>Double Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleVar2()
	 * @generated
	 * @ordered
	 */
	protected static final double DOUBLE_VAR2_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getDoubleVar3() <em>Double Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleVar3()
	 * @generated
	 * @ordered
	 */
	protected static final double DOUBLE_VAR3_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getDoubleVar4() <em>Double Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleVar4()
	 * @generated
	 * @ordered
	 */
	protected static final double DOUBLE_VAR4_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getDoubleVar5() <em>Double Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDoubleVar5()
	 * @generated
	 * @ordered
	 */
	protected static final double DOUBLE_VAR5_EDEFAULT = 0.0;

	/**
	 * The default value of the '{@link #getBooleanVar1() <em>Boolean Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getBooleanVar1()
	 * @generated
	 * @ordered
	 */
	protected static final String BOOLEAN_VAR1_EDEFAULT = null;

	/**
	 * The default value of the '{@link #isBooleanVar2() <em>Boolean Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBooleanVar2()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOOLEAN_VAR2_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isBooleanVar3() <em>Boolean Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBooleanVar3()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOOLEAN_VAR3_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isBooleanVar4() <em>Boolean Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBooleanVar4()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOOLEAN_VAR4_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isBooleanVar5() <em>Boolean Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isBooleanVar5()
	 * @generated
	 * @ordered
	 */
	protected static final boolean BOOLEAN_VAR5_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isStringVar1() <em>String Var1</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStringVar1()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRING_VAR1_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isStringVar2() <em>String Var2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStringVar2()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRING_VAR2_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isStringVar3() <em>String Var3</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStringVar3()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRING_VAR3_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isStringVar4() <em>String Var4</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStringVar4()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRING_VAR4_EDEFAULT = false;

	/**
	 * The default value of the '{@link #isStringVar5() <em>String Var5</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isStringVar5()
	 * @generated
	 * @ordered
	 */
	protected static final boolean STRING_VAR5_EDEFAULT = false;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ArtefactPackage.Literals.FILE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isTested() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__TESTED, BasePackage.Literals.ITESTABLE__TESTED, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTested(boolean newTested) {
		eDynamicSet(ArtefactPackage.FILE__TESTED, BasePackage.Literals.ITESTABLE__TESTED, newTested);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eDynamicGet(ArtefactPackage.FILE__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(ArtefactPackage.FILE__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(ArtefactPackage.FILE__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getByteVar1() {
		return (Short)eDynamicGet(ArtefactPackage.FILE__BYTE_VAR1, ArtefactPackage.Literals.FILE__BYTE_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setByteVar1(short newByteVar1) {
		eDynamicSet(ArtefactPackage.FILE__BYTE_VAR1, ArtefactPackage.Literals.FILE__BYTE_VAR1, newByteVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getByteVar2() {
		return (Integer)eDynamicGet(ArtefactPackage.FILE__BYTE_VAR2, ArtefactPackage.Literals.FILE__BYTE_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setByteVar2(int newByteVar2) {
		eDynamicSet(ArtefactPackage.FILE__BYTE_VAR2, ArtefactPackage.Literals.FILE__BYTE_VAR2, newByteVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getByteVar3() {
		return (Long)eDynamicGet(ArtefactPackage.FILE__BYTE_VAR3, ArtefactPackage.Literals.FILE__BYTE_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setByteVar3(long newByteVar3) {
		eDynamicSet(ArtefactPackage.FILE__BYTE_VAR3, ArtefactPackage.Literals.FILE__BYTE_VAR3, newByteVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getByteVar4() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__BYTE_VAR4, ArtefactPackage.Literals.FILE__BYTE_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setByteVar4(float newByteVar4) {
		eDynamicSet(ArtefactPackage.FILE__BYTE_VAR4, ArtefactPackage.Literals.FILE__BYTE_VAR4, newByteVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getByteVar5() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__BYTE_VAR5, ArtefactPackage.Literals.FILE__BYTE_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setByteVar5(double newByteVar5) {
		eDynamicSet(ArtefactPackage.FILE__BYTE_VAR5, ArtefactPackage.Literals.FILE__BYTE_VAR5, newByteVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getShortVar1() {
		return (Integer)eDynamicGet(ArtefactPackage.FILE__SHORT_VAR1, ArtefactPackage.Literals.FILE__SHORT_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortVar1(int newShortVar1) {
		eDynamicSet(ArtefactPackage.FILE__SHORT_VAR1, ArtefactPackage.Literals.FILE__SHORT_VAR1, newShortVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getShortVar2() {
		return (Long)eDynamicGet(ArtefactPackage.FILE__SHORT_VAR2, ArtefactPackage.Literals.FILE__SHORT_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortVar2(long newShortVar2) {
		eDynamicSet(ArtefactPackage.FILE__SHORT_VAR2, ArtefactPackage.Literals.FILE__SHORT_VAR2, newShortVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getShortVar3() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__SHORT_VAR3, ArtefactPackage.Literals.FILE__SHORT_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortVar3(float newShortVar3) {
		eDynamicSet(ArtefactPackage.FILE__SHORT_VAR3, ArtefactPackage.Literals.FILE__SHORT_VAR3, newShortVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getShortVar4() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__SHORT_VAR4, ArtefactPackage.Literals.FILE__SHORT_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortVar4(double newShortVar4) {
		eDynamicSet(ArtefactPackage.FILE__SHORT_VAR4, ArtefactPackage.Literals.FILE__SHORT_VAR4, newShortVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public short getShortVar5() {
		return (Short)eDynamicGet(ArtefactPackage.FILE__SHORT_VAR5, ArtefactPackage.Literals.FILE__SHORT_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setShortVar5(short newShortVar5) {
		eDynamicSet(ArtefactPackage.FILE__SHORT_VAR5, ArtefactPackage.Literals.FILE__SHORT_VAR5, newShortVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getIntVar1() {
		return (Long)eDynamicGet(ArtefactPackage.FILE__INT_VAR1, ArtefactPackage.Literals.FILE__INT_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntVar1(long newIntVar1) {
		eDynamicSet(ArtefactPackage.FILE__INT_VAR1, ArtefactPackage.Literals.FILE__INT_VAR1, newIntVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getIntVar2() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__INT_VAR2, ArtefactPackage.Literals.FILE__INT_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntVar2(float newIntVar2) {
		eDynamicSet(ArtefactPackage.FILE__INT_VAR2, ArtefactPackage.Literals.FILE__INT_VAR2, newIntVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getIntVar3() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__INT_VAR3, ArtefactPackage.Literals.FILE__INT_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntVar3(double newIntVar3) {
		eDynamicSet(ArtefactPackage.FILE__INT_VAR3, ArtefactPackage.Literals.FILE__INT_VAR3, newIntVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIntVar4() {
		return (Integer)eDynamicGet(ArtefactPackage.FILE__INT_VAR4, ArtefactPackage.Literals.FILE__INT_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntVar4(int newIntVar4) {
		eDynamicSet(ArtefactPackage.FILE__INT_VAR4, ArtefactPackage.Literals.FILE__INT_VAR4, newIntVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIntVar5() {
		return (Integer)eDynamicGet(ArtefactPackage.FILE__INT_VAR5, ArtefactPackage.Literals.FILE__INT_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIntVar5(int newIntVar5) {
		eDynamicSet(ArtefactPackage.FILE__INT_VAR5, ArtefactPackage.Literals.FILE__INT_VAR5, newIntVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCharVar1() {
		return (Integer)eDynamicGet(ArtefactPackage.FILE__CHAR_VAR1, ArtefactPackage.Literals.FILE__CHAR_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCharVar1(int newCharVar1) {
		eDynamicSet(ArtefactPackage.FILE__CHAR_VAR1, ArtefactPackage.Literals.FILE__CHAR_VAR1, newCharVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getCharVar2() {
		return (Long)eDynamicGet(ArtefactPackage.FILE__CHAR_VAR2, ArtefactPackage.Literals.FILE__CHAR_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCharVar2(long newCharVar2) {
		eDynamicSet(ArtefactPackage.FILE__CHAR_VAR2, ArtefactPackage.Literals.FILE__CHAR_VAR2, newCharVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getCharVar3() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__CHAR_VAR3, ArtefactPackage.Literals.FILE__CHAR_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCharVar3(float newCharVar3) {
		eDynamicSet(ArtefactPackage.FILE__CHAR_VAR3, ArtefactPackage.Literals.FILE__CHAR_VAR3, newCharVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getCharVar4() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__CHAR_VAR4, ArtefactPackage.Literals.FILE__CHAR_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCharVar4(double newCharVar4) {
		eDynamicSet(ArtefactPackage.FILE__CHAR_VAR4, ArtefactPackage.Literals.FILE__CHAR_VAR4, newCharVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCharVar5() {
		return (Integer)eDynamicGet(ArtefactPackage.FILE__CHAR_VAR5, ArtefactPackage.Literals.FILE__CHAR_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCharVar5(int newCharVar5) {
		eDynamicSet(ArtefactPackage.FILE__CHAR_VAR5, ArtefactPackage.Literals.FILE__CHAR_VAR5, newCharVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getLongVar1() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__LONG_VAR1, ArtefactPackage.Literals.FILE__LONG_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLongVar1(float newLongVar1) {
		eDynamicSet(ArtefactPackage.FILE__LONG_VAR1, ArtefactPackage.Literals.FILE__LONG_VAR1, newLongVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLongVar2() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__LONG_VAR2, ArtefactPackage.Literals.FILE__LONG_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLongVar2(double newLongVar2) {
		eDynamicSet(ArtefactPackage.FILE__LONG_VAR2, ArtefactPackage.Literals.FILE__LONG_VAR2, newLongVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getLongVar3() {
		return (Long)eDynamicGet(ArtefactPackage.FILE__LONG_VAR3, ArtefactPackage.Literals.FILE__LONG_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLongVar3(long newLongVar3) {
		eDynamicSet(ArtefactPackage.FILE__LONG_VAR3, ArtefactPackage.Literals.FILE__LONG_VAR3, newLongVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getLongVar4() {
		return (Long)eDynamicGet(ArtefactPackage.FILE__LONG_VAR4, ArtefactPackage.Literals.FILE__LONG_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLongVar4(long newLongVar4) {
		eDynamicSet(ArtefactPackage.FILE__LONG_VAR4, ArtefactPackage.Literals.FILE__LONG_VAR4, newLongVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public long getLongVar5() {
		return (Long)eDynamicGet(ArtefactPackage.FILE__LONG_VAR5, ArtefactPackage.Literals.FILE__LONG_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLongVar5(long newLongVar5) {
		eDynamicSet(ArtefactPackage.FILE__LONG_VAR5, ArtefactPackage.Literals.FILE__LONG_VAR5, newLongVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getFloatVar1() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__FLOAT_VAR1, ArtefactPackage.Literals.FILE__FLOAT_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatVar1(double newFloatVar1) {
		eDynamicSet(ArtefactPackage.FILE__FLOAT_VAR1, ArtefactPackage.Literals.FILE__FLOAT_VAR1, newFloatVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getFloatVar2() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__FLOAT_VAR2, ArtefactPackage.Literals.FILE__FLOAT_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatVar2(float newFloatVar2) {
		eDynamicSet(ArtefactPackage.FILE__FLOAT_VAR2, ArtefactPackage.Literals.FILE__FLOAT_VAR2, newFloatVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getFloatVar3() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__FLOAT_VAR3, ArtefactPackage.Literals.FILE__FLOAT_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatVar3(float newFloatVar3) {
		eDynamicSet(ArtefactPackage.FILE__FLOAT_VAR3, ArtefactPackage.Literals.FILE__FLOAT_VAR3, newFloatVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getFloatVar4() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__FLOAT_VAR4, ArtefactPackage.Literals.FILE__FLOAT_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatVar4(float newFloatVar4) {
		eDynamicSet(ArtefactPackage.FILE__FLOAT_VAR4, ArtefactPackage.Literals.FILE__FLOAT_VAR4, newFloatVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public float getFloatVar5() {
		return (Float)eDynamicGet(ArtefactPackage.FILE__FLOAT_VAR5, ArtefactPackage.Literals.FILE__FLOAT_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatVar5(float newFloatVar5) {
		eDynamicSet(ArtefactPackage.FILE__FLOAT_VAR5, ArtefactPackage.Literals.FILE__FLOAT_VAR5, newFloatVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getDoubleVar1() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__DOUBLE_VAR1, ArtefactPackage.Literals.FILE__DOUBLE_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoubleVar1(double newDoubleVar1) {
		eDynamicSet(ArtefactPackage.FILE__DOUBLE_VAR1, ArtefactPackage.Literals.FILE__DOUBLE_VAR1, newDoubleVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getDoubleVar2() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__DOUBLE_VAR2, ArtefactPackage.Literals.FILE__DOUBLE_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoubleVar2(double newDoubleVar2) {
		eDynamicSet(ArtefactPackage.FILE__DOUBLE_VAR2, ArtefactPackage.Literals.FILE__DOUBLE_VAR2, newDoubleVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getDoubleVar3() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__DOUBLE_VAR3, ArtefactPackage.Literals.FILE__DOUBLE_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoubleVar3(double newDoubleVar3) {
		eDynamicSet(ArtefactPackage.FILE__DOUBLE_VAR3, ArtefactPackage.Literals.FILE__DOUBLE_VAR3, newDoubleVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getDoubleVar4() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__DOUBLE_VAR4, ArtefactPackage.Literals.FILE__DOUBLE_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoubleVar4(double newDoubleVar4) {
		eDynamicSet(ArtefactPackage.FILE__DOUBLE_VAR4, ArtefactPackage.Literals.FILE__DOUBLE_VAR4, newDoubleVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getDoubleVar5() {
		return (Double)eDynamicGet(ArtefactPackage.FILE__DOUBLE_VAR5, ArtefactPackage.Literals.FILE__DOUBLE_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDoubleVar5(double newDoubleVar5) {
		eDynamicSet(ArtefactPackage.FILE__DOUBLE_VAR5, ArtefactPackage.Literals.FILE__DOUBLE_VAR5, newDoubleVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getBooleanVar1() {
		return (String)eDynamicGet(ArtefactPackage.FILE__BOOLEAN_VAR1, ArtefactPackage.Literals.FILE__BOOLEAN_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBooleanVar1(String newBooleanVar1) {
		eDynamicSet(ArtefactPackage.FILE__BOOLEAN_VAR1, ArtefactPackage.Literals.FILE__BOOLEAN_VAR1, newBooleanVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBooleanVar2() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__BOOLEAN_VAR2, ArtefactPackage.Literals.FILE__BOOLEAN_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBooleanVar2(boolean newBooleanVar2) {
		eDynamicSet(ArtefactPackage.FILE__BOOLEAN_VAR2, ArtefactPackage.Literals.FILE__BOOLEAN_VAR2, newBooleanVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBooleanVar3() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__BOOLEAN_VAR3, ArtefactPackage.Literals.FILE__BOOLEAN_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBooleanVar3(boolean newBooleanVar3) {
		eDynamicSet(ArtefactPackage.FILE__BOOLEAN_VAR3, ArtefactPackage.Literals.FILE__BOOLEAN_VAR3, newBooleanVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBooleanVar4() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__BOOLEAN_VAR4, ArtefactPackage.Literals.FILE__BOOLEAN_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBooleanVar4(boolean newBooleanVar4) {
		eDynamicSet(ArtefactPackage.FILE__BOOLEAN_VAR4, ArtefactPackage.Literals.FILE__BOOLEAN_VAR4, newBooleanVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isBooleanVar5() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__BOOLEAN_VAR5, ArtefactPackage.Literals.FILE__BOOLEAN_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBooleanVar5(boolean newBooleanVar5) {
		eDynamicSet(ArtefactPackage.FILE__BOOLEAN_VAR5, ArtefactPackage.Literals.FILE__BOOLEAN_VAR5, newBooleanVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStringVar1() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__STRING_VAR1, ArtefactPackage.Literals.FILE__STRING_VAR1, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringVar1(boolean newStringVar1) {
		eDynamicSet(ArtefactPackage.FILE__STRING_VAR1, ArtefactPackage.Literals.FILE__STRING_VAR1, newStringVar1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStringVar2() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__STRING_VAR2, ArtefactPackage.Literals.FILE__STRING_VAR2, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringVar2(boolean newStringVar2) {
		eDynamicSet(ArtefactPackage.FILE__STRING_VAR2, ArtefactPackage.Literals.FILE__STRING_VAR2, newStringVar2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStringVar3() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__STRING_VAR3, ArtefactPackage.Literals.FILE__STRING_VAR3, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringVar3(boolean newStringVar3) {
		eDynamicSet(ArtefactPackage.FILE__STRING_VAR3, ArtefactPackage.Literals.FILE__STRING_VAR3, newStringVar3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStringVar4() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__STRING_VAR4, ArtefactPackage.Literals.FILE__STRING_VAR4, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringVar4(boolean newStringVar4) {
		eDynamicSet(ArtefactPackage.FILE__STRING_VAR4, ArtefactPackage.Literals.FILE__STRING_VAR4, newStringVar4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isStringVar5() {
		return (Boolean)eDynamicGet(ArtefactPackage.FILE__STRING_VAR5, ArtefactPackage.Literals.FILE__STRING_VAR5, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringVar5(boolean newStringVar5) {
		eDynamicSet(ArtefactPackage.FILE__STRING_VAR5, ArtefactPackage.Literals.FILE__STRING_VAR5, newStringVar5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ArtefactPackage.FILE__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ArtefactPackage.FILE__TESTED:
				return isTested();
			case ArtefactPackage.FILE__ID:
				return getId();
			case ArtefactPackage.FILE__CONTENTS:
				return getContents();
			case ArtefactPackage.FILE__BYTE_VAR1:
				return getByteVar1();
			case ArtefactPackage.FILE__BYTE_VAR2:
				return getByteVar2();
			case ArtefactPackage.FILE__BYTE_VAR3:
				return getByteVar3();
			case ArtefactPackage.FILE__BYTE_VAR4:
				return getByteVar4();
			case ArtefactPackage.FILE__BYTE_VAR5:
				return getByteVar5();
			case ArtefactPackage.FILE__SHORT_VAR1:
				return getShortVar1();
			case ArtefactPackage.FILE__SHORT_VAR2:
				return getShortVar2();
			case ArtefactPackage.FILE__SHORT_VAR3:
				return getShortVar3();
			case ArtefactPackage.FILE__SHORT_VAR4:
				return getShortVar4();
			case ArtefactPackage.FILE__SHORT_VAR5:
				return getShortVar5();
			case ArtefactPackage.FILE__INT_VAR1:
				return getIntVar1();
			case ArtefactPackage.FILE__INT_VAR2:
				return getIntVar2();
			case ArtefactPackage.FILE__INT_VAR3:
				return getIntVar3();
			case ArtefactPackage.FILE__INT_VAR4:
				return getIntVar4();
			case ArtefactPackage.FILE__INT_VAR5:
				return getIntVar5();
			case ArtefactPackage.FILE__CHAR_VAR1:
				return getCharVar1();
			case ArtefactPackage.FILE__CHAR_VAR2:
				return getCharVar2();
			case ArtefactPackage.FILE__CHAR_VAR3:
				return getCharVar3();
			case ArtefactPackage.FILE__CHAR_VAR4:
				return getCharVar4();
			case ArtefactPackage.FILE__CHAR_VAR5:
				return getCharVar5();
			case ArtefactPackage.FILE__LONG_VAR1:
				return getLongVar1();
			case ArtefactPackage.FILE__LONG_VAR2:
				return getLongVar2();
			case ArtefactPackage.FILE__LONG_VAR3:
				return getLongVar3();
			case ArtefactPackage.FILE__LONG_VAR4:
				return getLongVar4();
			case ArtefactPackage.FILE__LONG_VAR5:
				return getLongVar5();
			case ArtefactPackage.FILE__FLOAT_VAR1:
				return getFloatVar1();
			case ArtefactPackage.FILE__FLOAT_VAR2:
				return getFloatVar2();
			case ArtefactPackage.FILE__FLOAT_VAR3:
				return getFloatVar3();
			case ArtefactPackage.FILE__FLOAT_VAR4:
				return getFloatVar4();
			case ArtefactPackage.FILE__FLOAT_VAR5:
				return getFloatVar5();
			case ArtefactPackage.FILE__DOUBLE_VAR1:
				return getDoubleVar1();
			case ArtefactPackage.FILE__DOUBLE_VAR2:
				return getDoubleVar2();
			case ArtefactPackage.FILE__DOUBLE_VAR3:
				return getDoubleVar3();
			case ArtefactPackage.FILE__DOUBLE_VAR4:
				return getDoubleVar4();
			case ArtefactPackage.FILE__DOUBLE_VAR5:
				return getDoubleVar5();
			case ArtefactPackage.FILE__BOOLEAN_VAR1:
				return getBooleanVar1();
			case ArtefactPackage.FILE__BOOLEAN_VAR2:
				return isBooleanVar2();
			case ArtefactPackage.FILE__BOOLEAN_VAR3:
				return isBooleanVar3();
			case ArtefactPackage.FILE__BOOLEAN_VAR4:
				return isBooleanVar4();
			case ArtefactPackage.FILE__BOOLEAN_VAR5:
				return isBooleanVar5();
			case ArtefactPackage.FILE__STRING_VAR1:
				return isStringVar1();
			case ArtefactPackage.FILE__STRING_VAR2:
				return isStringVar2();
			case ArtefactPackage.FILE__STRING_VAR3:
				return isStringVar3();
			case ArtefactPackage.FILE__STRING_VAR4:
				return isStringVar4();
			case ArtefactPackage.FILE__STRING_VAR5:
				return isStringVar5();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ArtefactPackage.FILE__TESTED:
				setTested((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__ID:
				setId((String)newValue);
				return;
			case ArtefactPackage.FILE__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case ArtefactPackage.FILE__BYTE_VAR1:
				setByteVar1((Short)newValue);
				return;
			case ArtefactPackage.FILE__BYTE_VAR2:
				setByteVar2((Integer)newValue);
				return;
			case ArtefactPackage.FILE__BYTE_VAR3:
				setByteVar3((Long)newValue);
				return;
			case ArtefactPackage.FILE__BYTE_VAR4:
				setByteVar4((Float)newValue);
				return;
			case ArtefactPackage.FILE__BYTE_VAR5:
				setByteVar5((Double)newValue);
				return;
			case ArtefactPackage.FILE__SHORT_VAR1:
				setShortVar1((Integer)newValue);
				return;
			case ArtefactPackage.FILE__SHORT_VAR2:
				setShortVar2((Long)newValue);
				return;
			case ArtefactPackage.FILE__SHORT_VAR3:
				setShortVar3((Float)newValue);
				return;
			case ArtefactPackage.FILE__SHORT_VAR4:
				setShortVar4((Double)newValue);
				return;
			case ArtefactPackage.FILE__SHORT_VAR5:
				setShortVar5((Short)newValue);
				return;
			case ArtefactPackage.FILE__INT_VAR1:
				setIntVar1((Long)newValue);
				return;
			case ArtefactPackage.FILE__INT_VAR2:
				setIntVar2((Float)newValue);
				return;
			case ArtefactPackage.FILE__INT_VAR3:
				setIntVar3((Double)newValue);
				return;
			case ArtefactPackage.FILE__INT_VAR4:
				setIntVar4((Integer)newValue);
				return;
			case ArtefactPackage.FILE__INT_VAR5:
				setIntVar5((Integer)newValue);
				return;
			case ArtefactPackage.FILE__CHAR_VAR1:
				setCharVar1((Integer)newValue);
				return;
			case ArtefactPackage.FILE__CHAR_VAR2:
				setCharVar2((Long)newValue);
				return;
			case ArtefactPackage.FILE__CHAR_VAR3:
				setCharVar3((Float)newValue);
				return;
			case ArtefactPackage.FILE__CHAR_VAR4:
				setCharVar4((Double)newValue);
				return;
			case ArtefactPackage.FILE__CHAR_VAR5:
				setCharVar5((Integer)newValue);
				return;
			case ArtefactPackage.FILE__LONG_VAR1:
				setLongVar1((Float)newValue);
				return;
			case ArtefactPackage.FILE__LONG_VAR2:
				setLongVar2((Double)newValue);
				return;
			case ArtefactPackage.FILE__LONG_VAR3:
				setLongVar3((Long)newValue);
				return;
			case ArtefactPackage.FILE__LONG_VAR4:
				setLongVar4((Long)newValue);
				return;
			case ArtefactPackage.FILE__LONG_VAR5:
				setLongVar5((Long)newValue);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR1:
				setFloatVar1((Double)newValue);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR2:
				setFloatVar2((Float)newValue);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR3:
				setFloatVar3((Float)newValue);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR4:
				setFloatVar4((Float)newValue);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR5:
				setFloatVar5((Float)newValue);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR1:
				setDoubleVar1((Double)newValue);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR2:
				setDoubleVar2((Double)newValue);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR3:
				setDoubleVar3((Double)newValue);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR4:
				setDoubleVar4((Double)newValue);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR5:
				setDoubleVar5((Double)newValue);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR1:
				setBooleanVar1((String)newValue);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR2:
				setBooleanVar2((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR3:
				setBooleanVar3((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR4:
				setBooleanVar4((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR5:
				setBooleanVar5((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__STRING_VAR1:
				setStringVar1((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__STRING_VAR2:
				setStringVar2((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__STRING_VAR3:
				setStringVar3((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__STRING_VAR4:
				setStringVar4((Boolean)newValue);
				return;
			case ArtefactPackage.FILE__STRING_VAR5:
				setStringVar5((Boolean)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ArtefactPackage.FILE__TESTED:
				setTested(TESTED_EDEFAULT);
				return;
			case ArtefactPackage.FILE__ID:
				setId(ID_EDEFAULT);
				return;
			case ArtefactPackage.FILE__CONTENTS:
				getContents().clear();
				return;
			case ArtefactPackage.FILE__BYTE_VAR1:
				setByteVar1(BYTE_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BYTE_VAR2:
				setByteVar2(BYTE_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BYTE_VAR3:
				setByteVar3(BYTE_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BYTE_VAR4:
				setByteVar4(BYTE_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BYTE_VAR5:
				setByteVar5(BYTE_VAR5_EDEFAULT);
				return;
			case ArtefactPackage.FILE__SHORT_VAR1:
				setShortVar1(SHORT_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__SHORT_VAR2:
				setShortVar2(SHORT_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__SHORT_VAR3:
				setShortVar3(SHORT_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__SHORT_VAR4:
				setShortVar4(SHORT_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__SHORT_VAR5:
				setShortVar5(SHORT_VAR5_EDEFAULT);
				return;
			case ArtefactPackage.FILE__INT_VAR1:
				setIntVar1(INT_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__INT_VAR2:
				setIntVar2(INT_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__INT_VAR3:
				setIntVar3(INT_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__INT_VAR4:
				setIntVar4(INT_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__INT_VAR5:
				setIntVar5(INT_VAR5_EDEFAULT);
				return;
			case ArtefactPackage.FILE__CHAR_VAR1:
				setCharVar1(CHAR_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__CHAR_VAR2:
				setCharVar2(CHAR_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__CHAR_VAR3:
				setCharVar3(CHAR_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__CHAR_VAR4:
				setCharVar4(CHAR_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__CHAR_VAR5:
				setCharVar5(CHAR_VAR5_EDEFAULT);
				return;
			case ArtefactPackage.FILE__LONG_VAR1:
				setLongVar1(LONG_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__LONG_VAR2:
				setLongVar2(LONG_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__LONG_VAR3:
				setLongVar3(LONG_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__LONG_VAR4:
				setLongVar4(LONG_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__LONG_VAR5:
				setLongVar5(LONG_VAR5_EDEFAULT);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR1:
				setFloatVar1(FLOAT_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR2:
				setFloatVar2(FLOAT_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR3:
				setFloatVar3(FLOAT_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR4:
				setFloatVar4(FLOAT_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__FLOAT_VAR5:
				setFloatVar5(FLOAT_VAR5_EDEFAULT);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR1:
				setDoubleVar1(DOUBLE_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR2:
				setDoubleVar2(DOUBLE_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR3:
				setDoubleVar3(DOUBLE_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR4:
				setDoubleVar4(DOUBLE_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__DOUBLE_VAR5:
				setDoubleVar5(DOUBLE_VAR5_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR1:
				setBooleanVar1(BOOLEAN_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR2:
				setBooleanVar2(BOOLEAN_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR3:
				setBooleanVar3(BOOLEAN_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR4:
				setBooleanVar4(BOOLEAN_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__BOOLEAN_VAR5:
				setBooleanVar5(BOOLEAN_VAR5_EDEFAULT);
				return;
			case ArtefactPackage.FILE__STRING_VAR1:
				setStringVar1(STRING_VAR1_EDEFAULT);
				return;
			case ArtefactPackage.FILE__STRING_VAR2:
				setStringVar2(STRING_VAR2_EDEFAULT);
				return;
			case ArtefactPackage.FILE__STRING_VAR3:
				setStringVar3(STRING_VAR3_EDEFAULT);
				return;
			case ArtefactPackage.FILE__STRING_VAR4:
				setStringVar4(STRING_VAR4_EDEFAULT);
				return;
			case ArtefactPackage.FILE__STRING_VAR5:
				setStringVar5(STRING_VAR5_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ArtefactPackage.FILE__TESTED:
				return isTested() != TESTED_EDEFAULT;
			case ArtefactPackage.FILE__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case ArtefactPackage.FILE__CONTENTS:
				return !getContents().isEmpty();
			case ArtefactPackage.FILE__BYTE_VAR1:
				return getByteVar1() != BYTE_VAR1_EDEFAULT;
			case ArtefactPackage.FILE__BYTE_VAR2:
				return getByteVar2() != BYTE_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__BYTE_VAR3:
				return getByteVar3() != BYTE_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__BYTE_VAR4:
				return getByteVar4() != BYTE_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__BYTE_VAR5:
				return getByteVar5() != BYTE_VAR5_EDEFAULT;
			case ArtefactPackage.FILE__SHORT_VAR1:
				return getShortVar1() != SHORT_VAR1_EDEFAULT;
			case ArtefactPackage.FILE__SHORT_VAR2:
				return getShortVar2() != SHORT_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__SHORT_VAR3:
				return getShortVar3() != SHORT_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__SHORT_VAR4:
				return getShortVar4() != SHORT_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__SHORT_VAR5:
				return getShortVar5() != SHORT_VAR5_EDEFAULT;
			case ArtefactPackage.FILE__INT_VAR1:
				return getIntVar1() != INT_VAR1_EDEFAULT;
			case ArtefactPackage.FILE__INT_VAR2:
				return getIntVar2() != INT_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__INT_VAR3:
				return getIntVar3() != INT_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__INT_VAR4:
				return getIntVar4() != INT_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__INT_VAR5:
				return getIntVar5() != INT_VAR5_EDEFAULT;
			case ArtefactPackage.FILE__CHAR_VAR1:
				return getCharVar1() != CHAR_VAR1_EDEFAULT;
			case ArtefactPackage.FILE__CHAR_VAR2:
				return getCharVar2() != CHAR_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__CHAR_VAR3:
				return getCharVar3() != CHAR_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__CHAR_VAR4:
				return getCharVar4() != CHAR_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__CHAR_VAR5:
				return getCharVar5() != CHAR_VAR5_EDEFAULT;
			case ArtefactPackage.FILE__LONG_VAR1:
				return getLongVar1() != LONG_VAR1_EDEFAULT;
			case ArtefactPackage.FILE__LONG_VAR2:
				return getLongVar2() != LONG_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__LONG_VAR3:
				return getLongVar3() != LONG_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__LONG_VAR4:
				return getLongVar4() != LONG_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__LONG_VAR5:
				return getLongVar5() != LONG_VAR5_EDEFAULT;
			case ArtefactPackage.FILE__FLOAT_VAR1:
				return getFloatVar1() != FLOAT_VAR1_EDEFAULT;
			case ArtefactPackage.FILE__FLOAT_VAR2:
				return getFloatVar2() != FLOAT_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__FLOAT_VAR3:
				return getFloatVar3() != FLOAT_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__FLOAT_VAR4:
				return getFloatVar4() != FLOAT_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__FLOAT_VAR5:
				return getFloatVar5() != FLOAT_VAR5_EDEFAULT;
			case ArtefactPackage.FILE__DOUBLE_VAR1:
				return getDoubleVar1() != DOUBLE_VAR1_EDEFAULT;
			case ArtefactPackage.FILE__DOUBLE_VAR2:
				return getDoubleVar2() != DOUBLE_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__DOUBLE_VAR3:
				return getDoubleVar3() != DOUBLE_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__DOUBLE_VAR4:
				return getDoubleVar4() != DOUBLE_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__DOUBLE_VAR5:
				return getDoubleVar5() != DOUBLE_VAR5_EDEFAULT;
			case ArtefactPackage.FILE__BOOLEAN_VAR1:
				return BOOLEAN_VAR1_EDEFAULT == null ? getBooleanVar1() != null : !BOOLEAN_VAR1_EDEFAULT.equals(getBooleanVar1());
			case ArtefactPackage.FILE__BOOLEAN_VAR2:
				return isBooleanVar2() != BOOLEAN_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__BOOLEAN_VAR3:
				return isBooleanVar3() != BOOLEAN_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__BOOLEAN_VAR4:
				return isBooleanVar4() != BOOLEAN_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__BOOLEAN_VAR5:
				return isBooleanVar5() != BOOLEAN_VAR5_EDEFAULT;
			case ArtefactPackage.FILE__STRING_VAR1:
				return isStringVar1() != STRING_VAR1_EDEFAULT;
			case ArtefactPackage.FILE__STRING_VAR2:
				return isStringVar2() != STRING_VAR2_EDEFAULT;
			case ArtefactPackage.FILE__STRING_VAR3:
				return isStringVar3() != STRING_VAR3_EDEFAULT;
			case ArtefactPackage.FILE__STRING_VAR4:
				return isStringVar4() != STRING_VAR4_EDEFAULT;
			case ArtefactPackage.FILE__STRING_VAR5:
				return isStringVar5() != STRING_VAR5_EDEFAULT;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eBaseStructuralFeatureID(int derivedFeatureID, Class<?> baseClass) {
		if (baseClass == IID.class) {
			switch (derivedFeatureID) {
				case ArtefactPackage.FILE__ID: return BasePackage.IID__ID;
				default: return -1;
			}
		}
		if (baseClass == IContentElement.class) {
			switch (derivedFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IContainer.class) {
			switch (derivedFeatureID) {
				case ArtefactPackage.FILE__CONTENTS: return BasePackage.ICONTAINER__CONTENTS;
				default: return -1;
			}
		}
		return super.eBaseStructuralFeatureID(derivedFeatureID, baseClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public int eDerivedStructuralFeatureID(int baseFeatureID, Class<?> baseClass) {
		if (baseClass == IID.class) {
			switch (baseFeatureID) {
				case BasePackage.IID__ID: return ArtefactPackage.FILE__ID;
				default: return -1;
			}
		}
		if (baseClass == IContentElement.class) {
			switch (baseFeatureID) {
				default: return -1;
			}
		}
		if (baseClass == IContainer.class) {
			switch (baseFeatureID) {
				case BasePackage.ICONTAINER__CONTENTS: return ArtefactPackage.FILE__CONTENTS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //FileImpl
