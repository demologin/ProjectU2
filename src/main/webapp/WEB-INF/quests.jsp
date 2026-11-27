<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>
<body>
<c:forEach var="quest" items="${requestScope.quests}">
    <a href="play-quest?questId=${quest.id}">${quest.name}</a>
    <p> ${quest.description}</p>
</c:forEach>

</body>
