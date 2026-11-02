<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>

<h1>Выбери гримуар</h1>

<c:forEach var="quest" items="${requestScope.quests}">
    <c:set var="score" value="${requestScope.stats[quest.id]}"/>
    <article class="card">
        <h2>${quest.name}</h2>
        <p>${quest.description}</p>
        <p style="font-size: 14px; color: #B3AA98;">
            <c:choose>
                <c:when test="${score.total == 0}">
                    Эту книгу ещё никто не открывал.
                </c:when>
                <c:otherwise>
                    Книга победила: <span style="color: #E58A78;">${score.losses}</span>
                    · Читатели вырвались: <span style="color: #D4B26A;">${score.wins}</span>
                </c:otherwise>
            </c:choose>
        </p>
        <a class="btn" href="play-game?questId=${quest.id}">Начать</a>
    </article>
</c:forEach>

<%@ include file="parts/footer.jsp" %>