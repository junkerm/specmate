/*
 * Copyright (c) 2010-2012, 2014-2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.util.CDOFetchRule;
import org.eclipse.emf.cdo.internal.net4j.bundle.OM;
import org.eclipse.emf.cdo.session.CDOCollectionLoadingPolicy;
import org.eclipse.emf.cdo.spi.common.revision.RevisionInfo;
import org.eclipse.emf.cdo.view.CDOFetchRuleManager;

import org.eclipse.net4j.util.om.trace.ContextTracer;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Eike Stepper
 */
public class LoadRevisionsRequest extends CDOClientRequest<List<RevisionInfo>>
{
  private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG_PROTOCOL, LoadRevisionsRequest.class);

  private List<RevisionInfo> infos;

  private CDOBranchPoint branchPoint;

  private int referenceChunk;

  private int prefetchDepth;

  public LoadRevisionsRequest(CDOClientProtocol protocol, List<RevisionInfo> infos, CDOBranchPoint branchPoint, int referenceChunk, int prefetchDepth)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_LOAD_REVISIONS);
    this.infos = infos;
    this.branchPoint = branchPoint;
    this.referenceChunk = referenceChunk;
    this.prefetchDepth = prefetchDepth;
  }

  @Override
  protected void requesting(CDODataOutput out) throws IOException
  {
    if (TRACER.isEnabled())
    {
      TRACER.format("Writing branchPoint: {0}", branchPoint); //$NON-NLS-1$
    }

    out.writeCDOBranchPoint(branchPoint);
    if (TRACER.isEnabled())
    {
      TRACER.format("Writing referenceChunk: {0}", referenceChunk); //$NON-NLS-1$
    }

    out.writeXInt(referenceChunk);
    int size = infos.size();
    if (TRACER.isEnabled())
    {
      TRACER.format("Writing {0} infos", size); //$NON-NLS-1$
    }

    if (prefetchDepth == 0)
    {
      out.writeXInt(size);
    }
    else
    {
      out.writeXInt(-size);
      if (TRACER.isEnabled())
      {
        TRACER.format("Writing prefetchDepth: {0}", prefetchDepth); //$NON-NLS-1$
      }

      out.writeXInt(prefetchDepth);
    }

    Collection<CDOID> ids = new ArrayList<CDOID>(size);
    for (RevisionInfo info : infos)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("Writing info: {0}", info); //$NON-NLS-1$
      }

      info.write(out);
      ids.add(info.getID());
    }

    CDOFetchRuleManager ruleManager = getSession().getFetchRuleManager();
    CDOCollectionLoadingPolicy collectionLoadingPolicy = ruleManager.getCollectionLoadingPolicy();
    List<CDOFetchRule> fetchRules = ruleManager.getFetchRules(ids);
    if (fetchRules == null || fetchRules.size() <= 0)
    {
      out.writeXInt(0);
    }
    else
    {
      // At this point, fetch size is more than one.
      int fetchSize = fetchRules.size();
      CDOID contextID = ruleManager.getContext();

      out.writeXInt(fetchSize);
      out.writeXInt(collectionLoadingPolicy != null ? collectionLoadingPolicy.getInitialChunkSize() : CDORevision.UNCHUNKED);
      out.writeCDOID(contextID);

      for (CDOFetchRule fetchRule : fetchRules)
      {
        fetchRule.write(out);
      }
    }
  }

  @Override
  protected List<RevisionInfo> confirming(CDODataInput in) throws IOException
  {
    int size = infos.size();
    if (TRACER.isEnabled())
    {
      TRACER.format("Reading {0} revisions", size); //$NON-NLS-1$
    }

    for (RevisionInfo info : infos)
    {
      info.readResult(in);
    }

    List<RevisionInfo> additionalRevisionInfos = null;
    int additionalSize = in.readXInt();
    if (additionalSize != 0)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("Reading {0} additional revision infos", additionalSize); //$NON-NLS-1$
      }

      additionalRevisionInfos = new ArrayList<RevisionInfo>(additionalSize);
      for (int i = 0; i < additionalSize; i++)
      {
        RevisionInfo info = RevisionInfo.read(in, branchPoint);
        info.readResult(in);
        additionalRevisionInfos.add(info);
      }
    }

    return additionalRevisionInfos;
  }

  @Override
  public String toString()
  {
    return MessageFormat.format("LoadRevisionsRequest(infos={0}, branchPoint={1}, referenceChunk={2}, prefetchDepth={3})", infos, branchPoint, referenceChunk,
        prefetchDepth);
  }
}
