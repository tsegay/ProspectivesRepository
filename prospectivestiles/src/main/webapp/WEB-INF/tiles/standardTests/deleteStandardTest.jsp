<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="deleteStandardTestUrl" value="/accounts/${standardTest.userEntity.id}/standardTest/${standardTest.id}/delete" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="deleteStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}/delete" />
</sec:authorize>

<!-- 
########################################################
This is for Modal use
######################################################## 
-->

<form:form action="${deleteStandardTestUrl}" modelAttribute="address"
	role="form" class="form-horizontal">
	<div class="modal-header">
		<!-- <h4>Delete Address</h4> -->
	</div>
	<div class="modal-body">
		Are you sure you want to delete this Standard Test?
	</div>

	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-danger" type="submit" value="Delete"></input>
	</div>
</form:form> 
