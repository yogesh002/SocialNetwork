package com.parishram.model;

import java.util.List;

public class UserInfoModel {
	private UserDetailsModel userDetailsModel;
	private List<EducationDetailsModel> educationDetails;
	private List<AddressDetailsModel> addressDetails;

	public UserDetailsModel getUserDetailsModel() {
		return userDetailsModel;
	}

	public void setUserDetailsModel(UserDetailsModel userDetailsModel) {
		this.userDetailsModel = userDetailsModel;
	}

	public List<EducationDetailsModel> getEducationDetails() {
		return educationDetails;
	}

	public void setEducationDetails(List<EducationDetailsModel> educationDetails) {
		this.educationDetails = educationDetails;
	}

	public List<AddressDetailsModel> getAddressDetails() {
		return addressDetails;
	}

	public void setAddressDetails(List<AddressDetailsModel> addressDetails) {
		this.addressDetails = addressDetails;
	}

}
