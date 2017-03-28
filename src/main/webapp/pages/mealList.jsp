<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${not empty meals}"><jsp:useBean id="meals" scope="request"
                                             type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/></c:if>
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
    <tr><th colspan="5">
        <a href="meals?action=add">Добавить запись</a>
    </th></tr>
    <tr>
        <th>Дата и Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th colspan=2>Действия</th>
    </tr>
    <c:forEach items="${meals}" var="meal">
        <c:set var="styleExceed" value="${meal.exceed ? 'exceeded' : 'normal'}"/>
        <tr class="${styleExceed}">
            <td>${fn:replace(meal.dateTime, 'T', ' ')}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=edit&id=${meal.id}">Редактировать</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
