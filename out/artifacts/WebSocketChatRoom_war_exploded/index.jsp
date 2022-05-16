<%-- Created by IntelliJ IDEA. User: shun_ Date: 2022/5/15 Time: 14:06 To change this template use File | Settings |
  File Templates. --%>
  <%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <html>

    <head>
      <meta name="viewport" content="width=device-width, initial-scale=1.0">
      <link rel="stylesheet" href="./css/index_main.css" />
      <link rel="stylesheet" href="./css/bootstrap.min.css" />
      <title>登陆页面</title>
      <link rel="icon" href="./favicon.ico" type="image/x-icon">
    </head>

    <body>
      <!--
        <form action="${pageContext.request.contextPath}/login" method="post">
        账号：<input type="text" name="userName"><br/>
        密码：<input type="password" name="userPwd"><br/>
        <input type="submit" name="登录">
      </form>
    -->
      <nav class="navbar navbar-expand-lg bg-dark navbar-dark" id="fix">
        <div class="container">
          <a href="#" class="navbar-brand">登录</a>
          <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navmenu">
            <span class="navbar-toggler-icon"></span></button>
          <div class="collapse navbar-collapse" id="navmenu">
            <ul class="navbar-nav ms-auto">
              <li class="nav-item">
                <div class="nav-link" id="github">Github</div>
              </li>
            </ul>
          </div>
        </div>
      </nav>
      <div id="bg">
        <div class="bg-contect"></div>
        <div class="container">
          <div id="login">
            <div id="loginin">
              <form action="${pageContext.request.contextPath}/login" method="post">
                <h2 class="text-center">用户登录</h2>
                <div class="form-group">
                  <div class="input-group mb-3">
                    <div class="input-group-prepend">
                      <span class="input-group-text" id="basic-addon1">账号</span>
                    </div>
                    <input name="userName" type="text" class="form-control" placeholder="Username" aria-label="Username"
                      aria-describedby="basic-addon1">
                  </div>
                </div>
                <div class="input-group mb-3">
                  <div class="input-group-prepend">
                    <span class="input-group-text" id="basic-addon1">密码</span>
                  </div>
                  <input name="password" type="password" class="form-control" placeholder="Password" aria-label="Username"
                    aria-describedby="basic-addon1">
                </div>
                <center>
                  <input type="submit" class="btn btn-primary text-center btn-default" value="登录">
                </center>
              </form>
            </div>
          </div>
        </div>
      </div>
      <script src="./js/index_main.js"></script>
    </body>

    </html>