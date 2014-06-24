<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<c:set var="user" value="${emergencyContact.userEntity}" />


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="emergencyContactsUrl" value="/accounts/${user.id}/emergencyContacts" />
	<c:url var="editEmergencyContactUrl" value="/accounts/${user.id}/emergencyContact/${emergencyContact.id}" />
	<c:url var="deleteEmergencyContactUrl" value="/accounts/${user.id}/emergencyContact/${emergencyContact.id}/delete" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="editEmergencyContactUrl" value="/myAccount/emergencyContact/${emergencyContact.id}" />
	<c:url var="deleteEmergencyContactUrl" value="/myAccount/emergencyContact/${emergencyContact.id}/delete" />
</sec:authorize>

<h1>editEmergencyContact.jsp</h1>

<form:form action="${editEmergencyContactUrl}"
	modelAttribute="emergencyContact" role="form" class="form-horizontal">

		<div class="form-group row">
			<label for="firstName" class="col-sm-2 control-label">firstName</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="firstName"
					placeholder="Your Emergency Contact firstName" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="firstName" htmlEscape="false" />
			</div>
		</div>
	
	<div class="form-group row">
		<label for="lastName" class="col-sm-2 control-label">lastName</label>
		<div class="col-sm-5">
			<form:input class="form-control" path="lastName"
				placeholder="Your lastName" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="lastName" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="phone" class="col-sm-2 control-label">phone</label>
		<div class="col-sm-5">
			<form:input path="phone" class="form-control" id="phone"
				placeholder="Your phone" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="phone" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="email" class="col-sm-2 control-label">email</label>
		<div class="col-sm-5">
			<form:input path="email" class="form-control" id="email"
				placeholder="Your email" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="email" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="relationship" class="col-sm-2 control-label">relationship</label>
		<div class="col-sm-5">
			<form:input path="relationship" class="form-control"
				id="relationship" placeholder="Your relationship" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="relationship" htmlEscape="false" />
		</div>
	</div>
	
	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<input class="btn btn-primary" type="submit" value="Save"></input> 
			<a class="btn btn-default" href="${emergencyContactsUrl}">Cancel</a>
		</div>
	</div>

</form:form>
