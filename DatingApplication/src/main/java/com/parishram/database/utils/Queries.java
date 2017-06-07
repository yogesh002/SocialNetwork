package com.parishram.database.utils;

public enum Queries {
	//PARISHRAM START
	CHECK_IF_USERNAME_EXISTS("SELECT COUNT(username) FROM login WHERE username = ?"),
	CHECK_IF_EMAIL_EXISTS("SELECT CASE WHEN EXISTS(SELECT email FROM login WHERE email = ?) THEN 1 ELSE 0 END"),
	SIGNUP_NEW_USER("INSERT INTO LOGIN(login_id, username, password, email,phone,enabled, role_id) VALUES(DEFAULT, ?, ?,?, ?,DEFAULT,DEFAULT)"),
	RETRIEVE_LOGINID("SELECT login_id FROM login WHERE USERNAME = ?"),
	RETRIEVE_PHONE_NUMBER("SELECT phone FROM login WHERE login_id = ?"),
	RETRIEVE_RELIGION("SELECT * FROM religion_description"),
	SAVE_IMAGE("INSERT INTO IMAGE (image_id, image, is_profile_pic, login_id, album_id) VALUES (DEFAULT, ?, ?, ?, ?)"),
	INSERT_USER_DETAILS("INSERT INTO persondetails(persondetails_id, login_id, firstname, lastname, age, weight, occupation, gender, salary,religion_id) VALUES(DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?)"), 
	INSERT_ADDRESS_DETAILS("INSERT INTO address VALUES(?, ?, ?, ?, ?)"),
	INSERT_EDUCATION_DETAILS("INSERT INTO school VALUES(?, ?, ?, ?, ?, ?, ?)"),
	UPDATE_PROFILE_IMAGE("UPDATE image SET image = ? WHERE login_id = ? and is_profile_pic = 't'"),
	SAVE_TOKEN("UPDATE login SET token = ? WHERE login_id = ?"),
	RETRIEVE_TOKEN("SELECT token FROM login WHERE email = ?"),
	UPDATE_ENABLED("UPDATE login SET enabled = 1 WHERE email = ?"),
	SELECT_PERSONDETAILS("SELECT pd.firstname, pd.lastname, pd.age, pd.weight, pd.occupation, pd.gender, pd.salary, rd.religion FROM persondetails pd INNER JOIN RELIGION_DESCRIPTION rd ON rd.religion_id = pd.religion_id AND pd.login_id =?"),
	SELECT_ADDRESS("SELECT city, state, zip, country FROM address WHERE login_id = ?"),
	SELECT_EDUCATION("SELECT school_name, school_city, school_state, school_street, field_of_study, level_of_education FROM school where login_id = ?"),
	//MAIL
	INSERT_MAIL("INSERT INTO mail VALUES(DEFAULT,?, DEFAULT, ?, ?, DEFAULT, ?)"),
	RETRIEVE_ADMIN_EMAILS("SELECT * FROM MAIL"),
	COUNT_NEW_EMAIL("SELECT COUNT(*) FROM mail WHERE read = 0"),
	COUNT_TOTAL_EMAIL("SELECT COUNT(*) FROM mail"),
	DELETE_EMAIL("DELETE FROM mail WHERE email_id IN (:deleteEmailId)"),
	MARK_EMAIL_AS_READ("UPDATE mail SET read = 1 WHERE email_id = ?"),
	MARK_EMAIL_AS_UNREAD("UPDATE mail SET read = 0 WHERE email_id = ?"),
	REPLY_EMAIL_TO_CUSTOMER("INSERT INTO mail_reply(email_id, from_user, to_user, reply_msg, reply_date) VALUES (?, ? , ?, ?, DEFAULT)"),
	LOGIN_SELECT("SELECT * FROM LOGIN WHERE USERNAME=?"),
	LOGIN_INSERT("INSERT INTO LOGIN(PERSON_ID, USERNAME, PASSWORD, EMAIL, ENABLED) VALUES (DEFAULT, ?, ?, ?, DEFAULT)"), 
	SIGNUP_EDUCATION_DETAILS("INSERT INTO EDUCATION_DETAILS(EDUCATION_ID, LEVEL_OF_EDUCATION, FIELD_OF_STUDY, SCHOOL_NAME, SCHOOL_CITY, SCHOOL_STATE) VALUES(?, ?, ?, ?, ?, ?)"), 
	SIGNUP_ADDRESS_DETAILS("INSERT INTO ADDRESS(ADDRESS_ID, COUNTRY, STATE, CITY, ZIP) VALUES(?, ?, ?, ?, ?)"),
	SELECT_PERSON_ID("SELECT PERSON_ID FROM LOGIN WHERE USERNAME = ? AND PASSWORD = ?"),
	//UPDATE USER DETAILS
	UPDATE_EDUCATION_DETAILS("UPDATE school SET school_name = ?, school_city = ?, school_state = ?,school_street = ?,field_of_study = ?,level_of_education = ? WHERE login_id = ?"),
	UPDATE_ADDRESS_DETAILS("UPDATE address SET city = ?, state = ?, zip = ?,country = ? WHERE login_id = ?"),
	UPDATE_PERSONAL_DETAILS("UPDATE persondetails SET firstname = ?, lastname = ?, age = ?,weight = ?,occupation = ?,gender = ?, salary=?, religion_id = ? WHERE login_id = ?");
	/*MARK_EMAIL_AS_UNREAD("UPDATE mail SET read = 0 WHERE email_id = ?"),
	MARK_EMAIL_AS_UNREAD("UPDATE mail SET read = 0 WHERE email_id = ?");*/
	//PARISHRAM END
	
	private String query;

	Queries(String query) {
		this.query = query;
	}

	public String getValue() {
		return query;
	}
}
