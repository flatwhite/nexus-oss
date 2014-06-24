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
package org.sonatype.security.ldap.realms;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import com.sonatype.nexus.ldap.LdapPlugin;

import org.sonatype.sisu.goodies.eventbus.EventBus;

import org.eclipse.sisu.Description;

@Singleton
@Named(LdapPlugin.REALM_NAME)
@Description("Test Authentication LDAP Realm")
public class SimpleLdapAuthenticatingRealm
    extends AbstractLdapAuthenticatingRealm
{

  @Inject
  public SimpleLdapAuthenticatingRealm(final EventBus eventBus, final LdapManager ldapManager) {
    super(eventBus, ldapManager);
    setName(LdapPlugin.REALM_NAME);
  }
}
