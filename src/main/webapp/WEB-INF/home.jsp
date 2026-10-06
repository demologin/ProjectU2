<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="parts/header.jsp" %>

<div class="container flex-grow-1 py-4">
    <div class="row mb-5">
        <div class="col-md-8 col-xl-6 text-center mx-auto">
            <h2>Список квестов</h2>
            <p>Выберите среди следующего списка доступных квестов:</p>
        </div>
    </div>
    <div class="row gy-4 row-cols-1 row-cols-md-2 row-cols-xl-3">
        <c:forEach var="quest" items="${requestScope.quests}">
            <c:url value="/quests" var="questUrl">
                <c:param name="id" value="${quest.id}"/>
            </c:url>
            <div class="col">
                <div class="position-relative"><a class="stretched-link" href="${questUrl}"></a><img
                        class="img-fluid aspect-ratio-16x9 object-fit-cover rounded d-block w-100"
                        src="<c:url value='/assets/img/spaceship.jpg'/>" alt="${quest.name}">
                    <div class="py-4">
                        <h4>${quest.name}</h4>
                        <p>${quest.description}</p>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

<%@include file="parts/footer.jsp" %>
