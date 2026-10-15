<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp" %>
<%@ page import="com.javarush.khmelov.entity.State" %>
<c:set var="PLAY" value="<%= State.PLAY %>"/>
<c:set var="WIN" value="<%= State.WIN %>"/>
<c:set var="LOSS" value="<%= State.LOSS %>"/>

<a>СТАТИСТИКА</a>

<table class="table">
    <tr>
        <th>Пользователь</th>
        <th>В процессе</th>
        <th>Победа</th>
        <th>Поражение</th>
        <th>Всего</th>
    </tr>
    <c:forEach var="User" items="${requestScope.userStat.keySet()}">
        <tr>
            <td>${User.login}</td>
            <td>${requestScope.userStat.get(User).get(PLAY)}</td>
            <td>${requestScope.userStat.get(User).get(WIN)}</td>
            <td>${requestScope.userStat.get(User).get(LOSS)}</td>
            <td>${requestScope.totalStat.get(User)}</td>
        </tr>
    </c:forEach>
</table>
