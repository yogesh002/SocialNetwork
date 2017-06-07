package com.parishram.dao;

import java.util.List;
import java.util.Map;

import com.parishram.dto.CommentsDTO;
import com.parishram.service.exception.PersistentException;

public interface LoginDao {
	// Status
	public List<Map<String, Object>> displayAllPostsOnMyProfile(int loginId) throws PersistentException;

	public void postStatusInMyProfile(int from_user, int to_user, String status) throws PersistentException;

	// Profile Pic
	public String displayMyProfilePicture(int loginId) throws PersistentException;

	public int getImageIdOfProfilePic(int login_id) throws PersistentException;

	public int getImageIdUsingLoginId(int loginId) throws PersistentException;

	public List<Map<String, Object>> displayAllCommentsInPicture(int imageId) throws PersistentException;

	public String getUserNameUsingLoginId(int loginId) throws PersistentException;

	public void deleteImageUsingImageId(int loginId) throws PersistentException;

	// like
	public void likePhoto(int from_user, int to_user, int imageId) throws PersistentException;

	public List<Map<String, Object>> getLikesDetail(int imageId) throws PersistentException;

	public int countLikesOnPhoto(int imageId) throws PersistentException;
	
	public int countLikesOnPhotoByAnUser(int from_user, int to_user, int imageId) throws PersistentException;

	public void postCommentInImage(CommentsDTO commentsDTO) throws PersistentException;

	// wall
	public List<Map<String, Object>> displayMyWall(int loginId) throws PersistentException;

	// ADMIN
	public List<Map<String, Object>> displayAllUsers() throws PersistentException;

	public void deleteUser(int login_id) throws PersistentException;
	
	public void editRoleOfUser(int roleId, int login_id) throws PersistentException;
	
	public String getRole(int loginId) throws PersistentException;
}
