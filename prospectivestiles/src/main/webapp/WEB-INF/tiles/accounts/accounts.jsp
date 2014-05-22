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
					<c:url var="myAccountUrl" value="../accounts/${user.id}" />
					<a href="${myAccountUrl}"><c:out value="${user.username}"></c:out></a>
				</td>
				<td><c:out value="${user.firstName}"></c:out></td>
				<td><c:out value="${user.lastName}"></c:out></td>
				<td><c:out value="${user.email}"></c:out></td>
				<td><c:out value="${user.acceptTerms}"></c:out></td>
				
				
				
			</tr>
	
		</c:forEach>
		</tbody>
			<tfoot class="hide-if-no-paging">
                <tr>
                    <td colspan="5">
                        <div class="pagination pagination-centered"></div>
                    </td>
                </tr>
             </tfoot>
	</table>
</div>


	
<%-- <script rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/footable.core.css" type="text/css" ></script>
<script rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/footable.standalone.css" type="text/css" ></script>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script> --%>
		
<%-- <script src="${pageContext.request.contextPath}/resources/js/footable.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/footable.sort.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/footable.filter.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/footable.paginate.js"></script>

<script type="text/javascript">

	$(document).ready(function(){
		$('.footable').footable();
	});
</script> --%>
