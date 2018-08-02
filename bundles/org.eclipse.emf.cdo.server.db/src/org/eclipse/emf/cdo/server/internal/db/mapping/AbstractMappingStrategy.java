/*
 * Copyright (c) 2009-2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 *    Stefan Winkler - major refactoring
 *    Stefan Winkler - Bug 271444: [DB] Multiple refactorings bug 271444
 *    Stefan Winkler - Bug 282976: [DB] Influence Mappings through EAnnotations
 *    Kai Schlamp - Bug 284680 - [DB] Provide annotation to bypass ClassMapping
 *    Stefan Winkler - maintenance
 *    Stefan Winkler - Bug 285426: [DB] Implement user-defined typeMapping support
 */
package org.eclipse.emf.cdo.server.internal.db.mapping;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.id.CDOIDUtil;
import org.eclipse.emf.cdo.common.model.CDOFeatureType;
import org.eclipse.emf.cdo.common.model.CDOModelUtil;
import org.eclipse.emf.cdo.common.model.CDOPackageRegistry;
import org.eclipse.emf.cdo.common.model.CDOPackageUnit;
import org.eclipse.emf.cdo.common.model.EMFUtil;
import org.eclipse.emf.cdo.common.revision.CDORevisionHandler;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.etypes.EtypesPackage;
import org.eclipse.emf.cdo.server.IStoreAccessor.CommitContext;
import org.eclipse.emf.cdo.server.StoreThreadLocal;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IMetaDataManager;
import org.eclipse.emf.cdo.server.db.mapping.IClassMapping;
import org.eclipse.emf.cdo.server.db.mapping.IListMapping;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping;
import org.eclipse.emf.cdo.server.internal.db.DBAnnotation;
import org.eclipse.emf.cdo.server.internal.db.ObjectIDIterator;
import org.eclipse.emf.cdo.spi.common.commit.CDOChangeSetSegment;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageInfo;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageRegistry;
import org.eclipse.emf.cdo.spi.common.model.InternalCDOPackageUnit;
import org.eclipse.emf.cdo.spi.server.InternalRepository;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBSchemaTransaction;
import org.eclipse.net4j.db.ddl.IDBSchema;
import org.eclipse.net4j.db.ddl.IDBTable;
import org.eclipse.net4j.util.ImplementationError;
import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.WrappedException;
import org.eclipse.net4j.util.collection.CloseableIterator;
import org.eclipse.net4j.util.lifecycle.Lifecycle;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.monitor.OMMonitor;
import org.eclipse.net4j.util.om.monitor.OMMonitor.Async;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EModelElement;
import org.eclipse.emf.ecore.ENamedElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Semaphore;

/**
 * This abstract base class implements those methods which are most likely common to most mapping strategies. It can be
 * used to derive custom mapping strategy implementation.
 *
 * @author Eike Stepper
 * @since 2.0
 */
public abstract class AbstractMappingStrategy extends Lifecycle implements IMappingStrategy
{
  // --------- database name generation strings --------------
  protected static final String NAME_SEPARATOR = "_"; //$NON-NLS-1$

  protected static final String TYPE_PREFIX_FEATURE = "F"; //$NON-NLS-1$

  protected static final String TYPE_PREFIX_CLASS = "C"; //$NON-NLS-1$

  protected static final String TYPE_PREFIX_PACKAGE = "P"; //$NON-NLS-1$

  protected static final String GENERAL_PREFIX = "X"; //$NON-NLS-1$

  protected static final String GENERAL_SUFFIX = "0"; //$NON-NLS-1$

  /**
   * Prefix for unsettable feature helper columns
   */
  protected static final String CDO_SET_PREFIX = "cdo_set_"; //$NON-NLS-1$

  protected static final String FEATURE_TABLE_SUFFIX = "_list"; //$NON-NLS-1$

  private IDBStore store;

  private Map<String, String> properties;

  private ConcurrentMap<EClass, IClassMapping> classMappings;

  private boolean allClassMappingsCreated;

  private SystemPackageMappingInfo systemPackageMappingInfo;

  private Set<CDOFeatureType> forceIndexes;

  public AbstractMappingStrategy()
  {
    classMappings = new ConcurrentHashMap<EClass, IClassMapping>();
  }

  // -- property related methods -----------------------------------------

  public synchronized Map<String, String> getProperties()
  {
    if (properties == null)
    {
      properties = new HashMap<String, String>();
    }

    return properties;
  }

  public synchronized void setProperties(Map<String, String> properties)
  {
    this.properties = properties;
  }

  private int getMaxTableNameLength()
  {
    String value = getProperties().get(Props.MAX_TABLE_NAME_LENGTH);
    return value == null ? store.getDBAdapter().getMaxTableNameLength() : Integer.valueOf(value);
  }

  private int getMaxFieldNameLength()
  {
    String value = getProperties().get(Props.MAX_FIELD_NAME_LENGTH);
    return value == null ? store.getDBAdapter().getMaxFieldNameLength() : Integer.valueOf(value);
  }

  private boolean isQualifiedNames()
  {
    String value = getProperties().get(Props.QUALIFIED_NAMES);
    return value == null ? false : Boolean.valueOf(value);
  }

  private boolean isForceNamesWithID()
  {
    String value = getProperties().get(Props.FORCE_NAMES_WITH_ID);
    return value == null ? false : Boolean.valueOf(value);
  }

  private String getTableNamePrefix()
  {
    String value = getProperties().get(Props.TABLE_NAME_PREFIX);
    return StringUtil.safe(value);
  }

  public Set<CDOFeatureType> getForceIndexes()
  {
    if (forceIndexes == null)
    {
      forceIndexes = doGetForceIndexes(this);
    }

    return forceIndexes;
  }

  // -- getters and setters ----------------------------------------------

  public final IDBStore getStore()
  {
    return store;
  }

  public final void setStore(IDBStore dbStore)
  {
    checkInactive();
    store = dbStore;
  }

  protected final IMetaDataManager getMetaDataManager()
  {
    return getStore().getMetaDataManager();
  }

  // -- object id related methods ----------------------------------------

  public void handleRevisions(IDBStoreAccessor accessor, EClass eClass, CDOBranch branch, long timeStamp, boolean exactTime, CDORevisionHandler handler)
  {
    if (eClass == null)
    {
      Collection<IClassMapping> values = getClassMappings().values();
      for (IClassMapping mapping : values)
      {
        mapping.handleRevisions(accessor, branch, timeStamp, exactTime, handler);
      }
    }
    else
    {
      EPackage ePackage = eClass.getEPackage();
      if (CDOModelUtil.isCorePackage(ePackage))
      {
        initSystemPackageMappingInfo();
        if (!systemPackageMappingInfo.ecoreMapped)
        {
          return;
        }
      }

      if (CDOModelUtil.isTypesPackage(ePackage))
      {
        initSystemPackageMappingInfo();
        if (!systemPackageMappingInfo.etypesMapped)
        {
          return;
        }
      }

      IClassMapping classMapping = getClassMapping(eClass);
      classMapping.handleRevisions(accessor, branch, timeStamp, exactTime, handler);
    }
  }

  public Set<CDOID> readChangeSet(IDBStoreAccessor accessor, OMMonitor monitor, CDOChangeSetSegment[] segments)
  {
    Set<CDOID> result = new HashSet<CDOID>();
    Collection<IClassMapping> classMappings = getClassMappings().values();

    monitor.begin(classMappings.size());

    try
    {
      for (IClassMapping mapping : classMappings)
      {
        Async async = monitor.forkAsync();

        try
        {
          Set<CDOID> ids = mapping.readChangeSet(accessor, segments);
          result.addAll(ids);
        }
        finally
        {
          async.stop();
        }
      }

      return result;
    }
    finally
    {
      monitor.done();
    }
  }

  public CloseableIterator<CDOID> readObjectIDs(IDBStoreAccessor accessor)
  {
    Collection<EClass> classes = getClassesWithObjectInfo();
    final Iterator<EClass> classIt = classes.iterator();

    return new ObjectIDIterator(this, accessor)
    {
      private PreparedStatement currentStatement;

      @Override
      protected ResultSet getNextResultSet()
      {
        while (classIt.hasNext())
        {
          EClass eClass = classIt.next();
          IClassMapping mapping = getClassMapping(eClass);
          currentStatement = mapping.createObjectIDStatement(getAccessor());

          ResultSet resultSet = null;

          try
          {
            resultSet = currentStatement.executeQuery();
            return resultSet;
          }
          catch (Exception ex)
          {
            DBUtil.close(resultSet); // only on error
            releaseCurrentStatement();
            throw new DBException(ex);
          }
        }

        return null;
      }

      @Override
      protected void closeCurrentResultSet()
      {
        super.closeCurrentResultSet();
        releaseCurrentStatement();
      }

      private void releaseCurrentStatement()
      {
        DBUtil.close(currentStatement);
        currentStatement = null;
      }
    };
  }

  protected abstract Collection<EClass> getClassesWithObjectInfo();

  // -- database name demangling methods ---------------------------------

  private String getTableNamePrefix(EModelElement element)
  {
    String prefix = StringUtil.safe(DBAnnotation.TABLE_NAME_PREFIX.getValue(element));
    if (prefix.length() != 0 && !prefix.endsWith(NAME_SEPARATOR))
    {
      prefix += NAME_SEPARATOR;
    }

    EObject eContainer = element.eContainer();
    if (eContainer instanceof EModelElement)
    {
      EModelElement parent = (EModelElement)eContainer;
      prefix = getTableNamePrefix(parent) + prefix;
    }

    return prefix;
  }

  public String getTableName(ENamedElement element)
  {
    String name = null;
    String typePrefix = null;

    if (element instanceof EClass)
    {
      typePrefix = TYPE_PREFIX_CLASS;
      name = DBAnnotation.TABLE_NAME.getValue(element);
      if (name == null)
      {
        name = isQualifiedNames() ? EMFUtil.getQualifiedName((EClass)element, NAME_SEPARATOR) : element.getName();
      }
    }
    else if (element instanceof EPackage)
    {
      typePrefix = TYPE_PREFIX_PACKAGE;
      name = DBAnnotation.TABLE_NAME.getValue(element);
      if (name == null)
      {
        name = isQualifiedNames() ? EMFUtil.getQualifiedName((EPackage)element, NAME_SEPARATOR) : element.getName();
      }
    }
    else
    {
      throw new ImplementationError("Unknown element: " + element); //$NON-NLS-1$
    }

    String prefix = getTableNamePrefix();
    if (prefix.length() != 0 && !prefix.endsWith(NAME_SEPARATOR))
    {
      prefix += NAME_SEPARATOR;
    }

    prefix += getTableNamePrefix(element);

    String suffix = typePrefix + getUniqueID(element);
    int maxTableNameLength = getMaxTableNameLength();

    return getName(prefix + name, suffix, maxTableNameLength);
  }

  public String getTableName(EClass eClass, EStructuralFeature feature)
  {
    String name = DBAnnotation.TABLE_NAME.getValue(eClass);
    if (name == null)
    {
      name = isQualifiedNames() ? EMFUtil.getQualifiedName(eClass, NAME_SEPARATOR) : eClass.getName();
    }

    name += NAME_SEPARATOR;
    name += feature.getName();
    name += FEATURE_TABLE_SUFFIX;

    String prefix = getTableNamePrefix();
    if (prefix.length() != 0 && !prefix.endsWith(NAME_SEPARATOR))
    {
      prefix += NAME_SEPARATOR;
    }

    prefix += getTableNamePrefix(feature);

    String suffix = TYPE_PREFIX_FEATURE + getUniqueID(feature);
    int maxTableNameLength = getMaxTableNameLength();

    return getName(prefix + name, suffix, maxTableNameLength);
  }

  public String getFieldName(EStructuralFeature feature)
  {
    String name = DBAnnotation.COLUMN_NAME.getValue(feature);
    if (name == null)
    {
      name = getName(feature.getName(), TYPE_PREFIX_FEATURE + getUniqueID(feature), getMaxFieldNameLength());
    }

    return name;
  }

  public String getUnsettableFieldName(EStructuralFeature feature)
  {
    String name = DBAnnotation.COLUMN_NAME.getValue(feature);
    if (name != null)
    {
      return CDO_SET_PREFIX + name;
    }

    return getName(CDO_SET_PREFIX + feature.getName(), TYPE_PREFIX_FEATURE + getUniqueID(feature), getMaxFieldNameLength());
  }

  private String getName(String name, String suffix, int maxLength)
  {
    if (!store.getDBAdapter().isValidFirstChar(name.charAt(0)))
    {
      name = GENERAL_PREFIX + name;
    }

    boolean forceNamesWithID = isForceNamesWithID();
    if (!forceNamesWithID && store.getDBAdapter().isReservedWord(name))
    {
      name = name + GENERAL_SUFFIX;
    }

    if (name.length() > maxLength || forceNamesWithID)
    {
      suffix = NAME_SEPARATOR + suffix.replace('-', 'S');
      int length = Math.min(name.length(), maxLength - suffix.length());
      if (length < 0)
      {
        // Most likely CDOIDs are client side-assigned, i.e., meta IDs are extrefs. See getUniqueID()
        throw new IllegalStateException("Suffix is too long: " + suffix);
      }

      name = name.substring(0, length) + suffix;
    }

    return name;
  }

  private String getUniqueID(ENamedElement element)
  {
    long timeStamp;
    CommitContext commitContext = StoreThreadLocal.getCommitContext();
    if (commitContext != null)
    {
      timeStamp = commitContext.getBranchPoint().getTimeStamp();
    }
    else
    {
      // This happens outside a commit, i.e. at system init time.
      // Ensure that resulting ext refs are not replicated!
      timeStamp = CDOBranchPoint.INVALID_DATE;
      // timeStamp = getStore().getRepository().getTimeStamp();
    }

    IMetaDataManager metaDataManager = getMetaDataManager();
    CDOID result = metaDataManager.getMetaID(element, timeStamp);

    StringBuilder builder = new StringBuilder();
    CDOIDUtil.write(builder, result);
    return builder.toString();
  }

  // -- factories for mapping of classes, values, lists ------------------

  public void createMapping(Connection connection, InternalCDOPackageUnit[] packageUnits, OMMonitor monitor)
  {
    boolean passedPackageUnits = packageUnits != null && packageUnits.length != 0;
    Semaphore packageRegistryCommitLock = null;
    boolean ecoreNew = false;
    boolean etypesNew = false;

    Async async = null;
    monitor.begin();

    try
    {
      async = monitor.forkAsync();

      boolean isInitialCommit = passedPackageUnits && containsPackageUnit(packageUnits, EresourcePackage.eINSTANCE.getNsURI());
      if (isInitialCommit)
      {
        systemPackageMappingInfo = new SystemPackageMappingInfo();
      }
      else
      {
        CommitContext commitContext = StoreThreadLocal.getCommitContext();
        if (commitContext != null && (commitContext.isUsingEcore() || commitContext.isUsingEtypes()))
        {
          InternalRepository repository = (InternalRepository)store.getRepository();
          if (!passedPackageUnits)
          {
            try
            {
              packageRegistryCommitLock = repository.getPackageRegistryCommitLock();
              packageRegistryCommitLock.acquire();
            }
            catch (InterruptedException ex)
            {
              throw WrappedException.wrap(ex);
            }
          }

          initSystemPackageMappingInfo();

          if (!systemPackageMappingInfo.ecoreMapped || !systemPackageMappingInfo.etypesMapped)
          {
            CDOPackageRegistry packageRegistry = repository.getPackageRegistry();
            List<InternalCDOPackageUnit> extendedPackageUnits = new ArrayList<InternalCDOPackageUnit>();
            if (passedPackageUnits)
            {
              extendedPackageUnits.addAll(Arrays.asList(packageUnits));
            }

            if (!systemPackageMappingInfo.ecoreMapped && commitContext.isUsingEcore())
            {
              CDOPackageUnit packageUnit = packageRegistry.getPackageUnit(EcorePackage.eINSTANCE);
              extendedPackageUnits.add((InternalCDOPackageUnit)packageUnit);
              ecoreNew = true;
            }

            if (!systemPackageMappingInfo.etypesMapped && commitContext.isUsingEtypes())
            {
              CDOPackageUnit packageUnit = packageRegistry.getPackageUnit(EtypesPackage.eINSTANCE);
              extendedPackageUnits.add((InternalCDOPackageUnit)packageUnit);
              etypesNew = true;
            }

            if (ecoreNew || etypesNew)
            {
              packageUnits = extendedPackageUnits.toArray(new InternalCDOPackageUnit[extendedPackageUnits.size()]);
            }
          }
        }
      }

      if (packageUnits != null && packageUnits.length != 0)
      {
        IDBSchemaTransaction schemaTransaction = store.getDatabase().openSchemaTransaction();

        try
        {
          mapPackageUnits(packageUnits, connection, false);
          schemaTransaction.commit();
        }
        finally
        {
          schemaTransaction.close();
        }
      }
    }
    finally
    {
      if (async != null)
      {
        async.stop();
      }

      if (packageRegistryCommitLock != null)
      {
        systemPackageMappingInfo.ecoreMapped |= ecoreNew;
        systemPackageMappingInfo.etypesMapped |= etypesNew;
        packageRegistryCommitLock.release();
      }

      monitor.done();
    }
  }

  private void initSystemPackageMappingInfo()
  {
    if (systemPackageMappingInfo == null)
    {
      systemPackageMappingInfo = new SystemPackageMappingInfo();
      systemPackageMappingInfo.ecoreMapped = hasTableFor(EcorePackage.eINSTANCE.getEPackage());
      systemPackageMappingInfo.etypesMapped = hasTableFor(EtypesPackage.eINSTANCE.getAnnotation());
    }
  }

  public void removeMapping(Connection connection, InternalCDOPackageUnit[] packageUnits)
  {
    IDBSchemaTransaction schemaTransaction = store.getDatabase().openSchemaTransaction();

    try
    {
      mapPackageUnits(packageUnits, connection, true);
      schemaTransaction.commit();
    }
    finally
    {
      schemaTransaction.close();
    }
  }

  private boolean hasTableFor(EClass eClass)
  {
    String tableName = getTableName(eClass);
    return store.getDBSchema().getTable(tableName) != null;
  }

  protected Set<IClassMapping> mapPackageUnits(InternalCDOPackageUnit[] packageUnits, Connection connection, boolean unmap)
  {
    Set<IClassMapping> classMappings = new HashSet<IClassMapping>();

    if (packageUnits != null && packageUnits.length != 0)
    {
      for (InternalCDOPackageUnit packageUnit : packageUnits)
      {
        InternalCDOPackageInfo[] packageInfos = packageUnit.getPackageInfos();
        mapPackageInfos(packageInfos, connection, unmap, classMappings);
      }
    }

    return classMappings;
  }

  private void mapPackageInfos(InternalCDOPackageInfo[] packageInfos, Connection connection, boolean unmap, Set<IClassMapping> classMappings)
  {
    for (InternalCDOPackageInfo packageInfo : packageInfos)
    {
      EPackage ePackage = packageInfo.getEPackage();
      EClass[] persistentClasses = EMFUtil.getPersistentClasses(ePackage);
      mapClasses(connection, unmap, persistentClasses, classMappings);
    }
  }

  private void mapClasses(Connection connection, boolean unmap, EClass[] eClasses, Set<IClassMapping> classMappings)
  {
    for (EClass eClass : eClasses)
    {
      if (!(eClass.isInterface() || eClass.isAbstract()))
      {
        String mappingAnnotation = DBAnnotation.TABLE_MAPPING.getValue(eClass);

        // TODO Maybe we should explicitly report unknown values of the annotation
        if (mappingAnnotation != null && mappingAnnotation.equalsIgnoreCase(DBAnnotation.TABLE_MAPPING_NONE))
        {
          continue;
        }

        IClassMapping classMapping = unmap ? removeClassMapping(eClass) : createClassMapping(eClass);
        if (classMapping != null)
        {
          classMappings.add(classMapping);
        }
      }
    }
  }

  private IClassMapping createClassMapping(EClass eClass)
  {
    IClassMapping classMapping = doCreateClassMapping(eClass);
    if (classMapping != null)
    {
      classMappings.put(eClass, classMapping);
    }

    return classMapping;
  }

  private IClassMapping removeClassMapping(EClass eClass)
  {
    IClassMapping classMapping = classMappings.get(eClass);
    if (classMapping != null)
    {
      IDBSchema schema = getStore().getDBSchema();
      for (IDBTable table : classMapping.getDBTables())
      {
        schema.removeTable(table.getName());
      }

      classMappings.remove(eClass);
    }

    return classMapping;
  }

  protected abstract IClassMapping doCreateClassMapping(EClass eClass);

  public final IClassMapping getClassMapping(EClass eClass)
  {
    if (!isMapped(eClass))
    {
      throw new IllegalArgumentException("Class is not mapped: " + eClass);
    }

    // Try without synchronization first; this will almost always succeed, so it avoids the
    // performance penalty of syncing in the majority of cases
    IClassMapping result = classMappings.get(eClass);
    if (result == null)
    {
      // Synchronize on the classMappings to prevent concurrent invocations of createClassMapping
      // (Synchronizing on the eClass allows for more concurrency, but is risky because application
      // code may be syncing on the eClass also.)
      synchronized (classMappings)
      {
        // Check again, because other thread may have just added the mapping
        result = classMappings.get(eClass);
        if (result == null)
        {
          result = createClassMapping(eClass);
        }
      }
    }

    return result;
  }

  public final Map<EClass, IClassMapping> getClassMappings()
  {
    return getClassMappings(true);
  }

  public final Map<EClass, IClassMapping> getClassMappings(boolean createOnDemand)
  {
    return doGetClassMappings(createOnDemand);
  }

  public final Map<EClass, IClassMapping> doGetClassMappings(boolean createOnDemand)
  {
    if (createOnDemand)
    {
      synchronized (classMappings)
      {
        if (!allClassMappingsCreated)
        {
          createAllClassMappings();
          allClassMappingsCreated = true;
        }
      }
    }

    return classMappings;
  }

  private void createAllClassMappings()
  {
    InternalRepository repository = (InternalRepository)getStore().getRepository();
    InternalCDOPackageRegistry packageRegistry = repository.getPackageRegistry(false);
    for (InternalCDOPackageInfo packageInfo : packageRegistry.getPackageInfos())
    {
      for (EClassifier eClassifier : packageInfo.getEPackage().getEClassifiers())
      {
        if (eClassifier instanceof EClass)
        {
          EClass eClass = (EClass)eClassifier;
          if (isMapped(eClass))
          {
            getClassMapping(eClass); // Get or create it
          }
        }
      }
    }
  }

  protected abstract boolean isMapped(EClass eClass);

  public ITypeMapping createValueMapping(EStructuralFeature feature)
  {
    ITypeMapping.Provider provider = getTypeMappingProvider();
    return provider.createTypeMapping(this, feature);
  }

  protected ITypeMapping.Provider getTypeMappingProvider()
  {
    return ITypeMapping.Provider.INSTANCE;
  }

  public final IListMapping createListMapping(EClass containingClass, EStructuralFeature feature)
  {
    checkArg(feature.isMany(), "Only many-valued features allowed"); //$NON-NLS-1$
    return doCreateListMapping(containingClass, feature);
  }

  public final IListMapping createFeatureMapMapping(EClass containingClass, EStructuralFeature feature)
  {
    checkArg(FeatureMapUtil.isFeatureMap(feature), "Only FeatureMaps allowed"); //$NON-NLS-1$
    return doCreateFeatureMapMapping(containingClass, feature);
  }

  public abstract IListMapping doCreateListMapping(EClass containingClass, EStructuralFeature feature);

  public abstract IListMapping doCreateFeatureMapMapping(EClass containingClass, EStructuralFeature feature);

  @Override
  protected void doDeactivate() throws Exception
  {
    deactivateClassMappings();
    super.doDeactivate();
  }

  protected void deactivateClassMappings()
  {
    for (IClassMapping classMapping : classMappings.values())
    {
      LifecycleUtil.deactivate(classMapping);
    }
  }

  private static boolean containsPackageUnit(InternalCDOPackageUnit[] packageUnits, String packageUnitID)
  {
    for (InternalCDOPackageUnit packageUnit : packageUnits)
    {
      if (packageUnit.getID().equals(packageUnitID))
      {
        return true;
      }
    }

    return false;
  }

  private static Set<CDOFeatureType> doGetForceIndexes(IMappingStrategy mappingStrategy)
  {
    return CDOFeatureType.readCombination(mappingStrategy.getProperties().get(Props.FORCE_INDEXES));
  }

  public static Set<CDOFeatureType> getForceIndexes(IMappingStrategy mappingStrategy)
  {
    if (mappingStrategy instanceof AbstractMappingStrategy)
    {
      return ((AbstractMappingStrategy)mappingStrategy).getForceIndexes();
    }

    return doGetForceIndexes(mappingStrategy);
  }

  /**
   * @author Eike Stepper
   */
  private static final class SystemPackageMappingInfo
  {
    public boolean ecoreMapped;

    public boolean etypesMapped;
  }
}
