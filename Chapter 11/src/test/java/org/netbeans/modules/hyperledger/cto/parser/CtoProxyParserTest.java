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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.hyperledger.cto.parser.CtoProxyParser.CtoParserResult;
import org.netbeans.modules.parsing.api.Snapshot;
import org.netbeans.modules.parsing.api.Task;
import org.netbeans.modules.parsing.spi.Parser;
import org.netbeans.modules.parsing.spi.SourceModificationEvent;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;

/**
 *
 * @author mario.schroeder
 */
@ExtendWith(MockitoExtension.class)
public class CtoProxyParserTest {
    
    private static final String TEXT = "namespace foo\n";
    
    @Mock
    private Snapshot snapshot;
    
    @Mock
    private Task task;
    
    @Mock
    private SourceModificationEvent event;

    private CtoProxyParser classUnderTest;

    @BeforeEach
    public void setUp() {
        classUnderTest = new CtoProxyParser(ParserProvider.INSTANCE);
        given(snapshot.getText()).willReturn(TEXT);
    }

    /**
     * Test of parse method, of class CtoProxyParser.
     */
    @Test
    @DisplayName("The parse method should produce a result")
    public void parse() throws Exception {
        classUnderTest.parse(snapshot, task, event);
        Parser.Result result = classUnderTest.getResult(task);
        assertThat(result instanceof CtoParserResult, is(true));
    }
}
