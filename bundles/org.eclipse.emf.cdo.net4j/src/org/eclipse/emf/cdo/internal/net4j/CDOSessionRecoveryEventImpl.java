/*
 * Copyright (c) 2010-2012, 2014 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Caspar De Groot - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j;

import org.eclipse.emf.cdo.net4j.CDOSessionRecoveryEvent;
import org.eclipse.emf.cdo.session.CDOSession;

import org.eclipse.net4j.util.event.Event;

/**
 * @author Caspar De Groot
 */
public class CDOSessionRecoveryEventImpl extends Event implements CDOSessionRecoveryEvent
{
  private static final long serialVersionUID = 1L;

  private Type type;

  public CDOSessionRecoveryEventImpl(CDOSession source, Type type)
  {
    super(source);
    this.type = type;
  }

  @Override
  public CDOSession getSource()
  {
    return (CDOSession)super.getSource();
  }

  public Type getType()
  {
    return type;
  }

  @Override
  protected String formatAdditionalParameters()
  {
    return "type=" + type;
  }
}
