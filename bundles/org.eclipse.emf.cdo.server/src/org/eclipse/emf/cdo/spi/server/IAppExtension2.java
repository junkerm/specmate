/*
 * Copyright (c) 2013, 2015 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Christian W. Damus - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import java.io.Reader;

/**
 * An optional extension of the {@link IAppExtension} interface for app extensions that support invocation
 * on the XML configurations of dynamically-managed repositories.  These may be instantiated multiple
 * times, will only be given repository configurations (not Net4j acceptors etc.) and are stopped if and
 * when their associated repositories are deleted.
 *
 * @author Christian W. Damus (CEA LIST)
 * @since 4.3
 */
public interface IAppExtension2 extends IAppExtension
{
  public void startDynamic(Reader xmlConfigReader) throws Exception;
}
