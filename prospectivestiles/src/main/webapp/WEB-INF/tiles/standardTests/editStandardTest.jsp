<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="user" value="${standardTest.userEntity}" />


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<%-- <c:url var="standardTestsUrl" value="/accounts/${user.id}/standardTests" /> --%>
	<c:url var="editStandardTestUrl" value="/accounts/${user.id}/standardTest/${standardTest.id}/edit" />
	<c:url var="deleteStandardTestUrl" value="/accounts/${user.id}/standardTest/${standardTest.id}/delete" />
	<c:url var="educationUrl" value="/accounts/${userEntity.id}/educations" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS')">
	<%-- <c:url var="standardTestsUrl" value="/myAccount/standardTests" /> --%>
	<c:url var="editStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}/edit" />
	<c:url var="deleteStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}/delete" />
	<c:url var="educationUrl" value="/myAccount/educations" />
</sec:authorize>

<h1>Edit Standard Test</h1>

<form:form action="${editStandardTestUrl}"
	modelAttribute="standardTest" role="form" class="form-horizontal">

		<div class="form-group row">
			<!-- <label for="name" class="col-sm-2 control-label">Test Name</label> -->
			<label for="name" class="col-sm-2 control-label">
				<spring:message code="standardTestsForm.label.name" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		    	<form:select path="name" class="form-control">
					<%-- <form:option value="NONE" label="--- Select ---" /> --%>
					<form:option value="TOEFL" label="TOEFL" />
					<form:option value="IELTS" label="IELTS" />
				</form:select>
		      <%-- <form:input path="name" class="form-control" placeholder = "Your test" /> --%>
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
		
		<%-- <div class="form-group row">
			<label for="validTill" class="col-sm-2 control-label">
				<spring:message code="standardTestsForm.label.validTill" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
		    <div class="col-sm-5">
		      <form:input id="validTill" path="validTill" class="form-control" />
		      <!-- <input type="text" id="validTill"> -->
		    </div>
		    <div class="col-sm-5">
		    	<form:errors class="errormsg" path="validTill" htmlEscape="false" />
		    </div>
		</div> --%>
	
	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<input class="btn btn-primary" type="submit" value="Save"></input> 
			<a class="btn btn-default" href="${educationUrl}">Cancel</a>
		</div>
	</div>

</form:form>

<!-- <script>
	$(function (){
		$('#validTill').datepicker();
	});
</script> -->