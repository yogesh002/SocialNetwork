package com.parishram.daoimpl;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.parishram.dao.ProfileDao;
import com.parishram.database.utils.Queries;
import com.parishram.dto.AddressDTO;
import com.parishram.dto.EducationDTO;
import com.parishram.dto.PersonDTO;
import com.parishram.service.exception.PersistentException;

@Repository
public class ProfileDaoImpl implements ProfileDao {
	private static Logger LOGGER = Logger.getLogger(ProfileDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	// PARISHRAM DATABSE START

	@Override
	public int checkExistingUserName(String userName) throws PersistentException {
		Integer result = 0;
		try {
			result = jdbcTemplate.queryForObject(Queries.CHECK_IF_USERNAME_EXISTS.getValue(), new Object[] { userName },
					Integer.class);
			return result;
		} catch (DataAccessException dataAcessException) {
			LOGGER.error(
					"Error while accessing the database - Unable to check if the email address already exists in the database ",
					dataAcessException);
			throw new PersistentException("Unable to check if the email address already exists in the database",
					dataAcessException);
		}

	}

	@Override
	public int checkExistingEmailAddress(String email) throws PersistentException {
		Integer result = 0;
		try {
			result = jdbcTemplate.queryForObject(Queries.CHECK_IF_EMAIL_EXISTS.getValue(), new Object[] { email },
					Integer.class);
			return result;
		} catch (DataAccessException dataAcessException) {
			LOGGER.error(
					"Error while accessing the database - Unable to check if the email address already exists in the database ",
					dataAcessException);
			throw new PersistentException("Unable to check if the email address already exists in the database",
					dataAcessException);
		}
	}

	@Override
	public void signUpNewUser(String userName, String email, String password, long phone) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.SIGNUP_NEW_USER.getValue(), userName, password, email, phone);
		} catch (DataAccessException dataAcessException) {
			LOGGER.error("Unable to sign up new user. Unable to insert record in the database.", dataAcessException);
			throw new PersistentException("Unable to sign up new user", dataAcessException);
		}
	}

	@Override
	public int getLoginId(String userName) throws PersistentException {
		int loginId = 0;
		try {
			loginId = jdbcTemplate.queryForObject(Queries.RETRIEVE_LOGINID.getValue(), new Object[] { userName },
					Integer.class);
			return loginId;
		} catch (DataAccessException dataAcessException) {
			LOGGER.error("Unable to register the user details because user id cannot be fetched from the database.",
					dataAcessException);
			throw new PersistentException("Unable to retrieve the login id; The user registration failed.",
					dataAcessException);
		}
	}

	@Override
	public void savePersonalDetails(PersonDTO personalDetailsDTO) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.INSERT_USER_DETAILS.getValue(), personalDetailsDTO.getPerson_id(),
					personalDetailsDTO.getFirst_name(), personalDetailsDTO.getLast_name(), personalDetailsDTO.getAge(),
					personalDetailsDTO.getWeight(), personalDetailsDTO.getOccupation(), personalDetailsDTO.getGender(),
					personalDetailsDTO.getSalary(), personalDetailsDTO.getReligionId());
		} catch (DataAccessException dataAcessException) {
			LOGGER.error("Unable to register the user details because user details cannot be saved in the database.",
					dataAcessException);
			throw new PersistentException("Unable to save the user details; The user registration failed.",
					dataAcessException);
		}

	}

	@Override
	public void saveAddressDetails(AddressDTO addressDTO) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.INSERT_ADDRESS_DETAILS.getValue(), addressDTO.getLoginId(),
					addressDTO.getCity(), addressDTO.getState(), addressDTO.getZipCode(), addressDTO.getCountry());
		} catch (DataAccessException dataAcessException) {
			LOGGER.error("Unable to register the user details because address details cannot be saved in the database.",
					dataAcessException);
			throw new PersistentException("Unable to save the user details; The user registration failed.",
					dataAcessException);
		}

	}

	@Override
	public void saveEducationDetails(EducationDTO educationDetailsDTO) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.INSERT_EDUCATION_DETAILS.getValue(), educationDetailsDTO.getLoginId(),
					educationDetailsDTO.getSchoolName(), educationDetailsDTO.getSchoolCity(),
					educationDetailsDTO.getSchoolState(), educationDetailsDTO.getSchoolStreet(),
					educationDetailsDTO.getFieldOfStudy(), educationDetailsDTO.getLevelOfEducation());
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to insert education details in the databse." + exception.getMessage(), exception);
			throw new PersistentException("Unable to save education details in the table.", exception);
		}
	}

	@Override
	public List<Map<String, Object>> getReligion() throws PersistentException {
		try {
			List<Map<String, Object>> religions = jdbcTemplate.queryForList(Queries.RETRIEVE_RELIGION.getValue());
			return religions;
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve religions from the databse." + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve religions from the database.", exception);
		}
	}

	@Override
	public void uploadImage(String imageData, int loginId, boolean isProfilePic, int albumId)
			throws PersistentException {
		try {
			jdbcTemplate.update(Queries.SAVE_IMAGE.getValue(), imageData, isProfilePic, loginId, albumId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to save image in the databse." + exception.getMessage(), exception);
			throw new PersistentException("Unable to save image in the table.", exception);
		}
	}

	@Override
	public void updateImage(String image, int login_id) throws PersistentException {
		try {
			jdbcTemplate.update(Queries.UPDATE_PROFILE_IMAGE.getValue(), image, login_id);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to update the image in the databse." + exception.getMessage(), exception);
			throw new PersistentException("Unable to update image in the table.", exception);
		}
	}

	@Override
	public PersonDTO getPersonalDetails(int loginId) {
		PersonDTO personDTO = new PersonDTO();
		List<PersonDTO> personList = jdbcTemplate.query(Queries.SELECT_PERSONDETAILS.getValue(),
				new Object[] { loginId }, new RowMapper<PersonDTO>() {
					@Override
					public PersonDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
						personDTO.setFirst_name(resultSet.getString("firstname"));
						personDTO.setLast_name(resultSet.getString("lastname"));
						personDTO.setAge(resultSet.getString("age").charAt(0));
						personDTO.setGender(resultSet.getString("gender").charAt(0));
						personDTO.setWeight(resultSet.getDouble("weight"));
						personDTO.setOccupation(resultSet.getString("occupation"));
						personDTO.setReligion(resultSet.getString("religion"));
						personDTO.setSalary(new BigDecimal(resultSet.getInt("salary")));
						return personDTO;
					}
				});
		return personList.get(0);

	}

	@Override
	public List<EducationDTO> getEducation(int loginId) {
		EducationDTO educationDTO = new EducationDTO();
		List<EducationDTO> educationList = jdbcTemplate.query(Queries.SELECT_EDUCATION.getValue(),
				new Object[] { loginId }, new RowMapper<EducationDTO>() {
					@Override
					public EducationDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
						educationDTO.setLevelOfEducation(resultSet.getString("level_of_education"));
						educationDTO.setFieldOfStudy(resultSet.getString("field_of_study"));
						educationDTO.setSchoolName(resultSet.getString("school_name"));
						educationDTO.setSchoolCity(resultSet.getString("school_city"));
						educationDTO.setSchoolState(resultSet.getString("school_state"));
						educationDTO.setSchoolStreet(resultSet.getString("school_street"));
						return educationDTO;
					}
				});
		return educationList;
	}

	@Override
	public List<AddressDTO> getAddresses(int loginId) {
		AddressDTO address = new AddressDTO();
		List<AddressDTO> addressList = jdbcTemplate.query(Queries.SELECT_ADDRESS.getValue(), new Object[] { loginId },
				new RowMapper<AddressDTO>() {
					@Override
					public AddressDTO mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
						address.setCity(resultSet.getString("city"));
						address.setState(resultSet.getString("state"));
						address.setZipCode(resultSet.getInt("zip"));
						address.setCountry(resultSet.getString("country"));
						return address;
					}
				});
		return addressList;
	}

	// PARISHRAM DATABASE END

	@Override
	public void updateEducationDetails(EducationDTO educationDetailsDTO) {
		jdbcTemplate.update(Queries.UPDATE_EDUCATION_DETAILS.getValue(), educationDetailsDTO.getSchoolName(),
				educationDetailsDTO.getSchoolCity(), educationDetailsDTO.getSchoolState(),
				educationDetailsDTO.getSchoolStreet(), educationDetailsDTO.getFieldOfStudy(),
				educationDetailsDTO.getLevelOfEducation(), educationDetailsDTO.getLoginId());

	}

	@Override
	public void updatePersonalDetails(PersonDTO personalDetailsDTO) {
		jdbcTemplate.update(Queries.UPDATE_PERSONAL_DETAILS.getValue(), personalDetailsDTO.getFirst_name(),
				personalDetailsDTO.getLast_name(), personalDetailsDTO.getAge(), personalDetailsDTO.getWeight(),
				personalDetailsDTO.getOccupation(), personalDetailsDTO.getGender(), personalDetailsDTO.getSalary(),
				personalDetailsDTO.getReligionId(), personalDetailsDTO.getPerson_id());
	}

	@Override
	public void updateAddressDetails(AddressDTO addressDTO) {
		jdbcTemplate.update(Queries.UPDATE_ADDRESS_DETAILS.getValue(), addressDTO.getCity(), addressDTO.getState(),
				addressDTO.getZipCode(), addressDTO.getCountry(), addressDTO.getLoginId());
	}

	public long getPhoneNumber(int loginId) throws PersistentException {
		try {
			long phoneNumber = jdbcTemplate.queryForObject(Queries.RETRIEVE_PHONE_NUMBER.getValue(),
					new Object[] { loginId }, Long.class);
			return phoneNumber;
		} catch (DataAccessException dataAcessException) {
			LOGGER.error("Unable to get phone number of the user from the database.", dataAcessException);
			throw new PersistentException("Unable to get phone number of the user from the database.",
					dataAcessException);
		}
	}

}