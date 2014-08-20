<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
	
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	
<script type="text/javascript">
	$(document).ready(function() {
		document.resetPasswordEntityForm.email.focus();
	});
</script>

<h1>Reset Password</h1>
<br>
<br>
<p>Please enter the email address associated with your account and we will send you a link to create a new password to your email address.</p>

<br>

<c:url var="resetPasswordEntityUrl" value="/postResetPasswordForm" />

<form:form action="${resetPasswordEntityUrl}" id="resetPasswordEntityForm"
	modelAttribute="resetPasswordEntity" role="form" class="form-horizontal">

	<div class="form-group row">
		<label for="email" class="col-sm-2 control-label">
			<spring:message code="resetPassword.label.email" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
	    <div class="col-sm-5">
	      <form:input path="email" class="form-control" placeholder = "Your email" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors class="errormsg" path="email" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<!-- submit calls the post method resetPasswordEntityUrl -->
			<input class="btn btn-primary" type="submit" value="Submit"></input> 
			<!-- cancel calls the get method resetPasswordEntityUrl -->
			<a class="btn btn-default" href="${resetPasswordEntityUrl}">Cancel</a>
		</div>
	</div>

</form:form>

