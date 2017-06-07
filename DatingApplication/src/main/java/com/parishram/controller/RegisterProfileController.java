package com.parishram.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.parishram.bean.validation.UserDetailsModelValidator;
import com.parishram.model.ProfileDetailsModel;
import com.parishram.model.UserSignUp;
import com.parishram.service.ProfileService;
import com.parishram.service.exception.ServiceException;
import com.parishram.utils.UploadPictures;

@SessionAttributes({ "usersignup"})
@Controller
public class RegisterProfileController {
	private static Logger LOGGER = Logger.getLogger(RegisterProfileController.class);

	@Autowired
	private ProfileService profileService;

	@Autowired
	private UserDetailsModelValidator userDetailsModelValidator;

	@Autowired
	private MessageSource messageSource;

	@RequestMapping(value = "/registerProfile", method = RequestMethod.GET)
	public String registerProfile(@ModelAttribute("usersignup") UserSignUp userSignUp, Model model) {
		try {
			int loginId = profileService.getLoginIdUsingUserName(userSignUp.getUsername());
			if (loginId != 0) {
				userSignUp.setLoginId(loginId);
			}
			List<Map<String, Object>> religion = profileService.getReligion();
			model.addAttribute("religion", religion);
			if (!model.containsAttribute("profileDetailsModel")) {
				model.addAttribute("profileDetailsModel", new ProfileDetailsModel());
			}
		} catch (ServiceException serviceException) {
			LOGGER.error("Unable to signup the user. " + serviceException.getMessage(), serviceException);
			model.addAttribute("signUpErrors", serviceException.getMessage());
			return "/login/usersignup";
		}
		return "profile/profilesignup";
	}

	@RequestMapping(value = "/imgupload", method = RequestMethod.POST)
	@ResponseBody
	public String getAvatarInString(HttpServletRequest request,
			@ModelAttribute("usersignup") UserSignUp signUpUserModel) {
		String signUpImage = "";
		try {
			signUpImage = UploadPictures.uploadPictures(request).get(0);
			if (StringUtils.isNotBlank(signUpUserModel.getSignUpProfileImage())) {
				profileService.updateImage(signUpImage, signUpUserModel.getLoginId());
			} else {
				signUpUserModel.setProfilePic(Boolean.TRUE);
				profileService.uploadImage(signUpImage, signUpUserModel.getLoginId(), signUpUserModel.isProfilePic(),
						-1);
			}
			signUpUserModel.setSignUpProfileImage(signUpImage);
		} catch (IOException | ServiceException | FileUploadException exception) {
			signUpImage = "";
			LOGGER.error("Unable to save the image in the database." + exception.getMessage(), exception);
		}
		return signUpImage;
	}

	@RequestMapping(value = "/saveUserPersonalDetails", method = RequestMethod.POST)
	public ModelAndView savePersonalDetails(
			@Valid @ModelAttribute("profileDetailsModel") ProfileDetailsModel profileDetailsModel, BindingResult result,
			@ModelAttribute("usersignup") UserSignUp signUpUserModel, final RedirectAttributes redirectAttributes, HttpSession session) {
		try {
			userDetailsModelValidator.validate(profileDetailsModel, result);
			if (result.hasErrors()) {
				Map<String, String> errorMap = new HashMap<String, String>();
				List<FieldError> fieldErrorList = result.getFieldErrors();

				for (FieldError fieldError : fieldErrorList) {
					String messageCode = fieldError.getCode();
					String[] resolveMessageCodes = result.resolveMessageCodes(messageCode);
					String resolvedMsgCode = resolveMessageCodes[1];
					String message = messageSource.getMessage(resolvedMsgCode,
							new Object[] { fieldError.getRejectedValue() }, null);
					errorMap.put(fieldError.getField(), message);
				}
				ModelAndView modelAndView = new ModelAndView();
				List<Map<String, Object>> religion = profileService.getReligion();
				modelAndView.addObject("religion", religion);
				modelAndView.setViewName("profile/profilesignup");
				return modelAndView;
			} else {
				profileService.savePersonalDetails(signUpUserModel, profileDetailsModel.getUserDetailsModel());
				profileService.saveAddressDetails(signUpUserModel, profileDetailsModel.getAddressDetailsModel());
				profileService.saveEducationDetails(signUpUserModel, profileDetailsModel.getEducationDetailsModel());
				session.setAttribute("profileDetailsModel", profileDetailsModel);
				return new ModelAndView("redirect:/sendEmail");
			}
		} catch (ServiceException serviceException) {
			LOGGER.error("Unable to save user details. " + serviceException.getMessage(), serviceException);
			redirectAttributes.addFlashAttribute("signUpErrors", serviceException.getMessage());
			return new ModelAndView("redirect:/registerProfile");

		}
		// TODO: redirect user to a new jsp with error message to come back
		// later
		catch (Exception exception) {
			LOGGER.error("Unable to save user details. " + exception.getMessage(), exception);
			return null;
		}
	}
}
