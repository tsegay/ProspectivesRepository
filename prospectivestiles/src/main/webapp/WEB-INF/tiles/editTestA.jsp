<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>



<h1>Address editTestA.jsp</h1>
      <form:form action="${editTestAUrl}" modelAttribute="testA" role="form" class = "form-horizontal">
          <div class = "modal-header">
              <h4>Add Address</h4>
              address: 
              <c:if test="${address.id > 0}">
              	<c:out value="${address.id}" />
              </c:if>
              <br />
              address.userEntity.id: 
              <c:if test="${address.id > 0}">
              	<c:out value="${address.userEntity.id}" />
              </c:if>
          </div>
          <div class = "modal-body">
          
              <div class="form-group">
			<label for="address1" class="col-sm-2 control-label">address1</label>
		    <div class="col-sm-10">
		      <form:input class="form-control" path="address1" placeholder = "Your address1" />
		    </div>
		</div>
		
		<div class="form-group">
			<label for="address2" class="col-sm-2 control-label">address2</label>
			<div class = "col-sm-10">
				<form:input class="form-control" path="address2" placeholder = "Your address2" />
			</div>
		</div>
		
		<div class = "form-group">
			<label for="city" class="col-sm-2 control-label">city</label>
			<div class = "col-sm-10">
				<form:input path="city" class = "form-control" id = "city" placeholder = "Your city"/>
			</div>
        </div>
		
		
          </div>
          <div class = "modal-footer">
      		<a class = "btn btn-default" data-dismiss = "modal">Cancel</a>    
      		<input class="btn btn-primary" type="submit" value="Submit"></input>
          </div>
      </form:form>
