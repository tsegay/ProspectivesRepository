<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	
<script type="text/javascript">
	$(document).ready(function() {
		document.newPasswordForm.password.focus();
	});
</script>

<h1>Reset Password</h1>
<br>
<br>
<p>
Please enter a new password for your account.
<br>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="changePasswordUrl" value="/myAccount/changePassword" />
</sec:authorize>

<form:form action="${changePasswordUrl}" id="newPasswordForm"
	modelAttribute="resetPasswordEntity" role="form" class="form-horizontal">

	<%-- <form:hidden path="id" /> --%>
	
	<div class="form-group row">
		<label for="password" class="col-sm-2 control-label">
			<spring:message code="resetPassword.label.password" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
	    <div class="col-sm-5">
	      <form:password path="password" class="form-control" placeholder = "Your password" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors class="errormsg" path="password" htmlEscape="false" />
	    	<%-- <form:errors class="errormsg" path="password" cssClass="error" element="div" /> --%>
	    </div>
	</div>
	
	<div class="form-group row">
		<label for="confirmPassword" class="col-sm-2 control-label">
			<spring:message code="resetPassword.label.confirmPassword" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
	    <div class="col-sm-5">
	      <form:password path="confirmPassword" class="form-control" placeholder = "Confirm your password" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors class="errormsg" path="confirmPassword" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<!-- submit calls the post method resetPasswordEntityUrl -->
			<input class="btn btn-primary" type="submit" value="Submit"></input> 
			<!-- cancel calls the get method resetPasswordEntityUrl -->
			<a class="btn btn-default" href="${changePasswordUrl}">Cancel</a>
		</div>
	</div>

</form:form>

