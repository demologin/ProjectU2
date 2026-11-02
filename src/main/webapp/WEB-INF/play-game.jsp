<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>

<c:choose>
    <c:when test="${empty requestScope.question}">
        <h1>Игра не начата</h1>
        <p>Выбери квест, чтобы начать.</p>
        <a class="btn" href="list-quest">К списку квестов</a>
    </c:when>
    <c:otherwise>

        <c:if test="${not empty requestScope.question.image}">
            <div style="height: 260px; margin-bottom: 28px; border-radius: 12px; background-color: #1E1A26;
                    background-image: url('images/${requestScope.question.image}');
                    background-size: cover; background-position: center;"></div>
        </c:if>

        <h1>${requestScope.question.title}</h1>

        <div class="card">
            <p>${requestScope.question.text}</p>
        </div>

        <c:choose>
            <c:when test="${requestScope.question.gameState == 'PLAY'}">
                <c:forEach var="answer" items="${requestScope.question.answers}" varStatus="status">
                    <form method="post" action="play-game"
                          style="background: none; border: 0; padding: 0; max-width: none; margin: 0 0 12px;">
                        <input type="hidden" name="answer" value="${status.index}">
                        <button type="submit" class="btn-outline" style="width: 100%; text-align: left;">
                                ${answer.text}
                        </button>
                    </form>
                </c:forEach>
            </c:when>
            <c:when test="${requestScope.question.gameState == 'WIN'}">
                <div style="text-align: center; padding: 40px 24px; border: 1px solid #D4B26A; border-radius: 12px;
                            background: radial-gradient(circle at center, rgba(212,178,106,0.20), transparent 70%);">
                    <p style="margin: 0; font-size: 13px; letter-spacing: 0.15em; text-transform: uppercase; color: #D4B26A;">Победа</p>
                    <h2 style="font-size: 44px; margin: 8px 0 24px; color: #D4B26A;">Ты свободна</h2>
                    <a class="btn" href="list-quest">К списку квестов</a>
                </div>
            </c:when>
            <c:otherwise>
                <div style="text-align: center; padding: 40px 24px; border: 1px solid #E58A78; border-radius: 12px;
                            background: radial-gradient(circle at center, rgba(229,138,120,0.16), transparent 70%);">
                    <p style="margin: 0; font-size: 13px; letter-spacing: 0.15em; text-transform: uppercase; color: #E58A78;">Поражение</p>
                    <h2 style="font-size: 44px; margin: 8px 0 24px; color: #E58A78;">Гримуар победил</h2>
                    <a class="btn btn-outline" href="list-quest">Попробовать снова</a>
                </div>
            </c:otherwise>
        </c:choose>

    </c:otherwise>
</c:choose>

<%@ include file="parts/footer.jsp" %>