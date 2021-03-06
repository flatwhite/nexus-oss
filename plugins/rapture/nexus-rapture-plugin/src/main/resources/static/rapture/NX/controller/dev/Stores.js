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
/**
 * Stores developer panel controller.
 *
 * @since 3.0
 */
Ext.define('NX.controller.dev.Stores', {
  extend: 'Ext.app.Controller',

  refs: [
    {
      ref: 'stores',
      selector: 'nx-dev-stores'
    }
  ],

  /**
   * @protected
   */
  init: function () {
    var me = this;

    me.listen({
      component: {
        'nx-dev-stores combobox': {
          change: me.onStoreSelected
        },
        'nx-dev-stores button[action=load]': {
          click: me.loadStore
        },
        'nx-dev-stores button[action=clear]': {
          click: me.clearStore
        }
      }
    });
  },

  /**
   * @private
   */
  onStoreSelected: function (combobox) {
    var me = this,
        storeId = combobox.getValue(),
        panel = me.getStores(),
        grid = panel.down('grid'),
        store, columns = [];

    if (grid) {
      panel.remove(grid);
    }
    if (storeId) {
      store = Ext.data.StoreManager.get(storeId);
      if (store) {
        Ext.each(store.model.getFields(), function (field) {
          columns.push({ text: field.name, dataIndex: field.name });
        });
        panel.add({
          xtype: 'grid',
          store: store,
          columns: columns
        });
      }
    }
  },

  /**
   * @private
   */
  loadStore: function () {
    var me = this,
        panel = me.getStores(),
        grid = panel.down('grid');

    if (grid) {
      grid.getStore().load();
    }
  },

  /**
   * @private
   */
  clearStore: function () {
    var me = this,
        panel = me.getStores(),
        grid = panel.down('grid');

    if (grid) {
      grid.getStore().removeAll();
    }
  }

});