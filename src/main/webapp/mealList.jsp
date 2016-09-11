<%@ page import="java.util.*,java.time.*" %>
<%@ page import="ru.javawebinar.topjava.model.MealWithExceed" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ page import="ru.javawebinar.topjava.repository.MealSimpleRepo" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Meal list</title>
</head>
<body>
<% List<MealWithExceed> list = MealsUtil.getFilteredWithExceeded(MealSimpleRepo.showAll(), LocalTime.of(0, 0), LocalTime.of(23, 59), 2000);
    request.setAttribute("list",list);
%>
<table width="100%" border="1" align="center">
    <tr bgcolor="#949494">
        <th>#</th>
        <th>LocalDateTime</th>
        <th>Description</th>
        <th>Calories</th>
        <th>Exceed</th>
    </tr>
    <c:forEach items="${list}" var="meal">
        <tr bgcolor="${meal.exceed ? '#FF0000' : '#FFFFFF'}">
            <td>#</td>
            <td>${f:format(meal.dateTime)}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>${meal.exceed}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
