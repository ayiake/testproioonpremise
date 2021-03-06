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
package step.datapool.file;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import step.datapool.DataSet;


public class FileDataPoolImpl extends DataSet<DirectoryDataPool> {
	
	public FileDataPoolImpl(DirectoryDataPool configuration) {
		super(configuration);
	}
		
	Iterator<File> fileIterator;
	
	File currentFile;
	
	@Override
	public void init() {
		File folder = new File(configuration.getFolder().get());
		List<File> fileList = Arrays.asList(folder.listFiles());
		fileIterator = fileList.iterator();
	}
	
	@Override
	public void reset() {
		throw new RuntimeException("Reset method not implemented for this DataSet type");
	}

	@Override
	public Object next_() {
		if(fileIterator.hasNext()) {
			return fileIterator.next().getAbsolutePath();
		} else {
			return null;
		}
	}

	@Override
	public void close() {
	}
	
	@Override
	public void addRow(Object row) {
		throw new RuntimeException("Not implemented");
	}
}
