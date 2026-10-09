<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="com.javarush.quest.config.constant.Schema.Url" %>
<%@ page import="com.javarush.quest.entity.Scene.Type" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%--@elvariable id="scene" type="com.javarush.quest.entity.Scene"--%>

<%@ include file="parts/header.jsp" %>
<div class="container flex-grow-1 py-4">
    <p>${scene.text}</p>
    <div class="hstack gap-3">
        <c:forEach var="option" items="${scene.options}">
            <c:url value="${Url.SCENE}" var="sceneUrl">
                <c:param name="id" value="${option.nextSceneId}"/>
            </c:url>
            <a class="btn btn-primary text-nowrap" role="button" href="${sceneUrl}">${option.text}</a>
        </c:forEach>

        <c:if test="${scene.type == Type.GAME_OVER}">
            <c:url value="${Url.QUEST}" var="questUrl">
                <c:param name="id" value="${scene.questId}"/>
            </c:url>
            <a class="btn btn-primary text-nowrap" role="button" href="${questUrl}">Начать заново</a>
            <a class="btn btn-primary text-nowrap" role="button" href="<c:url value="${Url.HOME}"/>">На главную</a>
        </c:if>
    </div>
</div>
<%@include file="parts/footer.jsp" %>
