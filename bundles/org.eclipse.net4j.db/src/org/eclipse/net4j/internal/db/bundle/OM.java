/*
 * Copyright (c) 2007-2009, 2011, 2012, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.net4j.internal.db.bundle;

import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.internal.db.DBAdapterDescriptor;
import org.eclipse.net4j.internal.db.DBAdapterRegistry;
import org.eclipse.net4j.util.om.OMBundle;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.OSGiActivator;
import org.eclipse.net4j.util.om.log.OMLogger;
import org.eclipse.net4j.util.om.trace.OMTracer;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;

/**
 * The <em>Operations & Maintenance</em> class of this bundle.
 *
 * @author Eike Stepper
 */
public abstract class OM
{
  public static final String BUNDLE_ID = "org.eclipse.net4j.db"; //$NON-NLS-1$

  public static final OMBundle BUNDLE = OMPlatform.INSTANCE.bundle(BUNDLE_ID, OM.class);

  public static final OMTracer DEBUG = BUNDLE.tracer("debug"); //$NON-NLS-1$

  public static final OMTracer DEBUG_SQL = DEBUG.tracer("sql"); //$NON-NLS-1$

  public static final OMLogger LOG = BUNDLE.logger();

  public static final String EXT_POINT = "dbAdapters"; //$NON-NLS-1$

  /**
   * @author Eike Stepper
   */
  public static final class Activator extends OSGiActivator
  {
    public Activator()
    {
      super(BUNDLE);
    }

    @Override
    protected void doStart() throws Exception
    {
      initDBAdapterRegistry();
    }

    private void initDBAdapterRegistry()
    {
      IExtensionRegistry registry = Platform.getExtensionRegistry();
      IConfigurationElement[] elements = registry.getConfigurationElementsFor(BUNDLE_ID, EXT_POINT);
      for (final IConfigurationElement element : elements)
      {
        if ("dbAdapter".equals(element.getName())) //$NON-NLS-1$
        {
          DBAdapterDescriptor descriptor = new DBAdapterDescriptor(element.getAttribute("name")) //$NON-NLS-1$
          {
            @Override
            public IDBAdapter createDBAdapter()
            {
              try
              {
                return (IDBAdapter)element.createExecutableExtension("class"); //$NON-NLS-1$
              }
              catch (CoreException ex)
              {
                OM.LOG.error(ex);
                return null;
              }
            }
          };

          DBAdapterRegistry.INSTANCE.addDescriptor(descriptor);
        }
      }
    }
  }
}
