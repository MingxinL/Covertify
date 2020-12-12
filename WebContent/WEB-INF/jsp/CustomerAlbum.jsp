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
				
						<!-- construct an "add" link with album id -->
						<c:url var="addLink" value="/album/add">
							<c:param name= "albumId" value="${tempAlbum.id}" />
							<c:param name= "albumName" value="${tempAlbum.name}" />
							<c:param name= "albumImage" value="${tempAlbum.image}" />
						</c:url>
				
					<tr>
		
						<td> ${tempAlbum.name} </td>
						<td> <img src=${tempAlbum.image} /> </td>
						<td> <a href = "${addLink}">Add</a></td>
					</tr>
				
		</c:forEach>

</body>
</html>