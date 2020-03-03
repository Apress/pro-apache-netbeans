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

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import static java.lang.String.format;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.MissingResourceException;
import java.util.Optional;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.netbeans.api.editor.completion.Completion;
import org.netbeans.spi.editor.completion.CompletionDocumentation;
import org.netbeans.spi.editor.completion.CompletionItem;
import org.netbeans.spi.editor.completion.CompletionResultSet;
import org.netbeans.spi.editor.completion.CompletionTask;
import org.netbeans.spi.editor.completion.support.AsyncCompletionQuery;
import org.netbeans.spi.editor.completion.support.AsyncCompletionTask;
import org.netbeans.spi.editor.completion.support.CompletionUtilities;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

/**
 *
 * @author mario.schroeder
 */
@NbBundle.Messages({
    "docUrl=https://hyperledger.github.io/composer/latest/reference/cto_language.html"
})
public abstract class AbstractCompletionItem implements CompletionItem {

    private static final String TEMPLATE = "%s ";

    private static final Color SELECTED_COLOR = Color.decode("0x0000B2");
    private final String name;
    private final int startOffset;
    private final int endOffset;

    public AbstractCompletionItem(String name, Pair<Integer, Integer> location) {
        this.name = name;
        this.startOffset = location.first();
        this.endOffset = location.second();
    }

    @Override
    public void defaultAction(JTextComponent jtc) {
        try {
            Document doc = jtc.getDocument();
            doc.remove(startOffset, endOffset - startOffset);
            doc.insertString(startOffset, format(TEMPLATE, name), null);
            Completion.get().hideAll();
        } catch (BadLocationException ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    public void processKeyEvent(KeyEvent ke) {
    }

    @Override
    public int getPreferredWidth(Graphics graphics, Font font) {
        return CompletionUtilities.getPreferredWidth(name, null, graphics, font);
    }

    @Override
    public void render(Graphics grphcs, Font font, Color frontCol, Color backCol, int width, int height, boolean selected) {
        CompletionUtilities.renderHtml(getIcon(), name, null, grphcs, font,
                (selected ? Color.white : SELECTED_COLOR), width, height, selected);
    }

    @Override
    public CompletionTask createDocumentationTask() {
        Optional<String> opt = getMessage(name);
        return opt.map(msg -> new AsyncCompletionTask(new AsyncCompletionQuery() {
            @Override
            protected void query(CompletionResultSet completionResultSet, Document document, int i) {
                completionResultSet.setDocumentation(new Documentation(msg, getDocumentationURL()));
                completionResultSet.finish();
            }
        })).orElse(null);
    }

    @Override
    public CompletionTask createToolTipTask() {
        return null;
    }

    @Override
    public boolean instantSubstitution(JTextComponent jtc) {
        return false;
    }

    @Override
    public CharSequence getSortText() {
        return name;
    }

    @Override
    public CharSequence getInsertPrefix() {
        return name;
    }

    private URL getDocumentationURL() {
        String docUrl = getMessage("docUrl").orElse("");
        try {
            return new URL(docUrl);
        } catch (MalformedURLException ex) {
            return null;
        }
    }

    private Optional<String> getMessage(String key) {
        try {
            return of(NbBundle.getMessage(AbstractCompletionItem.class, key));
        } catch (MissingResourceException e) {
            return empty();
        }
    }

    protected abstract ImageIcon getIcon();

    static class Documentation implements CompletionDocumentation {

        private final String message;

        private final URL docUrl;

        public Documentation(String message, URL docUrl) {
            this.message = message;
            this.docUrl = docUrl;
        }

        @Override
        public String getText() {
            return message;
        }

        @Override
        public URL getURL() {
            return docUrl;
        }

        @Override
        public CompletionDocumentation resolveLink(String string) {
            return null;
        }

        @Override
        public Action getGotoSourceAction() {
            return null;
        }

    }
}
