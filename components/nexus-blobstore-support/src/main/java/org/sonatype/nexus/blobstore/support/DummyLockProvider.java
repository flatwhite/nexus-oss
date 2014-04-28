/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2014 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
package org.sonatype.nexus.blobstore.support;

import javax.annotation.Nullable;

import org.sonatype.nexus.blobstore.api.BlobId;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A null implementation that returns do-nothing locks and never causes threads to wait.
 *
 * @since 3.0
 */
public class DummyLockProvider
    implements BlobLockProvider
{
  private static final Logger logger = LoggerFactory.getLogger(DummyLockProvider.class);

  @Override
  public BlobLock readLock(final BlobId blobId) {
    return doNothingLock(blobId);
  }

  @Nullable
  @Override
  public BlobLock tryExclusiveLock(final BlobId blobId) {
    return doNothingLock(blobId);
  }

  @Override
  public BlobLock exclusiveLock(final BlobId blobId) {
    return doNothingLock(blobId);
  }

  private BlobLock doNothingLock(final BlobId blobId) {
    logger.debug("Obtaining dummy lock for blob " + blobId);

    return new BlobLock()
    {
      @Override
      public void close() {
        logger.debug("Closing dummy lock for blob {}", blobId);
      }
    };
  }

}
