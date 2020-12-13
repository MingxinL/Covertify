<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<title>Covertify</title>
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/Resources/CSS/mainPage.css"/>

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
						</c:url>
				
					<tr>
		
						<td> ${tempAlbum.name} </td>
						<td> <img src=${tempAlbum.image} width=25% /> </td>
						<td> <a href = "${addLink}">Add</a></td>
					</tr>
				
				</c:forEach>
	
	<script>
		/* var URLList="${AlbumCoverURLs}".slice(1, -1);
	
		URLList = URLList.split(", ");
		console.log("111");
		console.log(URLList);
		
		for(var i = 0; i < URLList.length; i++) {
			/* TODO: add infinite scroll */
		/* 	var div = document.createElement('div');
		    var img = document.createElement('img');
		    var a  = document.createElement('a');
		    a.href="http://localhost:8080/Covertify/add?"+"album="+URLList[i];
		    a.innerHTML = "add it";
		    console.log(${AlbumCoverURLs[i]});
		    img.src = URLList[i];
		    img.height = 200;
		    img.width = 200;
		   
		   
		    div.appendChild(img);
		    div.appendChild(a);
		    document.body.appendChild(div); */
		//} */
	</script>
</body>
</html>




