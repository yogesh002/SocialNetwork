package com.parishram.service;

import com.parishram.model.UserSignUp;
import com.parishram.service.exception.ServiceException;

public interface ProfileActivationService {
	public String generateRandomToken() throws ServiceException;
	public void saveToken(String token, int loginId) throws ServiceException;
	public void sendEmail(UserSignUp userSignUp, String confirmationUrl) throws ServiceException;
	public boolean isTokenValid(String emailId, String token) throws ServiceException;
	public void enableUser(String emailId) throws ServiceException;
}
