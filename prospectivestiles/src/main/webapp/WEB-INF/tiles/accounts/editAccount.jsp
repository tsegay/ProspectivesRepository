<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="accountsUrl" value="/accounts/${userEntity.id}" />
	<c:url var="editAccountUrl" value="/accounts/${userEntity.id}/edit" />
	<c:url var="deleteAccountUrl" value="" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<%-- <c:url var="accountsUrl" value="/myAccount/accounts" /> --%>
	<%-- <c:url var="editAccountUrl" value="/myAccount/account/${userEntity.id}" /> --%>
	<c:url var="deleteAccountUrl" value="" />
</sec:authorize>

<h1>editAccount.jsp</h1>

<form:form action="${editAccountUrl}"
	modelAttribute="userEntity" role="form" class="form-horizontal">

		<div class="form-group row">
			<label for="username" class="col-sm-2 control-label">username</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="username"
					placeholder="Your username" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="username" htmlEscape="false" />
			</div>
		</div>
	
		<div class="form-group row">
			<label for="email" class="col-sm-2 control-label">email</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="email"
					placeholder="Your email" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="email" htmlEscape="false" />
			</div>
		</div>
	
		<div class="form-group row">
			<label for="firstName" class="col-sm-2 control-label">firstName</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="firstName"
					placeholder="Your firstName" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="firstName" htmlEscape="false" />
			</div>
		</div>
	
		<div class="form-group row">
			<label for="middleName" class="col-sm-2 control-label">middleName</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="middleName"
					placeholder="Your middleName" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="middleName" htmlEscape="false" />
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
			<label for="homePhone" class="col-sm-2 control-label">homePhone</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="homePhone"
					placeholder="Your homePhone" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="homePhone" htmlEscape="false" />
			</div>
		</div>
	
		<div class="form-group row">
			<label for="cellPhone" class="col-sm-2 control-label">cellPhone</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="cellPhone"
					placeholder="Your cellPhone" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="cellPhone" htmlEscape="false" />
			</div>
		</div>
	
		<div class="form-group row">
			<label for="gender" class="col-sm-2 control-label">gender</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="gender"
					placeholder="Your gender" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="gender" htmlEscape="false" />
			</div>
		</div>
	
		<div class="form-group row">
			<label for="ssn" class="col-sm-2 control-label">ssn</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="ssn"
					placeholder="Your ssn" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="ssn" htmlEscape="false" />
			</div>
		</div>
	
		<div class="form-group row">
			<label for="citizenship" class="col-sm-2 control-label">citizenship</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="citizenship"
					placeholder="Your citizenship" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="citizenship" htmlEscape="false" />
			</div>
		</div>
	
	
		<div class="form-group row">
			<label for="ethnicity" class="col-sm-2 control-label">ethnicity</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="ethnicity"
					placeholder="Your ethnicity" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="ethnicity" htmlEscape="false" />
			</div>
		</div>
	
		<div class="form-group row">
			<label for="sevisNumber" class="col-sm-2 control-label">sevisNumber</label>
			<div class="col-sm-5">
				<form:input class="form-control" path="sevisNumber"
					placeholder="Your sevisNumber" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="sevisNumber" htmlEscape="false" />
			</div>
		</div>
	
		<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
		<fmt:formatDate value="${userEntity.dob}" var="dateString" pattern="dd/MM/yyyy" />
		<div class="form-group row">
			<label for="dob" class="col-sm-2 control-label">dob</label>
			<div class="col-sm-5">
				<form:input path="dob" value="${dateString}" class="form-control" id="dob" placeholder="Your dob" />
			</div>
			<div class="col-sm-5">
				<form:errors class="errormsg" path="sevisNumber" htmlEscape="false" />
			</div>
		</div>
		

		<%-- <div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="transferee" path="transferee" />
		    </div>
			<label for="transferee" class="col-sm-5 control-label">transferee</label>
		    <div class="col-sm-5">
		    </div>
		</div> --%>

	
		<div class="form-group">
			<label for="" class="col-sm-2 control-label">&nbsp;</label>
			<div class="col-sm-10">
				<input class="btn btn-primary" type="submit" value="Save"></input> 
				<a class="btn btn-default" href="${accountsUrl}">Cancel</a>
			</div>
		</div>

</form:form>

