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

import com.parishram.dao.AlbumDao;
import com.parishram.database.utils.AlbumQueries;
import com.parishram.model.PhotoAlbumModel;
import com.parishram.service.exception.PersistentException;

@Repository
public class AlbumDaoImpl implements AlbumDao {
	private static Logger LOGGER = Logger.getLogger(AlbumDaoImpl.class);

	private JdbcTemplate jdbcTemplate;

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}


	@Override
	public void deleteAlbum(int albumId, int login_id) throws PersistentException {
		try {
			jdbcTemplate.update(AlbumQueries.DELETE_ALBUM.getValue(), albumId, login_id);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to delete the requested album. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to delete the requested album", exception);
		}

	}

	@Override
	public void createAlbum(int login_id, String albumName) throws PersistentException {
		try {
			jdbcTemplate.update(AlbumQueries.CREATE_ALBUM.getValue(), albumName, login_id);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to create album with entered name " + exception.getMessage(), exception);
			throw new PersistentException("Unable to create album with entered name", exception);
		}

	}

	@Override
	public List<Map<String, Object>> retrieveImagesFromAlbum(int albumId, int login_id) throws PersistentException {
		try {
			return jdbcTemplate.queryForList(AlbumQueries.RETRIEVE_IMAGES.getValue(), albumId, login_id);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve images from the selected album. " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve images from the selected album.", exception);
		}
	}

	@Override
	public void editAlbumName(String album, int albumId) throws PersistentException {
		try {
			jdbcTemplate.update(AlbumQueries.EDIT_ALBUM.getValue(), album, albumId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to edit album name " + exception.getMessage(), exception);
			throw new PersistentException("Unable to edit album name", exception);
		}
	}

	@Override
	public void rateAlbum(int rating, int albumId) throws PersistentException {
		try {
			jdbcTemplate.update(AlbumQueries.RATE_ALBUM.getValue(), rating, albumId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to rate album " + exception.getMessage(), exception);
			throw new PersistentException("Unable to rate album", exception);
		}
	}

	@Override
	public int getAlbumRating(int albumId, int login_id) throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(
					AlbumQueries.GET_ALBUM_RATING.getValue(), new Object[] { albumId, login_id }, Integer.class);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve album ratin " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve album ratin", exception);
		}
	}


	@Override
	public int getAlbumIdUsingAlbumName(String albumName) throws PersistentException {
		try {
			return jdbcTemplate.queryForObject(
					AlbumQueries.GET_ALBUM_ID.getValue(), new Object[] {albumName }, Integer.class);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to retrieve album ratin " + exception.getMessage(), exception);
			throw new PersistentException("Unable to retrieve album ratin", exception);
		}
	}


	@Override
	public void updateAlbumId(int albumId, int loginId) throws PersistentException {
		try {
			jdbcTemplate.update(AlbumQueries.UPDATE_ALBUM_ID.getValue(), albumId, loginId);
		} catch (DataAccessException exception) {
			LOGGER.error("Unable to update albumId " + exception.getMessage(), exception);
			throw new PersistentException("Unable to rate album", exception);
		}
	}

	@Override
	public List<PhotoAlbumModel> getAllAlbums(int loginId) throws PersistentException {
		final List<PhotoAlbumModel> photoAlbums = jdbcTemplate.query(AlbumQueries.GET_ALL_ALBUMS.getValue(), new Object[] {loginId}, new RowMapper<PhotoAlbumModel>() {
			@Override
			public PhotoAlbumModel mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
				PhotoAlbumModel album = new PhotoAlbumModel();
				album.setAlbumId(Integer.valueOf(resultSet.getString("album_id")));
				album.setAlbumName(resultSet.getString("album_name"));
				album.setLoginId(loginId);
				album.setRating(Integer.valueOf(resultSet.getString("rating")));
				return album;
			}
		});
		return photoAlbums;
	}

}
