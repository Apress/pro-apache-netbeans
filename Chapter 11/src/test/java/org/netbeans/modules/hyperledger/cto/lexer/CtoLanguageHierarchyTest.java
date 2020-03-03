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
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * 
 * @author mario.schroeder
 */
public class CtoLanguageHierarchyTest {

    private CtoLanguageHierarchy classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new CtoLanguageHierarchy();
    }

    @Test
    @DisplayName("It should return a non empty collection of tokens.")
    public void createTokenIds_NotEmpty() {
        Collection<CtoTokenId> result = classUnderTest.createTokenIds();
        assertThat(result.isEmpty(), is(false));
    }

}
