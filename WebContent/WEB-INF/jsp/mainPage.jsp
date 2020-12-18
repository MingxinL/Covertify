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
<!-- bootstrap bundle -->
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
<style type="text/css">
	.searchRes, .adminAlbum {
		float: left;
	}
	.card-title {
		height: 30px;
	}
	#fname {
		width: 30%;
	}
	.clear {
		clear: both;
	}
</style>

</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <img alt="Covertify-Logo" src="Resources/Assets/Covertify.png" height=50px>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="album/readAlbums">Saved Album Covers</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">Link</a>
        </li>
      </ul>
    </div>
    <div>
	    <ul class="navbar-nav">
	    	<li class="nav-item">
	    		<p>${user.getDisplayName()}</p>
	    	</li>
	    	<li class="nav-item">
	    		<img src=${user.getImages()[0].getUrl()} height=50px/>
	    	</li>
	    	<li>
				<!-- Add a logout button -->
				<form:form action="${pageContext.request.contextPath}/logout" method="POST">
					<input type="submit" value="Logout" />
				</form:form>
	    	</li>
	    </ul>
    </div>
  </div>
</nav>


 	
 	<h5>customer role : ${sessionScope.role}</h5>

	
	
	<h1>choice of Covertify</h1>
	<c:forEach var="tempAlbum"  items="${sessionScope.AuthList}">
		<!-- construct an "add" link with album id -->
		<div class="adminAlbum">
     		<div class="card" style="width: 18rem;">
				<img src=${tempAlbum.image} class="card-img-top" alt="...">
				<div class="card-body">
					<h5 class="card-title">${tempAlbum.name}</h5>
					<audio controls="controls" src=${tempAlbum.preUrl} />
					Your browser does not support the
					<code>audio</code> element
				</div>
			</div>		
		</div>			
	</c:forEach>
	
	<div class="clear"></div>
	
	<h1>search section</h1>
    <form action="search">
		  <label for="search">search:</label><br>
		  <input class="form-control me-2" type="text" id="fname" name="search" value="Enter Album Keyword..."><br>
		  <input class="btn btn-outline-info" type="submit" value="Search">
		  <input type="hidden" name="code" id="code" value="" >
  	</form>


		<!-- loop over and print our customers -->
				<c:forEach var="tempAlbum" items="${albumList}">
				<!-- construct an "add" link with album id -->
						<c:url var="addLink" value="/album/add">
							<c:param name= "albumId" value="${tempAlbum.id}" />
							<c:param name= "albumName" value="${tempAlbum.name}" />
							<c:param name= "albumImage" value="${tempAlbum.image}" />
							<c:param name="albumPreUrl" value = "${tempAlbum.preUrl} " />
						</c:url>
				
					<div class="searchRes">
						<div class="card" style="width: 18rem;">
							  <img src=${tempAlbum.image} class="card-img-top" alt="...">
							  <div class="card-body">
							    <p class="card-title">${tempAlbum.name}</p>
							    <audio controls="controls" src=${tempAlbum.preUrl} />
					            Your browser does not support the
					            <code>audio</code> element
							  </div>
						</div>
						 <a href = "${addLink}" class="btn btn-primary">Add</a>
					</div>
				</c:forEach>
</body>
</html>




