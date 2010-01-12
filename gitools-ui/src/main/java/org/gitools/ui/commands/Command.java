package org.gitools.ui.commands;

import edu.upf.bg.progressmonitor.IProgressMonitor;

public interface Command {

	public class CommandException extends Exception {
		private static final long serialVersionUID = 2147640402258540409L;
		
		public CommandException(Exception e) {
			super(e);
		}
		public CommandException(String msg) {
			super(msg);
		}
		public CommandException(String msg, Throwable cause) {
			super(msg, cause);
		}
	}
	
	void execute(IProgressMonitor monitor) throws CommandException;
}