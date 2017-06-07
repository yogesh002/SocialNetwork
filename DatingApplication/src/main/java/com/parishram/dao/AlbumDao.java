package com.parishram.dao;

import java.util.List;
import java.util.Map;

import com.parishram.model.PhotoAlbumModel;
import com.parishram.service.exception.PersistentException;

public interface AlbumDao {
	public void deleteAlbum(int albumId, int login_id) throws PersistentException;
	public void createAlbum(int login_id, String albumName) throws PersistentException;
	public List<Map<String, Object>> retrieveImagesFromAlbum(int albumId, int loginId) throws PersistentException;
	public void editAlbumName(String albumName, int albumId) throws PersistentException;
	public void rateAlbum(int albumId, int rating) throws PersistentException;
	public int getAlbumRating(int albumId, int login_id) throws PersistentException;
	public List<PhotoAlbumModel> getAllAlbums(int loginId) throws PersistentException;
	public int getAlbumIdUsingAlbumName(String albumName) throws PersistentException;
	public void updateAlbumId(int albumId, int loginId) throws PersistentException;
}
