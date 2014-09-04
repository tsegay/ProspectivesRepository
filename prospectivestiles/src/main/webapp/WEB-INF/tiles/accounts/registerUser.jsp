<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<spring:message var="registerLabel" code="account.label.register" />

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="homeUrl" value="/welcome" />
	<c:url var="registerUserUrl" value="/registerUser" />
</sec:authorize>

<h1>SignUp a New Applicant</h1>

<!-- ########################################## -->

<form:form action="${registerUserUrl}" modelAttribute="userEntity" role="form">

	<%-- <p><spring:message code="account.message.allFieldsRequired" /></p> --%>
	
	<form:hidden path="username" />
	<form:hidden path="password" />
	<form:hidden path="confirmPassword" />
	<form:hidden path="acceptTerms" />
	
	<div class="form-group row">
		<label for="firstName" class="col-sm-2 control-label"><spring:message code="account.label.firstName" />
		<span class="glyphicon glyphicon-asterisk red-asterisk"></span></label>
	    <div class="col-sm-5">
	      <form:input path="firstName" class="form-control" placeholder = "Your First Name" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors class="errormsg" path="firstName" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group row">
		<label for="lastName" class="col-sm-2 control-label"><spring:message code="account.label.lastName" />
		<span class="glyphicon glyphicon-asterisk red-asterisk"></span></label>
	    <div class="col-sm-5">
	      <form:input path="lastName" class="form-control" placeholder = "Your Last Name" />
	    </div>
	    <div class="col-sm-5">
	      	<form:errors class="errormsg" path="lastName" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group row">
		<label for="email" class="col-sm-2 control-label"><spring:message code="account.label.email" />
		<span class="glyphicon glyphicon-asterisk red-asterisk"></span></label>
	    <div class="col-sm-5">
	      <form:input path="email" class="form-control" placeholder = "Your Email" />
	    </div>
	    <div class="col-sm-5">
	      	<form:errors class="errormsg" path="email" htmlEscape="false" />
	    </div>
	</div>
	<hr style="border-bottom: groove;"/>
	
	<div class="form-group checkbox row">
		<label for="international" class="col-sm-5 control-label label-pad">Check if applicant is an international student</label>
	    <div class="col-sm-2">
	    	<form:checkbox class="form-control" id="international" path="international" />
	    </div>
	    <div class="col-sm-5">
	      	<form:errors class="errormsg" path="international" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group checkbox row">
		<label for="transferee" class="col-sm-5 control-label label-pad">Check if applicant is transferring in from another college</label>
	    <div class="col-sm-2">
	    	<form:checkbox class="form-control" id="transferee" path="transferee" />
	    </div>
	    <div class="col-sm-5">
	      	<form:errors class="errormsg" path="transferee" htmlEscape="false" />
	    </div>
	</div>
	<hr style="border-bottom: groove;"/>
	
	
	<%-- <div class="form-group checkbox row">
		<label for="acceptTerms" class="col-sm-5 control-label label-pad"><spring:message code="account.label.acceptTerms" />
		<span class="glyphicon glyphicon-asterisk red-asterisk"></span></label>
	    <div class="col-sm-2">
	    	<form:checkbox class="form-control" id="acceptTerms" path="acceptTerms" />
	    </div>
	    <div class="col-sm-5">
	      	<form:errors class="errormsg" path="acceptTerms" htmlEscape="false" />
	    </div>
	</div> --%>
	
	
    <div class = "form-group row">
     	<label class="col-sm-2 control-label">&nbsp;</label>
     	<div class = "col-sm-10">
     		<input class="btn btn-default" type="submit" value="${registerLabel}"></input>
     		<a class="btn btn-default" href="${homeUrl}">Cancel</a>
     	</div>
    </div>
    
       
</form:form>

