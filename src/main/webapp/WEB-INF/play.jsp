<%@ page pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/parts/header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<p>${sessionScope.playerName}, игра номер ${sessionScope.gamesPlayed}.</p>

<c:if test="${not empty sessionScope.note}">
    <p>${sessionScope.note}</p>
</c:if>

<h2>${question.text}</h2>
<form method="post" action="${pageContext.request.contextPath}/play">
    <button type="submit" name="choice" value="A">A. ${question.optionA}</button>
    <button type="submit" name="choice" value="B">B. ${question.optionB}</button>
    <button type="submit" name="choice" value="C">C. ${question.optionC}</button>
</form>

<%@ include file="/WEB-INF/parts/footer.jsp" %>
