<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp"%>

<!-- State -->
<c:if test="${requestScope.game.state == 'WIN'}">
    <a>Победа</a>
</c:if>
<c:if test="${requestScope.game.state == 'LOSS'}">
    <a>Поражение</a>
</c:if>


<c:if test="${requestScope.game.state == null || requestScope.game.state == 'PLAY'}">

    <form class="form-horizontal" action="play-quest" method="get">
        <fieldset>

            <!-- Form Name -->
            <input type="hidden" name="questId" value="${requestScope.quest.id}">
            <input type="hidden" name="questionId" value="${requestScope.question.id}">
            <input type="hidden" name="gameId" value="${requestScope.game.id}">
            <input type="hidden" name="state" value="${requestScope.state}">
            <legend>"${requestScope.quest.name}"</legend>

            <!-- Multiple Answer -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="answer">${requestScope.question.text}</label>
                <div class="col-md-4">
                    <div class="radio">
                        <label for="answer-0">
                            <input type="radio" name="answerId" id="answer-0"
                                   value="${requestScope.question.answers.get(0).id}" checked="checked">
                            ${requestScope.question.answers.get(0).text}
                        </label>
                    </div>
                    <div class="radio">
                        <label for="answer-1">
                            <input type="radio" name="answerId" id="answer-1"
                                   value="${requestScope.question.answers.get(1).id}">
                            ${requestScope.question.answers.get(1).text}
                        </label>
                    </div>
                </div>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="answer"></label>
                <div class="col-md-4">
                    <button id="answer" name="" class="btn btn-primary">Ответить</button>
                </div>
            </div>

        </fieldset>
    </form>

</c:if>