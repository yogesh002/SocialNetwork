package com.parishram.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.parishram.model.CommentsModel;
import com.parishram.model.LoginContext;
import com.parishram.model.UserModel;
import com.parishram.service.LoginService;
import com.parishram.service.MailService;
import com.parishram.service.ManageFriendsService;
import com.parishram.service.ProfileService;
import com.parishram.service.exception.ServiceException;
import com.parishram.utils.ThumbnailGenerator;

@SessionAttributes({ "loginContext" })
@Controller
public class LoginController {
	@Autowired
	private ProfileService profileService;

	@Autowired
	private ManageFriendsService manageFriendsService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private MailService mailService;

	@RequestMapping(value = "/login", method = { RequestMethod.GET })
	public ModelAndView signInUser(Authentication authentication) {
		LoginContext loginContext = new LoginContext();
		User user = (User) authentication.getPrincipal();
		loginContext.setUserName(user.getUsername());
		loginContext.setUserName(user.getUsername());
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		for (GrantedAuthority grantedAuthority : authorities) {
			String authority = grantedAuthority.getAuthority();
			if (StringUtils.isNotBlank(authority)) {
				switch (authority) {
				case "ROLE_USER":
					return new ModelAndView("redirect:/user", "loginContext", loginContext);
				case "ROLE_ADMIN":
					return new ModelAndView("redirect:/admin", "loginContext", loginContext);
				default:
					return new ModelAndView("redirect:/home", "loginContext", loginContext);
				}
			}
		}
		return null;
	}

	@RequestMapping(value = "/admin", method = { RequestMethod.GET })
	public ModelAndView getAdmin(@ModelAttribute("loginContext") LoginContext loginContext, Model model) {
		try {
			List<UserModel> users = new ArrayList<UserModel>();
			ModelAndView modelAndView = new ModelAndView();
			List<Map<String, Object>> userInfo = loginService.displayAllUsers();
			for (Map<String, Object> map : userInfo) {
				UserModel user = new UserModel();
				int loginId = (Integer) map.get("login_id");
				user.setLoginId(loginId);
				String userName = (String) map.get("username");
				user.setUserName(userName);
				String email = (String) map.get("email");
				user.setEmailId(email);
				String role = (String) map.get("role");
				user.setRole(role);
				users.add(user);
			}
			int totalNewEmails = mailService.countNewEmails();
			model.addAttribute("newEmailsCount", totalNewEmails);
			model.addAttribute("users", users);
			modelAndView.setViewName("admin/displayusers");
			return modelAndView;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/user", method = { RequestMethod.GET })
	public ModelAndView getUser(@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			loginContext.setSearchUser(null);
			ModelAndView modelAndView = buildUserDetailsView(loginContext.getUserName());
			modelAndView.addObject("userName", loginContext.getUserName());
			return modelAndView;
		} catch (ServiceException e) {
			// TODO: error page????
		}
		return null;
	}

	// TODO: can go in service??
	public ModelAndView buildUserDetailsView(String userName) throws ServiceException {
		ModelAndView modelAndView = new ModelAndView();
		if (StringUtils.isNotEmpty(userName)) {
			int loginId = profileService.getLoginIdUsingUserName(userName);
			List<Map<String, Object>> friendsRequest = manageFriendsService.displayAllFriendsRequest(loginId);
			List<Map<String, String>> friendRequestDetail = new ArrayList<Map<String, String>>();
			for (Map<String, Object> requests : friendsRequest) {
				Map<String, String> friendRequestAndStatus = new HashMap<String, String>();
				int userWhoSentRequest = (Integer) requests.get("from_user");
				String senderUserName = loginService.getUserNameUsingLoginId(userWhoSentRequest);
				int toWhomRequestIsSent = (Integer) requests.get("to_user");
				String receiverUserName = loginService.getUserNameUsingLoginId(toWhomRequestIsSent);
				int acceptStatus = (Integer) requests.get("accept");
				friendRequestAndStatus.put("sender", senderUserName);
				friendRequestAndStatus.put("receiver", receiverUserName);
				friendRequestAndStatus.put("acceptStatus", String.valueOf(acceptStatus));
				friendRequestDetail.add(friendRequestAndStatus);
			}
			List<Map<String, Object>> friendsLoginId = manageFriendsService.displayFriends(loginId);
			List<String> friends = new ArrayList<String>();
			for (Map<String, Object> map : friendsLoginId) {
				int fromReq = (Integer) map.get("from_user");
				int toReq = (Integer) map.get("to_user");
				String frnRequestFrom = loginService.getUserNameUsingLoginId(fromReq);
				String toFrnRequest = loginService.getUserNameUsingLoginId(toReq);
				if (!friends.contains(frnRequestFrom)) {
					friends.add(frnRequestFrom);
				}
				if (!friends.contains(toFrnRequest)) {
					friends.add(toFrnRequest);
				}
				if (friends.contains(userName)) {
					friends.remove(userName);
				}
			}
			String profilepicture = loginService.displayMyProfilePicture(Short.valueOf(String.valueOf(loginId)));
			modelAndView.addObject("profilePicture", profilepicture);
			List<Map<String, Object>> statusPost = loginService.displayAllPostsOnMyProfile(loginId);
			for (Map<String, Object> map : statusPost) {
				Map<String, Object> backupMap = new HashMap<String, Object>();
				int fromUserId = (Integer) map.get("from_user");
				int toUserId = (Integer) map.get("to_user");
				String status = (String) map.get("status");
				String image = (String) map.get("image");
				String userImgThumbnail = "";
				try {
					if(StringUtils.isNotBlank(image)){
						userImgThumbnail = ThumbnailGenerator.generateThumbnail(image);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
				int currentIndex = statusPost.indexOf(map);
				String fromUser = loginService.getUserNameUsingLoginId(fromUserId);
				String toUser = loginService.getUserNameUsingLoginId(toUserId);
				backupMap.put("from_user", fromUser);
				backupMap.put("to_user", toUser);
				backupMap.put("status", status);
				backupMap.put("thumbnail", userImgThumbnail);
				statusPost.set(currentIndex, backupMap);
			}
			int totalFriendRequest = manageFriendsService.countTotalFriendRequest(loginId);
			modelAndView.addObject("totalFriendRequest", totalFriendRequest);
			modelAndView.addObject("statusPost", statusPost);
			modelAndView.addObject("friendRequest", friendRequestDetail);
			modelAndView.addObject("friends", friends);
			modelAndView.addObject("userName", userName);
			int imageId = loginService.getImageIdUsingLoginId(loginId);
			if (imageId > 0) {
				int likeCount = countLikesOnPhoto(imageId);
				modelAndView.addObject("likeCount", likeCount);
				List<CommentsModel> commentsModelList = manageCommentsInImage(imageId);
				modelAndView.addObject("commentsModelList", commentsModelList);
			}
			modelAndView.setViewName("profile/profile");
		}
		return modelAndView;
	}

	// TODO: can go in service?
	public int countLikesOnPhoto(int imageId) throws ServiceException {
		return loginService.countLikesOnPhoto(imageId);
	}

	public List<CommentsModel> manageCommentsInImage(int imageId) throws ServiceException {
		List<Map<String, Object>> imageComments = loginService.displayAllImageComments(imageId);
		List<CommentsModel> commentsModelList = new ArrayList<CommentsModel>();
		for (Map<String, Object> map : imageComments) {
			CommentsModel commentsModel = new CommentsModel();
			commentsModel.setComment((String) map.get("comments"));
			commentsModel.setFrom_user((int) map.get("from_user"));
			commentsModel.setTo_user((int) map.get("to_user"));
			String userName = loginService.getUserNameUsingLoginId((int) map.get("from_user"));
			commentsModel.setUserName(userName);
			commentsModelList.add(commentsModel);
		}
		return commentsModelList;
	}

	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	public ModelAndView goToWelcomePage() {
		return new ModelAndView("redirect:/");
	}

	@RequestMapping(value = "/logoutUser", method = RequestMethod.GET)
	public ModelAndView logoutUser(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("profile/logout");
		return modelAndView;
	}
	@RequestMapping(value = "/denyAccess", method = RequestMethod.GET)
	public ModelAndView invalidCredential(ModelMap modelMap) {
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("error/denyaccess");
		return modelAndView;
	}

}
