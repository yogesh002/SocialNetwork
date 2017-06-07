package com.parishram.dao;

import java.util.List;
import java.util.Map;

import com.parishram.service.exception.PersistentException;

public interface ManageFriendsDao {
	public void sendFriendRequest(int from_user, int to_user, int isAccepted) throws PersistentException;
	public List<Map<String, Object>> displayAllFriendsRequest(int to_user) throws PersistentException;
	public void acceptFriendRequest(int from_user) throws PersistentException;
	public void cancelFriendRequestSent(int from_user, int to_user) throws PersistentException;
	public List<Map<String, Object>> checkIfFriendExist(int currentUserId, int potentialFriendLoginId) throws PersistentException;
	public int countTotalFriendRequest(int to_user) throws PersistentException;
	public void acceptFriendRequest(int from_user, int to_user) throws PersistentException;
	public void rejectFriendRequest(int from_user, int to_user) throws PersistentException;
	public void unFriendUser(int to_user, int from_user) throws PersistentException;
	public List<Map<String, Object>>  displayFriends(int to_user) throws PersistentException;
}
