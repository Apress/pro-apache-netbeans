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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.netbeans.modules.hyperledger.LookupContext;
import org.netbeans.modules.hyperledger.cto.CtoResource;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParser;
import org.netbeans.modules.hyperledger.cto.parser.ParserListener;
import org.netbeans.modules.hyperledger.cto.parser.ParserProvider;
import org.openide.filesystems.FileChangeAdapter;
import org.openide.filesystems.FileEvent;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataNode;
import org.openide.loaders.DataObject;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;

/**
 *
 * @author mario.schroeder
 */
final class MembersFactory extends ChildFactory<CtoResource> implements LookupListener {

    private final LookupContext lookupContext = LookupContext.INSTANCE;

    private Collection<CtoResource> resources = new ArrayList<>();

    private final DataNode root;
    
    private boolean fromFile = true;

    private Lookup.Result<List> selection;
    
    private final FileChangeAdapter adapter = new FileChangeAdapter() {
        @Override
        public void fileChanged(FileEvent fe) {
            fromFile = true;
            refresh(false);
        }
    };

    MembersFactory(DataNode root) {
        this.root = root;
    }

    private FileObject getPrimaryFile() {
        return getDataObject().getPrimaryFile();
    }

    private DataObject getDataObject() {
        return root.getDataObject();
    }

    @Override
    protected Node createNodeForKey(CtoResource resource) {
        if (CtoLexer.NAMESPACE == resource.getType()) {
            updateRootName(resource.getName());
            return null;
        } else {
            return new ChildNode(getDataObject(), resource);
        }
    }

    @Override
    protected boolean createKeys(List<CtoResource> toPopulate) {
        if(fromFile) {
            resources = parseFile();
            fromFile = false;
        }
        
        resources.forEach(toPopulate::add);
        return true;
    }
    
    private List<CtoResource> parseFile() {
        ParserListener listener = new ParserListener();
        try {
            String text = getPrimaryFile().asText();
            CtoParser parser = ParserProvider.INSTANCE.apply(text);
            parser.addParseListener(listener);
            parser.modelUnit();
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
        return listener.getResources();
    };

    private void updateRootName(String rootName) {
        String oldName = root.getDisplayName();
        if (!rootName.equals(oldName)) {
            root.setDisplayName(rootName);
        }
    }

    void register() {
        getPrimaryFile().addFileChangeListener(adapter);
        selection = lookupContext.getLookup().lookupResult(List.class);
        selection.addLookupListener(this);
    }

    void cleanup() {
        getPrimaryFile().removeFileChangeListener(adapter);
        selection.removeLookupListener(this);
    }

    @Override
    public void resultChanged(LookupEvent ev) {
        if (selection != null) {
            //consume and remove
            Collection<? extends List> results = selection.allInstances();
            if (!results.isEmpty()) {
                Collection<CtoResource> instance = results.iterator().next();
                resources = instance;
                lookupContext.remove(instance);
                refresh(false);
            }
        }
    }
}
