package com.parishram.daoimpl;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.parishram.dao.ManageFriendsDao;
import com.parishram.database.utils.ManageFriendsQueries;
import com.parishram.service.exception.PersistentException;

@Repository
public class ManageFriendsDaoImpl implements ManageFriendsDao {
	private static Logger LOGGER = Logger.getLogger(ManageFriendsDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public void sendFriendRequest(int from_user, int to_user, int isAccepted) throws PersistentException {
		try {
			jdbcTemplate.update(ManageFriendsQueries.SEND_FRIEND_REQUEST.getValue(), from_user, to_user, isAccepted);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to send friend request" + exception.getMessage(), exception);
			throw new PersistentException("Unable send friend request", exception);
		}
	}

	@Override
	public void acceptFriendRequest(int from_user) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Map<String, Object>> displayAllFriendsRequest(int to_user) throws PersistentException {
		try {
			return jdbcTemplate.queryForList(ManageFriendsQueries.DISPLAY_FRIEND_REQUST.getValue(), to_user);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to send friend request" + exception.getMessage(), exception);
			throw new PersistentException("Unable send friend request", exception);
		}
	}

	@Override
	public List<Map<String, Object>> checkIfFriendExist(int currentUserId, int potentialFriendLoginId)
			throws PersistentException {
		try {
			return jdbcTemplate.queryForList(ManageFriendsQueries.CHECK_IF_FRIEND_EXIST.getValue(), currentUserId,
					potentialFriendLoginId, currentUserId, potentialFriendLoginId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to check friend request status." + exception.getMessage(), exception);
			throw new PersistentException("Unable to check friend request status.", exception);
		}
	}

	@Override
	public void acceptFriendRequest(int from_user, int to_user) throws PersistentException {
		try {
			jdbcTemplate.update(ManageFriendsQueries.ACCEPT_FRIEND_REQUEST.getValue(), from_user, to_user);
		} catch (DataAccessException exception) {
			LOGGER.error("There was a problem while accepting the friend request" + exception.getMessage(), exception);
			throw new PersistentException("There was a problem while accepting the friend request", exception);
		}
	}

	@Override
	public void rejectFriendRequest(int from_user, int to_user) throws PersistentException {
		try {
			jdbcTemplate.update(ManageFriendsQueries.REJECT_FRIEND_REQUEST.getValue(), from_user, to_user);
		} catch (DataAccessException exception) {
			LOGGER.error("There was a problem while rejecting the friend request" + exception.getMessage(), exception);
			throw new PersistentException("There was a problem while rejecting the friend request", exception);
		}
	}

	@Override
	public List<Map<String, Object>> displayFriends(int to_user) throws PersistentException {
		try {
			return jdbcTemplate.queryForList(ManageFriendsQueries.DISPLAY_FRIENDS_LIST.getValue(), to_user, to_user);
		} catch (DataAccessException exception) {
			LOGGER.error("There was a problem while displaying the friend list" + exception.getMessage(), exception);
			throw new PersistentException("Unable to display friend list", exception);
		}
	}

	@Override
	public void cancelFriendRequestSent(int from_user, int to_user) throws PersistentException {
		try {
			jdbcTemplate.update(ManageFriendsQueries.CANCEL_FRIEND_REQUEST.getValue(), from_user, to_user);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to cancel the friend request" + exception.getMessage(), exception);
			throw new PersistentException("Unable to cancel the friend request", exception);
		}
	}

	@Override
	public void unFriendUser(int to_user, int from_user) throws PersistentException {
		try {
			jdbcTemplate.update(ManageFriendsQueries.UNFRIEND_USER.getValue(), from_user, to_user, to_user, from_user );
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to unfriend the selected user" + exception.getMessage(), exception);
			throw new PersistentException("Unable to unfriend the selected user", exception);
		}

	}

	@Override
	public int countTotalFriendRequest(int to_user) throws PersistentException {
		try {
			int countTotalFriends = jdbcTemplate.queryForObject(
					ManageFriendsQueries.COUNT_TOTAL_FRIEND_REQUEST.getValue(), new Object[] { to_user },
					Integer.class);
			return countTotalFriends;

		} catch (DataAccessException exception) {
			LOGGER.error("Unable to count total friends " + exception.getMessage(), exception);
			throw new PersistentException("Unable to count total friends", exception);
		}
	}

}
