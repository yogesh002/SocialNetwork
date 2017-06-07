package com.parishram.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.node.TextNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.parishram.common.Constant;
import com.parishram.json.response.CommentsResponse;
import com.parishram.json.response.PhotoJsonHandler;
import com.parishram.model.CommentsModel;
import com.parishram.model.LoginContext;
import com.parishram.model.StatusModel;
import com.parishram.model.UserModel;
import com.parishram.service.LoginService;
import com.parishram.service.ManageFriendsService;
import com.parishram.service.ProfileService;
import com.parishram.service.exception.ServiceException;
import com.parishram.utils.ThumbnailGenerator;
import com.parishram.utils.UploadPictures;

@SessionAttributes({ "loginContext" })
@Controller
public class ProfileController {
	private static Logger LOGGER = Logger.getLogger(ProfileController.class);

	@Autowired
	private ProfileService profileService;

	@Autowired
	private LoginService loginService;

	@Autowired
	private Gson gson;

	//private StatusModel statusModel = null;

	@Autowired
	private ManageFriendsService manageFriendsService;

	@RequestMapping(value = "/addUpdateProfilePicture", method = RequestMethod.POST)
	@ResponseBody
	public String addUpdateProfilePicture(HttpServletRequest request,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		String profilePic = "";
		try {
			List<String> images = UploadPictures.uploadPictures(request);
			String uploadedImage = images.get(0);
			int loginId = profileService.getLoginIdUsingUserName(loginContext.getUserName());
			profilePic = loginService.displayMyProfilePicture(loginId);
			if (StringUtils.isNotBlank(profilePic)) {
				profileService.updateImage(uploadedImage, loginId);
			} else {
				profileService.uploadImage(uploadedImage, loginId, Boolean.TRUE, -1);
			}
			loginContext.setProfilePicture(profilePic);
			return uploadedImage;
		} catch (IOException | ServiceException | FileUploadException exception) {
			LOGGER.error("Unable to save the image in the database." + exception.getMessage(), exception);
			return "";
		}
	}

	@RequestMapping(value = "/deleteImage", method = RequestMethod.POST)
	@ResponseBody
	public String deleteSelectedImage(@ModelAttribute("loginContext") LoginContext loginContext) {
		String status = "";
		try {
			int loginId = profileService.getLoginIdUsingUserName(loginContext.getUserName());
			int imageId = loginService.getImageIdUsingLoginId(loginId);
			if (imageId > 0) {
				loginService.deleteImageUsingImageId(imageId);
			}
			status = Constant.SUCCESS;
		} catch (ServiceException exception) {
			status = Constant.FAILURE;
			LOGGER.error("Unable to delete the picture." + exception.getMessage(), exception);
		}
		return status;
	}

	@RequestMapping(value = "/likeController", method = RequestMethod.POST)
	@ResponseBody
	public int likePhoto(@ModelAttribute("loginContext") LoginContext loginContext,
			@RequestBody CommentsResponse commentsResponse) {
		try {
			int search_loginId = -1;
			int imageId = -1;
			int loginId = -1;
			String search_useName = loginContext.getSearchUser();
			String userName = loginContext.getUserName();
			if (StringUtils.isNotBlank(userName)) {
				loginId = profileService.getLoginIdUsingUserName(userName);
				if (search_loginId > 0) {
					imageId = loginService.getProfilePicImageId(loginId);
				}
			}
			if (StringUtils.isNotBlank(search_useName)) {
				search_loginId = profileService.getLoginIdUsingUserName(search_useName);
				imageId = loginService.getProfilePicImageId(search_loginId);
			}
			if (search_loginId != -1 && loginId != -1 && imageId != -1) {
				int likesByUser = loginService.countLikesOnPhotoByAnUser(loginId,search_loginId, imageId);
				if (likesByUser < 1) {
					loginService.likePhoto(loginId, search_loginId, imageId);
					return loginService.countLikesOnPhoto(imageId);
				}
			}
			return -1;
		} catch (ServiceException exception) {
			LOGGER.error("Unable to like the picture" + exception.getMessage(), exception);
			return -1;
		}

	}

	@RequestMapping(value = "/showFriendsWhoLiked", method = RequestMethod.POST)
	public @ResponseBody String getLikeAndTakeLike(@RequestBody CommentsResponse response,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			int imageId = -1;
			int searchUserId = -1;
			String searchedUser = loginContext.getSearchUser();
			String userName = loginContext.getUserName();
			int loginId = profileService.getLoginIdUsingUserName(userName);
			if (StringUtils.isNotBlank(searchedUser)) {
				searchUserId = profileService.getLoginIdUsingUserName(searchedUser);
			}
			ArrayList<PhotoJsonHandler> hitLikeAndReceiveLike = new ArrayList<PhotoJsonHandler>();
			if (StringUtils.isNotBlank(searchedUser)) {
				imageId = loginService.getProfilePicImageId(searchUserId);
			} else {
				imageId = loginService.getProfilePicImageId(loginId);
			}
			List<Map<String, Object>> likeDetails = loginService.getLikesDetail(imageId);
			for (Map<String, Object> map : likeDetails) {
				PhotoJsonHandler photoLikeHandler = new PhotoJsonHandler();
				int from_user = (Integer) map.get("from_user");
				int to_user = (Integer) map.get("to_user");
				if (from_user != -1) {
					String userWhoLiked = loginService.getUserNameUsingLoginId(from_user);
					photoLikeHandler.setLikedBy(userWhoLiked);
				}
				if (to_user != -1) {
					String userWhoGotLike = loginService.getUserNameUsingLoginId(to_user);
					photoLikeHandler.setLikedOn(userWhoGotLike);
				}
				hitLikeAndReceiveLike.add(photoLikeHandler);
			}
			return gson.toJson(hitLikeAndReceiveLike);
		} catch (ServiceException exception) {
			LOGGER.error("Exception is thrown while retrieving list of people who liked the picture. "
					+ exception.getMessage(), exception);
			return "";
		}

	}

	@RequestMapping(value = "/searchUser", method = RequestMethod.GET)
	public ModelAndView searchUser(@ModelAttribute("userName") String usernameFromFlashAttribute,
			@ModelAttribute("loginContext") LoginContext loginContext, Model model,
			@RequestParam(value = "searchUser", required = false) String userName) {
		StatusModel statusModel = new StatusModel();
		statusModel.setUserName(userName);
		// loginContext.setSearchUser(userName);
		try {
			if (StringUtils.isNotBlank(userName)) {
				if (userName.equalsIgnoreCase(loginContext.getUserName())) {
					return new ModelAndView("redirect:/user");
				}
				int searchedPersonId = profileService.getLoginIdUsingUserName(userName);
				int currentUserLoginId = profileService.getLoginIdUsingUserName(loginContext.getUserName());
				List<Map<String, Object>> checkFriendship = manageFriendsService.checkIfFriendExist(currentUserLoginId,
						searchedPersonId);
				if (!checkFriendship.isEmpty()) {
					Map<String, Object> checkFriendRequest = checkFriendship.get(0);
					int fromUser = (Integer) checkFriendRequest.get("from_user");
					int toUser = (Integer) checkFriendRequest.get("to_user");
					int accept = (Integer) checkFriendRequest.get("accept");
					String friendWhoSentRequest = loginService.getUserNameUsingLoginId(fromUser);
					String requestSentTo = loginService.getUserNameUsingLoginId(toUser);
					Map<String, String> isFriend = new HashMap<String, String>();
					isFriend.put("from_user", friendWhoSentRequest);
					isFriend.put("to_user", requestSentTo);
					isFriend.put("accept", String.valueOf(accept));
					model.addAttribute("isFriend", isFriend);
				}
				model.addAttribute("searchUser", userName);
			}
			return buildUserDetailsView(userName);
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@RequestMapping(value = "/findUser", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView findUser(@RequestBody TextNode userName) {
		try {
			return buildUserDetailsView(userName.asText());
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			return null;

		}
	}

	// TODO: can go in service??
	public ModelAndView buildUserDetailsView(String userName) throws ServiceException {
		ModelAndView modelAndView = new ModelAndView();
		if (StringUtils.isNotEmpty(userName)) {
			int loginId = profileService.getLoginIdUsingUserName(userName);
			List<Map<String, Object>> friendsRequest = manageFriendsService.displayAllFriendsRequest(loginId);
			List<Map<String, Integer>> friendRequestDetail = new ArrayList<Map<String, Integer>>();
			for (Map<String, Object> requests : friendsRequest) {
				Map<String, Integer> friendRequestAndStatus = new HashMap<String, Integer>();
				int userWhoSentRequest = (Integer) requests.get("from_user");
				int toWhomRequestIsSent = (Integer) requests.get("to_user");
				int acceptStatus = (Integer) requests.get("accept");
				friendRequestAndStatus.put("sender", userWhoSentRequest);
				friendRequestAndStatus.put("receiver", toWhomRequestIsSent);
				friendRequestAndStatus.put("acceptStatus", acceptStatus);
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
			String profilepicture = loginService.displayMyProfilePicture(loginId);
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
					if (StringUtils.isNotBlank(image)) {
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

	@RequestMapping(value = "/manageFriends/addFriends", method = RequestMethod.POST)
	public @ResponseBody String sendFriendRequest(@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			int searchUserLoginId = profileService.getLoginIdUsingUserName(loginContext.getSearchUser());
			int loginId = profileService.getLoginIdUsingUserName(loginContext.getUserName());
			manageFriendsService.sendFriendRequest(loginId, searchUserLoginId, 0);
			return Constant.SUCCESS;
		} catch (ServiceException serviceException) {
			LOGGER.error("Unable to send friend request to the user." + serviceException.getMessage(),
					serviceException);
			return Constant.FAILURE;
		}

	}

	@RequestMapping(value = "/manageFriends/cancelFriendRequestSent", method = RequestMethod.POST)
	public @ResponseBody String cancelSentFriendRequest(@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			int searchUserLoginId = profileService.getLoginIdUsingUserName(loginContext.getSearchUser());
			int loginId = profileService.getLoginIdUsingUserName(loginContext.getUserName());
			manageFriendsService.cancelFriendRequest(loginId, searchUserLoginId);
			return Constant.SUCCESS;
		} catch (ServiceException serviceException) {
			LOGGER.error("Unable to send friend request to the user." + serviceException.getMessage(),
					serviceException);
			return Constant.FAILURE;
		}

	}

	@RequestMapping(value = "/manageFriends/unFriendUser", method = RequestMethod.POST)
	public @ResponseBody String unFriendUser(@RequestBody UserModel unfrienduser,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			int userToUnfriend = profileService.getLoginIdUsingUserName(unfrienduser.getUserName());
			int loginId = profileService.getLoginIdUsingUserName(loginContext.getUserName());
			manageFriendsService.unfriendUser(userToUnfriend, loginId);
			return Constant.SUCCESS;
		} catch (ServiceException serviceException) {
			LOGGER.error("Unable to unfriend user" + serviceException.getMessage(), serviceException);
			return Constant.FAILURE;
		}

	}

	@RequestMapping(value = "/poststatus", method = RequestMethod.POST)
	public @ResponseBody StatusModel postStatus(@RequestBody String postedStatus,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		// TODO: validate the status null, trim..
		StatusModel responseStatusModel = new StatusModel();
		try {
			String userName = loginContext.getUserName();
			int loginId = profileService.getLoginIdUsingUserName(userName);
			String thumbnail = "";
			if (StringUtils.isNotBlank(loginContext.getSearchUser())) {
				int searchUserLoginId = profileService.getLoginIdUsingUserName(loginContext.getSearchUser());
				loginService.postStatusInMyProfile(loginId, searchUserLoginId, postedStatus);
			} else {
				loginService.postStatusInMyProfile(loginId, loginId, postedStatus);
			}
			String image = loginService.displayMyProfilePicture(loginId);
			if (StringUtils.isNotBlank(image)) {
				thumbnail = ThumbnailGenerator.generateThumbnail(image);
			}
			responseStatusModel.setPostedStatus(postedStatus);
			responseStatusModel.setImage(thumbnail);

		} catch (Exception ex) {
			// TODO: error??
		}
		return responseStatusModel;
	}

	@RequestMapping(value = "/postimgcomment", method = RequestMethod.POST, consumes = { "application/json" })
	public @ResponseBody String postCommentInImage(@RequestBody CommentsResponse commentsResponse,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		// TODO: validate the status null, trim..
		try {
			// can create abstract class for repeated code
			String currentUser = loginContext.getUserName();
			int currentUserId = profileService.getLoginIdUsingUserName(currentUser);
			String searchUserName = loginContext.getSearchUser();
			int imageId = -1;
			int searchUserId = -1;
			if (StringUtils.isNotBlank(searchUserName)) {
				searchUserId = profileService.getLoginIdUsingUserName(searchUserName);
				imageId = loginService.getProfilePicImageId(searchUserId);
			} else {
				imageId = loginService.getProfilePicImageId(currentUserId);
			}
			CommentsModel commentsModel = new CommentsModel();
			commentsModel.setComment(commentsResponse.getComment());
			commentsModel.setFrom_user(currentUserId);
			if (searchUserId != -1) {
				commentsModel.setTo_user(searchUserId);
			} else {
				commentsModel.setTo_user(currentUserId);
			}
			commentsModel.setImage_id(imageId);
			commentsModel.setStatus_id(0);
			loginService.postCommentInImage(commentsModel);
			return Constant.SUCCESS;
		} catch (Exception ex) {
			// TODO: error??
			return Constant.FAILURE;
		}
	}

	@RequestMapping(value = "/manageFriends/acceptFriendRequest", method = RequestMethod.POST)
	public @ResponseBody String acceptFriendRequest(@RequestBody TextNode userWhoSentFriendRequest,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			int from_user = profileService.getLoginIdUsingUserName(userWhoSentFriendRequest.asText());
			int to_user = profileService.getLoginIdUsingUserName(loginContext.getUserName());
			manageFriendsService.acceptFriendRequest(from_user, to_user);
			return Constant.SUCCESS;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constant.FAILURE;
		}

	}

	@RequestMapping(value = "/manageFriends/rejectFriendRequest", method = RequestMethod.POST)
	public @ResponseBody String rejectFriendRequest(@RequestBody TextNode userWhoSentFriendRequest,
			@ModelAttribute("loginContext") LoginContext loginContext) {
		try {
			int from_user = profileService.getLoginIdUsingUserName(userWhoSentFriendRequest.asText());
			int to_user = profileService.getLoginIdUsingUserName(loginContext.getUserName());
			manageFriendsService.rejectFriendRequest(from_user, to_user);
			return Constant.SUCCESS;
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Constant.FAILURE;
		}

	}

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView userHomePage(@ModelAttribute("loginContext") LoginContext loginContext)
			throws javax.mail.MessagingException {

		ModelAndView modelAndView = new ModelAndView();
		String userName = loginContext.getUserName();
		int loginId;
		try {
			loginId = profileService.getLoginIdUsingUserName(userName);
			List<Map<String, Object>> wallPosts = loginService.displayMyWall(loginId);
			List<Map<String, Object>> wallPostsWithImage = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : wallPosts) {
				String status = (String) map.get("status");
				String image = (String) map.get("image");
				String thumbnail = "";
				if (StringUtils.isNotBlank(image)) {
					thumbnail = ThumbnailGenerator.generateThumbnail(image);
				}
				Map<String, Object> imageMapsStatus = new HashMap<String, Object>();
				imageMapsStatus.put("status", status);
				imageMapsStatus.put("image", thumbnail);
				wallPostsWithImage.add(imageMapsStatus);
			}
			modelAndView.addObject("wallPosts", wallPostsWithImage);
			modelAndView.addObject("user", userName);
			modelAndView.setViewName("profile/wallpost");
			return modelAndView;
		} catch (ServiceException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
