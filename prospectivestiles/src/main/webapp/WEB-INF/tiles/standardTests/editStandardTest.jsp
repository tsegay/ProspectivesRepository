<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>



<h1>editStandardTest.jsp</h1>
<form:form action="${editStandardTestUrl}"
	modelAttribute="standardTest" role="form" class="form-horizontal">
	<div class="modal-header">
		<h4>Edit standardTest</h4>
		standardTest:
		<c:if test="${standardTest.id > 0}">
			<c:out value="${standardTest.id}" />
		</c:if>
		<br /> standardTest.userEntity.id:
		<c:if test="${standardTest.id > 0}">
			<c:out value="${standardTest.userEntity.id}" />
		</c:if>
	</div>
	<div class="modal-body">

		<div class="form-group row">
			<label for="name" class="col-sm-2 control-label">name</label>
		    <div class="col-sm-5">
		      <form:input path="name" class="form-control" placeholder = "Your name" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="name" htmlEscape="false" />
		    </div>
		</div>
        <div class="form-group row">
			<label for="score" class="col-sm-2 control-label">score</label>
		    <div class="col-sm-5">
		      <form:input path="score" class="form-control" placeholder = "Your score" />
		    </div>
		    <div class="col-sm-5">
		    	<form:errors path="score" htmlEscape="false" />
		    </div>
		</div>
        

	</div>
	<div class="modal-footer">
		<a class="btn btn-default" data-dismiss="modal">Cancel</a> <input
			class="btn btn-primary" type="submit" value="Submit"></input>
	</div>
</form:form>
