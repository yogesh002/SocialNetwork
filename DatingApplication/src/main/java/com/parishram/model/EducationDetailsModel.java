package com.parishram.model;

import java.io.Serializable;

public class EducationDetailsModel implements Serializable {
	private static final long serialVersionUID = 570540655035489476L;
	private int loginId;
	private String schoolName;
	private String schoolCity;
	private String schoolState;
	private String schoolStreet;
	private String levelOfEducation;
	private String fieldOfStudy;
	public EducationDetailsModel(){}
	public int getLoginId() {
		return loginId;
	}

	public void setLoginId(int loginId) {
		this.loginId = loginId;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public String getSchoolCity() {
		return schoolCity;
	}

	public void setSchoolCity(String schoolCity) {
		this.schoolCity = schoolCity;
	}

	public String getSchoolState() {
		return schoolState;
	}

	public void setSchoolState(String schoolState) {
		this.schoolState = schoolState;
	}

	public String getSchoolStreet() {
		return schoolStreet;
	}

	public void setSchoolStreet(String schoolStreet) {
		this.schoolStreet = schoolStreet;
	}

	public String getLevelOfEducation() {
		return levelOfEducation;
	}

	public void setLevelOfEducation(String levelOfEducation) {
		this.levelOfEducation = levelOfEducation;
	}

	public String getFieldOfStudy() {
		return fieldOfStudy;
	}

	public void setFieldOfStudy(String fieldOfStudy) {
		this.fieldOfStudy = fieldOfStudy;
	}

}
