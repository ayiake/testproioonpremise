/*******************************************************************************
 * (C) Copyright 2016 Jerome Comte and Dorian Cransac
 *  
 * This file is part of STEP
 *  
 * STEP is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *  
 * STEP is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *  
 * You should have received a copy of the GNU Affero General Public License
 * along with STEP.  If not, see <http://www.gnu.org/licenses/>.
 *******************************************************************************/
package step.commons.buffering;

import java.util.Iterator;

public class FilterIterator<T> implements Iterator<T> {

	private Iterator<T> it;
	
	private ObjectFilter<T> filter;
	
	private T nextMatch = null;
	
	public FilterIterator(Iterator<T> it, ObjectFilter<T> filter) {
		super();
		this.it = it;
		this.filter = filter;
		
		findNextMatch();
	}

	private void findNextMatch() {
		T nextElement;
		
		for(;;) {
			if(it.hasNext()) {
				nextElement = it.next();
				if(filter.matches(nextElement)) {
					nextMatch = nextElement;
					break;
				} else {
					// next
				}
			} else {
				nextMatch = null;
				break;
			}
		}
	}
	
	@Override
	public boolean hasNext() {
		return nextMatch!=null;
	}
	@Override
	public T next() {
		T result = nextMatch;
		findNextMatch();
		return result;
	}
	@Override
	public void remove() {
		throw new RuntimeException("Remove method not supported!");
	}
}
