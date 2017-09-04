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
    <title>添加订单类型</title>
    <script src="../../../script/order/orderManage.js"></script>
</head>
<body>
<div class="container"style="padding: 1% 10%;">
    <form class="form-horizontal" role="form">
        <br>
        <div class="form-group">
            <label class="col-sm-2 control-label">添加类型</label>
            <div class="col-sm-10">
                <input id="orderType" class="form-control" type="text">
            </div>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">类型单位</span>
            <input type="number" id="orderAmount" class="form-control" placeholder="吨，台，等单位名称">
        </div>
        <br>
    </form>
    <div align="center">
    <button id="addNew" class="btn btn-primary">添加</button>
    <br>
    <div id="message"></div>
    </div>
    <br>
</div>
</body>
</html>