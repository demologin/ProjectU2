<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="parts/header.jsp" %>

<section class="d-flex vw-100 vh-100 flex-column justify-content-center align-items-center p-0 pt-8 pb-8 mb-lg-0"
         id="services" style="margin: auto;">
    <form method="post">
        <div class="row" id="login-row" style="width: 300px;">
            <div class="col" style="height: 40px;width: 300px;"><input
                    class="text-center form-control d-flex flex-column align-items-center align-content-center"
                    type="text" id="login" style="height: 40px;width: 300px;" placeholder="login"
                    name="login">
            </div>
        </div>
        <div class="row" id="password-row" style="padding-top: 10px;">
            <div class="col" style="height: 40px;width: 300px;"><input
                    class="text-center form-control d-flex flex-column align-items-center align-content-center"
                    type="text" style="height: 40px;width: 300px;" placeholder="input password" name="passwordFirst">
            </div>
        </div>
        <div class="row" id="password-row-1" style="padding-top: 10px;">
            <div class="col" style="height: 40px;width: 300px;"><input
                    class="text-center form-control d-flex flex-column align-items-center align-content-center"
                    type="text" style="height: 40px;width: 300px;padding: 0;" placeholder="repeat password"
                    name="passwordSecond"></div>
        </div>
        <div class="row" id="button-row" style="padding-top: 10px;">
            <div class="col d-flex">
                <button class="btn btn-primary d-flex w-100 flex-column align-items-center align-content-center"
                        type="submit">Зарегистрироваться
                </button>
            </div>
        </div>
    </form>
</section>
<%@include file="parts/footer.jsp" %>
