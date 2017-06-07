package com.parishram.bean.validation;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.parishram.model.LoginContext;

public class LoginValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return LoginContext.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmpty(errors, "loginContext.userName", "NotEmpty.login.userName");
		ValidationUtils.rejectIfEmpty(errors, "loginContext.password", "NotEmpty.login.password");
	}
}
