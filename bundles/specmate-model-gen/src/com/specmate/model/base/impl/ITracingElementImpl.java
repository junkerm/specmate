/**
 */
package com.specmate.model.base.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.ITracingElement;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>ITracing Element</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.base.impl.ITracingElementImpl#getTracesTo <em>Traces To</em>}</li>
 *   <li>{@link com.specmate.model.base.impl.ITracingElementImpl#getTracesFrom <em>Traces From</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ITracingElementImpl extends CDOObjectImpl implements ITracingElement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ITracingElementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasePackage.Literals.ITRACING_ELEMENT;
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
	@SuppressWarnings("unchecked")
	@Override
	public EList<ITracingElement> getTracesTo() {
		return (EList<ITracingElement>)eDynamicGet(BasePackage.ITRACING_ELEMENT__TRACES_TO, BasePackage.Literals.ITRACING_ELEMENT__TRACES_TO, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<ITracingElement> getTracesFrom() {
		return (EList<ITracingElement>)eDynamicGet(BasePackage.ITRACING_ELEMENT__TRACES_FROM, BasePackage.Literals.ITRACING_ELEMENT__TRACES_FROM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasePackage.ITRACING_ELEMENT__TRACES_TO:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTracesTo()).basicAdd(otherEnd, msgs);
			case BasePackage.ITRACING_ELEMENT__TRACES_FROM:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTracesFrom()).basicAdd(otherEnd, msgs);
		}
		return super.eInverseAdd(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasePackage.ITRACING_ELEMENT__TRACES_TO:
				return ((InternalEList<?>)getTracesTo()).basicRemove(otherEnd, msgs);
			case BasePackage.ITRACING_ELEMENT__TRACES_FROM:
				return ((InternalEList<?>)getTracesFrom()).basicRemove(otherEnd, msgs);
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
			case BasePackage.ITRACING_ELEMENT__TRACES_TO:
				return getTracesTo();
			case BasePackage.ITRACING_ELEMENT__TRACES_FROM:
				return getTracesFrom();
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
			case BasePackage.ITRACING_ELEMENT__TRACES_TO:
				getTracesTo().clear();
				getTracesTo().addAll((Collection<? extends ITracingElement>)newValue);
				return;
			case BasePackage.ITRACING_ELEMENT__TRACES_FROM:
				getTracesFrom().clear();
				getTracesFrom().addAll((Collection<? extends ITracingElement>)newValue);
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
			case BasePackage.ITRACING_ELEMENT__TRACES_TO:
				getTracesTo().clear();
				return;
			case BasePackage.ITRACING_ELEMENT__TRACES_FROM:
				getTracesFrom().clear();
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
			case BasePackage.ITRACING_ELEMENT__TRACES_TO:
				return !getTracesTo().isEmpty();
			case BasePackage.ITRACING_ELEMENT__TRACES_FROM:
				return !getTracesFrom().isEmpty();
		}
		return super.eIsSet(featureID);
	}

} //ITracingElementImpl
