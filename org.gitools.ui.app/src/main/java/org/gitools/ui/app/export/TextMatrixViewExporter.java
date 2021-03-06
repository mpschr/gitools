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
package org.gitools.ui.app.export;

import org.gitools.api.matrix.IMatrixDimension;
import org.gitools.api.matrix.IMatrixLayer;
import org.gitools.api.matrix.IMatrixLayers;
import org.gitools.api.matrix.ValueTranslator;
import org.gitools.api.matrix.view.IMatrixView;

import java.io.*;
import java.util.zip.GZIPOutputStream;

public class TextMatrixViewExporter {

    public static void exportMatrix(IMatrixView matrixView, IMatrixLayer layer, File file) throws IOException {
        PrintWriter pw = new PrintWriter(openWriter(file));

        IMatrixDimension rows = matrixView.getRows();
        IMatrixDimension columns = matrixView.getColumns();

        //header
        for (String column : columns)
            pw.print("\t" + column);
        pw.println();

        // body
        ValueTranslator translator = layer.getTranslator();

        for (String row : rows) {
            pw.print(row);
            for (String column : columns) {
                pw.print("\t" + translator.valueToString(matrixView.get(layer, row, column)));
            }
            pw.println();
        }

        pw.close();
    }

    public static void exportTable(IMatrixView matrixView, int[] propIndices, File file) throws IOException {

        PrintWriter pw = new PrintWriter(openWriter(file));

        IMatrixLayers layers = matrixView.getLayers();
        IMatrixDimension rows = matrixView.getRows();
        IMatrixDimension columns = matrixView.getColumns();

        // header
        pw.print("column\trow");
        for (int l : propIndices) {
            IMatrixLayer layer = layers.get(l);
            pw.print("\t" + layer.getId());
        }
        pw.println();

        // body
        for (String column : columns) {
            for (String row : rows) {
                pw.print(column);
                pw.print("\t" + row);
                for (int l : propIndices) {
                    IMatrixLayer layer = layers.get(l);
                    pw.print("\t" + layer.getTranslator().valueToString(matrixView.get(layer, row, column)));
                }
                pw.println();
            }
        }
        pw.close();
    }

    private static Writer openWriter(File path) throws IOException {
        if (path == null) {
            return null;
        }

        if (path.getName().endsWith(".gz")) {
            return new OutputStreamWriter(new GZIPOutputStream(new FileOutputStream(path, false)));
        } else {
            return new BufferedWriter(new FileWriter(path, false));
        }
    }
}
