<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<style>
<!--
.notification {
	margin-bottom: 20px;
	border-bottom: 1px dashed brown;
}

div.subject {
	font-size: large;
	font-weight: bold;
}

div.notificationbody {
	display: block;
	font-style: italic;
}

div.name {
	font-size: medium;
}

form.notificationform {
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

<h1>Notifications page</h1>


<a href="<c:url value="/notifications"/>">Notifications (<span id="notificationsCount">0</span>)</a>

<div id="notifications">

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
	<c:url var="getNotificationsUrl" value="/accounts/notifications" />
	<%-- <c:url var="markReadUrl" value="/accounts/notifications/markRead" /> --%>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">

</sec:authorize>

<script>	
	var timer;
	
	/* 
	#############################################
			markNoticeRead
	#############################################
	 */
	
	function success(data){
		alert("Successfully markNoticeRead");
		/* alert(data);
		updatePage();
		startTimer();
		$(".notification").text("Message sent"); */
		
		var url = "${pageContext.request.contextPath}"+"/accounts/" + studentId;
		
		/* to simulate an HTTP redirect, use location.replace */
		window.location.replace(url);
	}
	
	function error(data){
		alert("Error. markNoticeRead");
		/* alert(data); */
	}
	
	function markNoticeRead(noticeId){
		alert("markNoticeRead noticeId..." + noticeId);
		var markReadUrl = "${pageContext.request.contextPath}"+"/accounts/notifications/markRead";
		alert("markNoticeRead markReadUrl..." + markReadUrl);
		
		$.ajax({
			"type": 'POST',
			"url" : '${markReadUrl}',
			"data": JSON.stringify({"noticeId": noticeId}),
			"complete": function(response, textStatus){
				return alert("#### complete called. " + textStatus);
			},
			"success": success,
			"error" : error,
			contentType : "application/json",
			dataType : "json"
		});
		
		
	}
	
	/* 
	#############################################
			End markNoticeRead
	#############################################
	 */
	 
	/* when user clicks on the email subject link 
	mark the notice as read: pass the notice id to the fn markNoticeRead
	rediredt to the student's page */
	function goToMessage(noticeId, studentId){
		alert("goToMessage noticeId..." + noticeId);
		alert("goToMessage studentId..." + studentId);
		/* 
		when user click on the notice, assuming user read it, i want to mark the notice as read
		 */
		 
		 markNoticeRead(noticeId);
		 
		/* var url = "${pageContext.request.contextPath}"+"/accounts/" + id + "/messages"; */
		/* var url = "${pageContext.request.contextPath}"+"/accounts/" + studentId; */
		
		/* to simulate an HTTP redirect, use location.replace */
		/* window.location.replace(url); */
	}
	
	function fetchAndDisplayNotifications(data){
		
		$("#notificationsCount").text(data.notificationsCount);
		$("div#notifications").html("");
		
		/* fetch all the notifications from the db and display it */
		for (var i = 0; i < data.notifications.length; i++) {
			var notification = data.notifications[i];
			
			var notificationDiv = document.createElement("div");
			notificationDiv.setAttribute("class", "notification");
			
			var dateSpan = document.createElement("span");
			dateSpan.setAttribute("class", "date");
			
			var date = new Date(notification.dateCreated);
			
			dateSpan.appendChild(document.createTextNode(date.toString('MM-dd-yyyy')));
			dateSpan.appendChild(document.createTextNode(" "));
			dateSpan.appendChild(document.createTextNode(date.toString('HH:mm')));
			dateSpan.appendChild(document.createTextNode(" "));
			
			var studentSpan = document.createElement("span");
			studentSpan.setAttribute("class", "student");
			studentSpan.appendChild(document.createTextNode(notification.student.fullName));
			studentSpan.appendChild(document.createTextNode(" "));
			/* studentSpan.appendChild(document.createTextNode(notification.student.lastName));
			studentSpan.appendChild(document.createTextNode(" ")); */
			
			var subjectSpan = document.createElement("span");
			subjectSpan.setAttribute("class", "subject");
			
			subjectSpan.appendChild(document.createTextNode("("));
				var studentLink = document.createElement("a");
				studentLink.setAttribute("class", "emaillink");
				studentLink.setAttribute("href", "#");
				/* studentLink.setAttribute("onclick", "goToMessage(" + notification.student.id + ")"); */
				/* pass the notification */
				studentLink.setAttribute("onclick", "goToMessage(" + notification.id + ", " + notification.student.id + ")");
				studentLink.appendChild(document.createTextNode(notification.notice));
			
			subjectSpan.appendChild(studentLink);
			subjectSpan.appendChild(document.createTextNode(")"));
			
			notificationDiv.appendChild(dateSpan);
			notificationDiv.appendChild(studentSpan);
			notificationDiv.appendChild(subjectSpan);
			
			$("div#notifications").append(notificationDiv);
			
		}
	}
	
	function startTimer(){
		timer = window.setInterval(updatePage, 40000);
	}
	
	function stopTimer(){
		window.clearInterval(timer);
	}
	
	function updatePage(){
		$.getJSON('${getNotificationsUrl}', fetchAndDisplayNotifications);
	}
	
	function onLoad(){
		/* Call the dropdowns for the menu bar (Profile, Reports) via JavaScript */
		$('.dropdown-toggle').dropdown();
		updatePage();
		startTimer();
	}
	
	$(document).ready(onLoad);

</script>




