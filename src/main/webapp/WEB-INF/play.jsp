<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="parts/header.jsp" %>

<section id="services" class="d-flex vh-100 justify-content-center align-items-center">
    <form class="d-flex flex-column" method="POST">
        <header>${question.question}</header>
        <c:if test="${statement eq 'PLAY'}">
            <p>Выбери ответ:</p>
            <c:forEach var="answer" items="${question.answers}">
                <div class="form-check">
                    <label class="form-check-label">
                        <input name="nextQuestionId"
                               value="${answer.nextId}"
                               class="form-check-input"
                               type="radio"/>
                            ${answer.answer}
                    </label>
                </div>
            </c:forEach>
            <button class="btn btn-primary" type="submit" style="margin-top: 15px;">Ответить</button>
        </c:if>
        <c:if test="${statement ne 'PLAY'}">
           <button class="btn btn-primary" type="submit" style="margin-top: 15px;">Начать новую игру</button>
        </c:if>
    </form>
</section>

<%@include file="parts/footer.jsp" %>
