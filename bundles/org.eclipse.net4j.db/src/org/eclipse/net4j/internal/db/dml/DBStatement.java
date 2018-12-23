/*
 * Copyright (c) 2008, 2009, 2011-2013 Eike Stepper (Loehne, Germany) and others.
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
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.db.dml.IDBParameter;
import org.eclipse.net4j.db.dml.IDBStatement;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Eike Stepper
 * @deprecated
 */
@Deprecated
public class DBStatement implements IDBStatement
{
  private static final DBParameter[] NO_PARAMETERS = {};

  private List<DBParameter> parameters;

  private List<Object> sequence = new ArrayList<Object>();

  public IDBParameter addParameter(DBType type)
  {
    int position = 0;
    if (parameters == null)
    {
      parameters = new ArrayList<DBParameter>();
    }
    else
    {
      position = parameters.size();
    }

    DBParameter parameter = new DBParameter(this, position, type);
    parameters.add(parameter);
    return parameter;
  }

  public IDBParameter addParameter(IDBField field)
  {
    return addParameter(field.getType());
  }

  public DBParameter[] getParameters()
  {
    if (parameters == null)
    {
      return NO_PARAMETERS;
    }

    return parameters.toArray(new DBParameter[parameters.size()]);
  }

  public void addSQL(String literal)
  {
    int tailPos = sequence.size() - 1;
    Object tail = sequence.get(tailPos);
    if (tail instanceof String)
    {
      sequence.set(tailPos, (String)tail + literal);
    }
    else
    {
      sequence.add(literal);
    }
  }

  public void addSQL(IDBParameter parameter)
  {
    sequence.add(parameter);
  }

  public void addSQL(IDBSchemaElement schemaElement)
  {
    addSQL(schemaElement.getName());
  }

  public String getSQL()
  {
    StringBuilder builder = new StringBuilder();
    for (Object element : sequence)
    {
      if (element instanceof IDBParameter)
      {
        builder.append("?"); //$NON-NLS-1$
      }
      else
      {
        builder.append(element);
      }
    }

    return builder.toString();
  }
}
