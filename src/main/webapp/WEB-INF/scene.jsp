<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%@ include file="parts/header.jsp" %>
<%--@elvariable id="scene" type="com.javarush.quest.entity.Scene"--%>

<div class="container flex-grow-1 py-4">
    <p>${scene.text}</p>
    <div class="hstack gap-3">
        <c:forEach var="option" items="${scene.options}">
            <c:url value='/scenes' var="sceneUrl">
                <c:param name="id" value="${option.nextSceneId}"/>
            </c:url>
            <a class="btn btn-primary text-nowrap" role="button" href="${sceneUrl}">${option.text}</a>
        </c:forEach>

        <c:forEach var="systemAction" items="${scene.systemActions}">
            <a class="btn btn-secondary text-nowrap" role="button" href="#">${systemAction.text}</a>
        </c:forEach>
    </div>
</div>

<%@include file="parts/footer.jsp" %>
