/*
 * #%L
 * gitools-utils
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
package org.gitools.utils.progressmonitor;

import org.jetbrains.annotations.NotNull;

public class NullProgressMonitor implements IProgressMonitor {

    @Override
    public void begin(String title, int totalWork) {
    }

    @Override
    public void end() {
    }

    @Override
    public int getLevel() {
        return 0;
    }

    @Override
    public void cancel() {
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setLevel(int level) {
    }

    @NotNull
    @Override
    public IProgressMonitor subtask() {
        return new NullProgressMonitor();
    }

    @Override
    public void title(String title) {
    }

    @Override
    public void worked(int workInc) {
    }

    @Override
    public void debug(String msg) {
    }

    @Override
    public void info(String msg) {
    }

    @Override
    public void exception(Throwable cause) {
    }
}