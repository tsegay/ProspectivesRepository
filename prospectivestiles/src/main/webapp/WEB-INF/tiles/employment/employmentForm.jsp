<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="employersUrl" value="/accounts/${userEntity.id}/employers" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="employersUrl" value="/myAccount/employers" />
</sec:authorize>

<sec:authorize access="hasRole('ROLE_ADMIN')">
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

<h1>Employment Form</h1>

<form:form action="${employersUrl}" modelAttribute="employer"
	role="form" class="form-horizontal">

	<%-- <div class="form-group checkbox row">
		<div class="col-sm-2">
			<form:checkbox class="form-control" id="employed" path="employed" />
		</div>
		<label for="employed" class="col-sm-5 control-label">employed</label>
		<div class="col-sm-5">
			<form:errors path="employed" htmlEscape="false" />
		</div>
	</div> --%>

	<div class="form-group row">
		<label for="position" class="col-sm-2 control-label">position</label>
		<div class="col-sm-5">
			<form:input path="position" class="form-control"
				placeholder="Your position" />
		</div>
		<div class="col-sm-5">
			<form:errors path="position" htmlEscape="false" />
		</div>
	</div>
	
	<fmt:formatDate value="${employer.employedFrom}"
		var="employedFromString" pattern="dd/MM/yyyy" />
	<div class="form-group row">
		<label for="employedFrom" class="col-sm-2 control-label">employedFrom</label>
		<div class="col-sm-5">
			<form:input path="employedFrom" value="${employedFromString}"
				class="form-control" id="employedFrom"
				placeholder="Your employedFrom" />
		</div>
		<div class="col-sm-5">
			<form:errors path="employedFrom" htmlEscape="false" />
		</div>
	</div>
	
	<fmt:formatDate value="${employer.employedTo}"
		var="employedToString" pattern="dd/MM/yyyy" />
	<div class="form-group row">
		<label for="employedTo" class="col-sm-2 control-label">employedTo</label>
		<div class="col-sm-5">
			<form:input path="employedTo" value="${employedToString}"
				class="form-control" id="employedTo"
				placeholder="Your employedTo" />
		</div>
		<div class="col-sm-5">
			<form:errors path="employedTo" htmlEscape="false" />
		</div>
	</div>
	
	<div class="form-group row">
		<label for="companyName" class="col-sm-2 control-label">companyName</label>
		<div class="col-sm-5">
			<form:input path="companyName" class="form-control"
				placeholder="Your companyName" />
		</div>
		<div class="col-sm-5">
			<form:errors path="companyName" htmlEscape="false" />
		</div>
	</div>
	
	<div class="form-group row">
		<label for="employerName" class="col-sm-2 control-label">employerName</label>
		<div class="col-sm-5">
			<form:input path="employerName" class="form-control"
				placeholder="Your employerName" />
		</div>
		<div class="col-sm-5">
			<form:errors path="employerName" htmlEscape="false" />
		</div>
	</div>
	

	

	


	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<!-- submit calls the post method employersUrl -->
			<input class="btn btn-primary" type="submit" value="Save"></input> 
			
			<!-- cancel calls the get method employersUrl -->
			<a class="btn btn-default" href="${employersUrl}">Cancel</a>
			
		</div>
	</div>
</form:form>



