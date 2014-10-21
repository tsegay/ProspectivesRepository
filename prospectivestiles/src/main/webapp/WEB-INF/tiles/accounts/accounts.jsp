<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>

<sec:authorize access="hasAnyRole('ROLE_ADMIN', 'ROLE_ADMISSION', 'ROLE_ADMISSION_ASSIST')">
	<c:url var="getAccountsUrl" value="/accounts/accounts/1/3" />
	<c:url var="searchAccountUrl" value="/accounts/accounts/searchAccount" />
	
	<%-- <c:url var="getAccountsUrl" value="/accounts/accounts/{page}/{pageSize}" /> --%>
	<%-- <c:url var="getAccountsUrl" value="/accounts/accountspage?page=1&pageSize=3" /> --%>
</sec:authorize>

<h1>All Prospective Students</h1>


<!-- ############################################################################################# -->
<!-- Using JavaScript for Pagination -->
<!-- ############################################################################################# -->

<div id="search-div" class="text-right">

</div>

<%-- <form id="searchForm" action="${searchFormUrl}" method="post">
	<div>
		<input type="text" name="search" />
		<input type="submit" value="Search" />
	</div>
</form> --%>
				
<div class="table-responsive">
	<table class="table table-hover table-striped" data-filter="#filter" data-page-size="5"> 
		 <thead>
			<tr>
				<th data-toggle="true">Username</th>
				<th data-sort-initial="true">First Name</th>
				<!-- <th data-hide="phone,tablet" data-name="Email address">Email</th> -->
				<th>Last Name</th>
				<th>Date of Birth</th>
				<th>Email</th>
				<th>Checklist</th>
				<!-- <th>Evaluation</th> -->
				<th>Status</th>
			</tr>
		</thead>
		<tbody id="tbody-content">
		

		</tbody>
		<tfoot>
			<div class="text-center">
				<ul id="pagination-ul" class="pagination">
				  
				  
				</ul>
			</div>
			<p class="text-right"><span id="pageSize">0</span> of <span id="usersCount">0</span> Users</p>
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



<script>
	/* 
	#############################################
	#############################################
	 */
	/* 
	when the page loads, call up updatePage method, 
	this will call "/accounts/accounts/1/3" passing default page number and default page size
	the controller will return x accounts, the updatePage fn will pass the results to fetchAndDisplayAccounts fn
	this fn displays all the accounts in the table and calls the pagination fn.
	When I use this: $.getJSON("<c:url value="/accounts/accounts/'+i+'/3"/>"... the var i is not passed to the url
			
	GETTING THE CHECKLIST NUMS USING JSON	
	$.getJSON("${pageContext.request.contextPath}"+"/accounts/"+ user.id + "/checklistStatus", 
			function checklistStatus(chkl) {
		return checklistTd.appendChild(document.createTextNode(chkl.checklistCount));
	});
	The problem with this one is:
	The function you pass to getJSON is run when the response to the HTTP request arrives which is not immediately.
	The return statement executes before the response, so the variable hasn't yet been set.
	Source: http://stackoverflow.com/questions/4200641/how-to-return-a-value-from-a-function-that-calls-getjson
	callback function (function(data) {...}) runs later when the response comes back...because it's an asynchronous function. 
	Instead use the value once you have it set. 
	This is the way all asynchronous behavior should be, kick off whatever needs the value once you have it...which is when the server responds with data.
	So use something like this:
	
	function lookupRemote(searchTerm)
	{
	    var defaultReturnValue = 1010;
	    var returnValue = defaultReturnValue;
	    $.getJSON(remote, function(data) {           
	        if (data != null) {
	              $.each(data.items, function(i, item) {                 
	                    returnValue = item.libraryOfCongressNumber;
	              });
	        }
	        OtherFunctionThatUsesTheValue(returnValue);
	     });
	}
	
	 */
	 /* 
	#############################################
	#############################################
	 */
	 

	/* 
	#############################################
			searchAccount
	#############################################
	 */
	 
	 var chklNum;
	 
	function success(data){
		/* alert("Successfully ..."); */
		/* alert(data); */

		fetchAndDisplayAccounts(data);
	}
	
	function error(data){
		/* alert("Error. ... within JS"); */
		/* alert(data.stId); */
	}
	
	function searchAccount(){
		
		var text = $("#searchtext").val();
		var searchUrl = "${pageContext.request.contextPath}"+"/accounts/accounts/searchAccount";
		/* alert("searchUrl" + searchUrl);
		alert("searchAccountUrl" + "${searchAccountUrl}"); */
		
		$.ajax({
			type: 'POST',
			url : '${searchAccountUrl}',
			data: JSON.stringify({"text": text}),
			complete: function(response, textStatus){
				 /* alert("#### complete called. " + textStatus); */
			},
			success: success,
			error : function(jqXHR, textStatus, errorThrown ){
				/* alert(errorThrown); */
			},
			contentType : "application/json",
			dataType : "json"
		});
		return false;
		
	}
	$(document).on("submit","#searchForm",null,function(){
		/* alert("submitting form"); */
		searchAccount();
		/* alert("submit form complete"); */
		return false;
	});
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
		searchButton.appendChild(document.createTextNode("Search"));
			/* searchForm.onSubmit = function(){
			
					searchAccount();
					return false;
				
			}();
			searchButton.onclick = function(){
				return function(){
					//searchAccount();
				};
			}(); */
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
	
	/* appends the checklist count and total checklist eg. 5/8 to the td tag */
	function insertChklCountToTD(id, chkCount, chkTot) {
		$("#checklist-td" + id).text(chkCount + "/" + chkTot);
	}
	
	/* Uses JSON to get the checklist count for a user from the controller by passing userId
	then, pass the count to the insertChklCountToTD fn to append it to the TD tag
	*/
	function checklistState(id){
		$.getJSON("${pageContext.request.contextPath}"+"/accounts/"+ id + "/checklistState", function checklistNums(data) {
			if (data != null) {
				chkCount = data.checklistCount;
				chkTot = data.checklistTotal;
			}
			/* OtherFunctionThatUsesTheValue(returnValue) */
			insertChklCountToTD(id, chkCount, chkTot);
		});
		
	}
	
	/* appends the evaluation count and total evaluation eg. 5/8 to the td tag */
	function insertEvalCountToTD(id, evalCount, evalTot, evaluationStatus) {
		$("#evaluation-td" + id).text(evalCount + "/" + evalTot);
		$("#status-td" + id).text(evaluationStatus);
	}
	
	/* Uses JSON to get the evaluation count for a user from the controller by passing userId
	then, pass the count to the insertEvalCountToTD fn to append it to the TD tag
	*/
	function evaluationState(id){
		var evalCount=0;
		var evalTot=0;
		$.getJSON("${pageContext.request.contextPath}"+"/accounts/"+ id + "/evaluationState", function evaluationNums(data) {
			if (data != null) {
				evalCount = data.evaluationCount;
				evalTot = data.evaluationTotal;
				evaluationStatus = data.evaluationStatus;
			}
			/* OtherFunctionThatUsesTheValue(returnValue) */
			insertEvalCountToTD(id, evalCount, evalTot, evaluationStatus);
		});
		
	}
	
	function fetchAndDisplayAccounts(data){
		
		$("#usersCount").text(data.usersCount);
		$("#pageSize").text(data.pageSize);
		
		$("#tbody-content").html("");
		
		/* fetch all the accounts from the db and display it */
		for (var i = 0; i < data.users.length; i++) {
			
			var user = data.users[i];
			
			/* Create <tr> */
			var userTr = document.createElement("tr");
			userTr.setAttribute("class", "user");
			
				/* Create <td> */
				var usernameTd = document.createElement("td");
				
					var accountLink = document.createElement("a");
					accountLink.setAttribute("class", "accountLink");
					accountLink.setAttribute("href", "#");
					accountLink.setAttribute("onclick", "goToAccount(" + user.id + ")");
					accountLink.appendChild(document.createTextNode(user.username));
				
				usernameTd.appendChild(accountLink);
				
				/* Create <td> */
				var firstnameTd = document.createElement("td");
				firstnameTd.appendChild(document.createTextNode(user.firstName));
				
				/* Create <td> */
				var lastnameTd = document.createElement("td");
				lastnameTd.appendChild(document.createTextNode(user.lastName));
			
				/* Create <td> */
				var dobTd = document.createElement("td");
					var date = new Date(user.dob);
				
				dobTd.appendChild(document.createTextNode(date.toString('MM-dd-yyyy')));
				/* dobTd.appendChild(document.createTextNode(" "));
				dobTd.appendChild(document.createTextNode(date.toString('HH:mm'))); */
			
				/* Create <td> */
				var emailTd = document.createElement("td");
				emailTd.appendChild(document.createTextNode(user.email));
				
				/* Removing the checklist class for now. Using evaluatin only. But label it as checklist. */
				/* Create <td> */
				/* var checklistTd = document.createElement("td");
				checklistTd.setAttribute("id", "checklist-td" + user.id);
				checklistState(user.id); */
				
				
				/* Create <td> */
				var evaluationTd = document.createElement("td");
				evaluationTd.setAttribute("id", "evaluation-td" + user.id);
				evaluationState(user.id);
				
				/* Create <td> */
				var statusTd = document.createElement("td");
				statusTd.setAttribute("id", "status-td" + user.id);
			
			/* Append all <TD>s to the <TR> */
			userTr.appendChild(usernameTd);
			userTr.appendChild(firstnameTd);
			userTr.appendChild(lastnameTd);
			userTr.appendChild(dobTd);
			userTr.appendChild(emailTd);
			/* userTr.appendChild(checklistTd); */
			userTr.appendChild(evaluationTd);
			userTr.appendChild(statusTd);
			
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