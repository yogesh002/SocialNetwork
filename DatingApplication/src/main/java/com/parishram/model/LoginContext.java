package com.parishram.model;

import java.util.List;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public class LoginContext {
	@NotEmpty
	@NotNull
	private String userName;
	@NotEmpty
	@NotNull
	private String password;
	private String profilePicture;
	private List<CommentsModel> commentsModel;
	private String searchUser;

	public String getSearchUser() {
		return searchUser;
	}

	public void setSearchUser(String searchUser) {
		this.searchUser = searchUser;
	}

	public List<CommentsModel> getCommentsModel() {
		return commentsModel;
	}

	public void setCommentsModel(List<CommentsModel> commentsModel) {
		this.commentsModel = commentsModel;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
