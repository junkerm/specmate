/*
 * Copyright (c) 2010-2012, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server;

import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory;

import org.eclipse.net4j.util.container.IContainer;

/**
 * Synchronizes a {@link ISynchronizableRepository synchronizable repository} with a master repository.
 *
 * @author Eike Stepper
 * @since 3.0
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 * @apiviz.landmark
 * @apiviz.has {@link org.eclipse.emf.cdo.session.CDOSessionConfigurationFactory} oneway - - remote
 */
public interface IRepositorySynchronizer extends IContainer<CDOSession>
{
  /**
   * @since 4.4
   */
  public static final int DEFAULT_RETRY_INTERVAL = 3;

  /**
   * @since 4.4
   */
  public static final int DEFAULT_MAX_RECOMMITS = 10;

  /**
   * @since 4.4
   */
  public static final int DEFAULT_RECOMMIT_INTERVAL = 1;

  public int getRetryInterval();

  public void setRetryInterval(int retryInterval);

  public ISynchronizableRepository getLocalRepository();

  public CDOSessionConfigurationFactory getRemoteSessionConfigurationFactory();

  public CDOSession getRemoteSession();

  public boolean isRawReplication();

  /**
   * @since 4.0
   */
  public void setRawReplication(boolean rawReplication);

  public int getMaxRecommits();

  public void setMaxRecommits(int maxRecommits);

  public int getRecommitInterval();

  public void setRecommitInterval(int recommitInterval);
}
