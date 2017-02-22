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

import step.artefacts.handlers.CallCompositeHandler;
import step.commons.dynamicbeans.DynamicAttribute;
import step.core.artefacts.AbstractArtefact;
import step.core.artefacts.Artefact;

@Artefact(handler = CallCompositeHandler.class)
public class CallCompositeControl extends AbstractArtefact {
	
	@DynamicAttribute
	private String artefactId;
	
	private String cachedArtefactName;
	
	@DynamicAttribute
	private String inputJson;

	public String getArtefactId() {
		return artefactId;
	}

	public void setArtefactId(String artefactId) {
		this.artefactId = artefactId;
	}

	public String getCachedArtefactName() {
		return cachedArtefactName;
	}

	public void setCachedArtefactName(String cachedArtefactName) {
		this.cachedArtefactName = cachedArtefactName;
	}

	public String getInputJson() {
		return inputJson;
	}

	public void setInputJson(String inputJson) {
		this.inputJson = inputJson;
	}
}