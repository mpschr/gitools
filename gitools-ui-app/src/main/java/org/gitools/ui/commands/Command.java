/*
 *  Copyright 2009 Universitat Pompeu Fabra.
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

package org.gitools.ui.commands;

import org.gitools.utils.progressmonitor.IProgressMonitor;

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
