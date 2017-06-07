package com.parishram.dao;

import java.util.List;
import java.util.Map;

import com.parishram.dto.AddressDTO;
import com.parishram.dto.EducationDTO;
import com.parishram.dto.PersonDTO;
import com.parishram.service.exception.PersistentException;

public interface ProfileDao {
	//PARISHRAM DATABASE START
	public int checkExistingUserName (String userName) throws PersistentException;
	public int checkExistingEmailAddress (String userName) throws PersistentException;
	public void signUpNewUser(String userName, String email, String password, long phone) throws PersistentException;
	public int getLoginId(String userName) throws PersistentException;
	public long getPhoneNumber(int loginId) throws PersistentException;
	public void savePersonalDetails(PersonDTO personalDetailsDTO) throws PersistentException;
	public void saveAddressDetails(AddressDTO addressDetailsDTO) throws PersistentException;
	public List<Map<String, Object>>  getReligion() throws PersistentException;
	public void uploadImage(String imageData, int loginId, boolean isProfilePic, int albumId) throws PersistentException;
	public void saveEducationDetails(EducationDTO educationDetailsDTO) throws PersistentException;
	public void updateImage(String image, int login_id) throws PersistentException;
	public void updatePersonalDetails(PersonDTO personalDetailsDTO) throws PersistentException;
	public void updateEducationDetails(EducationDTO personalDetailsDTO) throws PersistentException;
	public void updateAddressDetails(AddressDTO personalDetailsDTO) throws PersistentException;
	public List<AddressDTO> getAddresses(int loginId) throws PersistentException;
	public List<EducationDTO> getEducation(int loginId) throws PersistentException;
	public PersonDTO getPersonalDetails(int loginId) throws PersistentException;
	//PARISHRAM DATABASE END
}
