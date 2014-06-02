<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<%-- <sec:authentication var="userEntity" property="principal" /> --%>

<div class="well well-sm row">
	<div class="col-sm-3">
  		<img src="${pageContext.request.contextPath}/resources/images/placeholderImage_140x140.jpg" alt="Your Pic" class="img-rounded profileImg">
    </div>
	<dl class="dl-horizontal col-sm-9">
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


<h1 class="page-header">MyAccount Page</h1>
<p>
	Welcome
	<c:out value="${userEntity.username}" />
</p>


<dl class="dl-horizontal">
	
	<dt>id</dt>
	<dd>
		<c:out value="${userEntity.id}" />
	</dd>
	
	<dt>firstName</dt>
	<dd>
		<c:out value="${userEntity.firstName}" />
	</dd>

	<dt>middleName</dt>
	<dd>
		<c:out value="${userEntity.middleName}" />
	</dd>

	<dt>lastName</dt>
	<dd>
		<c:out value="${userEntity.lastName}" />
	</dd>

	<dt>Username</dt>
	<dd>
		<c:out value="${userEntity.username}" />
	</dd>

	<dt>E-mail</dt>
	<dd>
		<a href="mailto:${userEntity.email}">${userEntity.email}</a>
	</dd>
	
	<dt>marketingOk</dt>
	<dd>
		<c:out value="${userEntity.marketingOk}" />
	</dd>
	
	<dt>acceptTerms</dt>
	<dd>
		<c:out value="${userEntity.acceptTerms}" />
	</dd>
	
	<dt>dateCreated</dt>
	<dd>
		<c:out value="${userEntity.dateCreated}" />
	</dd>
	
	<dt>dob</dt>
	<dd>
		<c:out value="${userEntity.dob}" />
	</dd>
	
	<dt>gender</dt>
	<dd>
		<c:out value="${userEntity.gender}" />
	</dd>
	
	<dt>transferee</dt>
	<dd>
		<c:out value="${userEntity.transferee}" />
	</dd>
	<dt>homePhone</dt>
	<dd>
		<c:out value="${userEntity.homePhone}" />
	</dd>
	<dt>cellPhone</dt>
	<dd>
		<c:out value="${userEntity.cellPhone}" />
	</dd>
	<dt>ssn</dt>
	<dd>
		<c:out value="${userEntity.ssn}" />
	</dd>
	
	<dt>citizenship</dt>
	<dd>
		<c:out value="${userEntity.citizenship}" />
	</dd>
	
	<dt>ethnicity</dt>
	<dd>
		<c:out value="${userEntity.ethnicity}" />
	</dd>
	
	<dt>Account enabled</dt>
	<dd>
		<c:out value="${userEntity.enabled}" />
	</dd>

	<dt>Roles</dt>
	<dd>
		<c:forEach var="role" items="${userEntity.roles}">
			<c:out value="${role.name}" />
			<br />
		</c:forEach>
	</dd>

</dl>

<sec:authorize access="hasRole('ROLE_ADMIN')">
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="userEntityUrl"	value="/myAccount" />
	<c:url var="editUserEntityUrl" value="/myAccount/edit" />
	<c:url var="deleteUserEntityUrl" value="/myAccount/delete" />
</sec:authorize>

<h3>
	<a href="${editUserEntityUrl}" class="btn btn-primary btn-lg">Edit/Update</a>
</h3>


<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	
	<!-- 
		When I click on EDIT button the Modal is popluated with db datas for the current address. 
		When I clikc on Cancle and click on edit for another address, the selected value is populated to the modal.
		After 3 - 4 clicks the modal no more reloads.
		Using this js to fix that issue
	 -->
	<script>
		$(function (){
			$('body').on('hidden.bs.modal', '.modal', function () {
			    $(this).removeData('bs.modal');
			});
		});
	</script>
	<!-- Call the dropdowns via JavaScript  -->
	<script>
		$(document).ready(function () {
	        $('.dropdown-toggle').dropdown();
	    });
	</script>