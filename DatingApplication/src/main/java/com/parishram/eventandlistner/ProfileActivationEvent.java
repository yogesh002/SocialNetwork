package com.parishram.eventandlistner;

import java.util.Locale;

import org.springframework.context.ApplicationEvent;

import com.parishram.model.UserSignUp;
import com.parishram.service.exception.EventListnerException;

public class ProfileActivationEvent extends ApplicationEvent {
	private static final long serialVersionUID = -9100185776607495252L;

	private final String appUrl;
	private final Locale locale;
	private final UserSignUp signUpUser;
	private final EventListnerException exception;

	public ProfileActivationEvent(UserSignUp signUpUser, EventListnerException exception, String appUrl,
			Locale locale) {
		super(signUpUser);
		this.appUrl = appUrl;
		this.locale = locale;
		this.signUpUser = signUpUser;
		this.exception = exception;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public Locale getLocale() {
		return locale;
	}

	public UserSignUp getSignUpUser() {
		return signUpUser;
	}

	public EventListnerException getException() {
		return exception;
	}

}
