<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<footer class="fixed-bottom" style="padding: 0;">
    <c:if test="${not empty errorMessage}">
        <div class="bg-dark text-primary text-center fw-bold py-2 border-top border-bottom border-secondary">
            ${errorMessage}
        </div>
    </c:if>

    <div class="row" style="margin: 0;">
        <div class="col-md-4 w-100" style="width: 1140px;padding: 0;"><span class="copyright" style="text-align: center;">Copyright&nbsp;© Brand 2026</span></div>
    </div>
</footer>
<script src="/assets/bootstrap/js/bootstrap.min.js"></script>
<script src="/assets/js/agency.js"></script>
<script src="/assets/js/upload.js"></script>
</body>

</html>
