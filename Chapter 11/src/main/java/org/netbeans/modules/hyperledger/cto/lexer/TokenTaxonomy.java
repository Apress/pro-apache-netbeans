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


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;

import static java.util.stream.Collectors.*;

import org.netbeans.modules.hyperledger.cto.parser.NameMapping;

/**
 * Class for organizing similar tokens into groups.
 * 
 * @author mario.schroeder
 */
public enum TokenTaxonomy {

    INSTANCE;

    private final List<CtoTokenId> tokens;

    private TokenTaxonomy() {
        tokens = new ArrayList<>();

        int max = CtoLexer.VOCABULARY.getMaxTokenType() + 1;
        for (int i = 1; i < max; i++) {
            CtoTokenId token = new CtoTokenId(NameMapping.map(i), getCategory(i), i);
            tokens.add(token);
        }
    }

    private String getCategory(int token) {
        Function<Integer, Category> mapping = t -> {
            if (t < CtoLexer.BOOLEAN) {
                return Category.KEYWORD;
            } else if (t < CtoLexer.LPAREN) {
                return Category.TYPE;
            } else if (t < CtoLexer.REF) {
                return Category.SEPARATOR;
            } else if (t < CtoLexer.DECIMAL_LITERAL) {
                return Category.FIELD;
            } else if (t < CtoLexer.WS || t == CtoLexer.CHAR_LITERAL || t == CtoLexer.STRING_LITERAL) {
                return Category.VALUE;
            } else if (t == CtoLexer.COMMENT || t == CtoLexer.LINE_COMMENT) {
                return Category.COMMENT;
            }
            return Category.TEXT;
        };

        return mapping.apply(token).name();
    }

    public List<CtoTokenId> allTokens() {
        return tokens;
    }

    public List<CtoTokenId> tokens(Category category) {
        return tokens.stream().filter(t -> category.name().equals(t.primaryCategory())).collect(toList());
    }

    public Map<Integer, CtoTokenId> getIdTokenMap() {
        return tokens.stream().collect(toMap(CtoTokenId::ordinal, t -> t));
    }
}
