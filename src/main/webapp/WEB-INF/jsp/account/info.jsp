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
    <script type="text/javascript" src="../../../script/citypick/city-picker.data.min.js"></script>
    <script type="text/javascript" src="../../../script/citypick/city-picker.min.js"></script>
    <script src="../../../script/citypick/jquery.cxselect.min.js"></script>

    <script src="../../../script/account/info.js"></script>
    <script src="../../../script/login/jQuery-MD5.js"></script>
    <script src="../../../script/login/login.js"></script>
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
                            <div style="position: relative;">
                                <input id="distpicker" class="form-control" readonly type="text">
                            </div>
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
                <div align="center">
                    <button id="addMoreType" class="btn btn-default" >添加类型</button>
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
    <div class="modal fade" id="type_detail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">选择偏好类型</h4>
                </div>
                <br>
                <form class="bs-example bs-example-form" role="form">
                    <div id="type_picker">
                        <select class="type_category form-control"></select>
                        <select class="type_content form-control"></select>
                    </div>
                </form>
                <div align="center">
                    <br>
                    <button type="button" id="add" class="btn btn-primary">添加</button>
                    <br>
                    <br>
                    <div id="provide_type_dialog">

                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" id="confirm" class="btn btn-primary" data-dismiss="modal">确认</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal -->
    </div>
</body>
</html>
