/*
 * Copyright (c) 2010-2017 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - Bug 331619 - Support cross-referencing (XRef) for abstract classes and class hierarchies
 */
package org.eclipse.emf.cdo.internal.server;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDReference;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.model.CDOClassifierRef;
import org.eclipse.emf.cdo.common.model.CDOModelUtil;
import org.eclipse.emf.cdo.common.model.CDOPackageInfo;
import org.eclipse.emf.cdo.common.model.CDOPackageRegistry;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit.State;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.common.revision.CDORevision;
import org.eclipse.emf.cdo.common.util.CDOQueryInfo;
import org.eclipse.emf.cdo.server.IQueryContext;
import org.eclipse.emf.cdo.server.IQueryHandler;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.IView;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.server.StoreThreadLocal.NoSessionRegisteredException;
import org.eclipse.emf.cdo.spi.common.branch.CDOBranchUtil;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.revision.DetachedCDORevision;
import org.eclipse.emf.cdo.spi.common.revision.InternalCDORevisionManager;
import org.eclipse.emf.cdo.spi.common.revision.SyntheticCDORevision;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.cdo.spi.server.QueryHandlerFactory;

import org.eclipse.net4j.util.factory.ProductCreationException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * @author Eike Stepper
 */
public class XRefsQueryHandler implements IQueryHandler
{
  public XRefsQueryHandler()
  {
  }

  public void executeQuery(CDOQueryInfo info, IQueryContext context)
  {
    try
    {
      IStoreAccessor accessor = StoreThreadLocal.getAccessor();

      CDOBranchPoint branchPoint = context;
      CDOBranch branch = branchPoint.getBranch();

      if (branch.isMainBranch())
      {
        QueryContext xrefsContext = new QueryContext(info, context);
        accessor.queryXRefs(xrefsContext);
      }
      else
      {
        QueryContext xrefsContext = new QueryContextBranching(info, context);
        accessor.queryXRefs(xrefsContext);

        int maxResults = info.getMaxResults();
        while (!branch.isMainBranch() && (maxResults == CDOQueryInfo.UNLIMITED_RESULTS || context.getResultCount() < maxResults))
        {
          branchPoint = branch.getBase();
          branch = branchPoint.getBranch();

          xrefsContext.setBranchPoint(branchPoint);
          accessor.queryXRefs(xrefsContext);
        }
      }
    }
    catch (NoSessionRegisteredException ex)
    {
      // View has been closed - do nothing
    }
  }

  public static void collectSourceCandidates(IView view, Collection<EClass> concreteTypes, Map<EClass, List<EReference>> sourceCandidates)
  {
    InternalRepository repository = (InternalRepository)view.getRepository();
    CDOPackageRegistry packageRegistry = repository.getPackageRegistry(false);

    for (CDOPackageInfo packageInfo : packageRegistry.getPackageInfos())
    {
      // System.out.println();
      // System.out.println();
      // System.out.println(packageInfo);
      collectSourceCandidates(packageInfo, concreteTypes, sourceCandidates);
      // for (Entry<EClass, List<EReference>> entry : sourceCandidates.entrySet())
      // {
      // System.out.println(" ---> " + entry.getKey().getName());
      // for (EReference eReference : entry.getValue())
      // {
      // System.out.println(" ---> " + eReference.getName());
      // }
      // }
      //
      // System.out.println();
      // System.out.println();
    }
  }

  public static void collectSourceCandidates(CDOPackageInfo packageInfo, Collection<EClass> concreteTypes, Map<EClass, List<EReference>> sourceCandidates)
  {
    State state = packageInfo.getPackageUnit().getState();
    if (state == CDOPackageUnit.State.LOADED || state == CDOPackageUnit.State.PROXY)
    {
      EPackage ePackage = packageInfo.getEPackage();
      for (EClassifier eClassifier : ePackage.getEClassifiers())
      {
        if (eClassifier instanceof EClass)
        {
          collectSourceCandidates((EClass)eClassifier, concreteTypes, sourceCandidates);
        }
      }
    }
  }

  public static void collectSourceCandidates(EClass eClass, Collection<EClass> concreteTypes, Map<EClass, List<EReference>> sourceCandidates)
  {
    if (!eClass.isAbstract() && !eClass.isInterface())
    {
      for (EReference eReference : CDOModelUtil.getClassInfo(eClass).getAllPersistentReferences())
      {
        collectSourceCandidates(eClass, eReference, concreteTypes, sourceCandidates);
      }
    }
  }

  public static void collectSourceCandidates(EReference eReference, Collection<EClass> concreteTypes, Map<EClass, List<EReference>> sourceCandidates,
      CDOPackageRegistry packageRegistry)
  {
    EClass rootClass = eReference.getEContainingClass();
    collectSourceCandidates(rootClass, eReference, concreteTypes, sourceCandidates);

    Collection<EClass> descendentClasses = packageRegistry.getSubTypes().get(rootClass);
    if (descendentClasses != null)
    {
      for (EClass candidateClass : descendentClasses)
      {
        collectSourceCandidates(candidateClass, eReference, concreteTypes, sourceCandidates);
      }
    }
  }

  public static void collectSourceCandidates(EClass eClass, EReference eReference, Collection<EClass> concreteTypes,
      Map<EClass, List<EReference>> sourceCandidates)
  {
    if (eClass.isAbstract())
    {
      return;
    }

    if (eClass.isInterface())
    {
      return;
    }

    if (eReference.isContainer())
    {
      return;
    }

    if (eReference.isContainment() && !eReference.isResolveProxies())
    {
      return;
    }

    if (canReference(eReference.getEReferenceType(), concreteTypes))
    {
      List<EReference> list = sourceCandidates.get(eClass);
      if (list == null)
      {
        list = new ArrayList<EReference>();
        sourceCandidates.put(eClass, list);
      }

      list.add(eReference);
    }
  }

  private static boolean canReference(EClass declaredType, Collection<EClass> concreteTypes)
  {
    for (EClass concreteType : concreteTypes)
    {
      if (declaredType == EcorePackage.Literals.EOBJECT || declaredType.isSuperTypeOf(concreteType))
      {
        return true;
      }
    }

    return false;
  }

  /**
   * @author Eike Stepper
   * @since 3.0
   */
  private static class QueryContext implements IStoreAccessor.QueryXRefsContext
  {
    private CDOQueryInfo info;

    private IQueryContext context;

    private CDOBranchPoint branchPoint;

    private Map<CDOID, EClass> targetObjects;

    private Map<EClass, List<EReference>> sourceCandidates;

    private EReference[] sourceReferences;

    public QueryContext(CDOQueryInfo info, IQueryContext context)
    {
      this.info = info;
      this.context = context;
      branchPoint = context;
    }

    public final void setBranchPoint(CDOBranchPoint branchPoint)
    {
      this.branchPoint = branchPoint;
    }

    public final CDOBranch getBranch()
    {
      return branchPoint.getBranch();
    }

    public final long getTimeStamp()
    {
      return branchPoint.getTimeStamp();
    }

    public final Map<CDOID, EClass> getTargetObjects()
    {
      if (targetObjects == null)
      {
        IRepository repository = getRepository();
        IStore store = repository.getStore();
        CDOPackageRegistry packageRegistry = repository.getPackageRegistry();

        targetObjects = CDOIDUtil.createMap();
        StringTokenizer tokenizer = new StringTokenizer(info.getQueryString(), "|");
        while (tokenizer.hasMoreTokens())
        {
          String val = tokenizer.nextToken();

          CDOID id;
          if (val.startsWith("e"))
          {
            id = CDOIDUtil.createExternal(val.substring(1));
          }
          else
          {
            id = store.createObjectID(val.substring(1));
          }

          CDOClassifierRef classifierRef;
          if (id instanceof CDOClassifierRef.Provider)
          {
            classifierRef = ((CDOClassifierRef.Provider)id).getClassifierRef();
          }
          else
          {
            val = tokenizer.nextToken();
            classifierRef = new CDOClassifierRef(val);
          }

          EClass eClass = (EClass)classifierRef.resolve(packageRegistry);
          targetObjects.put(id, eClass);
        }
      }

      return targetObjects;
    }

    public final EReference[] getSourceReferences()
    {
      if (sourceReferences == null)
      {
        sourceReferences = parseSourceReferences();
      }

      return sourceReferences;
    }

    public final Map<EClass, List<EReference>> getSourceCandidates()
    {
      if (sourceCandidates == null)
      {
        sourceCandidates = new HashMap<EClass, List<EReference>>();
        Collection<EClass> concreteTypes = getTargetObjects().values();
        EReference[] sourceReferences = getSourceReferences();

        if (sourceReferences.length != 0)
        {
          InternalRepository repository = (InternalRepository)getRepository();
          InternalCDOPackageRegistry packageRegistry = repository.getPackageRegistry(false);
          for (EReference eReference : sourceReferences)
          {
            collectSourceCandidates(eReference, concreteTypes, sourceCandidates, packageRegistry);
          }
        }
        else
        {
          collectSourceCandidates(context.getView(), concreteTypes, sourceCandidates);
        }
      }

      return sourceCandidates;
    }

    public final int getMaxResults()
    {
      return info.getMaxResults();
    }

    public final IRepository getRepository()
    {
      return context.getView().getRepository();
    }

    public final boolean addXRef(CDOID targetID, CDOID sourceID, EReference sourceReference, int sourceIndex)
    {
      if (CDOIDUtil.isNull(targetID))
      {
        return true;
      }

      if (isIgnoredObject(sourceID))
      {
        return true;
      }

      return context.addResult(new CDOIDReference(targetID, sourceID, sourceReference, sourceIndex));
    }

    protected boolean isIgnoredObject(CDOID sourceID)
    {
      return false;
    }

    private EReference[] parseSourceReferences()
    {
      List<EReference> result = new ArrayList<EReference>();
      CDOPackageRegistry packageRegistry = getRepository().getPackageRegistry();

      String params = (String)info.getParameters().get(CDOProtocolConstants.QUERY_LANGUAGE_XREFS_SOURCE_REFERENCES);
      if (params == null)
      {
        return new EReference[0];
      }

      StringTokenizer tokenizer = new StringTokenizer(params, "|");
      while (tokenizer.hasMoreTokens())
      {
        String className = tokenizer.nextToken();
        CDOClassifierRef classifierRef = new CDOClassifierRef(className);
        EClass eClass = (EClass)classifierRef.resolve(packageRegistry);

        String featureName = tokenizer.nextToken();
        EReference sourceReference = (EReference)eClass.getEStructuralFeature(featureName);
        result.add(sourceReference);
      }

      return result.toArray(new EReference[result.size()]);
    }
  }

  /**
   * @author Eike Stepper
   */
  private static final class QueryContextBranching extends QueryContext
  {
    private final CDOBranchPoint originalBranchPoint;

    private final Set<CDOID> ignoredObjects = new HashSet<CDOID>();

    public QueryContextBranching(CDOQueryInfo info, IQueryContext context)
    {
      super(info, context);
      originalBranchPoint = CDOBranchUtil.copyBranchPoint(context);
    }

    @Override
    protected boolean isIgnoredObject(CDOID sourceID)
    {
      if (!ignoredObjects.add(sourceID))
      {
        return true;
      }

      if (isDetachedObject(sourceID))
      {
        ignoredObjects.add(sourceID);
        return true;
      }

      return false;
    }

    private boolean isDetachedObject(CDOID sourceID)
    {
      if (getBranch() == originalBranchPoint.getBranch())
      {
        return false;
      }

      SyntheticCDORevision[] synthetics = { null };

      InternalCDORevisionManager revisionManager = (InternalCDORevisionManager)getRepository().getRevisionManager();
      revisionManager.getRevision(sourceID, originalBranchPoint, CDORevision.UNCHUNKED, CDORevision.DEPTH_NONE, true, synthetics);

      return synthetics[0] instanceof DetachedCDORevision;
    }
  }

  /**
   * @author Eike Stepper
   */
  public static class Factory extends QueryHandlerFactory
  {
    public Factory()
    {
      super(CDOProtocolConstants.QUERY_LANGUAGE_XREFS);
    }

    @Override
    public XRefsQueryHandler create(String description) throws ProductCreationException
    {
      return new XRefsQueryHandler();
    }
  }
}
