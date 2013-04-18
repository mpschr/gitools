/*
 * #%L
 * gitools-core
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
package org.gitools.model;

import org.jetbrains.annotations.Nullable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serializable;
import java.util.ArrayList;

@XmlAccessorType(XmlAccessType.FIELD)
public abstract class AbstractModel implements IModel, Serializable, Cloneable {
    public static final String PROPERTY_CHANGED = "propertyChanged";

    private transient ArrayList<PropertyChangeListener> listeners;

    private transient boolean quiet = false;

    protected AbstractModel() {
    }

    ArrayList<PropertyChangeListener> getListeners() {
        if (listeners == null) {
            listeners = new ArrayList<PropertyChangeListener>(0);
        }
        return listeners;
    }

    @Override
    public void addPropertyChangeListener(@Nullable PropertyChangeListener listener) {
        if (listener != null) {
            getListeners().add(listener);
        }
    }

    @Override
    public void removePropertyChangeListener(@Nullable PropertyChangeListener listener) {
        if (listener != null) {
            getListeners().remove(listener);
        }
    }

    protected void firePropertyChange(PropertyChangeEvent evt) {
        if (!quiet) {
            for (PropertyChangeListener l : getListeners())
                l.propertyChange(evt);
        }
    }

    protected void firePropertyChange(String propName) {
        if (!quiet) {
            for (PropertyChangeListener l : getListeners()) {
                PropertyChangeEvent evt = new PropertyChangeEvent(this, propName, null, null);
                l.propertyChange(evt);
            }
        }
    }

    protected void firePropertyChange(String propName, @Nullable Object oldValue, @Nullable Object newValue) {
        if (!quiet) {
            if ((oldValue != null && !oldValue.equals(newValue)) || (oldValue == null && newValue != null)) {

                for (PropertyChangeListener l : getListeners()) {
                    PropertyChangeEvent evt = new PropertyChangeEvent(this, propName, oldValue, newValue);
                    l.propertyChange(evt);
                }
            }
        }
    }

    public boolean isQuiet() {
        return quiet;
    }

    public void setQuiet(boolean quiet) {
        this.quiet = quiet;
    }

}

