/*
 * #%L
 * gitools-ui-platform
 * %%
 * Copyright (C) 2013 Universitat Pompeu Fabra - Biomedical Genomics group
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the 
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public 
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */
package org.gitools.ui.core.components.editor;

import org.gitools.api.analysis.IProgressMonitor;
import org.gitools.api.components.IEditor;
import org.gitools.ui.core.actions.ActionManager;
import org.gitools.ui.core.components.boxes.Box;
import org.gitools.ui.platform.view.AbstractView;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class AbstractEditor<M> extends AbstractView implements IEditor<M> {

    private final List<EditorListener> listeners = new ArrayList<>();
    private File file;
    private boolean dirty = false;
    private boolean saveAsAllowed = false;
    private boolean saveAllowed = false;

    @Override
    public void setName(String name) {
        String oldName = getName();
        if (oldName == null || !oldName.equals(name)) {
            super.setName(name);
            for (EditorListener l : listeners)
                l.nameChanged(this);
        }
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        if (this.file != file || !this.file.equals(file)) {
            this.file = file;
            for (EditorListener l : listeners)
                l.fileChanged(this);
            if (file != null) {
                setName(file.getName());
            }
        }
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    protected void setDirty(boolean dirty) {
        if (this.dirty != dirty) {
            this.dirty = dirty;
            for (EditorListener l : listeners)
                l.dirtyChanged(this);
            ActionManager.getDefault().updateEnabledByEditor(this);
        }
    }

    @Override
    public boolean isSaveAsAllowed() {
        return saveAsAllowed;
    }

    protected void setSaveAsAllowed(boolean saveAsAllowed) {
        this.saveAsAllowed = saveAsAllowed;
    }

    @Override
    public boolean isSaveAllowed() {
        return saveAllowed;
    }

    protected void setSaveAllowed(boolean saveAllowed) {
        this.saveAllowed = saveAllowed;
    }

    @Override
    public void doSave(IProgressMonitor monitor) {
        for (EditorListener l : listeners)
            l.saved(this);
    }

    @Override
    public void doSaveAs(IProgressMonitor monitor) {
        for (EditorListener l : listeners)
            l.saved(this);
    }

    @Override
    public void doVisible() {
    }

    @Override
    public boolean doClose() {
        return true;
    }

    @Override
    public void detach() {
        // Override if needed
    }

    public void addEditorListener(EditorListener listener) {
        listeners.add(listener);
    }

    public Collection<Box> getBoxes() {
        return null;
    }

    public static abstract class EditorListener {
        public void nameChanged(IEditor editor) {
        }

        public void fileChanged(IEditor editor) {
        }

        public void dirtyChanged(IEditor editor) {
        }

        public void saved(IEditor editor) {
        }

    }

}
