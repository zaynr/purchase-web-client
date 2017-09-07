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
    <script src="../../../script/order/orderManage.js"></script>
</head>
<body>
<div class="jumbotron" style="padding: 1% 10%;">
    <br>
    <div id="message"></div>
    <form class="form-horizontal" role="form">
        <br>
        <div class="input-group">
            <span class="input-group-addon">添加类型</span>
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
        <button id="addNew" class="btn btn-primary">添加</button>
        <br>
    </div>
    <br>
</div>
<div class="container">
    <div class="row">
        <div class="col-md-4">
        </div>
        <div class="col-md-4">
            <br>
            <div class="input-group" align="center">
                <span class="input-group-addon">修改每页报价数</span>
                <input id="offerPageSize" class="form-control" type="num">
            </div>
            <br>
            <div align="center">
                <button id="update" class="btn btn-primary">更新</button>
                <br>
            </div>
            <br>
        </div>
        <div class="col-md-4">
        </div>
    </div>
</div>
</body>
</html>
