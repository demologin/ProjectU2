<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@include file="head.jsp" %>

<form class="form-horizontal" method="post">
    <fieldset>

        <!-- Form Name -->
        <legend>Sign-Up</legend>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="login">Login</label>
            <div class="col-md-4">
                <input
                        id="login"
                        name="login"
                        type="text"
                        placeholder="your login"
                        class="form-control input-md">
            </div>
        </div>

        <!-- Password input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="password">Password</label>
            <div class="col-md-4">
                <input
                        id="password"
                        name="password"
                        type="password"
                        placeholder="your password"
                        class="form-control input-md">

            </div>
        </div>

        <!-- Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="signup"></label>
            <div class="col-md-4">
                <button
                        id="signup"
                        name="signup"
                        class="btn btn-success">Sign-Up
                </button>
            </div>
        </div>

    </fieldset>
</form>
