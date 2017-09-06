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
    <link href="../../../css/city-picker.css" rel="stylesheet">
    <script src="../../../script/login/login.js"></script>
    <script src="../../../script/login/jQuery-MD5.js"></script>
    <script type="text/javascript" src="../../../script/citypick/city-picker.data.min.js"></script>
    <script type="text/javascript" src="../../../script/citypick/city-picker.min.js"></script>

    <script src="../../../script/account/info.js"></script>
    <script src="../../../script/login/jQuery-MD5.js"></script>
    <title>账户信息</title>
</head>
<body>
    <div class="jumbotron" style="padding: 1% 10%;" align="center">
        <h2>基本信息</h2>
        <p id="userType"></p>
        <br>
        <div class="input-group">
            <span class="input-group">用户名</span>
            <input id="userName" class="form-control">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group">绑定手机</span>
            <input id="mobileNo" class="form-control">
        </div>
        <br>
    </div>

    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <h1>地址</h1>
                <br>
                <form class="form">
                    <div style="position:relative;">
                        <div class="form-group">
                            <input id="distpicker" class="form-control" readonly type="text">
                            <br>
                            <div class="input-group">
                                <span class="input-group">详细地址</span>
                                <input type="text" id="detailAddress" class="form-control" style="width: 400px;">
                            </div>
                        </div>
                    </div>
                </form>
                <br>
                <div id="provideType">

                </div>
                <br>
            </div>
            <div class="col-md-2">
            </div>
            <div class="col-md-4">
                <h1>密码</h1>
                <br>
                <form class="form">
                    <div class="input-group">
                        <span class="input-group">旧密码：</span>
                        <input type="password" id="oldPwd" class="form-control">
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group">新密码：</span>
                        <input type="password" id="newPwd" class="form-control">
                    </div>
                    <br>
                    <div class="input-group">
                        <span class="input-group">重复新密码：</span>
                        <input type="password" id="rptPwd" class="form-control">
                    </div>
                </form>
            </div>
        </div>
        <br>
        <div id="message"></div>
        <br>
        <div align="center">
            <button id="commitChange" class="btn btn-primary">提交更改</button>
        </div>
        <br>
    </div>

</body>
</html>
