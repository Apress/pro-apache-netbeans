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

import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileSystem;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 *
 * @author mario.schroeder
 */
public class RootNodeTest {
    
    private RootNode classUnderTest;
    
    @BeforeEach
    void setup() throws IOException {
        FileSystem fs = FileUtil.createMemoryFileSystem();
        FileObject fileObject = fs.getRoot().createData("sample.cto");
        DataObject dataObject = DataObject.find(fileObject);

        classUnderTest = new RootNode(dataObject);
    }
    
    @Test
    @DisplayName("The root should return an instance of the factory for children.")
    public void getFactory() {
        MembersFactory result = classUnderTest.getFactory();
        assertThat(result, notNullValue());
    }
    
}
