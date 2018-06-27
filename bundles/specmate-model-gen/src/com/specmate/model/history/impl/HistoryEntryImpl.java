/**
 */
package com.specmate.model.history.impl;

import com.specmate.model.history.Change;
import com.specmate.model.history.HistoryEntry;
import com.specmate.model.history.HistoryPackage;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Entry</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.history.impl.HistoryEntryImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.HistoryEntryImpl#getUser <em>User</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.HistoryEntryImpl#getComment <em>Comment</em>}</li>
 *   <li>{@link com.specmate.model.history.impl.HistoryEntryImpl#getChanges <em>Changes</em>}</li>
 * </ul>
 *
 * @generated
 */
public class HistoryEntryImpl extends CDOObjectImpl implements HistoryEntry {
	/**
	 * The default value of the '{@link #getTimestamp() <em>Timestamp</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTimestamp()
	 * @generated
	 * @ordered
	 */
	protected static final long TIMESTAMP_EDEFAULT = 0L;

	/**
	 * The default value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getComment() <em>Comment</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getComment()
	 * @generated
	 * @ordered
	 */
	protected static final String COMMENT_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected HistoryEntryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return HistoryPackage.Literals.HISTORY_ENTRY;
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
	public long getTimestamp() {
		return (Long)eDynamicGet(HistoryPackage.HISTORY_ENTRY__TIMESTAMP, HistoryPackage.Literals.HISTORY_ENTRY__TIMESTAMP, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestamp(long newTimestamp) {
		eDynamicSet(HistoryPackage.HISTORY_ENTRY__TIMESTAMP, HistoryPackage.Literals.HISTORY_ENTRY__TIMESTAMP, newTimestamp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getUser() {
		return (String)eDynamicGet(HistoryPackage.HISTORY_ENTRY__USER, HistoryPackage.Literals.HISTORY_ENTRY__USER, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUser(String newUser) {
		eDynamicSet(HistoryPackage.HISTORY_ENTRY__USER, HistoryPackage.Literals.HISTORY_ENTRY__USER, newUser);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getComment() {
		return (String)eDynamicGet(HistoryPackage.HISTORY_ENTRY__COMMENT, HistoryPackage.Literals.HISTORY_ENTRY__COMMENT, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComment(String newComment) {
		eDynamicSet(HistoryPackage.HISTORY_ENTRY__COMMENT, HistoryPackage.Literals.HISTORY_ENTRY__COMMENT, newComment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Change> getChanges() {
		return (EList<Change>)eDynamicGet(HistoryPackage.HISTORY_ENTRY__CHANGES, HistoryPackage.Literals.HISTORY_ENTRY__CHANGES, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case HistoryPackage.HISTORY_ENTRY__CHANGES:
				return ((InternalEList<?>)getChanges()).basicRemove(otherEnd, msgs);
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
			case HistoryPackage.HISTORY_ENTRY__TIMESTAMP:
				return getTimestamp();
			case HistoryPackage.HISTORY_ENTRY__USER:
				return getUser();
			case HistoryPackage.HISTORY_ENTRY__COMMENT:
				return getComment();
			case HistoryPackage.HISTORY_ENTRY__CHANGES:
				return getChanges();
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
			case HistoryPackage.HISTORY_ENTRY__TIMESTAMP:
				setTimestamp((Long)newValue);
				return;
			case HistoryPackage.HISTORY_ENTRY__USER:
				setUser((String)newValue);
				return;
			case HistoryPackage.HISTORY_ENTRY__COMMENT:
				setComment((String)newValue);
				return;
			case HistoryPackage.HISTORY_ENTRY__CHANGES:
				getChanges().clear();
				getChanges().addAll((Collection<? extends Change>)newValue);
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
			case HistoryPackage.HISTORY_ENTRY__TIMESTAMP:
				setTimestamp(TIMESTAMP_EDEFAULT);
				return;
			case HistoryPackage.HISTORY_ENTRY__USER:
				setUser(USER_EDEFAULT);
				return;
			case HistoryPackage.HISTORY_ENTRY__COMMENT:
				setComment(COMMENT_EDEFAULT);
				return;
			case HistoryPackage.HISTORY_ENTRY__CHANGES:
				getChanges().clear();
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
			case HistoryPackage.HISTORY_ENTRY__TIMESTAMP:
				return getTimestamp() != TIMESTAMP_EDEFAULT;
			case HistoryPackage.HISTORY_ENTRY__USER:
				return USER_EDEFAULT == null ? getUser() != null : !USER_EDEFAULT.equals(getUser());
			case HistoryPackage.HISTORY_ENTRY__COMMENT:
				return COMMENT_EDEFAULT == null ? getComment() != null : !COMMENT_EDEFAULT.equals(getComment());
			case HistoryPackage.HISTORY_ENTRY__CHANGES:
				return !getChanges().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //HistoryEntryImpl
