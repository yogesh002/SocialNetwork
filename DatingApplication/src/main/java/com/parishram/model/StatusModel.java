package com.parishram.model;

import java.io.Serializable;

public class StatusModel implements Serializable {
	private static final long serialVersionUID = -3595080945047210612L;
	private int from_user;
	private int to_user;
	private String userName;
	private int status;
	private String image;
	private String postedStatus;

	public String getPostedStatus() {
		return postedStatus;
	}

	public void setPostedStatus(String postedStatus) {
		this.postedStatus = postedStatus;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

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

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

}
