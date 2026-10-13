<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.javarush.quest.config.constant.Schema.Url" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--@elvariable id="user" type="com.javarush.quest.entity.User"--%>
<%--@elvariable id="userStats" type="com.javarush.quest.entity.UserStats"--%>

<%@ include file="parts/header.jsp" %>
<div class="container flex-grow-1 py-4">
    <h1>${user.login}</h1>
    <p class="lead"><span>Квестов закончено: </span>${userStats.playedGames}</p>
    <p class="lead"><span>Победы: </span>${userStats.wins}</p>
    <p class="lead"><span>Поражения: </span>${userStats.losses}</p>
    <a class="btn btn-secondary text-nowrap" role="button"
       href="<c:url value="${Url.HOME}"/>">На главную</a>
</div>
<%@include file="parts/footer.jsp" %>
