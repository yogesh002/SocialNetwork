<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href = "css/bootstrap.min.css" rel = "stylesheet">
<link rel="stylesheet" href="./css/userprofile.css">
<link rel="stylesheet" href="./css/album.css">
<link rel="stylesheet" href="./css/youtube.css">
<link rel="stylesheet" href="./css/profile.css">
<link rel="stylesheet" href="./css/font-awesome.css">
<title>Videos</title>
</head>
<body>
<div class="navigation">
	<%@include file="../profile/profileheader.jsp"%>
</div>
<div class="row container section">
<div class="videoWrapper col-md-7">
<form action="addVideosInPlayList" method="POST">
<c:if test="${createPlaylistSuccessMsg != null}">
	<h3>${createPlaylistSuccessMsg}</h3>
</c:if>
<c:choose>
	<c:when test="${null != recommend_videoDetails}">
		<c:forEach var="video" items="${recommend_videoDetails}">
		<div class="player">
			<h4>${video.title}</h4>
			<div class="player">
				<iframe class="video w100" width="550" height="360" src="//www.youtube.com/embed/${video.videoId}" frameborder="0" allowfullscreen></iframe>
			</div>
		</div>		
		</c:forEach>
	</c:when>
	<c:when test="${null != search_videoDetails}">
		<c:forEach var="video" items="${search_videoDetails}">
		<div class="player">
			<h4>${video.title}</h4>
			<div>
				<iframe class="video w100" width="550" height="360" src="//www.youtube.com/embed/${video.videoId}" frameborder="0" allowfullscreen></iframe>
			</div>
			<c:if test="${playListDetails != null}">
			<div class="playlist_adder">
				<span>
					<input type="checkbox" name="videoId" value="${video.videoId}">
				</span>
				<span>
					<input type="submit" value="Add to Playlist" class="btn btn-success">
				</span>
				<select name="playlistInfo">
					<c:forEach var ="playlistObj" items="${playListDetails}">
						<option value="${playlistObj.playlistID}">
							${playlistObj.playlistTitle}
						</option>
				</c:forEach>
				</select>
			</div>
			</c:if>
		</div>
		</c:forEach>
	</c:when>
	<c:when test="${null != playlist_videoDetails}">
		<c:forEach var="video" items="${playlist_videoDetails}">
		<div class="player">
			<h4>${video.title}</h4>
			<div>${video.videoId}</div>
		</div>	
		</c:forEach>
	</c:when>
</c:choose>
</form>	
</div>
<div class="searchBox col-md-5">
<div>
<h3>Search Video:</h3>
<form action="searchYoutubeVideos" method="POST">
	<input type="text" id="search" name="search" class="form-control" placeholder="Search Videos here..">
	<input type="submit" value="Search" class="btn btn-primary search_btn" >
</form>
</div>
<div id="showPlaylistBtn">
<a href="showMyPlayList">
	Show My Playlist:
</a>
</div>
<div id="playlist_createForm">
<h3>Create a PlayList:</h3>
<form action="createPlayList" method="POST">
	<div>
		<input type="text" id="playlist_title" name="playListTitle" class="form-control" placeholder="Playlist name..">
	</div>
	<div>
		<input type="text" id="playlist_desc" name="playListDescription" class="form-control" placeholder="Write something about your playlist..">
	</div>
	<div>
		<select name="playListPrivacy">
			<option value="PUBLIC">PUBLIC</option>
			<option value="PRIVATE">PRIVATE</option>
		</select>	
	</div>
	<div>
		<input type="submit" value="Create Playlist" class="btn btn-primary">
	</div>
</form>
</div>
</div>
</div>
<div class="footer">
	<%@include file="/html/footer.html" %>
</div>
<script src = "js/lib/jquery.min.js"></script>
<script src = "js/lib/angular.min.js"></script>
</body>
</html>