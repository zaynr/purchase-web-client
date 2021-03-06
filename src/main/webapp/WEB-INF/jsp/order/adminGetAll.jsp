<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/7
  Time: 21:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>所有订单管理</title>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/adminConf.js"></script>
</head>
<body>
<div class="jumbotron">
    <br>
    <div id="message"></div>
    <br>
    <form class="bs-example bs-example-form" role="form">
        <p><strong>按照从上到下，第一个非空的条件进行查询</strong></p>
        <br>
        <div class="input-group">
            <span class="input-group-addon">采购商手机号：</span>
            <input type="text" class="form-control" id="pur_mobile_no" placeholder="手机号">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">供应商手机号：</span>
            <input type="text" class="form-control" id="pro_mobile_no" placeholder="手机号">
        </div>
        <br>
        <div class="input-group">
            <span class="input-group-addon">订单号：</span>
            <input type="text" class="form-control" id="serial_no" placeholder="订单号">
        </div>
    </form>
    <br>
    <div align="center">
        <button id="query" type="button" class="btn btn-default">查询</button>
    </div>
    <br>
</div>
<div class="container">
    <div align="center" style="margin: auto;">
        <br>
        <ul class="nav nav-pills" role="tablist">
            <li role="presentation" class="active"><a id="current">查看进行需求</a></li>
            <li role="presentation"><a id="his">查看完成需求</a></li>
        </ul>
        <br>
    </div>
    <div class="table-responsive" style="margin: auto; max-width: 800px;">
        <table class="table table-bordered table-hover" >
            <thead>
            <tr>
                <th>订单序列号</th>
                <th>订单类型</th>
                <th>订单状态</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody id="orderTableContent">

            </tbody>
        </table>
    </div>
    <br>
    <div align="center">
        <ul class="pagination" style="max-width: 300px; margin: auto;">
            <li class="previous"><a id="prev">&larr; 上一页</a></li>
            <li class="current"><a id="cur">1</a></li>
            <li class="next"><a id="next">下一页 &rarr;</a></li>
        </ul>
    </div>
    <br>
</div>
</body>
</html>
