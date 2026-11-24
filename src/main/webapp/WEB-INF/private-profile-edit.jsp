<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="parts/header.jsp"%>

<section class="vh-100 align-items-center" >
        <form method="post" enctype="multipart/form-data">
            <div class="row align-items-center mb-4">
                <div class="col-4 text-end">
                    <div class="p-3 border border-2 rounded-3 bg-light text-center d-inline-block ms-3">
                        <span class="text-muted d-block small text-uppercase fw-semibold mb-1" style="letter-spacing: 0.5px;">Score</span>
                        <!-- Значение очков -->
                        <span class="display-5 fw-bold text-primary px-2">${enteredUser.score}</span>
                    </div>
                </div>
                <div class="col-4 mx-auto text-center">
                    <div class="ratio ratio-1x1 mb-2 mx-auto" style="max-width: 150px;">
                        <img class="img-thumbnail img-fluid object-fit-cover"
                             id="avatarPreview"
                             src="${imagePath}">
                    </div>
                    <button class="btn btn-link btn-sm text-center w-100" id="btn-load-image" type="button"
                            onclick="document.getElementById('filePicker').click();">Загрузить фото</button>
                    <input class="form-control d-none" type="file" id="filePicker" name="image" accept="image/*">
                </div>
                <div class="col-4"></div>
            </div>
            <c:forEach var="field" items="${[
                {'label': 'login', 'value': requestScope.enteredUser.login, 'type':'text', 'name':'login'},
                {'label': 'password', 'value': requestScope.enteredUser.password, 'type':'password', 'name':'password'},
                {'label': 'Имя', 'value': requestScope.enteredUser.name, 'type':'text', 'name':'name'},
                {'label': 'Фамилия', 'value': requestScope.enteredUser.surname, 'type':'text', 'name':'surname'},
                {'label': 'День рождения', 'value': requestScope.enteredUser.birthday, 'type':'date', 'name':'birthday'}
            ]}">
                <div class="row">
                    <div class="col" style="height: 40px;">
                        <p style="margin: 0; font-size: 25px; text-align: right;">
                                ${field.label}
                        </p>
                    </div>
                    <div class="col" style="height: 40px;">
                        <input class="form-control" type="${field.type}" value="${field.value}" name="${field.name}">
                    </div>
                    <div class="col" style="height: 40px;"></div>
                </div>
            </c:forEach>
            <div class="row" id="role-row">
                <div class="col" style="height: 40px;">
                    <p style="margin-top: 0;margin-right: 0px;margin-bottom: 0;margin-left: 0;font-size: 25px;text-align: right;">Статус</p>
                </div>
                <div class="col">
                    <select name="role"  class="form-select">
                        <c:forEach var="role" items="${roles}">
                            <c:set var="isSelected" value="${role == enteredUser.role ? 'selected' : ''}" />
                            <option value="${role}" ${isSelected}>
                                    ${role}
                            </option>
                        </c:forEach>
                    </select>
                </div>
                <div class="col" style="height: 40px;"></div>
            </div>
            <div class="col d-flex justify-content-center"><button class="btn btn-primary text-center w-25" type="submit">Изменить</button></div>
        </form>
    </section>
    <%@include file="parts/footer.jsp"%>
