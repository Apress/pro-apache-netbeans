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
package org.netbeans.modules.hyperledger.cto;

/**
 * A resource like an asset from the cto source.
 *
 * @author mario.schroeder
 */
public class CtoResource {

    private final String name;
    private final int type;
    private final int offset;

    public CtoResource(String name, int type, int offset) {
        this.name = name;
        this.type = type;
        this.offset = offset;
    }

    /**
     * Name of the resource
     *
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Type of the resource.
     *
     * @return int
     */
    public int getType() {
        return type;
    }

    /**
     * Return the offset in the editor.
     *
     * @return int
     */
    public int getOffset() {
        return offset;
    }
}
