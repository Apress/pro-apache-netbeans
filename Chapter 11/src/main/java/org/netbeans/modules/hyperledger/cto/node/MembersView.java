/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.netbeans.modules.hyperledger.cto.node;

import java.awt.BorderLayout;
import javax.swing.ActionMap;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;
import org.openide.explorer.ExplorerManager;
import org.openide.explorer.ExplorerUtils;
import org.openide.explorer.view.BeanTreeView;
import org.openide.util.Lookup;

/**
 *
 * @author mario.schroeder
 */
final class MembersView extends JPanel implements ExplorerManager.Provider, Lookup.Provider {

    private final ExplorerManager manager;
    private final Lookup lookup;
    private final BeanTreeView view;

    MembersView() {
        this.manager = new ExplorerManager();
        this.lookup = ExplorerUtils.createLookup(manager, new ActionMap());
        view = new BeanTreeView();

        view.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setLayout(new BorderLayout());
        add(view, BorderLayout.CENTER);
    }

    @Override
    public ExplorerManager getExplorerManager() {
        return manager;
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }

    @Override
    public boolean requestFocusInWindow() {
        return view.requestFocusInWindow();
    }
}
