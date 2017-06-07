package com.parishram.serviceimpl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parishram.dao.ManageFriendsDao;
import com.parishram.service.ManageFriendsService;
import com.parishram.service.exception.PersistentException;
import com.parishram.service.exception.ServiceException;

@Component
public class ManageFriendsServiceImpl implements ManageFriendsService {
	@Autowired
	private ManageFriendsDao manageFriendsDao;
	private static Logger LOGGER = Logger.getLogger(ManageFriendsServiceImpl.class);

	@Override
	public void sendFriendRequest(int from_user, int to_user, int accepted) throws ServiceException {
		try {
			manageFriendsDao.sendFriendRequest(from_user, to_user, accepted);
		} catch (PersistentException exception) {
			LOGGER.error("Exception is thrown while sending friend request" + exception.getMessage(), exception);
			throw new ServiceException("Unable to send friend request", exception);
		}
	}

	@Override
	public List<Map<String, Object>> displayAllFriendsRequest(int to_user) throws ServiceException {
		try {
			return manageFriendsDao.displayAllFriendsRequest(to_user);
		} catch (PersistentException exception) {
			LOGGER.error("Unable to display friends who sent you request." + exception.getMessage(), exception);
			throw new ServiceException("Unable to display friends who sent you friend request.", exception);
		}
	}

	@Override
	public List<Map<String, Object>> checkIfFriendExist(int currentUserId, int potentialFriendLoginId)
			throws ServiceException {
		try {
			return manageFriendsDao.checkIfFriendExist(currentUserId, potentialFriendLoginId);
		} catch (PersistentException exception) {
			LOGGER.error("Unable to display friends who sent you request." + exception.getMessage(), exception);
			throw new ServiceException("Unable to display friends who sent you friend request.", exception);
		}
	}

	@Override
	public List<Map<String, Object>> displayFriends(int to_user) throws ServiceException {
		try {
			return manageFriendsDao.displayFriends(to_user);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to display friends." + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to display friends" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void acceptFriendRequest(int from_user, int to_user) throws ServiceException {
		try {
			manageFriendsDao.acceptFriendRequest(from_user, to_user);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to accept friend request" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to accept friend request" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public void rejectFriendRequest(int from_user, int to_user) throws ServiceException {
		try {
			manageFriendsDao.rejectFriendRequest(from_user, to_user);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to reject friend request" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to reject friend request" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public void cancelFriendRequest(int from_user, int to_user) throws ServiceException {
		try {
			manageFriendsDao.cancelFriendRequestSent(from_user, to_user);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to cancel Friend Request." + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to cancel Friend Request." + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void unfriendUser(int to_user, int from_user) throws ServiceException {
		try {
			manageFriendsDao.unFriendUser(to_user, from_user);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to remove the selected user from friend list." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"Unable to remove the selected user from friend list" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public int countTotalFriendRequest(int to_user) throws ServiceException {
		try {
			return manageFriendsDao.countTotalFriendRequest(to_user);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to count total friends." + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to count total friends" + persistentException.getMessage(),
					persistentException);
		}
	}

}
