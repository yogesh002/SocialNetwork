<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<link rel="stylesheet" href="./css/profile.css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
<%@ page session="true"%>
<!DOCTYPE>
<head>
<meta http-equiv=Content-Type content=text/html; charset=ISO-8859-1>
<title>Welcome ${username}</title>
<style type="text/css">
.title>th {
	font-size: 1.3em;
	background-color: #ECEFF1;
	font-family: font-family : 'Volkhov', serif;
	color: #1B5E20;
	text-decoration: underline;
}

.originalValue, .editedValue, .lebel {
	font-family: 'Libre Baskerville', serif;
	color: #1A237E;
}
.lebel {
	font-weight: bold;
}

.img_msg{
	width: 50%;
	float: left;
}

#profile_pic{
margin-top: 5%;
margin-bottomloginquer: 5%;
	width: 80%;
	
}

#profile_pic>img {
	max-width: 100%;
	max-height: 100%;
}
.resultTable{
	float: right;
	width: 50%;
}

.footer{
position:relative;
bottom: 0px;
padding: 10px;
color: white;
background-color: black;
margin:0px;
width: 100%;
}

.copyright_msg{
	font-size: 1em;
}
.nav_logo {
	font-size: 2.5em;
	color: #FFFF00;
}
.navbar{
	 border-radius: 0px;
}

#successMsg{
color: #1B5E20;
}

#user{
color: #1A237E;
font-weight: bolder;
}

</style>
</head>
<html>
<body>
<nav class="navbar navbar-inverse container-fluid">
	<div class="nav_logo">PARISHRAM</div>
</nav>
<section class="container">
<div>
	<div>We sent you an activation code attached in a link to your email address that you provided. Please click on that link to complete your registration process.</div>
	<div>You MUST activate your account before proceeding with the registration process.</div>
</div>
<div class="img_msg">
	<c:if test="${not empty successMessage}">
		<div class="accountcreatedmsg">
			<h1 id="successMsg">Success!</h1>
			<span id="user"><c:out value="${username}"></c:out></span>
			<c:out value=",${successMessage}"></c:out>
		</div>
		<div>
			Return to home page <a href="welcome">Click here</a>
		</div>
	</c:if>
	<div id="profile_pic">
		<img class="img-rounded" alt="PROFILE_PIC" src="${profileImage}">
	</div>
</div>	
	<table class="resultTable table table-hover">
		<tr class="title">
			<th colspan="100%">Personal Details</th>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.userDetailsModel.firstName}">
				<td class="lebel">First Name:</td>
				<c:set var="firstName"
					value="${profileDetailsModel.userDetailsModel.firstName}" />
				<td class="originalValue"><c:out value="${firstName}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.userDetailsModel.lastName}">
				<td class="lebel">Last Name:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.userDetailsModel.lastName}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="${not empty profileDetailsModel.userDetailsModel.gender}">
				<td class="lebel">Gender:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.userDetailsModel.gender}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="${not empty profileDetailsModel.userDetailsModel.age}">
				<td class="lebel">Age:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.userDetailsModel.age}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="${not empty profileDetailsModel.userDetailsModel.weight}">
				<td class="lebel">Weight:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.userDetailsModel.weight}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.userDetailsModel.occupation}">
				<td class="lebel">Occupation:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.userDetailsModel.occupation}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="${not empty profileDetailsModel.userDetailsModel.salary}">
				<td class="lebel">Salary:</td>
				<c:set var="salary"
					value="${profileDetailsModel.userDetailsModel.salary}" />
				<td class="originalValue"><c:out value="${salary}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty userDetailsModel.religion}">
				<td class="lebel">Religion:</td>
				<c:set var="religion"
					value="${userDetailsModel.religion}" />
				<td class="originalValue"><c:out
						value="${religion}" /></td>
			</c:if>
		</tr>
		<tr class="title">
			<th colspan="100%">Address</th>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.addressDetailsModel.country}">
				<td class="lebel">Country:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.addressDetailsModel.country}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.addressDetailsModel.state}">
				<td class="lebel">State:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.addressDetailsModel.state}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.addressDetailsModel.city}">
				<td class="lebel">City:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.addressDetailsModel.city}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="${not empty profileDetailsModel.addressDetailsModel.zip}">
				<td class="lebel">Zip Code:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.addressDetailsModel.zip}" /></td>
			</c:if>
		</tr>
		<tr class="title">
			<th colspan="100%">Education Details</th>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.educationDetailsModel.schoolName} ">
				<td class="lebel">Name of School:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.educationDetailsModel.schoolName}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.educationDetailsModel.schoolStreet}">
				<td class="lebel">Street:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.educationDetailsModel.schoolStreet}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.educationDetailsModel.schoolState}">
				<td class="lebel">State:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.educationDetailsModel.schoolState}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.educationDetailsModel.schoolCity}">
				<td class="lebel">City:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.educationDetailsModel.schoolCity}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if test="${not empty educationDetailsModel.levelOfEducation}">
				<td class="lebel">Highest Level Of Education:</td>
				<td class="originalValue"><c:out
						value="${educationDetailsModel.levelOfEducation}" /></td>
			</c:if>
		</tr>
		<tr>
			<c:if
				test="${not empty profileDetailsModel.educationDetailsModel.fieldOfStudy}">
				<td class="lebel">Field of Study:</td>
				<td class="originalValue"><c:out
						value="${profileDetailsModel.educationDetailsModel.fieldOfStudy}" /></td>
			</c:if>
		</tr>
	</table>
</section>
<div class="footer">
	<%@include file="/html/footer.html" %>
</div>
</body>
</html>

