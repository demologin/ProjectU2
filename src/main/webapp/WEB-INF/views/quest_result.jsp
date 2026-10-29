<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Результат квеста</title>
  <link rel="stylesheet"
        href="${pageContext.request.contextPath}/textquest/assets/bootstrap/css/bootstrap.min.css">
</head>
<body>
<div class="container py-5 text-center">
  <c:choose>
    <c:when test="${questStatus == 'VICTORY'}">
      <h1 class="text-success mb-4">Поздравляем! Ты победил!</h1>
      <p class="lead">Ты успешно завершил квест «<c:out value="${quest.title}"/>».</p>
    </c:when>
    <c:when test="${questStatus == 'DEFEAT'}">
      <h1 class="text-danger mb-4">Ты проиграл!</h1>
      <p class="lead">На этот раз пройти квест «<c:out value="${quest.title}"/>» не удалось.</p>
    </c:when>
  </c:choose>
  <div class="d-flex justify-content-center gap-3 mt-4 flex-wrap">
    <a class="btn btn-outline-primary"
       href="${pageContext.request.contextPath}/home-quest">
      На главную
    </a>
    <a class="btn btn-primary"
       href="${pageContext.request.contextPath}/quest-page?questId=${quest.id}">
      Начать заново
    </a>
  </div>
</div>
</body>
</html>