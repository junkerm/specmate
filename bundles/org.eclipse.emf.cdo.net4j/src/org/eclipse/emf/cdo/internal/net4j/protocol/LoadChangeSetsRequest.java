/*
 * Copyright (c) 2010-2012, 2016, 2017 Eike Stepper (Loehne, Germany) and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Eike Stepper - initial API and implementation
 */
package org.eclipse.emf.cdo.internal.net4j.protocol;

import org.eclipse.emf.cdo.common.branch.CDOBranchPointRange;
import org.eclipse.emf.cdo.common.commit.CDOChangeSetData;
import org.eclipse.emf.cdo.common.protocol.CDODataInput;
import org.eclipse.emf.cdo.common.protocol.CDODataOutput;
import org.eclipse.emf.cdo.common.protocol.CDOProtocolConstants;
import org.eclipse.emf.cdo.spi.common.branch.CDOBranchUtil;

import java.io.IOException;

/**
 * @author Eike Stepper
 */
public class LoadChangeSetsRequest extends CDOClientRequest<CDOChangeSetData[]>
{
  private CDOBranchPointRange[] ranges;

  public LoadChangeSetsRequest(CDOClientProtocol protocol, CDOBranchPointRange... ranges)
  {
    super(protocol, CDOProtocolConstants.SIGNAL_LOAD_CHANGE_SETS);
    this.ranges = ranges;
  }

  @Override
  protected void requesting(CDODataOutput out) throws IOException
  {
    out.writeXInt(ranges.length);
    for (CDOBranchPointRange range : ranges)
    {
      CDOBranchUtil.writeRange(out, range);
    }
  }

  @Override
  protected CDOChangeSetData[] confirming(CDODataInput in) throws IOException
  {
    CDOChangeSetData[] result = new CDOChangeSetData[ranges.length];
    for (int i = 0; i < result.length; i++)
    {
      CDOChangeSetData changeSetData = in.readCDOChangeSetData();
      result[i] = changeSetData;
    }

    return result;
  }
}
