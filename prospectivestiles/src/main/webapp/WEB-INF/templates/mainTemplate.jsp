<%@ page language="java" contentType="text/html; charset=US-ASCII"
	pageEncoding="US-ASCII"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<meta http-equiv="Content-Type" content="text/html; charset=US-ASCII">
	<title><tiles:insertAttribute name="title"></tiles:insertAttribute></title>
	
	
	<%-- <link rel="shortcut icon"
		href="${pageContext.request.contextPath}/resources/css/images/favicon-spring.ico" /> --%>
	<link rel="shortcut icon"
		href="${pageContext.request.contextPath}/resources/images/favicon-2.jpg" />
 	<%-- <link rel="shortcut icon"
		href="${pageContext.request.contextPath}/resources/images/favicon-spring.ico" /> --%>
	
	<%-- <script type="text/javascript" src="${pageContext.request.contextPath}/resources/script/jquery.js"></script> --%>
	
	<!-- Latest compiled and minified CSS -->
	<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.css">
	
	<link rel="stylesheet" type="text/css"
		href="${pageContext.request.contextPath}/resources/css/style.css" />
	
	<tiles:insertAttribute name="includes"></tiles:insertAttribute>

</head>
<body>



	<div id="container" class="container no-right-left-padding">

		<div id="greeting">
			<div>
				<sec:authorize access="isAnonymous()">
					<div>
						Welcome, guest. | <a href="<c:url value='/login'/>">Log in</a>
					</div>
				</sec:authorize>
				<sec:authorize access="isAuthenticated()">
					<div>
						Welcome,
						<sec:authentication property="principal.fullName" />
						. | <a href="<c:url value='/j_spring_security_logout'/>">Log
							out</a>
					</div>
				</sec:authorize>
			</div>
		</div>

		<div class="header">
			<tiles:insertAttribute name="header"></tiles:insertAttribute>
		</div>

		<div class="line"></div>

		<div class="navbar navbar-inverse navbar-static-top">
			<div>

				<a href="<c:url value='/welcome'/>" class="navbar-brand">Prospectives</a>

				<button class="navbar-toggle" data-toggle="collapse"
					data-target=".navHeaderCollapse">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>

				<div class="collapse navbar-collapse navHeaderCollapse">

					<ul class="nav navbar-nav navbar-right">

						<li><a href="<c:url value='/welcome'/>">Home</a></li>
						<sec:authorize access="!isAuthenticated()">
							<li><a href="<c:url value='/registrationform'/>">Register</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="<c:url value='/registrationform'/>">Register</a></li>
						</sec:authorize>
						<sec:authorize access="isAuthenticated()">
							<li><a href="<c:url value='/myAccount'/>">MyAccount</a></li>
						</sec:authorize>
						<sec:authorize access="hasRole('ROLE_ADMIN')">
							<li><a href="<c:url value='/accounts/notification'/>">Notifications</a></li>
						</sec:authorize>
						<!-- <li><a href="#">Blog</a></li> -->
						<li class="dropdown"><a href="#" class="dropdown-toggle"
							data-toggle="dropdown">Social Media <b class="caret"></b></a>
							<ul class="dropdown-menu">
								<li><a href="#">Twitter</a></li>
								<li><a href="#">Facebook</a></li>
								<li><a href="#">Google+</a></li>
								<li><a href="#">Instagram</a></li>
							</ul></li>
						<!-- <li><a href="#">About</a></li> -->
						<!-- <li><a href="#">Contact</a></li> -->

					</ul>

				</div>

			</div>
		</div>

		<!-- <div class="text-center">

			<div class="jumbotron">
				<h4>Welcome Lorem ipsum dolor sit amet</h4>
			</div>

		</div> -->
		
		<div>
			<div class="row">

				<div class="sidebar">
					<tiles:insertAttribute name="sidebar"></tiles:insertAttribute>
				</div>

				<div class="col-lg-9">

					<div class="panel panel-default">

						<div class="panel-body">
						
							<div class="bodycontent">
								<div class="row">
									<div class="navMenu">
										<tiles:insertAttribute name="navMenu"></tiles:insertAttribute>
									</div>
								</div>
								
								<tiles:insertAttribute name="bodycontent"></tiles:insertAttribute>
							</div>

						</div>

					</div>

				</div>

			</div>

		</div>

		<hr />
		<div class="footer">
			<tiles:insertAttribute name="footer"></tiles:insertAttribute>
		</div>

	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/resources/js/bootstrap.js"></script>
		
		
	<!-- Twitter Bootstrap to add 'active' class to li in Master page 
	http://stackoverflow.com/questions/20117985/twitter-bootstrap-add-active-class-to-li-in-master-page-->	
	<!-- <script>
		$(function (){
			$('.nav-tabs li').click(function (e) {
			    $('.nav-tabs li.active').removeClass('active');
			    var $this = $(this);
			    if (!$this.hasClass('active')) {
			        $this.addClass('active');
			    }
			    e.preventDefault();
			});
		});
	  </script> -->

</body>
</html>