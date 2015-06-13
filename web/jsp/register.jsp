<%--
  Created by IntelliJ IDEA.
  User: decaywood
  Date: 2015/6/9
  Time: 19:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>

    <title>Loft Page Register</title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="<%=path%>/css/register/register.css" media="screen" type="text/css"/>
    <link rel="stylesheet" href="<%=path%>/css/dependency/smoke.css" media="screen" type="text/css"/>
</head>
<body id="home">
<div id="registerComponent">
    <div id="border start">
        <div>
            <span id="leftBlock">
                  <table>
                      <tr>
                          <input type="text"
                                 name="userName"
                                 id="userName"
                                 placeholder="User Name"
                                 data-validation-msg="Username must be 2 - 20 characters"
                                 required/>
                      </tr>
                      <tr>
                          <input type="password"
                                 name="password"
                                 id="password"
                                 placeholder="Password"
                                 data-validation-msg="Password must be 2-20 characters"
                                 required>
                      </tr>
                      <tr>
                          <input type="text"
                                 name="userNickName"
                                 id="userNickName"
                                 placeholder="Nick Name"
                                 data-validation-msg="Nick Name must be 2-20 characters"
                                 required>
                      </tr>
                      <tr>
                          <input type="email"
                                 name="userEmail"
                                 id="userEmail"
                                 placeholder="Email">
                      </tr>
                      <tr>
                          <input type="tel"
                                 name="userPhone"
                                 id="userPhone"
                                 placeholder="Phone Number">
                      </tr>
                      <tr>
                          <div>
                                <span>
                                    <button type="button" id="commitButton">Commit</button>
                                </span>
                                <span>
                                    <button type="button" id="cancelButton">Cancel</button>
                                </span>

                          </div>
                      </tr>

                  </table>
            </span>
            <span id="rightBlock">
                 <iframe src="<%=path%>/jsp/upload.html" frameborder="0" scrolling="no" width="350"
                         height="350"></iframe>
            </span>
        </div>
    </div>

</div>
<script src='<%=path%>/js/dependency/jquery.js'></script>
<script src='<%=path%>/js/dependency/smoke.min.js'></script>
<script src="<%=path%>/js/register/register.js"></script>
</body>
</html>
