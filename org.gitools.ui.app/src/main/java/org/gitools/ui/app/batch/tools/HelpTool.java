/*
 * #%L
 * gitools-ui-app
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
package org.gitools.ui.app.batch.tools;

import com.google.common.base.Strings;
import org.gitools.ui.app.Main;

import java.io.PrintWriter;
import java.util.Set;

public class HelpTool implements ITool {

    private String toolName;
    private static Set<String> availableTools;

    public HelpTool(String toolName, Set<String> availableTools) {
        super();
        this.toolName = toolName;
        this.availableTools = availableTools;
    }


    public HelpTool() {
        super();
    }

    public HelpTool(Set<String> availableTools) {
        this("", availableTools);
    }

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public boolean run(String[] args, PrintWriter out) {
        out.println(getVersion());

        if (!Strings.isNullOrEmpty(toolName)) {
            out.println("\nERROR: Unknown command: '" + toolName + "'\n");
        }
        printAvailableTools(out);


        return true;
    }

    public static String getVersion() {
        return "Gitools " + Main.class.getPackage().getImplementationVersion();
    }

    @Override
    public boolean check(String[] args, PrintWriter out) {
        return true;
    }


    private static void printAvailableTools(PrintWriter out) {
        out.println("Available commands:");
        for (String tool : availableTools) {
            out.println("  " + tool);
        }
        out.println("Obtain more info running './gitools <command> --help' or at online documentation\n");
    }

}