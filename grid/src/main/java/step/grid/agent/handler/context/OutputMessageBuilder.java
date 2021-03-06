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
package step.grid.agent.handler.context;

import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.spi.JsonProvider;

import step.grid.io.Attachment;
import step.grid.io.AttachmentHelper;
import step.grid.io.Measure;
import step.grid.io.OutputMessage;

public class OutputMessageBuilder {
	
	private JsonObjectBuilder payloadBuilder;
	
	private String payloadJson;
	
	private MeasurementsBuilder measureHelper;
	
	private String error;
	
	private List<Attachment> attachments;
	
	private static JsonProvider jprov = JsonProvider.provider();
	
	private Measure lastMeasureHandle = null;

	public OutputMessageBuilder() {
		super();
		
		payloadBuilder = jprov.createObjectBuilder();

		measureHelper = new MeasurementsBuilder();
	}
	
	public JsonObjectBuilder getPayloadBuilder() {
		return payloadBuilder;
	}

	public void setPayloadBuilder(JsonObjectBuilder payloadBuilder) {
		this.payloadBuilder = payloadBuilder;
	}

	public OutputMessageBuilder add(String arg0, boolean arg1) {
		payloadBuilder.add(arg0, arg1);
		return this;
	}

	public OutputMessageBuilder add(String arg0, double arg1) {
		payloadBuilder.add(arg0, arg1);
		return this;
	}

	public OutputMessageBuilder add(String arg0, int arg1) {
		payloadBuilder.add(arg0, arg1);
		return this;
	}
	
	public OutputMessageBuilder add(String arg0, long arg1) {
		payloadBuilder.add(arg0, arg1);
		return this;
	}
	
	public OutputMessageBuilder add(String arg0, String arg1) {
		payloadBuilder.add(arg0, arg1);
		return this;
	}

	public OutputMessageBuilder setError(String technicalError) {
		error = technicalError;
		return this;
	}
	
	public OutputMessageBuilder appendError(String technicalError) {
		if(error!=null) {
			error += technicalError;			
		} else {
			error = technicalError;	
		}
		return this;
	}
	
	public String getPayloadJson() {
		return payloadJson;
	}

	public void setPayloadJson(String payloadJson) {
		this.payloadJson = payloadJson;
	}

	public OutputMessageBuilder setError(String errorMessage, Throwable e) {
		setError(errorMessage);
		addAttachment(generateAttachmentForException(e));
		return this;
	}

	public void addAttachments(List<Attachment> attachments) {
		createAttachmentListIfNeeded();
		attachments.addAll(attachments);
	}

	public void addAttachment(Attachment attachment) {
		createAttachmentListIfNeeded();
		attachments.add(attachment);
	}

	private void createAttachmentListIfNeeded() {
		if(attachments==null) {
			attachments = new ArrayList<>();
		}
	}
	
	public void startMeasure(String id) {
		measureHelper.startMeasure(id);
	}

	public void startMeasure(String id, long begin) {
		measureHelper.startMeasure(id, begin);
	}

	public void addMeasure(String measureName, long durationMillis) {
		measureHelper.addMeasure(measureName, durationMillis);
	}
	
	public void addMeasure(String measureName, long aDurationMillis, Map<String, Object> data) {
		measureHelper.addMeasure(measureName, aDurationMillis, data);
	}

	public void stopMeasure() {
		measureHelper.stopMeasure();
	}

	public void stopMeasure(Map<String, Object> data) {
		measureHelper.stopMeasure(data);
	}
	
	public void stopMeasureForAdditionalData() {
		this.lastMeasureHandle = measureHelper.stopMeasure();
	}
	
	public void setLastMeasureAdditionalData(Map<String, Object> data) {
		this.lastMeasureHandle.setData(data);
		this.lastMeasureHandle = null;
	}

	public OutputMessage build() {
		OutputMessage message = new OutputMessage();
		JsonObject payload;
		if(payloadJson==null) {
			payload = payloadBuilder.build();			
		} else {
			JsonReader reader = Json.createReader(new StringReader(payloadJson));
			try {
				payload = reader.readObject();				
			} finally {
				reader.close();
			}
		}
		message.setPayload(payload);
		message.setMeasures(measureHelper.getMeasures());
		message.setAttachments(attachments);
		message.setError(error);
		return message;
	}

	private Attachment generateAttachmentForException(Throwable e) {
		Attachment attachment = new Attachment();	
		attachment.setName("exception.log");
		StringWriter w = new StringWriter();
		e.printStackTrace(new PrintWriter(w));
		attachment.setHexContent(AttachmentHelper.getHex(w.toString().getBytes()));
		return attachment;
	}
}
