package com.parishram.json.response;

import java.io.Serializable;

public class PhotoJsonHandler implements Serializable {
	private static final long serialVersionUID = -6990896637781466756L;
	private String likedOn;
	private int picOwnerId;
	private String likedBy;
	private int idOfUserWhoLiked;

	public int getPicOwnerId() {
		return picOwnerId;
	}

	public void setPicOwnerId(int picOwnerId) {
		this.picOwnerId = picOwnerId;
	}

	public int getIdOfUserWhoLiked() {
		return idOfUserWhoLiked;
	}

	public void setIdOfUserWhoLiked(int idOfUserWhoLiked) {
		this.idOfUserWhoLiked = idOfUserWhoLiked;
	}

	public String getLikedOn() {
		return likedOn;
	}

	public void setLikedOn(String likedOn) {
		this.likedOn = likedOn;
	}

	public String getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(String likedBy) {
		this.likedBy = likedBy;
	}

}
