/*
 * Copyright (c) 2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.ddl;

import org.eclipse.net4j.spi.db.ddl.InternalDBElement;
import org.eclipse.net4j.util.event.Notifier;

import java.io.Serializable;
import java.util.Properties;

/**
 * @since 4.2
 * @author Eike Stepper
 */
public abstract class DBElement extends Notifier implements InternalDBElement, Serializable
{
  private static final long serialVersionUID = 1L;

  private Properties properties;

  public DBElement()
  {
  }

  public synchronized final Properties getProperties()
  {
    if (properties == null)
    {
      properties = new Properties();
    }

    return properties;
  }
}
