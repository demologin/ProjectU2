<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="parts/header.jsp"%>

<section id="services" style="padding-top: 120px;padding-bottom: 120px;">
        <div class="container">
            <div class="row">
                <div class="col col-lg-12 text-center d-block" style="color: var(--bs-primary);font-family: 'Kaushan Script', serif;">
                    <h1 class="section-heading" style="font-family: Montserrat, sans-serif;">Статистика</h1><
                </div>
            </div>
            <c:if test="${not empty topUsers}">
<%--                <ul>--%>
                <c:forEach var="user" items="${topUsers}" varStatus="status">
                    <div class="row text-center align-items-center mb-3"> <!-- Заменили mb-7 на mb-3 и добавили вертикальное выравнивание -->
                        <div class="col text-end"> <!-- Выравнивание текста вправо для первой колонки -->
                            <span>${status.count}</span>
                        </div>
                        <div class="col">
                            <a href="user-profile?id=${user.id}">
                                    ${fn:escapeXml(user.login)}
                            </a>
                        </div>
                        <div class="col text-start"> <!-- Выравнивание текста влево для третьей колонки -->
                            <span>${user.score}</span>
                        </div>
                    </div>
                </c:forEach>

                <%--                </ul>--%>
            </c:if>

            <c:if test="${empty topUsers}">
                <p>Статистика не найдена.</p>
            </c:if>

        </div>
    </section>
    <%@include file="parts/footer.jsp"%>
