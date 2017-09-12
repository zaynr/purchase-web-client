<%--
  Created by IntelliJ IDEA.
  User: zengzy19585
  Date: 2017/9/12
  Time: 16:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<html>
<head>
    <title>查看合同</title>
    <jsp:include page="../script-sources.jsp"/>
    <script src="../../../script/order/orderDisplay.js"></script>
</head>
<body>
<div class="container">
    <div align="center">
        <br>
        <div id="message"></div>
        <br>
    </div>
    <div class="table-responsive">
        <table class="table table-bordered table-hover" >
            <thead>
            <tr id="tableHead">
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
