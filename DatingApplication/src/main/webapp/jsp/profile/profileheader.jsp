<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script src = "js/jquery.min.js"></script>
<script src = "js/bootstrap.min.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Parishram | Profile Header</title>
<link rel="stylesheet" href="./css/profileheader.css">
</head>
<body>
<div class="container">
<nav class="navbar navbar-inverse">
	<div class="nav_logo">
		<span id="parishram_logo">PARISHRAM</span>
		<div class="input-group" style="display: inline">
			<form action="searchUser" method="GET" style="display: inline">
				<div class="input-group-btn" style="width: 300px; padding: 5px 0px 0px 50px;">
    				<input type="text" name="searchUser" class="form-control" placeholder="Search friends"/>
    				<input type ="submit" class="btn btn-default" value="GO"/>
   				</div>
   			</form>	
		</div>
   	</div>	
		<div>
			<ul class="nav navbar-nav">
				<li><a href="home"><i class="fa fa-home fa-fw" aria-hidden="true"></i>Home</a></li>
				<li>
					<a href="./user"> <i class="fa fa-user" aria-hidden="true"></i>${loginContext.userName}</a>
				</li>
				<li id="total_frn_req" class="dropdown">
				<c:choose>
					<c:when test="${totalFriendRequest > 0}">
						<a class="dropdown-toggle" data-toggle="dropdown" href="#" id="total_frn_req">
							<i class="fa fa-user" aria-hidden="true"></i><span id="friendreq_count">${totalFriendRequest}</span>
						</a>
						<ul class="dropdown-menu list-group" role="menu">
							<li class="list-group-item frn_req_list">
							<div>
							<c:if test="${(currentUser != null && interestedUser == null) || (currentUser == interestedUser)}">
								<c:set var="count" value="0" scope="page" />
								<c:forEach var="friendRequest" items="${friendRequest}">
								<c:if test="${friendRequest.acceptStatus == 0}">
									<div style="width:200px;margin: 6px 10px 6px 10px; background-color: white">
										<span id="friendRequestFrom_${count}"><c:out value="${friendRequest.sender}"></c:out></span>
										<input type="button" value = "Accept" class="btn btn-info" id="frnReqAccept_btn_${count}" onclick = "acceptFriendRequest(this);"> 
										<input type="button" value = "Reject" class="btn btn-danger" id="frnReqReject_btn_${count}" onclick = "rejectFriendRequest(this);"/>
									</div>
									<c:set var="count" value="${count + 1}" scope="page"/>
								</c:if>	
								</c:forEach>
							</c:if>
							</div>
							</li>
						</ul>
					</c:when>
				</c:choose>	
				</li>	
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href=""><i class="fa fa-cog fa-fw" aria-hidden="true"></i>Settings<span class="caret"></span></a>
					<ul class="dropdown-menu list-group" role="menu">
						<li class="list-group-item">
							<a href="getUserDetails" class="nav_link_text">Update Personal Information</a>
						</li>
						<li class="list-group-item">
							<a href="" class="nav_link_text">Update Professional Information</a>
						</li>
						<li class="list-group-item">
							<a href="" class="nav_link_text">Update Hobbies</a>
						</li>
					</ul>
				</li>
				<li class="dropdown"><a class="dropdown-toggle" data-toggle="dropdown" href="#"><i class="fa fa-camera-retro"></i>Media<span class="caret"></span></a>
					<ul class="dropdown-menu list-group" role="menu">
						<li class="list-group-item"><a href="album" class="nav_link_text">Pictures</a></li>
						<li class="list-group-item"><a href="youtuberecommendation" class="nav_link_text">Videos</a>
					</ul>
				</li>
				<li><a href="logout"><i class="fa fa-sign-out" aria-hidden="true"></i>LogOut</a></li>
			</ul>
		</div>
	</div>
</nav>
</body>
</html>