<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<h1>All Accounts Page</h1>

<h3><a class="btn btn-info" href="<c:url value='/registrationform'/>">Register</a></h3>

<div class="table-responsive">
	<!-- <table class="footable table"> -->
	<table class="table table-hover table-striped" data-filter="#filter" data-page-size="5"> 
		 <thead>
			<tr>
				<th data-toggle="true">Username</th>
				<th data-sort-initial="true">firstName</th>
				<th>lastName</th>
				<th data-hide="phone,tablet" data-name="Email address">Email</th>
				<th>acceptTerms</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>
					<c:url var="myAccountUrl" value="/accounts/${user.id}" />
					<a href="${myAccountUrl}"><c:out value="${user.username}"></c:out></a>
				</td>
				<td><c:out value="${user.firstName}"></c:out></td>
				<td><c:out value="${user.lastName}"></c:out></td>
				<td><c:out value="${user.email}"></c:out></td>
				<td><c:out value="${user.acceptTerms}"></c:out></td>
			</tr>
		</c:forEach>
		</tbody>
		<tfoot>
			<ul class="pagination">
			  <li><a href="#">&laquo;</a></li>
			  <c:forEach var="i" begin="1" end="${totalPages}">
				   <c:choose> 
					  <c:when test="${i == page}" > 
					    <li class="active"><a href="<c:url value='/accounts/accounts/${i}'/>"><c:out value="${i}"/></a></li> 
					  </c:when> 
					  <c:otherwise> 
					    <li><a href="<c:url value='/accounts/accounts/${i}'/>"><c:out value="${i}"/></a></li>
					  </c:otherwise> 
					</c:choose> 
				</c:forEach>
			  <li><a href="#">&raquo;</a></li>
			</ul>
        </tfoot>
	</table>
                   		usersCount:<c:out value="${usersCount}" />
                   		pageSize:<c:out value="${pageSize}" />
                   		page:<c:out value="${page}" />
                   		totalPages:<c:out value="${totalPages}" />
</div>
		


