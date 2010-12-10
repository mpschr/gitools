/*
 *  Copyright 2010 Universitat Pompeu Fabra.
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

package org.gitools.ui.workspace;

import java.io.File;

public class FileNode extends AbstractNode {

	private static final long serialVersionUID = 1168962294490262027L;

	public FileNode(File fileObject) {
		super(fileObject);
	}
	
	protected File getFileObject() {
		return (File) getUserObject();
	}
	
	@Override
	public void expand() {
		super.expand();
		
		File[] files = getFileObject().listFiles();
		for (File file : files)
			add(new FileNode(file));
	}
	
	@Override
	public boolean isLeaf() {
		return !getFileObject().isDirectory();
	}
	
	@Override
	public String getLabel() {
		return getFileObject().getName();
	}
}
