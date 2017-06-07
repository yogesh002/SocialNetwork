package com.parishram.database.utils;

public enum ManageFriendsQueries {
	SEND_FRIEND_REQUEST("INSERT INTO friendrequest(from_user, to_user, accept) VALUES (?,?,?)"),
	DISPLAY_FRIEND_REQUST("SELECT * FROM friendrequest WHERE to_user = ?"),
	CHECK_IF_FRIEND_EXIST("SELECT from_user, to_user, accept FROM friendrequest WHERE from_user = ? AND to_user = ? OR to_user = ? AND from_user = ?"),
	COUNT_TOTAL_FRIEND_REQUEST("SELECT COUNT(*) FROM friendrequest WHERE accept = 0 AND to_user = ?"),
	ACCEPT_FRIEND_REQUEST("UPDATE friendrequest SET accept = 1 WHERE from_user = ? AND to_user = ?"),
	REJECT_FRIEND_REQUEST("DELETE FROM friendrequest WHERE from_user = ? AND to_user = ?"),
	CANCEL_FRIEND_REQUEST("DELETE FROM friendrequest WHERE from_user = ? AND to_user = ? AND accept = 0"),
	UNFRIEND_USER("DELETE FROM friendrequest WHERE from_user = ? AND to_user = ? OR from_user = ? AND to_user = ? AND accept = 1"),
	DISPLAY_FRIENDS_LIST("SELECT * from friendrequest where to_user = ? AND accept = 1 OR from_user = ? and accept = 1");

	private String query;

	ManageFriendsQueries(String query) {
		this.query = query;
	}

	public String getValue() {
		return query;
	}

}
