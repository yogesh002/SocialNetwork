package com.parishram.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Activity;
import com.google.api.services.youtube.model.ActivityContentDetails;
import com.google.api.services.youtube.model.ActivityContentDetailsUpload;
import com.google.api.services.youtube.model.ActivityListResponse;
import com.google.api.services.youtube.model.ActivitySnippet;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistItem;
import com.google.api.services.youtube.model.PlaylistItemSnippet;
import com.google.api.services.youtube.model.PlaylistListResponse;
import com.google.api.services.youtube.model.PlaylistPlayer;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.SearchResultSnippet;
import com.google.common.collect.Lists;
import com.parishram.common.Constant;
import com.parishram.utils.Auth;

@Controller
public class YoutubeController {
	private static Logger LOGGER = Logger.getLogger(YoutubeController.class);
	private static final long MAX_SEARCHED_ITEMS = 20;

	private static YouTube youtube;

	private static YouTube buildYoutubeApi() throws IOException {
		List<String> scopes = Lists.newArrayList(Constant.YOUTUBE_READ_ONLY, Constant.YOUTUBE_FORCE_SSL,
				Constant.YOUTUBE_AUTH, Constant.YOUTUBE_PARTNER);
		Credential credential = Auth.authorize(scopes, "recommendation");
		youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
				.setApplicationName("parishram-youtube").build();
		return youtube;
	}

	@RequestMapping(value = "/youtuberecommendation", method = RequestMethod.GET)
	public ModelAndView getYoutubeDetails() {
		ModelAndView modelAndView = new ModelAndView();
		YouTube youtube = null;
		try {
			youtube = buildYoutubeApi();
			YouTube.Activities.List activities = youtube.activities().list("id, snippet,contentDetails");
			activities.setPart("contentDetails, id, snippet");
			activities.setHome(true);
			ActivityListResponse response = activities.execute();
			List<Map<String, String>> videoDetailsList = new ArrayList<Map<String, String>>();
			List<Activity> activityList = response.getItems();
			for (Activity activity : activityList) {
				ActivitySnippet snippet = activity.getSnippet();
				String title = snippet.getTitle();
				ActivityContentDetails details = activity.getContentDetails();
				ActivityContentDetailsUpload upload = details.getUpload();
				String videoId = upload.getVideoId();
				Map<String, String> videoDetails = new HashMap<String, String>();
				videoDetails.put("videoId", videoId);
				videoDetails.put("title", title);
				videoDetailsList.add(videoDetails);
			}
			modelAndView.addObject("recommend_videoDetails", videoDetailsList);
			modelAndView.setViewName("/videos/video");

		} catch (Exception exception) {
			exception.printStackTrace();
		}

		return modelAndView;
	}

	@RequestMapping(value = "/searchYoutubeVideos", method = RequestMethod.POST)
	public ModelAndView searchYoutubeVideos(HttpServletRequest request) {
		ModelAndView modelAndView = new ModelAndView();
		try {
			YouTube.Playlists.List playList = youtube.playlists().list("snippet,contentDetails");
			playList.setPart("snippet,player");
			playList.setMaxResults(MAX_SEARCHED_ITEMS);
			playList.setMine(true);
			PlaylistListResponse playListResponse = playList.execute();
			List<Playlist> playListItems = playListResponse.getItems();
			List<Map<String, String>> videoDetailsList = new ArrayList<Map<String, String>>();
			for (Playlist playListItem : playListItems) {
				Map<String, String> playListDetails = new HashMap<String, String>();
				String playListId = playListItem.getId();
				PlaylistSnippet snippet = playListItem.getSnippet();
				String title = snippet.getTitle();
				playListDetails.put("playlistTitle", title);
				playListDetails.put("playlistID", playListId);
				videoDetailsList.add(playListDetails);
			}
			modelAndView.addObject("playListDetails", videoDetailsList);

			YouTube.Search.List searchList = youtube.search().list("snippet,contentDetails");
			searchList.setPart("snippet");
			searchList.setType("video");
			searchList.setMaxResults(MAX_SEARCHED_ITEMS);
			String query = request.getParameter("search");
			query = query.replace("/%20/g", "+");
			searchList.setQ(query);
			SearchListResponse searchListResponse = searchList.execute();
			List<SearchResult> searchResults = searchListResponse.getItems();
			List<Map<String, String>> videoSearchDetailsList = new ArrayList<Map<String, String>>();
			for (SearchResult searchResult : searchResults) {
				SearchResultSnippet snippet = searchResult.getSnippet();
				String title = snippet.getTitle();
				ResourceId resourceId = searchResult.getId();
				String videoId = resourceId.getVideoId();
				Map<String, String> videoDetails = new HashMap<String, String>();
				videoDetails.put("videoId", videoId);
				videoDetails.put("title", title);
				videoSearchDetailsList.add(videoDetails);
			}
			modelAndView.addObject("search_videoDetails", videoSearchDetailsList);
			modelAndView.setViewName("/videos/video");

		} catch (Exception exception) {
			LOGGER.error("There was a problem while searching youtube videos" + exception.getMessage(), exception);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/showMyPlayList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView displayPlayList() {
		ModelAndView modelAndView = new ModelAndView();
		try {
			YouTube.Playlists.List playList = youtube.playlists().list("snippet,contentDetails");
			playList.setPart("snippet,player");
			playList.setMaxResults(MAX_SEARCHED_ITEMS);
			playList.setMine(true);
			PlaylistListResponse searchListResponse = playList.execute();
			List<Playlist> playListItems = searchListResponse.getItems();
			List<Map<String, String>> videoDetailsList = new ArrayList<Map<String, String>>();
			for (Playlist playListItem : playListItems) {
				PlaylistSnippet snippet = playListItem.getSnippet();
				String title = snippet.getTitle();
				PlaylistPlayer player = playListItem.getPlayer();
				String embedHtml = player.getEmbedHtml();
				Map<String, String> videoDetails = new HashMap<String, String>();
				videoDetails.put("videoId", embedHtml);
				videoDetails.put("title", title);
				videoDetailsList.add(videoDetails);
			}
			modelAndView.addObject("playlist_videoDetails", videoDetailsList);
			modelAndView.setViewName("/videos/video");

		} catch (Exception exception) {
			LOGGER.error("There was a problem while searching youtube videos" + exception.getMessage(), exception);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/createPlayList", method = RequestMethod.POST)
	public String createPlayList(HttpServletRequest request) throws IOException {
		try {
			PlaylistSnippet snippet = buildPlayListSnippet(request);
			PlaylistStatus status = buildPlayListStatus(request);
			Playlist playList = buildPlayList(snippet, status);
			YouTube.Playlists.Insert insertPlayList = youtube.playlists().insert("snippet,status", playList);
			insertPlayList.execute();
			return ("redirect:/showMyPlayList");
		} catch (Exception exception) {
			LOGGER.error("There was a problem while creating the playlist.", exception);
			return "";
		}

	}

	@RequestMapping(value = "/addVideosInPlayList", method = RequestMethod.POST)
	public String addVideosInPlayList(HttpServletRequest request) throws IOException {
		String videoId = request.getParameter("videoId");
		String playListId = request.getParameter("playlistInfo");
		// String title = request.getParameter("playlist_title");
		ResourceId resourceId = buildResourceId(videoId);
		PlaylistItemSnippet playListItemSnippet = buildPlayListSnippet(playListId, resourceId);
		PlaylistItem playListItem = buildPlayListItem(playListItemSnippet);
		YouTube.PlaylistItems.Insert playlistItemsInsertCommand = youtube.playlistItems()
				.insert("snippet,contentDetails", playListItem);
		playlistItemsInsertCommand.execute();
		return ("redirect:/showMyPlayList");
	}

	//
	private ResourceId buildResourceId(String videoId) {
		ResourceId resourceId = new ResourceId();
		resourceId.setKind("youtube#video");
		resourceId.setVideoId(videoId);
		return resourceId;
	}

	private PlaylistItemSnippet buildPlayListSnippet(String playListId, ResourceId resourceId) {
		PlaylistItemSnippet playlistItemSnippet = new PlaylistItemSnippet();
		// playlistItemSnippet.setTitle(title);
		playlistItemSnippet.setPlaylistId(playListId);
		playlistItemSnippet.setResourceId(resourceId);
		return playlistItemSnippet;
	}

	private PlaylistItem buildPlayListItem(PlaylistItemSnippet snippet) {
		PlaylistItem playlistItem = new PlaylistItem();
		playlistItem.setSnippet(snippet);
		return playlistItem;
	}

	//
	private PlaylistSnippet buildPlayListSnippet(HttpServletRequest request) {
		PlaylistSnippet playlistSnippet = new PlaylistSnippet();
		playlistSnippet.setTitle(request.getParameter("playListTitle"));
		playlistSnippet.setDescription(request.getParameter("playListDescription"));
		return playlistSnippet;
	}

	private PlaylistStatus buildPlayListStatus(HttpServletRequest request) {
		PlaylistStatus playlistStatus = new PlaylistStatus();
		playlistStatus.setPrivacyStatus(request.getParameter("playListPrivacy"));
		return playlistStatus;
	}

	private Playlist buildPlayList(PlaylistSnippet snippet, PlaylistStatus status) {
		Playlist playList = new Playlist();
		playList.setSnippet(snippet);
		playList.setStatus(status);
		return playList;
	}
}
