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

<h1>Checklist Report</h1>

<!-- 
check why I am using this checlist urls
 -->
 
<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="getChecklistReport" value="/admin/report/${userEntity.id}/checklist" />
</sec:authorize>
		
<c:choose>
	<c:when test="${empty evaluation}">
		<p>Checklist is not created yet.</p>
	</c:when>
	<c:otherwise>
	
		<a href="${getChecklistReport}" class="btn btn-primary btn-lg" target="_blank">Print/Download</a>
		<br />
		<br />
		
		<!-- use userEntity.fullName -->
		<%-- <p>Student Name: <c:out value="${userEntity.fullName}" /></p>
		<p>
			I have carefully evaluated 
			<c:choose>
				<c:when test="${userEntity.gender == 'male'}">his</c:when>
				<c:when test="${userEntity.gender == 'female'}">her</c:when>
				<c:otherwise>his/her</c:otherwise>
			</c:choose>
			files.
			I grant
			<c:choose>
				<c:when test="${userEntity.gender == 'male'}">him</c:when>
				<c:when test="${userEntity.gender == 'female'}">her</c:when>
				<c:otherwise>him/her</c:otherwise>
			</c:choose>
			admission.
		</p> --%>
		
<%-- 		<p><strong>Admission Officer Report</strong></p>
		<p>
			<c:out value="${evaluationReportSummary.admnOfficerReport}" />
		</p>
		
		<p><strong>Admission Officer Name</strong></p>
		<p>
			<c:out value="${evaluationReportSummary.admissionOfficerName}" />
		</p>
		
		<p><strong>Date Last Modified</strong></p>
		<fmt:formatDate var="dateLastModifiedString" value="${evaluationReportSummary.dateLastModified}" pattern="MM-dd-yyyy" />
		<p>
			<c:out value="${dateLastModifiedString}" />
		</p> --%>
		
						
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