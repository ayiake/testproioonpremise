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
package step.core.artefacts;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import step.core.artefacts.handlers.ArtefactHandler;
import step.core.artefacts.reports.ReportNode;

@Retention(RetentionPolicy.RUNTIME)
public @interface Artefact {

	String name() default ""; 

	Class<? extends ArtefactHandler<?,?>> handler();
	
	Class<? extends ReportNode> report() default ReportNode.class;
	
	boolean block() default true;
	
}
