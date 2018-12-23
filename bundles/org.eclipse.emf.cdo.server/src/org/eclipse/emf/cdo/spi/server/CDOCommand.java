/*
 * Copyright (c) 2013, 2015, 2016 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.spi.server;

import org.eclipse.emf.cdo.internal.server.bundle.CDOServerApplication;
import org.eclipse.emf.cdo.server.IStoreAccessor;
import org.eclipse.emf.cdo.server.StoreThreadLocal;

import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.factory.ProductCreationException;

import org.eclipse.osgi.framework.console.CommandInterpreter;

import org.osgi.framework.Bundle;

import java.util.Dictionary;

/**
 * @author Eike Stepper
 * @since 4.3
 */
public abstract class CDOCommand extends org.eclipse.net4j.util.factory.Factory
{
  public static final String PRODUCT_GROUP = "org.eclipse.emf.cdo.server.commands";

  public static final String INDENT = "   "; //$NON-NLS-1$

  private static final CommandParameter[] NO_PARAMETERS = new CommandParameter[0];

  private final String description;

  private final CommandParameter[] parameters;

  private CommandInterpreter interpreter;

  public CDOCommand(String name, String description, CommandParameter... parameters)
  {
    super(PRODUCT_GROUP, name);
    this.description = description;
    this.parameters = parameters == null ? NO_PARAMETERS : parameters;
  }

  public CDOCommand(String name, String description)
  {
    this(name, description, NO_PARAMETERS);
  }

  public final CDOCommand create(String description) throws ProductCreationException
  {
    return this;
  }

  public final CommandInterpreter getInterpreter()
  {
    return interpreter;
  }

  public final void setInterpreter(CommandInterpreter interpreter)
  {
    this.interpreter = interpreter;
  }

  public final String getName()
  {
    return getType();
  }

  public final String getDescription()
  {
    return description;
  }

  public final CommandParameter[] getParameters()
  {
    return parameters;
  }

  public final String getSyntax()
  {
    StringBuilder builder = new StringBuilder();
    builder.append("cdo ");
    builder.append(getName());

    for (CommandParameter parameter : parameters)
    {
      builder.append(" ");
      if (parameter.isOptional())
      {
        builder.append("[");
      }

      builder.append("<");
      builder.append(parameter.getName());
      builder.append(">");

      if (parameter.isOptional())
      {
        builder.append("]");
      }
    }

    return builder.toString();
  }

  public final Object executeCommand(String cmd)
  {
    return interpreter.execute(cmd);
  }

  public final void print(Object o)
  {
    interpreter.print(o);
  }

  public final void println()
  {
    interpreter.println();
  }

  public final void println(Object o)
  {
    interpreter.println(o);
  }

  public final void printStackTrace(Throwable t)
  {
    interpreter.printStackTrace(t);
  }

  public final void printDictionary(Dictionary<?, ?> dic, String title)
  {
    interpreter.printDictionary(dic, title);
  }

  public final void printBundleResource(Bundle bundle, String resource)
  {
    interpreter.printBundleResource(bundle, resource);
  }

  public final void execute() throws Exception
  {
    int length = parameters.length;
    String[] args = new String[length];
    for (int i = 0; i < parameters.length; i++)
    {
      String arg;

      CommandParameter parameter = parameters[i];
      if (parameter.isOptional())
      {
        arg = nextArgumentOptional();
      }
      else
      {
        arg = nextArgument();
      }

      args[i] = arg;
    }

    execute(args);
  }

  public abstract void execute(String[] args) throws Exception;

  private String nextArgument()
  {
    String argument = interpreter.nextArgument();
    if (argument == null && parameters != null)
    {
      throw new CommandException("Syntax: " + getSyntax());
    }

    return argument;
  }

  private String nextArgumentOptional()
  {
    return interpreter.nextArgument();
  }

  public static CommandParameter[] parameters(CommandParameter parameter, CommandParameter[] parameters)
  {
    if (parameters == null || parameters.length == 0)
    {
      return new CommandParameter[] { parameter };
    }

    CommandParameter[] result = new CommandParameter[1 + parameters.length];
    result[0] = parameter;
    System.arraycopy(parameters, 0, result, 1, parameters.length);
    return result;
  }

  public static CommandParameter parameter(String name, boolean optional)
  {
    return new CommandParameter(name, optional);
  }

  public static CommandParameter parameter(String name)
  {
    return parameter(name, false);
  }

  public static CommandParameter optional(String name)
  {
    return parameter(name, true);
  }

  protected static String[] trimFirstArgument(String[] args)
  {
    int length = args.length - 1;
    String[] result = new String[length];
    System.arraycopy(args, 1, result, 0, length);
    return result;
  }

  /**
   * @author Eike Stepper
   */
  public static abstract class WithRepository extends CDOCommand
  {
    public WithRepository(String name, String description, CommandParameter... parameters)
    {
      super(name, description, parameters(parameter("repository-name"), parameters));
    }

    public WithRepository(String name, String description)
    {
      this(name, description, NO_PARAMETERS);
    }

    @Override
    public final void execute(String[] args) throws Exception
    {
      String repositoryName = args[0];
      InternalRepository repository = getRepository(repositoryName);
      if (repository == null)
      {
        throw new CommandException("Repository not found: " + repositoryName);
      }

      execute(repository, trimFirstArgument(args));
    }

    public abstract void execute(InternalRepository repository, String[] args) throws Exception;

    private InternalRepository getRepository(String name)
    {
      IManagedContainer container = CDOServerApplication.getContainer();
      for (Object element : container.getElements(RepositoryFactory.PRODUCT_GROUP))
      {
        if (element instanceof InternalRepository)
        {
          InternalRepository repository = (InternalRepository)element;
          if (repository.getName().equals(name))
          {
            return repository;
          }
        }
      }

      return null;
    }
  }

  /**
   * @author Eike Stepper
   */
  public static abstract class WithAccessor extends CDOCommand.WithRepository
  {
    public WithAccessor(String name, String description, CommandParameter... parameters)
    {
      super(name, description, parameters);
    }

    public WithAccessor(String name, String description)
    {
      this(name, description, NO_PARAMETERS);
    }

    @Override
    public final void execute(InternalRepository repository, String[] args) throws Exception
    {
      IStoreAccessor accessor = repository.getStore().getReader(null);
      StoreThreadLocal.setAccessor(accessor);

      try
      {
        execute(repository, accessor, args);
      }
      finally
      {
        StoreThreadLocal.release();
      }
    }

    public abstract void execute(InternalRepository repository, IStoreAccessor accessor, String[] args) throws Exception;
  }

  /**
   * @author Eike Stepper
   */
  public static final class CommandParameter
  {
    private final String name;

    private final boolean optional;

    public CommandParameter(String name, boolean optional)
    {
      this.name = name;
      this.optional = optional;
    }

    public String getName()
    {
      return name;
    }

    public boolean isOptional()
    {
      return optional;
    }
  }

  /**
   * @author Eike Stepper
   */
  public static final class CommandException extends RuntimeException
  {
    private static final long serialVersionUID = 1L;

    public CommandException(String message)
    {
      super(message);
    }
  }

  // /**
  // * @author Eike Stepper
  // */
  // public static abstract class Factory extends org.eclipse.net4j.util.factory.Factory
  // {
  // public Factory(String type)
  // {
  // super(CDOCommand.PRODUCT_GROUP, type);
  // }
  //
  // public abstract CDOCommand create(String description) throws ProductCreationException;
  // }
}
