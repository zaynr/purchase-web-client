<%@page isELIgnored="false"%>
<%@page pageEncoding="UTF-8"%>
<html>
    <head>
        <script type="text/javascript" src="script/jquery-3.2.1.js"></script>
        <script type="text/javascript" src="script/jQuery-MD5.js"></script>
        <script type="text/javascript" src="script/customer-login.js"></script>
        <title>登录</title>
    </head>
    <body>
        <div id="test">${test}</div>
        <br>用户名：<br>
        <input id="serial_number" type="number"/>
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
    </body>
</html>
