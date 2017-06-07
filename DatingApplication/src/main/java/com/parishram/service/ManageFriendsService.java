package com.parishram.service;

import java.util.List;
import java.util.Map;

import com.parishram.service.exception.ServiceException;

public interface ManageFriendsService {
	public List<Map<String, Object>> displayAllFriendsRequest(int to_user) throws ServiceException;
	public void sendFriendRequest(int from_user, int to_user, int accepted) throws ServiceException;
	public void cancelFriendRequest(int from_user, int to_user) throws ServiceException;
	public List<Map<String, Object>> checkIfFriendExist(int currentUserId, int potentialFriendLoginId) throws ServiceException;
	public int countTotalFriendRequest(int to_user) throws ServiceException;
	public List<Map<String, Object>> displayFriends(int to_user) throws ServiceException;
	public void acceptFriendRequest(int from_user, int to_user) throws ServiceException;
	public void unfriendUser(int to_user, int from_user) throws ServiceException;
	public void rejectFriendRequest(int from_user, int to_user) throws ServiceException;
}
