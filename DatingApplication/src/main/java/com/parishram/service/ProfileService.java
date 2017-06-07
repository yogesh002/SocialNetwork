package com.parishram.service;

import java.util.List;
import java.util.Map;

import com.parishram.model.AddressDetailsModel;
import com.parishram.model.ContactFormModel;
import com.parishram.model.EducationDetailsModel;
import com.parishram.model.UserDetailsModel;
import com.parishram.model.UserSignUp;
import com.parishram.service.exception.ServiceException;

public interface ProfileService {
	public void signUpNewUser(String userName, String email, String password,long phone, List<String> errorList) throws ServiceException;
	public int getLoginIdUsingUserName(String userName)throws ServiceException;
	public int getEmailIdUsingUserName(String userName) throws ServiceException;
	public long getPhoneNumber(int loginId) throws ServiceException;
	public List<Map<String, Object>>  getReligion() throws ServiceException;
	public void savePersonalDetails(UserSignUp userSignup, UserDetailsModel personalDetails) throws ServiceException;
	public void saveAddressDetails(UserSignUp userSignup, AddressDetailsModel addressDeatailModel) throws ServiceException;
	public void uploadImage(String imageData, int loginId, boolean isProfilePic, int albumId) throws ServiceException;
	public void saveEducationDetails(UserSignUp userSignup, EducationDetailsModel educationDetailsModel) throws ServiceException ;
	public void updateImage(String image, int login_id) throws ServiceException;
	public void sendEmailFromContactForm(ContactFormModel contactForm) throws ServiceException;
	public UserDetailsModel getPersonalDetails(int loginId) throws ServiceException;
	public List<EducationDetailsModel> getEducationDetails(int loginId) throws ServiceException;
	public List<AddressDetailsModel> getAddressDetails(int loginId) throws ServiceException;
	public void updateEducationDetails(int loginId, EducationDetailsModel educationModel) throws ServiceException;
	public void updateAddressDetails(int loginId, AddressDetailsModel addressModel) throws ServiceException;
	public void updatePersonalDetails(int loginId, UserDetailsModel userModel) throws ServiceException;

}
