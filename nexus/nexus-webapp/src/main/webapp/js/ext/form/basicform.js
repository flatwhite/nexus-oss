/*
 * Sonatype Nexus (TM) Open Source Version
 * Copyright (c) 2007-2012 Sonatype, Inc.
 * All rights reserved. Includes the third-party code listed at http://links.sonatype.com/products/nexus/oss/attributions.
 *
 * This program and the accompanying materials are made available under the terms of the Eclipse Public License Version 1.0,
 * which accompanies this distribution and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Sonatype Nexus (TM) Professional Version is available from Sonatype, Inc. "Sonatype" and "Sonatype Nexus" are trademarks
 * of Sonatype, Inc. Apache Maven is a trademark of the Apache Software Foundation. M2eclipse is a trademark of the
 * Eclipse Foundation. All other trademarks are the property of their respective owners.
 */
/*global define*/
define(['extjs'], function(Ext) {
Ext.override(Ext.form.BasicForm, {
  clearInvalid : function() {
    // same as before, but ignore items without clearInvalid (== non-form-items)
    this.items.each(function(f) {
      if (f.clearInvalid) {
        f.clearInvalid();
      }
    });
  },
  /**
   * Override findField to look for enabled field and return that, otherwise
   * return first found
   */
  findField : function(id) {
    var
          field = null,
          fallbackField = null;
    this.items.each(function(f) {
      if (f.isFormField && (f.dataIndex === id || f.id === id || f.getName() === id))
      {
        // Only want to grab the first one found, to match default
        // behaviour
        if (!fallbackField)
        {
          fallbackField = f;
        }

        // If the field isn't disabled use it
        if (f.disabled === false)
        {
          field = f;
          return false;
        }
      }
    });

    if (!field)
    {
      field = Ext.value(fallbackField, this.items.get(id));
    }

    return field || null;
  }
});
});
