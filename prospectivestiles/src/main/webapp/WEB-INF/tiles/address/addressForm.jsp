<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="accountsUrl" value="/accounts/${userEntity.id}" />
	<c:url var="addressFormUrl" value="/accounts/${userEntity.id}/address/new" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS')">
	<c:url var="accountsUrl" value="/myAccount" />
	<c:url var="addressFormUrl" value="/myAccount/address/new" />
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

<h1>Address Form page</h1>

<form:form action="${addressFormUrl}" id="addressForm"
	modelAttribute="address" role="form" class="form-horizontal">

	<div class="form-group row">
		<!-- <label for="addressType" class="col-sm-2 control-label">AddressType 
		<span class="glyphicon glyphicon-asterisk red-asterisk"></span></label> -->
		
		
		<label for="addressType" class="col-sm-2 control-label">
			<spring:message code="addressForm.label.addressType" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
		
		<div class="col-sm-5">
			<form:select path="addressType" class="form-control">
				<%-- <form:option value="NONE" label="--- Select ---" /> --%>
				<form:option value="HOME_ADDRESS" label="Home Address" />
				<form:option value="WORK_ADDRESS" label="Work Address" />
				<form:option value="MAILING_ADDRESS" label="Mailing Address" />
				<form:option value="FOREIGN_COUNTRY_ADDRESS"
					label="Foreign Country Address" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="addressType" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="address1" class="col-sm-2 control-label">address1 <span class="glyphicon glyphicon-asterisk red-asterisk"></span></label>
		 -->
		 <label for="address1" class="col-sm-2 control-label">
			<spring:message code="addressForm.label.address1" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
		<div class="col-sm-5">
			<form:input id="address1" path="address1" class="form-control"
				placeholder="Your Address Line1. Eg. 100 Main St" />
			<!-- <span class="hide help-inline">This is required</span> -->
			<div id="address1Error"></div>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="address1" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="address2" class="col-sm-2 control-label">address2</label> -->
		<label for="address2" class="col-sm-2 control-label">
			<spring:message code="addressForm.label.address2" />
		</label>
		<div class="col-sm-5">
			<form:input path="address2" class="form-control"
				placeholder="Your Address Line 2. Eg. Apt 200" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="address2" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="city" class="col-sm-2 control-label">city <span class="glyphicon glyphicon-asterisk red-asterisk"></span></label>
		 -->
		 <label for="city" class="col-sm-2 control-label">
			<spring:message code="addressForm.label.city" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
		<div class="col-sm-5">
			<form:input path="city" class="form-control" placeholder="Your city" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="city" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="state" class="col-sm-2 control-label">state</label> -->
		<label for="state" class="col-sm-2 control-label">
			<spring:message code="addressForm.label.state" />
		</label>
		<div class="col-sm-5">
			<form:input path="state" class="form-control"
				placeholder="Your state" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="state" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="zipcode" class="col-sm-2 control-label">zipcode</label> -->
		<label for="zipcode" class="col-sm-2 control-label">
			<spring:message code="addressForm.label.zipcode" />
		</label>
		<div class="col-sm-5">
			<form:input path="zipcode" class="form-control"
				placeholder="Your zipcode" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="zipcode" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<!-- <label for="country" class="col-sm-2 control-label">country 
		<span class="glyphicon glyphicon-asterisk red-asterisk"></span></label>
		 -->
		 <label for="country" class="col-sm-2 control-label">
			<spring:message code="addressForm.label.country" />
			<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
		</label>
		<div class="col-sm-5">
			<form:select path="country.id">
			    <c:forEach var="c" items="${countries}">
			   		<form:option value="${c.id}" label="${c.name}"/>
			   </c:forEach>
			</form:select>
			<%-- <form:input path="country" class="form-control"
				placeholder="Your country" /> --%>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="country" htmlEscape="false" />
		</div>
	</div>



	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<input class="btn btn-primary" type="submit" value="Save"></input> <a
				class="btn btn-default" href="${accountsUrl}">Cancel</a>
		</div>
	</div>

</form:form>




