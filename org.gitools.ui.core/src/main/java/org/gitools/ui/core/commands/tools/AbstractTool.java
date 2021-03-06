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
package org.gitools.ui.core.commands.tools;

import org.gitools.ui.core.Application;
import org.gitools.ui.core.commands.Command;
import org.gitools.ui.platform.progress.JobRunnable;
import org.gitools.ui.platform.progress.JobThread;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.PrintWriter;
import java.util.concurrent.CountDownLatch;

public abstract class AbstractTool implements ITool {

    private int exitStatus = -1;
    private String exitMessage = "";


    @Override
    public boolean check(String[] args, PrintWriter out) {

        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
        } catch (CmdLineException e) {
            out.println("\n" + e.getMessage() + "\n");
            out.print("USAGE | " + getName() + "\n");
            parser.printUsage(out, null);
            return false;
        } catch (Exception e) {
            out.println("ERROR " + e.getMessage());
            return false;
        }

        return true;
    }

    @Override
    public boolean run(String[] args, PrintWriter out) {

        CmdLineParser parser = new CmdLineParser(this);
        try {
            parser.parseArgument(args);
            out.print("RUNNING " + this.getName() + "\n");
            execute();
            //exitStatus = 0;
        } catch (CmdLineException e) {
            out.print("USAGE | " + getName() + "\n");
            parser.printUsage(out, null);
            return false;
        } catch (Exception e) {
            out.println("ERROR | " + e.getMessage());
            return false;
        }
        if (exitStatus != 0) {
            return false;
        }

        return true;
    }

    void execute() throws InterruptedException {
        Command job = newJob();

        if (job != null) {

            Application mainFrame = Application.get();

            // Trick to force window to front, the setAlwaysOnTop works on a Mac,  toFront() does nothing.
            mainFrame.toFront();
            mainFrame.setAlwaysOnTop(true);
            mainFrame.setAlwaysOnTop(false);

            CountDownLatch latch = new CountDownLatch(1);
            JobThread.execute(mainFrame, (JobRunnable) job, latch);
            latch.await();
            setExitStatus(job.getExitStatus());
            setExitMessage(job.getExitMessage());


        } else {
            throw new RuntimeException("No Job");
        }
    }

    protected void setExitStatus(int exitStatus) {
        this.exitStatus = exitStatus;
    }

    public int getExitStatus() {
        return this.exitStatus;
    }


    protected abstract Command newJob();

    public String getExitMessage() {
        return exitMessage;
    }

    public void setExitMessage(String exitMessage) {
        this.exitMessage = exitMessage;
    }
}
