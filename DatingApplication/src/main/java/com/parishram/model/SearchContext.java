package com.parishram.model;

import java.util.List;

public class SearchContext {
	private int loginId;
	private String userName;
	private String profilePicture;
	private List<CommentsModel> commentsModel;
	private List<String> status;

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<CommentsModel> getCommentsModel() {
		return commentsModel;
	}

	public void setCommentsModel(List<CommentsModel> commentsModel) {
		this.commentsModel = commentsModel;
	}

	public List<String> getStatus() {
		return status;
	}

	public void setStatus(List<String> status) {
		this.status = status;
	}

}
