package com.parishram.model;

public class ContactFormModel {
	private String messageBody;
	private String name;
	private String senderEmail;
	private String receiverEmail;

	public String getReceiverEmail() {
		return receiverEmail;
	}

	public void setReceiverEmail(String receiverEmail) {
		this.receiverEmail = receiverEmail;
	}

	public String getSenderEmail() {
		return senderEmail;
	}

	public void setSenderEmail(String senderEmail) {
		this.senderEmail = senderEmail;
	}

	public String getMessageBody() {
		return messageBody;
	}

	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
