/*
 * Copyright (c) 2010-2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOCommitData;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDProvider;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.spi.cdo.InternalCDOTransaction.InternalCDOCommitContext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public class CommitDelegationRequest extends CommitTransactionRequest {
	private static final DelegationIDProvider delegationIDProvider = new DelegationIDProvider();

	private CDOBranch branch;

	private String userID;

	private InternalCDOCommitContext context;

	private Map<CDOID, EClass> detachedTypes;

	public CommitDelegationRequest(CDOClientProtocol protocol, InternalCDOCommitContext context) {
		super(protocol, CDOProtocolConstants.SIGNAL_COMMIT_DELEGATION, context);
		branch = context.getBranch();
		userID = context.getUserID();
		this.context = context;

		try {
			CDOCommitData commitData = this.context.getCommitData();
			Method m = commitData.getClass().getDeclaredMethod("getDetachedTypes"); // NoSuchFieldException
			m.setAccessible(true);
			this.detachedTypes = (Map<CDOID,EClass>)m.invoke(commitData,null);
		} catch (Exception e) {;
		} 
	}

	@Override
	protected void requestingTransactionInfo(CDODataOutput out) throws IOException {
		out.writeCDOBranch(branch);
		out.writeString(userID);
	}

	@Override
	protected long getLastUpdateTime() {
		return CDOBranchPoint.UNSPECIFIED_DATE;
	}

	@Override
	protected CDOBranch getBranch() {
		return branch;
	}

	@Override
	protected EClass getObjectType(CDOID id) {
		if(this.detachedTypes!=null){
			return this.detachedTypes.get(id);
		} else {
			throw new UnsupportedOperationException();
		}
	}

	@Override
	protected CDOIDProvider getIDProvider() {
		return delegationIDProvider;
	}

	/**
	 * @author Eike Stepper
	 */
	private static class DelegationIDProvider implements CDOIDProvider {
		public CDOID provideCDOID(Object idOrObject) {
			return (CDOID) idOrObject;
		}
	}
}
