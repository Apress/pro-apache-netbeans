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
package org.netbeans.modules.hyperledger.cto.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.netbeans.modules.hyperledger.cto.CtoResource;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParser;
import org.netbeans.modules.hyperledger.cto.grammar.CtoParserBaseListener;

/**
 *
 * @author mario.schroeder
 */
public final class ParserListener extends CtoParserBaseListener {

    private final List<CtoResource> resources = new ArrayList<>();

    public List<CtoResource> getResources() {
        return resources;
    }

    private void addNode(TerminalNode node, int type, int offset) {
        if (node != null && !(node instanceof ErrorNode)) {
            addNode(node.getText(), type, offset);
        }
    }
    
    private void addNode(String text, int type, int offset) {
        resources.add(new CtoResource(text, type, offset));
    }
    
    private int getStart(ParserRuleContext ctx) {
        return ctx.getStart().getStartIndex();
    }

    @Override
    public void exitNamespaceDeclaration(CtoParser.NamespaceDeclarationContext ctx) {
        CtoParser.QualifiedNameContext qualCtx = ctx.qualifiedName();
        if (qualCtx != null) {
            List<TerminalNode> identifiers = qualCtx.IDENTIFIER();
            String name = identifiers.stream().map(TerminalNode::getText).collect(Collectors.joining("."));
            addNode(name, CtoLexer.NAMESPACE, getStart(ctx));
        }
    }

    @Override
    public void exitAssetDeclaration(CtoParser.AssetDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.ASSET, getStart(ctx));
    }

    @Override
    public void exitParticipantDeclaration(CtoParser.ParticipantDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.PARTICIPANT, getStart(ctx));
    }

    @Override
    public void exitTransactionDeclaration(CtoParser.TransactionDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.TRANSACTION, getStart(ctx));
    }

    @Override
    public void exitEventDeclaration(CtoParser.EventDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.EVENT, getStart(ctx));
    }

    @Override
    public void exitEnumDeclaration(CtoParser.EnumDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(), CtoLexer.ENUM, getStart(ctx));
    }

    @Override
    public void exitConceptDeclaration(CtoParser.ConceptDeclarationContext ctx) {
        addNode(ctx.IDENTIFIER(0), CtoLexer.CONCEPT, getStart(ctx));
    }
}
