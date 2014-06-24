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
package com.sonatype.nexus.ldap.internal.ui

import groovy.transform.ToString
import org.hibernate.validator.constraints.NotEmpty
import org.sonatype.nexus.extdirect.model.Password
import org.sonatype.nexus.validation.Update

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

/**
 * LDAP Server exchange object.
 *
 * @since 3.0
 */
@ToString(includePackage = false, includeNames = true)
class LdapServerXO
{
  @NotEmpty(groups = Update)
  String id

  Integer order

  @NotEmpty
  String name
  String url

  @NotNull
  Protocol protocol
  Boolean useTrustStore

  @NotEmpty
  String host

  @NotNull
  @Min(1L)
  @Max(65535L)
  Integer port

  @NotEmpty
  String searchBase

  @NotEmpty
  String authScheme

  String authRealm

  @NotEmpty(groups = AuthScheme)
  String authUsername

  @NotNull(groups = AuthScheme)
  Password authPassword

  @Min(0L)
  Integer connectionTimeout

  @Min(0L)
  Integer connectionRetryDelay

  @Min(0L)
  Integer cacheTimeout

  Boolean backupMirrorEnabled

  @NotNull(groups = BackupMirror)
  Protocol backupMirrorProtocol

  @NotEmpty(groups = BackupMirror)
  String backupMirrorHost

  @NotNull(groups = BackupMirror)
  @Min(1L)
  @Max(65535L)
  Integer backupMirrorPort

  String userBaseDn
  Boolean userSubtree

  @NotEmpty
  String userObjectClass

  String userLdapFilter

  @NotEmpty
  String userIdAttribute

  @NotEmpty
  String userRealNameAttribute

  @NotEmpty
  String userEmailAddressAttribute

  String userPasswordAttribute

  Boolean ldapGroupsAsRoles

  String groupType

  String groupBaseDn

  Boolean groupSubtree

  @NotEmpty(groups = GroupStatic)
  String groupObjectClass

  @NotEmpty(groups = GroupStatic)
  String groupIdAttribute

  @NotEmpty(groups = GroupStatic)
  String groupMemberAttribute

  @NotEmpty(groups = GroupStatic)
  String groupMemberFormat

  @NotEmpty(groups = GroupStatic)
  String userMemberOfAttribute

  public static enum Protocol {
    ldap, ldaps
  }

  public interface AuthScheme
  {}

  public interface BackupMirror
  {}

  public interface GroupDynamic
  {}

  public interface GroupStatic
  {}

}
