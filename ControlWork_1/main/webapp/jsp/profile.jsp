<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 16.10.2025
  Time: 22:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>

<h1>Profile page</h1>
<h2>Your account is <%=request.getAttribute("email")%></h2>

<a href="/">MAIN</a><br>
<a href="/logout">LOGOUT</a><br>

</body>
</html>
