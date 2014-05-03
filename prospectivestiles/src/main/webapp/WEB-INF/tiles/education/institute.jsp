<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="accountsPath" value="/accounts" />

<c:set var="user" value="${institute.userEntity}" />
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="instituteFormUrl" value="/accounts/${user.id}/institute" />
	<c:url var="educationUrl" value="/accounts/${user.id}/educations" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="instituteFormUrl" value="/myAccount/institute" />
	<c:url var="educationUrl" value="/myAccount/educations" />
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

<h1>Institute Form page</h1>


<div class="yui-u">
	Institute for:
	<c:out value="${institute.userEntity.username}" />
</div>

<a class = "btn btn-default" href="${educationUrl}">Back to Educations</a>


