package com.parishram.model;

import java.io.Serializable;
public class ProfileDetailsModel implements Serializable {
	private static final long serialVersionUID = 2963313858145080840L;
	private UserDetailsModel userDetailsModel;
	private AddressDetailsModel addressDetailsModel;
	private EducationDetailsModel educationDetailsModel;
	public UserDetailsModel getUserDetailsModel() {
		return userDetailsModel;
	}
	public void setUserDetailsModel(UserDetailsModel userDetailsModel) {
		this.userDetailsModel = userDetailsModel;
	}
	public AddressDetailsModel getAddressDetailsModel() {
		return addressDetailsModel;
	}
	public void setAddressDetailsModel(AddressDetailsModel addressDetailsModel) {
		this.addressDetailsModel = addressDetailsModel;
	}
	public EducationDetailsModel getEducationDetailsModel() {
		return educationDetailsModel;
	}
	public void setEducationDetailsModel(EducationDetailsModel educationDetailsModel) {
		this.educationDetailsModel = educationDetailsModel;
	}
	
}
