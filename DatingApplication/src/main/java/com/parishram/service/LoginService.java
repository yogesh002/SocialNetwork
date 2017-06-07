package com.parishram.service;

import java.util.List;
import java.util.Map;

import com.parishram.model.CommentsModel;
import com.parishram.service.exception.ServiceException;

public interface LoginService {
	public String displayMyProfilePicture(int loginId) throws ServiceException;
	public int getProfilePicImageId(int login_id) throws ServiceException;
	public int getImageIdUsingLoginId(int loginId) throws ServiceException;
	public void deleteImageUsingImageId(int imageId) throws ServiceException;
	public List<Map<String, Object>> displayAllPostsOnMyProfile(int loginId) throws ServiceException;
	public String getUserNameUsingLoginId(int loginId) throws ServiceException;
	public void postStatusInMyProfile(int from_user, int to_user, String status) throws ServiceException;
	public void postCommentInImage(CommentsModel commentsModel) throws ServiceException;
	public void likePhoto(int from_user, int to_user, int imageId) throws ServiceException;
	public int countLikesOnPhotoByAnUser(int from_user, int to_user, int imageId) throws ServiceException;
	public List<Map<String, Object>> getLikesDetail(int imageId) throws ServiceException;
	public int countLikesOnPhoto(int imageId) throws ServiceException;
	public List<Map<String, Object>> displayAllImageComments(int imageId) throws ServiceException;
	public List<Map<String, Object>> displayMyWall(int loginId) throws ServiceException;
	public List<Map<String, Object>> displayAllUsers() throws ServiceException;
	public void deleteUser(int login_id) throws ServiceException;
	public void editRoleOfUser(int roleId, int login_id) throws ServiceException;
	public String getRole(int loginId) throws ServiceException;

}
