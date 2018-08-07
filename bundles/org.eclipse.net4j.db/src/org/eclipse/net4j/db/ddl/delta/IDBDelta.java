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
package org.eclipse.net4j.db.ddl.delta;

import org.eclipse.net4j.db.ddl.IDBNamedElement;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBSchemaElement;
import org.eclipse.net4j.util.container.IContainer;

/**
 * @since 4.2
 * @author Eike Stepper
 * @noextend This interface is not intended to be extended by clients.
 * @noimplement This interface is not intended to be implemented by clients.
 */
public interface IDBDelta extends IDBNamedElement, IContainer<IDBDelta>, Comparable<IDBDelta>
{
  public DeltaType getDeltaType();

  public IDBDelta getParent();

  public ChangeKind getChangeKind();

  public void accept(IDBDeltaVisitor visitor);

  public IDBSchemaElement getSchemaElement(IDBSchema schema);

  /**
   * @author Eike Stepper
   */
  public enum DeltaType
  {
    SCHEMA, TABLE, FIELD, INDEX, INDEX_FIELD, PROPERTY
  }

  /**
   * @author Eike Stepper
   */
  public enum ChangeKind
  {
    ADD, REMOVE, CHANGE
  }
}
