/**
 */
package com.specmate.model.batch.impl;

import com.specmate.model.base.IContentElement;
import com.specmate.model.batch.BatchPackage;
import com.specmate.model.batch.Operation;
import com.specmate.model.batch.OperationType;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Operation</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.batch.impl.OperationImpl#getType <em>Type</em>}</li>
 *   <li>{@link com.specmate.model.batch.impl.OperationImpl#getTarget <em>Target</em>}</li>
 *   <li>{@link com.specmate.model.batch.impl.OperationImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class OperationImpl extends CDOObjectImpl implements Operation {
	/**
	 * The default value of the '{@link #getType() <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getType()
	 * @generated
	 * @ordered
	 */
	protected static final OperationType TYPE_EDEFAULT = OperationType.CREATE;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OperationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BatchPackage.Literals.OPERATION;
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
	@Override
	public OperationType getType() {
		return (OperationType)eDynamicGet(BatchPackage.OPERATION__TYPE, BatchPackage.Literals.OPERATION__TYPE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setType(OperationType newType) {
		eDynamicSet(BatchPackage.OPERATION__TYPE, BatchPackage.Literals.OPERATION__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IContentElement getTarget() {
		return (IContentElement)eDynamicGet(BatchPackage.OPERATION__TARGET, BatchPackage.Literals.OPERATION__TARGET, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public IContentElement basicGetTarget() {
		return (IContentElement)eDynamicGet(BatchPackage.OPERATION__TARGET, BatchPackage.Literals.OPERATION__TARGET, false, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setTarget(IContentElement newTarget) {
		eDynamicSet(BatchPackage.OPERATION__TARGET, BatchPackage.Literals.OPERATION__TARGET, newTarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public IContentElement getValue() {
		return (IContentElement)eDynamicGet(BatchPackage.OPERATION__VALUE, BatchPackage.Literals.OPERATION__VALUE, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetValue(IContentElement newValue, NotificationChain msgs) {
		msgs = eDynamicInverseAdd((InternalEObject)newValue, BatchPackage.OPERATION__VALUE, msgs);
		return msgs;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setValue(IContentElement newValue) {
		eDynamicSet(BatchPackage.OPERATION__VALUE, BatchPackage.Literals.OPERATION__VALUE, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BatchPackage.OPERATION__VALUE:
				return basicSetValue(null, msgs);
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
			case BatchPackage.OPERATION__TYPE:
				return getType();
			case BatchPackage.OPERATION__TARGET:
				if (resolve) return getTarget();
				return basicGetTarget();
			case BatchPackage.OPERATION__VALUE:
				return getValue();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case BatchPackage.OPERATION__TYPE:
				setType((OperationType)newValue);
				return;
			case BatchPackage.OPERATION__TARGET:
				setTarget((IContentElement)newValue);
				return;
			case BatchPackage.OPERATION__VALUE:
				setValue((IContentElement)newValue);
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
			case BatchPackage.OPERATION__TYPE:
				setType(TYPE_EDEFAULT);
				return;
			case BatchPackage.OPERATION__TARGET:
				setTarget((IContentElement)null);
				return;
			case BatchPackage.OPERATION__VALUE:
				setValue((IContentElement)null);
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
			case BatchPackage.OPERATION__TYPE:
				return getType() != TYPE_EDEFAULT;
			case BatchPackage.OPERATION__TARGET:
				return basicGetTarget() != null;
			case BatchPackage.OPERATION__VALUE:
				return getValue() != null;
		}
		return super.eIsSet(featureID);
	}

} //OperationImpl
