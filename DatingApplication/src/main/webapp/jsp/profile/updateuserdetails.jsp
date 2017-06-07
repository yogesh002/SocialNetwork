<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html  ng-app="userDetailsModule">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href = "css/bootstrap.min.css" rel = "stylesheet">
<link rel="stylesheet" href="./css/bootstrap.min.css">
<link rel="stylesheet" href="./css/font-awesome.css">
<link rel="stylesheet" href="./css/profile.css">
<title>Album</title>
</head>
<body class="container">
<div class="navigation">
	<%@include file="../profile/profileheader.jsp"%>
</div>
<div class="container">
<ui-view>
<div>
	<h1 class="updateTitle">Update User Details</h1>
</div>
<nav>
	<ul>
		<li ui-sref="updateAddressDetails" class="details_select">Update Address Details</li>
		<li ui-sref="updatepersonalinfo" class="details_select">Update User Details</li>
		<li ui-sref="updateeducationdetails" class="details_select">Update Education Details</li>
	</ul>
</nav>
</ui-view>
</div>
<script src = "js/lib/jquery.min.js"></script>
<script src = "js/lib/angular.min.js"></script>
<script src = "js/lib/angular-ui-router.min.js"></script>
<script src="js/module/user.details.js"></script>
<script src="js/controller/user.details.ctrl.js"></script>
<script src="js/service/user.details.service.js"></script>
<script src = "js/userdetails/route.js"></script>
</body>
</html>