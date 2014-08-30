<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	
<script type="text/javascript">
	$(document).ready(function() {
		document.loginForm.j_username.focus();
	});
</script>

	
<div>
	
	<form name="loginForm" action="${pageContext.request.contextPath}/j_spring_security_check" method="post">
	
	<h1>Login Form</h1>
		<div class="form-group">
			<label for="j_username">Username:</label>
			<input class="form-control" type="text" name="j_username" placeholder="Enter your username" />
		</div>
		<div class="form-group">
			<label for="j_password">Password:</label>
			<input class="form-control" type="password"
				name="j_password" placeholder="password" />
		</div>
		<div class="form-group">
			<span>Remember me: </span>
			<input type="checkbox" name="_spring_security_remember_me">
		</div>
		
		<div class="form-group">
			<input type="submit" class="btn btn-default" value="Log in" />
			<a class="btn btn-danger btn-sm" href="<c:url value='/registrationform'/>">SignUp</a>
			<a href="<c:url value='/resetPasswordRequest'/>">Lost your password?</a>
		</div>
	</form>

</div>

