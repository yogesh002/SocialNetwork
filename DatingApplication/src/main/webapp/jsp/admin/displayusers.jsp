<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="adminMessage">
<head>
<link rel="stylesheet" href="./css/font-awesome.css">
<link href = "css/bootstrap.min.css" rel = "stylesheet">
<link href = "css/intro.css" rel ="stylesheet">
<link rel="stylesheet" href="./css/font-awesome.css">
<script src = "js/lib/jquery.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Display All Users</title>
</head>
<body>
<div class="container">
	<nav class="navbar navbar-default"> <span class="nav_logo">Parishram</span>
	<ul class="info nav navbar-nav">
		<li><a href="./">Home</a></li>
		<li><a href="newUserSignUp" class="signup_btn">Sign Up</a></li>
	</ul>
	</nav>
	<h1 id="title">
		WELCOME ADMIN 
	</h1>
	<div id="admin_profilePage">
		<a href="./user"><i class="fa fa-user fa-fw"></i>${loginContext.userName}</a>
		<span ui-sref = "adminMsg"  ng-click="show =! show" ng-show="!show">
			<i class="fa fa-envelope" aria-hidden="true"></i><span id="newmsgcount">
				<c:if test="${newEmailsCount > 0}">${newEmailsCount}</c:if>
			</span>
		</span>
	</div>
	<ui-view>
	<hr>
		<%@include file="/jsp/admin/userandroles.jsp" %>
	<div id="msg"></div>
	</ui-view>	
</div>	
<div class="footer">
	<%@include file="/html/footer.html"%>
</div>
<script src = "js/lib/angular.min.js"></script>
<script src = "js/lib/angular-ui-router.min.js"></script>
<script src = "js/module/admin.msg.js"></script>
<script src = "js/controller/admin.msg.ctrl.js"></script>
<script src = "js/service/admin.msg.service.js"></script>
<script src = "js/route.js"></script>
<script type="text/javascript" src="./js/admin.js"></script>
</body>
</html>