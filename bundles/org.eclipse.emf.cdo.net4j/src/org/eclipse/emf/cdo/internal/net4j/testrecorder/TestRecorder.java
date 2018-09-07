/*
 * Copyright (c) 2004-2016 Eike Stepper (Berlin, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j.testrecorder;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;
import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.model.CDOModelUtil;
import org.eclipse.emf.cdo.common.model.CDOType;
import org.eclipse.emf.cdo.common.revision.delta.CDOAddFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOClearFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOMoveFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDORemoveFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOSetFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOUnsetFeatureDelta;
import org.eclipse.emf.cdo.common.util.CDOTimeProvider;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.CDOResourceNode;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.spi.common.branch.CDOBranchUtil;
import org.eclipse.emf.cdo.spi.common.branch.InternalCDOBranchManager;
import org.eclipse.emf.cdo.spi.common.revision.CDOFeatureDeltaVisitorImpl;
import org.eclipse.emf.cdo.transaction.CDOMerger;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.transaction.CDOTransactionHandler1;
import org.eclipse.emf.cdo.util.CDOUtil;

import org.eclipse.net4j.util.StringUtil;
import org.eclipse.net4j.util.event.EventUtil;
import org.eclipse.net4j.util.io.IOUtil;
import org.eclipse.net4j.util.lifecycle.ILifecycle;
import org.eclipse.net4j.util.lifecycle.LifecycleEventAdapter;
import org.eclipse.net4j.util.om.OMPlatform;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.spi.cdo.CDOSessionProtocol;
import org.eclipse.emf.spi.cdo.FSMUtil;
import org.eclipse.emf.spi.cdo.InternalCDOView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Eike Stepper
 */
@SuppressWarnings("restriction")
public final class TestRecorder implements CDOTransactionHandler1.WithUndo
{
  public static final TestRecorder INSTANCE = new TestRecorder();

  private static final String OUTPUT_FOLDER = OMPlatform.INSTANCE.getProperty("org.eclipse.emf.cdo.test.recorder.outputFolder");

  private static final String CLASS_NAME = OMPlatform.INSTANCE.getProperty("org.eclipse.emf.cdo.test.recorder.className");

  private static final String DESCRIPTION = OMPlatform.INSTANCE.getProperty("org.eclipse.emf.cdo.test.recorder.description");

  private static final boolean RECORD_VIEWS = OMPlatform.INSTANCE.isProperty("org.eclipse.emf.cdo.test.recorder.recordViews");

  private static final Map<Object, String> TYPES = new HashMap<Object, String>();
  static
  {
    TYPES.put("mango", "getMangoFactory()");
    TYPES.put("model1", "getModel1Factory()");
    TYPES.put("model2", "getModel2Factory()");
    TYPES.put("model3", "getModel3Factory()");
    TYPES.put("subpackage", "getModel3SubpackageFactory()");
    TYPES.put("model4", "getModel4Factory()");
    TYPES.put("model5", "getModel5Factory()");
    TYPES.put("model6", "getModel6Factory()");

    TYPES.put(EresourcePackage.Literals.CDO_RESOURCE_FOLDER, "folder");
    TYPES.put(EresourcePackage.Literals.CDO_RESOURCE, "resource");
    TYPES.put(EresourcePackage.Literals.CDO_TEXT_RESOURCE, "text");
    TYPES.put(EresourcePackage.Literals.CDO_BINARY_RESOURCE, "file");
  }

  private final Map<Object, Variable> variables = new HashMap<Object, Variable>();

  private final Map<String, AtomicInteger> typeCounters = new HashMap<String, AtomicInteger>();

  private PrintStream out;

  private TestRecorder()
  {
  }

  private void println(String line)
  {
    if (out == null)
    {
      String className = CLASS_NAME;
      String packageName;

      if (StringUtil.isEmpty(className))
      {
        className = "RecordedTest";
        packageName = "";
      }
      else
      {
        int lastDot = className.lastIndexOf('.');
        if (lastDot != -1)
        {
          packageName = className.substring(0, lastDot);
          className = className.substring(lastDot + 1);
        }
        else
        {
          packageName = "";
        }
      }

      if (StringUtil.isEmpty(OUTPUT_FOLDER))
      {
        out = System.out;
      }
      else
      {
        String prefix;
        String suffix;

        int star = className.indexOf('*');
        if (star != -1)
        {
          prefix = className.substring(0, star);
          suffix = className.substring(star + 1);
        }
        else
        {
          prefix = className;
          suffix = "";
        }

        className = prefix + suffix;

        File folder = new File(OUTPUT_FOLDER, packageName.replace('.', '/'));
        File file = new File(folder, className + ".java");
        char c = 'a';

        while (file.exists())
        {
          className = prefix + Character.toString(c++) + suffix;
          file = new File(folder, className + ".java");
        }

        try
        {
          out = new PrintStream(file);

          Runtime.getRuntime().addShutdownHook(new Thread()
          {
            @Override
            public void run()
            {
              if (out != null)
              {
                out.println("\t}");
                out.println("}");
                IOUtil.close(out);
              }
            }
          });
        }
        catch (FileNotFoundException ex)
        {
          ex.printStackTrace();
          out = System.out;
        }
      }

      if (packageName.length() != 0)
      {
        out.println("package " + packageName + ";");
        out.println();
      }

      out.println("import org.eclipse.emf.cdo.common.branch.CDOBranch;");
      out.println("import org.eclipse.emf.cdo.common.branch.CDOBranchPoint;");
      out.println("import org.eclipse.emf.cdo.common.commit.CDOCommitInfo;");
      out.println("import org.eclipse.emf.cdo.eresource.CDOResource;");
      out.println("import org.eclipse.emf.cdo.session.CDOSession;");
      out.println("import org.eclipse.emf.cdo.tests.AbstractCDOTest;");
      out.println("import org.eclipse.emf.cdo.transaction.CDOTransaction;");
      out.println("import org.eclipse.emf.spi.cdo.DefaultCDOMerger;");
      out.println();

      if (!StringUtil.isEmpty(DESCRIPTION))
      {
        out.println("/**");
        out.println(" * " + DESCRIPTION);
        out.println(" */");
      }

      out.println("public class " + className + " extends AbstractCDOTest");
      out.println("{");
      out.println("\tpublic void testCase() throws Exception");
      out.println("\t{");
    }

    out.println("\t\t" + line);
  }

  public synchronized void openSession(final TestRecorderSession session)
  {
    Variable variable = createVariable("session", session);
    println("CDOSession " + variable + " = openSession();");

    addCloseListener(variable);
  }

  public synchronized void createBranch(TestRecorderBranch branch, long originalBaseTimeStamp)
  {
    Variable variable = createVariable("branch", branch);

    String lhs = "CDOBranch " + variable + " = ";
    String create = ".createBranch(" + list(quot(branch.getName()), formatTimeStamp(originalBaseTimeStamp, true)) + ");";

    CDOBranch baseBranch = branch.getBase().getBranch();
    if (baseBranch.isMainBranch())
    {
      println(lhs + variables.get(getSession(branch)) + ".getBranchManager().getMainBranch()" + create);
    }
    else
    {
      Variable baseBranchVariable = variables.get(baseBranch);
      if (baseBranchVariable != null)
      {
        println(lhs + baseBranchVariable + create);
      }
      else
      {
        println(lhs + variables.get(getSession(branch)) + ".getBranchManager().getBranch(" + quot(baseBranch.getPathName()) + ")" + create);
      }
    }
  }

  public synchronized void renameBranch(TestRecorderBranch branch)
  {
  }

  public synchronized void openView(final TestRecorderView view, CDOSession session, CDOBranch branch, long timeStamp)
  {
    if (RECORD_VIEWS)
    {
      Variable variable = createVariable("view", view);
      println(
          "CDOView " + variable + " = " + variables.get(session) + ".openView(" + list(formatBranch(branch, true), formatTimeStamp(timeStamp, true)) + ");");

      addCloseListener(variable);
    }
  }

  public synchronized void openTransaction(TestRecorderTransaction transaction, CDOSession session, CDOBranch branch)
  {
    Variable variable = createVariable("transaction", transaction);
    println("CDOTransaction " + variable + " = " + variables.get(session) + ".openTransaction(" + formatBranch(branch, true) + ");");

    addCloseListener(variable);
    transaction.addTransactionHandler(this);
  }

  public synchronized void attachingObject(CDOTransaction transaction, CDOObject object)
  {
    if (object instanceof CDOResourceNode)
    {
      CDOResourceNode resourceNode = (CDOResourceNode)object;

      EClass eClass = resourceNode.eClass();
      String typeName = eClass.getName();
      String type = TYPES.get(eClass);
      Variable variable = createVariable(type, resourceNode);

      println(typeName + " " + variable + " = " + variables.get(transaction) + "." + typeName.replace("CDO", "create") + "(getResourcePath("
          + quot(resourceNode.getPath()) + "));");
    }
  }

  public synchronized void detachingObject(CDOTransaction transaction, CDOObject object)
  {
    if (object instanceof CDOResourceNode)
    {
      CDOResourceNode resourceNode = (CDOResourceNode)object;
      println(formatResourceNode(resourceNode) + ".delete(null);");

      variables.remove(resourceNode);
    }
  }

  public synchronized void modifyingObject(CDOTransaction transaction, final CDOObject object, CDOFeatureDelta featureDelta)
  {
    if (object instanceof CDOResourceNode)
    {
      // TODO Handle path modifications.
      // CDOResourceNode resourceNode = (CDOResourceNode)object;
      return;
    }

    final EStructuralFeature feature = featureDelta.getFeature();
    final String featureName = StringUtil.cap(feature.getName());

    featureDelta.accept(new CDOFeatureDeltaVisitorImpl()
    {
      @Override
      public void visit(CDOClearFeatureDelta delta)
      {
        if (filter(feature))
        {
          return;
        }

        Object value = object.eGet(feature);
        if (value == null || value instanceof List<?> && ((List<?>)value).isEmpty())
        {
          return;
        }

        println(formatGet() + ".clear();");
      }

      @Override
      public void visit(CDOUnsetFeatureDelta delta)
      {
        if (filter(feature))
        {
          return;
        }

        println(formatGet() + ".unset();");
      }

      @Override
      public void visit(CDOSetFeatureDelta delta)
      {
        if (filter(feature))
        {
          return;
        }

        String value = formatValue(object, feature, delta.getValue());
        println(formatObject(object) + ".set" + featureName + "(" + value + ");");
      }

      @Override
      public void visit(CDORemoveFeatureDelta delta)
      {
        if (filter(feature))
        {
          return;
        }

        println(formatGet() + ".remove(" + delta.getIndex() + ");");
      }

      @Override
      public void visit(CDOAddFeatureDelta delta)
      {
        if (filter(feature))
        {
          return;
        }

        List<?> list = (List<?>)object.eGet(feature);
        Integer index = delta.getIndex() >= list.size() - 1 ? null : delta.getIndex();
        String value = formatValue(object, feature, delta.getValue());
        println(formatGet() + ".add(" + list(index, value) + ");");
      }

      @Override
      public void visit(CDOMoveFeatureDelta delta)
      {
        println(formatGet() + ".move(" + delta.getOldPosition() + ", " + delta.getNewPosition() + ");");
      }

      private boolean filter(EStructuralFeature feature)
      {
        if (feature instanceof EReference)
        {
          EReference reference = (EReference)feature;

          EReference oppositeReference = reference.getEOpposite();
          if (oppositeReference != null)
          {
            if (reference.isMany())
            {
              if (oppositeReference.isMany())
              {
                if (pick(reference, oppositeReference))
                {
                  return true;
                }
              }
            }
            else
            {
              if (oppositeReference.isMany())
              {
                return true;
              }

              if (pick(reference, oppositeReference))
              {
                return true;
              }
            }
          }
        }

        return false;
      }

      private boolean pick(EReference r1, EReference r2)
      {
        return getID(r1).compareTo(getID(r2)) > 0;
      }

      private String getID(EReference r)
      {
        EClass c = r.getEContainingClass();
        return c.getEPackage().getNsURI() + "#" + c.getName() + "#" + r.getName();
      }

      private String formatGet()
      {
        return formatObject(object) + ".get" + featureName + "()";
      }
    });
  }

  public synchronized void undoingObject(CDOTransaction transaction, CDOObject object, CDOFeatureDelta featureDelta)
  {
    modifyingObject(transaction, object, featureDelta);
  }

  public synchronized void mergeTransaction(TestRecorderTransaction transaction, CDOBranch source, CDOMerger merger)
  {
    println(variables.get(transaction) + ".merge(" + formatBranch(source, false) + ", new " + simpleClassName(merger) + "());");
  }

  public synchronized void mergeTransaction(TestRecorderTransaction transaction, CDOBranchPoint source, CDOMerger merger)
  {
    println(variables.get(transaction) + ".merge(" + formatBranchPoint(source, false) + ", new " + simpleClassName(merger) + "());");
  }

  public synchronized void commitTransaction(TestRecorderTransaction transaction, CDOCommitInfo commitInfo)
  {
    Variable variable = createVariable("commit", commitInfo);

    long timeStamp = commitInfo.getTimeStamp();
    Variable oldVariable = variables.put(timeStamp, variable);
    if (oldVariable != null)
    {
      variables.put(timeStamp, oldVariable);
    }

    CDOBranchPoint branchPoint = CDOBranchUtil.copyBranchPoint(commitInfo);
    oldVariable = variables.put(branchPoint, variable);
    if (oldVariable != null)
    {
      variables.put(branchPoint, oldVariable);
    }

    StringBuilder builder = new StringBuilder("CDOCommitInfo " + variable + " = commitAndSync(" + variables.get(transaction));

    for (InternalCDOView view : transaction.getSession().getViews())
    {
      if (view != transaction)
      {
        Variable viewVariable = variables.get(view);
        if (viewVariable != null)
        {
          builder.append(", ");
          builder.append(viewVariable);
        }
      }
    }

    builder.append(");");
    println(builder.toString());
  }

  public synchronized void rollbackTransaction(TestRecorderTransaction transaction)
  {
    println(variables.get(transaction) + ".rollback();");
  }

  private Variable createVariable(String type, Object object)
  {
    AtomicInteger counter = typeCounters.get(type);
    if (counter == null)
    {
      counter = new AtomicInteger();
      typeCounters.put(type, counter);
    }

    int number = counter.incrementAndGet();

    Variable variable = new Variable(type, number, object);
    variables.put(object, variable);
    return variable;
  }

  private void addCloseListener(final Variable variable)
  {
    final Object object = variable.getObject();
    EventUtil.addListener(object, new LifecycleEventAdapter()
    {
      @Override
      protected void onDeactivated(ILifecycle lifecycle)
      {
        variables.remove(object);
        println(variable + ".close();");
      }
    });
  }

  private String formatBranchPoint(CDOBranchPoint branchPoint, boolean optional)
  {
    CDOBranch branch = branchPoint.getBranch();
    long timeStamp = branchPoint.getTimeStamp();

    if (branch.isMainBranch() && timeStamp == CDOBranchPoint.UNSPECIFIED_DATE && optional)
    {
      return "";
    }

    Variable branchPointVariable = variables.get(branchPoint);
    if (branchPointVariable == null)
    {
      branchPointVariable = createVariable("point", branchPoint);
      println("CDOBranchPoint " + branchPointVariable + " = " + formatBranch(branch, false) + ".getPoint(" + formatTimeStamp(timeStamp, false) + ");");
    }

    return branchPointVariable.toString();
  }

  private String formatBranch(CDOBranch branch, boolean optional)
  {
    if (branch.isMainBranch() && optional)
    {
      return "";
    }

    Variable branchVariable = variables.get(branch);
    if (branchVariable == null)
    {
      Variable sessionVariable = variables.get(getSession(branch));

      if (branch.isMainBranch())
      {
        return sessionVariable + ".getBranchManager().getMainBranch()";
      }

      branchVariable = createVariable("branch", branch);
      println("CDOBranch " + branchVariable + " = " + sessionVariable + ".getBranchManager().getBranch(" + quot(branch.getPathName()) + ");");
    }

    return branchVariable.toString();
  }

  private String formatTimeStamp(long timeStamp, boolean optional)
  {
    if (timeStamp == CDOBranchPoint.UNSPECIFIED_DATE)
    {
      if (optional)
      {
        return "";
      }

      return "CDOBranchPoint.UNSPECIFIED_DATE";
    }

    Variable variable = variables.get(timeStamp);
    if (variable != null && variable.getObject() instanceof CDOTimeProvider)
    {
      return variable + ".getTimeStamp()";
    }

    return timeStamp + "L";
  }

  private String formatResourceNode(CDOResourceNode resourceNode)
  {
    Variable variable = variables.get(resourceNode);
    if (variable == null)
    {
      EClass eClass = resourceNode.eClass();
      String typeName = eClass.getName();
      String type = TYPES.get(eClass);
      variable = createVariable(type, resourceNode);

      println(typeName + " " + variable + " = " + variables.get(resourceNode.cdoView()) + "." + typeName.replace("CDO", "get") + "(getResourcePath("
          + quot(resourceNode.getPath()) + "));");
    }

    return variable.toString();
  }

  private String formatObject(CDOObject object)
  {
    Variable variable = variables.get(object);
    if (variable == null)
    {
      EClass eClass = object.eClass();
      String typeName = eClass.getName();
      String type = StringUtil.uncap(eClass.getName());
      variable = createVariable(type, object);

      if (FSMUtil.isTransient(object))
      {
        String packageName = eClass.getEPackage().getName();
        String factory = TYPES.get(packageName);
        if (factory == null)
        {
          factory = StringUtil.cap(packageName) + "Factory.eINSTANCE";
        }

        println(typeName + " " + variable + " = " + factory + ".create" + eClass.getName() + "();");

        for (EStructuralFeature feature : eClass.getEAllStructuralFeatures())
        {
          Object value = object.eGet(feature);
          if (value instanceof List<?>)
          {
            List<?> list = (List<?>)value;
            for (Object element : list)
            {
              println(variable + ".get" + StringUtil.cap(feature.getName()) + "().add(" + formatValue(object, feature, element) + ");");
            }
          }
          else if (value != null)
          {
            println(variable + ".set" + StringUtil.cap(feature.getName()) + "(" + formatValue(object, feature, value) + ");");
          }
        }
      }
      else
      {
        String rhs;

        CDOObject container = CDOUtil.getCDOObject(object.eContainer());
        if (container != null)
        {
          EReference reference = (EReference)object.eContainingFeature();
          rhs = formatObject(container) + ".get" + StringUtil.cap(reference.getName()) + "()";

          if (reference.isMany())
          {
            List<?> list = (List<?>)container.eGet(reference);
            int index = list.indexOf(object);
            rhs += ".get(" + index + ")";
          }
        }
        else
        {
          CDOResource resource = (CDOResource)object.eResource();
          int index = resource.getContents().indexOf(object);
          rhs = "(" + typeName + ")" + formatResourceNode(resource) + ".getContents().get(" + index + ")";
        }

        println(typeName + " " + variable + " = " + rhs + ";");
      }
    }

    return variable.toString();
  }

  private String formatValue(CDOObject object, EStructuralFeature feature, Object value)
  {
    if (value != null)
    {
      EClassifier eType = feature.getEType();

      if (eType instanceof EEnum)
      {
        EEnumLiteral literal = ((EEnum)eType).getEEnumLiteral(value.toString());
        return literal.getEEnum().getName() + "." + literal.toString().toUpperCase();
      }

      CDOType type = CDOModelUtil.getType(eType);
      if (type != null)
      {
        value = type.convertToEMF(eType, value);
      }

      if (value instanceof String)
      {
        return quot((String)value);
      }

      if (value instanceof CDOID)
      {
        value = object.cdoView().getObject((CDOID)value);
      }

      if (value instanceof EObject)
      {
        return formatObject(CDOUtil.getCDOObject((EObject)value));
      }
    }

    return String.valueOf(value);

  }

  private static TestRecorderSession getSession(CDOBranch branch)
  {
    InternalCDOBranchManager branchManager = (InternalCDOBranchManager)branch.getBranchManager();
    return (TestRecorderSession)((CDOSessionProtocol)branchManager.getBranchLoader()).getSession();
  }

  private static String simpleClassName(Object object)
  {
    String name = object.getClass().getName();
    int lastDot = name.lastIndexOf('.');
    if (lastDot != -1)
    {
      name = name.substring(lastDot + 1);
    }

    return name.replace('$', '.');
  }

  private static String list(Object... values)
  {
    StringBuilder builder = new StringBuilder();
    for (Object value : values)
    {
      if (value != null)
      {
        String str = value.toString();
        if (str.length() != 0)
        {
          if (builder.length() != 0)
          {
            builder.append(", ");
          }

          builder.append(str);
        }
      }
    }

    return builder.toString();
  }

  private static String quot(String value)
  {
    StringBuilder builder = new StringBuilder();
    builder.append("\"");

    if (value != null && value.length() != 0)
    {
      builder.append(value);
    }

    builder.append("\"");
    return builder.toString();
  }

  /**
   * @author Eike Stepper
   */
  public static final class Variable
  {
    private final String type;

    private final int number;

    private final Object object;

    private Variable(String type, int number, Object object)
    {
      this.type = type;
      this.number = number;
      this.object = object;
    }

    public String getType()
    {
      return type;
    }

    public int getNumber()
    {
      return number;
    }

    public Object getObject()
    {
      return object;
    }

    @Override
    public String toString()
    {
      return type + number;
    }
  }
}
