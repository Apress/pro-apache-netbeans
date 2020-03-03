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
package org.netbeans.modules.hyperledger.cto.completion;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.openide.util.Pair;

/**
 * @author mario.schroeder
 */
@ExtendWith(MockitoExtension.class)
public class CtoCompletionQueryTest {
    @Mock
    private CompletionResultSet resultSet;
    
    @Mock
    private CompletionFilter completionFilter;
    
    @Mock
    private Document document;
    
    @InjectMocks
    private CtoCompletionQuery classUnderTest;
    
    @BeforeEach
    public void setUp() throws BadLocationException {
        reset(document, completionFilter);
        
        CompletionFilter.FilterResult result = new CompletionFilter.FilterResult();
        result.location = Pair.of(0, 0);
        when(completionFilter.filter(document, 0)).thenReturn(result);
    }
    
    @Test
    @DisplayName("The query should add items to the result set.")
    public void query_AddItems() {
        classUnderTest.query(resultSet, document, 0);
        verify(resultSet, times(2)).addAllItems(anyList());
    }
    
}
