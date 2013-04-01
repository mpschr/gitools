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
package org.gitools.analysis.groupcomparison;

import org.gitools.datafilters.BinaryCutoff;
import org.gitools.heatmap.header.HeatmapHeader;
import org.gitools.matrix.model.AnnotationMatrix;
import org.gitools.matrix.model.IMatrix;
import org.gitools.matrix.model.IMatrixView;
import org.gitools.matrix.model.MatrixFactory;
import org.gitools.model.Analysis;
import org.gitools.model.ToolConfig;
import org.gitools.persistence.formats.analysis.adapter.PersistenceReferenceXmlAdapter;
import org.gitools.stats.mtc.MTC;
import org.gitools.stats.mtc.MTCFactory;
import org.gitools.stats.test.Test;
import org.gitools.stats.test.factory.TestFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class GroupComparisonAnalysis extends Analysis implements Serializable
{


    protected String sizeAttrName;
    //protected int sizeAttrIndex;

    protected String pvalueAttrName;
    //protected int pvalueAttrIndex;

    protected boolean transposeData;

    protected int attributeIndex;
    //which attribute of the matrix should be taken as value

    public static String COLUMN_GROUPING_BY_VALUE = "Group by value";
    public static String COLUMN_GROUPING_BY_LABEL = "Group by label";

    protected String columnGrouping = null;

    protected String dataFile = "";

    @XmlTransient
    protected AnnotationMatrix rowAnnotations;

    @XmlTransient
    protected List<HeatmapHeader> rowHeaders;

    @XmlTransient
    protected List<HeatmapHeader> columnHeaders;

    @XmlTransient
    protected AnnotationMatrix columnAnnotations;

    @XmlTransient
    protected ColumnGroup group1;
    @XmlTransient
    protected ColumnGroup group2;

    /**
     * Test name
     */
    protected ToolConfig testConfig;
    protected String mtc;


    /**
     * Data
     */
    @XmlJavaTypeAdapter(PersistenceReferenceXmlAdapter.class)
    protected IMatrix data;

    /**
     * Results
     */
    @XmlJavaTypeAdapter(PersistenceReferenceXmlAdapter.class)
    protected IMatrix results;

    public MTC getMtc()
    {
        MTC mtcObj = MTCFactory.createFromName(mtc);
        String name = mtcObj.toString();
        return mtcObj;
    }

    public void setMtc(String mtc)
    {
        this.mtc = mtc;
    }

    public void setToolConfig(ToolConfig testConfig)
    {
        this.testConfig = testConfig;
    }

    public Test getTest()
    {
        TestFactory tf = TestFactory.createFactory(testConfig);
        return tf.create();
    }

    public GroupComparisonAnalysis()
    {
        this.transposeData = false;
        group1 = new ColumnGroup("Group 1");
        group2 = new ColumnGroup("Group 2");
    }

    public String getSizeAttrName()
    {
        return sizeAttrName;
    }


    public void setAttributeIndex(int attributeIndex)
    {
        this.attributeIndex = attributeIndex;
    }

    public int getAttributeIndex()
    {
        return attributeIndex;
    }

    public void setSizeAttrName(String sizeAttrName)
    {
        this.sizeAttrName = sizeAttrName;
    }

    public String getPvalueAttrName()
    {
        return pvalueAttrName;
    }

    public void setPvalueAttrName(String pvalueAttrName)
    {
        this.pvalueAttrName = pvalueAttrName;
    }

    public boolean isTransposeData()
    {
        return transposeData;
    }

    public void setTransposeData(boolean transposeData)
    {
        this.transposeData = transposeData;
    }

    public String getColumnGrouping()
    {
        return columnGrouping;
    }

    public static String[] getColumnGroupingMethods()
    {
        return new String[]{
                COLUMN_GROUPING_BY_LABEL,
                COLUMN_GROUPING_BY_VALUE
        };
    }

    public void setColumnGrouping(String columnGrouping)
    {
        this.columnGrouping = columnGrouping;
    }

    public ColumnGroup getGroups1()
    {
        return group1;
    }

    public ColumnGroup getGroups2()
    {
        return group2;
    }

    public void setGroup1(int[] group1)
    {
        this.group1.setColumns(group1);
    }

    public void setGroup2(int[] group2)
    {
        this.group2.setColumns(group2);
    }

    public void setGroup1(BinaryCutoff binaryCutoff, int cutoffAttrIndex)
    {
        this.group1.setBinaryCutoff(binaryCutoff);
        this.group1.setCutoffAttributeIndex(cutoffAttrIndex);
    }

    public void setGroup2(BinaryCutoff binaryCutoff, int cutoffAttrIndex)
    {
        this.group2.setBinaryCutoff(binaryCutoff);
        this.group2.setCutoffAttributeIndex(cutoffAttrIndex);
    }

    public void setGroup1(ColumnGroup group1)
    {
        this.group1 = group1;
    }

    public void setGroup2(ColumnGroup group2)
    {
        this.group2 = group2;
    }

    public IMatrix getData()
    {
        return data;
    }


    public void setData(IMatrix data)
    {
        if (data instanceof IMatrixView)
        {
            this.data = MatrixFactory.create((IMatrixView) data);
        }
        else
        {
            this.data = data;
        }
    }

    public IMatrix getResults()
    {
        return results;
    }

    public void setResults(IMatrix results)
    {
        this.results = results;
    }

    public void setRowAnnotations(AnnotationMatrix annotations)
    {
        this.rowAnnotations = annotations;
    }

    public AnnotationMatrix getRowAnnotations()
    {
        return this.rowAnnotations;
    }

    public AnnotationMatrix getColumnAnnotations()
    {
        return columnAnnotations;
    }

    public void setColumnAnnotations(AnnotationMatrix columnAnnotations)
    {
        this.columnAnnotations = columnAnnotations;
    }

    public List<HeatmapHeader> getRowHeaders()
    {
        return rowHeaders;
    }

    public void setRowHeaders(List<HeatmapHeader> rowHeaders)
    {
        this.rowHeaders = rowHeaders;
    }

    public List<HeatmapHeader> getColumnHeaders()
    {
        return columnHeaders;
    }

    public void setColumnHeaders(List<HeatmapHeader> columnHeaders)
    {
        this.columnHeaders = columnHeaders;
    }
}
