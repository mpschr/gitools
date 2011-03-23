/*
 *  Copyright 2011 chris.
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
package org.gitools.newick;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

class ReverseListIterator<T> implements Iterable<T>, Iterator<T> {

	private ListIterator<T> iterator;

	public ReverseListIterator(List<T> list) {
		this.iterator = list.listIterator(list.size());
	}

	@Override
	public Iterator<T> iterator() {
		return this;
	}
	
	@Override
	public boolean hasNext() {
		return iterator.hasPrevious();
	}

	@Override
	public T next() {
		return iterator.previous();
	}

	@Override
	public void remove() {
		iterator.remove();
	}
}