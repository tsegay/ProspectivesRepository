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

span.subject {
	font-size: large;
	color: brown;
	font-weight: bold;
}

span.messagebody {
	display: block;
	font-style: italic;
}

span.name {
	color: brown;
	font-size: medium;
}

form.replyform {
	padding: 20px;
	display: none;
}

textarea.replyarea {
	width: 400px;
	height: 250px;
}

input.replybutton {
	display: block;
	font-size: large;
	border: 1px solid gray;
}

a.replylink:link {
	color: brown;
}

span.alert {
	display: block;
	font-weight: bold;
	color: green;
}
-->
</style>

<h1>jsonMessages page</h1>

<a href="<c:url value="/messages"/>">Messages (<span id="numberMessages">0</span>)</a>

<div id="messages">

</div>





<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<!-- <script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script> -->
	<%-- <script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script> --%>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/resources/css/style.css" />

<script>	
	/*
	client javascript code needs to get data from our database in a special data format known as JSON
	Get info from application on a timer and update page without refreshing the browser
	To transfer data from server to client
	Create a new request mapping for url JavaScript need to get the resources it needs to display data in browser
	Create a timer that will automatically update the msg num in the page when a user sends messages
	Generating an entire page with JavaScript
	*/

	/* We need to temporarily stop page refresh when the user starts writing a message, 
	otherwise their message will disappear. */
	var timer;

	/*
	Before you display the reply form, stop the timer. 
	or the page will refresh and you loose the form
	*/
	function showReply(i){
		stopTimer();
		$("#form" + i).toggle();
	}

	/*
	use target to know which message was clicked
	hide the message form if success
	*/
	function success(data){
		alert(data.target);
		$("#form" + data.target).toggle();
		$("#alert" + data.target).text("Message sent");
		startTimer();
	}
	function error(data){
		alert("Error");
	}

	/*
	Put name and email in the sendMessage args, we need to know name and email of person we emailing to
	*/
	function sendMessage(i, name, email){
		/* alert($("#textbox" + i).val()); */
		/* alert(name + " : " + email); */
		/*
		post back the info

		In the controller add a key 'target' to the return map, 
		for the target pass on 'i' . 
		this is the index of message form in the sendMEssage function in jquery in the list of messages
		*/
		var text = $("#textbox" + i).val();
		$.ajax({
			"type": 'POST',
			"url" : '<c:url value="/json/accounts/sendmessage"/>',
			"data": JSON.stringify({"target": i, "text": text, "name": name, "email": email}),
			"success" : success,
			"error" : error,
			contentType : "application/json",
			dataType : "json"
		});

	}

	function showMessages(data){
		/* $("#numberMessages").text(data.number); */
		$("div#messages").html("");
		for(var i=0; i<data.messages.length; i++){
			var message = data.messages[i];

			var messageDiv = document.createElement("div");
			messageDiv.setAttribute("class", "message");

			var subjectSpan = document.createElement("span");
			subjectSpan.setAttribute("class", "subject");
			subjectSpan.appendChild(document.createTextNode(message.subject));

			var contentSpan = document.createElement("span");
			contentSpan.setAttribute("class", "messagebody");
			contentSpan.appendChild(document.createTextNode(message.text));

			var nameSpan = document.createElement("span");
			nameSpan.setAttribute("class", "name");
			/* nameSpan.appendChild(document.createTextNode(message.student.firstName + "(" + message.student.email + ")")); */
			nameSpan.appendChild(document.createTextNode(message.student.firstName + "("));

			var link = document.createElement("a");
			link.setAttribute("class", "replylink");
			link.setAttribute("href", "#");
			link.setAttribute("onclick", "showReply(" + i + ")");
			link.appendChild(document.createTextNode(message.student.email));
			nameSpan.appendChild(link);
			nameSpan.appendChild(document.createTextNode(")"));

			/* get notification when message is sent */
			var alertSpan = document.createElement("span");
			alertSpan.setAttribute("class", "alert");
			alertSpan.setAttribute("id", "alert" + i);
			//alertSpan.appendChild(document.createTextNode("Message sent"));

			var replyForm = document.createElement("form");
			replyForm.setAttribute("class", "replyform");
			replyForm.setAttribute("id", "form" + i);

			var textarea = document.createElement("textarea");
			textarea.setAttribute("class", "replyarea");
			textarea.setAttribute("id", "textbox" + i);

			var replyButton = document.createElement("input");
			replyButton.setAttribute("class", "replyButton");
			replyButton.setAttribute("type", "button");
			replyButton.setAttribute("value", "Reply");
			/* 
			Retrieving text from the appropriate textarea 
			Need to know which form the user was typing in in order to grab the text and send it
			Create anonymous function, to return a function that calls sendReply
			As soon as we execute this code onclick 
			We are passing the value 'i' and the anonymous function is getting that value as 'j'
			Supply the actualy values (message.student.firstName, message.student.email) to the args
			*/
			replyButton.onclick = function(j, name, email){
				return function() {
					sendMessage(j, name, email);
				}
			}(i, message.student.firstName, message.student.email);


			replyForm.appendChild(textarea);
			replyForm.appendChild(replyButton);

			messageDiv.appendChild(subjectSpan);
			messageDiv.appendChild(contentSpan);
			messageDiv.appendChild(nameSpan);
			messageDiv.appendChild(alertSpan);
			messageDiv.appendChild(replyForm);

			$("div#messages").append(messageDiv);
		}
	}

	function onLoad(){
		updatePage();
		startTimer();
	}

	function startTimer(){
		timer = window.setInterval(updatePage, 30000);
	}

	function stopTimer(){
		window.clearInterval(timer);
	}

	/*

	getJSON is going to go that url and try to download the data from that url, 
	parse that data and pass it to the function showMessages when that request is complete
	*/
	function updatePage(){
		/* alert("updating page"); */
		$.getJSON("<c:url value="/json/accounts/messages"/>", showMessages);
	}


	$(document).ready(onLoad);
</script>



		
<!-- <script>	
	
	function updateMessageLink(data){
		/* alert(data.number); */
		$("#numberMessages").text(data.number);
		
	}
	
	function updatePage(){
		alert("updating page");
		$.getJSON("<c:url value="/json/accounts/messages"/>", updateMessageLink);
		
	}
	
	function onLoad(){
		updatePage();
		window.setInterval(updatePage, 30000);
	}
	
	$(document).ready(onLoad);
	
</script> -->
	
	
	
	
	
	
	
	
	