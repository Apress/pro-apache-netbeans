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

import org.netbeans.modules.hyperledger.cto.FileType;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.Children;

/**
 *
 * @author mario.schroeder
 */
final class RootNode extends DataNode {
    
    private final MembersFactory factory;

    RootNode(DataObject obj) {
        super(obj, Children.LEAF);
        factory = new MembersFactory(this);
        
        setIconBaseWithExtension(FileType.ICON);
        setChildren(Children.create(factory, true));
    }

    MembersFactory getFactory() {
        return factory;
    } 
}
