<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Java Enterprise (Topjava)</title>
</head>
<body>
<h2>Проект "<a href="https://github.com/JavaWebinar/topjava08" target="_blank">Java Enterprise (Topjava)"</a></h2>
<hr>
<form method="post" action="users">
    <dl>
        <dt>Email:</dt>
        <dd><input type="text" name="email"></dd>
    </dl>
    <dl>
        <dt>Password:</dt>
        <dd><input type="text" name="password"></dd>
    </dl>
    <button type="submit">Log in</button>
</form>
</body>
</html>
