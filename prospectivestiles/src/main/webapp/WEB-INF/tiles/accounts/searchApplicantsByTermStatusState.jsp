<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="sec"	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="homeUrl" value="/welcome" />
	<c:url var="accountsByTermStatusState" value="/accounts/getAccountsByTermStatusState" />
</sec:authorize>

<h1>Generate a Report</h1>

<!-- ########################################## -->

<form:form action="${accountsByTermStatusState}" modelAttribute="userEntity" role="form" target="_blank">

	
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
		<!-- <label for="term" class="col-sm-3 control-label">term</label> -->
		<label for="international" class="col-sm-2 control-label">
			International/Domestic
		</label>
	    <div class="col-sm-5">
	      <form:select path="international">
			   <form:option value="true" label="international"/>
			   <form:option value="false" label="domestic"/>
			</form:select>
	    </div>
	    <div class="col-sm-3">
	    	<form:errors class="errormsg" path="international" htmlEscape="false" />
	    </div>
	</div>
	
	<div class="form-group row">
		<!-- <label for="term" class="col-sm-3 control-label">term</label> -->
		<label for="accountState" class="col-sm-2 control-label">
			Account State: 
		</label>
	    <div class="col-sm-5">
	      <form:select path="accountState">
			   <form:option value="pending" label="pending"/>
			   <form:option value="inprocess" label="inprocess"/>
			   <form:option value="complete" label="complete"/>
			   <form:option value="admitted" label="admitted"/>
			   <form:option value="denied" label="denied"/>
			   <form:option value="enrolled" label="enrolled"/>
			</form:select>
	    </div>
	    <div class="col-sm-3">
	    	<form:errors class="errormsg" path="accountState" htmlEscape="false" />
	    </div>
	</div>
	
    <div class = "form-group row">
     	<label class="col-sm-2 control-label">&nbsp;</label>
     	<div class = "col-sm-10">
     		<input class="btn btn-default" type="submit" value="Generate Report"></input>
     		<a class="btn btn-default" href="${homeUrl}">Cancel</a>
     	</div>
    </div>
    
       
</form:form>

