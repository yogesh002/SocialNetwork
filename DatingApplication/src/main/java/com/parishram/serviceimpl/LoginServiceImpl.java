package com.parishram.serviceimpl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parishram.dao.LoginDao;
import com.parishram.dto.CommentsDTO;
import com.parishram.model.CommentsModel;
import com.parishram.service.LoginService;
import com.parishram.service.exception.PersistentException;
import com.parishram.service.exception.ServiceException;

@Component
public class LoginServiceImpl implements LoginService {
	private static Logger LOGGER = Logger.getLogger(LoginServiceImpl.class);

	@Autowired
	private LoginDao loginDao;

	@Override
	public String displayMyProfilePicture(int loginId) throws ServiceException {
		try {
			String image = loginDao.displayMyProfilePicture(loginId);
			return image;
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to fetch image for this user from the database" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void postStatusInMyProfile(int from_user, int to_user, String status) throws ServiceException {
		try {
			loginDao.postStatusInMyProfile(from_user, to_user, status);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to post status in the profile." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException("Unable to post status in the profile." + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public List<Map<String, Object>> displayAllPostsOnMyProfile(int loginId) throws ServiceException {
		try {
			return loginDao.displayAllPostsOnMyProfile(loginId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to display status posts in the profile." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException("Unable display status posts in the profile." + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void postCommentInImage(CommentsModel commentsModel) throws ServiceException {
		CommentsDTO commentsDTO = new CommentsDTO();
		commentsDTO.setComment(commentsModel.getComment());
		commentsDTO.setFrom_user(commentsModel.getFrom_user());
		commentsDTO.setTo_user(commentsModel.getTo_user());
		commentsDTO.setImage_id(commentsModel.getImage_id());
		commentsDTO.setStatus_id(commentsModel.getStatus_id());
		try {
			loginDao.postCommentInImage(commentsDTO);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to post your comment." + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to post your comment." + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public int getProfilePicImageId(int login_id) throws ServiceException {
		try {
			return loginDao.getImageIdOfProfilePic(login_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to get image id for the image you selected." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"Unable to get image id for the image you selected." + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public List<Map<String, Object>> displayAllImageComments(int imageId) throws ServiceException {
		try {
			return loginDao.displayAllCommentsInPicture(imageId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve comments for the selected picture" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"Unable to retrieve comments for the selected picture" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public int getImageIdUsingLoginId(int loginId) throws ServiceException {
		try {
			return loginDao.getImageIdUsingLoginId(loginId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve image. Please try again later." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"Unable to retrieve image. Please try again later." + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public String getUserNameUsingLoginId(int loginId) throws ServiceException {
		try {
			return loginDao.getUserNameUsingLoginId(loginId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve user name from the login Id provided." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"Unable to retrieve user name from the login Id provided." + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void deleteImageUsingImageId(int imageId) throws ServiceException {
		try {
			loginDao.deleteImageUsingImageId(imageId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to delete image from database." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException("Unable to delete image from database." + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void likePhoto(int from_user, int to_user, int imageId) throws ServiceException {
		try {
			loginDao.likePhoto(from_user, to_user, imageId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to like the photo" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to like the picture." + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public List<Map<String, Object>> getLikesDetail(int imageId) throws ServiceException {
		try {
			return loginDao.getLikesDetail(imageId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to count likes on the photo" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to count likes on the photo" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public int countLikesOnPhoto(int imageId) throws ServiceException {
		try {
			return loginDao.countLikesOnPhoto(imageId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to count likes on the photo" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to count likes on the photo" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public List<Map<String, Object>> displayMyWall(int to_user) throws ServiceException {
		try {
			return loginDao.displayMyWall(to_user);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to fetch wall posts." + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to fetch wall posts." + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public List<Map<String, Object>> displayAllUsers() throws ServiceException {
		try {
			return loginDao.displayAllUsers();
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to display all users" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to display all users" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void deleteUser(int login_id) throws ServiceException {
		try {
			loginDao.deleteUser(login_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to delete user" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to delete user" + persistentException.getMessage(), persistentException);
		}
	}

	@Override
	public void editRoleOfUser(int roleId, int login_id) throws ServiceException {
		try {
			loginDao.editRoleOfUser(roleId, login_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to edit user's role" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to edit user's role" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public String getRole(int loginId) throws ServiceException {
		try {
			return loginDao.getRole(loginId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to get role" + persistentException.getMessage(), persistentException);
			throw new ServiceException("Unable to get role" + persistentException.getMessage(), persistentException);
		}
	}

	@Override
	public int countLikesOnPhotoByAnUser(int from_user, int to_user, int imageId) throws ServiceException {
		try {
			return loginDao.countLikesOnPhotoByAnUser(from_user, to_user, imageId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unale to determine if the user liked the picture" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"Unale to determine if the user liked the picture" + persistentException.getMessage(),
					persistentException);
		}
	}
}
