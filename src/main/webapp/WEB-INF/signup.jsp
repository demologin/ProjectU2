<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>

<h1>Регистрация</h1>

<c:if test="${param.error == 'taken'}">
    <p>Такой логин уже занят.</p>
</c:if>
<c:if test="${param.error == 'empty'}">
    <p>Логин и пароль не могут быть пустыми.</p>
</c:if>

<form method="post" action="signup">
    <p><label for="login">Логин</label><input id="login" name="login" type="text" required></p>
    <p><label for="password">Пароль</label><input id="password" name="password" type="password" required></p>
    <button type="submit">Создать аккаунт</button>
</form>

<p>Уже есть аккаунт? <a href="login">Войти</a></p>

<%@ include file="parts/footer.jsp" %>