<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>	
<link rel="stylesheet" href="./css/font-awesome.css">
<link href='https://fonts.googleapis.com/css?family=Kreon:700'
	rel='stylesheet' type='text/css'>
<link href="https://fonts.googleapis.com/css?family=Playfair+Display" rel="stylesheet">
<link href = "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css" rel = "stylesheet">
<script src = "https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
<script src = "//maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<script src="https://code.angularjs.org/1.3.0-rc.5/angular.min.js"></script>
<script src="https://code.angularjs.org/1.3.0-rc.5/angular-messages.min.js"></script>
<script src="./js/parishram.js"></script>
<script>
 $(function(){
	$("#weight").val("");
}); 

</script>

<style>
.profileImg {
	width: 40%;
	height: 40%;
}

.profileImg img {
	max-height: 100%;
	max-width: 100%;
}

fieldset {
	margin: 0 auto;
	border-radius: 5px; 
	padding: 25px;
	width: 80%;
	margin-top: 2%;
	font-family: 'Playfair Display', serif;
	font-size:  1.2em;
		color: #000000
;
	
}
.container{
	background-color:#FAFAFA;
}
 
ul li {
	width: 25%;
	text-align: center;
	
}

.registerProfileForm:not(:first-of-type){
	display: none;
}

.form-group{
padding: 20px;
}

#btn_group{
margin-bottom: 10px;
width:76%;
margin-left: auto;
margin-right: auto;
padding: 15px;
}



#title {
	color: #212121;
	font-size: 3em;
	text-align: center;
	font-weight: bold;
	font-family:'Kreon','serif';
	
}

#subtitle {
	color: #212121;
	font-family:'Kreon','serif';
	text-align: center;
}

.jumbotron {
border-radius: 0px;
	padding: 20px;
	background-color: #F5F5F5;
	color: #212121;
	margin: 0px;
}

.well {
	border-radius: 0px;
	background-color: #FFFFFF; 
	color: #880E4F;
	text-align: center;
	margin: 0px;
	border:0px;
}

.img-thumbnail {
	width: 150px;
	height: 150px
}

#uploadBtn {
	margin: 0 auto;
}

legend{
	font-weight: bolder;
	color: #3E2723;
	font-size: 2em;
	color:#000000;
	text-align: center;

}

 .navbar{
 border-radius: 0px;
 padding-bottom: 0px;
 margin-bottom: 0px;
 border-bottom: 3px solid #EEFF41;
 }
 
 .saveandcont_btn{
 	color: white;
	display: block;
	float: right;
 }
 
 .signup_serviceerrors{
 	background-color: #FFECB3;
 	text-align: center;
 	font-weight: 1.2em;
 	color: #D50000;
 	height: 35px;
 	font-weight: bold;
 	font-size: 1.2em;
 }
 body{
 	width: 100%;
 	height: 100%;
 }
 .container{
 	padding: 0px;
 }

input::-webkit-input-placeholder {
font-family:'Kreon','serif' !important;
font-size: 0.9em !important; 

}
 
input:-moz-placeholder { /* Firefox 18- */
font-family:'Kreon','serif' !important;
font-size: 0.9em !important; 
}
 
input::-moz-placeholder {  /* Firefox 19+ */
font-family:'Kreon','serif' !important;
font-size: 0.9em !important;

 }
 
input[value]:not(.saveandcont_btn):not(.save_btn){
font-size: 1.2em;
font-family:'Kreon','serif';
background-color: #FFFFFF;
color: #1A237E; 
padding-left: 10px;
} 

input[id='age'], input[id="weight"]{
	color:#1A237E;
font-family:'Kreon','serif';
padding-left: 10px;

}
.religion{
padding: 10px;
}

.selectbox, #country, #levelOfEducation{
padding: 10px;
color:#1A237E;
font-family:'Kreon','serif';
font-size: 1.2em;
padding: 0px 0px 0px 10px;
}

.save_btn{
width: 20%;
}

.nav_logo {
	font-size: 2.5em;
	color: #FFFF00;
}

.formErrors{
	color: red; 
	font-weight: bolder;
	font-size: 1.2 em;
	
}

</style>
<!DOCTYPE>
<html id="profileSignUpHtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Find Your Match | Parishram Dating</title>
</head>
<body>
<nav class="navbar navbar-inverse container-fluid">
	<div class="nav_logo">PARISHRAM</div>
</nav>
	<header>
		<div class="jumbotron">
			<p id="title">	
				<fmt:message key="profile.signup.title.msg"></fmt:message>
			</p>
		<p id="subtitle">
			<fmt:message key="profile.signup.ask.user.details.msg"></fmt:message>
		</p>
		</div>
		<div  class="well">
		<div>
			<c:if test="${url == '' || url == null}">
			<img src = "${url}" alt="PROFILE PICTURE" class="img-thumbnail" id="uploadedImage">
			</c:if>
		</div>
		<div class="imageuploadedmsg"></div>
		<form:form id="imageUploadForm" action="imgupload" method="post"
		enctype="multipart/form-data">
		<div id="profilePicUploadMsg">
			<div id="uploadedImgMsg">
				<h2>
					<fmt:message key="profile.signup.upload.pic.msg" />
				</h2>
			</div>
			<div>
				<input type="file" name="profile_pic" id="uploadBtn"/>
			</div>
		</div>
	</form:form>
	</div>
	</header>
	<div class="container">
	<div class="signup_serviceerrors">
		<c:if test="${signUpErrors != null || signUpErrors != ''}">
		<c:forEach var="signup_serviceError" items="${signUpErrors}">
			<span class="signup_errorMessage"><c:out value="${signup_serviceError}" ></c:out></span>
			<br>
		</c:forEach>
		</c:if>
	</div>
	</div>
	<form:form action="saveUserPersonalDetails" class="registerProfileForm container"  method="post" modelAttribute="profileDetailsModel" id="userDetailsForm">
		<section class="container">
			<article>
					<fieldset>
					<legend id="personalDetailsLegend">
						<fmt:message key="profile.signup.legend.personal.details"/>
					</legend>
					<div class="form-group row">
							<label for="first_name" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.first.name"/></label>
					<div class="col-xs-10">		
							<form:input path="userDetailsModel.firstName" id="first_name" class="col-md-6 form-control" placeholder="Enter Name"/>
							<form:errors class="formErrors" path="userDetailsModel.firstName"></form:errors>
					</div>
					</div>
					<div class="form-group row">
							<label for="last_name" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.last.name"/></label>
							<div class="col-xs-10">
							<form:input path="userDetailsModel.lastName" id="last_name" placeholder="Enter Last Name" class="col-md-6 form-control" />
						  <form:errors class="formErrors" path="userDetailsModel.lastName"></form:errors>
						  </div>
					</div>
					<div class="form-group row">
					 <label for="gender" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.gender"/></label>
						<div class="col-sm-10">
	       					<div class="form-check">
		       					<label class="form-check-label" for="male" class="form-check-input">
										<form:radiobutton path="userDetailsModel.gender" value="M" id="male"/>
										<fmt:message key="profile.signup.gender.male"/>
								</label>
	       					 </div>
	       					 <div class="form-check">
		       					<label class="form-check-label" for="female" class="col-md-4">
										<form:radiobutton path="userDetailsModel.gender" value="F" id="female"/>
										<fmt:message key="profile.signup.gender.female"/>
								</label>
							</div>
							<form:errors class="formErrors" path="userDetailsModel.gender"></form:errors>
						</div>
					</div>
					<div class="form-group row">
							<label for="age" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.age"/></label>
							<div class="col-xs-10">
								<form:input class="col-md-6 col-md-6 form-control" type="number" id="age" path="userDetailsModel.age" min="18" max="100" value="18"/>
							</div>	
					</div>		
					<div class="form-group row">
							<label for="weight" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.weight"/></label>
							<div class="col-xs-10">
								<form:input type="number"  min="0"  class="col-md-6 col-md-6 form-control" id="weight" path="userDetailsModel.weight" placeholder = "Enter Weight"/>
							</div>		
					</div>		
					<div class="form-group row">
							<label class="col-xs-2 col-form-label" for="occupation"><fmt:message key="profile.signup.occupation"/></label>
							<div class="col-xs-10">
								<form:input class="col-md-6 col-md-6 form-control" id="occupation" path="userDetailsModel.occupation" placeholder = "Enter Occupation"/>
							</div>					
					</div>
					<div class="form-group row">
							<label class="col-xs-2 col-form-label" for="salary"><fmt:message key="profile.signup.salary"/></label>
							<div class="col-xs-10">
								<form:input class="col-md-6 col-md-6 form-control" path ="userDetailsModel.salary" type="number" id="salary" step="1000" min="0" value="Enter Salary"
								max="99999999" placeholder = "Enter Salary"/>
							</div>	
					</div>
					<div class="form-group row">	
							<label class="col-xs-2 col-form-label" for="religion"><fmt:message key="profile.signup.religion"/></label>
							<div class="col-xs-10">	
								<form:select class="form-control selectbox" data-style="btn-primary"  path="userDetailsModel.religionId">
								<c:forEach var="religion_description" items="${religion}">
									<form:option value="${religion_description.religion_id}" class="religion">${religion_description.religion}</form:option>
								</c:forEach>
								</form:select>
							</div>	
					</div>
				</fieldset>
				</article>
			</section>
				<fieldset>
					<legend><fmt:message key="profile.signup.legend.country.details"/></legend>
					<div class="form-group row">	
							<label for="country" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.country"/></label>
							<div class="col-xs-10">	
							<form:input list="countries" name="country" path="addressDetailsModel.country" id="country"  class="col-md-6 col-md-6 form-control" placeholder="Enter Country"/> 
									<datalist id="countries">
										<option value="United Kingdom">
										<option value="Nepal">
										<option value="India">
										<option value="Japan">
										<option value="China">
								</datalist>
							</div>
							<div>
								<form:errors class="formErrors" path="addressDetailsModel.country"></form:errors>
							</div>
						</div>	
						<div class="form-group row">		
							<label for="address_state"  class="col-xs-2 col-form-label" ><fmt:message key="profile.signup.state"/></label>
							<div class="col-xs-10">	
								<form:input path="addressDetailsModel.state" id="address_state"   class="col-md-6 col-md-6 form-control"  placeholder = "Enter State"/>
							</div>
							<div>
								<form:errors class="formErrors" path="addressDetailsModel.state"></form:errors>
							</div>
						</div>		
						<div class="form-group row">		
							<label for="city"  class="col-xs-2 col-form-label"><fmt:message key="profile.signup.city" /></label>
						<div class="col-xs-10">		
							<form:input id="city" path="addressDetailsModel.city" class="col-md-6 col-md-6 form-control" placeholder="Enter City"/>
						</div>
						<div>
							<form:errors class="formErrors" path="addressDetailsModel.city"></form:errors>
						</div>
						</div>	
						<div class="form-group row">	
							<label for="zip" class="col-xs-2 col-form-label" ><fmt:message key="profile.signup.zip"/></label>
						<div class="col-xs-10">		
							<form:input id="zip" path="addressDetailsModel.zip" placeholder = "Enter Zip Code" class="col-md-6 col-md-6 form-control"/>
						</div>
						<div>
							<form:errors class="formErrors" path="addressDetailsModel.zip"></form:errors>
						</div>
						</div>	
				</fieldset>
				<fieldset>
					<legend><fmt:message key="profile.signup.legend.education.details"/></legend>
					<div class="form-group row">
					<label for="levelOfEducation" class="col-xs-2 col-form-label" >
							<fmt:message key="profile.signup.hightest.education"/>
					</label>
					<div class="col-xs-10">
							<form:select path="educationDetailsModel.levelOfEducation" id="levelOfEducation" class="col-md-6 col-md-6 form-control">
									<option value="select">-------Select Education-------</option>
									<option value="No Education">No Education</option>
									<option value="High School">High School</option>
									<option value="Some Undergraduate Courses">Some
										Undergraduate Courses</option>
									<option value="Bachelor's Degree">Bachelor's Degree</option>
									<option value="Some Graduate Level Courses">Some
										Graduate Level Courses</option>
									<option value="Master's degree">Master's degree</option>
									<option value="PHD or above">PHD or above</option>
							</form:select>
						</div>	
						</div>	
					<div class="form-group row">			
						<label for="field_of_study" class="col-xs-2 col-form-label" ><fmt:message key="profile.signup.education.fieldofstudy"/></label>
						<div class="col-xs-10">			
							<form:input path="educationDetailsModel.fieldOfStudy" id="field_of_study" class="col-md-6 col-md-6 form-control" placeholder="Enter Field Of Study"/>
						</div>
					</div>		
						<div class="form-group row">		
							<label for="school_name" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.education.schoolName"/></label>
						<div class="col-xs-10">		
							<form:input path="educationDetailsModel.schoolName" id="school_name" class="col-md-6 col-md-6 form-control" placeholder = "Enter Name of School"/>
						</div>	
						</div>	
						<div class="form-group row">		
							<label for="school_street" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.education.schoolStreet"/></label>
						<div class="col-xs-10">		
							<form:input path="educationDetailsModel.schoolStreet" id="school_street" class="col-md-6 col-md-6 form-control" placeholder = "Enter Street Address"/>
						</div>	
						</div>	
						<div class="form-group row">	
							<label for="school_state" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.education.state"/></label>
						<div class="col-xs-10">		
							<form:input id="school_state" path="educationDetailsModel.schoolState" class="col-md-6 col-md-6 form-control" placeholder="Enter Your State"/>
						</div>
						</div>
						<div class="form-group row">	
							<label for="school_city" class="col-xs-2 col-form-label"><fmt:message key="profile.signup.education.city"/></label>
						<div class="col-xs-10">	
							<form:input id="school_city" path="educationDetailsModel.schoolCity" class="col-md-6 col-md-6 form-control" placeholder="Enter Your City"/>
						</div>	
						</div>
			</fieldset>
			<div id="btn_group">
				<input type="submit" value="Save and Continue" class="btn btn-primary"/>
			</div>
	</form:form>
</body>
</html>