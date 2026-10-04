<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Оффер за 4 шага</title>
    <style>
        body {
            margin: 0;
            font-family: Georgia, "Times New Roman", serif;
            background: #f4f1ea;
            color: #1f1a17;
        }

        header, main, footer {
            width: min(720px, calc(100% - 32px));
            margin-left: auto;
            margin-right: auto;
        }

        header {
            padding: 28px 0 8px;
        }

        header a {
            color: inherit;
            text-decoration: none;
        }

        header p {
            margin: 0;
            color: #5c534c;
        }

        main {
            padding: 8px 0 32px;
        }

        footer {
            padding: 16px 0 28px;
            color: #5c534c;
            border-top: 1px solid #ddd6cc;
        }

        button {
            display: block;
            margin: 8px 0;
            text-align: left;
        }
    </style>
</head>
<body>
<header>
    <h1><a href="${pageContext.request.contextPath}/">Оффер за 4 шага</a></h1>
    <p>Стажёр ищет первую работу Java-разработчиком</p>
</header>
<main>