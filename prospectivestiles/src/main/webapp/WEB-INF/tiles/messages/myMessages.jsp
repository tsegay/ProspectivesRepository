<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
<!--
.message {
	margin-bottom: 20px;
	border-bottom: 1px dashed brown;
}

div.subject {
	font-size: large;
	font-weight: bold;
}

div.messagebody {
	display: block;
	font-style: italic;
}

div.name {
	font-size: medium;
}

form.messageform {
	padding: 20px;
	display: none;
}

textarea.textareafield {
	width: 400px;
	height: 250px;
}

input.sendbutton {
	display: block;
	font-size: large;
	border: 1px solid gray;
}

a.emaillink:link {
	color: brown;
}

span.notification {
	display: block;
	font-weight: bold;
	color: green;
}
-->
</style>

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
	<c:url var="addressUrl" value="/myAccount/addresses" />
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="employersUrl" value="/myAccount/employers" />
	<c:url var="messagesUrl" value="/myAccount/messages" />
</sec:authorize>

<ul class="nav nav-tabs">
	<li class="dropdown">
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
		<a href="${educationUrl}">Educational bg</a>
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
	<li class="active">
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


<h1>Messages page</h1>



<a href="<c:url value="/messages"/>">Messages (<span id="messagesCount">0</span>)</a>

<div id="messages">

</div>





<!-- ############################################################################################# -->
<!-- ############################################################################################# -->
<%-- <c:choose>
	<c:when test="${empty messages}">
		<p>No Message.</p>
	</c:when>
	<c:otherwise>
		
		<div class="panel-group" id="accordion">
		<c:forEach var="message" items="${messages}">
		
					
				  <div class="panel panel-default">
				    <div class="panel-heading">
				      <h4 class="panel-title">
				        
				        <a data-toggle="collapse" data-parent="#accordion" href="#_${message.id}">
				          <p><c:out value="${message.id}"></c:out>. <c:out value="${message.subject}"></c:out></p>
				        </a>
				      </h4>
				    </div>
				    <div id="_${message.id}" class="panel-collapse collapse in">
				      <div class="panel-body">
				        <p><c:out value="${message.text}"></c:out></p>
				      </div>
				    </div>
				  </div>
		
		</c:forEach>
		</div>

		
	</c:otherwise>
</c:choose> --%>
<%-- <a data-toggle="collapse" data-parent="#accordion" href="'#'+${message.id}"> --%>


<!-- Button trigger modal -->
<!-- <button class="btn btn-primary btn-lg" data-toggle="modal" data-target="#addMessageModal">
  Add Message
</button> -->

<!-- message Modal -->
<%-- <div class="modal fade" id="addMessageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	        <form:form action="${messagesUrl}" modelAttribute="message" role="form" class = "form-horizontal">
	            <div class = "modal-header">
	                <h4>Add Message</h4>
	                message: 
	                <c:if test="${message.id > 0}">
	                	<c:out value="${message.id}" />
	                </c:if>
	                <br />
	            </div>
	            <div class = "modal-body">
	            	
	                <div class="form-group row">
						<label for="subject" class="col-sm-2 control-label">subject</label>
					    <div class="col-sm-5">
					      <form:input path="subject" class="form-control" placeholder = "Your subject" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="subject" htmlEscape="false" />
					    </div>
					</div>
	                <div class="form-group row">
						<label for="text" class="col-sm-2 control-label">text</label>
					    <div class="col-sm-5">
					      <form:input path="text" class="form-control" placeholder = "Your text" />
					    </div>
					    <div class="col-sm-5">
					    	<form:errors path="text" htmlEscape="false" />
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
</div> --%>


<!-- edit message Modal -->
<!-- <div class="modal fade" id="editMessageModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class = "modal-content">
	  </div>
  </div>
</div> -->

<!-- ############################################################################################# -->
<!-- 			jQuery Scripts			 -->
<!-- ############################################################################################# -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
<!-- deleted modal script -->

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<!-- try admin js for the form -->
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<!-- try user js for the form -->
</sec:authorize>

<script>	
	var timer;
	
	/* This displays the message compose form when the user click the link 
	first stop the timer, so the page won't refresh while user is composing an email
	*/
	function displayMessageForm(studentId){
		alert("displayMessageForm called " + studentId);
		stopTimer();
		$("#messageform").toggle();
	}
	
	function success(data){
		alert("Successfully sent email");
		alert(data.stId);
		$("#messageform").toggle();
		/* update the page first to load the new message to the page
		and start the timer */
		updatePage();
		startTimer();
		$(".notification").text("Message sent");
	}
	
	function error(data){
		alert("Error. Send message failed. within JS");
		alert(data.stId);
	}
	
	function sendMessage(studentId){
		alert("sending message..." + studentId);
		
		var subject = $("#subjectfield").val();
		var text = $("#textareafield").val();
		
		$.ajax({
			"type": 'POST',
			"url" : '<c:url value="/myAccount/sendmessage"/>',
			"data": JSON.stringify({"studentId": studentId, "subject": subject, "text": text}),
			"complete": function(response, textStatus){
				return alert("#### complete called. " + textStatus);
			},
			"success": success,
			"error" : error,
			contentType : "application/json",
			dataType : "json"
		});
		
	}
	
	
	function fetchAndDisplayMessages(data){
		/* get student id from the model */
		var studentId = '${userEntityId}';
		
		$("#messagesCount").text(data.messagesCount);
		$("div#messages").html("");
		
		
		var messageForm = document.createElement("form");
		messageForm.setAttribute("class", "messageform");
		messageForm.setAttribute("id", "messageform");
		
		var subjectField = document.createElement("input");
		subjectField.setAttribute("class", "subjectfield");
		subjectField.setAttribute("id", "subjectfield");
		
		var textareaField = document.createElement("textarea");
		textareaField.setAttribute("class", "textareafield");
		textareaField.setAttribute("id", "textareafield");
		
		var sendButton = document.createElement("button");
		sendButton.setAttribute("class", "sendbutton");
		sendButton.setAttribute("type", "submit");
		sendButton.setAttribute("value", "Compose");
		
			sendButton.onclick = function(id){
				return function(){
					sendMessage(id);
				}
			}(studentId);
		
		messageForm.appendChild(subjectField);
		messageForm.appendChild(textareaField);
		messageForm.appendChild(sendButton);
		
		/* notification when message is sent */
		var notificationDiv = document.createElement("div");
		notificationDiv.setAttribute("class", "notification");
		
		$("div#messages").append(messageForm);
		$("div#messages").append(notificationDiv);
		
		/* a link to display the message form */
		var composeLink = document.createElement("a");
		composeLink.setAttribute("class", "composeLink");
		composeLink.setAttribute("href", "#");
		composeLink.setAttribute("onclick", "displayMessageForm(" + studentId +")");
		composeLink.appendChild(document.createTextNode("ComposeMesage"));
		$("div#messages").append(composeLink);
		
		/* fetch all the messages from the db and display it */
		for (var i = 0; i < data.messages.length; i++) {
			var message = data.messages[i];
			
			var messageDiv = document.createElement("div");
			messageDiv.setAttribute("class", "message");
			
			var subjectDiv = document.createElement("div");
			subjectDiv.setAttribute("class", "subject");
			subjectDiv.appendChild(document.createTextNode(message.subject));
			
			var textDiv = document.createElement("div");
			textDiv.setAttribute("class", "text");
			textDiv.appendChild(document.createTextNode(message.text));
			
			var studentDiv = document.createElement("div");
			studentDiv.setAttribute("class", "student");
			studentDiv.appendChild(document.createTextNode(message.student.firstName));
			
			studentDiv.appendChild(document.createTextNode("("));
				var emailLink = document.createElement("a");
				emailLink.setAttribute("class", "emaillink");
				emailLink.setAttribute("href", "#");
				/* emailLink.setAttribute("onclick", "displayMessageForm(" + i + ")"); */
				emailLink.appendChild(document.createTextNode(message.student.email));
			
			studentDiv.appendChild(emailLink);
			studentDiv.appendChild(document.createTextNode(")"));
			
			messageDiv.appendChild(subjectDiv);
			messageDiv.appendChild(textDiv);
			messageDiv.appendChild(studentDiv);
			
			$("div#messages").append(messageDiv);
			
		}
	}
	
	function startTimer(){
		timer = window.setInterval(updatePage, 40000);
	}
	
	function stopTimer(){
		window.clearInterval(timer);
	}
	
	function updatePage(){
		var userEntityId = '${userEntityId}';
		$.getJSON('<c:url value="/myAccount/getmessages"/>', fetchAndDisplayMessages);
	}
	
	function onLoad(){
		$('.dropdown-toggle').dropdown();
		updatePage();
		startTimer();
	}
	
	$(document).ready(onLoad);

</script>








<!-- Call the dropdowns via JavaScript  -->
<!-- <script>
	$(document).ready(function () {
        $('.dropdown-toggle').dropdown();
    });
</script> -->


