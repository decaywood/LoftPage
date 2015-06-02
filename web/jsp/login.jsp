<!DOCTYPE html>
<%--
  Created by IntelliJ IDEA.
  User: decaywood
  Date: 2015/5/24
  Time: 13:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>

    <meta charset="UTF-8">

    <title>登陆界面表单提交js特效</title>

    <link rel="stylesheet" href="<%=path%>/css/login.css" media="screen" type="text/css"/>

</head>
<body>
<div id="loginComponent">
    <form action="<%=path%>/validate.do" method="post" name="loginForm" id="loginForm">
        <fieldset>
            <div>
                <!-- Entypo &#128100; = User -->
                <input type="text"
                       name="userName"
                       id="userName"
                       placeholder="Username"
                       pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{1,19}$"
                       data-validation-msg="Username must be 2 - 20 characters"
                       required/>
                <label for="userName" data-icon="&#128100;">Username</label>
            </div>
            <div>
                <!-- Entypo &#128274; = Locked -->
                <input type="password"
                       name="password"
                       id="password"
                       placeholder="Password"
                       pattern="^[a-zA-Z][a-zA-Z0-9-_\.]{1,19}$"
                       data-validation-msg="Password must be 2-20 characters"
                       required>
                <label for="password" data-icon="&#128274;">Password</label>
                <button id="register" value="Submit" data-icon="&#58542;" title="register"/>
            </div>
            <div>
                <input name="validateCode"
                       type="text"
                       id="validate"
                       placeholder="Identify code"
                       pattern="[a-zA-Z0-9-_\.]{4}$"
                       data-validation-msg="Identify must be 4 characters"
                       errorInfo=""
                       required>
                <label for="validate" data-icon="&#x25;"></label>
                <button id="login" value="Submit" data-icon="&#58542;" title="login"/>
            </div>
            <div>
                <img id="codeImg"
                     alt="click to change"
                     title="click to change"
                     src="">
            </div>
        </fieldset>
    </form>
</div>


<script src='<%=path%>/js/jquery.js'></script>
<script src='<%=path%>/js/login.js'></script>
<script src='<%=path%>/js/smoke.min.js'></script>
<script type="text/javascript">
    smoke.signal("hi ervey one", function (e) {}, {});
    $(function () {
        var userName = "${userName}";
        var password = "${password}";
        var errorInfo = "${errorInfo}";
        $('input#userName').val(userName);
        $('input#password').val(password);
        smoke.signal("hi ervey one", function (e) {}, {});
        if(errorInfo != "") {

        }
    });
</script>

</body>
</html>
