/**
 */
package com.specmate.model.requirements.impl;

import com.specmate.model.base.BasePackage;
import com.specmate.model.base.IContentElement;
import com.specmate.model.base.IDescribed;
import com.specmate.model.base.INamed;
import com.specmate.model.base.ITracingElement;

import com.specmate.model.requirements.CEGModel;
import com.specmate.model.requirements.RequirementsPackage;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>CEG Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.requirements.impl.CEGModelImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGModelImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGModelImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGModelImpl#getContents <em>Contents</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGModelImpl#getTracesTo <em>Traces To</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGModelImpl#getTracesFrom <em>Traces From</em>}</li>
 *   <li>{@link com.specmate.model.requirements.impl.CEGModelImpl#getModelRequirements <em>Model Requirements</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CEGModelImpl extends CDOObjectImpl implements CEGModel {
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
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getModelRequirements() <em>Model Requirements</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getModelRequirements()
	 * @generated
	 * @ordered
	 */
	protected static final String MODEL_REQUIREMENTS_EDEFAULT = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CEGModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return RequirementsPackage.Literals.CEG_MODEL;
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
	public String getId() {
		return (String)eDynamicGet(RequirementsPackage.CEG_MODEL__ID, BasePackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setId(String newId) {
		eDynamicSet(RequirementsPackage.CEG_MODEL__ID, BasePackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getName() {
		return (String)eDynamicGet(RequirementsPackage.CEG_MODEL__NAME, BasePackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		eDynamicSet(RequirementsPackage.CEG_MODEL__NAME, BasePackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getDescription() {
		return (String)eDynamicGet(RequirementsPackage.CEG_MODEL__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		eDynamicSet(RequirementsPackage.CEG_MODEL__DESCRIPTION, BasePackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(RequirementsPackage.CEG_MODEL__CONTENTS, BasePackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<ITracingElement> getTracesTo() {
		return (EList<ITracingElement>)eDynamicGet(RequirementsPackage.CEG_MODEL__TRACES_TO, BasePackage.Literals.ITRACING_ELEMENT__TRACES_TO, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public EList<ITracingElement> getTracesFrom() {
		return (EList<ITracingElement>)eDynamicGet(RequirementsPackage.CEG_MODEL__TRACES_FROM, BasePackage.Literals.ITRACING_ELEMENT__TRACES_FROM, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String getModelRequirements() {
		return (String)eDynamicGet(RequirementsPackage.CEG_MODEL__MODEL_REQUIREMENTS, RequirementsPackage.Literals.CEG_MODEL__MODEL_REQUIREMENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void setModelRequirements(String newModelRequirements) {
		eDynamicSet(RequirementsPackage.CEG_MODEL__MODEL_REQUIREMENTS, RequirementsPackage.Literals.CEG_MODEL__MODEL_REQUIREMENTS, newModelRequirements);
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
			case RequirementsPackage.CEG_MODEL__TRACES_TO:
				return ((InternalEList<InternalEObject>)(InternalEList<?>)getTracesTo()).basicAdd(otherEnd, msgs);
			case RequirementsPackage.CEG_MODEL__TRACES_FROM:
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
			case RequirementsPackage.CEG_MODEL__CONTENTS:
				return ((InternalEList<?>)getContents()).basicRemove(otherEnd, msgs);
			case RequirementsPackage.CEG_MODEL__TRACES_TO:
				return ((InternalEList<?>)getTracesTo()).basicRemove(otherEnd, msgs);
			case RequirementsPackage.CEG_MODEL__TRACES_FROM:
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
			case RequirementsPackage.CEG_MODEL__ID:
				return getId();
			case RequirementsPackage.CEG_MODEL__NAME:
				return getName();
			case RequirementsPackage.CEG_MODEL__DESCRIPTION:
				return getDescription();
			case RequirementsPackage.CEG_MODEL__CONTENTS:
				return getContents();
			case RequirementsPackage.CEG_MODEL__TRACES_TO:
				return getTracesTo();
			case RequirementsPackage.CEG_MODEL__TRACES_FROM:
				return getTracesFrom();
			case RequirementsPackage.CEG_MODEL__MODEL_REQUIREMENTS:
				return getModelRequirements();
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
			case RequirementsPackage.CEG_MODEL__ID:
				setId((String)newValue);
				return;
			case RequirementsPackage.CEG_MODEL__NAME:
				setName((String)newValue);
				return;
			case RequirementsPackage.CEG_MODEL__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case RequirementsPackage.CEG_MODEL__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
				return;
			case RequirementsPackage.CEG_MODEL__TRACES_TO:
				getTracesTo().clear();
				getTracesTo().addAll((Collection<? extends ITracingElement>)newValue);
				return;
			case RequirementsPackage.CEG_MODEL__TRACES_FROM:
				getTracesFrom().clear();
				getTracesFrom().addAll((Collection<? extends ITracingElement>)newValue);
				return;
			case RequirementsPackage.CEG_MODEL__MODEL_REQUIREMENTS:
				setModelRequirements((String)newValue);
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
			case RequirementsPackage.CEG_MODEL__ID:
				setId(ID_EDEFAULT);
				return;
			case RequirementsPackage.CEG_MODEL__NAME:
				setName(NAME_EDEFAULT);
				return;
			case RequirementsPackage.CEG_MODEL__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case RequirementsPackage.CEG_MODEL__CONTENTS:
				getContents().clear();
				return;
			case RequirementsPackage.CEG_MODEL__TRACES_TO:
				getTracesTo().clear();
				return;
			case RequirementsPackage.CEG_MODEL__TRACES_FROM:
				getTracesFrom().clear();
				return;
			case RequirementsPackage.CEG_MODEL__MODEL_REQUIREMENTS:
				setModelRequirements(MODEL_REQUIREMENTS_EDEFAULT);
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
			case RequirementsPackage.CEG_MODEL__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case RequirementsPackage.CEG_MODEL__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case RequirementsPackage.CEG_MODEL__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case RequirementsPackage.CEG_MODEL__CONTENTS:
				return !getContents().isEmpty();
			case RequirementsPackage.CEG_MODEL__TRACES_TO:
				return !getTracesTo().isEmpty();
			case RequirementsPackage.CEG_MODEL__TRACES_FROM:
				return !getTracesFrom().isEmpty();
			case RequirementsPackage.CEG_MODEL__MODEL_REQUIREMENTS:
				return MODEL_REQUIREMENTS_EDEFAULT == null ? getModelRequirements() != null : !MODEL_REQUIREMENTS_EDEFAULT.equals(getModelRequirements());
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
		if (baseClass == INamed.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.CEG_MODEL__NAME: return BasePackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.CEG_MODEL__DESCRIPTION: return BasePackage.IDESCRIBED__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == ITracingElement.class) {
			switch (derivedFeatureID) {
				case RequirementsPackage.CEG_MODEL__TRACES_TO: return BasePackage.ITRACING_ELEMENT__TRACES_TO;
				case RequirementsPackage.CEG_MODEL__TRACES_FROM: return BasePackage.ITRACING_ELEMENT__TRACES_FROM;
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
		if (baseClass == INamed.class) {
			switch (baseFeatureID) {
				case BasePackage.INAMED__NAME: return RequirementsPackage.CEG_MODEL__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasePackage.IDESCRIBED__DESCRIPTION: return RequirementsPackage.CEG_MODEL__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == ITracingElement.class) {
			switch (baseFeatureID) {
				case BasePackage.ITRACING_ELEMENT__TRACES_TO: return RequirementsPackage.CEG_MODEL__TRACES_TO;
				case BasePackage.ITRACING_ELEMENT__TRACES_FROM: return RequirementsPackage.CEG_MODEL__TRACES_FROM;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //CEGModelImpl
