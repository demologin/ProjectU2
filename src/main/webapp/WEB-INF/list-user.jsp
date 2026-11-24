<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@include file="parts/header.jsp"%>


<section class="d-flex vh-100 align-items-center" id="services">
    <div class="container position-relative" style="display: block;">
        <h2>Список пользователей:</h2>
        <c:if test="${not empty users}">
            <ul>
                <c:forEach var="user" items="${users}">
                    <li>
                        <a href="user-profile?id=${user.id}">
                                ${fn:escapeXml(user.login)}
                        </a>
                    </li>
                </c:forEach>
            </ul>
        </c:if>

        <c:if test="${empty users}">
            <p>Пользователи не найдены.</p>
        </c:if>
    </div>
</section>

<%@include file="parts/footer.jsp"%>
