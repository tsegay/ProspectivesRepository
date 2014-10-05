<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

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

<h1>Term and Program of Study</h1>
<br><br>

<!-- if student has selected the term and program of study, show his selection and hide the add button
if student hasn't selected show the add button 
user can't delete term or program of study but they can change it.
I am using the same method to create and edit the term and program of study to 
insert the term and program of study to the userEntity using jdbc
-->

<c:choose>
	<c:when test="${empty userEntity.term}">
		<p>No Term and Program of Study selected.</p>
		
		<!-- Button trigger modal -->
		<p>Select term and program of study you are applying for:</p> <br />
		
		<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
			<button class="btn btn-primary btn-md" data-toggle="modal" data-target="#addTermModal">
			  Select
			</button>
		</sec:authorize>

	</c:when>
	<c:otherwise>
		
		<dl class="dl-horizontal">
			<dt><spring:message code="applyingFor.label.term" /></dt>
			<dd>
				<c:out value="${userEntity.term.name}"></c:out>
			</dd>
		
			<dt><spring:message code="applyingFor.label.programOfStudy" /></dt>
			<dd>
				<c:out value="${userEntity.programOfStudy.name}"></c:out>
			</dd>
			
			<dt></dt>

		</dl>
		<br>
		
		<div class="row">
			<div class="col-md-2">
				<sec:authorize access="hasAnyRole('ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS', 'ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
					<button class="btn btn-primary btn-sm" data-toggle="modal" data-target="#addTermModal">
					  Edit
					</button>
				</sec:authorize>
			</div>
		</div>
		
		
	</c:otherwise>
</c:choose>
	

<!-- address Modal -->
<div class="modal fade" id="addTermModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	        <form:form action="${applyingForUrl}" modelAttribute="userEntity" role="form" class = "form-horizontal">
	            <div class = "modal-header">
	                <h4>Select Term and Program of Study you are applying for</h4>
	            </div>
	            <div class = "modal-body">
	            
					
					<div class="form-group row">
						<!-- <label for="term" class="col-sm-3 control-label">term</label> -->
						<label for="term" class="col-sm-2 control-label">
							<spring:message code="applyingFor.label.term" />
							<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
						</label>
					    <div class="col-sm-5">
					      <form:select path="term.id">
							   <%-- <form:option value="NONE" label="--- Select ---"/> --%>
							   <%-- <form:options items="${terms}" /> --%>
							   <c:forEach var="term" items="${terms}">
							   		<form:option value="${term.id}" label="${term.name}"/>
							   </c:forEach>
							</form:select>
					    </div>
					    <div class="col-sm-3">
					    	<form:errors class="errormsg" path="term" htmlEscape="false" />
					    </div>
					</div>
					<div class="form-group row">
						<label for="programOfStudies" class="col-sm-2 control-label">
							<spring:message code="applyingFor.label.programOfStudies" />
							<span class="glyphicon glyphicon-asterisk red-asterisk"></span>
						</label>
					    <div class="col-sm-5">
					      <form:select path="programOfStudy.id">
							   <c:forEach var="programOfStudy" items="${programOfStudies}">
							   		<form:option value="${programOfStudy.id}" label="${programOfStudy.name}"/>
							   </c:forEach>
							</form:select>
					    </div>
					    <div class="col-sm-3">
					    	<form:errors class="errormsg" path="term" htmlEscape="false" />
					    </div>
					</div>
					
	            </div>
	            <div class = "modal-footer">
	        		<a class = "btn btn-default" data-dismiss = "modal">Cancel</a>    
	        		<input class="btn btn-primary" type="submit" value="Submit"></input>
	            </div>
	        </form:form>
	  </div>
  </div>
</div>


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