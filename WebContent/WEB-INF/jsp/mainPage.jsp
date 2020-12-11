<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
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
	
	<form action="search">
	  <label for="search">search:</label><br>
	  <input type="text" id="fname" name="search" value="Enter Album Keyword..."><br>
	  <input type="hidden" name="code" id="code" value="" >
	  <input type="submit" value="Submit">
	</form> 
	<p>${cookie['userName'].value}</p>
	<img src=${cookie['userImage'].value} />
	<script>
		var URLList="${AlbumCoverURLs}".slice(1, -1);
		URLList = URLList.split(", ");
		console.log("111");
		console.log(URLList);
		
		for(var i = 0; i < URLList.length; i++) {
			/* TODO: add infinite scroll */
		    var img = document.createElement('img');
		    console.log(${AlbumCoverURLs[i]});
		    img.src = URLList[i];
		    img.height = 200;
		    img.width = 200;
		    document.body.appendChild(img);
		}
	</script>
</body>
</html>




