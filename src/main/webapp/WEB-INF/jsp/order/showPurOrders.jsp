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
    <title>查看当前需求</title>
    <script src="../../../script/order/orderDisplay.js"></script>
</head>
<body>
<div class="container">
    <br>
    <div id="message"></div>
    <br>
    <div class="table-responsive">
    <table class="table table-bordered table-hover" >
        <thead>
        <tr>
            <th>需求序列号</th>
            <th>需求类型</th>
            <th>期望价格</th>
            <th>需求数量</th>
            <th>发布时间</th>
            <th>到期日</th>
            <th>已报价人次</th>
            <th>需求状态</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody id="orderTableContent">

        </tbody>
    </table>
    </div>
    <div align="center">
        <ul class="pagination" style="max-width: 300px; margin: auto;">
            <li class="previous"><a id="prev">&larr; 上一页</a></li>
            <li class="current"><a id="cur">1</a></li>
            <li class="next"><a id="next">下一页 &rarr;</a></li>
        </ul>
    </div>
</div>
</body>
</html>
