<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<h1>Referrers</h1>

<div class="table-responsive">
	<table class="table table-hover table-striped">
		<tr>
			<th>Referrer</th>
			<th>Student</th>
			<th>Student Status</th>
			<!-- <th>Date Created</th> -->
		</tr>
	
		<c:forEach var="associatedUsersReferrers" items="${associatedUsersReferrers}">
	
			<tr>
				<td><c:out value="${associatedUsersReferrers.referrer}"></c:out></td>
				<td><c:out value="${associatedUsersReferrers.student.fullName}"></c:out></td>
				<td><c:out value="${associatedUsersReferrers.student.accountState}"></c:out></td>
				<%-- <td>
					<fmt:formatDate var="dateCreatedString" value="${userEntity.dateCreated}" pattern="MM-dd-yyyy" />
					<c:out value="${dateCreatedString}" />
				</td> --%>
			</tr>
	
		</c:forEach>
	
	</table>
</div>
