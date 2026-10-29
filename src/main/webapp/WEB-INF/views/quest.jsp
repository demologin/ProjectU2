<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Quest</title>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/textquest/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/textquest/assets/css/Navbar-Centered-Links-icons.css">

</head>

<body>
<nav class="navbar navbar-light navbar-expand-md">
    <div class="container-fluid"><a class="navbar-brand" href="/home-quest">Квесты</a>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-1"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-1">
            <ul class="navbar-nav">
                <li class="nav-item"><a class="nav-link active" href="/home-quest">Главная</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Профиль</a></li>
            </ul>
        </div>
    </div>
</nav>
<h1><c:out value="${quest.title}"/></h1>
<p><c:out value="${currentQuestion.text}"/></p>
<form action="${pageContext.request.contextPath}/quest-page" method="post">
    <input type="hidden" name="questId" value="${quest.id}">
<c:forEach var="answer" items="${currentQuestion.answers}">
<div class="form-check">
    <input class="form-check-input"
           type="radio"
           name="answerId"
           id="answer-${answer.id}"
           value="${answer.id}">
    <label class="form-check-label" for="answer-${answer.id}">
        <c:out value="${answer.text}"/>
    </label>
</div>
</c:forEach>
<button class="btn btn-primary" type="submit">Отправить</button>
</form>

<script src="${pageContext.request.contextPath}/textquest/assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>
