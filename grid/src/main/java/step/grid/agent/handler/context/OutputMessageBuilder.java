package step.grid.agent.handler.context;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.JsonObjectBuilder;

import step.grid.io.Attachment;
import step.grid.io.AttachmentHelper;
import step.grid.io.OutputMessage;

public class OutputMessageBuilder {
	
	private JsonObjectBuilder payloadBuilder;
	
	private MeasurementsBuilder measureHelper;
	
	private String error;
	
	private List<Attachment> attachments;

	public OutputMessageBuilder() {
		super();
		
		payloadBuilder = Json.createObjectBuilder();

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
	
	public OutputMessageBuilder setError(String errorMessage, Throwable e) {
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

	public void stopMeasure(long end, Map<String, String> data) {
		measureHelper.stopMeasure(end, data);
	}

	public void addMeasure(String measureName, long durationMillis) {
		measureHelper.addMeasure(measureName, durationMillis);
	}

	public void stopMeasure() {
		measureHelper.stopMeasure();
	}

	public void stopMeasure(Map<String, String> data) {
		measureHelper.stopMeasure(data);
	}

	public OutputMessage build() {
		OutputMessage message = new OutputMessage();
		message.setPayload(payloadBuilder.build());
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