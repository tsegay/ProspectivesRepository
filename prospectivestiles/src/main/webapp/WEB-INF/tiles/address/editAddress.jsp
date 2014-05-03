<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h1>Address editAddress.jsp</h1>
<form:form action="${editAddressUrl}" modelAttribute="address"
	role="form" class="form-horizontal">
	<div class="modal-header">
		<h4>Add Address</h4>
		address:
		<c:if test="${address.id > 0}">
			<c:out value="${address.id}" />
		</c:if>
		<br /> address.userEntity.id:
		<c:if test="${address.id > 0}">
			<c:out value="${address.userEntity.id}" />
		</c:if>
	</div>
	<div class="modal-body">

		<div class="form-group row">
			<label for="addressType" class="col-sm-2 control-label">AddressType</label>
			<div class="col-sm-10">
				<form:select path="addressType" class="form-control">
					<%-- <form:option value="NONE" label="--- Select ---" /> --%>
					<form:option value="HOME_ADDRESS" label="HOME_ADDRESS" />
					<form:option value="WORK_ADDRESS" label="WORK_ADDRESS" />
					<form:option value="MAILING_ADDRESS" label="MAILING_ADDRESS" />
					<form:option value="FOREIGN_COUNTRY_ADDRESS"
						label="FOREIGN_COUNTRY_ADDRESS" />
				</form:select>
			</div>
		</div>

		<div class="form-group">
			<label for="address1" class="col-sm-2 control-label">address1</label>
			<div class="col-sm-10">
				<form:input class="form-control" path="address1"
					placeholder="Your address1" />
			</div>
		</div>

		<div class="form-group">
			<label for="address2" class="col-sm-2 control-label">address2</label>
			<div class="col-sm-10">
				<form:input class="form-control" path="address2"
					placeholder="Your address2" />
			</div>
		</div>

		<div class="form-group">
			<label for="city" class="col-sm-2 control-label">city</label>
			<div class="col-sm-10">
				<form:input path="city" class="form-control" id="city"
					placeholder="Your city" />
			</div>
		</div>

		<div class="form-group row">
			<label for="state" class="col-sm-2 control-label">state</label>
			<div class="col-sm-5">
				<form:input path="state" class="form-control"
					placeholder="Your state" />
			</div>
			<div class="col-sm-5">
				<form:errors path="state" htmlEscape="false" />
			</div>
		</div>
		
		<div class="form-group row">
			<label for="zipcode" class="col-sm-2 control-label">zipcode</label>
			<div class="col-sm-5">
				<form:input path="zipcode" class="form-control"
					placeholder="Your zipcode" />
			</div>
			<div class="col-sm-5">
				<form:errors path="zipcode" htmlEscape="false" />
			</div>
		</div>
		
		<div class="form-group row">
			<label for="country" class="col-sm-2 control-label">country</label>
			<div class="col-sm-5">
				<form:input path="country" class="form-control"
					placeholder="Your country" />
			</div>
			<div class="col-sm-5">
				<form:errors path="country" htmlEscape="false" />
			</div>
		</div>


	</div>

	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form>
