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

import javax.swing.Action;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.netbeans.modules.hyperledger.cto.CtoResource;
import org.openide.loaders.DataObject;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author mario.schroeder
 */
@ExtendWith(MockitoExtension.class)
public class ChildNodeTest {
    @Mock
    private DataObject dataObject;
    
    private CtoResource resource;
    
    private ChildNode classUnderTest;
    
    @BeforeEach
    void setup() {
        resource = new CtoResource("foo", 10, 0);
        classUnderTest = new ChildNode(dataObject, resource);
    }

    @Test
    @DisplayName("The node should have a preferred action")
    public void getPreferredAction() {
        Action result = classUnderTest.getPreferredAction();
        assertThat(result, notNullValue());
    }
    
    @Test
    @DisplayName("The node name should show name and type")
    public void getDisplayName() {
        String result = classUnderTest.getDisplayName();
        assertThat(result, equalTo("foo : namespace"));
    }
}
