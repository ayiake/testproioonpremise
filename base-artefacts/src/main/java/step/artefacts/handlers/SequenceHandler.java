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

import step.artefacts.Sequence;
import step.core.artefacts.handlers.ArtefactHandler;
import step.core.artefacts.reports.ReportNode;

public class SequenceHandler extends ArtefactHandler<Sequence, ReportNode> {
	
	@Override
	public void createReportSkeleton_(ReportNode node, Sequence testArtefact) {
		SequentialArtefactScheduler scheduler = new SequentialArtefactScheduler(context);
		scheduler.createReportSkeleton_(node, testArtefact);
	}
	
	@Override
	public void execute_(ReportNode node, Sequence testArtefact) {
		Long pacing = testArtefact.getPacing().get();
		long startTime = pacing!=null?System.currentTimeMillis():0;
		
		SequentialArtefactScheduler scheduler = new SequentialArtefactScheduler(context);
		scheduler.execute_(node, testArtefact, testArtefact.getContinueOnError().get());

		if(pacing!=null) {
			long endTime = System.currentTimeMillis();
			long duration = endTime-startTime;
			long pacingWait = pacing-duration;
			if(pacingWait>0) {
				try {
					Thread.sleep(pacingWait);
				} catch (InterruptedException e) {}
			} else {
				// TODO warning if the pacing exceeded
			}
		}
	}

	@Override
	public ReportNode createReportNode_(ReportNode parentNode, Sequence testArtefact) {
		return new ReportNode();
	}

}
