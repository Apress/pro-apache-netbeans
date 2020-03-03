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

import java.util.Optional;
import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

/**
 * Item for the keyword category.
 * 
 * @author mario.schroeder
 */
@NbBundle.Messages({
    "asset=Asset is an class definition that represent something valuable which is exchanged within the network.",
    "participant=Participant is a member of the network that may hold the asset.",
    "transaction=Transaction is the process when an assets changes the owner, e.g. from one participant to another."
})
public class KeywordCompletionItem extends AbstractCompletionItem {

    private final ImageIcon icon;

    public KeywordCompletionItem(Optional<String> iconPath, String name, Pair<Integer, Integer> location) {
        super(name, location);
        icon = iconPath.map(path -> new ImageIcon(ImageUtilities.loadImage(path))).orElse(null);
    }

    @Override
    public int getSortPriority() {
        return (icon != null) ? 50 : 100;
    }

    @Override
    protected ImageIcon getIcon() {
        return icon;
    }

}
