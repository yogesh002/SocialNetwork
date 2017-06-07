<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link href = "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel = "stylesheet">
<script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src = "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script src="https://code.angularjs.org/1.3.0-rc.5/angular.min.js"></script>
<script src="https://code.angularjs.org/1.3.0-rc.5/angular-messages.min.js"></script>
<link rel="stylesheet" href="./css/profile.css">
<link rel="stylesheet" href="./css/signup.css">
<script type="text/javascript" src="./js/parishram.js"></script>
<!DOCTYPE>
<html ng-app="usernamePasswordApp" class="signuphtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Parishram Dating | Sign In</title>
</head>
<body ng-controller="usernamePasswordController" class="signup_body" >
<nav class="signup_nav navbar navbar-inverse">
	<div class="signup_nav_logo nav_logo">PARISHRAM</div>
</nav>
<div class="container">
		<header class="signup_header">
			<div><h1>Sign Up</h1>
			<span><strong>Signup right now to get started.</strong></span>
			</div>
		</header>
		<section>
		<div class="signup_signupform">
		<form:form action="newUserSignUp" method="post" modelAttribute="usersignup" name="usernamePasswordForm" class="form-horizontal" role="form">
			<div class="form-group">
				<div class="control-label col-xs-3" ><label for="phone">Phone: </label></div>
				<div class="col-xs-6" >
					<form:input class="form-control" value="" type="tel" name="phone" path="phone" id="phone" placeholder="Enter Phone Number.." ng-model="phone" required="required" minlength="10"/>
				</div>
				<div ng-messages="usernamePasswordForm.phone.$error" class="col-xs-3">
			 		<div class = "signup_errorMsg" ng-message="required">
			 			Please enter your Phone Number
			 		</div>
	            	<div class = "signup_errorMsg" ng-message="minlength">
	            		Phone Number must be 10 characters long
	            	</div>
				</div>	
			</div>		
			<div class="form-group">
				<div class="control-label col-xs-3" ><label for="email">Email: </label></div>
				<div class="col-xs-6" >
					<form:input class="form-control" value="" type="email" name="email" required="required" path="email" id="email" placeholder="Enter Email Address" ng-model="email"/>
				</div>
				<div ng-messages="usernamePasswordForm.email.$error" class="col-xs-3">
			 		<div class = "signup_errorMsg" ng-message="required">
			 			Please enter your email address
			 		</div>
				</div>	
			</div>		
			<div class="form-group">
				<div class="control-label col-xs-3" ><label for="username">User Name: </label></div>
				<div class="col-xs-6" >
					<form:input class="form-control" value="" type="text" name="username" required="required" path="username" id="username" minlength="5" ng-model="username" placeholder="Enter User Name"/>
				</div>
				<div ng-messages="usernamePasswordForm.username.$error" class="col-xs-3">
					<div class = "signup_errorMsg" ng-message = "required">
						UserName is required
					</div>	
	            	<div class = "signup_errorMsg" ng-message="minlength">
	            		UserName must be at least 5 characters long
	            	</div>
				</div> 
			</div>
			<div class="form-group">
				<div class="control-label col-xs-3" ><label for="password">Password:</label></div>
				<div class="col-xs-6" >
					<form:input class="form-control" value="" type="password" name="password" required="required" path ="password" id="password" minlength="8" ng-model="password" placeholder = "Enter Password"/>
				</div>
				<div ng-messages="usernamePasswordForm.password.$error"  class="col-xs-3">
			 		<div class = "signup_errorMsg" ng-message="minlength">
			 			Password must be at least 8 characters long
			 		</div>
			 		<div class = "signup_errorMsg" ng-message="required">
			 			Password is required
			 		</div>
				</div>
			</div>
			<div id= "signup_signupforgotpass">
				<a href="#"><strong>Forgot password?</strong></a>
			</div>
			<div class="signup_btnGroup">
			<div class="signup_submitBtn">
				<input type="submit" value="Submit" class="btn btn-primary">
			</div>
				<div class="signup_cancelBtn">
				<a href="./">
					<input type="button" value="Cancel" class="btn btn-default">
				</a>
			</div>
			</div>
			<div class="signup_errorGrpBox">
				<div class="signup_errorblock">
					<form:errors path="email" class="signup_errorMessage" />
				</div>
				<div class="signup_errorblock">
					<form:errors path="username" class="signup_errorMessage" />
				</div>
				<div class="signup_errorblock">
					<form:errors path="password" class="signup_errorMessage" />
				</div>
				<div class="signup_serviceerrors">
					<c:if test="${signUpErrors != null}">
						<c:forEach var="signup_serviceError" items="${signUpErrors}">
							<span class="signup_errorMessage"><c:out value="${signup_serviceError}" ></c:out></span>
							<br>
						</c:forEach>
					</c:if>
				</div>
			</div>	
		</form:form>
		</div>
		</section>
	</div>	
		<div class="signup_footer">
			<%@include file="/html/footer.html"%>
		</div>
</body>
</html>