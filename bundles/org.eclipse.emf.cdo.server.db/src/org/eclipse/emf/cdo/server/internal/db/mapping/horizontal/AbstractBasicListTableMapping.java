/*
 * Copyright (c) 2013, 2016, 2018 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Stefan Winkler - initial API and implementation
 */
package org.eclipse.emf.cdo.server.internal.db.mapping.horizontal;

import org.eclipse.emf.cdo.common.branch.CDOBranch;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.common.model.CDOFeatureType;
import org.eclipse.emf.cdo.common.model.CDOModelUtil;
import org.eclipse.emf.cdo.common.revision.delta.CDOAddFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOClearFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOContainerFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOFeatureDeltaVisitor;
import org.eclipse.emf.cdo.common.revision.delta.CDOListFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOMoveFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDORemoveFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOSetFeatureDelta;
import org.eclipse.emf.cdo.common.revision.delta.CDOUnsetFeatureDelta;
import org.eclipse.emf.cdo.server.db.IDBStoreAccessor;
import org.eclipse.emf.cdo.server.db.IIDHandler;
import org.eclipse.emf.cdo.server.db.mapping.IClassMapping;
import org.eclipse.emf.cdo.server.db.mapping.IListMapping3;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.db.mapping.ITypeMapping;
import org.eclipse.emf.cdo.server.internal.db.DBIndexAnnotation;
import org.eclipse.emf.cdo.server.internal.db.bundle.OM;
import org.eclipse.emf.cdo.server.internal.db.mapping.AbstractMappingStrategy;

import org.eclipse.net4j.db.DBException;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.util.om.trace.ContextTracer;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;

/**
 * @author Stefan Winkler
 */
public abstract class AbstractBasicListTableMapping implements IListMapping3, IMappingConstants
{
  private IMappingStrategy mappingStrategy;

  private EClass containingClass;

  private EStructuralFeature feature;

  public AbstractBasicListTableMapping(IMappingStrategy mappingStrategy, EClass containingClass, EStructuralFeature feature)
  {
    this.mappingStrategy = mappingStrategy;
    this.containingClass = containingClass;
    this.feature = feature;
  }

  public final IMappingStrategy getMappingStrategy()
  {
    return mappingStrategy;
  }

  public final EClass getContainingClass()
  {
    return containingClass;
  }

  public final EStructuralFeature getFeature()
  {
    return feature;
  }

  public void addSimpleChunkWhere(IDBStoreAccessor accessor, CDOID cdoid, StringBuilder builder, int index)
  {
    builder.append(LIST_IDX);
    builder.append('=');
    builder.append(index);
  }

  public void addRangedChunkWhere(IDBStoreAccessor accessor, CDOID cdoid, StringBuilder builder, int fromIndex, int toIndex)
  {
    builder.append(LIST_IDX);
    builder.append(" BETWEEN "); //$NON-NLS-1$
    builder.append(fromIndex);
    builder.append(" AND "); //$NON-NLS-1$
    builder.append(toIndex - 1);
  }

  public void setClassMapping(IClassMapping classMapping)
  {
    // Subclasses may override.
  }

  public abstract void rawDeleted(IDBStoreAccessor accessor, CDOID id, CDOBranch branch, int version);

  protected final boolean needsIndexOnValueField(EStructuralFeature feature)
  {
    IMappingStrategy mappingStrategy = getMappingStrategy();
    Set<CDOFeatureType> forceIndexes = AbstractMappingStrategy.getForceIndexes(mappingStrategy);

    if (CDOFeatureType.matchesCombination(feature, forceIndexes))
    {
      return true;
    }

    EClass eClass = getContainingClass();
    EStructuralFeature[] allPersistentFeatures = CDOModelUtil.getClassInfo(eClass).getAllPersistentFeatures();

    for (List<EStructuralFeature> features : DBIndexAnnotation.getIndices(eClass, allPersistentFeatures))
    {
      if (features.size() == 1)
      {
        if (features.get(0) == feature)
        {
          return true;
        }
      }
    }

    return false;
  }

  /**
   * @author Eike Stepper
   */
  public static abstract class AbstractListDeltaWriter implements CDOFeatureDeltaVisitor
  {
    private static final ContextTracer TRACER = new ContextTracer(OM.DEBUG, AbstractListDeltaWriter.class);

    private static final int UNBOUNDED_SHIFT = -1;

    private static final int NO_INDEX = Integer.MIN_VALUE;

    private static final int NONE = 0;

    private static final int SET = 1 << 1;

    private static final int MOVE = 1 << 2;

    private static final int INSERT = 1 << 3;

    private static final int DELETE = 1 << 4;

    protected final IDBStoreAccessor accessor;

    protected final CDOID id;

    private final List<CDOFeatureDelta> listChanges;

    private final List<Manipulation> manipulations;

    private boolean clearFirst;

    private int offsetBefore;

    /**
     * Start of a range [tempIndex, tempIndex-1, ...] which lies outside of the normal list indexes and which serve as
     * temporary space to move items temporarily to get them out of the way of other operations.
     */
    private int tmpIndex = -1;

    private int newListSize;

    public AbstractListDeltaWriter(IDBStoreAccessor accessor, CDOID id, List<CDOFeatureDelta> listChanges, int oldListSize)
    {
      this.accessor = accessor;
      this.id = id;
      this.listChanges = listChanges;

      manipulations = createManipulations(id, listChanges, oldListSize);
      newListSize = oldListSize;
    }

    public void writeListDeltas()
    {
      if (TRACER.isEnabled())
      {
        TRACER.trace("Processing list deltas..."); //$NON-NLS-1$
      }

      for (CDOFeatureDelta listDelta : listChanges)
      {
        listDelta.accept(this);
      }

      // boolean zeroBasedIndex =
      // ((HorizontalNonAuditMappingStrategy)accessor.getStore().getMappingStrategy()).shallForceZeroBasedIndex();
      // if (!zeroBasedIndex)
      if (!isZeroBasedIndex())
      {
        if (TRACER.isEnabled())
        {
          TRACER.trace("Optimizing list indexes..."); //$NON-NLS-1$
        }

        optimizeListIndexes();
      }

      if (TRACER.isEnabled())
      {
        TRACER.trace("Result to be written to DB:");
        for (Manipulation manipulation : manipulations)
        {
          TRACER.trace(manipulation.toString());
        }
      }

      try
      {
        writeResultToDatabase();
      }
      catch (SQLException e)
      {
        throw new DBException(e);
      }

      throw new NewListSizeResult(newListSize);
    }

    public void visit(CDOAddFeatureDelta delta)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("  - insert at {0} value {1}", delta.getIndex(), delta.getValue()); //$NON-NLS-1$
      }

      // Make room for the new item
      shiftIndexes(delta.getIndex(), UNBOUNDED_SHIFT, +1);

      // Create the item
      manipulations.add(Manipulation.createInsertedElement(delta.getIndex(), delta.getValue()));
      ++newListSize;
    }

    public void visit(CDORemoveFeatureDelta delta)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("  - remove at {0}", delta.getIndex()); //$NON-NLS-1$
      }

      Manipulation e = findManipulation(delta.getIndex());
      deleteItem(e);

      // Fill the gap by shifting all subsequent items down
      shiftIndexes(delta.getIndex() + 1, UNBOUNDED_SHIFT, -1);
      --newListSize;
    }

    public void visit(CDOSetFeatureDelta delta)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("  - set at {0} value {1}", delta.getIndex(), delta.getValue()); //$NON-NLS-1$
      }

      Manipulation manipulation = findManipulation(delta.getIndex());

      // Set the new value
      manipulation.value = delta.getValue();

      // If the item is freshly inserted we do not set the SET-mark.
      // Setting the value of a new item results in inserting with the new value at once.
      if (!manipulation.is(INSERT))
      {
        // Else mark the existing item to be set to a new value
        manipulation.addType(SET);
      }
    }

    public void visit(CDOUnsetFeatureDelta delta)
    {
      if (!delta.getFeature().isUnsettable())
      {
        throw new IllegalArgumentException("Feature is not unsettable: " + delta);
      }

      if (TRACER.isEnabled())
      {
        TRACER.format("  - unset list"); //$NON-NLS-1$
      }

      // Set the clear-flag
      clearFirst = true;

      // And also clear all manipulation items
      manipulations.clear();
      newListSize = 0;
    }

    public void visit(CDOClearFeatureDelta delta)
    {
      if (TRACER.isEnabled())
      {
        TRACER.format("  - clear list"); //$NON-NLS-1$
      }

      // Set the clear-flag
      clearFirst = true;

      // And also clear all manipulation items
      manipulations.clear();
      newListSize = 0;
    }

    public void visit(CDOMoveFeatureDelta delta)
    {
      int fromIdx = delta.getOldPosition();
      int toIdx = delta.getNewPosition();

      if (TRACER.isEnabled())
      {
        TRACER.format("  - move {0} -> {1}", fromIdx, toIdx); //$NON-NLS-1$
      }

      // Ignore the trivial case
      if (fromIdx == toIdx)
      {
        return;
      }

      Manipulation manipulation = findManipulation(fromIdx);

      // Adjust indexes and shift either up or down
      if (fromIdx < toIdx)
      {
        shiftIndexes(fromIdx + 1, toIdx, -1);
      }
      else
      {
        // fromIdx > toIdx here
        shiftIndexes(toIdx, fromIdx - 1, +1);
      }

      // Set the new index
      manipulation.dstIndex = toIdx;

      // If it is a new element, no MOVE mark needed, because we insert it at the new position
      if (!manipulation.is(INSERT))
      {
        // Else we need to handle the move of an existing item
        manipulation.addType(MOVE);
      }
    }

    @Deprecated
    public void visit(CDOListFeatureDelta delta)
    {
      throw new UnsupportedOperationException("Should never be called");
    }

    @Deprecated
    public void visit(CDOContainerFeatureDelta delta)
    {
      throw new UnsupportedOperationException("Should never be called");
    }

    protected boolean isZeroBasedIndex()
    {
      return false;
    }

    protected List<Manipulation> createManipulations(CDOID id, List<CDOFeatureDelta> listChanges, int oldListSize)
    {
      List<Manipulation> manipulations = new ArrayList<Manipulation>(oldListSize);

      // Create list and initialize with original indexes
      for (int i = 0; i < oldListSize; i++)
      {
        manipulations.add(Manipulation.createOriginalElement(i));
      }

      return manipulations;
    }

    /**
     * Helper method: shift all (destination) indexes in the interval [from,to] (inclusive at both ends) by offset
     * (positive or negative).
     */
    private void shiftIndexes(int from, int to, int offset)
    {
      for (Manipulation manipulation : manipulations)
      {
        if (manipulation.dstIndex >= from && (to == UNBOUNDED_SHIFT || manipulation.dstIndex <= to))
        {
          manipulation.dstIndex += offset;
        }
      }
    }

    /**
     * Find a manipulation item by destination index).
     */
    private Manipulation findManipulation(int index)
    {
      for (Manipulation manipulation : manipulations)
      {
        if (manipulation.dstIndex == index)
        {
          return manipulation;
        }
      }

      throw new IllegalStateException("Should never be reached");
    }

    /**
     * Delete an element (used in remove and clear)
     */
    private void deleteItem(Manipulation manipulation)
    {
      if (manipulation.is(INSERT))
      {
        // Newly inserted items are simply removed, as removing inserted items is equal to no change at all.
        manipulations.remove(manipulation);
      }
      else
      {
        // Mark the existing item as to be deleted.
        // Previous MOVE and SET conditions are overridden by setting the exclusive DELETE type.
        manipulation.types = DELETE;
        manipulation.dstIndex = NO_INDEX;
      }
    }

    /**
     * Called after all deltas are applied and before the results are written to the database. This method post-processes
     * the manipulation elements in order to minimize database access.
     */
    private void optimizeListIndexes()
    {
      /*
       * This is an optimization which reduces the amount of modifications on the database to maintain list indexes. For the
       * optimization, we let go of the assumption that indexes are zero-based. Instead, we work with an offset at the
       * database level which can change with every change to the list (e.g. if the second element is removed from a list with
       * 1000 elements, instead of shifting down indexes 2 to 1000 by 1, we shift up index 0 by 1 and have now a list with
       * indexes starting at 1 instead of 0. This optimization is applied by modifying the list of Manipulations, which can be
       * seen as the database modification plan.
       */

      // First, get the current offset.
      offsetBefore = getCurrentIndexOffset();
      if (TRACER.isEnabled())
      {
        TRACER.trace("Offset optimization."); //$NON-NLS-1$
        TRACER.trace("Current offset = " + offsetBefore); //$NON-NLS-1$
      }

      applyOffsetToSourceIndexes(offsetBefore);

      int offsetAfter;

      if ((long)Math.abs(offsetBefore) + (long)manipulations.size() > Integer.MAX_VALUE)
      {
        // Safety belt for really huge collections or for collections that have been manipulated lots of times
        // -> Do not optimize after this border is crossed. Instead, reset offset for the whole list to a zero-based
        // index.
        offsetAfter = 0;
      }
      else
      {
        offsetAfter = calculateOptimalOffset();
      }

      if (TRACER.isEnabled())
      {
        TRACER.trace("New offset = " + offsetAfter); //$NON-NLS-1$
      }

      applyOffsetToDestinationIndexes(offsetAfter);

      // Make sure temporary indexes do not get in the way of the other operations.
      tmpIndex = Math.min(offsetBefore, offsetAfter) - 1;
    }

    /**
     * Calculate the optimal offset w.r.t. the manipulations planned. The optimal offset is the offset which occurs the
     * most in the manipulations (because letting this offset be neutral leads to the least manipulations. Note: the
     * zero offset is also regarded as an offset as any other, because selecting an offset != 0 would also lead to
     * elements with original offset 0 to be moved.
     */
    private int calculateOptimalOffset()
    {
      HashMap<Integer, Integer> occurrences = new HashMap<Integer, Integer>();
      int bestOffset = 0;
      int bestOffsetOccurrence = 0;

      for (Manipulation manipulation : manipulations)
      {
        int srcIndex = manipulation.srcIndex;
        int dstIndex = manipulation.dstIndex;

        if (srcIndex != NO_INDEX && dstIndex != NO_INDEX)
        {
          int offset = dstIndex - srcIndex;
          Integer oldOccurrence = occurrences.get(offset);

          int newOccurrence;
          if (oldOccurrence == null)
          {
            newOccurrence = 1;
          }
          else
          {
            newOccurrence = oldOccurrence + 1;
          }

          occurrences.put(offset, newOccurrence);

          // Remember maximum along the way
          if (newOccurrence > bestOffsetOccurrence)
          {
            bestOffsetOccurrence = newOccurrence;
            bestOffset = offset;
          }
        }
      }

      // The offset which has occurred the most has to be applied negatively to normalize the list
      // therefore return the negative offset as the new offset to be applied
      return -bestOffset;
    }

    private void applyOffsetToSourceIndexes(int offsetBefore)
    {
      if (offsetBefore != 0)
      {
        for (Manipulation manipulation : manipulations)
        {
          if (manipulation.srcIndex != NO_INDEX)
          {
            manipulation.srcIndex += offsetBefore;
          }
        }
      }
    }

    private void applyOffsetToDestinationIndexes(int offsetAfter)
    {
      if (offsetAfter != 0)
      {
        for (Manipulation manipulation : manipulations)
        {
          if (manipulation.dstIndex != NO_INDEX)
          {
            // Apply the offset to all indices to make them relative to the new offset
            manipulation.dstIndex += offsetAfter;
          }
        }
      }
    }

    protected final int getOffsetBefore()
    {
      return offsetBefore;
    }

    protected final int getNextTmpIndex()
    {
      return --tmpIndex;
    }

    /**
     * Write calculated changes to the database
     */
    protected void writeResultToDatabase() throws SQLException
    {
      IIDHandler idHandler = accessor.getStore().getIDHandler();
      if (TRACER.isEnabled())
      {
        TRACER.trace("Writing to database:"); //$NON-NLS-1$
      }

      if (clearFirst)
      {
        if (TRACER.isEnabled())
        {
          TRACER.trace(" - clear list"); //$NON-NLS-1$
        }

        clearList();
      }

      for (Manipulation manipulation : manipulations)
      {
        if (manipulation.is(DELETE))
        {
          /*
           * Step 1: DELETE all elements e which have e.is(DELETE) by e.srcIndex
           */
          dbDelete(idHandler, manipulation.srcIndex);

          if (TRACER.isEnabled())
          {
            TRACER.format(" - delete at {0} ", manipulation.srcIndex); //$NON-NLS-1$
          }
        }

        if (manipulation.is(MOVE))
        {
          /*
           * Step 2: MOVE all elements e (by e.srcIndex) which have e.is(MOVE) to tmpIndex (-1, -2, -3, -4, ...) and store
           * tmpIndex in e.tempIndex
           */
          manipulation.tmpIndex = getNextTmpIndex();
          dbMove(idHandler, manipulation.srcIndex, manipulation.tmpIndex, manipulation.srcIndex);

          if (TRACER.isEnabled())
          {
            TRACER.format(" - move {0} -> {1} ", manipulation.srcIndex, manipulation.tmpIndex); //$NON-NLS-1$
          }
        }
      }

      writeShifts(idHandler);

      ITypeMapping typeMapping = getTypeMapping();
      for (Manipulation manipulation : manipulations)
      {
        if (manipulation.is(MOVE))
        {
          /*
           * Step 4: MOVE all elements e have e.is(MOVE) from e.tempIdx to e.dstIndex (because we have moved them before, moveStmt
           * is always initialized
           */
          dbMove(idHandler, manipulation.tmpIndex, manipulation.dstIndex, manipulation.srcIndex);

          if (TRACER.isEnabled())
          {
            TRACER.format(" - move {0} -> {1} ", manipulation.tmpIndex, manipulation.dstIndex); //$NON-NLS-1$
          }
        }

        if (manipulation.is(SET))
        {
          /*
           * Step 5: SET all elements which have e.type == SET by index == e.dstIndex
           */
          dbSet(idHandler, typeMapping, manipulation.dstIndex, manipulation.value, manipulation.srcIndex);

          if (TRACER.isEnabled())
          {
            TRACER.format(" - set value at {0} to {1} ", manipulation.dstIndex, manipulation.value); //$NON-NLS-1$
          }
        }

        if (manipulation.is(INSERT))
        {
          /*
           * Step 6: INSERT all elements which have e.type == INSERT.
           */
          dbInsert(idHandler, typeMapping, manipulation.dstIndex, manipulation.value);

          if (TRACER.isEnabled())
          {
            TRACER.format(" - insert value at {0} : value {1} ", manipulation.dstIndex, manipulation.value); //$NON-NLS-1$
          }
        }
      }
    }

    /**
     * Perform the shift operations to adjust indexes resulting from remove, insert, and move operations.
     *
     * @see #writeResultToDatabase(IDBStoreAccessor, CDOID)
     * @throws SQLException
     */
    protected void writeShifts(IIDHandler idHandler) throws SQLException
    {
      /*
       * Step 3: shift all elements which have to be shifted up or down because of add, remove or move of other elements to
       * their proper position. This has to be done in two phases to avoid collisions, as the index has to be unique and shift
       * up operations have to be executed in top to bottom order.
       */
      LinkedList<Shift> shiftOperations = new LinkedList<Shift>();

      /*
       * If a necessary shift is detected (source and destination indices differ), firstIndex is set to the current index and
       * currentOffset is set to the offset of the shift operation. When a new offset is detected or the range is interrupted,
       * we record the range and start a new one if needed.
       */
      int rangeStartIndex = NO_INDEX;
      int rangeOffset = 0;
      int lastElementIndex = NO_INDEX;

      // Iterate through the manipulationElements and collect the necessary operations
      for (Manipulation manipulation : manipulations)
      {
        /*
         * Shift applies only to elements which are not moved, inserted or deleted (i.e. only plain SET and NONE are affected)
         */
        if (manipulation.types == NONE || manipulation.types == SET)
        {
          int elementOffset = manipulation.dstIndex - manipulation.srcIndex;

          /*
           * First make sure if we have to close a previous range. This is the case, if the current element's offset differs from
           * the rangeOffset and a range is open.
           */
          if (elementOffset != rangeOffset && rangeStartIndex != NO_INDEX)
          {
            // There is an open range but the rangeOffset differs. We have to close the open range
            shiftOperations.add(new Shift(rangeStartIndex, lastElementIndex, rangeOffset));

            // And reset the state
            rangeStartIndex = NO_INDEX;
            rangeOffset = 0;
          }

          /*
           * At this point, either a range is open, which means that the current element also fits in the range (i.e. the offsets
           * match) or no range is open. In the latter case, we have to open one if the current element's offset is not 0.
           */
          if (elementOffset != 0 && rangeStartIndex == NO_INDEX)
          {
            rangeStartIndex = manipulation.srcIndex;
            rangeOffset = elementOffset;
          }
        }
        else
        {
          // Shift does not apply to this element because of its type
          if (rangeStartIndex != NO_INDEX)
          {
            // If there is an open range, we have to close and remember it
            shiftOperations.add(new Shift(rangeStartIndex, lastElementIndex, rangeOffset));

            // And reset the state
            rangeStartIndex = NO_INDEX;
            rangeOffset = 0;
          }
        }

        lastElementIndex = manipulation.srcIndex;
      }

      // After the iteration, we have to make sure that we remember the last open range, if it is there
      if (rangeStartIndex != NO_INDEX)
      {
        shiftOperations.add(new Shift(rangeStartIndex, lastElementIndex, rangeOffset));
      }

      /*
       * Now process the operations. Move down operations can be performed directly, move up operations need to be performed
       * later in the reverse direction
       */
      ListIterator<Shift> operationIt = shiftOperations.listIterator();
      writeShiftsDown(idHandler, operationIt);
      writeShiftsUp(idHandler, operationIt);
    }

    protected void writeShiftsDown(IIDHandler idHandler, ListIterator<Shift> operationIt) throws SQLException
    {
      while (operationIt.hasNext())
      {
        Shift operation = operationIt.next();
        if (operation.offset < 0)
        {
          dbShiftDown(idHandler, operation.offset, operation.startIndex, operation.endIndex);

          if (TRACER.isEnabled())
          {
            TRACER.format(" - shift down {0} ", operation); //$NON-NLS-1$
          }

          operationIt.remove();
        }
      }
    }

    protected void writeShiftsUp(IIDHandler idHandler, ListIterator<Shift> operationIt) throws SQLException
    {
      while (operationIt.hasPrevious())
      {
        Shift operation = operationIt.previous();
        dbShiftUp(idHandler, operation.offset, operation.startIndex, operation.endIndex);

        if (TRACER.isEnabled())
        {
          TRACER.format(" - shift up {0} ", operation); //$NON-NLS-1$
        }
      }
    }

    protected abstract void dbDelete(IIDHandler idHandler, int index) throws SQLException;

    protected abstract void dbMove(IIDHandler idHandler, int fromIndex, int toIndex, int srcIndex) throws SQLException;

    protected abstract void dbSet(IIDHandler idHandler, ITypeMapping typeMapping, int index, Object value, int srcIndex) throws SQLException;

    protected abstract void dbInsert(IIDHandler idHandler, ITypeMapping typeMapping, int index, Object value) throws SQLException;

    protected abstract void dbShiftDown(IIDHandler idHandler, int offset, int startIndex, int endIndex) throws SQLException;

    protected abstract void dbShiftUp(IIDHandler idHandler, int offset, int startIndex, int endIndex) throws SQLException;

    protected static void close(PreparedStatement... stmts)
    {
      Throwable t = null;

      for (PreparedStatement stmt : stmts)
      {
        try
        {
          if (stmt != null)
          {
            try
            {
              stmt.clearBatch();
            }
            catch (SQLException e)
            {
              throw new DBException(e);
            }
            finally
            {
              DBUtil.close(stmt);
            }
          }
        }
        catch (Throwable th)
        {
          if (t == null)
          {
            // Remember first exception
            t = th;
          }

          // More exceptions go to the log
          OM.LOG.error(t);
        }
      }

      if (t != null)
      {
        throw new DBException(t);
      }
    }

    protected abstract ITypeMapping getTypeMapping();

    protected abstract int getCurrentIndexOffset();

    protected abstract void clearList();

    /**
     * @author Eike Stepper
     */
    public static final class NewListSizeResult extends RuntimeException
    {
      private static final long serialVersionUID = 1L;

      private final int newListSize;

      public NewListSizeResult(int newListSize)
      {
        this.newListSize = newListSize;
      }

      public int getNewListSize()
      {
        return newListSize;
      }
    }

    /**
     * @author Eike Stepper
     */
    public static final class Manipulation
    {
      private static final Object NIL = new Object()
      {
        @Override
        public String toString()
        {
          return "NIL";
        }
      };

      public int types;

      public int srcIndex;

      public int tmpIndex;

      public int dstIndex;

      public Object value;

      public Manipulation(int types, int srcIndex, int dstIndex, Object value)
      {
        this.types = types;
        this.srcIndex = srcIndex;
        tmpIndex = NO_INDEX;
        this.dstIndex = dstIndex;
        this.value = value;
      }

      public boolean is(int type)
      {
        return type == NONE ? types == NONE : (types & type) != 0;
      }

      public void addType(int type)
      {
        types |= type;
      }

      @Override
      public String toString()
      {
        return MessageFormat.format("Manipulation[types={0}, srcIndex={1}, tmpIndex={2}, dstIndex={3}, value={4}]", formatTypes(types), formatIndex(srcIndex),
            formatIndex(tmpIndex), formatIndex(dstIndex), String.valueOf(value));
      }

      /**
       * Create a Manipulation which represents an element which already is in the list.
       */
      public static Manipulation createOriginalElement(int index)
      {
        return new Manipulation(NONE, index, index, NIL);
      }

      /**
       * Create a Manipulation which represents an element which is inserted in the list.
       */
      public static Manipulation createInsertedElement(int index, Object value)
      {
        return new Manipulation(INSERT, NO_INDEX, index, value);
      }

      private static String formatTypes(int types)
      {
        StringBuilder builder = new StringBuilder();
        formatType(types, DELETE, "DELETE", builder);
        formatType(types, INSERT, "INSERT", builder);
        formatType(types, MOVE, "MOVE", builder);
        formatType(types, SET, "SET", builder);

        if (builder.length() != 0)
        {
          return builder.toString();
        }

        return "NONE";
      }

      private static void formatType(int types, int type, String label, StringBuilder builder)
      {
        if ((types & type) != 0)
        {
          if (builder.length() != 0)
          {
            builder.append("|");
          }

          builder.append(label);
        }
      }

      private static String formatIndex(int index)
      {
        if (index == NO_INDEX)
        {
          return "NONE";
        }

        return Integer.toString(index);
      }
    }

    /**
     * @author Eike Stepper
     */
    public static final class Shift
    {
      public final int startIndex;

      public final int endIndex;

      public final int offset;

      public Shift(int startIndex, int endIndex, int offset)
      {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
        this.offset = offset;
      }

      @Override
      public String toString()
      {
        return "Shift[" + startIndex + ".." + endIndex + ", offset=" + offset + "]";
      }
    }
  }
}
