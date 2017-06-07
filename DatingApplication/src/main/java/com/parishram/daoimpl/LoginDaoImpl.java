package com.parishram.daoimpl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.parishram.dao.LoginDao;
import com.parishram.database.utils.LoginQueries;
import com.parishram.dto.CommentsDTO;
import com.parishram.service.exception.PersistentException;

@Repository
public class LoginDaoImpl implements LoginDao {
	private static Logger LOGGER = Logger.getLogger(LoginDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Map<String, Object>> displayAllPostsOnMyProfile(int to_user) throws PersistentException {
		try {
			return jdbcTemplate.queryForList(LoginQueries.VIEW_ALL_STATUS.getValue(), to_user);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve posts in the profile. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve status posts in the profile.", exception);
		}
	}

	@Override
	public List<Map<String, Object>> displayMyWall(int to_user) throws PersistentException {
		try {
			return jdbcTemplate.queryForList(LoginQueries.SHOW_WALL_PAGE.getValue(), to_user);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve wall posts. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to fetch wall posts.", exception);
		}
	}

	@Override
	public String displayMyProfilePicture(int loginId) throws PersistentException {
		try {
			List<String> result = jdbcTemplate.query(LoginQueries.RETRIEVE_PROFILE_IMAGE.getValue(),
					new Object[] { loginId }, new RowMapper<String>() {
						@Override
						public String mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
							return resultSet.getString("image");
						}
					});
			return !result.isEmpty() ? result.get(0) : "";
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve profile image from the database." + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve image from the databse.", exception);
		}
	}

	@Override
	public void postStatusInMyProfile(int from_user, int to_user, String status) throws PersistentException {
		try {
			jdbcTemplate.update(LoginQueries.POST_STATUS_IN_MY_PROFILE.getValue(), from_user, to_user, status);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve to and from status in the profile. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve to and from status in the profile.", exception);
		}
	}

	@Override
	public void postCommentInImage(CommentsDTO commentsDTO) throws PersistentException {
		try {
			jdbcTemplate.update(LoginQueries.POST_COMMENT_IN_PIC.getValue(), commentsDTO.getFrom_user(),
					commentsDTO.getTo_user(), commentsDTO.getComment(), commentsDTO.getImage_id(),
					commentsDTO.getStatus_id());
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to post comment in the image. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to post comment in the image.", exception);
		}
	}

	@Override
	public int getImageIdOfProfilePic(int login_id) throws PersistentException {
		try {
			int imageId = jdbcTemplate.queryForObject(LoginQueries.RETRIEVE_PROFILE_PIC_IMAGE_ID.getValue(),
					new Object[] { login_id }, Integer.class);
			return imageId;

		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve image id for selected image. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve image id for selected image.", exception);
		}
	}

	@Override
	public List<Map<String, Object>> displayAllCommentsInPicture(int imageId) throws PersistentException {
		try {
			return jdbcTemplate.queryForList(LoginQueries.RETRIEVE_ALL_IMAGE_COMMENTS.getValue(), imageId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve posts in the profile. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve status posts in the profile.", exception);
		}
	}

	@Override
	public int getImageIdUsingLoginId(int loginId) throws PersistentException {
		try {
			List<Integer> result = jdbcTemplate.query(LoginQueries.RETRIEVE_IMAGE_ID_USING_LOGIN_ID.getValue(),
					new Object[] { loginId }, new RowMapper<Integer>() {
						@Override
						public Integer mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
							return resultSet.getInt(1);
						}

					});
			return (!result.isEmpty()) ? result.get(0) : -1;
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve image id for the particular login id. " + exception.getMessage(),
					exception);
			throw new PersistentException("Unable to retrieve image id for the particular login id. ", exception);
		}
	}

	@Override
	public String getUserNameUsingLoginId(int loginId) throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(LoginQueries.RETRIEVE_USERNAME_USING_LOGIN_ID.getValue(),
					new Object[] { loginId }, String.class);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve username with the login id provided. " + exception.getMessage(),
					exception);
			throw new PersistentException("Unable to retrieve username with the login id provided.", exception);
		}
	}

	@Override
	public void deleteImageUsingImageId(int imageId) throws PersistentException {
		try {
			jdbcTemplate.update(LoginQueries.DELETE_PROFILE_IMAGE_USING_IMAGE_ID.getValue(), imageId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to delete image from the databse." + exception.getMessage(), exception);
			throw new PersistentException("Unable to delete image from the databse.", exception);
		}
	}

	@Override
	public void likePhoto(int from_user, int to_user, int imageId) throws PersistentException {
		try {
			jdbcTemplate.update(LoginQueries.LIKE_PHOTO.getValue(), from_user, to_user, imageId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to like the picture." + exception.getMessage(), exception);
			throw new PersistentException("Unable to like the photo.", exception);
		}
	}

	@Override
	public List<Map<String, Object>> getLikesDetail(int imageId) throws PersistentException {
		try {
			return jdbcTemplate.queryForList(LoginQueries.SHOW_FRIENDS_WHO_LIKED.getValue(), imageId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve list of people who like the picture. " + exception.getMessage(),
					exception);
			throw new PersistentException("Unable to retrieve list of people who like the picture.", exception);
		}
	}

	@Override
	public int countLikesOnPhoto(int imageId) throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(LoginQueries.COUNT_LIKES_ON_PHOTOS.getValue(), new Object[] { imageId },
					Integer.class);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to count likes on the picture." + exception.getMessage(), exception);
			throw new PersistentException("Unable to count likes on the photo.", exception);
		}
	}

	@Override
	public List<Map<String, Object>> displayAllUsers() throws PersistentException {
		try {
			return jdbcTemplate.queryForList(LoginQueries.SHOW_ALL_USERS.getValue());
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve all users " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve all users ", exception);
		}
	}

	@Override
	public void deleteUser(int login_id) throws PersistentException {
		try {
			jdbcTemplate.update(LoginQueries.DELETE_USER.getValue(), login_id);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to delete the user." + exception.getMessage(), exception);
			throw new PersistentException("Unable to delete the user.", exception);
		}

	}

	@Override
	public void editRoleOfUser(int roleId, int login_id) throws PersistentException {
		try {
			jdbcTemplate.update(LoginQueries.EDIT_USER_ROLE.getValue(), roleId, login_id);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to edit the role of user" + exception.getMessage(), exception);
			throw new PersistentException("Unable to edit the role of user", exception);
		}

	}

	@Override
	public String getRole(int loginId) throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(LoginQueries.GET_ROLE.getValue(), new Object[] { loginId },
					String.class);

		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve the role " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve the role.", exception);
		}
	}

	@Override
	public int countLikesOnPhotoByAnUser(int from_user, int to_user, int imageId) throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(LoginQueries.COUNT_LIKES_ON_PHOTO_BY_USER.getValue(),
					new Object[] { from_user, to_user, imageId }, Integer.class);

		} catch (DataAccessException exception) {
			LOGGER.error("Unable to determine if the user liked the picture. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to determine if the user liked the picture.", exception);
		}
	}
}
