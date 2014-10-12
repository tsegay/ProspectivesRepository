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
			<dt></dt>
			<dd class="text-right">
				<c:choose>
					<c:when test="${(userEntity.accountState == 'enrolled')}">
						<h3 class="red">ENROLLED</h3>
					</c:when>
				</c:choose>
			</dd>
		</dl>
	</div>
</sec:authorize>

<h1>Acceptance Letter</h1>

<!-- 
I don't think i need all these checklist urls
 -->
 
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<!-- checklistUrl name is already used. look up -->
	<%-- <c:url var="checklistUrl"	value="/accounts/${userEntity.id}/checklist/${userEntity.checklist.id}" /> --%>
	<c:url var="editChecklistUrl" value="/accounts/${userEntity.id}/checklist/${userEntity.checklist.id}" />
	<c:url var="deleteChecklistUrl" value="/accounts/${userEntity.id}/checklist/${userEntity.checklist.id}/delete" />
	
	<c:url var="acceptanceLetterUrl" value="/admin/report/${userEntity.id}/acceptanceLetter" />
	
</sec:authorize>
<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS')">
	<%-- <c:url var="checklistUrl"	value="/myAccount/checklist/${userEntity.checklist.id}" /> --%>
	<c:url var="editChecklistUrl" value="/myAccount/checklist/${userEntity.checklist.id}" />
	<c:url var="deleteChecklistUrl" value="/myAccount/checklist/${userEntity.checklist.id}/delete" />
</sec:authorize>
		
<c:choose>
	<c:when test="${empty acceptanceLetterReport}">
		<p>Admission not granted yet.</p>
	</c:when>
	<c:otherwise>
	
		<c:choose>
			<c:when test="${acceptanceLetterReport.status == 'admitted'}">
			
				<div class="row">
		  			<div class="col-md-2">
						<a href="${acceptanceLetterUrl}" class="btn btn-primary btn-lg" target="_blank">Download</a>
					</div>
					
				<!-- Email acceptanceLetter attachment to student -->
				
					<%-- <div class="col-md-2">
						<c:url var="emailAcceptanceLetter" value="/admin/report/${userEntity.id}/acceptanceLetter/email" />
						<form action="${emailAcceptanceLetter}" method="post">
							<div>
								<input class="btn btn-primary btn-lg" type="submit" value="Email to Student" />
							</div>
						</form>
					</div> --%>
				</div>
				
				<br />
				<br />
			
				<p>Dear <c:out value="${userEntity.fullName}" />:</p>
				<p>Congratulations!!!</p>
				<p>The admission officer has approved your application for admission.
				</p>
				
				<p><strong>Admission Officer Name</strong></p>
				
				<p>
					<c:out value="${acceptanceLetterReport.admissionOfficerName}" />
				</p>
				
				<p><strong>Admitted By</strong></p>
				
				<p>
					<c:out value="${acceptanceLetterReport.admittedBy}" />
				</p>
				
				<p><strong>Date Admitted</strong></p>
				<p>
					<fmt:formatDate var="dateAdmittedString" value="${acceptanceLetterReport.dateAdmitted}" pattern="MM-dd-yyyy" />
					<c:out value="${dateAdmittedString}" />
					<%-- <c:out value="${acceptanceLetterReport.dateAdmitted}" /> --%>
				</p>
				
			</c:when>
			<c:otherwise>No acceptance letter yet.</c:otherwise>
		</c:choose>
		
	</c:otherwise>
</c:choose>





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