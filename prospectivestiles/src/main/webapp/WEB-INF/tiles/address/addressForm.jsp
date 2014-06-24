<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="accountsUrl" value="/accounts/${userEntity.id}" />
	<c:url var="addressFormUrl"
		value="/accounts/${userEntity.id}/addresses" />
	<c:url var="addressUrl"
		value="/accounts/${userEntity.id}/addresses" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="addressFormUrl" value="/myAccount/addresses" />
	<c:url var="addressUrl" value="/myAccount/addresses" />
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

<h1>Address Form page</h1>

<form:form action="${addressFormUrl}" id="addressForm"
	modelAttribute="address" role="form" class="form-horizontal">

	<div class="form-group row">
		<label for="addressType" class="col-sm-2 control-label">AddressType</label>
		<div class="col-sm-5">
			<form:select path="addressType" class="form-control">
				<%-- <form:option value="NONE" label="--- Select ---" /> --%>
				<form:option value="HOME_ADDRESS" label="HOME_ADDRESS" />
				<form:option value="WORK_ADDRESS" label="WORK_ADDRESS" />
				<form:option value="MAILING_ADDRESS" label="MAILING_ADDRESS" />
				<form:option value="FOREIGN_COUNTRY_ADDRESS"
					label="FOREIGN_COUNTRY_ADDRESS" />
			</form:select>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="addressType" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="address1" class="col-sm-2 control-label">address1</label>
		<div class="col-sm-5">
			<form:input id="address1" path="address1" class="form-control"
				placeholder="Your address1" />
			<!-- <span class="hide help-inline">This is required</span> -->
			<div id="address1Error"></div>
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="address1" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="address2" class="col-sm-2 control-label">address2</label>
		<div class="col-sm-5">
			<form:input path="address2" class="form-control"
				placeholder="Your address2" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="address2" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="city" class="col-sm-2 control-label">city</label>
		<div class="col-sm-5">
			<form:input path="city" class="form-control" placeholder="Your city" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="city" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="state" class="col-sm-2 control-label">state</label>
		<div class="col-sm-5">
			<form:input path="state" class="form-control"
				placeholder="Your state" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="state" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="zipcode" class="col-sm-2 control-label">zipcode</label>
		<div class="col-sm-5">
			<form:input path="zipcode" class="form-control"
				placeholder="Your zipcode" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="zipcode" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="country" class="col-sm-2 control-label">country</label>
		<div class="col-sm-5">
			<form:input path="country" class="form-control"
				placeholder="Your country" />
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




