<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="parts/header.jsp" %>
<%--@elvariable id="quest" type="com.javarush.quest.entity.Quest"--%>

<div class="container flex-grow-1 py-4">
    <h1>${quest.name}</h1>
    <p>${quest.text}</p>
    <c:url value='/scenes' var="sceneUrl">
        <c:param name="id" value="${quest.scenes[0].id}"/>
    </c:url>

    <form action="${sceneUrl}" method="post" style="max-width: 500px;">
        <div class="hstack gap-3"><label><input class="form-control" type="text" name="playerName"
                                                placeholder="Введите ваше имя..."></label>
            <button class="btn btn-primary text-nowrap" type="submit">Начать квест</button>
            <a class="btn btn-secondary text-nowrap" role="button" href="<c:url value='/'/>">Вернуться назад</a></div>
    </form>
</div>

<%@include file="parts/footer.jsp" %>
