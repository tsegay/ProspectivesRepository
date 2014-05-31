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


<%-- <sec:authorize access="hasRole('ROLE_ADMIN')">
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
	<c:url var="missingDocumentsUrl" value="/accounts/${userEntity.id}/reports/missingDocuments" />
	<c:url var="evaluationReportUrl" value="/accounts/${userEntity.id}/reports/evaluationReport" />
	<c:url var="messagesUrl" value="/accounts/${userEntity.id}/messages" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="myAccount" value='/myAccount'/>
	<c:url var="educationUrl" value="/myAccount/educations" />
	<c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" />
	<c:url var="addressUrl" value="/myAccount/addresses" />
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="employersUrl" value="/myAccount/employers" />
	<c:url var="messagesUrl" value="/myAccount/messages" />
</sec:authorize> --%>




<h1>Notifications page</h1>



<a href="<c:url value="/messages"/>">Messages (<span id="messagesCount">0</span>)</a>

<div id="messages">

</div>


<!-- ############################################################################################# -->
<!-- 			jQuery Scripts			 -->
<!-- ############################################################################################# -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

<!-- <script type="text/javascript" src="http://www.datejs.com/build/date.js"></script> -->
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/js/date.js"></script>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>


<%-- <script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script> --%>



<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="getMessagesUrl" value="/accounts/messages" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">

</sec:authorize>

<script>	
	var timer;
	
	/* This displays the message compose form when the user click the link 
	first stop the timer, so the page won't refresh while user is composing an email
	*/
	/* function displayMessageForm(studentId){
		alert("displayMessageForm called " + studentId);
		stopTimer();
		$("#messageform").toggle();
	} */
	
	
	function fetchAndDisplayMessages(data){
		/* get student id from the model */
		/* var studentId = '${userEntityId}'; */
		
		$("#messagesCount").text(data.messagesCount);
		$("div#messages").html("");
		
		/* fetch all the messages from the db and display it */
		for (var i = 0; i < data.messages.length; i++) {
			var message = data.messages[i];
			
			var messageDiv = document.createElement("div");
			messageDiv.setAttribute("class", "message");
			
			var dateSpan = document.createElement("span");
			dateSpan.setAttribute("class", "date");
			
			var now = new Date(message.dateCreated);
			
			dateSpan.appendChild(document.createTextNode(now.toString('MM-dd-yyyy')));
			dateSpan.appendChild(document.createTextNode(" "));
			dateSpan.appendChild(document.createTextNode(now.toString('HH:mm')));
			dateSpan.appendChild(document.createTextNode(" "));
			
			var studentSpan = document.createElement("span");
			studentSpan.setAttribute("class", "student");
			studentSpan.appendChild(document.createTextNode(message.student.firstName));
			studentSpan.appendChild(document.createTextNode(" "));
			
			var subjectSpan = document.createElement("span");
			subjectSpan.setAttribute("class", "subject");
			
			subjectSpan.appendChild(document.createTextNode("("));
				var emailLink = document.createElement("a");
				emailLink.setAttribute("class", "emaillink");
				emailLink.setAttribute("href", "#");
				/* emailLink.setAttribute("onclick", "displayMessageForm(" + i + ")"); */
				/* emailLink.appendChild(document.createTextNode(message.student.email)); */
				emailLink.appendChild(document.createTextNode(message.subject));
			
			subjectSpan.appendChild(emailLink);
			subjectSpan.appendChild(document.createTextNode(")"));
			
			messageDiv.appendChild(dateSpan);
			messageDiv.appendChild(studentSpan);
			messageDiv.appendChild(subjectSpan);
			
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
		/* var userEntityId = '${userEntityId}'; */
		/* alert('${getMessagesUrl}'); */
		$.getJSON('${getMessagesUrl}', fetchAndDisplayMessages);
	}
	
	function onLoad(){
		/* Call the dropdowns for the menu bar (Profile, Reports) via JavaScript */
		$('.dropdown-toggle').dropdown();
		updatePage();
		startTimer();
	}
	
	$(document).ready(onLoad);

</script>




