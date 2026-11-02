<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>

<h1>Профиль</h1>

<c:choose>
    <c:when test="${not empty sessionScope.user}">
        <p>Логин: ${sessionScope.user.login}</p>

        <div style="display: grid; grid-template-columns: repeat(3, minmax(0, 1fr)); gap: 16px; margin-top: 24px;">
            <div class="card" style="margin: 0;">
                <p style="margin: 0; font-size: 14px; color: #B3AA98;">Игр сыграно</p>
                <p style="margin: 0; font-family: 'Cormorant Garamond', Georgia, serif; font-size: 44px;">
                        ${requestScope.stats.total}
                </p>
            </div>
            <div class="card" style="margin: 0;">
                <p style="margin: 0; font-size: 14px; color: #B3AA98;">Побед</p>
                <p style="margin: 0; font-family: 'Cormorant Garamond', Georgia, serif; font-size: 44px; color: #D4B26A;">
                        ${requestScope.stats.wins}
                </p>
            </div>
            <div class="card" style="margin: 0;">
                <p style="margin: 0; font-size: 14px; color: #B3AA98;">Поражений</p>
                <p style="margin: 0; font-family: 'Cormorant Garamond', Georgia, serif; font-size: 44px; color: #E58A78;">
                        ${requestScope.stats.losses}
                </p>
            </div>
        </div>
    </c:when>
    <c:otherwise>
        <p>Чтобы увидеть профиль, нужно <a href="login">войти</a>.</p>
    </c:otherwise>
</c:choose>

<%@ include file="parts/footer.jsp" %>