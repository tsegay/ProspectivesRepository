<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
<!--

/* 
Put all css in style.css file 
*/

form.messageform {
	padding: 20px;
	display: none;
}


/* .message {
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
} */


/* textarea.textareafield {
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
} */
-->
</style>

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
<!-- ############################################################################################# -->
<!-- 			jQuery Scripts			 -->
<!-- ############################################################################################# -->

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
<!-- deleted modal script -->

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="getMessagesUrl" value="/accounts/${userEntity.id}/getmessages" />
	<%-- <c:url value="/accounts/' + userEntityId + '/getmessages"/> --%>
	<c:url var="postMessageUrl" value="/accounts/${userEntity.id}/sendmessage" />
	<%-- <c:url value="/accounts/' + studentId + '/sendmessage"/> --%>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="getMessagesUrl" value="/myAccount/getmessages" />
	<c:url var="postMessageUrl" value="/myAccount/sendmessage" />
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
			"url" : '${postMessageUrl}',
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
		
		/* 
		ceate form
			<form class="messageform" id="messageform" style="display: block;">
		*/
		var messageForm = document.createElement("form");
		messageForm.setAttribute("id", "messageform");
		messageForm.setAttribute("class", "messageform");
		/* 
		create div for the message input and label
			<div class="form-group">
				<label for="subjectfield">Subject</label>
				<input class="form-control" id="subjectfield">
			</div>
		 */
		var subjectDiv = document.createElement("div");
		subjectDiv.setAttribute("class", "form-group");
		
			var subjectLabel = document.createElement("label");
			subjectLabel.setAttribute("for", "subjectfield");
			subjectLabel.appendChild(document.createTextNode("Subject"));
			
			var subjectField = document.createElement("input");
			subjectField.setAttribute("id", "subjectfield");
			subjectField.setAttribute("class", "form-control");
		
		subjectDiv.appendChild(subjectLabel);
		subjectDiv.appendChild(subjectField);
		/* 
		create a div for the textarea field
			<div class="form-group">
				<label for="textareafield">Message</label>
				<textarea class="form-control" rows="4" id="textareafield"></textarea>
			</div>
		 */
		var textareaDiv = document.createElement("div");
		textareaDiv.setAttribute("class", "form-group");
		
			var textareaLabel = document.createElement("label");
			textareaLabel.setAttribute("for", "textareafield");
			textareaLabel.appendChild(document.createTextNode("Message"));
		
			var textareaField = document.createElement("textarea");
			textareaField.setAttribute("id", "textareafield");
			textareaField.setAttribute("class", "form-control");
			textareaField.setAttribute("rows", "6");
			
		textareaDiv.appendChild(textareaLabel);
		textareaDiv.appendChild(textareaField);
		
		/* 	
		create the send button
			<button class="sendbutton btn btn-primary" type="submit">Send</button>
		 */
		var sendButton = document.createElement("button");
		sendButton.setAttribute("class", "btn");
		sendButton.setAttribute("class", "btn-primary");
		sendButton.setAttribute("type", "submit");
		sendButton.appendChild(document.createTextNode("Send"));
		
			sendButton.onclick = function(id){
				return function(){
					sendMessage(id);
				}
			}(studentId);
		
		/* messageForm.appendChild(subjectField); 
		messageForm.appendChild(textareaField); */
		
		/* 
		append all the sections: inputfield, textarea and the button to the form
		 */
		messageForm.appendChild(subjectDiv);
		messageForm.appendChild(textareaDiv);
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
		/* alert('${getMessagesUrl}'); */
		$.getJSON('${getMessagesUrl}', fetchAndDisplayMessages);
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


