<%--
  Created by IntelliJ IDEA.
  User: zaynr
  Date: 2017/9/3
  Time: 0:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <title>账户信息</title>
</head>
<body>
    <div class="jumbotron" style="padding: 1% 10%;">
        <h2>基本信息</h2>
        <br>
        <div class="input-group">
            <span class="input-group-addon">用户名：</span>
            <input id="name" class="form-control">
        </div>
    </div>

    <div class="container"style="padding: 1% 10%;">
        <div class="row">
            <div class="col-md-4">
                <h1>信息1</h1>
            </div>
            <div class="col-md-4">
                <h1>信息2</h1>
            </div>
            <div class="col-md-4">
                <h1>密码</h1>
                <br>
                <div class="input-group">
                    <span class="input-group-addon">旧密码：</span>
                    <input type="password" id="oldPwd" class="form-control">
                </div>
                <br>
                <div class="input-group">
                    <span class="input-group-addon">新密码：</span>
                    <input type="password" id="newPwd" class="form-control">
                </div>
                <br>
                <div class="input-group">
                    <span class="input-group-addon">重复新密码：</span>
                    <input type="password" id="rptPwd" class="form-control">
                </div>
            </div>
        </div>
        <br>
        <div align="center">
            <button id="placeOrder" class="btn btn-primary">提交更改</button>
        </div>
        <br>
        <div id="message"></div>
    </div>

</body>
</html>
