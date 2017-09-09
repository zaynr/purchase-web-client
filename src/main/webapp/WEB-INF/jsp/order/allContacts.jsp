<%--
  Created by IntelliJ IDEA.
  User: zaynr
  Date: 2017/9/9
  Time: 14:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>查看联系人</title>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/orderDisplay.js"></script>
</head>
<body>
<div class="container">
    <br>
    <br>
    <div class="table-responsive">
        <table class="table table-bordered table-hover" >
            <thead>
            <tr>
                <th>采购商联系方式</th>
                <th>供应商联系方式</th>
                <th>合作次数</th>
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
