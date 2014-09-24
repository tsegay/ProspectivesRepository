<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<div class="col-lg-3">

	<div class="panel panel-default">
		<!-- Default panel contents -->
		<div class="panel-heading">Welcome to Fall 2014</div>
		<!-- <div class="panel-body">
		  <p>Welcome to Summer 2014</p>
		</div> -->
			  
		<ul class="list-group">
	
			<li class="list-group-item active"><a href="<c:url value='/welcome'/>">Home</a></li>
			<sec:authorize access="!isAuthenticated()">
				<li class="list-group-item"><a href="<c:url value='/registrationform'/>">SignUp</a></li>
			</sec:authorize>
			<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT_PENDING', 'ROLE_STUDENT_INPROCESS', 'ROLE_STUDENT_COMPLETE', 'ROLE_STUDENT_ADMITTED')">
				<li class="list-group-item"><a href="<c:url value='/myAccount'/>">MyAccount</a></li>
			</sec:authorize>
			
			<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
				<li class="list-group-item"><a href="<c:url value='/accounts/notification'/>">Notifications</a></li>
				<li class="list-group-item"><a href="<c:url value='/registerUser'/>">SignUp New Applicant</a></li>
				<li class="list-group-item"><a href="<c:url value='/accounts'/>">All Accounts</a></li>
				<li class="list-group-item"><a href="<c:url value='/accounts/getAccountsByTermStatusState'/>">Generate Report</a></li>
				<%-- <li class="list-group-item"><a href="<c:url value='/adminpage'/>">Admin</a></li> --%>
				<%-- <li class="list-group-item"><a href="<c:url value='/accounts/accounts/1'/>">All Accounts</a></li> --%>
				<li class="list-group-item"><a href="<c:url value='/accounts/agents'/>">Agents</a></li>
				<li class="list-group-item"><a href="<c:url value='/accounts/referrers'/>">Referrers</a></li>
				<%-- <li class="list-group-item"><a href="<c:url value='#'/>">Admission Counselors</a></li> --%>
				<li class="list-group-item"><a href="<c:url value='/accounts/pendingStudents'/>">Pending</a></li>
				<li class="list-group-item"><a href="<c:url value='/accounts/inprocessStudents'/>">In process</a></li>
				<li class="list-group-item"><a href="<c:url value='/accounts/completeStudents'/>">Complete</a></li>
				<li class="list-group-item"><a href="<c:url value='/accounts/admittedStudents'/>">Admitted</a></li>
			</sec:authorize>
			
			<sec:authorize access="isAuthenticated()">
				<li class="list-group-item"><a target="_blank" href="<c:url value='/resources/docs/EnrollmentAgreement.pdf'/>">Enrollment Agreement</a></li>
				<li class="list-group-item"><a target="_blank" href="<c:url value='/resources/docs/GrievanceProcedure.pdf'/>">Grievance Policy</a></li>
				<li class="list-group-item"><a href="<c:url value='/privacyAndPolicyStatement'/>">Privacy and Policy Statement</a></li>
				<li class="list-group-item"><a href="<c:url value='/paymentPage'/>">Pay Application Fee</a></li>
			</sec:authorize>
			
		</ul>
	</div>
	
	<!-- <div class="list-group">
		<a href="#" class="list-group-item active">
			<h4 class="list-group-item-heading">Lorem ipsum</h4>
			<p class="list-group-item-text">Fusce</p>
		</a> <a href="#" class="list-group-item">
			<h4 class="list-group-item-heading">Lorem ipsum</h4>
			<p class="list-group-item-text">Fusce</p>
		</a> <a href="#" class="list-group-item">
			<h4 class="list-group-item-heading">Lorem ipsum</h4>
			<p class="list-group-item-text">Fusce</p>
		</a> <a href="#" class="list-group-item">
			<h4 class="list-group-item-heading">Lorem ipsum</h4>
			<p class="list-group-item-text">Fusce</p>
		</a>
	</div> -->
	
</div>