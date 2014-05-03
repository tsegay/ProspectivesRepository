<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h1>editEmployer.jsp</h1>
<form:form action="${editEmployerUrl}"
	modelAttribute="employer" role="form" class="form-horizontal">
	<div class="modal-header">
		<h4>Edit employer</h4>
		employer:
		<c:if test="${employer.id > 0}">
			<c:out value="${employer.id}" />
		</c:if>
		<br /> employer.userEntity.id:
		<c:if test="${employer.id > 0}">
			<c:out value="${employer.userEntity.id}" />
		</c:if>
	</div>
	<div class="modal-body">

		<div class="form-group checkbox row">
		    <div class="col-sm-2">
		    	<form:checkbox class="form-control" id="employed" path="employed" />
		    </div>
			<label for="employed" class="col-sm-5 control-label">employed</label>
		    <div class="col-sm-5">
		      	<%-- <form:errors path="employed" htmlEscape="false" /> --%>
		    </div>
		</div>
              <div class="form-group row">
			<label for="employerName" class="col-sm-2 control-label">employerName</label>
		    <div class="col-sm-5">
		      <form:input path="employerName" class="form-control" placeholder = "Your employerName" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="employerName" htmlEscape="false" />
		    </div>
		</div>
              <div class="form-group row">
			<label for="companyName" class="col-sm-2 control-label">companyName</label>
		    <div class="col-sm-5">
		      <form:input path="companyName" class="form-control" placeholder = "Your companyName" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="companyName" htmlEscape="false" />
		    </div>
		</div>
        
        <fmt:formatDate value="${employer.employedSince}" var="employedSinceString" pattern="dd/MM/yyyy" />
        <div class="form-group row">
			<label for="employedSince" class="col-sm-2 control-label">employedSince</label>
		    <div class="col-sm-5">
		      <form:input path="employedSince" value="${employedSinceString}" class="form-control" id="employedSince" placeholder="Your employedSince" />
			</div>
		    <div class="col-sm-5">
		    	<form:errors path="employedSince" htmlEscape="false" />
		    </div>
		</div>
				
        <div class="form-group row">
			<label for="position" class="col-sm-2 control-label">position</label>
		    <div class="col-sm-5">
		      <form:input path="position" class="form-control" placeholder = "Your position" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="position" htmlEscape="false" />
		    </div>
		</div>

	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form>
