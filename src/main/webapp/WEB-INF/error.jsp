<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.javarush.quest.config.constant.Schema.Url" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html data-bs-theme="dark" lang="ru">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Quest App</title>
    <link rel="icon" type="image/png" sizes="32x32" href="<c:url value="/assets/img/favicon.png"/>">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/assets/css/styles.css"/>">
</head>

<body class="d-flex min-vh-100 flex-column">
<div class="container text-nowrap d-flex flex-grow-1 justify-content-center align-items-center">
    <div class="row justify-content-center">
        <div class="col-md-6">
            <h1 class="display-1 fw-bold text-danger">${pageContext.response.status}</h1>
            <h2 class="mb-3">${requestScope.errorMessage}</h2>
            <a class="btn btn-primary px-4 py-2" role="button" href="<c:url value="${Url.HOME}"/>">На главную</a>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
