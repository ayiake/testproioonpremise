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

import java.util.Map;

import step.artefacts.handlers.FunctionGroupHandler;
import step.core.artefacts.AbstractArtefact;
import step.core.artefacts.Artefact;

@Artefact(handler = FunctionGroupHandler.class)
public class FunctionGroup extends AbstractArtefact {

	private Map<String, String> selectionCriteria;
	
	private Map<String, String> attributes;

	public Map<String, String> getSelectionCriteria() {
		return selectionCriteria;
	}

	public void setSelectionCriteria(Map<String, String> selectionCriteria) {
		this.selectionCriteria = selectionCriteria;
	}

	public Map<String, String> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
}