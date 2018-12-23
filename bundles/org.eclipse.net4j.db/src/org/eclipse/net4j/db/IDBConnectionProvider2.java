/*
 * Copyright (c) 2013, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.db;

import org.eclipse.net4j.util.security.IUserAware;

import javax.sql.DataSource;

import java.sql.Connection;

/**
 * Provides a database {@link Connection connection}, roughly comparable with a {@link DataSource data source}.
 *
 * @author Eike Stepper
 * @since 4.3
 */
public interface IDBConnectionProvider2 extends IDBConnectionProvider, IUserAware
{
}
