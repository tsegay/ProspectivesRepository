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

<h1>Employer page</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">Employer deleted.</div>
</c:if>

<c:choose>
	<c:when test="${empty employers}">
		<p>No Employer.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>position</th>
					<th>employedFrom</th>
					<th>employedTo</th>
					<th>companyName</th>
					<th>employerName</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
	
				<c:forEach var="employer" items="${employers}">
				
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url var="employerUrl"	value="/accounts/${employer.userEntity.id}/employer/${employer.id}" />
					<c:url var="editEmployerUrl" value="/accounts/${employer.userEntity.id}/employer/${employer.id}/edit" />
					<c:url var="deleteEmployerUrl" value="/accounts/${employer.userEntity.id}/employer/${employer.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url var="employerUrl"	value="/myAccount/employer/${employer.id}" />
					<c:url var="editEmployerUrl" value="/myAccount/employer/${employer.id}/edit" />
					<c:url var="deleteEmployerUrl" value="/myAccount/employer/${employer.id}/delete" />
				</sec:authorize>
				
				<tr>
					<td><c:out value="${employer.id}"></c:out>
						<%-- <a href="${employerUrl}"><c:out value="${employer.id}"></c:out></a> --%>
					</td>
					<td><c:out value="${employer.position}"></c:out></td>
					<td><c:out value="${employer.employedFrom}"></c:out></td>
					<td><c:out value="${employer.employedTo}"></c:out></td>
					<td><c:out value="${employer.companyName}"></c:out></td>
					<td><c:out value="${employer.employerName}"></c:out></td>
					<td>
						<a href="${editEmployerUrl}" class="btn btn-primary btn-lg">Edit</a>
					</td>
					<td>
						<form id="deleteForm" action="${deleteEmployerUrl}" method="post">
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
	<c:url var="newEmployerUrl" value="/accounts/${userEntity.id}/employer/new" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="newEmployerUrl" value="/myAccount/employer/new" />
</sec:authorize>
			
<h3>
	<a href="${newEmployerUrl}">Add New Employment</a>
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
	
