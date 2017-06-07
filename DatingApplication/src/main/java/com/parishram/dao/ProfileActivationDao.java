package com.parishram.dao;

import com.parishram.service.exception.PersistentException;

public interface ProfileActivationDao {

	public void saveToken(String token, int loginId) throws PersistentException;

	public String retrieveToken(String emailId) throws PersistentException;
	
	public void enableUser(String emailId) throws PersistentException;
	
}
