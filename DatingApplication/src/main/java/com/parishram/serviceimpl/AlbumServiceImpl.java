package com.parishram.serviceimpl;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.parishram.dao.AlbumDao;
import com.parishram.model.PhotoAlbumModel;
import com.parishram.service.AlbumService;
import com.parishram.service.exception.PersistentException;
import com.parishram.service.exception.ServiceException;

@Component
public class AlbumServiceImpl implements AlbumService {

	private static Logger LOGGER = Logger.getLogger(AlbumServiceImpl.class);

	@Autowired
	private AlbumDao albumDao;

	@Override
	public void deleteAlbum(int albumId, int login_id) throws ServiceException {
		try {
			albumDao.deleteAlbum(albumId, login_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to delete album" + persistentException.getMessage(), persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public void createAlbum(int login_id, String albumName) throws ServiceException {
		try {
			albumDao.createAlbum(login_id, albumName);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to upload images in the particular album" + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public List<Map<String, Object>> retrieveImagesFromAlbum(int albumId, int login_id) throws ServiceException {
		try {
			return albumDao.retrieveImagesFromAlbum(albumId, login_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve images from the album selected." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void editAlbumName(String album, int albumId) throws ServiceException {
		try {
			albumDao.editAlbumName(album, albumId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to delete image from the album selected." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public void rateAlbum(int rating, int albumId) throws ServiceException {
		try {
			albumDao.rateAlbum(rating, albumId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to rate the selected album." + persistentException.getMessage(), persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public int getAlbumRating(int albumId, int login_id) throws ServiceException {
		try {
			return albumDao.getAlbumRating(albumId, login_id);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to rate the rating of selected album." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public int getAlbumIdUsingAlbumName(String albumName) throws ServiceException {
		try {
			return albumDao.getAlbumIdUsingAlbumName(albumName);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to get album Id from album name." + persistentException.getMessage(),
					persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}
	}

	@Override
	public void updateAlbumId(int albumId, int loginId) throws ServiceException {
		try {
			albumDao.updateAlbumId(albumId, loginId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to update albumId." + persistentException.getMessage(), persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}

	}

	@Override
	public List<PhotoAlbumModel> getAllAlbums(int loginId) throws ServiceException {
		try {
			return albumDao.getAllAlbums(loginId);
		} catch (PersistentException persistentException) {
			LOGGER.error("Unable to retrieve albums." + persistentException.getMessage(), persistentException);
			throw new ServiceException(
					"We are experiencing technical issue. Please try again later" + persistentException.getMessage(),
					persistentException);
		}

	}

}
