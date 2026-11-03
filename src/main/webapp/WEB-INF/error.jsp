<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>

<c:set var="code" value="${requestScope['jakarta.servlet.error.status_code']}"/>

<div style="text-align: center; padding: 48px 24px;">
    <p style="margin: 0; font-size: 72px; font-family: 'Cormorant Garamond', Georgia, serif; color: #D4B26A;">
        ${code}
    </p>
    <c:choose>
        <c:when test="${code == 404}">
            <h1>Страница не найдена</h1>
            <p style="color: #B3AA98;">Такой страницы нет в гримуаре. Возможно, её вырвали.</p>
        </c:when>
        <c:otherwise>
            <h1>Что-то пошло не так</h1>
            <p style="color: #B3AA98;">Чернила расплылись. Попробуй вернуться и начать заново.</p>
        </c:otherwise>
    </c:choose>
    <p style="margin-top: 32px;">
        <a class="btn" href="home">На главную</a>
    </p>
</div>

<%@ include file="parts/footer.jsp" %>