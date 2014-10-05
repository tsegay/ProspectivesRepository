<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="deleteAddressUrl" value="/accounts/${address.userEntity.id}/address/${address.id}/delete" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS')">
	<c:url var="deleteAddressUrl" value="/myAccount/address/${address.id}/delete" />
</sec:authorize>



<!-- 
########################################################
This is for Modal use
######################################################## 
-->

<form:form action="${deleteAddressUrl}" modelAttribute="address"
	role="form" class="form-horizontal">
	<div class="modal-header">
		<!-- <h4>Delete Address</h4> -->
	</div>
	<div class="modal-body">
		Are you sure you want to delete this address?
	</div>

	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-danger" type="submit" value="Delete"></input>
	</div>
</form:form> 
