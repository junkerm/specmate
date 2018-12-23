/*
 * Copyright (c) 2010-2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Christian W. Damus (CEA) - bug 399641: container-aware factories
 */
package org.eclipse.emf.cdo.internal.net4j;

import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.session.CDOSession;

import org.eclipse.emf.internal.cdo.session.CDOSessionFactory;

import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IManagedContainerFactory;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.security.CredentialsProviderFactory;
import org.eclipse.net4j.util.security.IPasswordCredentialsProvider;

import org.eclipse.emf.spi.cdo.InternalCDOSession;

import java.net.URI;
import java.util.Map;

/**
 * @author Eike Stepper
 */
public class Net4jSessionFactory extends CDOSessionFactory implements IManagedContainerFactory
{
  public static final String TYPE = "cdo"; //$NON-NLS-1$

  private IManagedContainer managedContainer = IPluginContainer.INSTANCE;

  public Net4jSessionFactory()
  {
    super(TYPE);
  }

  @Override
  protected InternalCDOSession createSession(URI uri, Map<String, String> parameters)
  {
    String userID = parameters.get("userID"); //$NON-NLS-1$
    String repositoryName = parameters.get("repositoryName"); //$NON-NLS-1$
    if (repositoryName == null)
    {
      throw new IllegalArgumentException("Repository name is missing: " + uri);
    }

    CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
    configuration.setRepositoryName(repositoryName);
    configuration.setUserID(userID);
    configuration.setCredentialsProvider(getCredentialsProvider());

    // The session will be activated by the container
    configuration.setActivateOnOpen(false);
    return (InternalCDOSession)configuration.openNet4jSession();
  }

  protected IPasswordCredentialsProvider getCredentialsProvider()
  {
    try
    {
      IManagedContainer container = getManagedContainer();
      String type = getCredentialsProviderType();
      return (IPasswordCredentialsProvider)container.getElement(CredentialsProviderFactory.PRODUCT_GROUP, type, null);
    }
    catch (Exception ex)
    {
      return null;
    }
  }

  public IManagedContainer getManagedContainer()
  {
    return managedContainer;
  }

  public void setManagedContainer(IManagedContainer managedContainer)
  {
    this.managedContainer = managedContainer;
  }

  protected String getCredentialsProviderType()
  {
    return "interactive";
  }

  public static CDOSession get(IManagedContainer container, String description)
  {
    return (CDOSession)container.getElement(PRODUCT_GROUP, TYPE, description);
  }
}
