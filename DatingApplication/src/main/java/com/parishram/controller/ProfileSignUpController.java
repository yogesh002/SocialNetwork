package com.parishram.controller;

import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.parishram.model.ProfileDetailsModel;
import com.parishram.model.UserSignUp;

@SessionAttributes({ "usersignup" })
@Controller
public class ProfileSignUpController {
	@Autowired
	private MessageSource messageSource;

	@RequestMapping("/signupsuccess")
	public ModelAndView signUpSuccess(@ModelAttribute("usersignup") UserSignUp signUpUserModel, HttpSession session) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.addObject("successMessage",
				messageSource.getMessage("profile.signup.success.message", null, Locale.ENGLISH));
		modelAndView.addObject("username", signUpUserModel.getUsername());
		modelAndView.addObject("profileImage", signUpUserModel.getSignUpProfileImage());
		ProfileDetailsModel profileDetailsModel = (ProfileDetailsModel)session.getAttribute("profileDetailsModel");
		modelAndView.addObject("profileDetailsModel", profileDetailsModel);
		modelAndView.setViewName("profile/signupsummary");
		return modelAndView;
	}

	@RequestMapping("/signupfailure")
	public ModelAndView signUpFailure() {
		ModelAndView modelAndView = new ModelAndView("login/signupfailure");
		return modelAndView;
	}
}
