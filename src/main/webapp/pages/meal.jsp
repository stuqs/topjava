<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${not empty meal}"><jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/></c:if>
<html>
<head>
    <title>Meal list</title>
    <link href="static/css/style.css" rel="stylesheet"/>
    <link href="static/img/favicon.ico" rel="icon" type="image/ico"/>
</head>
<body>
<h2 class="leftAlign"><a href="/topjava/">Home</a></h2>
<h2>Meal list</h2>
<form method="post" action="meals" name="addMeal">
    <table class="simple-little-table" cellspacing='0'>
        <tr>
            <th>Дата и Время</th>
            <th>Описание</th>
            <th>Калории</th>
        </tr>
        <tr>
            <c:set var="dateTime" value="${fn:contains(header['User-Agent'],'Chrome') ? meal.dateTime :
            fn:replace(meal.dateTime, 'T', ' ')}"/>
            <th><input type="datetime-local" name="date" value="${dateTime}" title=""/></th>
            <th><input size="50" type="text" name="description" value="${meal.description}" title=""/></th>
            <th><input type="text" name="calories" value="${meal.calories}" title=""/></th>
            <input hidden type="text" name="id" value="${meal.id}" title=""/>
        </tr>
        <tr>
            <th colspan="3"><input type="submit" value="Submit"/></th>
        </tr>
    </table>
</form>
</body>
</html>