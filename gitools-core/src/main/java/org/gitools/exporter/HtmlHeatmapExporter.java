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
package org.gitools.exporter;

import org.gitools.heatmap.Heatmap;
import org.gitools.model.decorator.ElementDecoration;
import org.gitools.utils.formatter.GenericFormatter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class HtmlHeatmapExporter extends AbstractHtmlExporter
{

    public HtmlHeatmapExporter()
    {
        super();
    }

    public void exportHeatmap(Heatmap figure)
    {

        File templatePath = getTemplatePath();
        if (templatePath == null)
        {
            throw new RuntimeException("Unable to locate templates path !");
        }

        try
        {
            copy(new File(templatePath, "media"), basePath);
        } catch (IOException e1)
        {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        TemplateEngine eng = new TemplateEngine();
        eng.setFileLoaderPath(templatePath);
        eng.init();

        try
        {
            Map<String, Object> context = new HashMap<String, Object>();
            context.put("fmt", new GenericFormatter());
            context.put("figure", figure);
            context.put("matrix", figure.getMatrixView());
            context.put("cellDecoration", new ElementDecoration());
            eng.setContext(context);

            File file = new File(basePath, indexName);
            eng.loadTemplate("matrixfigure.vm");
            eng.render(file);

        } catch (Exception e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
