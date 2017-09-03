<%--
  Created by IntelliJ IDEA.
  User: zaynr
  Date: 2017/9/3
  Time: 14:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/login/register.js"></script>
    <script src="../../../script/login/login.js"></script>
    <script src="../../../script/login/jQuery-MD5.js"></script>
    <title>注册页面</title>
</head>
<body>
<div class="jumbotron" style="padding: 1% 10%;">
    <form class="bs-example bs-example-form" role="form">
        <br>
        <div class="input-group">
            <span class="input-group-addon">手机号码：</span>
            <input type="text" class="form-control" id="mobile_no" placeholder="注册的手机号">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">用户名：</span>
            <input class="form-control" id="user_name" placeholder="昵称">
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
    </form>
    <div id="provide_type">

    </div>
    <br>
    <button id="register" type="button" class="btn btn-default">注册</button>
    <br>
    <div id="message"></div>
</div>
<div class="modal fade" id="type_detail" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" id="myModalLabel">选择可供应类型</h4>
            </div>
            <br>
            <div align="center">
            <form class="bs-example bs-example-form" role="form">
                <div class="form-group">
                    <label class="col-sm-2 control-label">供应类型: </label>
                    <div class="col-sm-10">
                        <select id="typeSelect" class="form-control">

                        </select>
                    </div>
                </div>
            </form>
            <br>
            <button type="button" id="add" class="btn btn-primary">添加</button>
            </div>
            <br>
            <div id="provide_type_dialog">

            </div>
            <br>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" id="confirm" class="btn btn-primary" data-dismiss="modal">确认</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>
</body>
</html>
