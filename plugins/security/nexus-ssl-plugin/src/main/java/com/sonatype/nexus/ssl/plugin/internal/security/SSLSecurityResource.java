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
package com.sonatype.nexus.ssl.plugin.internal.security;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.sonatype.nexus.ssl.plugin.SSLPlugin;

import org.sonatype.nexus.plugin.support.StaticSecurityResourceSupport;

/**
 * SSL plugin security resource.
 *
 * @since ssl 1.0
 */
@Named
@Singleton
public class SSLSecurityResource
    extends StaticSecurityResourceSupport
{
  @Inject
  public SSLSecurityResource(final SSLPlugin plugin) {
    super(plugin);
  }
}