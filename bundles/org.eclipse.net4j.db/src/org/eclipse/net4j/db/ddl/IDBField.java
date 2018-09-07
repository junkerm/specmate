/*
 * Copyright (c) 2008, 2010-2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db.ddl;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.util.collection.PositionProvider;

/**
 * A field (column) specification in a {@link IDBTable DB table}.
 *
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 */
public interface IDBField extends IDBSchemaElement, PositionProvider
{
  public static final int DEFAULT = -1;

  /**
   * @since 4.2
   */
  public IDBTable getParent();

  public IDBTable getTable();

  public DBType getType();

  public void setType(DBType type);

  public int getPrecision();

  public void setPrecision(int precision);

  public int getScale();

  public void setScale(int scale);

  public boolean isNotNull();

  public void setNotNull(boolean notNull);

  public String getFullName();

  public String formatPrecision();

  public String formatPrecisionAndScale();
}
