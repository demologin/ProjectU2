<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="parts/header.jsp" %>

<div class="container flex-grow-1 py-4">
    <h1>${sessionScope.user.login}</h1>
    <p>Последний квест: Spaceship</p>
    <a class="btn btn-secondary text-nowrap" role="button"
       href="<c:url value='/'/>">Вернуться на главную</a>
</div>

<%@include file="parts/footer.jsp" %>
