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

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import org.netbeans.api.annotations.common.StaticResource;
import org.netbeans.modules.hyperledger.cto.CtoResource;
import org.netbeans.modules.hyperledger.cto.parser.NameMapping;
import org.openide.loaders.DataObject;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.text.Line;
import org.openide.text.NbDocument;
import org.openide.util.RequestProcessor;

import static java.lang.String.format;

/**
 * This class is a child node in the navigator view. 
 * A node represents a resource from the cto file.
 * 
 * @author mario.schroeder
 */
public final class ChildNode extends AbstractNode {
    
    @StaticResource
    private static final String ICON = "org/netbeans/modules/hyperledger/cto/blue.png";
    private static final String MEMBER = "%s : %s";
    private static final RequestProcessor RP = new RequestProcessor();
    
    private final DataObject dataObject;
    private final CtoResource resource;
    private final Action openAction = new AbstractAction() {
        
        @Override
        public void actionPerformed(ActionEvent event) {
            RP.post(() -> {
                NbDocument.openDocument(dataObject, resource.getOffset(), 
                        Line.ShowOpenType.OPEN, Line.ShowVisibilityType.FOCUS);
            });
        }
    };
    
    public ChildNode(DataObject dataObject, CtoResource resource) {
        super(Children.LEAF);
        this.dataObject = dataObject;
        this.resource = resource;
        setIconBaseWithExtension(ICON);
        
        String type = NameMapping.map(resource.getType());
        setDisplayName(format(MEMBER, resource.getName(), type));
    }
    
    @Override
    public Action getPreferredAction() {
        return openAction;
    }
}
