<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>


<h1>All Accounts Page</h1>

<h3><a class="btn btn-info" href="<c:url value='/registrationform'/>">Register</a></h3>

<%-- <div class="table-responsive">
	<table class="table table-hover table-striped" data-filter="#filter" data-page-size="5"> 
		 <thead>
			<tr>
				<th data-toggle="true">Username</th>
				<th data-sort-initial="true">firstName</th>
				<th>lastName</th>
				<th data-hide="phone,tablet" data-name="Email address">Email</th>
				<th>acceptTerms</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>
					<c:url var="myAccountUrl" value="/accounts/${user.id}" />
					<a href="${myAccountUrl}"><c:out value="${user.username}"></c:out></a>
				</td>
				<td><c:out value="${user.firstName}"></c:out></td>
				<td><c:out value="${user.lastName}"></c:out></td>
				<td><c:out value="${user.email}"></c:out></td>
				<td><c:out value="${user.acceptTerms}"></c:out></td>
			</tr>
		</c:forEach>
		</tbody>
		<tfoot>
			<ul class="pagination">
			  <li><a href="#">&laquo;</a></li>
			  <c:forEach var="i" begin="1" end="${totalPages}">
				   <c:choose> 
					  <c:when test="${i == page}" > 
					    <li class="active"><a href="<c:url value='/accounts/accounts/${i}'/>"><c:out value="${i}"/></a></li> 
					  </c:when> 
					  <c:otherwise> 
					    <li><a href="<c:url value='/accounts/accounts/${i}'/>"><c:out value="${i}"/></a></li>
					  </c:otherwise> 
					</c:choose> 
				</c:forEach>
			  <li><a href="#">&raquo;</a></li>
			</ul>
        </tfoot>
	</table>
                   		usersCount:<c:out value="${usersCount}" />
                   		pageSize:<c:out value="${pageSize}" />
                   		page:<c:out value="${page}" />
                   		totalPages:<c:out value="${totalPages}" />
</div> --%>



<!-- ############################################################################################# -->
<!-- 			UNDER CONSTRUCTION			 -->
<!-- Using JavaScript for Pagination -->
<!-- ############################################################################################# -->

<div id="search-div">

</div>

<%-- <form id="searchForm" action="${searchFormUrl}" method="post">
	<div>
		<input type="text" name="search" />
		<input type="submit" value="Search" />
	</div>
</form> --%>
				
<%-- <form:form action="${searchFormUrl}" id="searchForm"
	modelAttribute="address" role="form" class="form-horizontal">


	<div class="form-group row">
		<label for="city" class="col-sm-2 control-label">Search</label>
		<div class="col-sm-5">
			<form:input path="city" class="form-control" placeholder="Last name" />
		</div>
	</div>


	<div class="form-group">
		<label for="" class="col-sm-2 control-label">&nbsp;</label>
		<div class="col-sm-10">
			<input class="btn btn-primary" type="submit" value="Search"></input> 
		</div>
	</div>

</form:form> --%>

<div class="table-responsive">
	<table class="table table-hover table-striped" data-filter="#filter" data-page-size="5"> 
		 <thead>
			<tr>
				<th data-toggle="true">Username</th>
				<th data-sort-initial="true">firstName</th>
				<th>lastName</th>
			</tr>
		</thead>
		<tbody id="tbody-content">
		

		</tbody>
		<tfoot>
			<ul id="pagination-ul" class="pagination">
			  
			  
			</ul>
        </tfoot>
	</table>
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
	<%-- <c:url var="getAccountsUrl" value="/accounts/accounts/{page}/{pageSize}" /> --%>
	<c:url var="getAccountsUrl" value="/accounts/accounts/1/3" />
	<c:url var="searchAccountUrl" value="/accounts/accounts/searchAccount" />
	<%-- <c:url var="getAccountsUrl" value="/accounts/accountspage?page=1&pageSize=3" /> --%>
</sec:authorize>
<sec:authorize access="hasRole('ROLE_USER')">

</sec:authorize>

<script>	
	/* 
	when the page loads, call up updatePage method, 
	this will call "/accounts/accounts/1/3" passing default page number and default page size
	the controller will return x accounts, the updatePage fn will pass the results to fetchAndDisplayAccounts fn
	this fn displays all the accounts in the table and calls the pagination fn.
	When I use this: $.getJSON("<c:url value="/accounts/accounts/'+i+'/3"/>"... the var i is not passed to the url
	 */
	 
	 

	/* 
	#############################################
			searchAccount
	#############################################
	 */
	 
	function success(data){
		alert("Successfully ...");
		/* alert(data); */

		fetchAndDisplayAccounts(data);
	}
	
	function error(data){
		alert("Error. ... within JS");
		/* alert(data.stId); */
	}
	
	function searchAccount(){
		
		var text = $("#searchtext").val();
		var searchUrl = "${pageContext.request.contextPath}"+"/accounts/accounts/searchAccount";
		alert("searchUrl" + searchUrl);
		alert("searchAccountUrl" + "${searchAccountUrl}");
		
		$.ajax({
			"type": 'POST',
			"url" : '${searchAccountUrl}',
			"data": JSON.stringify({"text": text}),
			"complete": function(response, textStatus){
				return alert("#### complete called. " + textStatus);
			},
			"success": success,
			"error" : error,
			contentType : "application/json",
			dataType : "json"
		});
		
	}
	
	/* I am passing data, but not using it */
	function searchFormProcessor(data){
		
		$("div#search-div").html("");
		
		var searchForm = document.createElement("form");
		searchForm.setAttribute("class", "searchForm");
		searchForm.setAttribute("id", "searchForm");
		
		var searchtext = document.createElement("input");
		searchtext.setAttribute("class", "searchtext");
		searchtext.setAttribute("id", "searchtext");
		
		var searchButton = document.createElement("button");
		searchButton.setAttribute("class", "searchButton");
		searchButton.setAttribute("type", "submit");
		searchButton.setAttribute("value", "Search");
		
			searchButton.onclick = function(){
				return function(){
					searchAccount();
				}
			}();
			/* searchButton.onclick = searchAccount(); */
		
		searchForm.appendChild(searchtext);
		searchForm.appendChild(searchButton);
		
		$("div#search-div").append(searchForm);
		
	}
	
	/* 
	#############################################
			END searchAccount
	#############################################
	 */
	
	function goToPage(i){
		/* alert("i" + i); */
		/* $.getJSON("<c:url value="/accounts/accounts/'+i+'/3"/>", fetchAndDisplayAccounts); */
		/* $.getJSON('<c:url value="/accounts/accountspage/" />', { page: pageNum, pageSize: 3 }, fetchAndDisplayAccounts); */
		
		$.getJSON("${pageContext.request.contextPath}"+"/accounts/accounts/"+i+"/3", fetchAndDisplayAccounts);
		
	}
	
	/* when user clicks on the username link rediredt to the student page */
	function goToAccount(id){
		var url = "${pageContext.request.contextPath}"+"/accounts/" + id;
		/* to simulate an HTTP redirect, use location.replace */
		window.location.replace(url);
	}
	
	function fetchAndDisplayAccounts(data){
		
		$("#tbody-content").html("");
		
		/* fetch all the accounts from the db and display it */
		for (var i = 0; i < data.users.length; i++) {
			
			var user = data.users[i];
			
			var userTr = document.createElement("tr");
			userTr.setAttribute("class", "user");
			
			
			var usernameTd = document.createElement("td");
			
			var accountLink = document.createElement("a");
			accountLink.setAttribute("class", "accountLink");
			accountLink.setAttribute("href", "#");
			accountLink.setAttribute("onclick", "goToAccount(" + user.id + ")");
			accountLink.appendChild(document.createTextNode(user.username));
			
			usernameTd.appendChild(accountLink);
			
			
			var firstnameTd = document.createElement("td");
			firstnameTd.appendChild(document.createTextNode(user.firstName));
			var lastnameTd = document.createElement("td");
			lastnameTd.appendChild(document.createTextNode(user.lastName));
			
			userTr.appendChild(usernameTd);
			userTr.appendChild(firstnameTd);
			userTr.appendChild(lastnameTd);
			
			$("#tbody-content").append(userTr);
			
		}
		getPagination(data);
		searchFormProcessor(data);
	}
	
	/* I should pass totalPages only */
	function getPagination(data){
		
		$("#pagination-ul").html("");
		
		for (var i = 1; i <= data.totalPages; i++) {
			
			var pageLi = document.createElement("li");
			
			if(i == data.page){
				pageLi.setAttribute("class", "active");
			} else {
				pageLi.setAttribute("class", "pageLi");
			}
			
			pageLi.setAttribute("id", "pageLi" + i);
			
			
			var pageLink = document.createElement("a");
			pageLink.setAttribute("class", "pageLink");
			pageLink.setAttribute("href", "#");
			pageLink.setAttribute("onclick", "goToPage(" + i + ")");
			pageLink.appendChild(document.createTextNode(i));

			pageLi.appendChild(pageLink);
			
			
			$("#pagination-ul").append(pageLi);
			
		}
	}
	
	function updatePage(){
		$.getJSON('${getAccountsUrl}', fetchAndDisplayAccounts);
	}
	
	function onLoad(){
		updatePage();
	}
	
	$(document).ready(onLoad);
	
	

</script>