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
package org.netbeans.modules.hyperledger.cto.lexer;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.netbeans.modules.hyperledger.cto.FileType;
import org.netbeans.spi.lexer.LanguageHierarchy;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author mario.schroeder
 */
public class CtoLanguageHierarchy extends LanguageHierarchy<CtoTokenId> {


    private final List<CtoTokenId> tokens;
    private final Map<Integer, CtoTokenId> idToToken;

    public CtoLanguageHierarchy() {
        tokens = TokenTaxonomy.INSTANCE.allTokens();
        idToToken = TokenTaxonomy.INSTANCE.getIdTokenMap();
    }

    @Override
    protected Collection<CtoTokenId> createTokenIds() {
        return tokens;
    }

    @Override
    protected Lexer<CtoTokenId> createLexer(LexerRestartInfo<CtoTokenId> info) {
        return new CtoEditorLexer(info, idToToken);
    }

    @Override
    protected String mimeType() {
        return FileType.MIME;
    }
}
