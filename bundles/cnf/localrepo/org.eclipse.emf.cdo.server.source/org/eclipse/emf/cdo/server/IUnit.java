/*
 * Copyright (c) 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server;

import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;

import org.eclipse.net4j.util.om.monitor.OMMonitor;

/**
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 * @author Eike Stepper
 * @since 4.5
 */
public interface IUnit
{
  public IUnitManager getManager();

  public CDOID getRootID();

  public boolean isOpen();

  public void open(IView view, CDORevisionHandler revisionHandler, OMMonitor monitor);

  public void close(IView view);
}
