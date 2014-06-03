<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasRole('ROLE_ADMIN')">
	<c:url var="myAccount" value="/accounts/${userEntity.id}" />
	<c:url var="educationUrl" value="/accounts/${userEntity.id}/educations" />
	<%-- <c:url var="newHighSchoolUrl" value="/accounts/${userEntity.id}/highSchool/new" />
	<c:url var="newInstituteUrl" value="/accounts/${userEntity.id}/institute/new" /> --%>
	<c:url var="addressUrl" value="/accounts/${userEntity.id}/addresses" />
	<c:url var="emergencyContactsUrl" value="/accounts/${userEntity.id}/emergencyContacts" />
	<c:url var="applyingForUrl" value="/accounts/${userEntity.id}/applyingFor" />
	<c:url var="standardTestsUrl" value="/accounts/${userEntity.id}/standardTests" />
	<c:url var="uploadedFilesUrl" value="/accounts/${userEntity.id}/files" />
	<c:url var="employersUrl" value="/accounts/${userEntity.id}/employers" />
	<c:url var="checklistUrl" value="/accounts/${userEntity.id}/checklists" />
	<c:url var="evaluationUrl" value="/accounts/${userEntity.id}/evaluations" />
	<c:url var="reportsUrl" value="/accounts/${userEntity.id}/reports" />
	<c:url var="missingDocumentsUrl" value="/accounts/${userEntity.id}/reports/missingDocuments" />
	<c:url var="evaluationReportUrl" value="/accounts/${userEntity.id}/reports/evaluationReport" />
	<c:url var="acceptanceLetterUrl" value="/accounts/${userEntity.id}/reports/acceptanceLetter" />
	<c:url var="messagesUrl" value="/accounts/${userEntity.id}/messages" />
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">
	<c:url var="myAccount" value='/myAccount'/>
	<c:url var="educationUrl" value="/myAccount/educations" />
	<%-- <c:url var="newHighSchoolUrl" value="/myAccount/highSchool/new" />
	<c:url var="newInstituteUrl" value="/myAccount/institute/new" /> --%>
	<c:url var="addressUrl" value="/myAccount/addresses" />
	<c:url var="emergencyContactsUrl" value="/myAccount/emergencyContacts" />
	<c:url var="applyingForUrl" value="/myAccount/applyingFor" />
	<c:url var="standardTestsUrl" value="/myAccount/standardTests" />
	<c:url var="uploadedFilesUrl" value="/myAccount/files" />
	<c:url var="employersUrl" value="/myAccount/employers" />
	<c:url var="messagesUrl" value="/myAccount/messages" />
</sec:authorize>

<div id="navmenu">
	<ul class="nav nav-tabs">
		<sec:authorize access="isAuthenticated()">
			<li id="personal-li active">
				<a href="${myAccount}">Profile</a>
			</li>
			<%-- <li id="personal-li" class="dropdown active">
			  <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-toggle="dropdown">Profile <span class="caret"></span></a>
			  <ul class="dropdown-menu">
			    <li id="personal-personal-li"><a href="${myAccount}">Personal Info</a></li>
			    <li id="personal-addresses-li"><a href="${addressUrl}">Addresses</a></li>
			    <li id="personal-emmergency-li"><a href="${emergencyContactsUrl}">EmergencyContacts</a></li>
			    <li class="divider"></li>
			    <li id="personal-employers-li"><a href="${employersUrl}">Employment</a></li>
			    <li class="divider"></li>
			    <li><a href="#">One more separated link</a></li>
			  </ul>
			</li> --%>
			<li id="applyingfor-li">
				<a href="${applyingForUrl}">ApplyingFor</a>
			</li>
			<li id="education-li">
				<a href="${educationUrl}">Educational bgd</a>
			</li>
			<li id="standardtests-li">
				<a href="${standardTestsUrl}">StandardTest</a>
			</li>
			<li id="uploadedfiles-li">
				<a href="${uploadedFilesUrl}">Documents</a>
			</li>
		</sec:authorize>
		<sec:authorize access="hasRole('ROLE_ADMIN')">
		
			<li id="checklist-li">
				<a href="${checklistUrl}">Checklist</a>
			</li>
			<li id="evaluation-li">
				<a href="${evaluationUrl}">Evaluation</a>
			</li>
			<li id="reports-li" class="dropdown">
			  <a href="#" class="dropdown-toggle" data-toggle="dropdown" data-toggle="dropdown">Reports<span class="caret"></span></a>
			  <ul class="dropdown-menu">
			    <li id="reports-reports-li"><a href="${reportsUrl}">Reports</a></li>
			    <li id="reports-missing-li"><a href="${missingDocumentsUrl}">MissingDocuments</a></li>
			    <li id="reports-evaluation-li"><a href="${evaluationReportUrl}">evaluationReport</a></li>
			    <li id="reports-acceptance-li"><a href="${acceptanceLetterUrl}">acceptanceLetter</a></li>
			    <li><a href="#">Link</a></li>
			    <li class="divider"></li>
			    <li><a href="#">Link</a></li>
			    <li class="divider"></li>
			    <li><a href="#">Link</a></li>
			  </ul>
			</li>
		</sec:authorize>
		<sec:authorize access="isAuthenticated()">
			<li id="messages-li">
				<a href="${messagesUrl}">Messages</a>
			</li>
		</sec:authorize>
	</ul>
</div>
<div id="localstoragetest"></div>

<!-- jQuery for JavaScript plugins -->

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery-1.7.1.min.js"></script>

<script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script>

<!-- Reference:
http://stackoverflow.com/questions/8849080/highlight-or-add-class-to-parent-li-element-in-click
http://stackoverflow.com/questions/10523433/how-do-i-keep-the-current-tab-active-with-twitter-bootstrap-after-a-page-reload

 -->

<script>
	$( document ).ready( function() {
	    $( '#navmenu > ul > li' ).click( function(e) {
	    	// save the latest li; you can also use cookies
	    	// $.cookie('last_tab', $(e.target).attr('href'));
	        localStorage.setItem('lastLi', $(e.target).parent().attr('id'));
	        
	    	/* 
	    	jquery get event object target's parent
	    	$(event.target).parent(): this returns a jQuery object. 
	    	That object does not have an id property.
	    	So you can't use $(event.target).parent().id 
	    	Use $(e.target).parent().attr('id') or $(e.target).parent()[0].id
	        $('selector').attr('id');
	    	 */
	    	
	        //alert("ul > li 2:" + $(e.target).parent().attr('id'));
	        //alert("ul > li 3:" + $( '#navmenu > ul > li' ).attr('id'));
	        //alert("ul > li 4:" + lastLi);
	    	
	    });
	    
	    $( '#navmenu > ul > li > ul > li' ).click( function(e) {
	    	localStorage.setItem('lastLi', $(e.target).parent().parent().parent().attr('id'));
	    	
	        //alert("ul > li > ul > li 2:" + $(e.target).parent().attr('id'));
	        //alert("ul > li > ul > li 4:" + $(e.target).parent().parent().parent().attr('id'));
	        //alert("ul > li > ul > li 5:" + $( '#navmenu > ul > li > ul > li' ).attr('id'));
	        //alert("ul > li > ul > li 6:" + lastLi);
	    });
	  	//go to the latest li, if it exists:
	    var lastLi = localStorage.getItem('lastLi');
	    //alert("e.lastLi: " + lastLi);
        if (lastLi) {

        	// for testing purpose display it in the page
        	//$("#localstoragetest").text(lastLi);
        	
            $('#navmenu > ul').children().removeClass('active');
        	$('#'+ lastLi).addClass('active');
        	
        }
	   
	});
	
</script>
<!-- <script>
	$( document ).ready( function() {
	    $( '#navmenu > ul > li' ).click( function() {
	        $( '#navmenu > ul' ).children('li').removeClass();
	        $( this ).addClass( 'active' );
	    });
	    $( '#navmenu > ul > li > ul > li' ).click( function() {
	        $( '#navmenu > ul' ).children('li').removeClass();
	        $( this ).parent('li').addClass( 'active' );
	    });
	});
	
</script> -->


