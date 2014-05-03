<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>


<!-- Use this for ROLE_USER to get current user -->
<%-- <sec:authentication var="myAccount" property="principal" /> --%>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<%-- <c:url var="myAccount" value='/myAccount'/>
	<c:url var="account" value="../accounts/${user.id}" /> --%>
	<c:url var="myAccount" value="/accounts/${userEntity.id}" />
	<c:url var="educationUrl" value="/accounts/${userEntity.id}/educations" />
	<c:url var="newHighSchoolUrl" value="/accounts/${userEntity.id}/highSchool/new" />
	<c:url var="newInstituteUrl" value="/accounts/${userEntity.id}/institute/new" />
	<c:url var="addressUrl" value="/accounts/${userEntity.id}/addresses" />
	<%-- <c:url var="newAddressUrl" value="/accounts/${userEntity.id}/address/new" /> --%>
	<c:url var="emergencyContactsUrl" value="/accounts/${userEntity.id}/emergencyContacts" />
	<c:url var="applyingForUrl" value="/accounts/${userEntity.id}/applyingFor" />
	<c:url var="standardTestsUrl" value="/accounts/${userEntity.id}/standardTests" />
	<c:url var="employersUrl" value="/accounts/${userEntity.id}/employers" />
	<c:url var="checklistUrl" value="/accounts/${userEntity.id}/checklists" />
	<c:url var="evaluationUrl" value="/accounts/${userEntity.id}/evaluations" />
	<c:url var="reportsUrl" value="/accounts/${userEntity.id}/reports" />
	<c:url var="missingDocumentsUrl" value="/accounts/${userEntity.id}/missingDocuments" />
	<c:url var="messagesUrl" value="/accounts/${userEntity.id}/messages" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="myAccount" value='/myAccount'/>
	<c:url var="educationUrl" value="/myAccount/educations" />
	<c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" />
	<!-- change address to addresses -->
	<c:url var="addressUrl" value="/json/myAccount/jsonAddresses" />
	<%-- <c:url var="newAddressUrl" value="/myAccount/address/new" /> --%>
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="employersUrl" value="/myAccount/employers" />
	<c:url var="messagesUrl" value="/myAccount/messages" />
</sec:authorize>


<ul class="nav nav-tabs">
	<li class="dropdown active">
	  <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-toggle="dropdown">Profile <span class="caret"></span></a>
	  <ul class="dropdown-menu">
	    <li><a href="${myAccount}">Personal Info</a></li>
	    <li><a href="${addressUrl}">Addresses</a></li>
	    <li><a href="${emergencyContactsUrl}">EmergencyContacts</a></li>
	    <li class="divider"></li>
	    <li><a href="${employersUrl}">Employment</a></li>
	    <li class="divider"></li>
	    <li><a href="#">One more separated link</a></li>
	  </ul>
	</li>
	<li>
		<a href="${educationUrl}">Educational bgd</a>
	</li>
	<li>
		<a href="${applyingForUrl}">ApplyingFor</a>
	</li>
	<li>
		<a href="${standardTestsUrl}">StandardTest</a>
	</li>
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<li>
			<a href="${checklistUrl}">Checklist</a>
		</li>
		<li>
			<a href="${evaluationUrl}">Evaluation</a>
		</li>
		<li class="dropdown">
		  <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-toggle="dropdown">Reports<span class="caret"></span></a>
		  <ul class="dropdown-menu">
		    <li><a href="${reportsUrl}">Reports</a></li>
		    <li><a href="${missingDocumentsUrl}">MissingDocuments</a></li>
		    <li><a href="#">Link</a></li>
		    <li class="divider"></li>
		    <li><a href="#">Link</a></li>
		    <li class="divider"></li>
		    <li><a href="#">Link</a></li>
		  </ul>
		</li>
	</sec:authorize>
	<li>
		<a href="${messagesUrl}">Messages</a>
	</li>
</ul>

<sec:authorize access="hasRole('ROLE_ADMIN')">
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

<h1>Addresses page</h1>


<c:if test="${param.deleted == true}">
	<div class="info alert">Address deleted.</div>
</c:if>


<c:choose>
	<c:when test="${empty addresses}">
		<p>No Address.</p>
	</c:when>
	<c:otherwise>
		<c:forEach var="address" items="${addresses}">
			<sec:authorize access="hasRole('ROLE_ADMIN')">
				<c:url var="editAddressUrl" value="/accounts/${address.userEntity.id}/address/${address.id}" />
				<c:url var="deleteAddressUrl" value="/accounts/${address.userEntity.id}/address/${address.id}/delete" />
			</sec:authorize>
			<sec:authorize access="hasRole('ROLE_USER')">
				<c:url var="editAddressUrl" value="/myAccount/address/${address.id}" />
				<c:url var="deleteAddressUrl" value="/myAccount/address/${address.id}/delete" />
			</sec:authorize>
		
			<address>
				<c:out value="${address.id}" />
			  <strong><c:out value="${address.addressType}" /></strong><br>
			  <hr />
			  <c:out value="${address.address1}" />, 
			  <c:out value="${address.address2}" /><br>
			  <c:out value="${address.city}" /><br>
			  <c:out value="${address.state}" />, 
			  <c:out value="${address.zipcode}" /><br>
			  <c:out value="${address.country}" /><br>
			  <abbr title="Phone">P:</abbr> (123) 456-7890<br>
			  <a href="mailto:#">first.last@example.com</a><br><br>
				<a data-toggle="modal" data-remote="${editAddressUrl}" data-target="#editAddressModal" 
					class="btn btn-primary btn-lg">Edit</a><br><br>
				<form id="deleteForm" action="${deleteAddressUrl}" method="post">
					<div><input type="submit" value="DELETE" /></div>
				</form>
			</address>
		</c:forEach>

	</c:otherwise>
</c:choose>

<!-- Button trigger modal -->
<!-- <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
  Add Address
</button>
 -->
<button id="newAddressBtn" class="btn btn-primary btn-lg">
  Add Address
</button>

<!-- address Modal -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	        <form:form id="addressForm" modelAttribute="address" role="form" class = "form-horizontal">
	            <div class = "modal-header">
	                <h4>Add Address</h4>
	            </div>
	            <div class = "modal-body">
	            
	            	<div class="form-group row">
						<label for="addressType" class="col-sm-2 control-label">AddressType</label>
					    <div class="col-sm-5">
							<form:select path="addressType" class="form-control">
								  <%-- <form:option value="NONE" label="--- Select ---" /> --%>
								  <form:option value="HOME_ADDRESS" label="HOME_ADDRESS" />
								  <form:option value="WORK_ADDRESS" label="WORK_ADDRESS" />
								  <form:option value="MAILING_ADDRESS" label="MAILING_ADDRESS" />
								  <form:option value="FOREIGN_COUNTRY_ADDRESS" label="FOREIGN_COUNTRY_ADDRESS" />
						    </form:select>
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="addressType" htmlEscape="false" />
					    </div>
					</div>
					
	            	<div class="form-group row">
						<label for="address1" class="col-sm-2 control-label">address1</label>
					    <div class="col-sm-5">
					      <form:input id="address1" path="address1" class="form-control" placeholder = "Your address1" />
					      <!-- <span class="hide help-inline">This is required</span> -->
					      <div id="address1Error"></div>
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
						<label for="state" class="col-sm-2 control-label">state</label>
					    <div class="col-sm-5">
					      <form:input path="state" class="form-control" placeholder = "Your state" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="state" htmlEscape="false" />
					    </div>
					</div>
					
	            	<div class="form-group row">
						<label for="zipcode" class="col-sm-2 control-label">zipcode</label>
					    <div class="col-sm-5">
					      <form:input path="zipcode" class="form-control" placeholder = "Your zipcode" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="zipcode" htmlEscape="false" />
					    </div>
					</div>
					
	            	<div class="form-group row">
						<label for="country" class="col-sm-2 control-label">country</label>
					    <div class="col-sm-5">
					      <form:input path="country" class="form-control" placeholder = "Your country" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="country" htmlEscape="false" />
					    </div>
					</div>
	            
	            </div>
	            <div class = "modal-footer">
	        		<a class = "btn btn-default" data-dismiss = "modal">Cancel</a>    
	        		<input class="btn btn-primary" type="submit" value="Submit" id="addAddressSubmit"></input>
	                <!-- <button class = "btn btn-primary" type = "submit">Send</button> -->
	            </div>
	        </form:form>
	  </div>
  </div>
</div>


<!-- edit address Modal -->
<div class="modal fade" id="editAddressModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	  </div>
  </div>
</div>


	
	<!-- jQuery for Bootstrap's JavaScript plugins -->
	
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery-1.7.1.min.js"></script>

	<!-- Bootstrap JS -->
	<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js"></script>

	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	<!-- Validate plugin -->
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.validate.min.js"></script>
	

	<script>
		$(document).ready(function(){
			
		    $("#newAddressBtn").click(function(e){
		    	
		    	/* e.preventDefault(); */
		    	
		    	/* Open new address modal with js */
		        $("#myModal").modal('show');
		    	
		    	$('#addAddressSubmit').click(function(evt){
		    		alert("addAddressSubmit ... clicked");
		    		
		    		
		    	    if ($('#address1').val()==="") {
		    	      // invalid
		    	      
		    	      evt.preventDefault();
		    		  alert( "This default event prevented: " + evt.type);
		    	      
		    	      alert( "Please provide your address1!" ); 
		    	      /* $('#address1').next('.help-inline').show(); */
		    	      
		    	      $("<span>Please provide your address1</span>").appendTo('#address1Error');
		    	      /* //return false; */
		    	    }
		    	    else {
		    	     /*  // submit the form here
			    	     // return true;
			    	   // $("#myModal").submit(); */
			    	   evt.preventDefault();
			    	   
			    	   getAddress();
			    	   $("#myModal").modal('hide');
		    	    
		    	    }
		    			    	
		    	/* //e.preventDefault(); */
		    });
		    
    
		    $('body').on('hidden.bs.modal', '.modal', function () {
		    	/* alert("Modal window has been completely closed."); */
			    $(this).removeData('bs.modal');
		    	/* location.reload(); */
			});
		    
		});
		});

		
		function success(data){
			alert("Success");
		}
		function error(data){
			alert("Error");
		}
		function saveAddressJson(address1, address2, city, state, zipcode, country){
			alert("saveAddressJson ... called");

			$.ajax({
				"type": 'POST',
				"url" : '<c:url value="/json/myAccount/jsonAddresses"/>',
				"data": JSON.stringify({"address1": address1, "address2": address2, "city": city, "state": state, "zipcode": zipcode, "country": country}),
				"success" : success,
				"error" : error,
				contentType : "application/json",
				dataType : "json"
			});
			
		}
		
		
		function getAddress(){
			alert("getAddress ... called");
			$.getJSON("<c:url value="/json/myAccount/addresses"/>", showAddresses);
		}
		
		
		
		function showAddresses(data) {
			alert("showAddresses ... called");
			var address = data.address;
			
			/* for(var i=0; i<data.addresses.length; i++){
				var anAddress = data.addresses[i];
			} */
			
			var address1 = $('#address1').val();
			address2 = $('#address2').val(); 
			city = $('#city').val(); 
			state = $('#state').val(); 
			zipcode = $('#zipcode').val(); 
			country = $('#country').val();
			
			saveAddressJson(address1, address2, city, state, zipcode, country);
			//saveAddressJson(address.address1, address.address2, address.city, address.state, address.zipcode, address.country);
		}
			
			
		
		</script>
	