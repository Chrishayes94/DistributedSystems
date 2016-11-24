<html>
	<head>
		<link type="text/css" rel="stylesheet" href="SocialNetwork.css"/>
	</head>

	<body>
		<%
			//TODO:: Implement check if user is logged in for posts and current status.
		%>
		<form action="/sign" method="post">
			<div><textarea name="content" rows="3" cols="60"></textarea></div>
			<div><input type="submit" value="Post Greeting"/></div>
			<input type="hidden" name="profileName" value=""/>
		</form>
		
		<form action="Profile.jsp" method="get">
			<div><input type="text" name="profileName" value=""/></div>
			<div><input type="submit" value="View Profile"/></div>
		</form>
	</body>
</html>