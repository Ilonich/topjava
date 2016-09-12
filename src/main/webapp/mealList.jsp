<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<!DOCTYPE html>
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<table width="100%" border="1" align="center">
    <tr bgcolor="#949494">
        <th>#</th>
        <th>LocalDateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Exceed</th>
        <th>Update/Delete</th>
    </tr>
    <c:forEach items="${requestScope.list}" var="meal">
        <tr bgcolor="${meal.exceed ? '#FF0000' : '#FFFFFF'}">
            <td>${meal.id}</td>
            <td>${f:format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
            <td>
                <form action="${pageContext.request.contextPath}/edit" method="get">
                    <button type="submit" name="id" value="${meal.id}">Edit</button>
               </form>
                <form action="${pageContext.request.contextPath}/meals" method="delete">
                    <button type="submit" name="id" value="${meal.id}">Delete</button>
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
<form action="${pageContext.request.contextPath}/meals" method="post">
    Description : <input type="text" name="description"/>
    <br />
    Calories : <input type="text" name="calories"/>
    <br />
    Date&Time : <input type="datetime" name="dob" />
    <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>
