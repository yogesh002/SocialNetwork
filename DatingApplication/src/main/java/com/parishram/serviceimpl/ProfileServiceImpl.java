package com.parishram.serviceimpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.parishram.dao.ProfileDao;
import com.parishram.dto.AddressDTO;
import com.parishram.dto.EducationDTO;
import com.parishram.dto.PersonDTO;
import com.parishram.model.AddressDetailsModel;
import com.parishram.model.ContactFormModel;
import com.parishram.model.EducationDetailsModel;
import com.parishram.model.UserDetailsModel;
import com.parishram.model.UserSignUp;
import com.parishram.service.ProfileService;
import com.parishram.service.exception.PersistentException;
import com.parishram.service.exception.ServiceException;

@Component
public class ProfileServiceImpl implements ProfileService {
	private static Logger LOGGER = Logger.getLogger(ProfileServiceImpl.class);
	@Autowired
	private ProfileDao profileDAO;

	@Autowired
	private PersonDTO personalDetailsDTO;

	@Autowired
	private EducationDTO educationDTO;

	@Autowired
	private AddressDTO addressDTO;

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void signUpNewUser(String userName, String email, String password, long phone, List<String> serviceErrorList)
			throws ServiceException {
		// TODO: validation logic here
		try {
			int loginIdStatus = profileDAO.checkExistingUserName(userName.toLowerCase());
			if (loginIdStatus > 0) {
				serviceErrorList.add("The username already exists !");
			}
			int emailIdStatus = profileDAO.checkExistingEmailAddress(email.toLowerCase());
			if (emailIdStatus > 0) {
				serviceErrorList.add("The email Id already exists !");
			}
			if (loginIdStatus == 0 && emailIdStatus == 0 && serviceErrorList.isEmpty()) {
				String hashedPassword = generateHashedPassword(password);
				profileDAO.signUpNewUser(userName.toLowerCase(), email.toLowerCase(), hashedPassword, phone);
			}
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to sign up new user. " + persistentException.getMessage(), persistentException);
			throw new ServiceException("We are experiencing technical issue. Please try again later.",
					persistentException);
		}
	}

	private String generateHashedPassword(String password) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(password);

	}

	@Override
	public void savePersonalDetails(UserSignUp userSignup, UserDetailsModel userDetailsModel) throws ServiceException {
		try {
			setUserDetailsInDTO(userSignup.getLoginId(), userDetailsModel);
			profileDAO.savePersonalDetails(personalDetailsDTO);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to signup new user because user id cannot be found or unable to save user details."
					+ persistentException.getMessage(), persistentException);
			throw new ServiceException("We are experiencing technical issue. Please try again later.",
					persistentException);
		}
	}

	@Override
	public void saveAddressDetails(UserSignUp userSignup, AddressDetailsModel addressDetailsModel)
			throws ServiceException {
		try {
			setAddressDetailsInDTO(userSignup.getLoginId(), addressDetailsModel);
			profileDAO.saveAddressDetails(addressDTO);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to signup new user because address cannot be saved in the database"
					+ persistentException.getMessage(), persistentException);
			throw new ServiceException("We are experiencing technical issue. Please try again later.",
					persistentException);
		}
	}

	@Override
	public void saveEducationDetails(UserSignUp userSignup, EducationDetailsModel educationDetailsModel)
			throws ServiceException {
		// TODO: use join instead of using personId
		/*
		*/
		try {
			//

			setEducationDetailsInDTO(userSignup.getLoginId(), educationDetailsModel);
			profileDAO.saveEducationDetails(educationDTO);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to signup new user because education details cannot be saved in the database"
					+ persistentException.getMessage(), persistentException);
			throw new ServiceException("We are experiencing technical issue. Please try again later.",
					persistentException);
		}

	}

	@Override
	public List<Map<String, Object>> getReligion() throws ServiceException {
		try {
			return profileDAO.getReligion();
		} catch (PersistentException exception) {
			LOGGER.error("Unable to signup new user because user id cannot be found or unable to save user details."
					+ exception.getMessage(), exception);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + exception.getMessage(), exception);
		}
	}

	@Override
	public void uploadImage(String imageData, int loginId, boolean isProfilePic, int albumId) throws ServiceException {
		try {
			profileDAO.uploadImage(imageData, loginId, isProfilePic, albumId);
		} catch (PersistentException exception) {
			LOGGER.error("Unable to upload the image" + exception.getMessage(), exception);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + exception.getMessage(), exception);
		}
	}

	@Override
	public int getLoginIdUsingUserName(String userName) throws ServiceException {
		try {
			return profileDAO.getLoginId(userName.toLowerCase());
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to fetch user id from the database" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	// TODO: Delete this
	@Override
	public int getEmailIdUsingUserName(String userName) throws ServiceException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void sendEmailFromContactForm(ContactFormModel contactForm) throws ServiceException {
		try {
			MimeMessage mimeMessage = mailSender.createMimeMessage();
			MimeMessageHelper mail = new MimeMessageHelper(mimeMessage, false, "utf-8");
			// mail.setTo(Constant.MAIL_RECEIVER);
			mail.setTo(contactForm.getReceiverEmail());
			mail.setSentDate(new Date());
			mail.setSubject("Questions");
			StringBuilder builder = new StringBuilder();
			if (StringUtils.isNotBlank(contactForm.getName())) {
				builder.append("<div style='font-weight: bolder; color: brown'>#SENDER : </div>");
				builder.append("<div style='font-weight: bold; color: purple; margin-bottom: 12px'>"
						+ contactForm.getName() + "</div>");
			}
			builder.append("<div style='font-weight: bolder; color: brown'>" + "#MESSAGE  : " + "</div>");
			builder.append("<div style='font-weight: bold; color: purple; margin-bottom: 12px'>"
					+ contactForm.getMessageBody() + "</div>");
			builder.append("<div style='font-weight: bolder; color: brown'>" + "#SENDER'S EMAIL : " + "</div>");
			builder.append("<div cstyle='font-weight: bold; color: purple; margin-bottom: 12px'>"
					+ contactForm.getSenderEmail() + "</div>");
			LOGGER.debug(builder.toString());
			mimeMessage.setContent(builder.toString(), "text/html");
			mailSender.send(mimeMessage);
		} catch (MailException | MessagingException exception) {
			LOGGER.error("Exception is thrown while sending email. Please try again later.");
			throw new ServiceException("Unable to send email. Please try again later." + exception.getMessage(),
					exception);
		}
	}

	private void setUserDetailsInDTO(int loginId, UserDetailsModel personalDetails) {
		personalDetailsDTO.setPerson_id(loginId);
		personalDetailsDTO.setAge(Integer.valueOf(personalDetails.getAge().trim()));
		personalDetailsDTO.setFirst_name(personalDetails.getFirstName().trim());
		personalDetailsDTO.setGender(personalDetails.getGender());
		personalDetailsDTO.setLast_name(personalDetails.getLastName().trim());
		personalDetailsDTO.setOccupation(personalDetails.getOccupation().trim());
		personalDetailsDTO.setSalary(StringUtils.isBlank(personalDetails.getSalary().trim()) ? new BigDecimal("0")
				: new BigDecimal(personalDetails.getSalary().trim()));
		personalDetailsDTO.setWeight(StringUtils.isBlank(personalDetails.getWeight().trim()) ? Float.parseFloat("0")
				: Float.parseFloat(personalDetails.getWeight().trim()));
		personalDetailsDTO.setReligionId(personalDetails.getReligionId());

	}

	private void setAddressDetailsInDTO(int loginId, AddressDetailsModel addressDetailsModel) {
		addressDTO.setZipCode(addressDetailsModel.getZip());
		addressDTO.setCity(addressDetailsModel.getCity());
		addressDTO.setCountry(addressDetailsModel.getCountry());
		addressDTO.setState(addressDetailsModel.getState());
		addressDTO.setLoginId(loginId);
	}

	private void setEducationDetailsInDTO(int loginId, EducationDetailsModel educationDetailsModel) {
		educationDTO.setLoginId(loginId);
		educationDTO.setFieldOfStudy(educationDetailsModel.getFieldOfStudy());
		educationDTO.setLevelOfEducation(educationDetailsModel.getLevelOfEducation());
		educationDTO.setSchoolCity(educationDetailsModel.getSchoolCity());
		educationDTO.setSchoolName(educationDetailsModel.getSchoolName());
		educationDTO.setSchoolState(educationDetailsModel.getSchoolState());
		educationDTO.setSchoolStreet(educationDetailsModel.getSchoolStreet());
	}

	public void updateImage(String image, int login_id) throws ServiceException {
		try {
			profileDAO.updateImage(image, login_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to update the image in the database" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public UserDetailsModel getPersonalDetails(int loginId) throws ServiceException {
		try {
			PersonDTO personDTO = profileDAO.getPersonalDetails(loginId);
			UserDetailsModel user = new UserDetailsModel();
			user.setAge(String.valueOf(personDTO.getAge()));
			user.setFirstName(personDTO.getFirst_name());
			user.setGender(personDTO.getGender());
			user.setLastName(personDTO.getLast_name());
			user.setOccupation(personDTO.getOccupation());
			user.setReligion(personDTO.getReligion());
			user.setSalary(String.valueOf(personDTO.getSalary()));
			user.setWeight(String.valueOf(personDTO.getWeight()));
			return user;
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve personal details" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to retrieve personal details" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public List<EducationDetailsModel> getEducationDetails(int loginId) throws ServiceException {
		List<EducationDetailsModel> educationDetailsModel = new ArrayList<EducationDetailsModel>();
		try {
			List<EducationDTO> educationDTOs = profileDAO.getEducation(loginId);
			for (EducationDTO educationDTO : educationDTOs) {
				EducationDetailsModel education = new EducationDetailsModel();
				education.setFieldOfStudy(educationDTO.getFieldOfStudy());
				education.setLevelOfEducation(educationDTO.getLevelOfEducation());
				education.setSchoolCity(educationDTO.getSchoolCity());
				education.setSchoolName(educationDTO.getSchoolName());
				education.setSchoolState(educationDTO.getSchoolState());
				education.setSchoolStreet(educationDTO.getSchoolStreet());
				educationDetailsModel.add(education);
			}
			return educationDetailsModel;
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve education details" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException("Unable to retrieve education details" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public List<AddressDetailsModel> getAddressDetails(int loginId) throws ServiceException {
		List<AddressDetailsModel> addressDetailsModel = new ArrayList<AddressDetailsModel>();
		try {
			List<AddressDTO> addressDTOs = profileDAO.getAddresses(loginId);
			for (AddressDTO addressDTO : addressDTOs) {
				AddressDetailsModel address = new AddressDetailsModel();
				address.setCity(addressDTO.getCity());
				address.setCountry(addressDTO.getCountry());
				address.setState(addressDTO.getState());
				address.setZip(addressDTO.getZipCode());
				addressDetailsModel.add(address);
			}
			return addressDetailsModel;
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve address details" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to retrieve address details" + persistentException.getMessage(),
					persistentException);
		}
	}

	public void updateEducationDetails(int loginId, EducationDetailsModel educationModel) throws ServiceException {
		try {
			setEducationDetailsInDTO(loginId, educationModel);
			profileDAO.updateEducationDetails(educationDTO);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to update the education details in the database" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void updateAddressDetails(int loginId, AddressDetailsModel addressModel) throws ServiceException {
		try {
			setAddressDetailsInDTO(loginId, addressModel);
			profileDAO.updateAddressDetails(addressDTO);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to update the address details in the database" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void updatePersonalDetails(int loginId, UserDetailsModel userModel) throws ServiceException {
		try {
			setUserDetailsInDTO(loginId, userModel);
			profileDAO.updatePersonalDetails(personalDetailsDTO);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to update the personal details in the database" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public long getPhoneNumber(int loginId) throws ServiceException {
		try {
			return profileDAO.getPhoneNumber(loginId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve phone number from the database" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

}
