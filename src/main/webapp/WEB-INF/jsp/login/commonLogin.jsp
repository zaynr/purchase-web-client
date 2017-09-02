<%--
  Created by IntelliJ IDEA.
  User: zaynr
  Date: 2017/9/2
  Time: 8:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/login.js"></script>
    <script src="../../../script/jQuery-MD5.js"></script>
    <title>用户登录</title>
</head>
<body>
<div class="jumbotron" style="padding: 1% 10%;">
    <form class="bs-example bs-example-form" role="form">
        <div class="input-group">
            <span class="input-group-addon">手机号码：</span>
            <input type="text" class="form-control" id="mobile_no" placeholder="注册的手机号">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">密码：</span>
            <input type="password" id="password" class="form-control">
        </div>
        <br>
        <select id="user_type" class="form-control">
            <option value="1">采购商</option>
            <option value="2">供应商</option>
        </select>
        <br>
        <div id="message"></div>
        <div class="btn-group" style="padding: 5% 0% 0%;">
            <br>
            <button id="login" type="button" class="btn btn-default">登录</button>
            <button id="register" type="button" class="btn btn-default">注册</button>
        </div>
    </form>
</div>
</body>
</html>
