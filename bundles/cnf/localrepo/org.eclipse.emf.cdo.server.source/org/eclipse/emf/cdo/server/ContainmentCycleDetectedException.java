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
package org.eclipse.emf.cdo.server;

import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.ContainmentCycleException;

/**
 * An unchecked exception that can thrown from a commit operation that is based on stale information
 * about the tree structure of the model and would introduce a containment cycle.
 * <p>
 * This situation results from a network race condition and can not be prevented by write locks on
 * the changed objects. The committing client must {@link CDOTransaction#rollback() rollback} the transaction
 * , replay the original changes and try to {@link CDOTransaction#commit() commit} again.
 *
 * @author Eike Stepper
 * @since 4.0
 * @deprecated As of 4.2 no longer used in the server; replaced by {@link ContainmentCycleException} in the client.
 */
@Deprecated
public class ContainmentCycleDetectedException extends IllegalStateException
{
  private static final long serialVersionUID = 1L;

  public ContainmentCycleDetectedException()
  {
  }

  public ContainmentCycleDetectedException(String message, Throwable cause)
  {
    super(message, cause);
  }

  public ContainmentCycleDetectedException(String s)
  {
    super(s);
  }

  public ContainmentCycleDetectedException(Throwable cause)
  {
    super(cause);
  }
}
