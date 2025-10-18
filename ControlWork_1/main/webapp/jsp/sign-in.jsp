<%@ page import="java.util.List" %>
<%@ page import="org.itis.Astrology.Dto.FieldErrorDto" %>
<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 16.10.2025
  Time: 22:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Title</title>
</head>
<body>

<h1>Sign In page</h1>

<form action="/sign-in" method="post">
  Email: <input type="text" name="email"/><br>
  Password: <input type="text" name="password"/><br>
  <input type="submit" value="Sign in!"/>
</form>

<% if(request.getAttribute("errors") != null) {
  for(FieldErrorDto errorDto : (List<FieldErrorDto>) request.getAttribute("errors")) { %>
<%--<span style="color: red;">Field <%=errorDto.getField()%>, error <%=errorDto.getMessage()%> <br></span>--%>
<% }
} %>

</body>
</html>
