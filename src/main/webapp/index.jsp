<%@ include file="/WEB-INF/parts/header.jsp" %>
<%@ page pageEncoding="UTF-8" %>

<p>
    Стажёр хочет найти первую работу Java-разработчиком.
    Впереди резюме, отклик, тестовое и собеседование.
    На каждом шаге один ответ ведёт дальше, два других заканчивают поиск.
</p>
<form method="post" action="${pageContext.request.contextPath}/start">
    <label>
        Как тебя зовут?
        <input type="text" name="name" required>
    </label>
    <button type="submit">Начать</button>
</form>
<%@ include file="/WEB-INF/parts/footer.jsp" %>