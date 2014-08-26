<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>

<%-- <c:set var="accountsPath" value="/accounts" />

<c:set var="user" value="${institute.userEntity}" /> --%>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="instituteFormUrl" value="/accounts/${userEntity.id}/institute/new" />
	<c:url var="myEducationUrl" value="/accounts/${userEntity.id}/educations" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="instituteFormUrl" value="/myAccount/institute/new" />
	<c:url var="myEducationUrl" value="/myAccount/educations" />
</sec:authorize>	

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div class="well well-sm row">
		<div class="col-sm-3">
	  		<img src="${pageContext.request.contextPath}/resources/images/placeholderImage_140x140.jpg" alt="Your Pic" class="img-rounded profileImg">
	    </div>
		<dl class="dl-horizontal col-sm-9">
			<dt>Full name:</dt>
			<dd>
				<c:out value="${user.firstName}"></c:out> <c:out value="${user.lastName}"></c:out>
			</dd>
			<dt>Username</dt>
			<dd>
				<c:out value="${user.username}" />
			</dd>
		</dl>
	</div>
</sec:authorize>

<h1>Institute Form</h1>


<%-- <div>
	Institute form for: <c:out value="${institute.userEntity.username}" />
</div> --%>
		
	<form:form action="${instituteFormUrl}" modelAttribute="institute" role="form">
	
		<%-- <p><spring:message code="newUserRegistration.message.allFieldsRequired" /></p> --%>
		
	
		<div class="form-group row">
			<!-- <label for="name" class="col-sm-2 control-label">Institute Name</label> -->
			<label for="name" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.name" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input class="form-control" path="name" placeholder = "Your Institute Name" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="name" htmlEscape="false" />
		    </div>
		</div>
		
		<div class="form-group row">
			<!-- <label for="state" class="col-sm-2 control-label">State</label> -->
			<label for="state" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.state" />
			</label>
			<div class = "col-sm-5">
				<form:input class="form-control" path="state" placeholder = "Your State" />
			</div>
			<div class="col-sm-5">
		    	<form:errors class="errormsg" path="state" htmlEscape="false" />
		    </div>
		</div>
		
		<div class="form-group row">
			<!-- <label for="city" class="col-sm-2 control-label">city</label> -->
			<label for="city" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.city" />
			</label>
			<div class = "col-sm-5">
				<form:input path="city" class = "form-control" id = "city" placeholder = "Your city"/>
			</div>
			<div class="col-sm-5">
		    	<form:errors class="errormsg" path="city" htmlEscape="false" />
		    </div>
        </div>
        
		<div class="form-group row">
			<!-- <label for="country" class="col-sm-2 control-label">Country</label> -->
			<label for="country" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.country" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
			<div class = "col-sm-5">
				<form:input path="country" class = "form-control" id = "country" placeholder = "Your Country"/>
			</div>
			<div class="col-sm-5">
		    	<form:errors class="errormsg" path="country" htmlEscape="false" />
		    </div>
        </div>
        
		<div class="form-group row">
			<!-- <label for="zip" class="col-sm-2 control-label">zip</label> -->
			<label for="zip" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.zip" />
			</label>
			<div class = "col-sm-5">
				<form:input path="zip" class = "form-control" id = "zip" placeholder = "Your zip"/>
			</div>
			<div class="col-sm-5">
		    	<form:errors class="errormsg" path="zip" htmlEscape="false" />
		    </div>
        </div>
        
		<div class="form-group row">
			<!-- <label for="programOfStudy" class="col-sm-2 control-label">programOfStudy</label> -->
			<label for="programOfStudy" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.programOfStudy" />
			</label>
			<div class = "col-sm-5">
				<form:input path="programOfStudy" class = "form-control" id = "programOfStudy" placeholder = "Your programOfStudy"/>
			</div>
			<div class="col-sm-5">
		    	<form:errors class="errormsg" path="programOfStudy" htmlEscape="false" />
		    </div>
        </div>
        
		<div class="form-group row">
			<!-- <label for="levelOfStudy" class="col-sm-2 control-label">levelOfStudy</label> -->
			<label for="levelOfStudy" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.levelOfStudy" />
			</label>
			<div class = "col-sm-5">
				<form:input path="levelOfStudy" class = "form-control" id = "levelOfStudy" placeholder = "Your levelOfStudy"/>
			</div>
			<div class="col-sm-5">
		    	<form:errors class="errormsg" path="levelOfStudy" htmlEscape="false" />
		    </div>
        </div>
        
        <div class="form-group row">
			<label for="attendedFrom" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.attendedFrom" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input id="attendedFrom" path="attendedFrom" class="form-control" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="attendedFrom" htmlEscape="false" />
		    </div>
		</div>
		
        <div class="form-group row">
			<label for="attendedTo" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.attendedTo" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input id="attendedTo" path="attendedTo" class="form-control" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="attendedTo" htmlEscape="false" />
		    </div>
		</div>
		
        <div class="form-group row">
			<label for="graduationDate" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.graduationDate" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input id="graduationDate" path="graduationDate" class="form-control" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="graduationDate" htmlEscape="false" />
		    </div>
		</div>
	
        <%-- <fmt:formatDate value="${institute.attendedFrom}" var="attendedFromString" pattern="dd/MM/yyyy" />
        <div class="form-group row">
			<label for="attendedFrom" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.attendedFrom" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input path="attendedFrom" value="${attendedFromString}" class="form-control" id="attendedFrom" placeholder="Your attendedFrom" />
			</div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="attendedFrom" htmlEscape="false" />
		    </div>
		</div>
        
        <fmt:formatDate value="${institute.attendedTo}" var="attendedToString" pattern="dd/MM/yyyy" />
        <div class="form-group row">
			<!-- <label for="attendedTo" class="col-sm-2 control-label">attendedTo</label> -->
			<label for="attendedTo" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.attendedTo" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input path="attendedTo" value="${attendedToString}" class="form-control" id="attendedTo" placeholder="Your attendedTo" />
			</div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="attendedTo" htmlEscape="false" />
		    </div>
		</div>
		
        <fmt:formatDate value="${institute.graduationDate}" var="graduationDateString" pattern="dd/MM/yyyy" />
        <div class="form-group row">
			<!-- <label for="graduationDate" class="col-sm-2 control-label">graduationDate</label> -->
			<label for="graduationDate" class="col-sm-2 control-label">
				<spring:message code="instituteForm.label.graduationDate" />
			</label>
		    <div class="col-sm-5">
		      <form:input path="graduationDate" value="${graduationDateString}" class="form-control" id="graduationDate" placeholder="Your graduationDate" />
			</div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="graduationDate" htmlEscape="false" />
		    </div>
		</div> --%>

		
        <div class = "form-group">
        	<label for="diplome" class="col-sm-2 control-label">&nbsp;</label>
        	<div class = "col-sm-10">
        		<input class="btn btn-primary" type="submit" value="Save"></input>
        		<a class = "btn btn-default" href="${myEducationUrl}">Cancel</a>
        	</div>
        </div>
	
	</form:form>
		
		
<script>
	$(function (){
		$('#attendedFrom').datepicker();
		$('#attendedTo').datepicker();
		$('#graduationDate').datepicker();
	});
 </script>