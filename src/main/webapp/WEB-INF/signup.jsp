<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.javarush.quest.config.constant.Schema.Url" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="parts/header.jsp" %>
<section class="position-relative py-4 py-xl-5">
    <div class="container">
        <div class="row mb-5">
            <div class="col-md-8 col-xl-6 text-center mx-auto">
                <h2>Регистрация</h2>
                <p>Чтобы начать пользоваться сервисом, необходимо зарегистрироваться:</p>
            </div>
        </div>
        <div class="row d-flex justify-content-center">
            <div class="col-md-6 col-xl-4">
                <div class="card bg-dark-subtle mb-5">
                    <div class="card-body d-flex flex-column align-items-center">
                        <div class="bs-icon-sm bs-icon-circle bs-icon-primary my-4 bs-icon">
                            <svg class="bi bi-person" xmlns="http://www.w3.org/2000/svg" width="1em" height="1em"
                                 fill="currentColor" viewBox="0 0 16 16">
                                <path d="M8 8a3 3 0 1 0 0-6 3 3 0 0 0 0 6m2-3a2 2 0 1 1-4 0 2 2 0 0 1 4 0m4 8c0 1-1 1-1 1H3s-1 0-1-1 1-4 6-4 6 3 6 4m-1-.004c-.001-.246-.154-.986-.832-1.664C11.516 10.68 10.289 10 8 10s-3.516.68-4.168 1.332c-.678.678-.83 1.418-.832 1.664z"></path>
                            </svg>
                        </div>
                        <form class="text-center" method="post">
                            <div class="mb-3"><label>
                                <input class="form-control" type="text" name="login" placeholder="Имя пользователя"
                                       autofocus="" value="test"></label>
                            </div>
                            <div class="mb-3"><label>
                                <input class="form-control" type="password" name="password"
                                       placeholder="Пароль" value="test"></label>
                            </div>
                            <div class="mb-3">
                                <button class="btn btn-primary w-100 d-block" type="submit">Создать аккаунт</button>
                            </div>
                        </form>
                        <p class="text-muted">Уже есть аккаунт?&nbsp;<a href="<c:url value="${Url.LOGIN}"/>">Войти</a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>
<%@include file="parts/footer.jsp" %>
