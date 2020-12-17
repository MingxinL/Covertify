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

<!-- Begin Mailchimp Signup Form -->
<link href="//cdn-images.mailchimp.com/embedcode/slim-10_7.css" rel="stylesheet" type="text/css">
<style type="text/css">
	#mc_embed_signup{background:#fff; clear:left; font:14px Helvetica,Arial,sans-serif; }
	/* Add your own Mailchimp form style overrides in your site stylesheet or in this style block.
	   We recommend moving this block and the preceding CSS link to the HEAD of your HTML file. */
</style>
</head>

<body>
	Logged in!
	
	
	
	
	

	<div id="mc_embed_signup">
	<form action="https://gmail.us4.list-manage.com/subscribe/post?u=0b955d0e741373c59b435d1ff&amp;id=29ada2d9ee" method="post" id="mc-embedded-subscribe-form" name="mc-embedded-subscribe-form" class="validate" target="_blank" novalidate>
	    <div id="mc_embed_signup_scroll">
		<label for="mce-EMAIL">Subscribe</label>
		<input type="email" value="" name="EMAIL" class="email" id="mce-EMAIL" placeholder="email address" required>
	    <!-- real people should not fill this in and expect good things - do not remove this or risk form bot signups-->
	    <div style="position: absolute; left: -5000px;" aria-hidden="true"><input type="text" name="b_0b955d0e741373c59b435d1ff_29ada2d9ee" tabindex="-1" value=""></div>
	    <div class="clear"><input type="submit" value="Subscribe" name="subscribe" id="mc-embedded-subscribe" class="button"></div>
	    </div>
	</form>
	</div>
	
	<!--End mc_embed_signup-->
	
	
	
	
	<img alt="Covertify-Logo" src="Resources/Assets/Covertify.png" width=30%>
	<a href="album/readAlbums">read albums before</a>
	<c:if test="${role.equals('auth')}"> 
  		<input type="submit" value="Add popular albums" />
 	</c:if> 
 	<h1>customer role : ${role}</h1>
	<!-- Add a logout button -->
	<form:form action="${pageContext.request.contextPath}/logout" 
			   method="POST">
	
		<input type="submit" value="Logout" />
	
	
	</form:form>
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




