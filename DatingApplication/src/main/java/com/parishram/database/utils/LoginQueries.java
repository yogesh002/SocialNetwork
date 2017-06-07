package com.parishram.database.utils;

public enum LoginQueries {
	//Status
	POST_STATUS_IN_MY_PROFILE("INSERT INTO status(status_id, from_user, to_user, status, time_created) VALUES (DEFAULT, ?, ?, ?, DEFAULT)"),
	VIEW_ALL_STATUS("SELECT i.image, s.status, s.from_user, s.to_user FROM status s INNER JOIN image i ON s.from_user = i.login_id WHERE s.to_user= ? and i.is_profile_pic= 't' ORDER BY time_created DESC"),
	RETRIEVE_PROFILE_IMAGE("SELECT image FROM image WHERE login_id =? AND is_profile_pic='t'"),
	RETRIEVE_PROFILE_PIC_IMAGE_ID("SELECT image_id FROM image WHERE is_profile_pic = 't' AND login_id = ?"),
	POST_COMMENT_IN_PIC("INSERT INTO COMMENTS (comment_id, from_user, to_user, comments, image_id, status_id) VALUES(DEFAULT, ?, ?, ?, ?, ?)"),
	RETRIEVE_ALL_IMAGE_COMMENTS("SELECT comments, from_user, to_user FROM comments WHERE image_id=?"),
	LIKE_PHOTO("INSERT INTO likes (like_id, from_user, to_user, image_id, status_id, comment_id) VALUES (DEFAULT, ?, ?, ?, DEFAULT, DEFAULT)"),
	COUNT_LIKES_ON_PHOTOS("SELECT COUNT(*) FROM likes WHERE image_id = ?"),
	COUNT_LIKES_ON_PHOTO_BY_USER("SELECT COUNT(*) FROM likes WHERE from_user = ? AND to_user = ? AND image_id = ?"),
	SHOW_FRIENDS_WHO_LIKED("SELECT from_user, to_user FROM likes WHERE image_id = ?"),
	RETRIEVE_IMAGE_ID_USING_LOGIN_ID("SELECT image_id FROM image WHERE login_id = ? AND is_profile_pic='t'"),
	DELETE_PROFILE_IMAGE_USING_IMAGE_ID("DELETE FROM image CASCADE WHERE image_id = ? AND is_profile_pic='t'"),
	RETRIEVE_USERNAME_USING_LOGIN_ID("SELECT username FROM login WHERE login_id = ?"),
	SHOW_WALL_PAGE("SELECT s.status, i.image, s.from_user, s.to_user from status s "+
					"INNER JOIN image AS i ON s.to_user = i.login_id "+
					"AND s.to_user = s.from_user AND i.is_profile_pic = 't' "+ 
					"UNION "+
					"SELECT s.status, i.image, s.from_user, s.to_user from status s "+
					"INNER JOIN image AS i ON s.to_user = i.login_id "+
					"AND s.to_user=(SELECT f.from_user FROM friendrequest f WHERE "+ 
					"f.to_user = ? "+
					"AND f.accept = 1) AND i.is_profile_pic = 't' WHERE s.from_user = s.to_user"),  
	//ADMIN
	SHOW_ALL_USERS("SELECT l.login_id, l.username, l.email, r.role FROM login l INNER JOIN role r on l.role_id = r.role_id"),
	DELETE_USER("DELETE FROM login cascade WHERE login_id = ?"),
	EDIT_USER_ROLE("UPDATE login SET role_id = ? WHERE login_id = ?"),
	GET_ROLE("SELECT r.role from role r INNER JOIN login l on r.role_id = l.role_id where l.login_id = ?");
	private String query;

	LoginQueries(String query) {
		this.query = query;
	}

	public String getValue() {
		return query;
	}
}
