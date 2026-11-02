<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>

<p style="margin: 0 0 8px; font-size: 13px; letter-spacing: 0.15em; text-transform: uppercase; color: #B3AA98;">
    Интерактивные квесты
</p>
<h1>Гримуар</h1>
<p style="font-size: 18px; color: #B3AA98;">
    Собрание книг, которые не стоило открывать. В каждой — своя история, свои ловушки
    и только один верный путь к финалу. Выбери книгу и попробуй выбраться.
</p>

<c:if test="${not empty sessionScope.user}">
    <p>Рады видеть, ${sessionScope.user.login}.</p>
</c:if>

<p style="margin-top: 32px;">
    <a class="btn" href="list-quest">Выбрать квест</a>
    <c:if test="${empty sessionScope.user}">
        <a class="btn btn-outline" href="signup" style="margin-left: 12px;">Регистрация</a>
    </c:if>
</p>

<%@ include file="parts/footer.jsp" %>