<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasRole('ROLE_ADMIN')">
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
</sec:authorize>

<h1>EmergencyContact page</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">EmergencyContact deleted.</div>
</c:if>

<c:choose>
	<c:when test="${empty emergencyContacts}">
		<p>No Address.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>firstName</th>
					<th>lastName</th>
					<th>phone</th>
					<th>email</th>
					<th>relationship</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
	
				<c:forEach var="emergencyContact" items="${emergencyContacts}">
				
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					<%-- <c:url var="emergencyContactUrl"	value="/accounts/${emergencyContact.userEntity.id}/emergencyContact/${emergencyContact.id}" /> --%>
					<c:url var="editEmergencyContactUrl" value="/accounts/${emergencyContact.userEntity.id}/emergencyContact/${emergencyContact.id}/edit" />
					<c:url var="deleteEmergencyContactUrl" value="/accounts/${emergencyContact.userEntity.id}/emergencyContact/${emergencyContact.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<%-- <c:url var="emergencyContactUrl"	value="/myAccount/emergencyContact/${emergencyContact.id}" /> --%>
					<c:url var="editEmergencyContactUrl" value="/myAccount/emergencyContact/${emergencyContact.id}/edit" />
					<c:url var="deleteEmergencyContactUrl" value="/myAccount/emergencyContact/${emergencyContact.id}/delete" />
				</sec:authorize>
				
				<tr>
					<td><c:out value="${emergencyContact.id}"></c:out>
						<%-- <a href="${emergencyContactUrl}"><c:out value="${emergencyContact.id}"></c:out></a> --%>
					</td>
					<td><c:out value="${emergencyContact.firstName}"></c:out></td>
					<td><c:out value="${emergencyContact.lastName}"></c:out></td>
					<td><c:out value="${emergencyContact.phone}"></c:out></td>
					<td><c:out value="${emergencyContact.email}"></c:out></td>
					<td><c:out value="${emergencyContact.relationship}"></c:out></td>
					<td>
						<a href="${editEmergencyContactUrl}" class="btn btn-primary btn-lg">Edit</a>
					</td>
					<td>
						<form id="deleteForm" action="${deleteEmergencyContactUrl}" method="post">
							<div><input type="submit" value="DELETE" /></div>
						</form>
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="newEmergencyContactUrl" value="/accounts/${userEntity.id}/emergencyContact/new" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="newEmergencyContactUrl" value="/myAccount/emergencyContact/new" />
</sec:authorize>
			
<h3>
	<a href="${newEmergencyContactUrl}">Add New EmergencyContact</a>
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
	
