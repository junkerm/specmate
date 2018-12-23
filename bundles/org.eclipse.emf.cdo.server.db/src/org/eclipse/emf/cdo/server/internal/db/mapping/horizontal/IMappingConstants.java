/*
 * Copyright (c) 2013 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

/**
 * @author Eike Stepper
 */
public interface IMappingConstants
{
  /*
   * Field names of attribute tables
   */

  public static final String ATTRIBUTES_ID = "CDO_ID"; //$NON-NLS-1$

  public static final String ATTRIBUTES_BRANCH = "CDO_BRANCH"; //$NON-NLS-1$

  public static final String ATTRIBUTES_VERSION = "CDO_VERSION"; //$NON-NLS-1$

  public static final String ATTRIBUTES_CLASS = "CDO_CLASS"; //$NON-NLS-1$

  public static final String ATTRIBUTES_CREATED = "CDO_CREATED"; //$NON-NLS-1$

  public static final String ATTRIBUTES_REVISED = "CDO_REVISED"; //$NON-NLS-1$

  public static final String ATTRIBUTES_RESOURCE = "CDO_RESOURCE"; //$NON-NLS-1$

  public static final String ATTRIBUTES_CONTAINER = "CDO_CONTAINER"; //$NON-NLS-1$

  public static final String ATTRIBUTES_FEATURE = "CDO_FEATURE"; //$NON-NLS-1$

  /*
   * Field names of list tables
   */

  public static final String LIST_FEATURE = "CDO_FEATURE"; //$NON-NLS-1$

  public static final String LIST_REVISION_ID = "CDO_SOURCE"; //$NON-NLS-1$

  public static final String LIST_REVISION_VERSION = "CDO_VERSION"; //$NON-NLS-1$

  public static final String LIST_REVISION_VERSION_ADDED = "CDO_VERSION_ADDED"; //$NON-NLS-1$

  public static final String LIST_REVISION_VERSION_REMOVED = "CDO_VERSION_REMOVED"; //$NON-NLS-1$

  public static final String LIST_REVISION_BRANCH = "CDO_BRANCH"; //$NON-NLS-1$

  public static final String LIST_IDX = "CDO_IDX"; //$NON-NLS-1$

  public static final String LIST_VALUE = "CDO_VALUE"; //$NON-NLS-1$

  /*
   * Field names of featuremap tables
   */

  public static final String FEATUREMAP_REVISION_ID = LIST_REVISION_ID;

  public static final String FEATUREMAP_VERSION = LIST_REVISION_VERSION;

  public static final String FEATUREMAP_VERSION_ADDED = LIST_REVISION_VERSION_ADDED;

  public static final String FEATUREMAP_VERSION_REMOVED = LIST_REVISION_VERSION_REMOVED;

  public static final String FEATUREMAP_BRANCH = LIST_REVISION_BRANCH;

  public static final String FEATUREMAP_IDX = LIST_IDX;

  public static final String FEATUREMAP_TAG = LIST_FEATURE;

  public static final String FEATUREMAP_VALUE = LIST_VALUE;
}
