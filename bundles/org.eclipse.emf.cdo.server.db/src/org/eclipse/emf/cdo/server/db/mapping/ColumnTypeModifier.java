/*
 * Copyright (c) 2013, 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.db.mapping;

import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping.Provider;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.util.factory.ProductCreationException;

import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * Can modify the column type of a {@link ITypeMapping type mapping} that is created by the {@link ITypeMapping.Registry type mapping registry}.
 *
 * @author Eike Stepper
 * @since 4.2
 */
public abstract class ColumnTypeModifier
{
  public static final ColumnTypeModifier NOOP = new ColumnTypeModifier()
  {
    @Override
    public DBType modify(Provider provider, IMappingStrategy mappingStrategy, EStructuralFeature feature, DBType dbType)
    {
      return dbType;
    }
  };

  public ColumnTypeModifier()
  {
  }

  /**
   * Can modify the column type of a {@link ITypeMapping type mapping} that is created by the {@link ITypeMapping.Registry type mapping registry}.
   */
  public abstract DBType modify(ITypeMapping.Provider provider, IMappingStrategy mappingStrategy, EStructuralFeature feature, DBType dbType);

  /**
   * Creates {@link ColumnTypeModifier} instances.
   *
   * @author Eike Stepper
   */
  public static abstract class Factory extends org.eclipse.net4j.util.factory.Factory
  {
    /**
     * The Net4j factory product group for column type modifiers.
     */
    public static final String PRODUCT_GROUP = "org.eclipse.emf.cdo.server.db.columnTypeModifiers";

    public Factory(String type)
    {
      super(PRODUCT_GROUP, type);
    }

    public abstract ColumnTypeModifier create(String description) throws ProductCreationException;
  }
}
