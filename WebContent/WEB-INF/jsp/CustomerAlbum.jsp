<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>   
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

		<c:forEach var="tempAlbum" items="${albumList}">
					
						<!-- construct an "delete" link with album id -->
						<c:url var="deleteLink" value="/album/delete">
							<c:param name= "albumId" value="${tempAlbum.id}" />
						</c:url>
				
					<tr>
		
						<td> ${tempAlbum.name} </td>
						<td> <img src=${tempAlbum.image} /> </td>
						    <audio controls="controls" src=${tempAlbum.preUrl} />
					           
						
						<td> <a href = "${deleteLink}"
								onclick = "if (!(confirm('Are you sure you want to delete this album'))) return false"
						>delete</a></td>
					</tr>
				
		</c:forEach>

</body>
</html>