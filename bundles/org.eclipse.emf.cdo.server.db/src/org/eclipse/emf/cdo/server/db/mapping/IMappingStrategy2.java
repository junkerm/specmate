/*
 * Copyright (c) 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.db.mapping;

import org.eclipse.emf.cdo.server.IStoreAccessor.CommitContext;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;

import org.eclipse.net4j.util.om.monitor.OMMonitor;

/**
 * Interface to complement {@link IMappingStrategy}.
 *
 * @author Eike Stepper
 * @since 4.5
 */
public interface IMappingStrategy2 extends IMappingStrategy
{
  public boolean needsRevisionPostProcessing();

  public void postProcessRevisions(IDBStoreAccessor accessor, CommitContext context, OMMonitor monitor);
}
