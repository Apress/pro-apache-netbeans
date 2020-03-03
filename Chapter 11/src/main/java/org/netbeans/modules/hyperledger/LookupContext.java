/* 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.netbeans.modules.hyperledger;

import org.openide.util.Lookup;
import org.openide.util.lookup.AbstractLookup;
import org.openide.util.lookup.InstanceContent;

/**
 *
 * @author mario.schroeder
 */
public enum LookupContext implements Lookup.Provider{
    INSTANCE;
    
    private final InstanceContent content;
    private final Lookup lookup;
    
    private LookupContext() {
        this.content = new InstanceContent();
        this.lookup = new AbstractLookup(content);
    }

    @Override
    public Lookup getLookup() {
        return lookup;
    }
    
    public void add(Object inst) {
        content.add(inst);
    }
    
    public void remove(Object inst) {
        content.remove(inst);
    }
}
