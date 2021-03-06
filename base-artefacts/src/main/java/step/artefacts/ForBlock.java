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
package step.artefacts;

import step.artefacts.handlers.ForBlockHandler;
import step.core.artefacts.Artefact;
import step.core.dynamicbeans.DynamicValue;

@Artefact(name = "For", handler = ForBlockHandler.class)
public class ForBlock extends AbstractForBlock {

	public ForBlock() {
		super();
		this.setDataSourceType("sequence");
		this.setItem(new DynamicValue<String>("counter"));
		this.setThreads(new DynamicValue<Integer>(1));
	}
		
}
