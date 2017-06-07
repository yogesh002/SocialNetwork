package com.parishram.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.parishram.model.UserSignUp;
import com.parishram.service.ProfileService;
import com.parishram.service.exception.ServiceException;

@SessionAttributes({ "usersignup" })
@Controller
public class UserSignUpController {
	private static Logger LOGGER = Logger.getLogger(UserSignUpController.class);

	@Autowired
	private ProfileService profileService;

	private List<String> errorList;

	@RequestMapping(value = "/newUserSignUp", method = RequestMethod.GET)
	public ModelAndView displaySignUpPage() {
		return new ModelAndView("login/usersignup", "usersignup", new UserSignUp());
	}

	@RequestMapping(value = "/newUserSignUp", method = RequestMethod.POST)
	public ModelAndView userSignUp(@Valid @ModelAttribute("usersignup") UserSignUp signUpUserModel,
			BindingResult result) {
		ModelAndView modelAndView = new ModelAndView();
		errorList = new ArrayList<String>();
		if (result.hasErrors()) {
			modelAndView.setViewName("login/usersignup");
			return modelAndView;
		}
		try {
			profileService.signUpNewUser(signUpUserModel.getUsername(), signUpUserModel.getEmail(),
					signUpUserModel.getPassword(),signUpUserModel.getPhone(), errorList);
			if (!errorList.isEmpty()) {
				modelAndView.addObject("signUpErrors", errorList);
				modelAndView.setViewName("login/usersignup");
			} else {
				modelAndView.setViewName("redirect:/registerProfile");
			}
			return modelAndView;
		} catch (ServiceException serviceException) {
			LOGGER.error("Unable to signup the user. " + serviceException.getMessage(), serviceException);
			modelAndView.addObject("signUpErrors", serviceException.getMessage());
			modelAndView.setViewName("login/usersignup");
			return modelAndView;
		}
	}
}
