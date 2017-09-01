<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <meta name="description" content="">
        <meta name="author" content="">

        <link href="css/bootstrap.min.css" rel="stylesheet">

        <script type="text/javascript" src="script/jquery-3.2.1.js"></script>
        <script type="text/javascript" src="script/jQuery-MD5.js"></script>
        <script type="text/javascript" src="script/login.js"></script>
        <script type="text/javascript" src="script/bootstrap.min.js"></script>
        <title>登录</title>
    </head>
    <body>
        <div class="container">
            <div id="test">${test}</div>
            <br>手机号码：<br>
            <input id="mobile_no" type="number"/>
            <br/>密码：<br>
            <input id="password" type="password"/>
            <br>
            <select id="user_type">
                <option value="1">采购商</option>
                <option value="2">供应商</option>
            </select>
            <br>
            <button id="login">登录</button>
            <button id="register">注册</button>
        </div>
    </body>
</html>
