<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<h1>Completed Prospective Students</h1>
<h4>Total completed applications: <c:out value="${completeCount}"></c:out></h4>

<div class="table-responsive">
	<table class="table table-hover table-striped">
		<tr>
			<th>ID</th>
			<th>Username</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Date of Birth</th>
			<th>Date Created</th>
			<!-- <th>Date Admitted</th> -->
			<!-- <th>Admission Counselor</th> -->
		</tr>
	
		<c:forEach var="evaluation" items="${completeEvaluations}">
	
			<tr>
				<td><c:out value="${evaluation.userEntity.id}"></c:out></td>
				<td><c:out value="${evaluation.userEntity.username}"></c:out></td>
				<td><c:out value="${evaluation.userEntity.firstName}"></c:out></td>
				<td><c:out value="${evaluation.userEntity.lastName}"></c:out></td>
				<td>
					<fmt:formatDate var="dobString" value="${evaluation.userEntity.dob}" pattern="MM-dd-yyyy" />
					<c:out value="${dobString}" />
				</td>
				<td>
					<fmt:formatDate var="dateCreatedString" value="${evaluation.userEntity.dateCreated}" pattern="MM-dd-yyyy" />
					<c:out value="${dateCreatedString}" />
				</td>
				<%-- <td><c:out value="${evaluation.dateAdmitted}"></c:out></td> --%>
				<%-- <td><c:out value="${evaluation.userEntity.}"></c:out></td> --%>
			</tr>
	
		</c:forEach>
	
	</table>
</div>
