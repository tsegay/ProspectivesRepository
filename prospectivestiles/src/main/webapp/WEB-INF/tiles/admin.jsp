<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<h1>Admin page</h1>
<h3>Authorised Users Only! Should display admins only</h3>

<div class="table-responsive">
	<table class="table table-hover table-striped">
		<tr>
			<th>Username</th>
			<th>firstName</th>
			<th>lastName</th>
			<th>Email</th>
			<th>acceptTerms</th>
		</tr>
	
		<c:forEach var="user" items="${users}">
	
			<tr>
				<td><c:out value="${user.username}"></c:out></td>
				<td><c:out value="${user.firstName}"></c:out></td>
				<td><c:out value="${user.lastName}"></c:out></td>
				<td><c:out value="${user.email}"></c:out></td>
				<td><c:out value="${user.acceptTerms}"></c:out></td>
			</tr>
	
		</c:forEach>
	
	</table>
</div>


<h3>testRestTemplate</h3>

<p><c:out value="${testRestTemplate.Name}"></c:out></p>
<p><c:out value="${testRestTemplate.About}"></c:out></p>
<p><c:out value="${testRestTemplate.Phone}"></c:out></p>
<p><c:out value="${testRestTemplate.Website}"></c:out></p>
