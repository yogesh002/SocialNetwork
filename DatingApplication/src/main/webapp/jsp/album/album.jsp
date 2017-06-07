<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html ng-app='photoAlbum'>
<link href = "css/bootstrap.min.css" rel = "stylesheet">
<link rel="stylesheet" href="./css/userprofile.css">
<link rel="stylesheet" href="./css/album.css">
<link rel="stylesheet" href="./css/profile.css">
<link rel="stylesheet" href="./css/font-awesome.css">
<title>Wall Posts</title>
</head>
<body>
<div class="navigation">
	<%@include file="../profile/profileheader.jsp"%>
</div>
<div class="container">
<ui-view>
<div class="container">
<div>
	<h1 id="title">Welcome to Album Section.</h1>
	<hr>
</div>
<nav>
	<ul class="albumDetails">
		<li ui-sref="createAlbum">Create Album</li>
		<li ui-sref="viewAllAlbumsOfUser">View Albums</li>
	</ul>
</nav>
</div>
</ui-view>
</div>
<div class="footer">
	<%@include file="/html/footer.html" %>
</div>
<script src = "js/lib/jquery.min.js"></script>
<script src = "js/lib/angular.min.js"></script>
<script src = "js/lib/angular-ui-router.min.js"></script>
<script src = "js/module/photo.album.js"></script>
<script src = "js/controller/photo.album.ctrl.js"></script>
<script src = "js/service/photo.album.service.js"></script>
<script src = "js/router/route.js"></script>
</body>
</html>