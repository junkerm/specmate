/**
 */
package com.specmate.model.processes.impl;

import com.specmate.model.base.impl.IModelNodeImpl;

import com.specmate.model.processes.ProcessNode;
import com.specmate.model.processes.ProcessesPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Process Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * @generated
 */
public abstract class ProcessNodeImpl extends IModelNodeImpl implements ProcessNode {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProcessNodeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProcessesPackage.Literals.PROCESS_NODE;
	}

} //ProcessNodeImpl
