<%@ page pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Проклятый гримуар</title>
    <link rel="stylesheet"
          href="https://fonts.googleapis.com/css2?family=Cormorant+Garamond:wght@500;600&family=Inter:wght@400;500&display=swap">
    <style>
        body { margin: 0; background: #14111A; color: #EDE6D6;
            font-family: Inter, system-ui, sans-serif; line-height: 1.6; }
        a { color: #D4B26A; }
        h1, h2 { font-family: 'Cormorant Garamond', Georgia, serif; font-weight: 600; margin: 0 0 16px; }
        h1 { font-size: 48px; }
        .site-header { display: flex; flex-wrap: wrap; align-items: center; justify-content: space-between;
            gap: 16px; padding: 20px 48px; border-bottom: 1px solid #3A3346; }
        .brand { font-family: 'Cormorant Garamond', Georgia, serif; font-size: 28px; font-weight: 600;
            color: #D4B26A; text-decoration: none; }
        .site-nav { display: flex; flex-wrap: wrap; align-items: center; gap: 20px; }
        .site-nav a { color: #EDE6D6; text-decoration: none; }
        .site-nav a:hover { color: #D4B26A; }
        main { max-width: 720px; margin: 0 auto; padding: 48px 24px; }
        main form { max-width: 420px; background: #1E1A26; border: 1px solid #3A3346;
            border-radius: 12px; padding: 32px; box-sizing: border-box; }
        label { font-size: 14px; }
        input { width: 100%; height: 44px; margin-top: 6px; padding: 0 12px; box-sizing: border-box;
            background: #14111A; border: 1px solid #3A3346; border-radius: 6px;
            color: #EDE6D6; font-size: 15px; }
        button, .btn { display: inline-block; padding: 12px 24px; border: 1px solid #D4B26A; border-radius: 6px;
            background: #D4B26A; color: #14111A; font: 500 15px Inter, sans-serif;
            text-decoration: none; cursor: pointer; }
        .btn-outline, .site-header button { background: transparent; color: #D4B26A; }
        .site-header form { display: inline; }
        .card { background: #1E1A26; border: 1px solid #3A3346; border-radius: 12px;
            padding: 28px; margin-bottom: 20px; }
    </style>
</head>
<body>
<header class="site-header">
    <a class="brand" href="home">Гримуар</a>
    <nav class="site-nav">
        <a href="list-quest">Квесты</a>
        <c:choose>
            <c:when test="${not empty sessionScope.user}">
                <a href="create-quest">Создать квест</a>
                <a href="profile">Профиль</a>
                <form method="post" action="logout">
                    <button type="submit">Выйти</button>
                </form>
            </c:when>
            <c:otherwise>
                <a class="btn btn-outline" href="login">Войти</a>
                <a class="btn" href="signup">Регистрация</a>
            </c:otherwise>
        </c:choose>
    </nav>
</header>
<main>