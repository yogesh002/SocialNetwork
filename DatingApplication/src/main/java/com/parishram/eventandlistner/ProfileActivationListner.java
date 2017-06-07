package com.parishram.eventandlistner;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.parishram.model.UserSignUp;
import com.parishram.service.ProfileActivationService;
import com.parishram.service.exception.EventListnerException;
import com.parishram.service.exception.ServiceException;

@Component
public class ProfileActivationListner implements ApplicationListener<ProfileActivationEvent> {
	private static Logger LOGGER = Logger.getLogger(ProfileActivationListner.class);

	@Autowired
	private ProfileActivationService profileActivationService;

	@Override
	public void onApplicationEvent(ProfileActivationEvent profileActivationEvent) {
		try {
			UserSignUp userSignUp = profileActivationEvent.getSignUpUser();
			String token = profileActivationService.generateRandomToken();
			profileActivationService.saveToken(token, userSignUp.getLoginId());
			String appUrl = profileActivationEvent.getAppUrl();
			StringBuilder builder = new StringBuilder();
			builder.append(appUrl);
			builder.append("/emailconfirmation?token=");
			builder.append(token);
			LOGGER.debug("Confirmation message along with the confirmation email is: " + builder.toString());
			profileActivationService.sendEmail(userSignUp, builder.toString());
		} catch (ServiceException serviceException) {
			EventListnerException exception = profileActivationEvent.getException();
			exception.setErrorMessage(serviceException.getMessage());
			exception.setErrorObject(serviceException);
		}
	}

}
