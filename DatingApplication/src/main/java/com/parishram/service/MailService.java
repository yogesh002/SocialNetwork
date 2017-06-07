package com.parishram.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.integration.mail.ImapMailReceiver;

import com.parishram.service.exception.ServiceException;

public interface MailService {
	public void saveIncomingEmail(String from, String to, String subject, String message, Date sentDate) throws ServiceException;
	public List<Map<String, Object>> retrieveEmailsSentFromCustomer() throws ServiceException;
	public int countNewEmails() throws ServiceException;
	public int countTotalEmails() throws ServiceException;
	public void markEmailAsRead(int email_Id) throws ServiceException;
	public void markEmailAsUnRead(int email_id) throws ServiceException;
	public void replayEmail(int email_Id, String from_user, String to_user, String message) throws ServiceException;
	public void deleteSelectedEmail(List<Integer> email_id) throws ServiceException;

	class MailServiceHelpers {
		private List<Map<String, Object>> emailDetails = new ArrayList<Map<String, Object>>();
		private boolean isMailDataMatches(String required, String actual) {
			if (actual.toLowerCase().contains(required.toLowerCase())) {
				return true;
			}
			return false;
		}

		private List<Map<String, Object>> buildEmailDetails(MimeMessage[] mimeMessages) throws IOException, MessagingException {
			Map<String, Object> emailMsg = null;
			for (MimeMessage mimeMessage : mimeMessages) {
				emailMsg = new HashMap<String, Object>();
				Date sentDate = mimeMessage.getSentDate();
				emailMsg.put("sentDate", sentDate);
				String result = ((String) mimeMessage.getContent()).replaceAll("\\<.*?>", "");
				String[] contents = result.split("#");
				String from="", to="", subject="", message = "";
				for (String content : contents) {
					String[] messageParts = content.split(":");
					from = isMailDataMatches("SENDER", messageParts[0]) ? messageParts[1] : from;
					to = isMailDataMatches("RECEIVER", messageParts[0]) ? messageParts[1] : to;
					subject = mimeMessage.getSubject();
					message = isMailDataMatches("MESSAGE", messageParts[0]) ? messageParts[1] : message;
					emailMsg.put("from", from);
					emailMsg.put("to", to);
					emailMsg.put("subject", subject);
					emailMsg.put("message", message);
				}
				emailDetails.add(emailMsg);
			}
			return emailDetails;
		}

		public List<Map<String, Object>> getIncomingEmailDetails() throws MessagingException, IOException {
			ImapMailReceiver receiver = buildImapMailReceiver();
			MimeMessage[] messages = (MimeMessage[]) receiver.receive();
			List<Map<String, Object>> emailDetails = buildEmailDetails(messages);
			return emailDetails;
			
		}

		private ImapMailReceiver buildImapMailReceiver() {
			ImapMailReceiver receiver = new ImapMailReceiver("imaps://yogeshghimire002:HelloJava@imap.gmail.com/INBOX");
			Properties mailProps = new Properties();
			mailProps.put("mail.debug", "true");
			mailProps.put("mail.imap.connectionpool.debug", "true");
			receiver.setJavaMailProperties(mailProps);
			receiver.setMaxFetchSize(1);
			receiver.setShouldDeleteMessages(false);
			receiver.setShouldMarkMessagesAsRead(true);
			receiver.setCancelIdleInterval(8);
			receiver.setUserFlag("spring-integration-mail-adapter");
			receiver.afterPropertiesSet();
			return receiver;
		}

	}
}
