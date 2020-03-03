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

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import org.antlr.v4.runtime.CharStream;
import org.netbeans.api.lexer.Token;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;
import org.netbeans.spi.lexer.Lexer;
import org.netbeans.spi.lexer.LexerInput;
import org.netbeans.spi.lexer.LexerRestartInfo;

/**
 *
 * @author mario.schroeder
 */
public final class CtoEditorLexer implements Lexer<CtoTokenId> {

    private final LexerRestartInfo<CtoTokenId> info;
    private final Map<Integer, CtoTokenId> idToToken;

    private final Function<CtoTokenId, Token<CtoTokenId>> tokenFactory;
    private final Supplier<org.antlr.v4.runtime.Token> tokenSupplier;

    public CtoEditorLexer(LexerRestartInfo<CtoTokenId> info, Map<Integer, CtoTokenId> idToToken) {
        this(info, idToToken, new TokenSupplier(info.input()));
    }
    
    CtoEditorLexer(LexerRestartInfo<CtoTokenId> info, Map<Integer, CtoTokenId> idToToken, 
            Supplier<org.antlr.v4.runtime.Token> tokenSupplier) {
        this.info = info;
        this.idToToken = idToToken;
        this.tokenSupplier = tokenSupplier;
        this.tokenFactory = id -> info.tokenFactory().createToken(id);
    }

    @Override
    public Token<CtoTokenId> nextToken() {
        Token<CtoTokenId> createdToken = null;

        org.antlr.v4.runtime.Token token = tokenSupplier.get();

        int type = token.getType();
        if (type != -1) {
            createdToken = createToken(type);
        } else if (info.input().readLength() > 0) {
            createdToken = createToken(CtoLexer.WS);
        }

        return createdToken;
    }

    private Token<CtoTokenId> createToken(int type) {
        Function<Integer, CtoTokenId> mapping = idToToken::get;
        return mapping.andThen(tokenFactory).apply(type);
    }

    @Override
    public Object state() {
        return null;
    }

    @Override
    public void release() {
        //nothing todo
    }

    private static class TokenSupplier implements Supplier<org.antlr.v4.runtime.Token> {

        private final CtoLexer lexer;

        TokenSupplier(LexerInput input) {
            CharStream stream = new LexerCharStream(input);
            lexer = new CtoLexer(stream);
        }

        @Override
        public org.antlr.v4.runtime.Token get() {
            return lexer.nextToken();
        }

    }
}
