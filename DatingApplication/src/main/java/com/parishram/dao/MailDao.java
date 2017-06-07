package com.parishram.dao;

import java.util.List;
import java.util.Map;

import com.parishram.dto.MailDTO;
import com.parishram.service.exception.PersistentException;

public interface MailDao {
	public void saveIncomingEmails(MailDTO mailDto) throws PersistentException;

	public List<Map<String, Object>> retrieveEmailDetails() throws PersistentException;

	public int countNewEmails() throws PersistentException;

	public int countTotalEmails() throws PersistentException;

	public void markEmailAsRead(int email_id) throws PersistentException;

	public void markEmailAsUnRead(int email_id) throws PersistentException;
	
	public void replayEmail(int emailId, String from_user, String to_user, String message) throws PersistentException;

	public void deleteSelectedEmail(List<Integer> email_id) throws PersistentException;
}
