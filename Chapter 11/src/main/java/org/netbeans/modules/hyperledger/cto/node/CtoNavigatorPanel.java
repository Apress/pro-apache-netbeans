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

import java.util.Collection;
import java.util.Optional;
import javax.swing.JComponent;

import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.spi.navigator.NavigatorPanel;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.netbeans.modules.hyperledger.cto.node.Bundle.*;

/**
 *
 * @author mario.schroeder
 */
@NbBundle.Messages({
    "CTO_NAV_NAME=Composer Model",
    "CTO_NAV_HINT=Overview of the resource definitions of the file."
})
@NavigatorPanel.Registration(mimeType = FileType.MIME, position = 500, displayName = "#CTO_NAV_NAME")
public class CtoNavigatorPanel implements NavigatorPanel {

    private static final RequestProcessor RP = new RequestProcessor(CtoNavigatorPanel.class.getName(), 1);
    private Lookup.Result<DataObject> selection;
    private Optional<RootNode> rootNode = empty();
    private final MembersView view = new MembersView();

    private final LookupListener selectionListener = ev -> {
        RP.post(() -> {
            rootNode.ifPresent(n -> {
                n.getFactory().cleanup();
                rootNode = empty();
            });
                        
            if (selection != null) {
                display(selection.allInstances());
            }
        });
    };

    @Override
    public String getDisplayName() {
        return CTO_NAV_NAME();
    }

    @Override
    public String getDisplayHint() {
        return CTO_NAV_HINT();
    }

    @Override
    public JComponent getComponent() {
        return view;
    }

    @Override
    public void panelActivated(Lookup lkp) {
        selection = lkp.lookupResult(DataObject.class);
        selection.addLookupListener(selectionListener);
        selectionListener.resultChanged(null);
    }

    @Override
    public void panelDeactivated() {
        selectionListener.resultChanged(null);
        selection.removeLookupListener(selectionListener);
        selection = null;
    }

    @Override
    public Lookup getLookup() {
        return view.getLookup();
    }

    private void display(Collection<? extends DataObject> selectedFiles) {
        if (selectedFiles.size() == 1) {
            DataObject dataObject = selectedFiles.iterator().next();
            RootNode node = new RootNode(dataObject);
            node.getFactory().register();
            rootNode = of(node);
            view.getExplorerManager().setRootContext(node);
        } else {
            view.getExplorerManager().setRootContext(Node.EMPTY);
        }
    }
}
