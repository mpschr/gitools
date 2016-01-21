/*
 * #%L
 * org.gitools.matrix
 * %%
 * Copyright (C) 2013 - 2016 Universitat Pompeu Fabra - Biomedical Genomics group
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
package org.gitools.matrix.transform.parameters;


public class LogParameter extends AbstractFunctionParameter<LogParameter.LogEnum> {

    @Override
    public boolean validate(LogParameter.LogEnum parameter) {
        return super.validate(parameter);
    }

    public enum LogEnum {
        baseN("Log Naturalis"), base10("Log10"),  base2("Log2"), custom("Custom");

        private String name;

        LogEnum(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }

}


