<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="parts/header.jsp" %>
<section id="services" class="container mt-3 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-8 col-lg-6">
            <h5 class="mb-2">Создание нового квеста</h5>

            <div class="card bg-light mb-3">
                <div class="card-body py-2">
                    <small class="text-muted d-block mb-1">Пример структуры квеста:</small>
                    <pre class="mb-0" style="font-size: 0.85rem;"><c:import url="/WEB-INF/textfiles/example.txt" charEncoding="UTF-8" /></pre>
                </div>
            </div>

            <form method="post">
                <div class="mb-3">
                    <textarea class="form-control" rows="8" name="new-quest-text" placeholder="Введите текст квеста по шаблону выше..."><c:out value="${sessionScope.savedQuestText}" /></textarea>
                    <c:remove var="savedQuestText" scope="session" />
                </div>
                <button class="btn btn-warning fw-bold px-4" type="submit">Создать</button>
            </form>
        </div>
    </div>
</section>
<%@include file="parts/footer.jsp" %>
