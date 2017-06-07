package com.parishram.daoimpl;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.parishram.dao.ProfileActivationDao;
import com.parishram.database.utils.Queries;
import com.parishram.service.exception.PersistentException;

@Repository
public class ProfileActivationDaoImpl implements ProfileActivationDao {
	private static Logger LOGGER = Logger.getLogger(ProfileDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void saveToken(String token, int loginId) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.SAVE_TOKEN.getValue(), token, loginId);
		} catch (Exception exception) {
			LOGGER.error("Unable to save token in the database " + exception.getMessage(), exception);
			throw new PersistentException("Unable to save token in the database.", exception);
		}
	}

	@Override
	public String retrieveToken(String emailId) throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(Queries.RETRIEVE_TOKEN.getValue(), new Object[] { emailId },
					String.class);
		} catch (Exception exception) {
			LOGGER.error("Unable to retrieve token from the database " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve token from the database.", exception);
		}
	}

	@Override
	public void enableUser(String emailId) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.UPDATE_ENABLED.getValue(), emailId);
		} catch (Exception exception) {
			LOGGER.error("Unable to enable the user. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to enable the user. Please try again later.", exception);
		}
	}

}
