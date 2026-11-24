<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="parts/header.jsp" %>
<section class="d-flex vh-100 flex-column justify-content-center align-items-center" style="margin: auto;">
    <p style="margin: 0; font-size: 25px;">
        Вы уверены, что хотите покинуть нас?
    </p>
    <div class="col d-flex justify-content-center">
        <div class="btn-group" role="group">
            <form action="/exit" method="post">
                <div class="btn-group" role="group">
                    <a class="btn btn-primary text-center w-25" role="button" style="margin: 23px;font-size: 25px;" href="/home">Отмена</a>
                    <button class="btn btn-primary text-center w-25" type="submit" style="margin: 23px;font-size: 25px;">Выйти</button>
                </div>
            </form>
        </div>
    </div>
</section>
<%@include file="parts/footer.jsp" %>

