<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@include file="parts/header.jsp" %>

<section class="d-flex vh-100 align-items-center" id="services">
    <div class="container position-relative" style="display: block;">
        <div class="row text-center">
            <c:forEach var="quest" items="${quests}">
                <div class="col-md-4">
                    <a href="play?questId=${quest.id}">
                         <span class="fa-stack fa-4x">
                            <i class="fa fa-circle fa-stack-2x text-primary"></i>
                            <i class="fa fa-gamepad fa-stack-1x fa-inverse"></i>
                         </span>
                    </a>
                    <h4 class="section-heading">${quest.name}</h4>
                </div>
            </c:forEach>
        </div>
    </div>
</section>
<%@include file="parts/footer.jsp" %>
