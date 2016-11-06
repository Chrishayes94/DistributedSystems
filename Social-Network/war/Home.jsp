<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="resources/css/style.css" type="text/css">
<link rel="stylesheet" href="resources/css/newsui.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
  <header id="banner" class="body">
  	<h1>Testing Banner.</h1>
	  	<form action="/" method="post" action="#">
	  		<label for="email">Email Address</label>
	  		<input type="text" id="email" name="usename">
	  		
	  		<label for="password">Password</label>
	  		<input type="password" id="password" name="password">
	  		
	  		<input type="submit" name="post" value="Login"/>
	  	</form>
	  	<p>
	  	<% String result = (String) request.getAttribute("message");
			if (result != null) out.println(result); %>
	  	</p>
   </header>
 <br>
 <form action="/" method="post" action="#">
 	<textarea cols="25" rows="7" name="statusText"></textarea>
 	<input type="submit" name="post" value="Post">
 </form>
  <br>
 <% String[] card = (String[]) request.getAttribute("updates");
 	
 	if (card != null) {
	 	for (String s : card) {
	 		if (s == null) break;
	 		out.println(s);
	 	}
 	} %>
</body>
</html>