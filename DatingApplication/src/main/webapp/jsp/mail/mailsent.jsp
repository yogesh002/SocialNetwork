<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="./css/intro.css">
<title>Parishram | Contact</title>
<style>
.label {
	font-size: 16px;
	font-weight: bolder;
}

.sender_info {
	font-weight: bold;
	color: purple;
	margin: 10px;
}

h3 {
	padding-top: 200px;
	text-align: center;
	color: purple;
}

 body { 
    height: 100%;
}

.wrapper{
	height: 100%;
}

.container{
	position:relative;
	overflow: hidden;
	height: 100%;
	max-height: 100%;
}
.footer {
	position: absolute;
	bottom: 0px;
}
.btns{
	display: inline-block;
	text-align:center;
	width: 99%;
	margin-top: 50px;
}
.button{
	padding: 5px 80px 5px 80px;
	background-color: #1B5E20;
	color: white;
	margin:0px 50px 0px 50px;
	border: none;
	
	
}
</style>
</head>
<body>
<div class="wrapper">
<div class="container">
	<nav class="navbar navbar-default"> <span class="nav_logo">Parishram</span>
	<ul class="info nav navbar-nav">
		<li><a href="./">Home</a></li>
		<li><a href="newUserSignUp" class="signup_btn">Sign Up</a></li>
	</ul>
	</nav>
	<h3>Thank you for contacting us. We will review your questions and
		get back to you soon.</h3>
	<div class="btns">
		<a href="/DatingApplication"><input type="submit" value="Sign In" class="button"></a>
		<a href="/DatingApplication/newUserSignUp"><input type="submit" value="Sign Up" class="button"></a>
	</div>	
</div>	
<div class="footer">
	<%@include file="/html/footer.html"%>
</div>	
</div>
</body>
</html>