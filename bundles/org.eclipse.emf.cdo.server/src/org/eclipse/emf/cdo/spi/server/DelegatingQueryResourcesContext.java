/*
 * Copyright (c) 2012 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.server.IStoreAccessor.QueryResourcesContext;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 4.2
 */
public abstract class DelegatingQueryResourcesContext implements QueryResourcesContext
{
  public CDOBranch getBranch()
  {
    return getDelegate().getBranch();
  }

  public long getTimeStamp()
  {
    return getDelegate().getTimeStamp();
  }

  public CDOID getFolderID()
  {
    return getDelegate().getFolderID();
  }

  public String getName()
  {
    return getDelegate().getName();
  }

  public boolean exactMatch()
  {
    return getDelegate().exactMatch();
  }

  public int getMaxResults()
  {
    return getDelegate().getMaxResults();
  }

  public boolean addResource(CDOID resourceID)
  {
    return getDelegate().addResource(resourceID);
  }

  protected abstract QueryResourcesContext getDelegate();
}
