<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/7
  Time: 21:46
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <title>报价管理</title>
    <script src="../../../script/order/adminConf.js"></script>
</head>
<body>
<div class="container">
    <br>
    <div id="message"></div>
    <br>
    <form class="bs-example bs-example-form" role="form">
        <div class="input-group">
            <span class="input-group-addon">报价的采购订单</span>
            <input type="text" class="form-control" id="pur_serial_no" placeholder="手机号">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">供应商手机号</span>
            <input type="text" class="form-control" id="pro_mobile_no" placeholder="手机号">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">报价</span>
            <input type="text" class="form-control" id="offer_price" placeholder="报价" onkeyup="num(this)">
            <span class="input-group-addon">元</span>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">寄送快递号</span>
            <input type="text" class="form-control" id="express_no" placeholder="快递号">
        </div>
    </form>
    <br>
    <div align="center">
        <button id="update" type="button" class="btn btn-primary">更新</button>
    </div>
    <br>
</div>
</body>
</html>
