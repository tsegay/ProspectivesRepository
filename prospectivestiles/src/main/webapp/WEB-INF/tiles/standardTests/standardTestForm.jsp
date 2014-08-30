<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="standardTestsUrl" value="/accounts/${userEntity.id}/standardTests" />
	<c:url var="newStandardTestsUrl" value="/accounts/${userEntity.id}/standardTest/new" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="newStandardTestsUrl" value="/myAccount/standardTest/new" />
</sec:authorize>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<div class="well well-sm row">
		<div class="col-sm-3">
			<img
				src="${pageContext.request.contextPath}/resources/images/placeholderImage_140x140.jpg"
				alt="Your Pic" class="img-rounded profileImg">
		</div>
		<dl class="dl-horizontal col-sm-9">
			<dt>Full name:</dt>
			<dd>
				<c:out value="${userEntity.firstName}"></c:out>
				<c:out value="${userEntity.lastName}"></c:out>
			</dd>
			<dt>Username</dt>
			<dd>
				<c:out value="${userEntity.username}" />
			</dd>
		</dl>
	</div>
</sec:authorize>

<h1>Standard Test Form</h1>
<br>
<br>
<h4>Please enter all the standard tests (TOEFL, IELTS) you took</h4>
<br>

<form:form action="${newStandardTestsUrl}" id="standardTestForm"
	modelAttribute="standardTest" role="form" class="form-horizontal">

	<div class="form-group row">
		<!-- <label for="name" class="col-sm-2 control-label">Test Name</label> -->
		<label for="name" class="col-sm-2 control-label">
			<spring:message code="standardTestsForm.label.name" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
	    <div class="col-sm-5">
	      <form:input path="name" class="form-control" placeholder = "Your test" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors class="errormsg" path="name" htmlEscape="false" />
	    </div>
	</div>
	
	 <div class="form-group row">
		<label for="score" class="col-sm-2 control-label">
			<spring:message code="standardTestsForm.label.score" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
	    <div class="col-sm-5">
	      <form:input path="score" class="form-control" placeholder = "Your score" />
	    </div>
	    <div class="col-sm-5">
	    	<form:errors class="errormsg" path="score" htmlEscape="false" />
	    </div>
	</div>
	
	
	<%-- <fmt:formatDate value="${standardTest.validTill}" var="validTillString" pattern="dd/MM/yyyy" />
    <div class="form-group row">
		<label for="validTill" class="col-sm-2 control-label">
			<spring:message code="standardTestsForm.label.validTill" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
	    <div class="col-sm-5">
	      <form:input path="validTill" value="${validTillString}" class="form-control" id="validTill" placeholder="Test valid till ..." />
		</div>
	    <div class="col-sm-5">
	    	<form:errors class="errormsg" path="validTill" htmlEscape="false" />
	    </div>
	</div> --%>

    <div class="form-group row">
		<label for="validTill" class="col-sm-2 control-label">
			<spring:message code="standardTestsForm.label.validTill" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
	    <div class="col-sm-5">
	      <form:input id="validTill" path="validTill" class="form-control" />
	      <!-- <input type="text" id="validTill"> -->
	      <%-- <form:input id="dob" path="dob" class="form-control" type="date" format="mm/dd/yyyy" /> --%>
	    </div>
	    <div class="col-sm-5">
	    	<form:errors class="errormsg" path="validTill" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<!-- submit calls the post method standardTestsUrl -->
			<input class="btn btn-primary" type="submit" value="Save"></input> 
			<!-- cancel calls returns to the all tests page -->
			<a class="btn btn-default" href="${standardTestsUrl}">Cancel</a>
		</div>
	</div>

</form:form>

<script>
	$(function (){
		$('#validTill').datepicker({
			format : 'mm/dd/yyyy'
		});
		/* $('#validTill').datepicker(); */
	});
 </script>

