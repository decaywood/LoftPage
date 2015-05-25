<%--
  Created by IntelliJ IDEA.
  User: decaywood
  Date: 2015/5/10
  Time: 16:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"
         language="java"
         pageEncoding="UTF-8"%>
<%
  String path = request.getContextPath();
%>
<html>
  <head>
    <title>LoftPage</title>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
  </head>
  <body>
    <p>hello!</p>
    <script type="text/javascript">
      document.location = "<%=path%>/login.do"
    </script>
  </body>
</html>
