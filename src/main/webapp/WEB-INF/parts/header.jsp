<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html data-bs-theme="dark" lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Quest App</title>
    <link rel="icon" type="image/png" sizes="32x32" href="<c:url value='/assets/img/favicon.png'/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value='/assets/css/styles.css'/>">
</head>

<body class="d-flex min-vh-100 flex-column">
<header class="sticky-top">
    <nav class="navbar navbar-expand-md bg-body-tertiary bg-gradient py-3">
        <div class="container"><a class="navbar-brand d-flex align-items-center" href="<c:url value='/'/>"><span
                class="bs-icon-sm bs-icon-rounded bs-icon-primary d-flex justify-content-center align-items-center me-2 bs-icon"><svg
                class="bi bi-star-fill" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em" fill="currentColor"
                viewBox="0 0 16 16">
                            <path d="M3.612 15.443c-.386.198-.824-.149-.746-.592l.83-4.73L.173 6.765c-.329-.314-.158-.888.283-.95l4.898-.696L7.538.792c.197-.39.73-.39.927 0l2.184 4.327 4.898.696c.441.062.612.636.282.95l-3.522 3.356.83 4.73c.078.443-.36.79-.746.592L8 13.187l-4.389 2.256z"></path>
                        </svg></span><span>Quest App</span></a>
            <button class="navbar-toggler" data-bs-toggle="collapse" data-bs-target="#navcol-2"><span
                    class="visually-hidden">Toggle navigation</span><span class="navbar-toggler-icon"></span></button>
            <div class="collapse navbar-collapse" id="navcol-2">
                <c:choose>
                    <c:when test="${empty sessionScope.user}">
                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item"><a class="nav-link" href="#">Создать аккаунт</a></li>
                        </ul>
                        <a class="btn btn-primary ms-md-2" role="button" href="<c:url value='/login'/>">Войти</a>
                    </c:when>
                    <c:otherwise>
                        <c:url value='/user' var="profileUrl">
                            <c:param name="id" value="${sessionScope.user.id}"/>
                        </c:url>

                        <ul class="navbar-nav ms-auto">
                            <li class="nav-item"><a class="nav-link" href="#">Создать квест</a></li>
                            <li class="nav-item"><a class="nav-link" href="${profileUrl}">Профиль</a></li>
                        </ul>
                        <a class="btn btn-danger ms-md-2" role="button" href="<c:url value='/logout'/>">Выйти</a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </nav>
</header>
