<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html data-bs-theme="light" lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0, shrink-to-fit=no">
    <title>Home - Brand</title>
    <link rel="stylesheet" href="/assets/bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Montserrat:400,700&amp;display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Kaushan+Script&amp;display=swap">
    <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Abril+Fatface&amp;display=swap">
    <link rel="stylesheet" href="/assets/fonts/font-awesome.min.css">
    <link rel="stylesheet" href="/assets/css/bss-overrides.css">
</head>

<body id="page-top" data-bs-spy="scroll" data-bs-target="#mainNav" data-bs-offset="54">
<nav class="navbar navbar-expand-lg fixed-top bg-dark" id="mainNav">
    <div class="container"><a class="navbar-brand" href="/home.html">U2</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav ms-auto text-uppercase">
                <li class="nav-item"><a class="nav-link" href="home">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="statistic">Statistic</a></li>
                <li class="nav-item"><a class="nav-link" href="list-user">players</a></li>
                <c:if test="${not empty userId and role eq 'ADMIN'}">
                    <li class="nav-item"><a class="nav-link" href="create-quest">create quest</a></li>
                </c:if>

                <li class="nav-item"><a class="nav-link" href="contacts">Contacts</a></li>
                <li class="nav-item">
                    <div class="btn-toolbar">
                        <div class="btn-group position-relative d-inline mx-2 btn-group-vertical" role="group"
                             style="margin: -11px;padding: 0px;width: 110px;">
                            <c:if test="${empty userId}">
                                <a class="btn btn-primary" role="button" href="/log-in">LOG IN</a>
                                <a class="btn btn-primary" role="button" style="margin: 0px 0px 0px 0px;"
                                   href="/sign-up">SIGN UP</a>
                            </c:if>
                            <c:if test="${not empty userId}">
                                <a class="btn btn-primary" role="button" href="/private-profile-edit">PRIVATE</a>
                                <a class="btn btn-primary" role="button" style="margin: 0px 0px 0px 0px;"
                                   href="/exit">EXIT</a>
                            </c:if>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
    </div>
</nav>