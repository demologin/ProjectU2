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
<nav class="navbar navbar-light navbar-expand-md py-3">
    <div class="container"><a class="navbar-brand d-flex align-items-center"
                              href="${pageContext.request.contextPath}/home-quest"><span
            class="bs-icon-sm bs-icon-rounded bs-icon-primary d-flex justify-content-center align-items-center me-2 bs-icon"><svg
            xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor" viewBox="0 0 16 16"
            class="bi bi-bezier">
                        <path fill-rule="evenodd"
                              d="M0 10.5A1.5 1.5 0 0 1 1.5 9h1A1.5 1.5 0 0 1 4 10.5v1A1.5 1.5 0 0 1 2.5 13h-1A1.5 1.5 0 0 1 0 11.5v-1zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1zm10.5.5A1.5 1.5 0 0 1 13.5 9h1a1.5 1.5 0 0 1 1.5 1.5v1a1.5 1.5 0 0 1-1.5 1.5h-1a1.5 1.5 0 0 1-1.5-1.5v-1zm1.5-.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1zM6 4.5A1.5 1.5 0 0 1 7.5 3h1A1.5 1.5 0 0 1 10 4.5v1A1.5 1.5 0 0 1 8.5 7h-1A1.5 1.5 0 0 1 6 5.5v-1zM7.5 4a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5h-1z"></path>
                        <path d="M6 4.5H1.866a1 1 0 1 0 0 1h2.668A6.517 6.517 0 0 0 1.814 9H2.5c.123 0 .244.015.358.043a5.517 5.517 0 0 1 3.185-3.185A1.503 1.503 0 0 1 6 5.5v-1zm3.957 1.358A1.5 1.5 0 0 0 10 5.5v-1h4.134a1 1 0 1 1 0 1h-2.668a6.517 6.517 0 0 1 2.72 3.5H13.5c-.123 0-.243.015-.358.043a5.517 5.517 0 0 0-3.185-3.185z"></path>
                    </svg></span><span>Квесты</span></a>
        <button data-bs-toggle="collapse" class="navbar-toggler" data-bs-target="#navcol-3"><span
                class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
        <div class="collapse navbar-collapse" id="navcol-3">
            <ul class="navbar-nav mx-auto">
                <li class="nav-item">
                    <a class="nav-link active"
                       href="${pageContext.request.contextPath}/home-quest">Главная</a>
                </li>
                <c:if test="${not empty sessionScope.user}">
                    <li class="nav-item">
                        <a class="nav-link"
                           href="${pageContext.request.contextPath}/profile-page">Профиль</a>
                    </li>
                </c:if>
            </ul>
            <c:choose>
                <c:when test="${empty sessionScope.user}">
                    <a class="btn btn-outline-primary me-2"
                       href="${pageContext.request.contextPath}/login-page">Войти</a>

                    <a class="btn btn-primary"
                       href="${pageContext.request.contextPath}/registration-page">Регистрация</a>
                </c:when>

            <c:otherwise>
    <span class="me-3">
        Привет, <c:out value="${sessionScope.user.username}"/>!
    </span>

                <a class="btn btn-outline-primary me-2"
                   href="${pageContext.request.contextPath}/profile-page">Профиль</a>

                <form action="${pageContext.request.contextPath}/logout"
                      method="post" class="d-inline">
                    <button class="btn btn-primary" type="submit">Выйти</button>
                </form>
            </c:otherwise>
            </c:choose>
        </div>
    </div>
</nav>
<div class="container mt-4">
    <c:forEach var="quest" items="${quests}">
        <a class="text-decoration-none text-dark"
           href="${pageContext.request.contextPath}/quest-page?questId=${quest.id}">
            <div class="card mb-3">
                <div class="card-body">
                    <h5 class="card-title">
                        <c:out value="${quest.title}"/>
                    </h5>
                    <p class="card-text">
                        <c:out value="${quest.description}"/>
                    </p>
                </div>
            </div>
        </a>

    </c:forEach>

</div>
<script src="${pageContext.request.contextPath}/textquest/assets/bootstrap/js/bootstrap.min.js"></script>
</body>

</html>