package com.parishram.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.parishram.model.AddressDetailsModel;
import com.parishram.model.EducationDetailsModel;
import com.parishram.model.LoginContext;
import com.parishram.model.ProfileDetailsModel;
import com.parishram.model.UserDetailsModel;
import com.parishram.model.UserInfoModel;
import com.parishram.service.ProfileService;
import com.parishram.service.exception.ServiceException;

@SessionAttributes({ "loginContext" })
@Controller
public class UpdateUserDetailsController {
	@Autowired
	private ProfileService profileService;

	@RequestMapping(value = "/getUserDetails", method = RequestMethod.GET)
	public ModelAndView goToAlbumPage() {
		try {
			return new ModelAndView("profile/updateuserdetails");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/updatepersonalInfo", method = RequestMethod.GET)
	public @ResponseBody UserInfoModel getPersonalInfoDetails(@ModelAttribute("loginContext") LoginContext loginContext,
			ModelAndView modelAndView, HttpSession session) {
		String userName = loginContext.getUserName();
		UserInfoModel userInfoModel = new UserInfoModel();
		try {
			int loginId = profileService.getLoginIdUsingUserName(userName);
			UserDetailsModel userDetails = profileService.getPersonalDetails(loginId);
			List<AddressDetailsModel> addresses = profileService.getAddressDetails(loginId);
			List<EducationDetailsModel> education = profileService.getEducationDetails(loginId);
			userInfoModel.setAddressDetails(addresses);
			userInfoModel.setEducationDetails(education);
			userInfoModel.setUserDetailsModel(userDetails);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return userInfoModel;
	}

	@RequestMapping(value = "/updateEducationDetails", method = RequestMethod.POST)
	public @ResponseBody EducationDetailsModel updateEducationDetails(
			@ModelAttribute("loginContext") LoginContext loginContext, @RequestBody ProfileDetailsModel model) {
		String userName = loginContext.getUserName();
		try {
			int loginId = profileService.getLoginIdUsingUserName(userName);
			profileService.updateEducationDetails(loginId, model.getEducationDetailsModel());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model.getEducationDetailsModel();
	}

	@RequestMapping(value = "/updateAddressDetails", method = RequestMethod.POST)
	public @ResponseBody AddressDetailsModel updateAddressDetails(
			@ModelAttribute("loginContext") LoginContext loginContext, @RequestBody ProfileDetailsModel model) {
		String userName = loginContext.getUserName();
		try {
			int loginId = profileService.getLoginIdUsingUserName(userName);
			profileService.updateAddressDetails(loginId, model.getAddressDetailsModel());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model.getAddressDetailsModel();
	}

	@RequestMapping(value = "/updatePersonalDetails", method = RequestMethod.POST)
	public @ResponseBody UserDetailsModel updateUserDetails(@ModelAttribute("loginContext") LoginContext loginContext,
			@RequestBody ProfileDetailsModel model) {
		String userName = loginContext.getUserName();
		try {
			int loginId = profileService.getLoginIdUsingUserName(userName);
			profileService.updatePersonalDetails(loginId, model.getUserDetailsModel());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return model.getUserDetailsModel();
	}
}
