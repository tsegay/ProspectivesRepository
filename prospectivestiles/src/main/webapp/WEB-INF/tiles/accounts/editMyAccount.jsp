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
			<label for="username" class="col-sm-2 control-label">username</label>
			<div class="col-sm-10">
				<form:input class="form-control" path="username"
					placeholder="Your username" />
			</div>
		</div>

		<div class="form-group">
			<label for="email" class="col-sm-2 control-label">email</label>
			<div class="col-sm-10">
				<form:input class="form-control" path="email"
					placeholder="Your email" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="firstName" class="col-sm-2 control-label">firstName</label>
			<div class="col-sm-10">
				<form:input path="firstName" class="form-control" id="firstName"
					placeholder="Your firstName" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="middleName" class="col-sm-2 control-label">middleName</label>
			<div class="col-sm-10">
				<form:input class="form-control" path="middleName"
					placeholder="Your middleName" />
			</div>
		</div>
		<div class="form-group">
			<label for="lastName" class="col-sm-2 control-label">lastName</label>
			<div class="col-sm-10">
				<form:input class="form-control" path="lastName"
					placeholder="Your lastName" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="homePhone" class="col-sm-2 control-label">homePhone</label>
			<div class="col-sm-10">
				<form:input class="form-control" path="homePhone"
					placeholder="Your homePhone" />
			</div>
		</div>
		<div class="form-group">
			<label for="cellPhone" class="col-sm-2 control-label">cellPhone</label>
			<div class="col-sm-10">
				<form:input class="form-control" path="cellPhone"
					placeholder="Your cellPhone" />
			</div>
		</div>
		
		<div class="form-group">
			<label for="gender" class="col-sm-2 control-label">gender</label>
			<div class="col-sm-10">
				<form:input path="gender" class="form-control" id="gender"
					placeholder="Your gender" />
			</div>
		</div>
		<div class="form-group">
			<label for="ssn" class="col-sm-2 control-label">ssn</label>
			<div class="col-sm-10">
				<form:input path="ssn" class="form-control" id="ssn"
					placeholder="Your ssn" />
			</div>
		</div>
		<div class="form-group">
			<label for="citizenship" class="col-sm-2 control-label">citizenship</label>
			<div class="col-sm-10">
				<form:input path="citizenship" class="form-control" id="citizenship"
					placeholder="Your citizenship" />
			</div>
		</div>
		<div class="form-group">
			<label for="ethnicity" class="col-sm-2 control-label">ethnicity</label>
			<div class="col-sm-10">
				<form:input path="ethnicity" class="form-control" id="ethnicity"
					placeholder="Your ethnicity" />
			</div>
		</div>
		<div class="form-group">
			<label for="sevisNumber" class="col-sm-2 control-label">sevisNumber</label>
			<div class="col-sm-10">
				<form:input path="sevisNumber" class="form-control" id="sevisNumber"
					placeholder="Your sevisNumber" />
			</div>
		</div>
		
		<%-- Use timeStyle="short" so jquery.tablesorter can parse column as date --%>
		<fmt:formatDate value="${userEntity.dob}" var="dateString" pattern="dd/MM/yyyy" />
		
		<div class="form-group">
			<label for="dob" class="col-sm-2 control-label">dob</label>
			<div class="col-sm-10">
				<form:input path="dob" value="${dateString}" class="form-control" id="dob" placeholder="Your dob" />
			</div>
		</div>
				<%-- <form:input path="dob" class="form-control" id="dob" placeholder="Your dob" /> --%>
		
		<%-- <div class="form-group row">
			<label for="password" class="col-sm-2 control-label"><spring:message code="newUserRegistration.label.password" /></label>
		    <div class="col-sm-5">
		      <form:input path="password" class="form-control" placeholder = "Your password" />
		    </div>
		</div> --%>
		<%-- <div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="marketingOk" path="marketingOk" />
		    </div>
			<label for="marketingOk" class="col-sm-5 control-label"><spring:message code="newUserRegistration.label.marketingOk" /></label>
		    <div class="col-sm-5">
		    </div>
		</div> --%>
		<%-- <div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="transferee" path="transferee" />
		    </div>
			<label for="transferee" class="col-sm-5 control-label">transferee</label>
		    <div class="col-sm-5">
		    </div>
		</div> --%>
		
		<%-- <div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="acceptTerms" path="acceptTerms" />
		    </div>
			<label for="acceptTerms" class="col-sm-5 control-label"><spring:message code="newUserRegistration.label.acceptTerms" /></label>
		</div> --%>
		
		<%-- <form:hidden path="password" />
		<form:hidden path="acceptTerms" /> --%>


	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form>
