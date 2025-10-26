<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 10.10.2025
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%--
  Created by IntelliJ IDEA.
  User: Honor
  Date: 10.10.2025
  Time: 12:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Cookie Example</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 600px;
            margin: 50px auto;
            padding: 20px;
            transition: background-color 0.5s ease;
        }
        .container {
            background: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        input[type="text"] {
            width: 70%;
            padding: 12px;
            border: 2px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            margin-right: 10px;
        }
        input[type="submit"] {
            background: linear-gradient(45deg, #FF6B6B, #FF8E53);
            color: white;
            border: none;
            padding: 12px 25px;
            border-radius: 8px;
            cursor: pointer;
            font-size: 16px;
            transition: transform 0.2s;
        }
        input[type="submit"]:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(255,107,107,0.3);
        }
    </style>
</head>
<body style="background-color: <%= request.getAttribute("color")%>">
<div class="container">
    <h1> Cookie Color Changer</h1>

    <form action="/cookie" method="post">
        <input type="text" name="color" placeholder="Enter color name or hex (e.g., #FF5733)"/>
        <input type="submit" value="Save Color">
    </form>
</div>
</body>
</html>
