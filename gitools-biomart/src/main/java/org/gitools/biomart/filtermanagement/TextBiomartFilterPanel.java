/*
 *  Copyright 2010 xavier.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */
package org.gitools.biomart.filtermanagement;

import java.util.ArrayList;
import java.util.List;
import org.gitools.biomart.soap.model.Filter;
import org.gitools.biomart.restful.model.FilterCollection;


/**
 *
 * @author xavier
 */
public class TextBiomartFilterPanel extends BiomartFilterPanel implements IBiomartFilterPanel {

	private final Integer textAreacols = 25;
	private final Integer textArearows = 4;
	private final Integer fileFieldSize = 16;
	private String labelPanel;
	private String nameCompo;
	private String valueCompo;
	private Boolean isTextArea;

	TextBiomartFilterPanel(FilterCollection fc) {

		isTextArea = fc.getFilterDescriptions().get(0).getMultipleValues().equals("1");
		labelPanel = fc.getDisplayName();
		nameCompo = fc.getFilterDescriptions().get(0).getInternalName();

	}

	public Boolean isTextArea(){
		return isTextArea;
	}

	@Override
	public void setFilterValue(String val) {
		valueCompo = val;
	}

	@Override
	public String getFilterName() {
		return nameCompo;
	}

	@Override
	//Coma separated list
	public String getFilterValue() {

		return valueCompo.replace(" ", ",");

	}

	@Override
	public List<Filter> getFilters() {
		List<Filter> lf = new ArrayList<Filter>();
		Filter f = new Filter();
		f.setName(nameCompo);
		f.setValue(valueCompo);
		lf.add(f);
		return lf;
	}
}