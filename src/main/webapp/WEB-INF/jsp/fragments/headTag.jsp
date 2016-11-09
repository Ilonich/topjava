<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><fmt:message key="app.title"/></title>
    <base href="${pageContext.request.contextPath}/"/>

    <link rel="stylesheet" href="resources/css/style.css">
    <link rel="stylesheet" href="webjars/bootstrap/3.3.7-1/css/bootstrap.min.css">
    <link rel="shortcut icon" href="resources/images/icon-meal.png">
    <script type="text/javascript">
        var i18n = [];
        <c:forEach var='key' items='<%=new String[]{"meals.edit", "users.edit", "common.update","common.delete","common.deleted","common.saved","common.enabled","common.disabled","common.failed", "common.search"}%>'>
        i18n['${key}'] = '<fmt:message key="${key}"/>';
        var localeCode="${pageContext.response.locale}";
        </c:forEach>
    </script>
</head>
