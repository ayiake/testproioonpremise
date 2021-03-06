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
package step.artefacts.handlers;

import org.json.JSONObject;

import step.artefacts.CallPlan;
import step.core.artefacts.AbstractArtefact;
import step.core.artefacts.handlers.ArtefactHandler;
import step.core.artefacts.reports.ReportNode;

public class CallPlanHandler extends ArtefactHandler<CallPlan, ReportNode> {

	@Override
	protected void createReportSkeleton_(ReportNode parentNode,	CallPlan testArtefact) {
		beforeDelegation(parentNode, testArtefact);
		
		AbstractArtefact a = context.getGlobalContext().getArtefactAccessor().get(testArtefact.getArtefactId());
		delegateCreateReportSkeleton(a, parentNode);
	}

	private void beforeDelegation(ReportNode parentNode, CallPlan testArtefact) {
		context.getVariablesManager().putVariable(parentNode, "#placeholder", testArtefact);

		JSONObject compositeInput = new JSONObject((testArtefact.getInput().get()!=null)?testArtefact.getInput().get():"{}");
		context.getVariablesManager().putVariable(parentNode, "compositeInput", compositeInput);
	}

	@Override
	protected void execute_(ReportNode node, CallPlan testArtefact) {
		beforeDelegation(node, testArtefact);

		AbstractArtefact a = context.getGlobalContext().getArtefactAccessor().get(testArtefact.getArtefactId());
		ReportNode resultNode = delegateExecute(context, a, node);
		node.setStatus(resultNode.getStatus());
	}

	@Override
	public ReportNode createReportNode_(ReportNode parentNode, CallPlan testArtefact) {
		return new ReportNode();
	}

}
