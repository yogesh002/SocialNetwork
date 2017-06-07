package com.parishram.daoimpl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.parishram.dao.MailDao;
import com.parishram.database.utils.Queries;
import com.parishram.dto.MailDTO;
import com.parishram.service.exception.PersistentException;

@Repository
public class MailDaoImpl implements MailDao {
	private static Logger LOGGER = Logger.getLogger(MailDao.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveIncomingEmails(MailDTO mailDto) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.INSERT_MAIL.getValue(), mailDto.getFrom_user(), mailDto.getSubject(),
					mailDto.getMessage(), mailDto.getSentDate());
		} catch (Exception exception) {
			LOGGER.error("Unable to insert incoming mail data into mail table. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to insert incoming mail data into mail table.", exception);
		}

	}

	@Override
	public List<Map<String, Object>> retrieveEmailDetails() throws PersistentException {
		try {
			return jdbcTemplate.queryForList(Queries.RETRIEVE_ADMIN_EMAILS.getValue());
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve email details " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve email details.", exception);
		}
	}

	@Override
	public int countNewEmails() throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(Queries.COUNT_NEW_EMAIL.getValue(), Integer.class);
		} catch (DataAccessException exception) {
			LOGGER.error(
					"Unable to count total number of new emails that customer sent to admin. " + exception.getMessage(),
					exception);
			throw new PersistentException("Unable to count total number of new emails that customer sent to admin.",
					exception);
		}
	}

	@Override
	public int countTotalEmails() throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(Queries.COUNT_TOTAL_EMAIL.getValue(), Integer.class);
		} catch (DataAccessException exception) {
			LOGGER.error(
					"Unable to count total number of emails that customer sent to admin. " + exception.getMessage(),
					exception);
			throw new PersistentException("Unable to count total number of emails that customer sent to admin.",
					exception);
		}
	}

	@Override
	public void deleteSelectedEmail(List<Integer> email_id) throws PersistentException {
		try {
			Map<String, List<Integer>> paramMap = Collections.singletonMap("deleteEmailId", email_id);
			NamedParameterJdbcTemplate template = 
			    new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
			template.update(Queries.DELETE_EMAIL.getValue(), paramMap);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to delete the selected email that customer sent to admin." + exception.getMessage(),
					exception);
			throw new PersistentException("Unable to delete the selected email that customer sent to admin.",
					exception);
		}

	}

	@Override
	public void markEmailAsRead(int email_id) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.MARK_EMAIL_AS_READ.getValue(), email_id);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to mark the message as read." + exception.getMessage(), exception);
			throw new PersistentException("Unable to mark the selected message as read.", exception);
		}
	}

	@Override
	public void markEmailAsUnRead(int email_id) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.MARK_EMAIL_AS_UNREAD.getValue(), email_id);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to mark the message as unread." + exception.getMessage(), exception);
			throw new PersistentException("Unable to mark the selected message as unread.", exception);
		}

	}

	@Override
	public void replayEmail(int emailId, String from_user, String to_user, String message) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.REPLY_EMAIL_TO_CUSTOMER.getValue(), emailId, from_user, to_user, message);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to reply to the email" + exception.getMessage(), exception);
			throw new PersistentException("Unable to reply the email", exception);
		}
	}
}
