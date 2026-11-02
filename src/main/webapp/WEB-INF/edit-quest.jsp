<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>

<c:choose>
    <c:when test="${empty requestScope.quest}">
        <h1>Нет доступа</h1>
        <p>Редактировать квест может только его автор.</p>
        <a class="btn" href="list-quest">К списку квестов</a>
    </c:when>
    <c:otherwise>
        <c:set var="quest" value="${requestScope.quest}"/>

        <p style="margin: 0 0 8px; font-size: 13px; letter-spacing: 0.15em; text-transform: uppercase; color: #B3AA98;">Конструктор квеста</p>
        <h1>${quest.name}</h1>
        <p style="color: #B3AA98;">${quest.description}</p>

        <div class="card" style="border-color: #D4B26A;">
            <h2 style="font-size: 24px;">Как собрать квест</h2>
            <p style="margin: 4px 0;"><b>1.</b> Внизу добавь <b>главы</b> и <b>финалы</b>. Первая глава станет началом квеста.</p>
            <p style="margin: 4px 0;"><b>2.</b> В каждой главе добавь <b>варианты выбора</b> и укажи, куда каждый ведёт.</p>
            <p style="margin: 4px 0;"><b>3.</b> Нажми «Сыграть» и проверь, что всё работает.</p>
            <p style="margin: 12px 0 0; font-size: 14px; color: #B3AA98;">
                Совет: сначала добавь все главы и финалы, потом связывай их выбором —
                в списке «Ведёт к» есть только то, что уже добавлено.
            </p>
        </div>

        <c:if test="${not empty requestScope.questions}">
            <p><a class="btn btn-outline" href="play-game?questId=${quest.id}">Сыграть в свой квест</a></p>
        </c:if>

        <h2 style="margin-top: 40px;">Главы и финалы</h2>

        <c:if test="${empty requestScope.questions}">
            <p style="color: #B3AA98;">Пока пусто. Начни с первой главы — форма внизу.</p>
        </c:if>

        <c:forEach var="question" items="${requestScope.questions}">
            <article class="card">
                <p style="margin: 0; font-size: 13px; color: #B3AA98;">
                    <c:choose>
                        <c:when test="${question.gameState == 'WIN'}"><span style="color: #D4B26A;">Финал: победа</span></c:when>
                        <c:when test="${question.gameState == 'LOSE'}"><span style="color: #E58A78;">Финал: поражение</span></c:when>
                        <c:otherwise>Глава</c:otherwise>
                    </c:choose>
                    <c:if test="${question.id == quest.startQuestionId}"> · начало квеста</c:if>
                </p>
                <h2 style="font-size: 26px; margin: 4px 0 8px;">${question.title}</h2>

                <form method="post" action="edit-quest"
                      onsubmit="return confirm('Удалить «${question.title}» и все варианты выбора, которые к ней ведут?');"
                      style="max-width: none; margin: 0 0 8px; padding: 0; background: none; border: 0;">
                    <input type="hidden" name="action" value="deleteQuestion">
                    <input type="hidden" name="questId" value="${quest.id}">
                    <input type="hidden" name="questionId" value="${question.id}">
                    <button type="submit" class="btn-outline" style="padding: 4px 10px; font-size: 13px;">Удалить главу</button>
                </form>

                <p>${question.text}</p>

                <c:if test="${question.gameState == 'PLAY'}">
                    <c:if test="${empty question.answers}">
                        <p style="color: #E58A78; font-size: 14px;">В этой главе пока нет выбора — игрок здесь застрянет.</p>
                    </c:if>

                    <c:forEach var="answer" items="${question.answers}" varStatus="status">
                        <form method="post" action="edit-quest"
                              style="display: flex; align-items: center; gap: 12px; max-width: none; margin: 4px 0;
                                     padding: 0; background: none; border: 0;">
                            <input type="hidden" name="action" value="deleteAnswer">
                            <input type="hidden" name="questId" value="${quest.id}">
                            <input type="hidden" name="questionId" value="${question.id}">
                            <input type="hidden" name="answer" value="${status.index}">
                            <span>→ ${answer.text}
                                <span style="color: #B3AA98;">(ведёт к «${requestScope.titles[answer.nextQuestionId]}»)</span>
                            </span>
                            <button type="submit" class="btn-outline" style="padding: 4px 10px; font-size: 13px;">Удалить</button>
                        </form>
                    </c:forEach>

                    <form method="post" action="edit-quest"
                          style="max-width: none; margin-top: 16px; padding: 16px; background: #14111A;">
                        <input type="hidden" name="action" value="answer">
                        <input type="hidden" name="questId" value="${quest.id}">
                        <input type="hidden" name="questionId" value="${question.id}">
                        <p style="margin: 0 0 8px;">
                            <label>Новый вариант выбора<input name="text" type="text" required
                                                              placeholder="Например: Открыть левую дверь"></label>
                        </p>
                        <p style="margin: 0 0 12px;">
                            <label>Ведёт к
                                <select name="next" style="width: 100%; height: 44px; margin-top: 6px; background: #14111A;
                                        border: 1px solid #3A3346; border-radius: 6px; color: #EDE6D6; padding: 0 12px;">
                                    <c:forEach var="target" items="${requestScope.questions}">
                                        <option value="${target.id}">
                                            <c:choose>
                                                <c:when test="${target.gameState == 'WIN'}">Финал: </c:when>
                                                <c:when test="${target.gameState == 'LOSE'}">Финал: </c:when>
                                                <c:otherwise>Глава: </c:otherwise>
                                            </c:choose>${target.title}
                                        </option>
                                    </c:forEach>
                                </select>
                            </label>
                        </p>
                        <button type="submit">Добавить выбор</button>
                    </form>
                </c:if>
            </article>
        </c:forEach>

        <h2 style="margin-top: 40px;">Добавить главу или финал</h2>
        <form method="post" action="edit-quest" style="max-width: none;">
            <input type="hidden" name="action" value="question">
            <input type="hidden" name="questId" value="${quest.id}">
            <p>
                <label>Что добавить
                    <select name="state" style="width: 100%; height: 44px; margin-top: 6px; background: #14111A;
                            border: 1px solid #3A3346; border-radius: 6px; color: #EDE6D6; padding: 0 12px;">
                        <option value="PLAY">Глава (с вариантами выбора)</option>
                        <option value="WIN">Финал: победа</option>
                        <option value="LOSE">Финал: поражение</option>
                    </select>
                </label>
            </p>
            <p><label>Название<input name="title" type="text" required placeholder="Например: Две двери"></label></p>
            <p>
                <label>Текст
                    <textarea name="text" rows="4" placeholder="Что происходит в этой главе"
                              style="width: 100%; margin-top: 6px; padding: 12px; box-sizing: border-box;
                                     background: #14111A; border: 1px solid #3A3346; border-radius: 6px;
                                     color: #EDE6D6; font: 15px Inter, sans-serif;"></textarea>
                </label>
            </p>
            <button type="submit">Добавить</button>
        </form>
    </c:otherwise>
</c:choose>

<%@ include file="parts/footer.jsp" %>