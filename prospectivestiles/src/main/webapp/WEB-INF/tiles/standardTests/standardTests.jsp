<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
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

<h1>Standard Test</h1>

<br><br>
<p>Please enter all standard test (TOEFL, IELTS) you took.</p>
<br><br>

<c:if test="${param.deleted == true}">
	<div class="info alert">StandardTest deleted.</div>
</c:if>

<c:choose>
	<c:when test="${empty standardTests}">
		<p>No StandardTest.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>name</th>
					<th>score</th>
					<th>validTill</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
	
				<c:forEach var="standardTest" items="${standardTests}">
				
				 <sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
					<c:url var="editStandardTestUrl" value="/accounts/${standardTest.userEntity.id}/standardTest/${standardTest.id}/edit" />
					<c:url var="deleteStandardTestUrl" value="/accounts/${standardTest.userEntity.id}/standardTest/${standardTest.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
					<c:url var="editStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}/edit" />
					<c:url var="deleteStandardTestUrl" value="/myAccount/standardTest/${standardTest.id}/delete" />
				</sec:authorize>
				
				<tr>
					<td><c:out value="${standardTest.id}"></c:out>
					</td>
					<td><c:out value="${standardTest.name}"></c:out></td>
					<td><c:out value="${standardTest.score}"></c:out></td>
					<td>
					<fmt:formatDate var="validTillString" value="${standardTest.validTill}" pattern="MM-dd-yyyy" />
					<c:out value="${validTillString}" />
					</td>
					<td>
						<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
							<a href="${editStandardTestUrl}" class="btn btn-primary btn-md">Edit</a>
						</sec:authorize>
					</td>
					<td>
						<!-- Button trigger modal -->
						<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
							<a data-toggle="modal" data-remote="${deleteStandardTestUrl}" data-target="#deleteStandardTestModal" 
								class="btn btn-danger btn-sm">Delete</a><br><br>
						</sec:authorize>
						
						<!-- delete address Modal -->
						<div class="modal fade" id="deleteStandardTestModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
						  <div class="modal-dialog">
						    <div class = "modal-content">
						    
							</div>
						  </div>
						</div>
						
						<%-- <form id="deleteForm" action="${deleteStandardTestUrl}" method="post">
							<div><input type="submit" value="DELETE" /></div>
						</form> --%>
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="newStandardTestUrl" value="/accounts/${userEntity.id}/standardTest/new" />
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING')">
	<c:url var="newStandardTestUrl" value="/myAccount/standardTest/new" />
</sec:authorize>
		
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">	
	<h3>
		<a href="${newStandardTestUrl}">Add New Standard Test</a>
	</h3>
</sec:authorize>


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