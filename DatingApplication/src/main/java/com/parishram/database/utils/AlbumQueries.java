package com.parishram.database.utils;

public enum AlbumQueries {
	DELETE_ALBUM("DELETE FROM album WHERE album_id = ? AND login_id = ?"),
	EDIT_ALBUM("UPDATE album SET album_name = ? WHERE album_id = ?"),
	RATE_ALBUM("UPDATE album SET rating = ? WHERE album_id = ?"),
	CREATE_ALBUM("INSERT INTO album(album_name, login_id, rating, album_id) VALUES (?, ?, DEFAULT, DEFAULT)"),
	RETRIEVE_IMAGES("SELECT i.image_id, i.image, a.album_name, a.rating FROM album AS a INNER JOIN image AS i ON a.album_id = i.album_id WHERE a.album_id = ? and i.login_id = ? AND i.is_profile_pic = 'f'"),
	GET_ALBUM_RATING("SELECT rating FROM album WHERE album_id = ? AND login_id = ?"),
	GET_ALBUM_ID("SELECT album_id FROM album WHERE album_name = ?"),
	GET_ALL_ALBUMS("SELECT * FROM album WHERE login_id = ?"),
	UPDATE_ALBUM_ID("UPDATE image SET album_id = ? where login_id = ? and album_id = -1"),
	UPLOAD_IMAGES("INSERT INTO image VALUES(?, ?, DEFAULT, ?)");

	private String query;

	AlbumQueries(String query) {
		this.query = query;
	}

	public String getValue() {
		return query;
	}
}
