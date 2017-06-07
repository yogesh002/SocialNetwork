package com.parishram.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PhotoAlbumModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6874447998436404137L;
	private int albumId;
	private String albumName;
	private int rating;
	private int loginId;
	List<Map<String, Object>> images = new ArrayList<Map<String, Object>>();

	public List<Map<String, Object>> getImages() {
		return images;
	}

	public void setImages(List<Map<String, Object>> images) {
		this.images = images;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public int getAlbumId() {
		return albumId;
	}

	public void setAlbumId(int albumId) {
		this.albumId = albumId;
	}

	public String getAlbumName() {
		return albumName;
	}

	public void setAlbumName(String albumName) {
		this.albumName = albumName;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

}
