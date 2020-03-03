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

import org.netbeans.modules.hyperledger.cto.grammar.CtoLexer;

/**
 * This mapping looks for the name of the token in the {@link CtoLexer.VOCABULARY}.
 * 
 * @author mario.schroeder
 */
public interface NameMapping {
    
    /**
     * Looks for the display name in the vocabulary and removes unwanted apostrophes. 
     * So 'foo' becomes foo.
     * @see CtoLexer.VOCABULARY.getDisplayName
     * 
     * @param type as int
     * @return type as String.
     */
    public static String map(int type) {
        String name = CtoLexer.VOCABULARY.getDisplayName(type);
        return name.replaceAll("^\\'|\\'$", "");
    }
}
