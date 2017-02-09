/**
 */
package com.specmate.model.basemodel.impl;

import com.specmate.model.basemodel.BaseModelNode;
import com.specmate.model.basemodel.BasemodelPackage;
import com.specmate.model.basemodel.IAnnotation;
import com.specmate.model.basemodel.IAnnotationContainer;
import com.specmate.model.basemodel.IContentElement;
import com.specmate.model.basemodel.IDescribed;
import com.specmate.model.basemodel.INamed;

import java.util.Collection;

import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.util.InternalEList;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Base Model Node</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link com.specmate.model.basemodel.impl.BaseModelNodeImpl#getId <em>Id</em>}</li>
 *   <li>{@link com.specmate.model.basemodel.impl.BaseModelNodeImpl#getName <em>Name</em>}</li>
 *   <li>{@link com.specmate.model.basemodel.impl.BaseModelNodeImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link com.specmate.model.basemodel.impl.BaseModelNodeImpl#getAnnotations <em>Annotations</em>}</li>
 *   <li>{@link com.specmate.model.basemodel.impl.BaseModelNodeImpl#getContents <em>Contents</em>}</li>
 * </ul>
 *
 * @generated
 */
public class BaseModelNodeImpl extends CDOObjectImpl implements BaseModelNode {
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
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected BaseModelNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return BasemodelPackage.Literals.BASE_MODEL_NODE;
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
	public String getId() {
		return (String)eDynamicGet(BasemodelPackage.BASE_MODEL_NODE__ID, BasemodelPackage.Literals.IID__ID, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eDynamicSet(BasemodelPackage.BASE_MODEL_NODE__ID, BasemodelPackage.Literals.IID__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eDynamicGet(BasemodelPackage.BASE_MODEL_NODE__NAME, BasemodelPackage.Literals.INAMED__NAME, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eDynamicSet(BasemodelPackage.BASE_MODEL_NODE__NAME, BasemodelPackage.Literals.INAMED__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDescription() {
		return (String)eDynamicGet(BasemodelPackage.BASE_MODEL_NODE__DESCRIPTION, BasemodelPackage.Literals.IDESCRIBED__DESCRIPTION, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDescription(String newDescription) {
		eDynamicSet(BasemodelPackage.BASE_MODEL_NODE__DESCRIPTION, BasemodelPackage.Literals.IDESCRIBED__DESCRIPTION, newDescription);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IAnnotation> getAnnotations() {
		return (EList<IAnnotation>)eDynamicGet(BasemodelPackage.BASE_MODEL_NODE__ANNOTATIONS, BasemodelPackage.Literals.IANNOTATION_CONTAINER__ANNOTATIONS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<IContentElement> getContents() {
		return (EList<IContentElement>)eDynamicGet(BasemodelPackage.BASE_MODEL_NODE__CONTENTS, BasemodelPackage.Literals.ICONTAINER__CONTENTS, true, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case BasemodelPackage.BASE_MODEL_NODE__ANNOTATIONS:
				return ((InternalEList<?>)getAnnotations()).basicRemove(otherEnd, msgs);
			case BasemodelPackage.BASE_MODEL_NODE__CONTENTS:
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
			case BasemodelPackage.BASE_MODEL_NODE__ID:
				return getId();
			case BasemodelPackage.BASE_MODEL_NODE__NAME:
				return getName();
			case BasemodelPackage.BASE_MODEL_NODE__DESCRIPTION:
				return getDescription();
			case BasemodelPackage.BASE_MODEL_NODE__ANNOTATIONS:
				return getAnnotations();
			case BasemodelPackage.BASE_MODEL_NODE__CONTENTS:
				return getContents();
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
			case BasemodelPackage.BASE_MODEL_NODE__ID:
				setId((String)newValue);
				return;
			case BasemodelPackage.BASE_MODEL_NODE__NAME:
				setName((String)newValue);
				return;
			case BasemodelPackage.BASE_MODEL_NODE__DESCRIPTION:
				setDescription((String)newValue);
				return;
			case BasemodelPackage.BASE_MODEL_NODE__ANNOTATIONS:
				getAnnotations().clear();
				getAnnotations().addAll((Collection<? extends IAnnotation>)newValue);
				return;
			case BasemodelPackage.BASE_MODEL_NODE__CONTENTS:
				getContents().clear();
				getContents().addAll((Collection<? extends IContentElement>)newValue);
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
			case BasemodelPackage.BASE_MODEL_NODE__ID:
				setId(ID_EDEFAULT);
				return;
			case BasemodelPackage.BASE_MODEL_NODE__NAME:
				setName(NAME_EDEFAULT);
				return;
			case BasemodelPackage.BASE_MODEL_NODE__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case BasemodelPackage.BASE_MODEL_NODE__ANNOTATIONS:
				getAnnotations().clear();
				return;
			case BasemodelPackage.BASE_MODEL_NODE__CONTENTS:
				getContents().clear();
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
			case BasemodelPackage.BASE_MODEL_NODE__ID:
				return ID_EDEFAULT == null ? getId() != null : !ID_EDEFAULT.equals(getId());
			case BasemodelPackage.BASE_MODEL_NODE__NAME:
				return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
			case BasemodelPackage.BASE_MODEL_NODE__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null ? getDescription() != null : !DESCRIPTION_EDEFAULT.equals(getDescription());
			case BasemodelPackage.BASE_MODEL_NODE__ANNOTATIONS:
				return !getAnnotations().isEmpty();
			case BasemodelPackage.BASE_MODEL_NODE__CONTENTS:
				return !getContents().isEmpty();
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
				case BasemodelPackage.BASE_MODEL_NODE__NAME: return BasemodelPackage.INAMED__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (derivedFeatureID) {
				case BasemodelPackage.BASE_MODEL_NODE__DESCRIPTION: return BasemodelPackage.IDESCRIBED__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == IAnnotationContainer.class) {
			switch (derivedFeatureID) {
				case BasemodelPackage.BASE_MODEL_NODE__ANNOTATIONS: return BasemodelPackage.IANNOTATION_CONTAINER__ANNOTATIONS;
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
				case BasemodelPackage.INAMED__NAME: return BasemodelPackage.BASE_MODEL_NODE__NAME;
				default: return -1;
			}
		}
		if (baseClass == IDescribed.class) {
			switch (baseFeatureID) {
				case BasemodelPackage.IDESCRIBED__DESCRIPTION: return BasemodelPackage.BASE_MODEL_NODE__DESCRIPTION;
				default: return -1;
			}
		}
		if (baseClass == IAnnotationContainer.class) {
			switch (baseFeatureID) {
				case BasemodelPackage.IANNOTATION_CONTAINER__ANNOTATIONS: return BasemodelPackage.BASE_MODEL_NODE__ANNOTATIONS;
				default: return -1;
			}
		}
		return super.eDerivedStructuralFeatureID(baseFeatureID, baseClass);
	}

} //BaseModelNodeImpl
