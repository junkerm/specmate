/*
 * Copyright (c) 2009, 2011, 2012, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IRepositoryProvider;

import org.eclipse.net4j.util.container.IManagedContainer;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 2.0
 */
public class ContainerRepositoryProvider implements IRepositoryProvider
{
  private IManagedContainer container;

  public ContainerRepositoryProvider(IManagedContainer container)
  {
    this.container = container;
  }

  public IManagedContainer getContainer()
  {
    return container;
  }

  public IRepository getRepository(String name)
  {
    try
    {
      return RepositoryFactory.get(container, name);
    }
    catch (Exception ex)
    {
      return null;
    }
  }
}
