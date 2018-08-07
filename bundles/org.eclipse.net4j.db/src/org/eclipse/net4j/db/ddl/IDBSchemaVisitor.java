/*
 * Copyright (c) 2013 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db.ddl;

/**
 * @since 4.2
 * @author Eike Stepper
 */
public interface IDBSchemaVisitor
{
  public void visit(IDBSchema schema);

  public void visit(IDBTable table);

  public void visit(IDBField field);

  public void visit(IDBIndex index);

  public void visit(IDBIndexField indexField);

  /**
   * @author Eike Stepper
   */
  public static final class StopRecursion extends RuntimeException
  {
    private static final long serialVersionUID = 1L;
  }

  /**
   * @author Eike Stepper
   */
  public static class Default implements IDBSchemaVisitor
  {
    public void visit(IDBSchema element)
    {
      visitDefault(element);
    }

    public void visit(IDBTable element)
    {
      visitDefault(element);
    }

    public void visit(IDBField element)
    {
      visitDefault(element);
    }

    public void visit(IDBIndex element)
    {
      visitDefault(element);
    }

    public void visit(IDBIndexField element)
    {
      visitDefault(element);
    }

    protected void visitDefault(IDBSchemaElement element)
    {
      visitDefault(element);
    }

    protected final void stopRecursion()
    {
      throw new StopRecursion();
    }
  }
}
