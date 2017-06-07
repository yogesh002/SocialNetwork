package com.parishram.model;

import java.io.Serializable;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("session")
public final class UserSignUp implements Serializable {
	private static final long serialVersionUID = 8441369549759677597L;
	@NotEmpty
	@NotNull
	private String username;
	@NotEmpty
	@NotNull
	@Size(min = 8, max = 12)
	private String password;
	@NotEmpty
	@NotNull
	@Email
	private String email;
	private long phone;
	private String signUpProfileImage;
	private boolean isProfilePic;

	public boolean isProfilePic() {
		return isProfilePic;
	}

	public void setProfilePic(boolean isProfilePic) {
		this.isProfilePic = isProfilePic;
	}

	public String getSignUpProfileImage() {
		return signUpProfileImage;
	}

	public void setSignUpProfileImage(String signUpProfileImage) {
		this.signUpProfileImage = signUpProfileImage;
	}

	private int loginId;

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

}
