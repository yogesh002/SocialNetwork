package com.parishram.json.response;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class AdminReplyMessage implements Serializable {
	private static final long serialVersionUID = 1L;
	private int emailId;
	private String content;
	private String sender;
	private List<Map<String, Object>> emails;

	public List<Map<String, Object>> getEmails() {
		return emails;
	}

	public void setEmails(List<Map<String, Object>> emails) {
		this.emails = emails;
	}

	public int getEmailId() {
		return emailId;
	}

	public void setEmailId(int emailId) {
		this.emailId = emailId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

}
