<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="educationUrl" value="/accounts/${userEntity.id}/educations" />
	<c:url var="newHighSchoolUrl" value="/accounts/${userEntity.id}/highSchool/new" />
	<c:url var="newInstituteUrl" value="/accounts/${userEntity.id}/institute/new" />
	<c:url var="addressUrl" value="/accounts/${userEntity.id}/addresses" />
	<c:url var="newAddressUrl" value="/accounts/${userEntity.id}/address/new" />
	<c:url var="emergencyContactsUrl" value="/accounts/${userEntity.id}/emergencyContacts" />
	<c:url var="myAccount" value='/myAccount'/>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="educationUrl" value="/myAccount/educations" />
	<c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" />
	<c:url var="addressUrl" value="/myAccount/addresses" />
	<c:url var="newAddressUrl" value="/myAccount/address/new" />
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="testAsUrl" value="/myAccount/testAs" />
	<c:url var="myAccount" value='/myAccount'/>
</sec:authorize>

<ul class="nav nav-tabs">
	<li>
		<a href="${myAccount}">Personal Info</a>
	</li>
	<li>
		<a href="${addressUrl}">Addresses</a>
	</li>
	<li>
		<a href="${educationUrl}">Education</a>
	</li>
	<li>
		<a href="${emergencyContactsUrl}">EmergencyContacts</a>
	</li>
	<li>
		<a href="${applyingForUrl}">ApplyingFor</a>
	</li>
	<li class="active">
		<a href="${testAUrl}">TestA</a>
	</li>
	<li><a href="#">Program of Study</a></li>
	<li><a href="#">Checklist</a></li>
</ul>


<h1>testAs page</h1>


<c:choose>
	<c:when test="${empty testAs}">
		<p>No testAs.</p>
	</c:when>
	<c:otherwise>

		<div class="table-responsive">
			<table class="table table-hover table-striped">
				<tr>
					<th>Id</th>
					<th>address1</th>
					<th>address2</th>
					<th>city</th>
					<th>term</th>
					<th>listOfProgramOfStudy</th>
					<th>Edit</th>
					<th>Delete</th>
				</tr>
	
				<c:forEach var="testA" items="${testAs}">
				
				 <sec:authorize access="hasRole('ROLE_ADMIN')">
					<c:url var="testAUrl"	value="/accounts/${testA.userEntity.id}/testA/${testA.id}" />
					<c:url var="editTestAUrl" value="/accounts/${testA.userEntity.id}/testA/${testA.id}/edit" />
					<c:url var="deleteTestAUrl" value="/accounts/${testA.userEntity.id}/testA/${testA.id}/delete" />
				</sec:authorize>
				<sec:authorize access="hasRole('ROLE_USER')">
					<c:url var="testAUrl"	value="/myAccount/testA/${testA.id}" />
					<c:url var="editTestAUrl" value="/myAccount/testA/${testA.id}" />
					<c:url var="deleteTestAUrl" value="/myAccount/testA/${testA.id}/delete" />
				</sec:authorize>
				
				<tr>
					<td><c:out value="${testA.id}"></c:out></td>
					<td><c:out value="${testA.address1}"></c:out></td>
					<td><c:out value="${testA.address2}"></c:out></td>
					<td><c:out value="${testA.city}"></c:out></td>
					<td><c:out value="${testA.term.name}"></c:out></td>
					<td>
						<c:forEach var="programOfStudy" items="${testA.listOfProgramOfStudy}">
							<c:out value="${programOfStudy.name}"></c:out>, 
						</c:forEach>
						<%-- <c:out value="${testA.listOfProgramOfStudy}"></c:out> --%>
					</td>
					<td>
						<a data-toggle="modal" data-remote="${editTestAUrl}" data-target="#editTestAModal" 
							class="btn btn-primary btn-lg">Edit</a>
					</td>
					<td>
						<form id="deleteForm" action="${deleteTestAUrl}" method="post">
							<div><input type="submit" value="DELETE" /></div>
						</form>
					</td>
				</tr>
								
				</c:forEach>
	
			</table>
		</div>
	</c:otherwise>
</c:choose>

<!-- Button trigger modal -->
<button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myTestAModal">
  Add TestA
</button>

<!-- address Modal -->
<div class="modal fade" id="myTestAModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	        <form:form action="${testAsUrl}" modelAttribute="testA" role="form" class = "form-horizontal">
	            <div class = "modal-header">
	                <h4>Add TestA</h4>
	                testA: 
	                <c:if test="${testA.id > 0}">
	                	<c:out value="${testA.id}" />
	                </c:if>
	                <br />
	                testA.userEntity.id: 
	                <c:if test="${testA.id > 0}">
	                	<c:out value="${testA.userEntity.id}" />
	                </c:if>
	                Terms:
	                <table class="table table-hover table-striped">
						<tr>
							<th>Id</th>
							<th>name</th>
							<th>duration</th>
						</tr>
						<c:forEach var="term" items="${terms}">
							<tr>
								<td><c:out value="${term.id}"></c:out></td>
								<td><c:out value="${term.name}"></c:out></td>
								<td><c:out value="${term.duration}"></c:out></td>
	                		</tr>
	                	</c:forEach>
	                </table>
	                		
	                		
	            </div>
	            <div class = "modal-body">
	            
	            	<div class="form-group row">
						<label for="address1" class="col-sm-2 control-label">address1</label>
					    <div class="col-sm-5">
					      <form:input path="address1" class="form-control" placeholder = "Your address1" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="address1" htmlEscape="false" />
					    </div>
					</div>
					
	            	<div class="form-group row">
						<label for="address2" class="col-sm-2 control-label">address2</label>
					    <div class="col-sm-5">
					      <form:input path="address2" class="form-control" placeholder = "Your address2" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="address2" htmlEscape="false" />
					    </div>
					</div>
	            	<div class="form-group row">
						<label for="city" class="col-sm-2 control-label">city</label>
					    <div class="col-sm-5">
					      <form:input path="city" class="form-control" placeholder = "Your city" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="city" htmlEscape="false" />
					    </div>
					</div>
					
					<div class="form-group row">
						<label for="term" class="col-sm-2 control-label">term</label>
					    <div class="col-sm-5">
					      <form:select path="term.id">
							   <%-- <form:option value="NONE" label="--- Select ---"/> --%>
							   <%-- <form:options items="${terms}" /> --%>
							   <c:forEach var="term" items="${terms}">
							   		<form:option value="${term.id}" label="${term.name}"/>
							   </c:forEach>
							</form:select>
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="term" htmlEscape="false" />
					    </div>
					</div>
					
					<div class="form-group row">
						<label for="term" class="col-sm-2 control-label">programOfStudies</label>
					    <div class="col-sm-5">
					      <form:select path="programOfStudy.id">
							   <%-- <form:option value="NONE" label="--- Select ---"/> --%>
							   <%-- <form:options items="${terms}" /> --%>
							   <c:forEach var="programOfStudy" items="${programOfStudies}">
							   		<form:option value="${programOfStudy.id}" label="${programOfStudy.name}"/>
							   </c:forEach>
							</form:select>
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="term" htmlEscape="false" />
					    </div>
					</div>
	            
					
	            </div>
	            <div class = "modal-footer">
	        		<a class = "btn btn-default" data-dismiss = "modal">Cancel</a>    
	        		<input class="btn btn-primary" type="submit" value="Submit"></input>
	                <!-- <button class = "btn btn-primary" type = "submit">Send</button> -->
	            </div>
	        </form:form>
	  </div>
  </div>
</div>


<!-- edit address Modal -->
<div class="modal fade" id="editTestAModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
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
	 -->
	
	<script>
		$(function (){
			$('body').on('hidden.bs.modal', '.modal', function () {
			    $(this).removeData('bs.modal');
			});
		});
	</script>
	
	
	<!-- FOR TESTING PURPOSE -->
	
	<!-- <script>
		$(function (){
			$('#editAddressModal').on('hide.bs.modal', function (){
				$(this).removeData('bs.modal');
				/* alert('Testing modals...'); */
			});
		});
	</script> -->
