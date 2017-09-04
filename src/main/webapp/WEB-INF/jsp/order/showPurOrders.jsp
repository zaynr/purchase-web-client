<%--
  Created by IntelliJ IDEA.
  User: zaynr
  Date: 2017/9/2
  Time: 15:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <jsp:include page="../script-sources.jsp"/>
    <title>查看订单</title>
    <script src="../../../script/order/orderDisplay.js"></script>
</head>
<body>

<div class="container"style="padding: 1% 10%;">
    <div class="table-responsive">
    <table class="table table-bordered table-hover" >
        <thead>
        <tr>
            <th>需求序列号</th>
            <th>期望价格</th>
            <th>需求数量</th>
            <th>采购商手机号</th>
            <th>已报价人次</th>
            <th>需求类型</th>
            <th>需求状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="purTableContent">

        </tbody>
    </table>
    </div>
</div>
</body>
</html>
