<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="user" value="${standardTest.userEntity}" />


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="standardTestsUrl" value="/accounts/${user.id}/standardTests" />
	<c:url var="editStandardTestUrl" value="/accounts/${user.id}/standardTest/${standardTest.id}" />
	<c:url var="deleteStandardTestUrl" value="/accounts/${user.id}/standardTest/${standardTest.id}/delete" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="editStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}" />
	<c:url var="deleteStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}/delete" />
</sec:authorize>

<h1>editStandardTest.jsp</h1>

<form:form action="${editStandardTestUrl}"
	modelAttribute="standardTest" role="form" class="form-horizontal">

		<div class="form-group row">
			<label for="name" class="col-sm-2 control-label">Test Name</label>
		    <div class="col-sm-5">
		      <form:input path="name" class="form-control" placeholder = "Your test" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="name" htmlEscape="false" />
		    </div>
		</div>
		
		 <div class="form-group row">
			<label for="score" class="col-sm-2 control-label">Score</label>
		    <div class="col-sm-5">
		      <form:input path="score" class="form-control" placeholder = "Your score" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="score" htmlEscape="false" />
		    </div>
		</div>
		
		<fmt:formatDate value="${standardTest.validTill}" var="validTillString" pattern="dd/MM/yyyy" />
	    <div class="form-group row">
			<label for="validTill" class="col-sm-2 control-label">Valid Till</label>
		    <div class="col-sm-5">
		      <form:input path="validTill" value="${validTillString}" class="form-control" id="validTill" placeholder="Test valid till ..." />
			</div>
		    <div class="col-sm-5">
		    	<form:errors path="validTill" htmlEscape="false" />
		    </div>
		</div>
	
	
	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<input class="btn btn-primary" type="submit" value="Save"></input> 
			<a class="btn btn-default" href="${standardTestsUrl}">Cancel</a>
		</div>
	</div>

</form:form>

<%-- <h1>editStandardTest.jsp Modal</h1>
<form:form action="${editStandardTestUrl}"
	modelAttribute="standardTest" role="form" class="form-horizontal">
	<div class="modal-header">
		<h4>Edit standardTest</h4>
		standardTest:
		<c:if test="${standardTest.id > 0}">
			<c:out value="${standardTest.id}" />
		</c:if>
		<br /> standardTest.userEntity.id:
		<c:if test="${standardTest.id > 0}">
			<c:out value="${standardTest.userEntity.id}" />
		</c:if>
	</div>
	<div class="modal-body">

		<div class="form-group row">
			<label for="name" class="col-sm-2 control-label">name</label>
		    <div class="col-sm-5">
		      <form:input path="name" class="form-control" placeholder = "Your name" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="name" htmlEscape="false" />
		    </div>
		</div>
        <div class="form-group row">
			<label for="score" class="col-sm-2 control-label">score</label>
		    <div class="col-sm-5">
		      <form:input path="score" class="form-control" placeholder = "Your score" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="score" htmlEscape="false" />
		    </div>
		</div>
        

	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form> --%>
