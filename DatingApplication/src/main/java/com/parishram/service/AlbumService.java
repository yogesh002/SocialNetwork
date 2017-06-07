package com.parishram.service;

import java.util.List;
import java.util.Map;

import com.parishram.model.PhotoAlbumModel;
import com.parishram.service.exception.ServiceException;

public interface AlbumService {
	public void deleteAlbum(int albumId, int login_id) throws ServiceException;
	public void createAlbum(int login_id, String albumName) throws ServiceException;
	public List<Map<String, Object>> retrieveImagesFromAlbum(int albumId, int login_id) throws ServiceException;
	public void editAlbumName(String album, int albumId) throws ServiceException;
	public void rateAlbum(int rating, int albumId) throws ServiceException;
	public int getAlbumRating(int albumId, int login_id) throws ServiceException;
	public int getAlbumIdUsingAlbumName(String albumName) throws ServiceException;
	public List<PhotoAlbumModel> getAllAlbums(int loginId) throws ServiceException;
	public void updateAlbumId(int albumId, int loginId) throws ServiceException;
}
