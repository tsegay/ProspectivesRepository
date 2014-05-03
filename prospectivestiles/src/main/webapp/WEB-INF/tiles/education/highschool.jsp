<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="accountsPath" value="/accounts" />

<c:set var="user" value="${highSchool.userEntity}" />
<%-- <c:url var="highSchoolFormUrl" value="/accounts/${user.id}/highSchool" /> --%>
<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="highSchoolFormUrl" value="/accounts/${user.id}/highSchool" />
	<c:url var="myEducationUrl" value="/accounts/${user.id}/educations" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="highSchoolFormUrl" value="/myAccount/highSchool" />
	<c:url var="myEducationUrl" value="/myAccount/educations" />
</sec:authorize>

<h1>HighSchool Form page</h1>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<div class="well well-sm">
		<dl class="dl-horizontal">
			<dt>Full name:</dt>
			<dd>
				<c:out value="${userEntity.firstName}"></c:out> <c:out value="${userEntity.lastName}"></c:out>
			</dd>
			<dt>Username</dt>
			<dd>
				<c:out value="${userEntity.username}" />
			</dd>
		</dl>
	</div>
</sec:authorize>

<div class="yui-u">
	HighSchool for:
	<c:out value="${highSchool.userEntity.username}" />
</div>

<a class = "btn btn-default" href="${myEducationUrl}">Back to Educations</a>


