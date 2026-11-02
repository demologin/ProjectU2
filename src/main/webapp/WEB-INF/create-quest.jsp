<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>

<h1>Новый квест</h1>
<p style="color: #B3AA98;">Сначала название и описание. Вопросы и ответы добавишь на следующем шаге.</p>

<c:if test="${param.error == 'empty'}">
    <p style="color: #E58A78;">Название не может быть пустым.</p>
</c:if>

<form method="post" action="create-quest">
    <p><label for="name">Название</label><input id="name" name="name" type="text" required></p>
    <p>
        <label for="description">Описание</label>
        <textarea id="description" name="description" rows="4"
                  style="width: 100%; margin-top: 6px; padding: 12px; box-sizing: border-box;
                         background: #14111A; border: 1px solid #3A3346; border-radius: 6px;
                         color: #EDE6D6; font: 15px Inter, sans-serif;"></textarea>
    </p>
    <button type="submit">Создать и перейти к вопросам</button>
</form>

<%@ include file="parts/footer.jsp" %>