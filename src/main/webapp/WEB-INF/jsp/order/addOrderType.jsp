<%--
  Created by IntelliJ IDEA.
  User: zaynr
  Date: 2017/9/2
  Time: 10:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <title>设置中心</title>
    <script src="../../../script/citypick/jquery.cxselect.min.js"></script>
    <script src="../../../script/order/orderManage.js"></script>
</head>
<body>
<div class="jumbotron" style="padding: 1% 10%;">
    <br>
    <form class="form-horizontal" role="form">
        <h3>类型管理</h3>
        <br>
        <div id="type_picker">
            <select class="type_category form-control"></select>
            <select class="type_content form-control"></select>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">类型所属大类</span>
            <input id="typeCategory" class="form-control" type="text">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">类型</span>
            <input id="orderType" class="form-control" type="text">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">类型单位</span>
            <input id="unitType" class="form-control" placeholder="吨，台，等单位名称">
        </div>
        <br>
    </form>
    <div align="center">
        <div class="btn-group">
            <button id="addNew" class="btn btn-primary">更新</button>
            <button id="delete" class="btn btn-danger">删除</button>
        </div>
    </div>
    <br>
    <div id="message"></div>
    <br>
</div>
<div class="container">
    <form class="form-horizontal" role="form">
        <br>
        <div class="input-group" align="center">
            <span class="input-group-addon">修改每页报价数</span>
            <input id="offerPageSize" class="form-control" type="number">
        </div>
        <br>
        <div align="center">
            <button id="update" class="btn btn-primary">更新</button>
            <br>
        </div>
        <br>
    </form>
</div>
</body>
</html>
