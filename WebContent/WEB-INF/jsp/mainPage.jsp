<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>


<html>
<head>
<title>Covertify</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resources/CSS/mainPage.css"/>
<!-- bootstrap css -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-giJF6kkoqNQ00vy+HMDP7azOuL0xtbfIcaT9wjKHr8RbDVddVHyTfAAsrekwKmP1" crossorigin="anonymous">
<!-- bootsrap bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta1/dist/js/bootstrap.bundle.min.js" integrity="sha384-ygbV9kiqUc6oa4msXn9868pTtWMgiQaeYH7/t7LECLbyPA2x65Kgf80OJFdroafW" crossorigin="anonymous"></script>
<script>
	window.onload = function() {
		// run code
		var query = new URLSearchParams(window.location.search);
		var code = "";
		for(var value of query.keys()) {
			if (value == "code") {
				code = query.get(value);
			}
		 }
		console.log(code);
		document.getElementById("code").value = code;
	};
</script>

</head>
<body>
	Logged in!
	<img alt="Covertify-Logo" src="Resources/Assets/Covertify.png" width=30%>
	
 	
 
  	<a href="album/readAlbums">read albums before</a>

 	
 	<h1>customer role : ${sessionScope.role}</h1>
	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout" 
			   method="POST">
	
		<input type="submit" value="Logout" />
	
	
	</form:form>
	
	
	<h1>choice of Covertify</h1>
	<c:forEach var="tempAlbum" items="${sessionScope.AuthList}">
				<!-- construct an "add" link with album id -->
					
			
			
						<div class="card" style="width: 18rem;">
							  <img src=${tempAlbum.image} class="card-img-top" alt="...">
							  <div class="card-body">
							    <h5 class="card-title">${tempAlbum.name}</h5>
							    <audio controls="controls" src=${tempAlbum.preUrl} />
					            Your browser does not support the
					            <code>audio</code> element
							   
							  </div>
						</div>
			
	
				
					
	</c:forEach>
	
	<h1>search section</h1>
	<form action="search">
	  <label for="search">search:</label><br>
	  <input type="text" id="fname" name="search" value="Enter Album Keyword..."><br>
	  <input type="hidden" name="code" id="code" value="" >
	  <input type="submit" value="Submit">
	</form> 
	<p>${user.getDisplayName()}</p>
	<img src=${user.getImages()[0].getUrl()} />
		<!-- loop over and print our customers -->
				<c:forEach var="tempAlbum" items="${albumList}">
				<!-- construct an "add" link with album id -->
						<c:url var="addLink" value="/album/add">
							<c:param name= "albumId" value="${tempAlbum.id}" />
							<c:param name= "albumName" value="${tempAlbum.name}" />
							<c:param name= "albumImage" value="${tempAlbum.image}" />
							<c:param name="albumPreUrl" value = "${tempAlbum.preUrl} " />
						</c:url>
				
			
						<div class="card" style="width: 18rem;">
							  <img src=${tempAlbum.image} class="card-img-top" alt="...">
							  <div class="card-body">
							    <h5 class="card-title">${tempAlbum.name}</h5>
							    <audio controls="controls" src=${tempAlbum.preUrl} />
					            Your browser does not support the
					            <code>audio</code> element
							   
							  </div>
						</div>
				
				
						 <a href = "${addLink}" class="btn btn-primary">Add</a>
				
					
				</c:forEach>
</body>
</html>




