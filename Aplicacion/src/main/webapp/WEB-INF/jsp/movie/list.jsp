<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Recomendación de películas</title>
</head>
<body>
	<h3>Recomendación de películas</h3>

	<div id="widget">
		<table border="1">
			<tr>
				<th></th>
				<th>Name</th>
			</tr>
			<c:forEach var="movie" items="${movieList}" varStatus="status">
					<c:choose>
						<c:when test="${status.last}">
							<tr bgcolor="#00FFFF">
						</c:when>
						
						<c:otherwise>
							<c:choose>	
								<c:when test="${status.index % 2 == 0}">
									<tr bgcolor="#00FFFF">
								</c:when>
								
								<c:otherwise>
									<tr>
								</c:otherwise>
							</c:choose>
						</c:otherwise>
					</c:choose>
					<td>${status.index + 1}</td>
					<td>${movie.name}</td>
				</tr>
			</c:forEach>
		</table>
	</div>
</body>
</html>
