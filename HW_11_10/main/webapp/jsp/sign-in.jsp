<%@ page import="java.util.List" %>
<%@ page import="org.example.FourthOctober.dto.FieldErrorDto" %><%--
<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 10.10.2025
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="java.util.List" %>
<%@ page import="org.example.FourthOctober.dto.FieldErrorDto" %><%--
<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 10.10.2025
  Time: 12:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
        }
        .login-container {
            background: white;
            padding: 40px;
            border-radius: 20px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
            font-size: 2em;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 15px;
            margin: 10px 0;
            border: 2px solid #e1e5ee;
            border-radius: 10px;
            font-size: 16px;
            box-sizing: border-box;
            transition: border-color 0.3s;
        }
        input[type="text"]:focus, input[type="password"]:focus {
            border-color: #667eea;
            outline: none;
        }
        input[type="submit"] {
            width: 100%;
            background: linear-gradient(45deg, #667eea, #764ba2);
            color: white;
            border: none;
            padding: 15px;
            border-radius: 10px;
            cursor: pointer;
            font-size: 18px;
            margin-top: 20px;
            transition: transform 0.2s;
        }
        input[type="submit"]:hover {
            transform: translateY(-2px);
            box-shadow: 0 6px 20px rgba(102,126,234,0.4);
        }
        .error {
            color: #ff4757;
            font-size: 14px;
            margin: 5px 0;
            padding: 8px;
            background: #ffe6e6;
            border-radius: 5px;
            border-left: 4px solid #ff4757;
        }
    </style>
</head>
<body>
<div class="login-container">
    <h1>Sign In</h1>

    <form action="/sign-in" method="post">
        <input type="text" name="email" placeholder="Enter your email"/><br>
        <input type="password" name="password" placeholder="Enter your password"/><br>
        <input type="submit" value="Sign In!"/>
    </form>

    <% if(request.getAttribute("errors") != null) {
        for(FieldErrorDto errorDto : (List<FieldErrorDto>) request.getAttribute("errors")) { %>
    <div class="error">
        Field <%=errorDto.getField()%>: <%=errorDto.getMessage()%>
    </div>
    <% }
    } %>
</div>
</body>
</html>