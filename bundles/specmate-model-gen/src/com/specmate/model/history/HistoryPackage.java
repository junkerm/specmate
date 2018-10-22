/**
 */
package com.specmate.model.history;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see com.specmate.model.history.HistoryFactory
 * @model kind="package"
 * @generated
 */
public interface HistoryPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "history";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://specmate.com/20180925/model/history";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "com.specmate.model.processes";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	HistoryPackage eINSTANCE = com.specmate.model.history.impl.HistoryPackageImpl.init();

	/**
	 * The meta object id for the '{@link com.specmate.model.history.impl.HistoryImpl <em>History</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.history.impl.HistoryImpl
	 * @see com.specmate.model.history.impl.HistoryPackageImpl#getHistory()
	 * @generated
	 */
	int HISTORY = 0;

	/**
	 * The feature id for the '<em><b>Entries</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY__ENTRIES = 0;

	/**
	 * The number of structural features of the '<em>History</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>History</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.history.impl.HistoryEntryImpl <em>Entry</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.history.impl.HistoryEntryImpl
	 * @see com.specmate.model.history.impl.HistoryPackageImpl#getHistoryEntry()
	 * @generated
	 */
	int HISTORY_ENTRY = 1;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_ENTRY__TIMESTAMP = 0;

	/**
	 * The feature id for the '<em><b>User</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_ENTRY__USER = 1;

	/**
	 * The feature id for the '<em><b>Deleted Objects</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_ENTRY__DELETED_OBJECTS = 2;

	/**
	 * The feature id for the '<em><b>Comment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_ENTRY__COMMENT = 3;

	/**
	 * The feature id for the '<em><b>Changes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_ENTRY__CHANGES = 4;

	/**
	 * The number of structural features of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_ENTRY_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Entry</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HISTORY_ENTRY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link com.specmate.model.history.impl.ChangeImpl <em>Change</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see com.specmate.model.history.impl.ChangeImpl
	 * @see com.specmate.model.history.impl.HistoryPackageImpl#getChange()
	 * @generated
	 */
	int CHANGE = 2;

	/**
	 * The feature id for the '<em><b>Object Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE__OBJECT_NAME = 0;

	/**
	 * The feature id for the '<em><b>Object Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE__OBJECT_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Old Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE__OLD_VALUE = 2;

	/**
	 * The feature id for the '<em><b>New Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE__NEW_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE__FEATURE = 4;

	/**
	 * The feature id for the '<em><b>Is Create</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE__IS_CREATE = 5;

	/**
	 * The feature id for the '<em><b>Is Delete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE__IS_DELETE = 6;

	/**
	 * The number of structural features of the '<em>Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Change</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CHANGE_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link com.specmate.model.history.History <em>History</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>History</em>'.
	 * @see com.specmate.model.history.History
	 * @generated
	 */
	EClass getHistory();

	/**
	 * Returns the meta object for the containment reference list '{@link com.specmate.model.history.History#getEntries <em>Entries</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Entries</em>'.
	 * @see com.specmate.model.history.History#getEntries()
	 * @see #getHistory()
	 * @generated
	 */
	EReference getHistory_Entries();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.history.HistoryEntry <em>Entry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entry</em>'.
	 * @see com.specmate.model.history.HistoryEntry
	 * @generated
	 */
	EClass getHistoryEntry();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.HistoryEntry#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see com.specmate.model.history.HistoryEntry#getTimestamp()
	 * @see #getHistoryEntry()
	 * @generated
	 */
	EAttribute getHistoryEntry_Timestamp();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.HistoryEntry#getUser <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>User</em>'.
	 * @see com.specmate.model.history.HistoryEntry#getUser()
	 * @see #getHistoryEntry()
	 * @generated
	 */
	EAttribute getHistoryEntry_User();

	/**
	 * Returns the meta object for the attribute list '{@link com.specmate.model.history.HistoryEntry#getDeletedObjects <em>Deleted Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Deleted Objects</em>'.
	 * @see com.specmate.model.history.HistoryEntry#getDeletedObjects()
	 * @see #getHistoryEntry()
	 * @generated
	 */
	EAttribute getHistoryEntry_DeletedObjects();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.HistoryEntry#getComment <em>Comment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comment</em>'.
	 * @see com.specmate.model.history.HistoryEntry#getComment()
	 * @see #getHistoryEntry()
	 * @generated
	 */
	EAttribute getHistoryEntry_Comment();

	/**
	 * Returns the meta object for the containment reference list '{@link com.specmate.model.history.HistoryEntry#getChanges <em>Changes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Changes</em>'.
	 * @see com.specmate.model.history.HistoryEntry#getChanges()
	 * @see #getHistoryEntry()
	 * @generated
	 */
	EReference getHistoryEntry_Changes();

	/**
	 * Returns the meta object for class '{@link com.specmate.model.history.Change <em>Change</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Change</em>'.
	 * @see com.specmate.model.history.Change
	 * @generated
	 */
	EClass getChange();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.Change#getObjectName <em>Object Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Name</em>'.
	 * @see com.specmate.model.history.Change#getObjectName()
	 * @see #getChange()
	 * @generated
	 */
	EAttribute getChange_ObjectName();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.Change#getObjectType <em>Object Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Object Type</em>'.
	 * @see com.specmate.model.history.Change#getObjectType()
	 * @see #getChange()
	 * @generated
	 */
	EAttribute getChange_ObjectType();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.Change#getOldValue <em>Old Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Old Value</em>'.
	 * @see com.specmate.model.history.Change#getOldValue()
	 * @see #getChange()
	 * @generated
	 */
	EAttribute getChange_OldValue();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.Change#getNewValue <em>New Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>New Value</em>'.
	 * @see com.specmate.model.history.Change#getNewValue()
	 * @see #getChange()
	 * @generated
	 */
	EAttribute getChange_NewValue();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.Change#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature</em>'.
	 * @see com.specmate.model.history.Change#getFeature()
	 * @see #getChange()
	 * @generated
	 */
	EAttribute getChange_Feature();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.Change#isIsCreate <em>Is Create</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Create</em>'.
	 * @see com.specmate.model.history.Change#isIsCreate()
	 * @see #getChange()
	 * @generated
	 */
	EAttribute getChange_IsCreate();

	/**
	 * Returns the meta object for the attribute '{@link com.specmate.model.history.Change#isIsDelete <em>Is Delete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Delete</em>'.
	 * @see com.specmate.model.history.Change#isIsDelete()
	 * @see #getChange()
	 * @generated
	 */
	EAttribute getChange_IsDelete();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	HistoryFactory getHistoryFactory();

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
		 * The meta object literal for the '{@link com.specmate.model.history.impl.HistoryImpl <em>History</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.history.impl.HistoryImpl
		 * @see com.specmate.model.history.impl.HistoryPackageImpl#getHistory()
		 * @generated
		 */
		EClass HISTORY = eINSTANCE.getHistory();

		/**
		 * The meta object literal for the '<em><b>Entries</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HISTORY__ENTRIES = eINSTANCE.getHistory_Entries();

		/**
		 * The meta object literal for the '{@link com.specmate.model.history.impl.HistoryEntryImpl <em>Entry</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.history.impl.HistoryEntryImpl
		 * @see com.specmate.model.history.impl.HistoryPackageImpl#getHistoryEntry()
		 * @generated
		 */
		EClass HISTORY_ENTRY = eINSTANCE.getHistoryEntry();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTORY_ENTRY__TIMESTAMP = eINSTANCE.getHistoryEntry_Timestamp();

		/**
		 * The meta object literal for the '<em><b>User</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTORY_ENTRY__USER = eINSTANCE.getHistoryEntry_User();

		/**
		 * The meta object literal for the '<em><b>Deleted Objects</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTORY_ENTRY__DELETED_OBJECTS = eINSTANCE.getHistoryEntry_DeletedObjects();

		/**
		 * The meta object literal for the '<em><b>Comment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute HISTORY_ENTRY__COMMENT = eINSTANCE.getHistoryEntry_Comment();

		/**
		 * The meta object literal for the '<em><b>Changes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HISTORY_ENTRY__CHANGES = eINSTANCE.getHistoryEntry_Changes();

		/**
		 * The meta object literal for the '{@link com.specmate.model.history.impl.ChangeImpl <em>Change</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see com.specmate.model.history.impl.ChangeImpl
		 * @see com.specmate.model.history.impl.HistoryPackageImpl#getChange()
		 * @generated
		 */
		EClass CHANGE = eINSTANCE.getChange();

		/**
		 * The meta object literal for the '<em><b>Object Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE__OBJECT_NAME = eINSTANCE.getChange_ObjectName();

		/**
		 * The meta object literal for the '<em><b>Object Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE__OBJECT_TYPE = eINSTANCE.getChange_ObjectType();

		/**
		 * The meta object literal for the '<em><b>Old Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE__OLD_VALUE = eINSTANCE.getChange_OldValue();

		/**
		 * The meta object literal for the '<em><b>New Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE__NEW_VALUE = eINSTANCE.getChange_NewValue();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE__FEATURE = eINSTANCE.getChange_Feature();

		/**
		 * The meta object literal for the '<em><b>Is Create</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE__IS_CREATE = eINSTANCE.getChange_IsCreate();

		/**
		 * The meta object literal for the '<em><b>Is Delete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CHANGE__IS_DELETE = eINSTANCE.getChange_IsDelete();

	}

} //HistoryPackage
