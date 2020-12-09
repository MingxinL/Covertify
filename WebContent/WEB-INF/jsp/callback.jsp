<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
 <%
			   Cookie token = new Cookie("Spotifytoken", request.getParameter("code"));
	   		   response.addCookie(token);
%> 

<!DOCTYPE html>
<html>
<head>
<title>Covertify</title>
<style type="text/css">
body {
	background-image: url('https://cdn.crunchify.com/bg.png');
}
</style>
</head>
<body>
	<h1>Logged in! </h1>
	
	<form action="search">
	  <label for="search">search:</label><br>
	  <input type="text" id="fname" name="fname" value="..."><br>
	  <input type="submit" value="Submit">
	   
	</form> 
</body>
</html>