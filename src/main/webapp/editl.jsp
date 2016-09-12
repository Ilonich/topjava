<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://example.com/functions" prefix="f" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<form action="${pageContext.request.contextPath}/edit" method="post">
    ID <input type="text" readonly="readonly" name="id" value="${requestScope.meal.id}"/>
    <br />
    Description : <input type="text" name="description" value="${requestScope.meal.description}"/>
    <br />
    Calories : <input type="text" name="calories" value="${requestScope.meal.calories}" />
    <br />
    Date&Time : <input type="datetime" name="dob" value="${f:format(requestScope.meal.dateTime)}" />
    <br />
    <input type="submit" value="Submit" />
</form>
</body>
</html>
