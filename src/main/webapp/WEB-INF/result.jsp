<%@ include file="/WEB-INF/parts/header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page pageEncoding="UTF-8" %>

<h2>
    <c:choose>
        <c:when test="${sessionScope.won}">Победа</c:when>
        <c:otherwise>Поражение</c:otherwise>
    </c:choose>
</h2>
<p>${sessionScope.ending}</p>
<p>${sessionScope.playerName}, сыграно игр: ${sessionScope.gamesPlayed}.</p>
<form method="post" action="${pageContext.request.contextPath}/restart">
    <button type="submit">Начать заново</button>
</form>

<%@ include file="/WEB-INF/parts/footer.jsp" %>
