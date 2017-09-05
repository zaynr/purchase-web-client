<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/1
  Time: 19:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <title>需求下单</title>
    <script src="../../../script/order/orderManage.js"></script>
</head>
<body>

<div class="container">
    <form class="form-horizontal" role="form">
        <br>
        <div class="input-group">
            <label class="input-group-addon">购买类型</label>
            <select id="typeSelect" class="form-control">

            </select>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">订购数量</span>
            <input type="number" id="orderAmount" class="form-control" placeholder="需求数量">
            <span id="typeUnit" class="input-group-addon">单位</span>
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">期望报价</span>
            <input id="expect" class="form-control" onkeyup="num(this)" placeholder="供应商将会看到">
            <span class="input-group-addon">元</span>
        </div>
        <br>
    </form>
    <div align="center">
        <button id="placeOrder" class="btn btn-primary">发布需求</button>
    </div>
    <br>
    <div id="message"></div>
</div>

</body>
</html>
