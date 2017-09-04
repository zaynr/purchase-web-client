<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/4
  Time: 10:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/orderDisplay.js"></script>
    <title>查看报价</title>
</head>
<body>
<div class="container"style="padding: 1% 10%;">
    <br>
    <div id="message"></div>
    <br>
    <div class="table-responsive">
        <table class="table table-bordered table-hover" >
            <thead>
            <tr>
                <th>报价序列号</th>
                <th>供应商报价</th>
                <th>供应商手机号</th>
                <th>需求序列号</th>
                <th>需求数量</th>
                <th>需求类型</th>
                <th>报价状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="orderTableContent">

            </tbody>
        </table>
    </div>
</div>
</body>
</html>