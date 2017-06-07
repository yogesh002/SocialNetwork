package com.parishram.bean.validation;

import org.apache.commons.lang.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.parishram.model.ProfileDetailsModel;

public class UserDetailsModelValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ProfileDetailsModel.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ProfileDetailsModel profileDetailsModel = (ProfileDetailsModel) target;
		if (profileDetailsModel.getUserDetailsModel() != null) {
			ValidationUtils.rejectIfEmpty(errors, "userDetailsModel.firstName", "NotEmpty.profilesignup.firstname");
			ValidationUtils.rejectIfEmpty(errors, "userDetailsModel.lastName", "NotEmpty.profilesignup.lastname");
			String gender = String.valueOf(profileDetailsModel.getUserDetailsModel().getGender());
			if ( !(StringUtils.equalsIgnoreCase(gender, "M") || StringUtils.equalsIgnoreCase(gender, "F"))) {
				errors.rejectValue("userDetailsModel.gender", "NotEmpty.profilesignup.gender");
			}
		}
		if (profileDetailsModel.getAddressDetailsModel() != null) {
			ValidationUtils.rejectIfEmpty(errors, "addressDetailsModel.country", "NotEmpty.profilesignup.country");
			ValidationUtils.rejectIfEmpty(errors, "addressDetailsModel.state", "NotEmpty.profilesignup.state");
			if (profileDetailsModel.getAddressDetailsModel().getZip() == 0) {
				errors.rejectValue("addressDetailsModel.zip", "NotNull.profilesignup.zip");
			}
		}
		
	}
}
