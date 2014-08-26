<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="accountsPath" value="/accounts" />

<c:set var="user" value="${highSchool.userEntity}" />


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="editHighSchoolFormUrl" value="/accounts/${user.id}/highSchool/${highSchool.id}/edit" />
	<c:url var="myEducationUrl" value="/accounts/${user.id}/educations" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="editHighSchoolFormUrl" value="/myAccount/highSchool/${highSchool.id}/edit" />
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

<h1>Edit HighSchool page</h1>

<%-- <c:if test="${param.saved == true}">
	<div class="info alert">
		HighSchool saved. <a href="${messageUrl}">View it</a>
	</div>
</c:if> --%>

<form:form cssClass="main" action="${editHighSchoolFormUrl}"
	modelAttribute="highSchool">

	<%-- <p>
		<spring:message code="newUserRegistration.message.allFieldsRequired" />
	</p> --%>
	
	<!--  -->
	
	<div class="form-group row">
			<!-- <label for="name" class="col-sm-2 control-label">School Name</label> -->
			<label for="name" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.name" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input class="form-control" path="name" placeholder = "Your School Name" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="name" htmlEscape="false" />
		    </div>
		</div>
		
		<div class="form-group row">
			<!-- <label for="state" class="col-sm-2 control-label">State</label> -->
			<label for="state" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.state" />
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
				<spring:message code="highSchoolForm.label.city" />
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
				<spring:message code="highSchoolForm.label.country" />
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
				<spring:message code="highSchoolForm.label.zip" />
			</label>
			<div class = "col-sm-5">
				<form:input path="zip" class = "form-control" id = "zip" placeholder = "Your zip"/>
			</div>
			<div class="col-sm-5">
		    	<form:errors class="errormsg" path="zip" htmlEscape="false" />
		    </div>
        </div>
        
		<div class="form-group row">
			<label for="attendedFrom" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.attendedFrom" />
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
				<spring:message code="highSchoolForm.label.attendedTo" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input id="attendedTo" path="attendedTo" class="form-control" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="attendedTo" htmlEscape="false" />
		    </div>
		</div>
		
        <%-- <fmt:formatDate value="${highSchool.attendedFrom}" var="attendedFromString" pattern="dd/MM/yyyy" />
        <div class="form-group row">
			<!-- <label for="attendedFrom" class="col-sm-2 control-label">attendedFrom</label> -->
			<label for="attendedFrom" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.attendedFrom" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input path="attendedFrom" value="${attendedFromString}" class="form-control" id="attendedFrom" placeholder="Your attendedFrom" />
			</div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="attendedFrom" htmlEscape="false" />
		    </div>
		</div>
        
        <fmt:formatDate value="${highSchool.attendedTo}" var="attendedToString" pattern="dd/MM/yyyy" />
        <div class="form-group row">
			<!-- <label for="attendedTo" class="col-sm-2 control-label">attendedTo</label> -->
			<label for="attendedTo" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.attendedTo" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input path="attendedTo" value="${attendedToString}" class="form-control" id="attendedTo" placeholder="Your attendedTo" />
			</div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="attendedTo" htmlEscape="false" />
		    </div>
		</div> --%>
		
		<div class="form-group checkbox">
			<!-- <label for="diplome" class="col-sm-2 control-label">Diplome</label> -->
			<label for="diplome" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.diplome" />
			</label>
			<div class = "col-sm-10">
				<form:checkbox id="diplome" path="diplome" />
			</div>
		</div>
		
		<div class="form-group checkbox">
			<!-- <label for="gED" class="col-sm-2 control-label">gED</label> -->
			<label for="gED" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.gED" />
			</label>
			<div class = "col-sm-10">
				<form:checkbox id="gED" path="gED" />
			</div>
		</div>
        
        <div class="form-group row">
			<label for="diplomeAwardedDate" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.diplomeAwardedDate" />
			</label>
		    <div class="col-sm-5">
		      <form:input id="diplomeAwardedDate" path="diplomeAwardedDate" class="form-control" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="diplomeAwardedDate" htmlEscape="false" />
		    </div>
		</div>
		
        <div class="form-group row">
			<label for="gEDAwardedDate" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.gEDAwardedDate" />
			</label>
		    <div class="col-sm-5">
		      <form:input id="gEDAwardedDate" path="gEDAwardedDate" class="form-control" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="gEDAwardedDate" htmlEscape="false" />
		    </div>
		</div>
		
        <%-- <fmt:formatDate value="${highSchool.diplomeAwardedDate}" var="diplomeAwardedDateString" pattern="dd/MM/yyyy" />
        <div class="form-group row">
			<!-- <label for="diplomeAwardedDate" class="col-sm-2 control-label">diplomeAwardedDate</label> -->
			<label for="diplomeAwardedDate" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.diplomeAwardedDate" />
			</label>
		    <div class="col-sm-5">
		      <form:input path="diplomeAwardedDate" value="${diplomeAwardedDateString}" class="form-control" id="diplomeAwardedDate" placeholder="Your diplomeAwardedDate" />
			</div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="diplomeAwardedDate" htmlEscape="false" />
		    </div>
		</div>
        
        <fmt:formatDate value="${highSchool.gEDAwardedDate}" var="gEDAwardedDateString" pattern="dd/MM/yyyy" />
        <div class="form-group row">
			<!-- <label for="gEDAwardedDate" class="col-sm-2 control-label">gEDAwardedDate</label> -->
			<label for="gEDAwardedDate" class="col-sm-2 control-label">
				<spring:message code="highSchoolForm.label.gEDAwardedDate" />
			</label>
		    <div class="col-sm-5">
		      <form:input path="gEDAwardedDate" value="${gEDAwardedDateString}" class="form-control" id="gEDAwardedDate" placeholder="Your gEDAwardedDate" />
			</div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="gEDAwardedDate" htmlEscape="false" />
		    </div>
		</div> --%>
		
        <div class = "form-group">
        	<label for="diplome" class="col-sm-2 control-label">&nbsp;</label>
        	<div class = "col-sm-10">
        		<input class="btn btn-primary" type="submit" value="Update"></input>
        		<a class = "btn btn-default" href="${myEducationUrl}">Cancel</a>
        	</div>
        </div>
        
</form:form>

<script>
	$(function (){
		$('#attendedFrom').datepicker();
		$('#attendedTo').datepicker();
		$('#diplomeAwardedDate').datepicker();
		$('#gEDAwardedDate').datepicker();
	});
 </script>