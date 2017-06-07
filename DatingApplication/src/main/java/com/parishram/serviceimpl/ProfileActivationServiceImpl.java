package com.parishram.serviceimpl;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.parishram.common.Constant;
import com.parishram.dao.ProfileActivationDao;
import com.parishram.model.UserSignUp;
import com.parishram.service.ProfileActivationService;
import com.parishram.service.exception.PersistentException;
import com.parishram.service.exception.ServiceException;

@Component
public class ProfileActivationServiceImpl implements ProfileActivationService {

	private static Logger LOGGER = Logger.getLogger(ProfileActivationServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private MessageSource messageSource;

	@Autowired
	private ProfileActivationDao profileActivationDao;

	@Override
	public String generateRandomToken() {
		return UUID.randomUUID().toString();
	}

	@Override
	public void saveToken(String token, int loginId) throws ServiceException {
		try {
			profileActivationDao.saveToken(token, loginId);
		} catch (PersistentException exception) {
			LOGGER.error("Exception is thrown while sending email during profile sign up." + exception.getMessage(),
					exception);
			throw new ServiceException("Unable to send email while signing up the profile", exception);
		}
	}

	@Override
	public boolean isTokenValid(String emailId, String token) throws ServiceException {
		try {
			String tokenFromDatabase = profileActivationDao.retrieveToken(emailId);
			if (StringUtils.equalsIgnoreCase(token, tokenFromDatabase)) {
				return true;
			}
			return false;
		} catch (PersistentException exception) {
			LOGGER.error("Exception is thrown while retrieving token during profile sign up." + exception.getMessage(),
					exception);
			throw new ServiceException("Unable to validate the token", exception);
		}
	}

	@Override
	public void sendEmail(UserSignUp signUpUserModel, String confirmationUrl) throws ServiceException {
		try {
			SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
			simpleMailMessage.setTo(signUpUserModel.getEmail());
			simpleMailMessage.setSentDate(new Date());
			simpleMailMessage
					.setSubject(messageSource.getMessage("profile.signup.success.email.subject", null, Locale.US));
			StringBuilder builder = new StringBuilder();
			builder.append(messageSource.getMessage("profile.signup.message.body", null, Locale.US));
			builder.append(Constant.HOST_PORT);
			builder.append(confirmationUrl);
			LOGGER.debug(builder.toString());
			simpleMailMessage.setText(builder.toString());
			mailSender.send(simpleMailMessage);
		} catch (MailException exception) {
			LOGGER.error("Exception is thrown while sending email during profile sign up.");
			throw new ServiceException("Unable to send email while signing up the profile" + exception.getMessage(),
					exception);
		}
	}
	
	@Override
	public void enableUser(String emailId) throws ServiceException {
		try {
			profileActivationDao.enableUser(emailId);
		} catch (PersistentException exception) {
			LOGGER.error(
					"Exception is thrown while enabling the user to activate the account." + exception.getMessage(),
					exception);
			throw new ServiceException(
					"Unable to activate the account because we are unable to enable the user. Please try again later.",
					exception);
		}

	}

}
