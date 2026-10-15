<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html xmlns:c="http://www.w3.org/1999/XSL/Transform">
<head>
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
            crossorigin="anonymous"></script>

    <c:if test="${sessionScope.user == null}">
        <a href="login">Войти</a>
        <a> в систему</a>
        <br/>
    </c:if>

    <c:if test="${sessionScope.user != null}">
        <a>Вы вошли как ${sessionScope.user.login}</a>
        <form action="logout" method="post">
            <button type="submit" id="logout" class="btn btn-warning">Выйти</button>
        </form>
    <a href="list-user">Пользователи</a>
    <a href="quests">Квесты</a>
    <a href="statistics">Статистика</a>
    <br/>
    <br/>
    </c:if>
</head>