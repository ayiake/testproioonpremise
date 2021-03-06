package step.plugins.views.functions;

import org.junit.Assert;
import org.junit.Test;

import step.artefacts.reports.CallFunctionReportNode;
import step.core.artefacts.reports.ReportNode;

public class ErrorRateViewTest {

	@Test
	public void test() {
		ErrorRateView view = new ErrorRateView();
		view.setResolutions(new int[]{10,100});
		AbstractTimeBasedModel<ErrorRateEntry> model = view.init();
		for(int j=0;j<10;j++) {
			for(int i=0;i<99;i++) {
				ReportNode node = new CallFunctionReportNode();
				node.setExecutionTime(j*100+i);
				node.setError("Error "+i%2, 0, true);
				view.afterReportNodeExecution(model, node);
			}
		}
		
		Assert.assertEquals(10,model.getIntervals().size());
		Assert.assertEquals(99,model.getIntervals().get(0l).getCount());
		Assert.assertEquals(99,model.getIntervals().get(900l).getCount());
		Assert.assertEquals(50,(int)model.getIntervals().get(900l).countByErrorMsg.get("Error 0"));
		Assert.assertEquals(49,(int)model.getIntervals().get(900l).countByErrorMsg.get("Error 1"));
	}
}
