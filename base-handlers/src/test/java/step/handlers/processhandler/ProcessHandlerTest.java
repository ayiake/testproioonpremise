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
package step.handlers.processhandler;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import junit.framework.Assert;
import step.grid.agent.handler.FunctionTester;
import step.grid.agent.handler.FunctionTester.Context;
import step.grid.io.OutputMessage;

public class ProcessHandlerTest {

	@Test 
	public void testt1() {
		Map<String, String> properties = new HashMap<>();
		Context context = FunctionTester.getContext(new ProcessHandler(), properties);
		
		String echoCmd;
		if(System.getProperty("os.name").startsWith("Windows")) {
			echoCmd = "{\"cmd\":\"cmd.exe /r echo test\"}";;
		} else {
			echoCmd = "{\"cmd\":\"echo test\"}";;
		}
		OutputMessage out = context.run("test1", echoCmd);
		
		Assert.assertEquals("test\n",out.getPayload().getString("stdout"));
	}

}
