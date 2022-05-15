<%-- Created by IntelliJ IDEA. User: shun_ Date: 2022/5/15 Time: 14:06 To change this template use File | Settings |
  File Templates. --%>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
      <title>登陆界面</title>
    </head>

    <body>
      <form action="${pageContext.request.contextPath}/login" method="post">
        账号：<input type="text" name="userName"><br/>
        密码：<input type="password" name="userPwd"><br/>
        <input type="submit" name="登录">
      </form>
    </body>

    </html>