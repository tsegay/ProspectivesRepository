<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="accountsUrl" value="/accounts/${userEntity.id}" />
	<c:url var="associatedUserFormUrl" value="/accounts/${userEntity.id}/associatedUser/edit" />
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

<h1>associatedUser Form</h1>

<form:form action="${associatedUserFormUrl}" modelAttribute="associatedUser" role="form" class="form-horizontal">

	<div class="form-group row">
		<label for="agent" class="col-sm-2 control-label">
			<spring:message code="associatedUserForm.label.aoId" />
		</label>
		<div class="col-sm-5">
			<form:select path="aoId">
			    <c:forEach var="admissionCounselor" items="${admissionCounselors}">
			   		<form:option value="${admissionCounselor.id}" label="${admissionCounselor.fullName}"/>
			   </c:forEach>
			</form:select>
			
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="aoId" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group row">
		<label for="agent" class="col-sm-2 control-label">
			<spring:message code="associatedUserForm.label.agent" />
		</label>
		<div class="col-sm-5">
			<form:input path="agent" class="form-control" placeholder="agent" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="agent" htmlEscape="false" />
		</div>
	</div>
	
	<div class="form-group row">
		<label for="agent" class="col-sm-2 control-label">
			<spring:message code="associatedUserForm.label.referrer" />
		</label>
		<div class="col-sm-5">
			<form:input path="referrer" class="form-control" placeholder="referrer" />
		</div>
		<div class="col-sm-5">
			<form:errors class="errormsg" path="referrer" htmlEscape="false" />
		</div>
	</div>

	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<input class="btn btn-primary" type="submit" value="Save"></input> <a
				class="btn btn-default" href="${accountsUrl}">Cancel</a>
		</div>
	</div>

</form:form>
