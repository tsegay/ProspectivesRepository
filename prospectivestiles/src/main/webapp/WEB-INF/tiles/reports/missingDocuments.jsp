<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">

	<c:url var="getMissingDocuments" value="/admin/report/${userEntity.id}/missingDocuments" />
	
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS')">
</sec:authorize>


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

<h1>Missing Documents</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">Checklist deleted.</div>
</c:if>

		
<c:choose>
	<c:when test="${empty missingDocuments}">
		<p>There are no missing documents for this student.</p>
	</c:when>
	<c:otherwise>
	
		<div class="row">
  			<div class="col-md-2">
				<a href="${getMissingDocuments}" class="btn btn-primary btn-lg" target="_blank">Download</a>
			</div>
			
		<!-- Email MissingDocuments attachment to student -->
			<div class="col-md-2">
				<c:url var="emailMissingDocuments" value="/admin/report/${userEntity.id}/missingDocuments/email" />
				<form action="${emailMissingDocuments}" method="post">
					<div>
						<input class="btn btn-primary btn-lg" type="submit" value="Email to Student" />
					</div>
				</form>
			</div>
		</div>
		
		<br />
		<br />
		
		<p>Dear <c:out value="${userEntity.fullName}" />:</p>
		<p>The admission office is processing your application. The office has conducted initial review on your files
		to process you application but you have some missing documents. 
		Please submit the missing documetns listed below. Upon completion of your 
		required files the admission officer will evaluate your documents inorder to grant you admission.
		</p>
		<p>These documents are missing from your file.</p>
		
		<ol>
			<c:forEach var="missingDocument" items="${missingDocuments}">
				<li><c:out value="${missingDocument}" /></li>			
			</c:forEach>
		</ol>
		<p><strong>Admission Officer Name</strong></p>
		<p>
			<c:out value="${admissionOfficerName}" />
		</p>
						
	</c:otherwise>
</c:choose>





<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!-- don't need the modal js -->
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