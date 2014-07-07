<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



<h1>MyAccount editMyAccount.jsp</h1>
<form:form action="${editUserEntityUrl}" modelAttribute="userEntity"
	role="form" class="form-horizontal">
	<div class="modal-header">
		<h4>editUserEntityUrl</h4>
		userEntity.id:
		<c:if test="${userEntity.id > 0}">
			<c:out value="${userEntity.id}" />
		</c:if>
	</div>
	<div class="modal-body">


		<div class="form-group">
			<label for="username" class="col-sm-2 control-label">
				<spring:message code="account.label.username" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="username"
					placeholder="Your username" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="username" htmlEscape="false" />
			</div>
		</div>

		<div class="form-group">
			<label for="email" class="col-sm-2 control-label">
				<spring:message code="account.label.email" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="email"
					placeholder="Your email" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="email" htmlEscape="false" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="firstName" class="col-sm-2 control-label">
				<spring:message code="account.label.firstName" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
			<div class="col-sm-5">
				<form:input path="firstName" class="form-control" id="firstName"
					placeholder="Your firstName" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="firstName" htmlEscape="false" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="middleName" class="col-sm-2 control-label">
				<spring:message code="account.label.middleName" />
			</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="middleName"
					placeholder="Your middleName" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="middleName" htmlEscape="false" />
			</div>
		</div>
		<div class="form-group">
			<label for="lastName" class="col-sm-2 control-label">
				<spring:message code="account.label.lastName" />
				<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
			</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="lastName"
					placeholder="Your lastName" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="lastName" htmlEscape="false" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="homePhone" class="col-sm-2 control-label">
				<spring:message code="account.label.homePhone" />
			</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="homePhone"
					placeholder="Your homePhone" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="homePhone" htmlEscape="false" />
			</div>
		</div>
		<div class="form-group">
			<label for="cellPhone" class="col-sm-2 control-label">
				<spring:message code="account.label.cellPhone" />
			</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="cellPhone"
					placeholder="Your cellPhone" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="cellPhone" htmlEscape="false" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="gender" class="col-sm-2 control-label">
				<spring:message code="account.label.gender" />
			</label>
			<div class="col-sm-5">
				<form:input path="gender" class="form-control" id="gender"
					placeholder="Your gender" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="gender" htmlEscape="false" />
			</div>
		</div>
		<div class="form-group">
			<label for="ssn" class="col-sm-2 control-label">
				<spring:message code="account.label.ssn" />
			</label>
			<div class="col-sm-5">
				<form:input path="ssn" class="form-control" id="ssn"
					placeholder="Your ssn" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="ssn" htmlEscape="false" />
			</div>
		</div>
		<div class="form-group">
			<label for="citizenship" class="col-sm-2 control-label">
				<spring:message code="account.label.citizenship" />
			</label>
			<div class="col-sm-5">
				<form:input path="citizenship" class="form-control" id="citizenship"
					placeholder="Your citizenship" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="citizenship" htmlEscape="false" />
			</div>
		</div>
		<div class="form-group">
			<label for="ethnicity" class="col-sm-2 control-label">
				<spring:message code="account.label.ethnicity" />
			</label>
			<div class="col-sm-5">
				<form:input path="ethnicity" class="form-control" id="ethnicity"
					placeholder="Your ethnicity" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="ethnicity" htmlEscape="false" />
			</div>
		</div>
		<div class="form-group">
			<label for="sevisNumber" class="col-sm-2 control-label">
				<spring:message code="account.label.sevisNumber" />
			</label>
			<div class="col-sm-5">
				<form:input path="sevisNumber" class="form-control" id="sevisNumber"
					placeholder="Your sevisNumber" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="sevisNumber" htmlEscape="false" />
			</div>
		</div>
		
		<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
		<fmt:formatDate value="${userEntity.dob}" var="dateString" pattern="dd/MM/yyyy" />
		
		<div class="form-group">
			<label for="dob" class="col-sm-2 control-label">
				<spring:message code="account.label.dob" />
			</label>
			<div class="col-sm-5">
				<form:input path="dob" value="${dateString}" class="form-control" id="dob" placeholder="Your dob" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="dob" htmlEscape="false" />
			</div>
		</div>

	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form>
