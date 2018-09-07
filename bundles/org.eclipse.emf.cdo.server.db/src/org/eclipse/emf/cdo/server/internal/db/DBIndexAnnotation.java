/*
 * Copyright (c) 2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.server.internal.db;

import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author Kai Schlamp
 */
public final class DBIndexAnnotation
{
  public static final String SOURCE_URI = "http://www.eclipse.org/CDO/DBIndex";

  public static final String FEATURES = "features";

  private DBIndexAnnotation()
  {
  }

  public static Set<List<EStructuralFeature>> getIndices(EClass eClass, EStructuralFeature[] allPersistentFeatures)
  {
    Set<List<EStructuralFeature>> indices = new HashSet<List<EStructuralFeature>>();

    for (EAnnotation annotation : EMFUtil.getAnnotations(eClass, SOURCE_URI))
    {
      List<EStructuralFeature> features = new ArrayList<EStructuralFeature>();

      String featureNames = annotation.getDetails().get(FEATURES);
      if (featureNames != null && featureNames.length() != 0)
      {
        StringTokenizer tokenizer = new StringTokenizer(featureNames, ",");
        while (tokenizer.hasMoreTokens())
        {
          String featureName = tokenizer.nextToken().trim();
          if (featureName.length() != 0)
          {
            EStructuralFeature feature = getPersistentFeature(featureName, allPersistentFeatures);
            if (feature == null)
            {
              OM.LOG.warn("Feature '" + featureName + "' not found in class '" + eClass.getName() + "' in package '" + eClass.getEPackage().getNsURI() + "'");
              continue;
            }

            features.add(feature);
          }
        }
      }
      else
      {
        for (EObject reference : annotation.getReferences())
        {
          if (reference instanceof EStructuralFeature)
          {
            EStructuralFeature feature = (EStructuralFeature)reference;
            if (!isPersistentFeature(feature, allPersistentFeatures))
            {
              OM.LOG.warn("Feature '" + feature.getName() + "' is not a persistent feature of class '" + eClass.getName() + "' in package '"
                  + eClass.getEPackage().getNsURI() + "'");
              continue;
            }

            features.add(feature);
          }
          else
          {
            OM.LOG.warn("Reference '" + reference + "' is not a feature");
          }
        }
      }

      int size = features.size();
      if (size > 0)
      {
        if (size > 1)
        {
          for (EStructuralFeature feature : features)
          {
            if (feature.isMany())
            {
              OM.LOG.warn("Many-valued feature '" + feature.getName() + "' not allowed in composed index on class '" + eClass.getName() + "' in package '"
                  + eClass.getEPackage().getNsURI() + "'");
              continue;
            }
          }
        }

        indices.add(features);
      }
    }

    for (EStructuralFeature feature : allPersistentFeatures)
    {
      if (feature.getEAnnotation(SOURCE_URI) != null)
      {
        indices.add(Collections.singletonList(feature));
      }
    }

    return indices;
  }

  private static EStructuralFeature getPersistentFeature(String featureName, EStructuralFeature[] allPersistentFeatures)
  {
    for (int i = 0; i < allPersistentFeatures.length; i++)
    {
      EStructuralFeature feature = allPersistentFeatures[i];
      if (feature.getName().equals(featureName))
      {
        return feature;
      }
    }

    return null;
  }

  private static boolean isPersistentFeature(EStructuralFeature feature, EStructuralFeature[] allPersistentFeatures)
  {
    for (int i = 0; i < allPersistentFeatures.length; i++)
    {
      if (allPersistentFeatures[i] == feature)
      {
        return true;
      }
    }

    return false;
  }
}
