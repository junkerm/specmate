/*
 * Copyright (c) 2008, 2011-2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db.dml;

import org.eclipse.net4j.db.DBType;
import org.eclipse.net4j.db.ddl.IDBField;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;

/**
 * Specifies an SQL statement with zero or more {@link IDBParameter parameters}.
 *
 * @author Eike Stepper
 * @noimplement This interface is not intended to be implemented by clients.
 * @noextend This interface is not intended to be extended by clients.
 * @deprecated
 */
@Deprecated
public interface IDBStatement
{
  public IDBParameter addParameter(DBType type);

  public IDBParameter addParameter(IDBField field);

  public IDBParameter[] getParameters();

  public void addSQL(String literal);

  public void addSQL(IDBParameter parameter);

  public void addSQL(IDBSchemaElement schemaElement);

  public String getSQL();
}
