<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h1>editEmergencyContact.jsp</h1>
<form:form action="${editEmergencyContactUrl}"
	modelAttribute="emergencyContact" role="form" class="form-horizontal">
	<div class="modal-header">
		<h4>Edit emergencyContact</h4>
		emergencyContact:
		<c:if test="${emergencyContact.id > 0}">
			<c:out value="${emergencyContact.id}" />
		</c:if>
		<br /> emergencyContact.userEntity.id:
		<c:if test="${emergencyContact.id > 0}">
			<c:out value="${emergencyContact.userEntity.id}" />
		</c:if>
	</div>
	<div class="modal-body">

		<div class="form-group">
			<label for="firstName" class="col-sm-2 control-label">firstName</label>
		    <div class="col-sm-10">
		      <form:input class="form-control" path="firstName" placeholder = "Your Emergency Contact firstName" />
		    </div>
		</div>
		
		<div class="form-group">
			<label for="lastName" class="col-sm-2 control-label">lastName</label>
			<div class = "col-sm-10">
				<form:input class="form-control" path="lastName" placeholder = "Your lastName" />
			</div>
		</div>
		
		<div class = "form-group">
			<label for="phone" class="col-sm-2 control-label">phone</label>
			<div class = "col-sm-10">
				<form:input path="phone" class = "form-control" id = "phone" placeholder = "Your phone"/>
			</div>
        </div>
        
		<div class = "form-group">
			<label for="email" class="col-sm-2 control-label">email</label>
			<div class = "col-sm-10">
				<form:input path="email" class = "form-control" id = "email" placeholder = "Your email"/>
			</div>
        </div>
        
		<div class = "form-group">
			<label for="relationship" class="col-sm-2 control-label">relationship</label>
			<div class = "col-sm-10">
				<form:input path="relationship" class = "form-control" id = "relationship" placeholder = "Your relationship"/>
			</div>
        </div>


	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form>
