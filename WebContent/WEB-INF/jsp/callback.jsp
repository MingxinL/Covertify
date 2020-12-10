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
	Logged in!
	
	<form action="search">
	  <label for="search">search:</label><br>
	  <input type="text" id="fname" name="search" value="Enter Album Keyword..."><br>
	  <input type="submit" value="Submit">
	</form> 
	<p>Search Result: ${AlbumCoverURLs}<p/>
	
<!-- 	<table id="coverTable" border="1">
		<tr></tr>
	</table> -->

	<script>
		var URLList="${AlbumCoverURLs}".slice(1, -1);
		URLList = URLList.split(", ");
		console.log("111");
		console.log(URLList);
		
		for(var i = 0; i < URLList.length; i++) {
			/* TODO: add infinite scroll */
/* 		    var row=document.getElementById("coverTable").rows[0];
		    var x=row.insertCell(-1); */
		    
		    var img = document.createElement('img');
		    console.log(${AlbumCoverURLs[i]});
		    img.src = URLList[i];
		    img.height = 200;
		    img.width = 200;
		    document.body.appendChild(img);
/* 		    x.appendChild(img); */
		}
	</script>
</body>
</html>