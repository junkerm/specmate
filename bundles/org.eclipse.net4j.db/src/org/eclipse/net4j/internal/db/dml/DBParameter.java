/*
 * Copyright (c) 2008, 2011-2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.dml;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.dml.IDBParameter;
import org.eclipse.net4j.db.dml.IDBStatement;

/**
 * @author Eike Stepper
 * @deprecated
 */
@Deprecated
public class DBParameter implements IDBParameter
{
  private IDBStatement statement;

  private int position;

  private DBType type;

  public DBParameter(IDBStatement statement, int position, DBType type)
  {
    this.statement = statement;
    this.position = position;
    this.type = type;
  }

  public IDBStatement getStatement()
  {
    return statement;
  }

  public int getPosition()
  {
    return position;
  }

  public DBType getType()
  {
    return type;
  }
}
