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

    <title>Loft Page</title>

    <link rel="stylesheet" href="<%=path%>/css/login/login.css" media="screen" type="text/css"/>
    <link rel="stylesheet" href="<%=path%>/css/dependency/smoke.css" media="screen" type="text/css"/>

</head>
<body id="home">
<div id="loginComponent">
    <div id="border start">
        <form action="<%=path%>/mainPage.do" method="post" name="loginForm" id="loginForm">
            <table>
                <tr>
                    <td>
                        <input type="text"
                               name="userName"
                               id="userName"
                               placeholder="Username"
                               data-validation-msg="Username must be 2 - 20 characters"
                               required/>
                    </td>
                    <td>
                        <button id="login" type="button" title="login">
                            Login
                        </button>
                    </td>

                </tr>
                <tr>
                    <td>
                        <input type="password"
                               name="password"
                               id="password"
                               placeholder="Password"
                               data-validation-msg="Password must be 2-20 characters"
                               required>
                    </td>
                    <td>
                        <button id="register" type="button" title="register" >
                            Register
                        </button>
                    </td>

                </tr>
                <tr>
                    <td>
                        <input name="validateCode"
                               type="text"
                               id="validate"
                               placeholder="Identify code"
                               pattern="[a-zA-Z0-9-_\.]{4}$"
                               data-validation-msg="Identify must be 4 characters"
                               errorInfo=""
                               required>
                    </td>
                    <td>
                        <img id="codeImg"
                             alt="click to change"
                             title="click to change"
                             src="">
                    </td>
                </tr>
            </table>

        </form>
    </div>
</div>


<script src='<%=path%>/js/dependency/jquery.js'></script>
<script src='<%=path%>/js/dependency/smoke.min.js'></script>
<script src='<%=path%>/js/login/login.js'></script>

</body>
</html>
