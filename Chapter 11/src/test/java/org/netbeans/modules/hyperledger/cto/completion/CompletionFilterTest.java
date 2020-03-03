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
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.anyInt;
import org.mockito.Mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.hyperledger.cto.completion.CompletionFilter.FilterResult;

/**
 * @author mario.schroeder
 */
@ExtendWith(MockitoExtension.class)
public class CompletionFilterTest {
    
    @Mock
    private StyledDocument document;
    
    @Mock
    private Element element;
    
    private CompletionFilter classUnderTest;
    
    @BeforeEach
    public void setUp() throws BadLocationException {
        reset(document);
        when(document.getText(anyInt(), anyInt())).thenReturn("foo");
        when(document.getParagraphElement(anyInt())).thenReturn(element);
        
        classUnderTest = new CompletionFilter.FilterImpl();
    }
    
    @Test
    @DisplayName("The filter should be present in the result.")
    public void filter_Presents() {
        FilterResult result = classUnderTest.filter(document, 0);
        assertThat(result.filter.isPresent(), is(true));
    }
    
}
