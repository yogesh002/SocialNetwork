<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script src = "js/jquery.min.js"></script>
<script src = "js/bootstrap.min.js"></script>
<link href = "css/bootstrap.min.css" rel = "stylesheet">
<link rel="stylesheet" href="./css/userprofile.css">
<link rel="stylesheet" href="./css/profile.css">
<link rel="stylesheet" href="./css/font-awesome.css">
<title>Wall Posts</title>
</head>
<body>
<div class="navigation">
	<%@include file="profileheader.jsp"%>
</div>
<div class="container section">
	<c:forEach var="posts" items="${wallPosts}">
	<div class="statusDetails">
	<div class="img_status_grp">
		<span id="user_img">
			<img alt="THUMBNAIL" src="${posts.image}">
		</span>
		<span id="person">
			${user}
		</span>
	</div>	
		<div id="wall_status">
			 ${posts.status}
		</div>
	</div>	
	</c:forEach>
</div>
<div></div>
</body>
</html>