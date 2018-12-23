/*
 * Copyright (c) 2009, 2011, 2012 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.server.IQueryHandler;

import org.eclipse.net4j.util.factory.Factory;
import org.eclipse.net4j.util.factory.ProductCreationException;

/**
 * If the meaning of this type isn't clear, there really should be more of a description here...
 *
 * @author Eike Stepper
 * @since 2.0
 */
public abstract class QueryHandlerFactory extends Factory
{
  public static final String PRODUCT_GROUP = "org.eclipse.emf.cdo.server.queryHandlerFactories"; //$NON-NLS-1$

  public QueryHandlerFactory(String language)
  {
    super(PRODUCT_GROUP, language);
  }

  /**
   * @since 3.0
   */
  public abstract IQueryHandler create(String description) throws ProductCreationException;
}
