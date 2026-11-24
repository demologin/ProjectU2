<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="parts/header.jsp" %>

<section class="vh-100 align-items-center" id="services">
    <form method="post" enctype="multipart/form-data">
        <div class="row align-items-center mb-4">
            <div class="col-4 text-end">
                <div class="p-3 border border-2 rounded-3 bg-light text-center d-inline-block ms-3">
                    <span class="text-muted d-block small text-uppercase fw-semibold mb-1" style="letter-spacing: 0.5px;">Score</span>
                    <span class="display-5 fw-bold text-primary px-2">${currentUser.score}</span>
                </div>
            </div>
            <div class="col-4 mx-auto text-center">
                <div class="ratio ratio-1x1 mb-2 mx-auto" style="max-width: 150px;">
                    <img class="img-thumbnail img-fluid object-fit-cover"
                         id="avatarPreview"
                         src="${imagePath}">
                </div>
            </div>
            <div class="col-4"></div>
        </div>
        <c:forEach var="field" items="${[
                {'label': 'login', 'value': currentUser.login},
                {'label': 'Имя', 'value': currentUser.name},
                {'label': 'Фамилия', 'value': currentUser.surname},
                {'label': 'День рождения', 'value': currentUser.birthday},
                {'label': 'Статус', 'value': currentUser.role}
            ]}">

            <div class="row">
                <div class="col" style="height: 40px;">
                    <p style="font-size: 25px; text-align: right;">
                            ${field.label}
                    </p>
                </div>
                <div class="col" style="height: 40px;">
                    <input class="form-control" type="text" value="${field.value}" readonly>
                </div>
                <div class="col" style="height: 40px;"></div>
            </div>
        </c:forEach>
    </form>
</section>
<%@include file="parts/footer.jsp" %>
