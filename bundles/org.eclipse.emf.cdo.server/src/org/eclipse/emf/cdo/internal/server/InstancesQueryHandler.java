/*
 * Copyright (c) 2013, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.common.util.CDOQueryInfo;
import org.eclipse.emf.cdo.server.IQueryContext;
import org.eclipse.emf.cdo.server.IQueryHandler;
import org.eclipse.emf.cdo.spi.common.revision.DetachedCDORevision;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.QueryHandlerFactory;

import org.eclipse.net4j.util.factory.ProductCreationException;

import org.eclipse.emf.ecore.EClass;

import java.util.List;

/**
 * @author Eike Stepper
 */
public class InstancesQueryHandler implements IQueryHandler
{
  public InstancesQueryHandler()
  {
  }

  public void executeQuery(CDOQueryInfo info, IQueryContext context)
  {
    EClass type = (EClass)info.getParameters().get(CDOProtocolConstants.QUERY_LANGUAGE_INSTANCES_TYPE);
    if (type != null)
    {
      executeQuery(type, context);

      if (!Boolean.TRUE.equals(info.getParameters().get(CDOProtocolConstants.QUERY_LANGUAGE_INSTANCES_EXACT)))
      {
        List<EClass> subTypes = context.getView().getRepository().getPackageRegistry().getSubTypes().get(type);
        if (subTypes != null && !subTypes.isEmpty())
        {
          for (EClass subType : subTypes)
          {
            if (context.getResultCount() == 0)
            {
              break;
            }

            executeQuery(subType, context);
          }
        }
      }
    }
  }

  private void executeQuery(EClass type, final IQueryContext context)
  {
    if (type.isInterface() || type.isAbstract())
    {
      return;
    }

    CDOBranch branch = context.getBranch();
    long timeStamp = context.getTimeStamp();

    InternalRepository repository = (InternalRepository)context.getView().getRepository();
    repository.handleRevisions(type, branch, false, timeStamp, false, new CDORevisionHandler()
    {
      public boolean handleRevision(CDORevision revision)
      {
        if (revision instanceof DetachedCDORevision)
        {
          return true;
        }

        return context.addResult(revision);
      }
    });
  }

  /**
   * @author Eike Stepper
   */
  public static class Factory extends QueryHandlerFactory
  {
    public Factory()
    {
      super(CDOProtocolConstants.QUERY_LANGUAGE_INSTANCES);
    }

    @Override
    public InstancesQueryHandler create(String description) throws ProductCreationException
    {
      return new InstancesQueryHandler();
    }
  }
}
