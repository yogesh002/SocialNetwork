package com.parishram.serviceimpl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parishram.dao.MailDao;
import com.parishram.dto.MailDTO;
import com.parishram.service.MailService;
import com.parishram.service.exception.PersistentException;
import com.parishram.service.exception.ServiceException;

@Component
public class MailServiceImpl implements MailService {
	private static Logger LOGGER = Logger.getLogger(MailServiceImpl.class);

	@Autowired
	private MailDao mailDao;

	@Override
	public void saveIncomingEmail(String from, String to, String subject, String message, Date sentDate)
			throws ServiceException {
		try {
			MailDTO mailDto = new MailDTO();
			mailDto.setFrom_user(from);
			mailDto.setTo_user(to.trim());
			mailDto.setSubject(subject);
			mailDto.setMessage(message);
			mailDto.setSentDate(sentDate);
			mailDao.saveIncomingEmails(mailDto);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to save the incoming emails into databse." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public List<Map<String, Object>> retrieveEmailsSentFromCustomer() throws ServiceException {
		try {
			return mailDao.retrieveEmailDetails();
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve the emails from customers" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public int countNewEmails() throws ServiceException {
		try {
			return mailDao.countNewEmails();
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to count new emails from customers" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public int countTotalEmails() throws ServiceException {
		try {
			return mailDao.countTotalEmails();
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to count total emails from customers" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void deleteSelectedEmail(List<Integer> email_id) throws ServiceException {
		try {
			mailDao.deleteSelectedEmail(email_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to delete particular email from customer" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void markEmailAsRead(int email_Id) throws ServiceException {
		try {
			mailDao.markEmailAsRead(email_Id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to mark the email as read." + persistentException.getMessage(), persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void markEmailAsUnRead(int email_id) throws ServiceException {
		try {
			mailDao.markEmailAsUnRead(email_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to mark the email as unread." + persistentException.getMessage(), persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void replayEmail(int email_Id, String from_user, String to_user, String message) throws ServiceException {
		try {
			mailDao.replayEmail(email_Id, from_user, to_user, message);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to replay the email to the customer" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}

	}

}
