<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
    <div class="table-responsive">
    	<table class="table">
    	<tr>
			<th class="col-md-3">Login Id</th>
			<th class="col-md-3">Username</th>
			<th class="col-md-3">Email</th>
			<th class="col-md-3">Role</th>
		</tr>
	<c:set var="counter" value="0"/>
   	 <c:forEach var = "user" items="${users}">
   	 <c:set var = "id"  value="${user.loginId}"/>
	<tbody class="userdetails">
		<tr>
			<td class="col-md-2">${user.loginId}</td>
			<td class="col-md-2">${user.userName}</td>
			<td class="col-md-2">${user.emailId}</td>
			<td class="col-md-2 currentrole">${user.role}</td>
			<td class="col-md-2">
				<select name = "role" id="role">
					<option value="1">User</option>
					<option value="2">Admin</option>
				</select>
			</td>
			<td><input type="hidden" value="${id}" name="loginId"></td>
			<td class="col-md-1"><input type="button" class="btn btn-warning" value = "UPDATE" onclick="editUserRole(this);"></td>
			<td class="col-md-1"><input type="button" value="DELETE" class="btn btn-danger" onclick="deleteUser(this);"></td>
		</tr>
	</tbody>	
</c:forEach>
</table>
 </div>
</body>
</html>