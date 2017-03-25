<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:useBean id="meals" scope="request" type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
<html>
<head>
    <title>Meal list</title>
    <link href="static/css/style.css" rel="stylesheet"/>
    <link href="static/img/favicon.ico" rel="icon" type="image/ico"/>
</head>
<body>
<h2 class="leftAlign"><a href="/topjava/">Home</a></h2>
<h2>Meal list</h2>

<table class="simple-little-table" cellspacing='0'>
    <tr>
        <th>Дата и Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <c:set var="styleExceed" value="${meal.exceed ? 'exceeded' : 'normal'}"/>
        <tr class="${styleExceed}">
            <c:set var="cleanedDateTime" value="${fn:replace(meal.dateTime, 'T', ' ')}"/>
            <td>${cleanedDateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
