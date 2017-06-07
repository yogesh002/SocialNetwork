<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec"%>
<%@ page import="com.parishram.model.LoginContext"%>
<link href = "css/bootstrap.min.css" rel = "stylesheet">
<link rel="stylesheet" href="./css/userprofile.css">
<link rel="stylesheet" href="./css/profile.css">
<link rel="stylesheet" href="./css/font-awesome.css">
<script src = "js/jquery.min.js"></script>
<script src = "js/bootstrap.min.js"></script>
<script src="js/angular.min.js"></script>
<script type="text/javascript" src="./js/parishram.js"></script>
<script type="text/javascript" src="./js/image_component.js"></script>
<script type="text/javascript" src="./js/managefriends.js"></script>
<title>Parishram | Profile</title>
<body class="body">
<div class="navigation">
	<%@include file="profileheader.jsp"%>
</div>
<ui-view>
<section class="row section container">
	<c:set var = "currentUser" value = "${loginContext.userName }"/>
	<c:set var = "interestedUser" value = "${userName}"/>
	<div class="col-md-6 avatar_parent">
	<div>	
		<figure class="avatar">
			<img alt="ABOUT_PIC" src="${profilePicture}" id="profile_image">
	</figure>
	</div>
	<div class="friendreqbtn"> <!-- used in managefriends.js -->
		<c:if test="${searchUser !=null && isFriend.accept == null}">
			<div>
				<input type="button" class="btn btn-lg btn-info" value= "Add Friend" id="addFriend_btn"> <!-- Used in managefriends.js -->
			</div>
		</c:if>
		<c:if test="${searchUser !=null && searchUser == isFriend.from_user && isFriend.accept == 0}">
			<input type="submit" class="btn btn-lg btn-success" value= "Confirm Friend Request">
		</c:if>
		<c:if test="${searchUser !=null && searchUser == isFriend.to_user && isFriend.accept == 0}">
			<input type="button" class="btn btn-lg btn-success" value= "Friend Request Sent" >
		</c:if>
	</div>
	<c:if test="${searchUser !=null && isFriend.accept == null}">
		<div id="send_text_btn">
			<input type="button" class="btn btn-lg btn-info" value= "Send Text"> 
		</div>
		<div id="contactBox">
			<div>
				<span>Enter your phone number : </span><input id="user_phone_number" type="tel" name="phoneNumber" class="form-control" placeholder="Enter phone number..">
			</div>
			<div>
				<span>Message: </span><textarea class="form-control messagebox" rows="10" placeholder="Enter message here.."></textarea>
			</div>
			<div>
				<span id="sendTextBtn"><input type="button" value="Send Text" class="btn btn-primary" onclick="sendText();"></span>
				<span id="cancelBtn"><input type="button" value="Cancel" class="btn btn-warning"></span>
			</div>
			<div style="clear:both"></div>
		</div>
		<div id="text_successmsg"></div>
	</c:if>
	<c:if test="${(searchUser !=null && isFriend.accept == 1) || (searchUser ==null && isFriend.accept == null)}">
	<div class="row preference_btn"> <!-- used in image_component.js -->
		<div class="col-md-2">
			<span>
				<i class="fa fa-thumbs-up" aria-hidden="true" id="thumbsup"></i>
			</span> 
			<span id="thumbsupcounter"> <!-- used in image_component.js -->
			<jsp:include page="friendsListmodel.jsp"></jsp:include>
				<a href = "#" data-toggle="modal" data-target="#myModal" id="likeCount">${likeCount}</a>
			</span>
		</div>
		<div class="col-md-2">
			<span><i class="fa fa-comment" aria-hidden="true" onclick = "toggleCommentBox();"></i></span>
		</div>
	</div>
	<div class="row">
	<div class="col-md-6">
		<c:if test="${currentUser != null && currentUser == interestedUser}">
			<input type='button'  value="Delete" class="btn btn-danger action_btns" id="img_del_btn">
		</c:if>				
		<form:form id="imageUploadForm" action="imgupload" method="post" enctype="multipart/form-data">
			<input type="file" id="uploadProfilePicBtn" name="profile_pic">
			<label for="uploadProfilePicBtn">
			<c:if test="${currentUser != null && currentUser == interestedUser}">
				<span class="img_add_btn">Add</span>
				<span id="img_update_btn">Update</span>
			</c:if>
		</label>
	</form:form>
	</div>
	</div>	
	<div id="img_component">
	<div class="statusbox imgcommentdisplaybox">
		<c:forEach var="commentsModel" items="${commentsModelList}">
			<span id="comment_username"><c:out
					value="${commentsModel.userName}"></c:out></span>
			<span id="comment_comment"><c:out
					value="${commentsModel.comment}"></c:out></span>
		</c:forEach>
	</div>
	<textarea id="imgcommentbox" class="form-control" rows="3"
			name="comment" placeholder="Place your comment here..."></textarea>
	<div>
		<input class="btn btn-primary" type="button" value="Comment"
			id="comment_btn">
	</div>
</div>
<div>
 <table class="table table-striped">
	<thead>
     		<tr>
       		<th>Friends</th>
     		</tr>
     	</thead>
	<c:forEach var="allFriends" items="${friends}">
     		<tbody>
    			<tr id="friendwrapper">
      				<td>
      				 	<a href="searchUser?searchUser=${allFriends}" id="friends"> ${allFriends}</a>
      				</td>
      				 <td>
      				 <c:if test="${currentUser != null && currentUser == interestedUser}">
      				 	<input type="button" id="unfriend" class="btn btn-danger btn-sm" value = "Unfriend" onclick="unfriendUser('${allFriends}')">
      				 </c:if>
      				 </td>
      			</tr> 
   		</tbody>
	</c:forEach>
</table>	
</div>
</c:if>	
</div>
	<c:if test="${(searchUser !=null && isFriend.accept == 1) || (searchUser ==null && isFriend.accept == null)}">
		<div class="poststatus col-md-6">
			<div class="statusbox">
					<div>
						<input class="form-control" type="text" name="status" id="status_inputbox"
							placeholder="Why not share your thoughts with your friends?">
					</div>
					<div id="btn_status">
						<input type="button" name="post_btn" class="btn btn-primary" value="Submit">
					</div>
					<div style="clear: both; text-align: center"></div>
					<div id="commentBox">
						<input id="hiddenUserName" type="hidden" value="${loginContext.userName}:"/>
					</div>
					<div class="statusholder">
						<c:forEach var="status" items="${statusPost}">
							<span><img src="${status.thumbnail}" alt = "IMAGE"></span>
							<span id="userId">${status.from_user}</span><p id="comments">${status.status}</p>
						</c:forEach>
					</div>
			</div>
		</div>
	</c:if>	
</section>
</ui-view>
<%@include file="/html/footer.html"%>
<script src = "js/jquery.min.js"></script>
<script src = "js/bootstrap.min.js"></script>

</body>
</html>