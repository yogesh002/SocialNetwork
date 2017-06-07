package com.parishram.model;

import java.io.Serializable;

public class CommentsModel implements Serializable {

	private static final long serialVersionUID = 1L;
	private int from_user;
	private int to_user;
	private String userName;
	private int status_id;
	private String comment;
	private int image_id;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public int getFrom_user() {
		return from_user;
	}

	public void setFrom_user(int from_user) {
		this.from_user = from_user;
	}

	public int getTo_user() {
		return to_user;
	}

	public void setTo_user(int to_user) {
		this.to_user = to_user;
	}

	public int getStatus_id() {
		return status_id;
	}

	public void setStatus_id(int status_id) {
		this.status_id = status_id;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getImage_id() {
		return image_id;
	}

	public void setImage_id(int image_id) {
		this.image_id = image_id;
	}

}
